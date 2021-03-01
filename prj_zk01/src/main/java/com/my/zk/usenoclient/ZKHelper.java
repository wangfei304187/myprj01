package com.my.zk.usenoclient;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

// https://github.com/sfines/menagerie/blob/master/src/main/java/org/menagerie/ZkUtils.java

public class ZKHelper
{

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
}
