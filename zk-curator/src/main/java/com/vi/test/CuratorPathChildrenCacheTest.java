package com.vi.test;

import com.vi.util.CuratorClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;

import java.util.List;

public class CuratorPathChildrenCacheTest {
    private static final String PATH = "/path/cache";

    public static void main(String[] args) {
        //创建客户端
        CuratorFramework client = CuratorClientUtil.getClient();
        client.start();
        try {
            PathChildrenCache cache = pathCacheTest(client);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH + "/data1", "first data".getBytes());
            Thread.sleep(500);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH + "/data2", "second data".getBytes());
            Thread.sleep(500);
            //获取所有子节点
            List<ChildData> datas =cache.getCurrentData();
            for(ChildData childData : datas){
                System.out.println("节点路径："+childData.getPath() + ",节点值：" + new String(childData.getData()));
            }
            client.setData().forPath(PATH + "/data1", "update data".getBytes());
            Thread.sleep(500);
            client.delete().deletingChildrenIfNeeded().forPath("/path");
            Thread.sleep(500);
            cache.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PathChildrenCache pathCacheTest(CuratorFramework client) throws Exception {
        //第三个参数不为true时，不会缓存data数据
        PathChildrenCache cache = new PathChildrenCache(client, PATH, true);
        /*
            三种启动方式
            NORMAL：正常初始化。
            BUILD_INITIAL_CACHE：在调用start()之前会调用rebuild()。
            POST_INITIALIZED_EVENT： 当Cache初始化数据后发送一个
            cache.start(PathChildrenCache.StartMode.NORMAL);
         */
        cache.start();
        //增加监听事件
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("事件为：" + event.getType() + "，数据为：" + new String(event.getData().getData()));
            }
        });
        return cache;
    }

}

