package com.farpower.iot;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 
 * 
 * @author luoyh(Roy) - Dec 13, 2018
 * @version 1.0
 * @since 1.8
 */
public class Z00 {
    
    public static void main(String[] args) throws Exception {
        System.err.println(Integer.MAX_VALUE);
        BitSet bs = new BitSet(Integer.MAX_VALUE);
        Field field = BitSet.class.getDeclaredField("words");
        field.setAccessible(true);
        long[] words = (long[]) field.get(bs);
        System.out.println(words.length);
        System.out.println(bs.size());
//        Path path = Paths.get("e:/number.txt");
        FileWriter w = new FileWriter("e:/number.txt", true);
        Random rand = new Random();
        IntStream.range(0, Integer.MAX_VALUE).forEach(id -> {
            try {
                //Files.write(path, (id + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                w.write(rand.nextInt(Integer.MAX_VALUE) + System.lineSeparator());
                if (id % 10000000 == 0) {
                    w.flush();
                    System.out.println(id);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        w.flush();
        w.close();
    }


}
