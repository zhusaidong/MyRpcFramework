package com.zhusaidong.rpc.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class PropertiesUtil{
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    
    /**
     * 加载classpath中的properties
     *
     * @param classpathFile classpath
     *
     * @return properties
     */
    private static Properties loadPropertiesFromClasspath(String classpathFile){
        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(
                new File(PropertiesUtil.class.getResource(classpathFile).toURI()))){
            properties.load(fileInputStream);
        }
        catch(IOException | URISyntaxException exception){
            logger.error(exception.getMessage());
        }
        
        return properties;
    }
    
    /**
     * 获取服务端的properties
     *
     * @return properties
     */
    public static Properties getServer(){
        return loadPropertiesFromClasspath("/server.properties");
    }
    
    /**
     * 获取客户端的properties
     *
     * @return properties
     */
    public static Properties getClient(){
        return loadPropertiesFromClasspath("/client.properties");
    }
}
