package com.farpower.iot;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.farpower.iot.data.Afn04Fn26;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;


/**
 * 
 * @author luoyh(Roy) - Jul 24, 2018
 * @version 1.0
 * @since 1.8
 */
public final class CommandUtil {
    
    public static void main(String[] args) throws Exception {
        byte[] x04f26 = x04f26(500014156, Afn04Fn26.news());
        System.out.println(Arrays.toString(toString(x04f26)));
        
//        int i = 0b01101001_10101001_11110101_11010011;
//                  //101001 10101001 1111 0101 1101 0011
//        byte x = (byte) ((i >>> 8) & 0xff);
//        System.out.println(i);
//        System.out.println(Integer.toBinaryString(x&0xff));
//        //1101_0011
//        //1101 0011
//        
//        //11110101
//        //11110101
//        
//        testMeterConfig();
//        testTaskData();
//        testTerminalSet();
//        byte[] b = ipPort(500014156, "192.168.11.250", 8006, "192.168.11.244", 8007);
//        System.out.println(Arrays.toString(toString(b)));
//
//        CommandServiceImpl x = new CommandServiceImpl();
//        CommunicationMessage msg = x.changeIPAndPort(new Terminal(500014156), "192.168.11.250", 8006, "192.168.11.244", 8007);
//        System.out.println(Arrays.toString(msg.getMessage()));
    }
    

    // ========================= TEST =====================================
    
    public static void testMeterConfig() {
        System.out.println("start testMeterConfig ================================");
        List<Integer> indices = Lists.newArrayList();
        for (int i = 0; i < 4; i ++) {
            indices.add(i);
        }
        System.out.println("cmd: " + Arrays.toString(toString(meterConfigs(500032853, indices))));
        System.out.println("end testMeterConfig ================================");
    }
    
    public static void testTaskData() {
        System.out.println("start testTaskData ================================");
        System.out.println("cmd: " + Arrays.toString(toString(taskData(500032853, 123))));
        System.out.println("end testTaskData ================================");
    }

    public static void testTerminalSet() {
        System.out.println("start testTerminalSet ================================");
        List<Integer> indices = Lists.newArrayList();
        for (int i = 0; i < 4; i ++) {
            indices.add(i);
        }
        System.out.println("cmd: " + Arrays.toString(toString(terminalSet(500032853, indices))));
        System.out.println("end testTerminalSet ================================");
    }
    
    // ========================== END TEST ================================
    
    public static String[] toString(byte[] bytes) {
        String[] r = new String[bytes.length];
        for (int i = 0, len = bytes.length; i < len; i ++) {
            r[i] = Strings.padStart(Integer.toHexString(bytes[i] & 0xff), 2, '0');
        }
        return r;
    }
    
    
    
    public static byte[] ipPort(int address, String ipMain, int portMain, String ipSlave, int portSlave) {
        int index = 0;
        byte[] bytes = new byte[18 + (12)/*user data*/ + 16 + 16 + 6 + 2];
        index = head(bytes, index);
        //0x4a
        bytes[index ++] = (byte) 0b0100_1010;   // C
        index = address(bytes, index, address); // A
        bytes[index ++] = (byte) 0x04;          // AFN
        bytes[index ++] = (byte) 0b1111_0001;   // SEQ
        index = pn(bytes, index, 0);            // PN
        index = fn(bytes, index, 3);           // FN
        
        String[] ips = ipMain.split("\\.");
        for (int i = 0; i < ips.length; i ++) {
            bytes[index ++] = (byte) (Integer.parseInt(ips[i]) & 0xff);
        }
        bytes[index ++] = (byte) (portMain & 0xff);
        bytes[index ++] = (byte) ((portMain >>> 8) & 0xff);

        // slave ip
        ips = ipSlave.split("\\.");
        for (int i = 0; i < ips.length; i ++) {
            bytes[index ++] = (byte) (Integer.parseInt(ips[i]) & 0xff);
        }
        bytes[index ++] = (byte) (portSlave & 0xff);
        bytes[index ++] = (byte) ((portSlave >>> 8) & 0xff);
        
        for (int i = 0; i < 32; i ++) { // apn,pw
            bytes[index ++] = (byte) 0x00;
        }
        
        tail(bytes, index);
        
        return bytes;
    }
    
    //68 32 00 32 00 68 4B 01 91 01 00 F2 0A 77 00 00    
    //01 0B 5D 16   
    
