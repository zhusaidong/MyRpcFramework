package com.zhusaidong.demo.controller;

import com.zhusaidong.api.service.HelloService;
import com.zhusaidong.rpc.core.annotation.RpcReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhusaidong
 */
@RestController
public class MainController{
    @RpcReference
    HelloService helloService;
    
    @RequestMapping("/test")
    public String test(){
        return helloService.world();
    }
}
