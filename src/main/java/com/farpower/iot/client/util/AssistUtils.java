package com.farpower.iot.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 9, 2019
 * @version 1.0
 * @since 1.8
 */
public final class AssistUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(AssistUtils.class);
    
    public static void printBytes(byte[] bytes) {
        printBytes("print msg: ", bytes);
    }

    public static void printBytes(String tips, byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Strings.padStart(Integer.toHexString(b & 0xff), 2, '0')).append(" ");
        }
        LOG.info("{} {}", tips, sb.toString());
    }
}
