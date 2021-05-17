package com.zhusaidong.rpc.client;

import com.zhusaidong.rpc.core.FunSerializable;
import com.zhusaidong.rpc.core.annotation.RpcReference;
import com.zhusaidong.rpc.core.netty.ClientContext;
import com.zhusaidong.rpc.core.netty.handler.ClientHandler;
import com.zhusaidong.rpc.core.util.ClientUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhusaidong
 */
@Sharable
public class ClientInvocationHandler<T> extends ClientHandler implements InvocationHandler{
    private static final Logger logger = LoggerFactory.getLogger(ClientInvocationHandler.class);
    
    private final Class<T> service;
    private final RpcReference rpcReference;
    private final String url;
    private static Object msg = null;
    private static ChannelPromise channelPromise = null;
    
    private final ExecutorService executorService = new ThreadPoolExecutor(1, 1, 10, TimeUnit.DAYS,
            new LinkedBlockingQueue<>(), (ThreadFactory)Thread::new);
    
    public ClientInvocationHandler(String url, Class<T> service, RpcReference rpcReference){
        this.url = url;
        this.service = service;
        this.rpcReference = rpcReference;
        logger.debug("服务：{}, 调用地址：{}", service.getCanonicalName(), url);
    }
    
    public void connect(){
        //启动客户端连接会阻塞的当前线程
        executorService.submit(() -> ClientUtil.create(url, this));
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InterruptedException{
        ChannelHandlerContext clientChannelContext = ClientContext.getClientChannelContext();
        
        //spring boot启动的http-nio的主线程
        if(clientChannelContext != null){
            //写入调用的数据：方法名，参数类型，具体参数
            FunSerializable funSerializable = new FunSerializable()
                    .setFullClassName(service.getCanonicalName())
                    .setMethod(method.getName())
                    .setParameterTypes(method.getParameterTypes())
                    .setArgs(args)
                    .setVersion(rpcReference.version());
            logger.debug("发起服务：{} 的调用", service.getCanonicalName());
            logger.debug(String.valueOf(funSerializable));
            
            ChannelFuture channelFuture = clientChannelContext.writeAndFlush(funSerializable);
            channelPromise = channelFuture.channel().newPromise();
            channelPromise.await();
        }
        logger.debug("接收到消息：{}", msg);
        
        return msg;
    }
    
    @Override
    public void read(Object object){
        //netty的子线程
        msg = object;
        if(channelPromise != null){
            channelPromise.setSuccess();
        }
    }
}
