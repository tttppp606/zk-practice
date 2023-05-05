package com.imooc.zkjavaapi;

import com.imooc.zkjavaapi.watcher.ConnectWatcher;
import java.io.IOException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * 描述：     连接到ZK
 */
public class FirstConnect {

    public static void main(String[] args) throws IOException, InterruptedException {
        ConnectWatcher connectWatcher = new ConnectWatcher();
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOST, ZkConstant.CONNECT_TIMEOUT, connectWatcher);
        System.out.println("客户端开始连接ZK服务器");
        States state = zk.getState();
        System.out.println(state);
        Thread.sleep(2000);
        state = zk.getState();
        System.out.println(state);
        Thread.sleep(2000);
        zk.close();
    }
}
