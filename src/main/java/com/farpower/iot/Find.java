package com.farpower.iot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.BitSet;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 10, 2019
 * @version 1.0
 * @since 1.8
 */
public class Find {
    
    public static void main(String[] args) throws Exception {
        BitSet bs = new BitSet(Integer.MAX_VALUE);
        BufferedReader br = new BufferedReader(new FileReader("e:/number.txt"));
        System.out.println(br);
        String line = null;
        while ((line = br.readLine()) != null) {
            bs.set(Integer.parseInt(line));
        }
        br.close();
        br = null;
        BufferedWriter w = new BufferedWriter(new FileWriter("e:/notin.txt"));
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            if (!bs.get(i)) {
                w.write(i + System.lineSeparator());
            }
            if (i % 10000000 == 0) {
                w.flush();
                System.out.println(i);
            }
        }
        w.flush();
        w.close();
        w = null;
        
        System.out.println(bs.size());
        Field field = BitSet.class.getDeclaredField("words");
        field.setAccessible(true);
        long[] words = (long[]) field.get(bs);
        System.out.println(words.length);
        System.out.println(bs.size());
        TimeUnit.SECONDS.sleep(3000);
    }

}
