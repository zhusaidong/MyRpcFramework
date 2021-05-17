# rpc服务

基于dubbo作者的文章：[简易RpcFramework](https://www.iteye.com/blog/javatar-1123915) 来学习rpc的原理

**rpc的核心是通讯（socket）和动态代理（客户端访问某个方法，实际上是调用socket，访问远程的方法，并返回）**

- 使用了spring-beans，来做包扫描。

- 注册中心使用了zookeeper。

    zookeeper结构:
    
    ```text
    /services
        /HelloService
            /127.0.0.1:8881
            /127.0.0.1:8882
    ```

- 使用netty来做通讯。

## 使用

```properties
# 注册中心
myrpc.server.zookeeper-url=127.0.0.1:21810
```

## 学习

### Netty中ChannelHandler的生命周期

- handlerAdded: 

    新建立的连接会按照初始化策略，把handler添加到该channel的pipeline里面，
    也就是channel.pipeline.addLast(new LifeCycleInBoundHandler)执行完成后的回调；
    
- channelRegistered: 

    当该连接分配到具体的worker线程后，该回调会被调用。
    
- channelActive：

    channel的准备工作已经完成，所有的pipeline添加完成，并分配到具体的线上，说明该channel准备就绪，可以使用了。
    
- channelRead：

    客户端向服务端发来数据，每次都会回调此方法，表示有数据可读；
    
- channelReadComplete：

    服务端每次读完一次完整的数据之后，回调该方法，表示数据读取完毕；
    
- channelInactive：

    当连接断开时，该回调会被调用，说明这时候底层的TCP连接已经被断开了。
    
- channelUnRegistered: 

    对应channelRegistered，当连接关闭后，释放绑定的worker线程；
    
- handlerRemoved： 

    对应handlerAdded，将handler从该channel的pipeline移除后的回调方法。

### ChannelHandlerContext

入站（inbound）Handler通常由底层Java NIO channel触发，

出站（outbound） Handler通常是Netty channel操作底层Java NIO channel，

## todo 

服务端挂掉时客户端，重试机制。

