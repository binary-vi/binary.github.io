package com.vi.test;

import com.vi.util.CuratorClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;

public class CuratorNodeCacheTest {
    private static final String PATH = "/path/cache";

    public static void main(String[] args) {
        //创建客户端
        CuratorFramework client = CuratorClientUtil.getClient();
        client.start();
        try {
            NodeCache cache = nodeCacheTest(client);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH, "first data".getBytes());
            Thread.sleep(500);
            client.setData().forPath(PATH, "update data".getBytes());
            Thread.sleep(500);
            client.delete().deletingChildrenIfNeeded().forPath("/path");
            Thread.sleep(500);
            cache.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NodeCache nodeCacheTest(CuratorFramework client) throws Exception {
        //只能监控一个节点
        NodeCache cache = new NodeCache(client, PATH);
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = cache.getCurrentData();
                if (childData == null) {
                    System.out.println("节点删除！");
                } else {
                    System.out.println("节点数据：" + new String(cache.getCurrentData().getData()));
                }
            }
        });
        cache.start();
        return cache;
    }
}

