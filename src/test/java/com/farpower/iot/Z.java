package com.farpower.iot;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.BitSet;

import com.farpower.iot.client.util.AssistUtils;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 8, 2019
 * @version 1.0
 * @since 1.8
 */
public class Z {
    
    public static void main(String[] args) throws Exception {
        byte[] x = {(byte) 0x68, (byte) 0x30, (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x68, (byte) 0x0b, (byte) 0x00, (byte) 0x50, (byte) 0x9a, (byte) 0x39, (byte) 0x00, (byte) 0x00, (byte) 0x63, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x92, (byte) 0x16};
        byte[] y = {(byte) 0x68, (byte) 0x32, (byte) 0x00, (byte) 0x32, (byte) 0x00, (byte) 0x68, (byte) 0x0b, (byte) 0x00, (byte) 0x50, (byte) 0x9a, (byte) 0x39, (byte) 0x00, (byte) 0x00, (byte) 0x60, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x8f, (byte) 0x16};
        tail(x, x.length - 2, false);
        tail(y, y.length - 2, false);

        AssistUtils.printBytes("confirm x: ", x);
        AssistUtils.printBytes("confirm y: ", y);
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

    private static int hexAsInt(String hex) {
        return Integer.parseInt(hex);
    }

}
