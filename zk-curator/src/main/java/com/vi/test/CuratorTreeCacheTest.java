package com.vi.test;

import com.vi.util.CuratorClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.CreateMode;

public class CuratorTreeCacheTest {
    private static final String PATH = "/path/cache";

    public static void main(String[] args) {
        //创建客户端
        CuratorFramework client = CuratorClientUtil.getClient();
        client.start();
        try {
            TreeCache cache = treeCacheTest(client);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH + "/data1", "first data".getBytes());
            Thread.sleep(500);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH + "/data2", "second data".getBytes());
            Thread.sleep(500);
            client.setData().forPath(PATH , "update path data".getBytes());
            Thread.sleep(500);
            client.setData().forPath(PATH + "/data1", "update data".getBytes());
            Thread.sleep(500);
            client.delete().deletingChildrenIfNeeded().forPath("/path");
            Thread.sleep(1000);
            cache.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TreeCache treeCacheTest(CuratorFramework client) throws Exception {
        //只能监控一个节点
        TreeCache cache = new TreeCache(client, PATH);
        cache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println("事件为：" + event.getType() + ",节点路径为：" + event.getData().getPath() + ",数据为：" +  new String(event.getData().getData()));
            }
        });
        cache.start();
        return cache;
    }
}

