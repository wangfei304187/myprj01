package com.my.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.my.zk.usenoclient.ZKConstants;

// curator创建连接session
// https://www.cnblogs.com/leeSmall/p/9576437.html

public class CreateSessionDemo
{
    public static void main(String[] args) throws Exception
    {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZKConstants.ADDRESS)
                .sessionTimeoutMs(5000).retryPolicy(policy).build();
        client.start();

        // client.delete().forPath("/my");
        // client.create().creatingParentsIfNeeded().forPath("/my/path", "123".getBytes());

        Thread.sleep(Integer.MAX_VALUE);
    }
}