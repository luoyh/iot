package com.farpower.iot.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 10, 2019
 * @version 1.0
 * @since 1.8
 */
public class Client {
    
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 6001);
    }

}
