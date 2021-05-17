package com.zhusaidong.rpc.core.zookeeper;

import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhusaidong
 */
@SuppressWarnings("unused")
public class ZooKeeperUtil{
    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperUtil.class);
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
    
    /**
     * 连接zookeeper
     *
     * @param url     zookeeper url
     * @param timeout session timeout
     *
     * @return zookeeper
     */
    public static ZooKeeper connect(String url, Integer timeout){
        ZooKeeper zooKeeper = null;
        try{
            zooKeeper = new ZooKeeper(url, timeout, watchedEvent -> {
                if(watchedEvent.getState() == KeeperState.SyncConnected){
                    COUNT_DOWN_LATCH.countDown();
                }
            });
            COUNT_DOWN_LATCH.await();
        }
        catch(IOException | InterruptedException exception){
            logger.error(exception.getMessage());
        }
        
        return zooKeeper;
    }
}
