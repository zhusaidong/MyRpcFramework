package com.zhusaidong.rpc.server;

import com.zhusaidong.rpc.core.FunSerializable;
import com.zhusaidong.rpc.core.annotation.RpcService;
import com.zhusaidong.rpc.core.netty.ServerContext;
import com.zhusaidong.rpc.core.netty.handler.ServerHandler;
import com.zhusaidong.rpc.core.util.ServerUtil;
import io.netty.channel.ChannelHandler.Sharable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * rpc
 *
 * @author zhusaidong
 */
@Sharable
public class RpcServer extends ServerHandler{
    /**
     * 服务端
     *
     * @param port the port
     */
    public static void start(int port){
        ServerUtil.create(port, new RpcServer());
    }
    
    @Override
    public void read(Object msg){
        try{
            //获取调用的数据：方法名，参数类型，具体参数
            FunSerializable clientFun = (FunSerializable)msg;
            
            Object service = RegisterCenter.SERVICES.get(clientFun.getFullClassName());
            if(service == null){
                return;
            }
            
            //反射调用对应方法
            Object   invoke            = null;
            Class<?> aClass            = service.getClass();
            Type[]   genericInterfaces = aClass.getGenericInterfaces();
            for(Type genericInterface : genericInterfaces){
                if(clientFun.getFullClassName().equals(genericInterface.getTypeName())){
                    RpcService rpcService = aClass.getAnnotation(RpcService.class);
                    if(rpcService != null && rpcService.version() != clientFun.getVersion()){
                        continue;
                    }
                    
                    Method method = aClass.getMethod(clientFun.getMethod(), clientFun.getParameterTypes());
                    invoke = method.invoke(service, clientFun.getArgs());
                    break;
                }
            }
            
            ServerContext.getClientChannelContext().writeAndFlush(invoke);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
