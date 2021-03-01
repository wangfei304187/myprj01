package com.my.zk.useZkClient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import com.my.zk.usenoclient.ZKConstants;

// ZkClient获取子节点数据
// https://www.cnblogs.com/leeSmall/p/9576437.html

public class GetChildrenDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        String path = "/zk-client";
        ZkClient client = new ZkClient(ZKConstants.ADDRESS, 10000);

        // 注册子节点数据改变的事件
        client.subscribeChildChanges(path, new IZkChildListener()
        {

            // 子节点数据改变事件 (create, delete, delete parent)
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception
            {
                System.out.println(parentPath + "的子发生变化: " + currentChilds);
            }
        });

        // 创建顺序节点
        client.createPersistent(path);
        Thread.sleep(1000);
        // 获取子节点数据 此时还没有创建获取不到
        System.out.println(client.getChildren(path));
        // 在前面的父节点 /zk-client下创建子节点c1
        client.createPersistent(path + "/c1");
        Thread.sleep(1000);
        // 删除子节点
        client.delete(path + "/c1");
        Thread.sleep(1000);
        // 删除父节点
        client.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}