package com.zhusaidong.rpc.server.annotation;

import com.zhusaidong.rpc.server.configuration.RpcServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * rpc服务注解
 *
 * @author zhusaidong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcServerAutoConfiguration.class)
public @interface EnableRpcServer{}
