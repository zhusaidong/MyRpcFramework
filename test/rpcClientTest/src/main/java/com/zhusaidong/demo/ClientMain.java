package com.zhusaidong.demo;

import com.zhusaidong.rpc.client.annotation.EnableRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhusaidong
 */
@SpringBootApplication
@EnableRpcClient
public class ClientMain{
    public static void main(String[] args){
        SpringApplication.run(ClientMain.class, args);
    }
}
