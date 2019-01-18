package com.farpower.iot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 11, 2019
 * @version 1.0
 * @since 1.8
 */
public class Z1 {

    static int responseSeq = 0;
    static int responseSeq1 = 0;
    
    public static void main(String[] args) {

        LocalDateTime startTime = Instant.ofEpochMilli(1546709400000l).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endTime = Instant.ofEpochMilli(1547481600000l).atZone(ZoneId.systemDefault()).toLocalDateTime();
        while (startTime.isBefore(endTime)) {
            System.out.println(startTime);
            startTime = startTime.plusDays(1);
        }
        for (int i = 0; i < 1; i ++) {
            System.out.println((i + 1) + "=" + getResponseSeq() + "=" + getResponseSeq1());
            System.out.println(responseSeq1 + "," + responseSeq);
        }
    }

    public static int getResponseSeq() {
        responseSeq += 1;
        String seqnumStr = Integer.toHexString(responseSeq);
        seqnumStr = seqnumStr.substring(seqnumStr.length() - 1);
        responseSeq = Integer.parseInt(seqnumStr, 16);
        return responseSeq;
    }

    public static int getResponseSeq1() {
        return (responseSeq1 = ++ responseSeq1 % 0x10);
    }
}
