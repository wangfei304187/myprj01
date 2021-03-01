package com.my.zk.curator;

import java.util.Collection;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.my.zk.usenoclient.ZKConstants;

public class CuratorTest
{

    public static void main(String[] args) throws Exception
    {
        new CuratorTest().todo();
    }

    public void todo() throws Exception
    {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZKConstants.ADDRESS)
                .sessionTimeoutMs(5000).retryPolicy(policy).build();
        client.start();

        // TODO
        transaction(client);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public Collection<CuratorTransactionResult> transaction(CuratorFramework client) throws Exception
    {
        // this example shows how to use ZooKeeper's transactions

        CuratorOp createOp = client.transactionOp().create().forPath("/a/path", "some data".getBytes());
        CuratorOp setDataOp = client.transactionOp().setData().forPath("/another/path", "other data".getBytes());
        CuratorOp deleteOp = client.transactionOp().delete().forPath("/yet/another/path");
        Collection<CuratorTransactionResult> results = client.transaction().forOperations(createOp, setDataOp, deleteOp);

        for (CuratorTransactionResult result : results)
        {
            System.out.println(result.getForPath() + " - " + result.getType());
        }

        return results;
    }

}
