package io.uve.yypush;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.EmailException;

import java.util.List;

/**
 * Created by yangyang21 on 2016/7/5.
 */
public class Monitor implements Runnable {
    public final static String zkBaseMonitor = "/logpushMonitor";

    @Override
    public void run() {
        ZkConfig zkConfig = ZkFactory.getZkConfig();
        ZooKeeper zk = zkConfig.zk;
        try {
            List<String> cl = zk.getChildren(zkBaseMonitor, false);
            if(cl != null && cl.size() > 0){
                for(String node : cl){
                    String threadnode = zkBaseMonitor + '/' + node;
                    List<String> cn = zk.getChildren(threadnode, false);
                    if(cn != null && cn.size() > 0) {
                        for (String nodetime : cn) {
                            String threadnode1 = threadnode + '/' + nodetime;
                            String updateTime = new String(zk.getData(threadnode1, true, null));
                            System.out.println(threadnode1 + "--->" + updateTime);
                        }
                    }
                }
            }else{
                sendMail();
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void sendMail(){
        try{
            Email email = new SimpleEmail();
            email.setHostName("smtp.sina.cn");
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator("yypushmonitor@sina.cn","uve2015"));
            email.setSSLOnConnect(false);
            email.setFrom("yypushmonitor@sina.cn");
            email.setSubject("yypush warning");
            email.setMsg("yypush is not push data,data is null.....");
            email.addTo("hanyan1@staff.weibo.com");
            email.addTo("delong1@staff.weibo.com");
            email.addTo("linhua@staff.weibo.com");
            email.addTo("zhenliang@staff.weibo.com");
            email.addTo("xinyu16@staff.weibo.com");
            email.send();
            System.out.println("Send successfully...");
        } catch (EmailException e){
            e.printStackTrace();
        }
    }
}





