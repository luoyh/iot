package com.farpower.iot;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import com.google.common.base.Strings;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author luoyh(Roy) - Dec 13, 2018
 */
public class App {
    
    private static int seq = 0;
    static byte[] confirm = {0x68, 0x32, 0x00, 0x32, 0x00, 0x68, 0x0b, 0x00, 0x50, (byte) 0x9a, 0x39, 0x00, 0x00, 0x60, 0x00, 0x00, 0x01, 0x00, (byte) 0x8f, 0x16};

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class) // (3)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ByteToMessageDecoder() {
                        
                        @Override
                        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                            System.out.println(LocalDateTime.now() + " decode::" + ctx.channel().id());
                            for (;;) { // find head
                                if (in.readableBytes() < 10) {
                                    return;
                                }
                                byte head = in.readByte();
                                if ((head & 0xff) != 0x68) {
                                    continue;
                                }
                                break;
                            }
                            // find length
                            byte len0 = in.readByte();
                            byte len1 = in.readByte();
                            byte len2 = in.readByte();
                            byte len3 = in.readByte();
                            if ((len0 & 0xff) != (len2 & 0xff)) {
                                return;
                            }
                            if ((len1 & 0xff) != (len3 & 0xff)) {
                                return;
                            }
                            int len = (((len0 & 0xff) | ((len1 & 0xff) << 8)) >>> 2) & 0xffff;
                            //if (in.readableBytes() M)
                            byte head = in.readByte();
                            if ((head & 0xff) != 0x68) {
                                return;
                            }
                            byte[] all = new byte[6 + 2 + len];
                            all[0] = 0x68 & 0xff;
                            all[1] = len0;
                            all[2] = len1;
                            all[3] = len2;
                            all[4] = len3;
                            all[5] = 0x68 & 0xff;
                            in.readBytes(all, 6, len + 2);
                            for (byte b : all) {
                                System.out.print(Strings.padStart(Integer.toHexString(b & 0xff), 2, '0') + " ");
                            }
                            System.out.println();
                            
                            out.add(all);
                        }
                        
                    })
                    .addLast(new MessageToByteEncoder<byte[]>() {

                        @Override
                        protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
                            out.clear();
                            out.writeBytes(msg);
                        }
                    })
                    .addLast(new ChannelInboundHandlerAdapter() {

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            //super.channelRead(ctx, msg);
                            confirm[6] = 0x0b;
                            confirm[12] = 0x00;
                            confirm[13] = (byte) ((((0x6 << 4) & 0xff) | ((seq ++ % 0xf) & 0xf)) & 0xff);
                            tail(confirm, confirm.length - 2, false);
                            System.out.println("write");
                            for (byte b : confirm) {
                                System.out.print(Strings.padStart(Integer.toHexString(b & 0xff), 2, '0') + " ");
                            }
                            System.out.println();
                            ctx.writeAndFlush(confirm);
                        }

                        @Override
                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                            ctx.flush();
                        }
                        // offline
                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            System.out.println(ctx.channel().id() + " =======================下线了======================  ");
                            super.channelInactive(ctx);
                        }
                    })
                    ;
                }
                
            })
            //.option(ChannelOption.SO_BACKLOG, 128) // (5)
            .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(6001).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    private static void tail(byte[] bytes, int index, boolean hasTP) {
        if (hasTP) {
            LocalDateTime localDate = LocalDateTime.now(ZoneId.systemDefault());
            bytes[index ++] = (byte) 0x04;    // PFC
            bytes[index ++] = intAsHexToByte(localDate.getSecond());       // s
            bytes[index ++] = intAsHexToByte(localDate.getMinute());       // m
            bytes[index ++] = intAsHexToByte(localDate.getHour());  // h
            bytes[index ++] = intAsHexToByte(localDate.getDayOfMonth()); // d
            bytes[index ++] = (byte) 0x00;    // delay(m) 延迟传输时间, 单位分钟
        }
        
        int cs = 0;
        for (int j = 6; j < bytes.length - 2; j ++) {
            cs += bytes[j];
            cs &= 0xff;
        }

        bytes[index] = (byte) cs;
        bytes[index + 1] = (byte) 0x16; // end
    }
    
    private static byte intAsHexToByte(int value) {
        value = value % 100; // because a byte
        return (byte) ((value / 10 * 16 + value % 10) & 0xff);
    }
}
