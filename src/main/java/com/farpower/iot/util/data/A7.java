package com.farpower.iot.util.data;

/**
 * 
 * 
 * @author luoyh(Roy) - Dec 13, 2018
 * @version 1.0
 * @since 1.8
 */
public class A7 {
    
    public static byte[] encode(float value) {
        byte[] b = new byte[2];
        int val = (int) (value * 10) & 0xffff;
        b[0] = (byte) (((val / 10 % 10) >>> 4 & 0xff) | ((val % 10) & 0xf));
        val = val / 10;
        b[1] = (byte) (((val / 100) >>> 4 & 0xff) | ((val / 10 % 10) & 0xf));
        return b;
    }

}
