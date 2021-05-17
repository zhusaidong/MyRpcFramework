package com.zhusaidong.rpc.core;

/**
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class PropertyConst{
    public static final String ROOT_PATH = "/services";
    
    public static final String ZOOKEEPER_URL = "zookeeper.url";
    public static final String ZOOKEEPER_SESSION_TIMEOUT = "zookeeper.session-timeout";
    public static final String SERVER_PORT = "rpc-server.port";
    
    public static class Default{
        public static final String ZOOKEEPER_URL = "127.0.0.1:2181";
        public static final String ZOOKEEPER_SESSION_TIMEOUT = "5000";
        public static final String SERVER_PORT = "8888";
    }
}
