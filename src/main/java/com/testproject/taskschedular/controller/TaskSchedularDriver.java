package com.testproject.taskschedular.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TaskSchedularDriver {

    @Autowired
    private TaskScheduler schedular;

    @Autowired
    private JobScheular jobSchedular;

    public void scheduleTask(MultipartFile file, String className, Date scheduleDate) {

        schedular.schedule(new Runnable() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                System.out.println("Job to be scheduled at: " + scheduleDate);
                jobSchedular.runJar("C:\\Users\\ali\\Desktop\\TestJar1.jar",   // path of any dummy file
                        className, file);

            }
        }, scheduleDate);

    }

}