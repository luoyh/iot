package com.farpower.iot.data;

import java.util.Random;

import com.farpower.iot.util.data.Encoder;

/**
 * 
 * 
 * @author luoyh(Roy) - Dec 13, 2018
 * @version 1.0
 * @since 1.8
 */
public class Afn04Fn26 {
    
    public static void main(String[] args) {
        byte[] a7 = Encoder.a23(rf());
        for (byte b : a7) {
            System.out.println(Integer.toHexString(b));
        }
    }
    
    private static Random random = new Random();
    
    private static float rf() {
        return random.nextFloat() * 1000;
    }
    private static int ri() {
        return random.nextInt(0x7fff);
    }
    
    public static Afn04Fn26 news() {
        Afn04Fn26 r = new Afn04Fn26();
        r.setV0(rf());
        r.setV1(rf());
        r.setV2(rf());
        r.setV3(rf());
        r.setV4(ri());
        r.setV5(rf());
        r.setV6(rf());
        r.setV7(ri());
        r.setV8(rf());
        r.setV9(rf());
        r.setV10(ri());
        r.setV11(rf());
        r.setV12(rf());
        r.setV13(ri());
        r.setV14(rf());
        r.setV15(rf());
        r.setV16(ri());
        r.setV17(rf());
        r.setV18(rf());
        r.setV19(ri());
        r.setV20(rf());
        r.setV21(rf());
        r.setV22(ri());
        r.setV23(rf());
        r.setV24(rf());
        r.setV25(ri());
        r.setV26(rf());
        r.setV27(rf());
        r.setV28(ri());
        r.setV29(rf());
        r.setV30(ri());
        return r;
    }

    public byte[] data() {
        int index = 0;
        byte[] bytes = new byte[57];
        index = append(bytes, Encoder.a7(v0), index);
        index = append(bytes, Encoder.a7(v1), index);
        index = append(bytes, Encoder.a7(v2), index);
        index = append(bytes, Encoder.a7(v3), index);
        bytes[index++] = (byte) (v4 & 0xff);
        index = append(bytes, Encoder.a5(v5), index);
        index = append(bytes, Encoder.a7(v6), index);
        bytes[index++] = (byte) (v7 & 0xff);
        index = append(bytes, Encoder.a5(v8), index);
        index = append(bytes, Encoder.a25(v9), index);
        bytes[index++] = (byte) (v10 & 0xff);
        index = append(bytes, Encoder.a5(v11), index);
        index = append(bytes, Encoder.a25(v12), index);
        bytes[index++] = (byte) (v13 & 0xff);
        index = append(bytes, Encoder.a5(v14), index);
        index = append(bytes, Encoder.a25(v15), index);
        bytes[index++] = (byte) (v16 & 0xff);
        index = append(bytes, Encoder.a5(v17), index);
        index = append(bytes, Encoder.a23(v18), index);
        bytes[index++] = (byte) (v19 & 0xff);
        index = append(bytes, Encoder.a5(v20), index);
        index = append(bytes, Encoder.a23(v21), index);
        bytes[index++] = (byte) (v22 & 0xff);
        index = append(bytes, Encoder.a5(v23), index);
        index = append(bytes, Encoder.a5(v24), index);
        bytes[index++] = (byte) (v25 & 0xff);
        index = append(bytes, Encoder.a5(v26), index);
        index = append(bytes, Encoder.a5(v27), index);
        bytes[index++] = (byte) (v28 & 0xff);
        index = append(bytes, Encoder.a5(v29), index);
        bytes[index++] = (byte) (v30 & 0xff);
        return bytes;
    }

    private int append(byte[] src, byte[] data, int index) {
        for (byte b : data) {
            src[index++] = b;
        }
        return index;
    }

