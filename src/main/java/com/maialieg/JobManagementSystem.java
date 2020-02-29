package com.maialieg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.*;

public class JobManagementSystem
{
    private static final Logger log = LoggerFactory.getLogger(JobManagementSystem.class);

    //assume that we will have maximum 20threads scheduled and 20 jobs queued
    private static final int MAX_THREADS = 20;
    private static final int MAX_QUEUED = 20;

    /*TODO: I put the scheduledExecuterService here in case I need to use it,
            for now I scheduled it according to priority, and put the scheduledExecuterService
            as a reminder if I need to add time scheduling later.*/

    private ScheduledExecutorService scheduledExecutorService;
    private ExecutorService priorityJobExecutor;
    private PriorityBlockingQueue<EntityJob> priorityQueue;

    public JobManagementSystem()
    {
        scheduledExecutorService = Executors.newScheduledThreadPool(MAX_QUEUED);
        priorityJobExecutor =  Executors.newFixedThreadPool(MAX_THREADS);

        priorityQueue = new PriorityBlockingQueue<EntityJob>(MAX_QUEUED, new Comparator<EntityJob>() {
            @Override
            public int compare(EntityJob o1, EntityJob o2) {
                return Integer.compare(o2.getJobPriority(),o1.getJobPriority());
            }});
    }

    public void execute()
    {
        log.info("----------------EXECUTION STARTS HERE-------------------");
//        scheduledExecutorService.execute(() -> {
            while (priorityQueue.size() !=0)
                priorityJobExecutor.execute(priorityQueue.poll());
    //    });
    }
    public void scheduleJob(EntityJob job)
    {
        priorityQueue.add(job);
    }

    public PriorityBlockingQueue<EntityJob> getPriorityQueue()
    {
        return priorityQueue;
    }
}
