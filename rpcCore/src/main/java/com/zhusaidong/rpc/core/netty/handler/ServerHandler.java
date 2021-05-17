package com.zhusaidong.rpc.core.netty.handler;

import com.zhusaidong.rpc.core.netty.ServerContext;
import com.zhusaidong.rpc.core.netty.ServerContexts;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhusaidong
 */
public abstract class ServerHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        //保存所有客户端context
        ServerContexts.addClientChannelContext(ctx);
        //当前线程保存当前客户端context
        ServerContext.setClientChannelContext(ctx);
        
        ctx.writeAndFlush("hi");
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        read(msg);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        ServerContexts.removeClientChannelContext(ctx);
        ServerContext.removeClientChannelContext();
    }
    
    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        logger.error(cause.getMessage());
        ctx.close();
    }
    
    /**
     * 读取消息
     *
     * @param msg 消息
     */
    public abstract void read(Object msg);
}
