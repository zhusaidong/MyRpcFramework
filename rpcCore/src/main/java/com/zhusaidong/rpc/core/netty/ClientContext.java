package com.zhusaidong.rpc.core.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class ClientContext{
    private static ChannelHandlerContext CLIENT_CHANNEL_CONTEXT;
    
    public static void removeClientChannelContext(){
        CLIENT_CHANNEL_CONTEXT = null;
    }
    
    public static void setClientChannelContext(ChannelHandlerContext context){
        CLIENT_CHANNEL_CONTEXT = context;
    }
    
    public static ChannelHandlerContext getClientChannelContext(){
        return CLIENT_CHANNEL_CONTEXT;
    }
}
