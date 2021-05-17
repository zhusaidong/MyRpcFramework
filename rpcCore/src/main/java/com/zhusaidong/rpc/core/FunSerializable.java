package com.zhusaidong.rpc.core;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 序列化调用方法
 *
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class FunSerializable implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String fullClassName;
    private String method;
    private Class<?>[] parameterTypes;
    private Object[] args;
    private int version = 1;
    
    public String getMethod(){
        return method;
    }
    
    public FunSerializable setMethod(String method){
        this.method = method;
        return this;
    }
    
    public Class<?>[] getParameterTypes(){
        return parameterTypes;
    }
    
    public FunSerializable setParameterTypes(Class<?>[] parameterTypes){
        this.parameterTypes = parameterTypes;
        return this;
    }
    
    public Object[] getArgs(){
        return args;
    }
    
    public FunSerializable setArgs(Object[] args){
        this.args = args;
        return this;
    }
    
    public String getFullClassName(){
        return fullClassName;
    }
    
    public FunSerializable setFullClassName(String fullClassName){
        this.fullClassName = fullClassName;
        return this;
    }
    
    public int getVersion(){
        return version;
    }
    
    public FunSerializable setVersion(int version){
        this.version = version;
        return this;
    }
    
    @Override
    public String toString(){
        return "FunSerializable{" + "fullClassName='" + fullClassName + '\'' + ", method='" + method + '\'' + ", parameterTypes=" + Arrays
                .toString(parameterTypes) + ", args=" + Arrays.toString(args) + ", version=" + version + '}';
    }
}
