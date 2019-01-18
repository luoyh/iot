package com.farpower.iot.t;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author luoyh(Roy) - Jan 10, 2019
 * @version 1.0
 * @since 1.8
 */
public class DataService {
    
    public String area(Param param) {
        if (StringUtils.isBlank(param.getGroup())) {
            
        }
        if (param.getGroup().equals("园区")) {
            return "999.12";
        }
        if (!StringUtils.isBlank(param.getTag())) {
            return "123.44";
        }
        
        return "18.31";
    }
    
    

}

