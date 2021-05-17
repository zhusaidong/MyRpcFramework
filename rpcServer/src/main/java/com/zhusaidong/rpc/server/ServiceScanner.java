package com.zhusaidong.rpc.server;

import com.zhusaidong.rpc.core.annotation.RpcService;
import com.zhusaidong.rpc.server.properties.RpcServerProperties;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 服务扫描
 *
 * @author zhusaidong
 */
@Component
public class ServiceScanner implements InitializingBean, ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(ServiceScanner.class);
    
    @Autowired
    private RegisterCenter registerCenter;
    @Autowired
    private RpcServerProperties rpcServerProperties;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class);
        beans.forEach((clazz, bean) -> {
            Type[] genericInterfaces = bean.getClass().getGenericInterfaces();
            for(Type genericInterface : genericInterfaces){
                logger.debug("扫描到服务：{}", genericInterface.getTypeName());
                RegisterCenter.SERVICES.put(genericInterface.getTypeName(), bean);
                try{
                    registerCenter.save(bean);
                }
                catch(KeeperException | InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        beans.clear();
    }
    
    @Override
    public void afterPropertiesSet(){
        Integer port = rpcServerProperties.getServerPort();
        
        logger.debug("rpcServer已启动，端口: {}", port);
        RpcServer.start(port);
    }
}
