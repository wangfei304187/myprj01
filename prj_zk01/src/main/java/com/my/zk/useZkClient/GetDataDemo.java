package com.my.zk.useZkClient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import com.my.zk.usenoclient.ZKConstants;

// ZkClient获取数据
// https://www.cnblogs.com/leeSmall/p/9576437.html

public class GetDataDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        String path = "/zk-client";
        ZkClient client = new ZkClient(ZKConstants.ADDRESS, 10000);

        // 创建临时节点
        client.createEphemeral(path, "123");

        // 注册父节点数据改变的事件
        client.subscribeDataChanges(path, new IZkDataListener()
        {

            // 父节点数据改变事件
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception
            {
                System.out.println(dataPath + " changed: " + data);
            }

            // 父节点数据删除事件
            @Override
            public void handleDataDeleted(String dataPath) throws Exception
            {
                System.out.println(dataPath + " deleted");
            }
        });

        System.out.println(client.readData(path).toString());
        client.writeData(path, "456");
        Thread.sleep(1000);
        client.delete(path);
        // sleep的目的是为了更好的观察事件变化
        Thread.sleep(Integer.MAX_VALUE);
    }
}