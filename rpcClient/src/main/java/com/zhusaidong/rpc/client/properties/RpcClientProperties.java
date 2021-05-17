package com.zhusaidong.rpc.client.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhusaidong
 */
@Component
@SuppressWarnings("unused")
public class RpcClientProperties{
    @Value("${myrpc.server.zookeeper-url:127.0.0.1:2181}")
    private String zookeeperUrl;
    @Value("${myrpc.server.zookeeper-session-timeout:5000}")
    private Integer zookeeperSessionTimeout;
    
    public String getZookeeperUrl(){
        return zookeeperUrl;
    }
    
    public RpcClientProperties setZookeeperUrl(String zookeeperUrl){
        this.zookeeperUrl = zookeeperUrl;
        return this;
    }
    
    public Integer getZookeeperSessionTimeout(){
        return zookeeperSessionTimeout;
    }
    
    public RpcClientProperties setZookeeperSessionTimeout(Integer zookeeperSessionTimeout){
        this.zookeeperSessionTimeout = zookeeperSessionTimeout;
        return this;
    }
}
