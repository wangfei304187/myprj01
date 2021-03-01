package com.my.zk.usenoclient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

// 异步调用并完成回调
class ChildrenCallback implements AsyncCallback.Children2Callback
{

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat)
    {
        System.out.println(
                "processResult Child: " + rc + ", path: " + path + ", ctx: " + ctx + ", children: " + children + ", stat: " + stat);
    }
}

public class ZKChildrenAsyncDemo implements Watcher
{
    private static final CountDownLatch cdl = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException
    {
        ZKChildrenAsyncDemo.zk = new ZooKeeper(ZKConstants.ADDRESS, 5000, new ZKChildrenAsyncDemo());
        ZKChildrenAsyncDemo.cdl.await();

        ZKChildrenAsyncDemo.zk.create("/zk-test", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        ZKChildrenAsyncDemo.zk.create("/zk-test/c1", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        ZKChildrenAsyncDemo.zk.getChildren("/zk-test", true, new ChildrenCallback(), "ok");

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
                ZKChildrenAsyncDemo.cdl.countDown();
            }
            else if (event.getType() == EventType.NodeChildrenChanged)
            {
                try
                {
                    System.out.println("Child: " + ZKChildrenAsyncDemo.zk.getChildren(event.getPath(), true));
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}