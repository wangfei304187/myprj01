package com.my.zk.useZkClient;

import org.I0Itec.zkclient.ZkClient;

import com.my.zk.usenoclient.ZKConstants;

// ZkClient递归创建顺序节点
// https://www.cnblogs.com/leeSmall/p/9576437.html

public class CreateNodeDemo
{
    public static void main(String[] args)
    {
        ZkClient client = new ZkClient(ZKConstants.ADDRESS, 10000, 10000);
        String path = "/zk-client/c1";
        // 递归创建顺序节点 true：先创建父节点/zk-client
        client.createPersistent(path, true);
    }
}