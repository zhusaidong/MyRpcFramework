package com.zhusaidong.demo;

import com.zhusaidong.rpc.server.annotation.EnableRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhusaidong
 */
@SpringBootApplication
@EnableRpcServer
public class ServerMain{
    public static void main(String[] args){
        SpringApplication.run(ServerMain.class,args);
    }
}
