package com.vooda.frame.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.qiniu.util.Auth;

/**
 * 读取properties文件的工具类
 */
public class PropertiesUtil {
    private static Properties p = new Properties();
    private static Map<String, String> cache = new HashMap<String, String>();
    
    static {
        try {
            p.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(
                    "qiniu.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getValue(String key) {
        if(StringUtils.isEmpty(cache.get(key)))
            cache.put(key, p.getProperty(key));
        
        return cache.get(key);
    }
    
    public static void main(String[] args){
        Auth auth = Auth.create(PropertiesUtil.getValue("ACCESS_KEY"), PropertiesUtil.getValue("SECRET_KEY"));
        String token = auth.uploadToken(PropertiesUtil.getValue("BUCKET_NAME"));
        System.out.println(token);
    }
}