package com.my.zk.usenoclient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

public class ZKAppTest implements Watcher
{
    private static final CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException
    {
        ZKAppTest app = new ZKAppTest();
        app.todo();
    }

    public void todo() throws IOException, InterruptedException, KeeperException
    {
        final ZooKeeper zk = new ZooKeeper(ZKConstants.ADDRESS, 5000, new ZKAppTest());
        ZKAppTest.cdl.await();

        String path0 = zk.create("/zk-test-", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create path: " + path0);

        // Success create path: /zk-test-
        // Success create path: /zk-test-0000000001

        ZKAppTest.recursiveSafeDelete(zk, "/zk-seq", -1);
        String path1 = zk.create("/zk-seq", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Success create path: " + path1);
        // String path2 = zk.create("/zk-seq/test-", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // System.out.println("Success create path: " + path2);
        // String path3 = zk.create("/zk-seq/test-", "7".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // System.out.println("Success create path: " + path3);
        // String path4 = zk.create("/zk-seq/test-", "8".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // System.out.println("Success create path: " + path4);
        // System.out.println(ZKOperateDemo.parseSequenceString(path2, '-'));
        // System.out.println(ZKOperateDemo.parseSequenceNumber(path2, '-'));
        CountDownLatch latch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++)
        {
            Integer N = i;
            Runnable runable = new MyRunnable(zk, N, latch);
            Executors.newCachedThreadPool().execute(runable);
        }
        latch.await();

        System.out.println("------->");
        List<String> childList = zk.getChildren("/zk-seq", false);
        for (String cpath : childList)
        {
            System.out.println(ZKAppTest.parseSequenceString(cpath, '-'));
        }
        System.out.println("<-------");

        String r1 = ZKAppTest.safeCreate(zk, "/zk_t", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        String r2 = ZKAppTest.safeCreate(zk, "/zk_t", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(r1);
        System.out.println(r2);

        byte[] bytes = zk.getData("/zk_t", false, null);
        System.out.println("get: " + new String(bytes));

        // // throw KeeperException$NoNodeException: KeeperErrorCode = NoNode for /zk_t/a
        // System.out.println(new String(zk.getData("/zk_t/a", false, null)));

        byte[] bytes2 = ZKAppTest.safeGetData(zk, "/zk_t", false, null);
        System.out.println("get2: " + new String(bytes2));

        byte[] bytes3 = ZKAppTest.safeGetData(zk, "/zk_t/b", false, null);
        System.out.println("get3: " + new String(bytes3) + ", length=" + bytes3.length);

        // String r3 = ZKOperateDemo.safeCreate(zk, "/zk_tr", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // System.out.println("Persistent, r3=" + r3);

        String r4 = ZKAppTest.recursiveSafeCreate(zk, "/zk_tr/a/b/c", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(r4 + ", length=" + r4.length());

        ZKAppTest.recursiveSafeDelete(zk, "/zk_tr", -1);
    }

    // 监听到事件时进行处理
    @Override
    public void process(WatchedEvent event)
    {
        System.out.println("Receive watched event:" + event);
        if (KeeperState.SyncConnected == event.getState())
        {
            ZKAppTest.cdl.countDown();
        }
    }

    // //////////////////////////////////////////////

    public static int parseSequenceNumber(String node, char sequenceStartDelimiter)
    {
        String sequenceStr = ZKAppTest.parseSequenceString(node, sequenceStartDelimiter);
        return Integer.parseInt(sequenceStr);
    }

    public static String parseSequenceString(String node, char sequenceStartDelimiter)
    {
        int seqStartIndex = node.lastIndexOf(sequenceStartDelimiter);
        return node.substring(seqStartIndex + 1);
    }

    public static byte[] safeGetData(ZooKeeper zk, String node, Watcher watcher, Stat stat) throws InterruptedException, KeeperException
    {
        try
        {
            return zk.getData(node, watcher, stat);
        }
        catch (KeeperException e)
        {
            if (e.code() != KeeperException.Code.NONODE)
            {
                throw e;
            }
            else
            {
                return new byte[0];
            }
        }
    }

    public static byte[] safeGetData(ZooKeeper zk, String node, boolean watch, Stat stat) throws InterruptedException, KeeperException
    {
        try
        {
            return zk.getData(node, watch, stat);
        }
        catch (KeeperException e)
        {
            if (e.code() != KeeperException.Code.NONODE)
            {
                throw e;
            }
            else
            {
                return new byte[0];
            }
        }
    }

    public static void recursiveSafeDelete(ZooKeeper zk, String path, int version) throws KeeperException, InterruptedException
    {
        try
        {
            List<String> children = zk.getChildren(path, false);
            for (String child : children)
            {
                ZKAppTest.recursiveSafeDelete(zk, path + "/" + child, version);
            }
            // delete this node
            ZKAppTest.safeDelete(zk, path, version);
        }
        catch (KeeperException ke)
        {
            if (ke.code() != KeeperException.Code.NONODE)
            {
                throw ke;
            }
        }
    }

    public static boolean safeDelete(ZooKeeper zk, String path, int version) throws KeeperException, InterruptedException
    {
        try
        {
            zk.delete(path, version);
            System.out.println("delete: " + path);
            return true;
        }
        catch (KeeperException ke)
        {
            // if the node has already been deleted, don't worry about it
            if (ke.code() == KeeperException.Code.NONODE)
            {
                return false;
            }
            else
            {
                throw ke;
            }
        }
    }

    public static String recursiveSafeCreate(ZooKeeper zk, String path, byte[] data, List<ACL> privileges, CreateMode createMode) throws KeeperException, InterruptedException
    {
        // try
        // {
        // PathUtils.validatePath(path);
        // }
        // catch (IllegalArgumentException e)
        // {
        // e.printStackTrace();
        // return "";
        // }

        if (path == null || path.isEmpty())
        {
            return ""; // nothing to do
        }
        else if ("/".equals(path))
        {
            return path;
        }
        else
        {
            int index = path.lastIndexOf("/");
            if (index == -1)
            {
                return ""; // nothing to do
            }
            String parent = path.substring(0, index);
            // make sure that the parent has been created
            ZKAppTest.recursiveSafeCreate(zk, parent, data, privileges, createMode);

            // create this node now
            return ZKAppTest.safeCreate(zk, path, data, privileges, createMode);
        }
    }

    public static String safeCreate(ZooKeeper zk, String path, byte[] data, List<ACL> acl, CreateMode createMode) throws KeeperException, InterruptedException
    {
        try
        {
            return zk.create(path, data, acl, createMode);
        }
        catch (KeeperException e)
        {
            if (e.code() == KeeperException.Code.NODEEXISTS)
            {
                return path;
            }
            else
            {
                throw e;
            }
        }
    }

    class MyRunnable implements Runnable
    {
        private ZooKeeper zk;
        private Integer n;
        private CountDownLatch cdl;

        public MyRunnable(ZooKeeper zk, Integer n, CountDownLatch cdl)
        {
            this.zk = zk;
            this.n = n;
            this.cdl = cdl;
        }

        @Override
        public void run()
        {
            String pathN;
            try
            {
                pathN = zk.create("/zk-seq/test-", String.valueOf(n).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                System.out.println("MyRunnable: Success create path: " + pathN);
            }
            catch (KeeperException | InterruptedException e)
            {
                e.printStackTrace();
            }

            cdl.countDown();
        }

    }
}