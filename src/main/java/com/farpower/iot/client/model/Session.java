package com.farpower.iot.client.model;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 8, 2019
 * @version 1.0
 * @since 1.8
 */
public class Session {
    
    private int address;
    private ChannelHandlerContext ctx;
    private int seq;
    
    public static Session create(Message message, ChannelHandlerContext ctx) {
        Session session = new Session();
        session.setAddress(message.getAddress());
        session.setCtx(ctx);
        session.setSeq(message.getSeq());
        return session;
    }
    
    public int getAddress() {
        return address;
    }
    public void setAddress(int address) {
        this.address = address;
    }
    public ChannelHandlerContext getCtx() {
        return ctx;
    }
    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }

}
