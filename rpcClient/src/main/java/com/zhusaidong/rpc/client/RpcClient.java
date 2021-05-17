package com.zhusaidong.rpc.client;

import com.zhusaidong.rpc.client.properties.RpcClientProperties;
import com.zhusaidong.rpc.core.PropertyConst;
import com.zhusaidong.rpc.core.annotation.RpcReference;
import com.zhusaidong.rpc.core.zookeeper.ZooKeeperUtil;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhusaidong
 */
public class RpcClient{
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    
    private static volatile ZooKeeper zooKeeper = null;
    
    /**
     * 连接zookeeper
     */
    public static void connect(RpcClientProperties rpcClientProperties){
        if(zooKeeper == null){
            Integer sessionTimeout = rpcClientProperties.getZookeeperSessionTimeout();
            logger.debug("连接到注册中心");
            zooKeeper = ZooKeeperUtil.connect(rpcClientProperties.getZookeeperUrl(), sessionTimeout);
        }
    }
    
    /**
     * 客户端
     *
     * @param service the cn.zhusaidong.rpc.service
     */
    @SuppressWarnings("unchecked")
    public static <T> T rpcClient(Class<T> service, RpcReference rpcReference){
        List<String> children = new ArrayList<>();
        try{
            if(zooKeeper != null){
                children = zooKeeper.getChildren(PropertyConst.ROOT_PATH + "/" + service.getCanonicalName(), null);
            }
        }
        catch(KeeperException | InterruptedException e){
            e.printStackTrace();
        }
        
        if(children.isEmpty()){
            logger.error("无服务可用!");
            return null;
        }
        
        Collections.shuffle(children);
        
        ClientInvocationHandler<T> clientHandler = new ClientInvocationHandler<>(children.get(0), service,
                rpcReference);
        clientHandler.connect();
        
        //动态代理
        return (T)Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] {service}, clientHandler);
    }
    
    /**
     * 注解
     */
    public static <T> T annotation(T object){
        Field[] fields = object.getClass().getDeclaredFields();
        try{
            for(Field field : fields){
                RpcReference rpcReference = field.getAnnotation(RpcReference.class);
                Class<?>     type         = field.getType();
                if(rpcReference != null && type.isInterface()){
                    logger.debug("扫描到服务调用：{}", type.getCanonicalName());
                    boolean accessible = field.isAccessible();
                    if(!accessible){
                        field.setAccessible(true);
                    }
                    field.set(object, rpcClient(type, rpcReference));
                    if(!accessible){
                        field.setAccessible(false);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return object;
    }
}
