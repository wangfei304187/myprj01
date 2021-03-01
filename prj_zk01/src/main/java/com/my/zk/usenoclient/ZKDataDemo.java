package com.my.zk.usenoclient;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

// 改变znode数据并监听事件
public class ZKDataDemo implements Watcher
{
    private static final CountDownLatch cdl = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException
    {
        ZKDataDemo.zk = new ZooKeeper(ZKConstants.ADDRESS, 5000, new ZKDataDemo());
        ZKDataDemo.cdl.await();

        ZKDataDemo.zk.create("/zk-test", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(new String(ZKDataDemo.zk.getData("/zk-test", true, ZKDataDemo.stat)));

        // ZKDataDemo.zk.getData("/zk-test", true, ZKDataDemo.stat);
        // System.out.println(ZKDataDemo.stat.getCzxid() + ", " + ZKDataDemo.stat.getMzxid() + ", " + ZKDataDemo.stat.getVersion());
        ZKDataDemo.zk.setData("/zk-test", "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    // 监听到事件时进行处理
    @Override
    public void process(WatchedEvent event)
    {
        if (KeeperState.SyncConnected == event.getState())
        {
            if (EventType.None == event.getType() && null == event.getPath())
            {
                ZKDataDemo.cdl.countDown();
            }
            else if (event.getType() == EventType.NodeDataChanged)
            {
                try
                {
                    System.out.println("1.==>" + new String(ZKDataDemo.zk.getData(event.getPath(), true, ZKDataDemo.stat)));
                    System.out.println("2.==>" + ZKDataDemo.stat.getCzxid() + ", " + ZKDataDemo.stat.getMzxid() + ", " + ZKDataDemo.stat.getVersion());
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}