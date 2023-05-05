package com.imooc.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;

/**
 * 描述：     用Curator来操作ZK
 */
public class CuratorTests {

    public static void main(String[] args) throws Exception {
        String connectString = "127.0.0.1:2181";
        String path = "/curator1";
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 10);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retry);
        client.start();
        client.getCuratorListenable().addListener((CuratorFramework c, CuratorEvent event) -> {
            switch (event.getType()) {
                case WATCHED:
                    WatchedEvent watchedEvent = event.getWatchedEvent();
                    if (watchedEvent.getType() == EventType.NodeDataChanged) {
                        //获取path节点的数据
                        System.out.println(new String(c.getData().forPath(path)));
                        System.out.println("触发事件");
                    }
            }
        });
        String data = "test";
        String data2 = "test2";
        //增，指定临时节点
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, data.getBytes());

        //查，并且触发监听
        byte[] bytes = client.getData().watched().forPath(path);
        System.out.println(new String(bytes));

        //改
        client.setData().forPath(path, data2.getBytes());
        Thread.sleep(2000);

        //删
        client.delete().forPath(path);
        Thread.sleep(2000);

        //永久监听
        String pathNew = "/curatorNew";
        client.create().withMode(CreateMode.EPHEMERAL).forPath(pathNew, data.getBytes());

        NodeCache nodeCache = new NodeCache(client, pathNew);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData currentData = nodeCache.getCurrentData();
                if (currentData != null) {
                    System.out.println("触发了永久监听的回调，当前值为：" + new String(currentData.getData()));
                }
            }
        });
        client.setData().forPath(pathNew, data2.getBytes());
        Thread.sleep(2000);
        client.setData().forPath(pathNew, data2.getBytes());
        Thread.sleep(2000);
        client.setData().forPath(pathNew, data2.getBytes());
        Thread.sleep(2000);
        client.delete().forPath(pathNew);
    }
}
