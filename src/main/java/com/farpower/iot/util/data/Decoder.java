package com.farpower.iot.util.data;

/**
 * <table cellspacing="1" cellpadding="5" border="0">
<tbody><tr align="center" bgcolor="#cccccc">
<th width="60">Level</th>
<th width="150">Operator</th>
<th width="175">Description</th>
<th width="125">Associativity</th>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>16</b></td>
<td>
<tt>[]</tt><br>
<tt> .</tt><br>
<tt>()</tt>
</td>
<td>
access array element<br>
access object member<br>
parentheses
</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>15</b></td>
<td>
<tt>++</tt><br>
<tt>--</tt>
</td>
<td>
unary post-increment<br>
unary post-decrement
</td>
<td>not associative</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>14</b></td>
<td>
<tt>++</tt><br>
<tt>--</tt><br>
<tt>+</tt><br>
<tt>-</tt><br>
<tt>!</tt><br>
<tt>~</tt>
</td>
<td>
unary pre-increment<br>
unary pre-decrement<br>
unary plus<br>
unary minus<br>
unary logical NOT<br>
unary bitwise NOT
</td>
<td>right to left</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>13</b></td>
<td>
<tt>()</tt><br>
<tt>new</tt>
</td>
<td>
cast<br>
object creation
</td>
<td>right to left</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>12</b></td>
<td>
<tt>* / %</tt>
</td>
<td>
multiplicative
</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>11</b></td>
<td>
<tt>+ -</tt><br>
<tt>+</tt>
</td>
<td>
additive<br>
string concatenation
</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>10</b></td>
<td>
<tt>&lt;&lt; &gt;&gt;</tt><br>
<tt>&gt;&gt;&gt;</tt>
</td>
<td>
shift
</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>9</b></td>
<td>
<tt>&lt;  &lt;=</tt><br>
<tt>&gt;  &gt;=</tt><br>
<tt>instanceof</tt>
</td>
<td>
relational
</td>
<td>not associative</td>
</tr>


<tr align="center" bgcolor="#ebebeb">
<td><b>8</b></td>
<td>
<tt>==</tt><br>
<tt>!=</tt>
</td>
<td>
equality
</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>7</b></td>
<td><tt>&amp;</tt></td>
<td>bitwise AND</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>6</b></td>
<td><tt>^</tt></td>
<td>bitwise XOR</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>5</b></td>
<td><tt>|</tt></td>
<td>bitwise OR</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>4</b></td>
<td><tt>&amp;&amp;</tt></td>
<td>logical AND</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>3</b></td>
<td><tt>||</tt></td>
<td>logical OR</td>
<td>left to right</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>2</b></td>
<td><tt>?:</tt></td>
<td>ternary</td>
<td>right to left</td>
</tr>

<tr align="center" bgcolor="#ebebeb">
<td><b>1</b></td>
<td>
<tt> &nbsp;=&nbsp;&nbsp; +=&nbsp;&nbsp; -=</tt><br>
<tt>      *=&nbsp;&nbsp; /=&nbsp;&nbsp; %=</tt><br>
<tt>      &amp;=&nbsp;&nbsp; ^=&nbsp;&nbsp; |=</tt><br>
<tt>     &lt;&lt;=      &nbsp;&gt;&gt;=           &gt;&gt;&gt;=</tt>

</td>
<td align="">
assignment
</td>
<td>right to left</td>
</tr>
</tbody></table> <br>
 * See also <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html">Java operator precedence</a>  
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
    
    
    /**
     * Return a array: [year, month, day, week, hour, minute, second]
     * @param b
     * @return 
     * @author luoyh(Roy) - Mar 9, 2019
     */
    public static int[] a1(byte[] b) {
        int[] r = new int[7];
        r[0] = (b[5] >>> 4 & 0xf) * 10 + (b[5] & 0xf);
        r[1] = (b[4] >>> 4 & 0x1) * 10 + (b[4] & 0xf);
        r[2] = (b[3] >>> 4 & 0xf) * 10 + (b[3] & 0xf);
        r[3] = b[4] >>> 5 & 0b111;
        r[4] = (b[2] >>> 4 & 0xf) * 10 + (b[2] & 0xf);
        r[5] = (b[1] >>> 4 & 0xf) * 10 + (b[1] & 0xf);
        r[5] = (b[0] >>> 4 & 0xf) * 10 + (b[0] & 0xf);
        return r;
    }
    
    public static double a2(byte[] b) {
        int value = (b[1] & 0xf) * 100 + (b[0] >>> 4 & 0xf) * 10 + (b[0] & 0xf);
        return val(value * g(b[1] >>> 5 & 0b111), negative(b[1], 4));
    }
    
    public static int a3(byte[] b) {
        return val((b[3] & 0xf) * 1000000 + 
                (b[2] >>> 4 & 0xf) * 100000 +
                (b[2] & 0xf) * 10000 +
                (b[1] >>> 4 & 0xf) * 1000 +
                (b[1] & 0xf) * 100 +
                (b[0] >>> 4 & 0xf) * 10 +
                (b[0] & 0xf), negative(b[3], 4));
    }
    
    public static int a4(byte[] b) {
        return val((b[0] >>> 4 & 0b111) * 10 + (b[0] & 0xf), negative(b[0], 7));
    }
    
    public static double a5(byte[] b) {
        return val((b[1] >>> 4 & 0b111) * 100 + 
                    (b[1] & 0xf) * 10 + 
                    (b[0] >>> 4 & 0xf) +
                    (b[0] & 0xf) * 0.1d, negative(b[1], 7));
    }
    
    public static double a6(byte[] b) {
        return val((b[1] >>> 4 & 0b111) * 10 + 
                    (b[1] & 0xf) + 
                    (b[0] >>> 4 & 0xf) * 0.1d +
                    (b[0] & 0xf) * 0.01d, negative(b[1], 7));
    }

    public static double a7(byte[] b) {
        return (b[1] >>> 4 & 0xf) * 100 + 
                (b[1] & 0xf) * 10 + 
                (b[0] >>> 4 & 0xf) +
                (b[0] & 0xf) * 0.1d;
    }
    
    public static int a8(byte[] b) {
        return (b[1] >>> 4 & 0xf) * 1000 + 
                (b[1] & 0xf) * 100 + 
                (b[0] >>> 4 & 0xf) * 10 +
                (b[0] & 0xf);
    }
    
    public static double a9(byte[] b) {
        return val((b[2] >>> 4 & 0b111) * 10 + 
                    (b[2] & 0xf) + 
                    (b[1] >>> 4 & 0xf) * 0.1d + 
                    (b[1] & 0xf) * 0.01d + 
                    (b[0] >>> 4 & 0xf) * 0.001d + 
                    (b[0] & 0xf) * 0.0001d, negative(b[2], 7));
    }
    
    public static int a10(byte[] b) {
        return (b[2] >>> 4 & 0xf) * 100000 +
                (b[2] & 0xf) * 10000 +
                (b[1] >>> 4 & 0xf) * 1000 +
                (b[1] & 0xf) * 100 + 
                (b[0] >>> 4 & 0xf) * 10 +
                (b[0] & 0xf);
    }
    
    public static double a11(byte[] b) {
        return (b[3] >>> 4 & 0xf) * 100000 + 
                (b[3] & 0xf) * 10000 + 
                (b[2] >>> 4 & 0xf) * 1000 +
                (b[2] & 0xf) * 100 + 
                (b[1] >>> 4 & 0xf) * 10 +
                (b[1] & 0xf) + 
                (b[0] >>> 4 & 0xf) * 0.1d +
                (b[0] & 0xf) * 0.01d;
    }
    
    public static long a12(byte[] b) {
        return (b[5] >>> 4 & 0xf) * 100000000000l +
                (b[5] & 0xf)       * 10000000000l +
                (b[4] >>> 4 & 0xf) * 1000000000 +
                (b[4] & 0xf)       * 100000000 +
                (b[3] >>> 4 & 0xf) * 10000000 +
                (b[3] & 0xf)       * 1000000 +
                (b[2] >>> 4 & 0xf) * 100000 +
                (b[2] & 0xf)       * 10000 +
                (b[1] >>> 4 & 0xf) * 1000 +
                (b[1] & 0xf)       * 100 + 
                (b[0] >>> 4 & 0xf) * 10 +
                (b[0] & 0xf);
    }
    
    public static double a13(byte[] b) {
        return (b[3] >>> 4 & 0xf) * 1000 + 
                (b[3] & 0xf)       * 100 +
                (b[2] >>> 4 & 0xf) * 10 +
                (b[2] & 0xf)       * 1 +
                (b[1] >>> 4 & 0xf) * 0.1d +
                (b[1] & 0xf)       * 0.01d +
                (b[0] >>> 4 & 0xf) * 0.001d + 
                (b[0] & 0xf)       * 0.0001d;
    }

    public static double a14(byte[] b) {
        return (b[4] >>> 4 & 0xf) * 100000 + 
                (b[4] & 0xf)       * 10000 + 
                (b[3] >>> 4 & 0xf) * 1000 + 
                (b[3] & 0xf)       * 100 +
                (b[2] >>> 4 & 0xf) * 10 +
                (b[2] & 0xf)       * 1 +
                (b[1] >>> 4 & 0xf) * 0.1d +
                (b[1] & 0xf)       * 0.01d +
                (b[0] >>> 4 & 0xf) * 0.001d + 
                (b[0] & 0xf)       * 0.0001d;
    }
    
    public static int[] a15(byte[] b) {
        return new int[] {
                (b[4] >>> 4 & 0xf) * 10 + (b[4] & 0xf), // year
                (b[3] >>> 4 & 0xf) * 10 + (b[3] & 0xf), // month
                (b[2] >>> 4 & 0xf) * 10 + (b[2] & 0xf), // day
                (b[1] >>> 4 & 0xf) * 10 + (b[1] & 0xf), // hour
                (b[0] >>> 4 & 0xf) * 10 + (b[0] & 0xf), // minute
        };
    }
    
    public static int[] a16(byte[] b) {
        return new int[] {
                (b[3] >>> 4 & 0xf) * 10 + (b[3] & 0xf), // day
                (b[2] >>> 4 & 0xf) * 10 + (b[2] & 0xf), // hour
                (b[1] >>> 4 & 0xf) * 10 + (b[1] & 0xf), // minute
                (b[0] >>> 4 & 0xf) * 10 + (b[0] & 0xf), // second
        };
    }
    
    /**
     * Return an array: [hour || year, minute || month]
     * @param b
     * @return
     * @author luoyh(Roy) - Mar 10, 2019
     */
    public static int[] a17(byte[] b) {
        return new int[] {
                (b[1] >>> 4 & 0xf) * 10 + (b[1] & 0xf), // hour or year
                (b[0] >>> 4 & 0xf) * 10 + (b[0] & 0xf), // minute or month
        };
    }
    
    public static int[] a18(byte[] b) {
        return new int[] {
                (b[2] >>> 4 & 0xf) * 10 + (b[2] & 0xf), // day
                (b[1] >>> 4 & 0xf) * 10 + (b[1] & 0xf), // hour
                (b[0] >>> 4 & 0xf) * 10 + (b[0] & 0xf), // minute
        };
    }
    
    /**
     * Return an array: [hour, minute]
     * @param b
     * @return
     * @author luoyh(Roy) - Mar 10, 2019
     */
    public static int[] a19(byte[] b) {
        return a17(b);
    }
    
    /**
     * Return an array: [year, month, day]
     * @param b
     * @return
     * @author luoyh(Roy) - Mar 10, 2019
     */
    public static int[] a20(byte[] b) {
        return a18(b);
    }
    
    /**
     * Return an array: [year, month]
     * @param b
     * @return
     * @author luoyh(Roy) - Mar 10, 2019
     */
    public static int[] a21(byte[] b) {
        return a17(b);
    }
    
    public static double a22(byte[] b) {
        return (b[0] >>> 4 & 0xf) + (b[0] & 0xf) * 0.1d;
    }
    
    public static double a23(byte[] b) {
        return (b[2] >>> 4 & 0xf) * 10 +
                (b[2] & 0xf) +
                (b[1] >>> 4 & 0xf) * 0.1d +
                (b[1] & 0xf)       * 0.01d +
                (b[0] >>> 4 & 0xf) * 0.001d +
                (b[0] & 0xf)       * 0.0001d;
    }
    
    /**
     * Return an array: [day, hour]
     * @param b
     * @return
     * @author luoyh(Roy) - Mar 10, 2019
     */
    public static int[] a24(byte[] b) {
        return a17(b);
    }
    
    
    public static double a25(byte[] b) {
        return val((b[2] >>> 4 & 0x7) * 100 +
                    (b[2] & 0xf)       * 10 +
                    (b[1] >>> 4 & 0xf) + 
                    (b[1] & 0xf)       * 0.1d + 
                    (b[0] >>> 4 & 0xf) * 0.01d + 
                    (b[0] & 0xf)       * 0.001d, negative(b[2], 7));
    }
    
    public static double a26(byte[] b) {
        return (b[1] >>> 4 & 0xf) + 
                (b[1] & 0xf) * 0.1d +
                (b[0] >>> 4 & 0xf) * 0.01d + 
                (b[0] & 0xf) * 0.001d;
    }


    public static int a27(byte[] b) {
        return (b[3] >>> 4 & 0xf) * 10000000 +
                (b[3] & 0xf)       * 1000000 +
                (b[2] >>> 4 & 0xf) * 100000 +
                (b[2] & 0xf)       * 10000 +
                (b[1] >>> 4 & 0xf) * 1000 +
                (b[1] & 0xf)       * 100 + 
                (b[0] >>> 4 & 0xf) * 10 +
                (b[0] & 0xf);
    }
    
    /**
     * F的定义：F=0，表示东经或北纬，F=1，表示西经或南纬。<br>
     * Return an array: [F, F.value, minute, second, millisecond]
     * @param b
     * @return
     * @author luoyh(Roy) - Mar 10, 2019
     */
    public static int[] a28(byte[] b) {
        System.currentTimeMillis();
        return new int[] {
                b[4] >>> 7 & 0x1,
                (b[4] & 0xf) * 100 + (b[3] >>> 4 & 0xf) * 10 + (b[3] & 0xf),
                (b[2] >>> 4 & 0xf) * 10 + (b[2] & 0xf),
                (b[1] >>> 4 & 0xf) * 10 + (b[1] & 0xf),
                (b[0] >>> 4 & 0xf) * 100 + (b[0] & 0xf) * 10
        };
    }
    
    
    private static double val(double value, boolean negative) {
        return negative ? -value : value;
    }
    
    private static int val(int value, boolean negative) {
        return negative ? -value : value;
    }
    
    private static boolean negative(byte b, int m) {
        return (b >>> m & 0x1) == 1;
    }
    
    private static double g(int value) {
        return Math.pow(10, 4 - value);
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
