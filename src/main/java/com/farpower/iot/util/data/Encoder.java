package com.farpower.iot.util.data;

/**
 * 
 * 
 * @author luoyh(Roy) - Dec 13, 2018
 * @version 1.0
 * @since 1.8
 */
public class Encoder {
    
    public static void main(String[] args) {
        int len = (18 << 2 | 0b10) & 0xffff;
        System.out.println(Integer.toHexString(len & 0xff));
        System.out.println((len >>> 8) & 0xff);
        // 68 32 00 32 00 68 c9 00 50 41 14 00 02 77 00 00 04 00 31 09 14 14 b2 18 eb 16
        
        byte[] a1 = a1(2018, 12, 5, 14, 14, 9, 31);
        for (byte b : a1) {
            System.out.println(Integer.toHexString(b & 0xff));
        }
        float value = -58.3f;
        System.out.println(a5(value));
    }
    
    public static byte[] a1(int year, int month, int week, int day, int hour, int minute, int second) {
        byte[] b = new byte[6];
        b[0] = (byte) (((second / 10 % 10 << 4 & 0xff) | (second % 10 & 0xf)) & 0xff);
        b[1] = (byte) (((minute / 10 % 10 << 4 & 0xff) | (minute % 10 & 0xf)) & 0xff);
        b[2] = (byte) (((hour / 10 % 10 << 4 & 0xff) | (hour % 10 & 0xf)) & 0xff);
        b[3] = (byte) (((day / 10 % 10 << 4 & 0xff) | (day % 10 & 0xf)) & 0xff);
        b[4] = (byte) ((((week << 1 | (month / 10 % 10 & 0x1)) << 4 & 0xff) | (month % 10 & 0xf)) & 0xff) ;
        b[5] = (byte) (((year / 10 % 10 << 4 & 0xff) | (year % 10 & 0xf)) & 0xff);
        return b;
    }
    
    public static byte[] a2() {
        throw new AbstractMethodError("Not implementation!!!");
    }
    
    public static byte[] a5(float value) {
        int s = value >= 0.0f ? 0 : 1;
        byte[] b = new byte[2];
        int val = (int) ((s == 0 ? value : -value) * 10);
        b[0] = (byte) ((((val / 10 % 10) << 4 & 0xff) | ((val % 10) & 0xf)) & 0xff);
        val = val / 10;
        b[1] = (byte) (((s << 7 & 0xff) | ((val / 100) << 4 & 0xff) | ((val / 10 % 10) & 0xf)) & 0xff);
        return b;
    }

    public static byte[] a7(float value) {
        value = Math.abs(value);
        byte[] b = new byte[2];
        int val = (int) (value * 10) ;
        b[0] = (byte) ((((val / 10 % 10) << 4 & 0xff) | ((val % 10) & 0xf)) & 0xff);
        val = val / 10;
        b[1] = (byte) ((((val / 100) << 4 & 0xff) | ((val / 10 % 10) & 0xf)) & 0xff);
        return b;
    }
    
    public static byte[] a15(int year, int month, int day, int hour, int minute) {
        byte[] b = new byte[5];
        b[0] = (byte) (((minute / 10 % 10 << 4 & 0xff) | (minute % 10 & 0xf)) & 0xff);
        b[1] = (byte) (((hour / 10 % 10 << 4 & 0xff) | (hour % 10 & 0xf)) & 0xff);
        b[2] = (byte) (((day / 10 % 10 << 4 & 0xff) | (day % 10 & 0xf)) & 0xff);
        b[3] = (byte) (((month / 10 % 10 << 4 & 0xff) | (month % 10 & 0xf)) & 0xff);
        b[4] = (byte) (((year / 10 % 10 << 4 & 0xff) | (year % 10 & 0xf)) & 0xff);
        return b;
    }
    public static byte[] a23(float value) {
        value = Math.abs(value);
        byte[] b = new byte[3];
        int val = (int) (value * 10000);
        b[0] = (byte) ((((val / 10 % 10) << 4 & 0xff) | ((val % 10) & 0xf)) & 0xff);
        b[1] = (byte) ((((val / 1000 % 10) << 4 & 0xff) | ((val / 100 % 10) & 0xf)) & 0xff);
        val = val / 10000;
        b[2] = (byte) ((((val / 10 % 10) << 4 & 0xff) | ((val % 10) & 0xf)) & 0xff);
        return b;
    }
    
    public static byte[] a25(float value) {
        int s = value >= 0.0f ? 0 : 1;
        value = Math.abs(value);
        byte[] b = new byte[3];
        int val = (int) (value * 1000) ;
        b[0] = (byte) ((((val / 10 % 10) << 4 & 0xff) | ((val % 10) & 0xf)) & 0xff);
        b[1] = (byte) ((((val / 1000 % 10) << 4 & 0xff) | ((val / 100 % 10) & 0xf)) & 0xff);
        val = val / 1000;
        b[2] = (byte) (((s << 7 & 0xff) | ((val / 100 % 10) << 4 & 0xff) | ((val / 10 % 10) & 0xf)) & 0xff);
        return b;
    }

  
    public static byte intAsHexToByte(int value) {
        value = value % 100; // because a byte
        return (byte) ((value / 10 * 16 + value % 10) & 0xff);
    }
}