    public static byte[] changeAddress(int address, int newAddress) {
        int index = 0;
        byte[] bytes = new byte[18 + (4)/*user data*/ + 16 + 2];
        index = head(bytes, index);
        //0x4a
        bytes[index ++] = (byte) 0b0100_1010;   // C
        index = address(bytes, index, address); // A
        bytes[index ++] = (byte) 0x04;          // AFN
        bytes[index ++] = (byte) 0b0111_0000;   // SEQ
        index = pn(bytes, index, 0);            // PN
        index = fn(bytes, index, 89);           // FN
        
        int bcd = newAddress / 100000;
        int bin = newAddress % 100000;
        bytes[index ++] = intAsHexToByte(bcd % 100);
        bytes[index ++] = intAsHexToByte(bcd / 100);
        bytes[index ++] = (byte) (bin & 0xff);
        bytes[index ++] = (byte) ((bin >>> 8) & 0xff);
        
        for (int i = 0; i < 16; i ++) {
            bytes[index ++] = (byte) 0x00;
        }
        

        tail(bytes, index, false);
        
        return bytes;
    }
    
    
    public static byte[] terminalSet(int address, List<Integer> indices) {
        int index = 0;
        byte[] bytes = new byte[18 + (1 + indices.size())/*user data*/ + 6 + 2];
        index = head(bytes, index);
        
        bytes[index ++] = (byte) 0b0100_1010;   // C
        index = address(bytes, index, address); // A
        bytes[index ++] = (byte) 0x0a;          // AFN
        bytes[index ++] = (byte) 0b1111_0100;   // SEQ
        index = pn(bytes, index, 0);            // PN
        index = fn(bytes, index, 33);           // FN
        
        bytes[index ++] = (byte) (indices.size() & 0xff);
        //bytes[index ++] = (byte) ((indices.size() >>> 8) & 0xff);

        for (int idx : indices) {
            bytes[index ++] = (byte) (idx & 0xff);
            //bytes[index ++] = (byte) ((idx >>> 8) & 0xff);
        }
        
        tail(bytes, index);
        
        return bytes;
    }
    
    public static byte[] taskData(int address, int taskNum) {
        int index = 0;
        byte[] bytes = new byte[18 + 6 + 2];
        index = head(bytes, index);
        bytes[index ++] = (byte) 0b0100_1010;   // C
        index = address(bytes, index, address); // A
        bytes[index ++] = (byte) 0x0a;          // AFN
        bytes[index ++] = (byte) 0b1111_0100;   // SEQ
        index = pn(bytes, index, taskNum);      // PN
        index = fn(bytes, index, 66);           // FN
        tail(bytes, index);
        return bytes;
    }
    
    public static byte[] x04f26(int address, Afn04Fn26 data) {
        int index = 0;
        byte[] unit = data.data();
        byte[] bytes = new byte[18 + (unit.length)/*user data*/ + 6 + 2];
        index = head(bytes, index);
        
        bytes[index ++] = (byte) 0b0100_1011;   // C
        index = address(bytes, index, address); // A
        bytes[index ++] = (byte) 0x04;          // AFN
        bytes[index ++] = (byte) 0b1110_0100;   // SEQ
        index = pn(bytes, index, 0);            // PN
        index = fn(bytes, index, 26);           // FN
        
        for (byte b : unit) {
            bytes[index ++] = b;
        }
        
        tail(bytes, index);
        
        return bytes;
    }
    
    public static byte[] meterConfigs(int address, List<Integer> indices) {
        int index = 0;
        byte[] bytes = new byte[18 + (2 + indices.size() * 2)/*user data*/ + 6 + 2];
        index = head(bytes, index);
        
        bytes[index ++] = (byte) 0b0100_1011;   // C
        index = address(bytes, index, address); // A
        bytes[index ++] = (byte) 0x0a;          // AFN
        bytes[index ++] = (byte) 0b1110_0100;   // SEQ
        index = pn(bytes, index, 0);            // PN
        index = fn(bytes, index, 10);           // FN
        
        bytes[index ++] = (byte) (indices.size() & 0xff);
        bytes[index ++] = (byte) ((indices.size() >>> 8) & 0xff);

        for (int idx : indices) {
            bytes[index ++] = (byte) (idx & 0xff);
            bytes[index ++] = (byte) ((idx >>> 8) & 0xff);
        }
        
        tail(bytes, index);
        
        return bytes;
    }
    
    
    // 起始字符 + 长度L + 长度L + 起始字符
    private static int head(byte[] bytes, int index) {
        int len = (((bytes.length - 8) << 2) | 0b10) & 0xffff;
        bytes[index ++] = (byte) 0x68;
        bytes[index ++] = (byte) (len & 0xff);
        bytes[index ++] = (byte) ((len >>> 8) & 0xff); 
        bytes[index ++] = bytes[1];
        bytes[index ++] = bytes[2];
        bytes[index ++] = (byte) 0x68;
        return index;
    }
    
    // TP + CS + 0x16
    private static void tail(byte[] bytes, int index, boolean hasTP) {
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
    
    private static void tail(byte[] bytes, int index) {
        tail(bytes, index, true);
    }
    
    // 地址域A
    private static int address(byte[] bytes, int index, int address) {
        int bcd = address / 100000;
        int bin = address % 100000;
        bytes[index ++] = intAsHexToByte(bcd % 100);
        bytes[index ++] = intAsHexToByte(bcd / 100);
        bytes[index ++] = (byte) (bin & 0xff);
        bytes[index ++] = (byte) ((bin >>> 8) & 0xff);
        bytes[index ++] = (byte) 0x00;
        return index;
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
    
    private static byte intAsHexToByte(int value) {
        value = value % 100; // because a byte
        return (byte) ((value / 10 * 16 + value % 10) & 0xff);
    }
    

}
