package com.zhusaidong.rpc.server.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhusaidong
 */
@Component
@SuppressWarnings("unused")
public class RpcServerProperties{
    @Value("${server.port}")
    private Integer serverPort;
    @Value("${myrpc.server.zookeeper-url:127.0.0.1:2181}")
    private String zookeeperUrl;
    @Value("${myrpc.server.zookeeper-session-timeout:5000}")
    private Integer zookeeperSessionTimeout;
    
    public Integer getServerPort(){
        return serverPort;
    }
    
    public RpcServerProperties setServerPort(Integer serverPort){
        this.serverPort = serverPort;
        return this;
    }
    
    public String getZookeeperUrl(){
        return zookeeperUrl;
    }
    
    public RpcServerProperties setZookeeperUrl(String zookeeperUrl){
        this.zookeeperUrl = zookeeperUrl;
        return this;
    }
    
    public Integer getZookeeperSessionTimeout(){
        return zookeeperSessionTimeout;
    }
    
    public RpcServerProperties setZookeeperSessionTimeout(Integer zookeeperSessionTimeout){
        this.zookeeperSessionTimeout = zookeeperSessionTimeout;
        return this;
    }
}
