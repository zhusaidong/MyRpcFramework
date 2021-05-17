package com.zhusaidong.rpc.core.util;

import com.zhusaidong.rpc.core.netty.ClientContext;
import com.zhusaidong.rpc.core.netty.InitChannel;
import com.zhusaidong.rpc.core.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhusaidong
 */
public class ServerUtil{
    public static void create(int port, ServerHandler serverHandler){
        EventLoopGroup boos   = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        
        ServerBootstrap bootstrap = new ServerBootstrap().group(boos, worker).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new InitChannel(serverHandler));
        
        try{
            ChannelFuture sync = bootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally{
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    
    /**
     * test server
     */
    public static void main(String[] args){
        ServerUtil.create(8882, new ServerHandler(){
            @Override
            public void read(Object msg){
                System.out.println(msg);
                ClientContext.getClientChannelContext().writeAndFlush("server:ok");
            }
        });
    }
}
