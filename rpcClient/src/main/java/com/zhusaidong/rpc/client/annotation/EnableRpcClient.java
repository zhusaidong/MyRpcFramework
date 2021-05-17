package com.zhusaidong.rpc.client.annotation;

import com.zhusaidong.rpc.client.configuration.RpcClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhusaidong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcClientAutoConfiguration.class)
public @interface EnableRpcClient{}
