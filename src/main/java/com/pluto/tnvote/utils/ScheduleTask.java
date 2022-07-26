package com.pluto.tnvote.utils;

import com.pluto.tnvote.service.TbSurveyService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private TbSurveyService tbSurveyService;
    /**
     * 调查问卷状态的任务
     */
    @Scheduled(fixedRate=20000)
//    @Scheduled(cron = "* * */1 * * ?")
    public void state(){
        System.out.println("执行任务....");
        tbSurveyService.updateState();
    }

}
