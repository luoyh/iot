package com.farpower.iot.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.farpower.iot.client.model.Message;
import com.farpower.iot.client.model.Session;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 9, 2019
 * @version 1.0
 * @since 1.8
 */
public enum Cache {
    
    INSTANCE;
    
    ConcurrentMap<Integer, Session> sessions = new ConcurrentHashMap<>();
    
    
    public void put(Integer key, Session value) {
        sessions.put(key, value);
    }
    
    public Session get(Integer key) {
        return sessions.get(key);
    }
    
    public void confirm(Message message) {
        
    }
    
    

}
