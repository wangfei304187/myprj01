package com.my.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import com.my.zk.usenoclient.ZKConstants;

// https://www.cnblogs.com/leeSmall/p/9576437.html

public class CreateNodeDemo
{
    public static void main(String[] args) throws Exception
    {
        String path = "/zk-curator/c1";
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZKConstants.ADDRESS)
                .sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        client.start();
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "test".getBytes());
    }
}