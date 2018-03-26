package com.example.domain.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务类
 * Created by shaobo on 2018/3/26.
 */
@Component
public class InsertTableJob {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void work() throws Exception {
        System.out.println("每分钟执行一次："+new Date());
    }

    @Scheduled(fixedRate = 5000)//每5秒执行一次
    public void play() throws Exception {
        System.out.println("每五秒执行一次："+new Date());
    }
}
