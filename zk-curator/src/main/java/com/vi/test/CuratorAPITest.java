package com.vi.test;

import com.vi.util.CuratorClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Created by shaobo on 2018/5/9.
 */
public class CuratorAPITest {

    public static void main(String[] args) {
        CuratorFramework client = CuratorClientUtil.getClient();
        client.start();
        try {
            createTest(client);
            queryTest(client);
            updateTest(client);
            deleteTest(client);
            Thread.sleep(1000);
            transcationTest(client);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createTest(CuratorFramework client) throws Exception {
        /*
         *   4种节点类型：
         *   PERSISTENT：持久化
         *   PERSISTENT_SEQUENTIAL：持久化并且带序列号
         *   EPHEMERAL：临时
         *   EPHEMERAL_SEQUENTIAL：临时并且带序列号
         */
        //1、什么都不加默认是持久化节点,返回节点路径path
        String result = client.create().forPath("/data1");
        System.out.println("1、默认创建持久化节点结果:" + result);
        //2、创建持久化节点并且附加值
        result = client.create().forPath("/data2", "this is data2".getBytes());
        System.out.println("2、创建持久化节点并且附加值结果:" + result);
        //3、创建临时节点，内容为空
        result = client.create().withMode(CreateMode.EPHEMERAL).forPath("/data3");
        System.out.println("3、创建临时节点，内容为空结果:" + result);
        //4、创建临时节点，附带初始化内容
        result = client.create().withMode(CreateMode.EPHEMERAL).forPath("/data4", "this is data4".getBytes());
        System.out.println("4、创建临时节点，附带初始化内容结果:" + result);
        //5、创建一个临时有序节点，附带初始化内容，并且自动创建父节点
        result = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/parent/data", "children".getBytes());
        System.out.println("5、同时创建父子节点，附带初始化内容结果:" + result);
    }

    public static void updateTest(CuratorFramework client) throws Exception {
        //1、更新一个节点的数据内容，返回一个stat
        Stat stat = client.setData().forPath("/data1", "update data1 data".getBytes());
    }

    public static void queryTest(CuratorFramework client) throws Exception {
        //1、读节点的数据内容，返回byte数组
        byte[] data = client.getData().forPath("/data2");
        System.out.println(new String(data));
        //2、读取一个节点的数据内容，同时获取到该节点的stat
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/data4");
        //3、获取一个路径下所有的子节点，返回List<String>
        List<String> childrens = client.getChildren().forPath("/");
        System.out.println(childrens);
        //4、检查节点是否存在
        stat = client.checkExists().forPath("/data3");
    }

    public static void deleteTest(CuratorFramework client) throws Exception {
        //1、只能删除叶子节点,否则抛出异常
        client.delete().forPath("/data1");
        //2、删除一个节点并递归删除其所有的子节点
        client.delete().deletingChildrenIfNeeded().forPath("/parent");
        //3、保证强制删除一个节点,只要客户端会话有效就会持续进行删除直到删除成功。
        client.delete().guaranteed().forPath("/data2");
    }

    public static void transcationTest(CuratorFramework client) throws Exception {
        //1、检查/data5节点是否存在，创建
        client.inTransaction()
                .create().withMode(CreateMode.PERSISTENT).forPath("/data5", "this is data5".getBytes())
                .and().setData().forPath("/data5", "update data5".getBytes())
                .and().commit();
    }
}
