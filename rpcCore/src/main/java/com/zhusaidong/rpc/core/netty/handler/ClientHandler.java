package com.zhusaidong.rpc.core.netty.handler;

import com.zhusaidong.rpc.core.netty.ClientContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhusaidong
 */
public abstract class ClientHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    
    private final ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(1,
            (ThreadFactory)Thread::new);
    
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ClientContext.setClientChannelContext(ctx);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg){
        if("hi".equals(msg)){
            logger.debug("已经连接到服务，并收到消息。");
        }else{
            read(msg);
        }
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        ClientContext.removeClientChannelContext();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        logger.error(cause.getMessage());
        ///重试机制
        /*
        scheduled.scheduleAtFixedRate(() -> {
            logger.debug("重试机制:" + Thread.currentThread().getName());
        }, 1, 2, TimeUnit.SECONDS);
        */
        ctx.close();
    }
    
    /**
     * 读取消息
     *
     * @param msg 消息
     */
    public abstract void read(Object msg);
}
