package com.imooc.zkjavaapi;

import com.imooc.zkjavaapi.watcher.ConnectWatcher;
import com.imooc.zkjavaapi.watcher.DataChangedWatcher;
import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * 描述：     ZK节点改变、删除事件的监听
 */
public class ZkWatcher {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DataChangedWatcher dataChangedWatcher = new DataChangedWatcher();
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOST, ZkConstant.CONNECT_TIMEOUT, dataChangedWatcher);
        System.out.println("客户端开始连接ZK服务器");
        States state = zk.getState();
        System.out.println(state);
        Thread.sleep(2000);
        state = zk.getState();
        System.out.println(state);
        Thread.sleep(2000);

        zk.create(ZkConstant.PATH1, "imooc1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(2000);
        byte[] data = null;
        //watcher设置为true，代表可以监听对应的事件
        data = zk.getData(ZkConstant.PATH1, true, null);
        System.out.println(new String(data));
        Thread.sleep(2000);
        zk.setData(ZkConstant.PATH1, "imooc2".getBytes(), -1);
        Thread.sleep(2000);
        data = zk.getData(ZkConstant.PATH1, true, null);
        System.out.println(new String(data));
        zk.delete(ZkConstant.PATH1, -1);

        zk.close();
    }
}
