package com.zhusaidong.rpc.server;

import com.zhusaidong.rpc.core.PropertyConst;
import com.zhusaidong.rpc.core.zookeeper.ZooKeeperUtil;
import com.zhusaidong.rpc.server.properties.RpcServerProperties;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 注册中心
 *
 * @author zhusaidong
 */
@Component
@SuppressWarnings("unused")
public class RegisterCenter implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(RegisterCenter.class);
    public static final ConcurrentMap<String, Object> SERVICES = new ConcurrentHashMap<>();
    private ZooKeeper zooKeeper;
    
    @Autowired
    private RpcServerProperties rpcServerProperties;
    
    public ZooKeeper connect(){
        Integer zookeeperSessionTimeout = rpcServerProperties.getZookeeperSessionTimeout();
        logger.debug("连接到注册中心");
        return ZooKeeperUtil.connect(rpcServerProperties.getZookeeperUrl(), zookeeperSessionTimeout);
    }
    
    @Override
    public void afterPropertiesSet(){
        zooKeeper = connect();
    }
    
    public void close() throws InterruptedException{
        zooKeeper.close();
    }
    
    public void save(Object object) throws KeeperException, InterruptedException{
        if(zooKeeper.exists(PropertyConst.ROOT_PATH, false) == null){
            zooKeeper.create(PropertyConst.ROOT_PATH, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        
        Type[] genericInterfaces = object.getClass().getGenericInterfaces();
        for(Type genericInterface : genericInterfaces){
            String serverPath = PropertyConst.ROOT_PATH + "/" + genericInterface.getTypeName();
            if(zooKeeper.exists(serverPath, false) == null){
                zooKeeper.create(serverPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //一个服务可以有多个节点，节点都是临时节点，随session消失而消失
            zooKeeper.create(serverPath + "/" + getServerUrl(), null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.debug("服务：{}, 已注册到注册中心", serverPath);
        }
    }
    
    private String getServerUrl(){
        String hostAddress;
        try{
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException e){
            hostAddress = InetAddress.getLoopbackAddress().getHostAddress();
        }
        
        return hostAddress + ":" + rpcServerProperties.getServerPort();
    }
}
