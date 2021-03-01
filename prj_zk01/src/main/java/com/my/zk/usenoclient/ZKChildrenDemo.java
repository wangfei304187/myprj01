package com.my.zk.usenoclient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

// 改变子节点并监听事件
public class ZKChildrenDemo implements Watcher
{
    private static final CountDownLatch cdl = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException
    {
        ZKChildrenDemo.zk = new ZooKeeper(ZKConstants.ADDRESS, 5000, new ZKChildrenDemo());
        ZKChildrenDemo.cdl.await();

        System.out.println("create /zk-test");
        ZKChildrenDemo.zk.create("/zk-test", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("create /zk-test/c1");
        ZKChildrenDemo.zk.create("/zk-test/c1", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        List<String> list = ZKChildrenDemo.zk.getChildren("/zk-test", true);
        for (String str : list)
        {
            System.out.println(str);
        }

        ZKChildrenDemo.zk.create("/zk-test/c2", "789".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("create /zk-test/c2");

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
                ZKChildrenDemo.cdl.countDown();
            }
            else if (event.getType() == EventType.NodeChildrenChanged)
            {
                try
                {
                    System.out.println("process NodeChildrenChanged");
                    System.out.println("Child: " + ZKChildrenDemo.zk.getChildren(event.getPath(), true));
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}