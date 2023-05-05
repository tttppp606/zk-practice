package com.imooc.zkjavaapi.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class DataChangedWatcher implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        System.out.println("DataChangedWatcher的process被调用了");
        if (event.getType()== EventType.NodeDataChanged) {
            System.out.println("数据被改变");
        }
        if (event.getType()== EventType.NodeDeleted) {
            System.out.println("节点已删除");
        }
    }
}
