package com.imooc.zkjavaapi;

import com.imooc.zkjavaapi.watcher.ConnectWatcher;
import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * 描述：     对节点的增删改查
 */
public class ZkCRUD {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConnectWatcher connectWatcher = new ConnectWatcher();
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOST, ZkConstant.CONNECT_TIMEOUT, connectWatcher);
        System.out.println("客户端开始连接ZK服务器");
        States state = zk.getState();
        System.out.println(state);
        Thread.sleep(2000);
        state = zk.getState();
        System.out.println(state);
        Thread.sleep(2000);

        //对节点进行增删改查
        zk.create(ZkConstant.PATH1, "imooc1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(2000);
        byte[] data = null;
        data = zk.getData(ZkConstant.PATH1, null, null);
        System.out.println(new String(data));
        Thread.sleep(2000);
        zk.setData(ZkConstant.PATH1, "imooc2".getBytes(), -1);
        Thread.sleep(2000);
        data = zk.getData(ZkConstant.PATH1, null, null);
        System.out.println(new String(data));
        zk.delete(ZkConstant.PATH1, -1);

        zk.close();
    }
}
