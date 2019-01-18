package com.farpower.iot;

/**
 * 
 * 
 * @author luoyh(Roy) - Dec 26, 2018
 * @version 1.0
 * @since 1.8
 */
public class PnFn {
    
    public static void main(String[] args) {
        byte[] b = new byte[2];
        fn(b, 0, 11);
        System.out.println(Integer.toHexString((b[0] & 0xff)));
        System.out.println(Integer.toHexString((b[1] & 0xff)));
    }
    
    static int dfn(byte[] bytes) {
        //f1=1,0
        
        return 0;
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

}
