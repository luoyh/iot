package com.farpower.iot.client.model;

import java.util.Calendar;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 8, 2019
 * @version 1.0
 * @since 1.8
 */
public class Message {
    
    private int address;    // terminal address
    private byte[] raw;     // raw message
    private int afn;        
    private int seq;
    private int dir;        // direct
    private int con;        // 1: confirm, 0: not confirm
    
    public byte[] confirmMessage() {
        // 1: 是否确认
        if (hasConfirm()) { // 需要确认
            return _confirmMessage();
        }
        // 2: 是否是启动站
        // 2.1: 是启动站相应数据
        if (formStation()) {
            int s = service();
            if (s == 0) return null; // 备用服务未实现
            if (s == 1) return null; // 请求/无回答服务
            if (s == 2 || s == 3) { // S2和S3服务需要响应
                return _confirmMessage();
            }
        }
        return null;
    }
    
    private boolean formStation() {
        byte c = raw[6];
        return (c >>> 6 & 0x1) == 1;
    }
    
    /**
     * 确认/否认报文是对接收报文中需要被确认（CON=1）的回答，以及终端对所请求的数据不具备响应条件的否认回答。
     * 该报文为单帧报文，帧序列域的标志位FIR=1，FIN=1，CON=0。格式见图13：
     * @return
     * 
     * @author luoyh(Roy) - Jan 9, 2019
     */
    private byte[] _confirmMessage() {
        byte c = raw[6];
        //68 32 00 32 00 68 0b 00 50 9a 39 00 00 62 00 00 01 00 91 16
        int l = (12 << 2) | (raw[1] & 0b11);
        byte[] b = new byte[20];
        b[0] = 0x68;
        b[1] = (byte) (l & 0xff);
        b[2] = (byte) (l >>> 8 & 0xff);
        b[3] = b[1];
        b[4] = b[2];
        b[5] = 0x68;
        int prm = ((c >>> 6 & 0x1) ^ 1) & 0x1;
        b[6] = (byte) ((prm << 6 | (confirmFeature() & 0xf)) & 0xff); // C: FIR=1，FIN=1，CON=0
        address(b, 7);
        b[12] = 0x00;
        b[13] = (byte) ((0b0110 << 4) | (seq & 0xf)); // seq, don't change seq
        int pn = pn(b, 14, 0);
        int fn = fn(b, pn, 1);
        tail(b, fn, false);
        return b;
    }
    
    private void tail(byte[] bytes, int index, boolean hasTP) {
        if (hasTP) {
            Calendar c = Calendar.getInstance();
            bytes[index ++] = (byte) 0x04;    // PFC
            bytes[index ++] = intAsHexToByte(c.get(Calendar.SECOND));       // s
            bytes[index ++] = intAsHexToByte(c.get(Calendar.MINUTE));       // m
            bytes[index ++] = intAsHexToByte(c.get(Calendar.HOUR_OF_DAY));  // h
            bytes[index ++] = intAsHexToByte(c.get(Calendar.DAY_OF_MONTH)); // d
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
    
    private static int fn(byte[] bytes, int index, int fn) {
        int dt2 = fn / 8;
        int dt1 = fn == 0 ? 0 : (1 << (fn % 8 - 1));
        bytes[index ++] = (byte) (dt1 & 0xff);
        bytes[index ++] = (byte) (dt2 & 0xff);
        return index;
    }
    
    private static int pn(byte[] bytes, int index, int pn) {
        /*
            p1,Da2=1,da1=1,0101
            p2,Da2=1,da1=2,0201
            p3,da2=1,da1=4,0401
            p4,da2=1,da1=8,0801,
            p9,da2=2,da1=1,0102
            p10,da2=2,da1=2,0202,
            P11,da2=2,da1=4,0402,
            P21,da2=3,da1=10,1003,
         */
        int da2 = pn == 0 ? 0 : (pn / 8 + 1);
        int da1 = pn == 0 ? 0 : (1 << (pn % 8 - 1));
        bytes[index ++] = (byte) (da1 & 0xff);
        bytes[index ++] = (byte) (da2 & 0xff);
        
        return index;
    }
    
    private int address(byte[] bytes, int index) {
        int bcd = address / 100000;
        int bin = address % 100000;
        bytes[index ++] = intAsHexToByte(bcd % 100);
        bytes[index ++] = intAsHexToByte(bcd / 100);
        bytes[index ++] = (byte) (bin & 0xff);
        bytes[index ++] = (byte) ((bin >>> 8) & 0xff);
        bytes[index ++] = (byte) 0x00;
        return index;
    }
    
    private byte intAsHexToByte(int value) {
        value = value % 100; // because a byte
        return (byte) ((value / 10 * 16 + value % 10) & 0xff);
    }
    
    /**
     * 获取链路传输服务
     * 当PRM=1时,
     * 功能码代表如下:
     * 4: 发送/无回答, S1,
     * 1: 发送/确认, S2,
     * 9/10/11: 发送/响应, S3
     * @return
     * 
     * @author luoyh(Roy) - Jan 9, 2019
     */
    private int service() {
        int c = raw[6] & 0xf;
        switch (c) {
        case 1: return 2;   
        case 4: return 1;
        case 9:  
        case 10:
        case 11:
            return 3;
        }
        return 0;
    }
    
    
    private int confirmFeature() {
        int c = raw[6] & 0xf;
        switch (c) {
        case 1: return 0;   
        case 9: return 11;  
        case 10:
        case 11:
            return 8;
        }
        return 0;
    }
    
    public boolean hasConfirm() {
        return con == 1;
    }
    
    public int getAddress() {
        return address;
    }
    public void setAddress(int address) {
        this.address = address;
    }
    public byte[] getRaw() {
        return raw;
    }
    public void setRaw(byte[] raw) {
        this.raw = raw;
    }
    public int getAfn() {
        return afn;
    }
    public void setAfn(int afn) {
        this.afn = afn;
    }
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public int getDir() {
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    public int getCon() {
        return con;
    }
    public void setCon(int con) {
        this.con = con;
    }
    
}
