package com.maialieg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityJob implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(EntityJob.class);
    private static final int DEFAULT_MAX_RETRIES = 3;

    private final String name;
    private final JobPriority priority;
    private final JobActionInterface jobAction;
    private final int maxRetries;
    private JobState jobState;

    public EntityJob(String jobName, JobPriority priority, JobActionInterface jobAction) {
        this(jobName, priority, jobAction, DEFAULT_MAX_RETRIES);
    }

    public EntityJob(String jobName, JobPriority priority, JobActionInterface jobAction, int maxRetries) {
        this.name = jobName;
        this.priority = priority;
        this.jobAction = jobAction;
        this.maxRetries = maxRetries;
        this.jobState = JobState.QUEUED;
        log.info("Job '{}' with priority {} is {}", name, priority, jobState);
    }

    public JobPriority getJobPriority() {
        return priority;
    }

    public JobState getJobState() {
        return jobState;
    }

    @Override
    public void run() {
        int attempts = 0;

        while (attempts <= maxRetries) {
            try {
                if (attempts == 0) {
                    jobState = JobState.RUNNING;
                    log.info("Job '{}' with priority {} is {}", name, priority, jobState);
                } else {
                    jobState = JobState.RETRYING;
                    log.info("Job '{}' retry attempt {}/{}", name, attempts, maxRetries);
                }

                jobAction.execute();

                jobState = JobState.SUCCESS;
                log.info("Job '{}' with priority {} is {}", name, priority, jobState);
                return;

            } catch (InterruptedException e) {
                attempts++;
                if (attempts > maxRetries) {
                    jobState = JobState.FAILED;
                    log.error("Job '{}' failed after {} retries", name, maxRetries);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}