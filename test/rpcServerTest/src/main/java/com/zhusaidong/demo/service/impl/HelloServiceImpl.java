package com.zhusaidong.demo.service.impl;

import com.zhusaidong.api.service.HelloService;
import com.zhusaidong.rpc.core.annotation.RpcService;

import java.time.LocalDateTime;

/**
 * @author zhusaidong
 */
@RpcService
public class HelloServiceImpl implements HelloService{
    @Override
    public String world(){
        return "hello world, this is server time: " + LocalDateTime.now().toString();
    }
}
