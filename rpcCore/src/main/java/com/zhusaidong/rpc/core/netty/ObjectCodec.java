package com.zhusaidong.rpc.core.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhusaidong
 */
public class ObjectCodec extends ByteToMessageCodec<Serializable>{
    
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf){
        byteBuf.writeBytes(convertToBytes(serializable));
    }
    
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list){
        list.add(convertToObject(byteBuf.array()));
    }
    
    private Serializable convertToObject(byte[] body){
        Object object = null;
        try{
            object = new ObjectInputStream(new ByteArrayInputStream(body)).readObject();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        
        return (Serializable)object;
    }
    
    private byte[] convertToBytes(Serializable serializable){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
            objectOutputStream.writeObject(serializable);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return bos.toByteArray();
    }
}