    private float v0;
    private float v1;
    private float v2;
    private float v3;
    private int v4;
    private float v5;
    private float v6;
    private int v7;
    private float v8;
    private float v9;
    private int v10;
    private float v11;
    private float v12;
    private int v13;
    private float v14;
    private float v15;
    private int v16;
    private float v17;
    private float v18;
    private int v19;
    private float v20;
    private float v21;
    private int v22;
    private float v23;
    private float v24;
    private int v25;
    private float v26;
    private float v27;
    private int v28;
    private float v29;

    public float getV0() {
        return v0;
    }

    public void setV0(float v0) {
        this.v0 = v0;
    }

    public float getV1() {
        return v1;
    }

    public void setV1(float v1) {
        this.v1 = v1;
    }

    public float getV2() {
        return v2;
    }

    public void setV2(float v2) {
        this.v2 = v2;
    }

    public float getV3() {
        return v3;
    }

    public void setV3(float v3) {
        this.v3 = v3;
    }

    public int getV4() {
        return v4;
    }

    public void setV4(int v4) {
        this.v4 = v4;
    }

    public float getV5() {
        return v5;
    }

    public void setV5(float v5) {
        this.v5 = v5;
    }

    public float getV6() {
        return v6;
    }

    public void setV6(float v6) {
        this.v6 = v6;
    }

    public int getV7() {
        return v7;
    }

    public void setV7(int v7) {
        this.v7 = v7;
    }

    public float getV8() {
        return v8;
    }

    public void setV8(float v8) {
        this.v8 = v8;
    }

    public float getV9() {
        return v9;
    }

    public void setV9(float v9) {
        this.v9 = v9;
    }

    public int getV10() {
        return v10;
    }

    public void setV10(int v10) {
        this.v10 = v10;
    }

    public float getV11() {
        return v11;
    }

    public void setV11(float v11) {
        this.v11 = v11;
    }

    public float getV12() {
        return v12;
    }

    public void setV12(float v12) {
        this.v12 = v12;
    }

    public int getV13() {
        return v13;
    }

    public void setV13(int v13) {
        this.v13 = v13;
    }

    public float getV14() {
        return v14;
    }

    public void setV14(float v14) {
        this.v14 = v14;
    }

    public float getV15() {
        return v15;
    }

    public void setV15(float v15) {
        this.v15 = v15;
    }

    public int getV16() {
        return v16;
    }

    public void setV16(int v16) {
        this.v16 = v16;
    }

    public float getV17() {
        return v17;
    }

    public void setV17(float v17) {
        this.v17 = v17;
    }

    public float getV18() {
        return v18;
    }

    public void setV18(float v18) {
        this.v18 = v18;
    }

    public int getV19() {
        return v19;
    }

    public void setV19(int v19) {
        this.v19 = v19;
    }

    public float getV20() {
        return v20;
    }

    public void setV20(float v20) {
        this.v20 = v20;
    }

    public float getV21() {
        return v21;
    }

    public void setV21(float v21) {
        this.v21 = v21;
    }

    public int getV22() {
        return v22;
    }

    public void setV22(int v22) {
        this.v22 = v22;
    }

    public float getV23() {
        return v23;
    }

    public void setV23(float v23) {
        this.v23 = v23;
    }

    public float getV24() {
        return v24;
    }

    public void setV24(float v24) {
        this.v24 = v24;
    }

    public int getV25() {
        return v25;
    }

    public void setV25(int v25) {
        this.v25 = v25;
    }

    public float getV26() {
        return v26;
    }

    public void setV26(float v26) {
        this.v26 = v26;
    }

    public float getV27() {
        return v27;
    }

    public void setV27(float v27) {
        this.v27 = v27;
    }

    public int getV28() {
        return v28;
    }

    public void setV28(int v28) {
        this.v28 = v28;
    }

    public float getV29() {
        return v29;
    }

    public void setV29(float v29) {
        this.v29 = v29;
    }

    public int getV30() {
        return v30;
    }

    public void setV30(int v30) {
        this.v30 = v30;
    }

    private int v30;

}
