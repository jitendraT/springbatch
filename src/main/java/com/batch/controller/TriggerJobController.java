package com.batch.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriggerJobController {

    private static final Log log = LogFactory.getLog(TriggerJobController.class);
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processJob;
    @GetMapping("/v1/invoke/job")
    public String handle() throws Exception {
        log.info("Received request to start batch job");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);
        return "Batch job has been invoked";
    }
}
