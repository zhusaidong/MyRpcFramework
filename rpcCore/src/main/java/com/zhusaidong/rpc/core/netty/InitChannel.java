package com.zhusaidong.rpc.core.netty;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class InitChannel extends ChannelInitializer<SocketChannel>{
    private final ChannelInboundHandler channelInboundHandler;
    
    public InitChannel(ChannelInboundHandler channelInboundHandler){
        this.channelInboundHandler = channelInboundHandler;
    }
    
    @Override
    protected void initChannel(SocketChannel socketChannel){
        ///添加对象解码器 负责对序列化POJO对象进行解码 设置对象序列化最大长度 防止内存溢出
        //设置线程安全的WeakReferenceMap对类加载器进行缓存 支持多线程并发访问 防止内存溢出
        ClassResolver classResolver = ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader());
        socketChannel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, classResolver));
        //添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
        socketChannel.pipeline().addLast(new ObjectEncoder());
        
        // 处理网络IO
        socketChannel.pipeline().addLast(channelInboundHandler);
    }
}
