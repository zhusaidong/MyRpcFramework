package com.zhusaidong.rpc.client;

import com.zhusaidong.rpc.client.properties.RpcClientProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author zhusaidong
 */
@Component
public class ClientScanner implements InitializingBean, ApplicationContextAware{
    private ApplicationContext applicationContext;
    
    @Autowired
    private RpcClientProperties rpcClientProperties;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
    
    @Override
    public void afterPropertiesSet(){
        RpcClient.connect(rpcClientProperties);
        Collection<Object> collection = applicationContext.getBeansWithAnnotation(Component.class).values();
        collection.forEach(RpcClient::annotation);
    }
}
