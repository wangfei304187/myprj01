package com.my.zk.usenoclient;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

// 连接zk并监听事件
public class ZKTest01 implements Watcher
{
    private static final CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws IOException
    {
        ZooKeeper zk = new ZooKeeper(ZKConstants.ADDRESS, 5000, new ZKTest01());
        System.out.println(zk.getState());

        try
        {
            ZKTest01.cdl.await();
        }
        catch (Exception e)
        {
            System.out.println("ZK Session established.");
        }
        System.out.println("--END--");
    }

    // 监听到事件时进行处理
    @Override
    public void process(WatchedEvent event)
    {
        System.out.println("Receive watched event:" + event);
        if (KeeperState.SyncConnected == event.getState())
        {
            ZKTest01.cdl.countDown();
        }
    }
}