package com.zhusaidong.rpc.core.netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class ServerContexts{
    private static final CopyOnWriteArrayList<ChannelHandlerContext> CHANNEL_CONTEXT_LIST = new CopyOnWriteArrayList<>();
    
    public static void removeClientChannelContext(ChannelHandlerContext context){
        CHANNEL_CONTEXT_LIST.remove(context);
    }
    
    public static void addClientChannelContext(ChannelHandlerContext context){
        CHANNEL_CONTEXT_LIST.add(context);
    }
    
    public static List<ChannelHandlerContext> getClientChannelContext(){
        return CHANNEL_CONTEXT_LIST;
    }
}
