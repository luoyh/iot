package com.farpower.iot.util.data;

/**
 * 
 * 
 * @author luoyh(Roy) - Dec 13, 2018
 * @version 1.0
 * @since 1.8
 */
public class Decoder {
    
    public static void main(String[] args) {
        String str = "04 00 04 00";
        byte[] b = new byte[4];
        int idx = 0;
        for (String s : str.split(" ")) {
            b[idx ++] = (byte) (Integer.parseInt(s, 16) & 0xff); 
        }
        System.out.println(fn(b));
        System.out.println(pn(b));
    }
    
    //6.    数据单元标识（4B）从左至右： DA1，DA2，DT1，DT2
    //Pn计算公式： (DA2-1)*8+DA1对应的值就是信息点标识pn
    //Fn计算公式：DT2*8+DT1对应的值就是信息类标识Fn
    public static int fn(byte[] b) {
        return (b[3] & 0xff) * 8 + dadt(b[2] & 0xff);
    }
    
    public static int pn(byte[] b) {
        return b[1] == 0 ? 0 : (((b[1] & 0xff) - 1) * 8 + dadt(b[0] & 0xff));
    }
    
    private static int dadt(int val) {
        switch (val) {
        case 1: return 1;
        case 2: return 2;
        case 4: return 3;
        case 8: return 4;
        case 16: return 5;
        case 32: return 6;
        case 64: return 7;
        case 128: return 8;
        }
        return 0;
    }

}
