package io.uve.yypush;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyang21 on 2016/7/5.
 */
public class Sailing {
    public static void main(String[] args){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        service.scheduleAtFixedRate(new Monitor(), 0, 5, TimeUnit.MINUTES);
    }
}
