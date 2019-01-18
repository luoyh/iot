package com.farpower.iot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 10, 2019
 * @version 1.0
 * @since 1.8
 */
public class F1 {
    
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("e:/notin.txt"));
        System.out.println(br);
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        br = null;
    }

}
