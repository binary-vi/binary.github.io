package com.vi.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by shaobo on 2018/5/9.
 */
public class CuratorClientUtil {

    private static final String ZK_ADDRESS = "localhost:2181";

    private static CuratorFramework client;

    private CuratorClientUtil(){}

    public static CuratorFramework getClient(){
        if(client == null){
            synchronized (ZK_ADDRESS){
                if(client == null){
                    client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, 5000,3000,new RetryNTimes(10, 5000));
                }
            }
        }
        return client;
    }
}
