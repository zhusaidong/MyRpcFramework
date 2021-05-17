package com.zhusaidong.rpc.core.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class ServerContext{
    private static final ThreadLocal<ChannelHandlerContext> CLIENT_CHANNEL_CONTEXT = new ThreadLocal<>();
    
    public static void removeClientChannelContext(){
        CLIENT_CHANNEL_CONTEXT.remove();
    }
    
    public static void setClientChannelContext(ChannelHandlerContext context){
        CLIENT_CHANNEL_CONTEXT.set(context);
    }
    
    public static ChannelHandlerContext getClientChannelContext(){
        return CLIENT_CHANNEL_CONTEXT.get();
    }
}
