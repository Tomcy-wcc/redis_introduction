package com.yc;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Description
 * @auther wcc
 * @create 2019-09-26 14:44
 */
public class ZookeeperTest {
    private ZooKeeper zk;

    /**
     * ZooKeeper 构造方法参数
     * String connectString 连接地址
     * int sessionTimeout 会话超时时间，默认单位是毫秒
     * Watcher watcher 监控者
     *
     * Zookeeper的连接是利用NIO的非阻塞方式连接
     * 就意味着可能没有连上就继续往下的操作
     * 因此需要监控这个连接是否建立
     */
    @Before
    public void zookeeperConn() throws InterruptedException, IOException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        zk = new ZooKeeper("192.168.72.132:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    System.out.println("连接成功");
                }
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        System.out.println("conn finished");
    }

    /**
     * 创建zookeeper节点
     *
     * String path 路径
     * byte[] data 数据
     * List<ACL> acl 策略
     * CreateMode createMode 节点类型
     *
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String s = zk.create("/news", "news server".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
    }

    @Test
    public void updateNode() throws KeeperException, InterruptedException {
        Stat stat = zk.setData("/news", "news".getBytes(), 0);
        System.out.println(stat);
    }

    /**
     * 获取节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void getNode() throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        byte[] data = zk.getData("/news", null, stat);
        System.out.println(stat.getVersion());
        System.out.println(new String(data));
    }

    /**
     * 删除节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void deleteNode() throws KeeperException, InterruptedException {
        zk.delete("/news", -1);
    }

    /**
     * 监控节点是否被改变
     */
    @Test
    public void dataChange() throws KeeperException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        zk.getData("/news", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getType() == Event.EventType.NodeDataChanged)
                    System.out.println("node changed");
                countDownLatch.countDown();
            }
        }, null);
        countDownLatch.await();
    }
}
