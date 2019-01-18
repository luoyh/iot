package com.farpower.iot.client;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.farpower.iot.client.model.Message;
import com.farpower.iot.client.model.Session;
import com.farpower.iot.client.util.AssistUtils;
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
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
 * @author luoyh(Roy) - Dec 13, 2018
 */
public class App {
    
    static ConcurrentMap<Integer, Session> cache = new ConcurrentHashMap<>();
    
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
                    ch.pipeline()
                    .addLast(new IdleStateHandler(5, 5, 0))
                    .addLast(new ByteToMessageDecoder() {
                        
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
                            if (in.readableBytes() < len + 2) {
                                return; // invalid message
                            }
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
                            out.add(decodeMessage(all));
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
                        public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.err.println(getIpPortStr(ctx) + "=" + ctx.channel().isActive() + "=" + ctx.channel().isOpen());
                            if (msg instanceof Message) {
                                Message message = (Message) msg;
                                AssistUtils.printBytes("upload msg: ", message.getRaw());
                                //cache.put(message.getAddress(), Session.create(message, ctx));
                                Cache.INSTANCE.put(message.getAddress(), Session.create(message, ctx));
                                Sender.send(message);
                            }
                            
                            new Thread(() -> {
                                for (;;) {
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                        System.out.println("delay active:: " + ctx.channel().isActive());
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }

                        @Override
                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                            ctx.flush();
                        }
                        
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // TODO Auto-generated method stub
                            super.channelActive(ctx);
                            
                        }

                        // offline
                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            System.out.println(ctx.channel().id() + " =======================下线了======================  ");
                            System.out.println(getIpPortStr(ctx) + " = " + ctx.channel().isActive());
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
    
    
    public static String getIpPortStr(ChannelHandlerContext ctx){
        InetSocketAddress inetSocketAddress= (InetSocketAddress) ctx.channel().remoteAddress();
        String ip=inetSocketAddress.getAddress().getHostAddress();
        int port=inetSocketAddress.getPort();
        return ip+":"+port;
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
    
    private static Message decodeMessage(byte[] raw) {
        Message msg = new Message();
        msg.setRaw(raw);
        msg.setAddress(hexAsInt(Integer.toHexString((raw[8] & 0xff))) * 10000000
                + hexAsInt(Integer.toHexString(raw[7] & 0xff)) * 10000
                + ((((raw[10] & 0xff) << 8) | (raw[9] & 0xff)) & 0xffff));
        msg.setAfn(raw[12] & 0xff);
        msg.setSeq(raw[13] & 0xf);
        msg.setDir(raw[6] >>> 7 & 0b1);
        msg.setCon(raw[13] >>> 4 & 0b1);
        return msg;
    }
    
    private static int hexAsInt(String hex) {
        return Integer.parseInt(hex);
    }
}
