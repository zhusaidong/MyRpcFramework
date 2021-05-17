package com.zhusaidong.rpc.core.util;

import com.zhusaidong.rpc.core.netty.ClientContext;
import com.zhusaidong.rpc.core.netty.InitChannel;
import com.zhusaidong.rpc.core.netty.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhusaidong
 */
public class ClientUtil{
    public static void create(String url, ClientHandler clientHandler){
        String host = url.split(":")[0];
        String port = url.split(":")[1];
        create(host, Integer.parseInt(port), clientHandler);
    }
    
    public static void create(String host, int port, ClientHandler clientHandler){
        EventLoopGroup worker = new NioEventLoopGroup();
        
        Bootstrap bootstrap = new Bootstrap()
                .group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new InitChannel(clientHandler));
        
        try{
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            sync.channel().closeFuture().sync();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally{
            worker.shutdownGracefully();
        }
    }
    
    /**
     * test client
     */
    public static void main(String[] args){
        ClientUtil.create("127.0.0.1", 8882, new ClientHandler(){
            @Override
            public void read(Object msg){
                System.out.println(msg);
                if("hi".equals(msg)){
                    ClientContext.getClientChannelContext().writeAndFlush("i am client");
                }
            }
        });
    }
}
