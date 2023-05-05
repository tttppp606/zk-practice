package com.imooc.zkjavaapi.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 描述：     连接Watcher
 */
public class ConnectWatcher implements Watcher {

    /**
     * 每次发生变化，都会回调process方法，传入参数，event相当于监听
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println("ConnectWatcher的process被调用了");
        if (event.getState()== KeeperState.SyncConnected) {
            System.out.println("连接成功");
        }
        if (event.getState()== KeeperState.Closed) {
            System.out.println("连接关闭");
        }
    }
}
