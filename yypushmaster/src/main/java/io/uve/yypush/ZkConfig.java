package io.uve.yypush;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by yangyang21 on 2016/7/5.
 */
public class ZkConfig implements Watcher {
    private static Logger log = Logger.getLogger(ZkConfig.class);
    public ZooKeeper zk = null;

    public ZkConfig(String connectStr) {
        try {
            this.zk = new ZooKeeper(connectStr, 6000, this);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("init zookeeper failed");
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected == event.getState()){

          // no need
        }else if(Event.KeeperState.Expired == event.getState()){
            log.info("zookeeper expared and begin to restart");
            log.info("handling expare stat");
        }
    }
}
