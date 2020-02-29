package com.optile;

import java.util.*;
import java.util.concurrent.*;

public class JobMangmentSystem
{
    //assume that we will have maximum 20threads scheduled and 20 jobs queued
    private static final int MAX_THREADS = 20;
    private static final int MAX_QUEUED = 20;

    /*TODO: I put the scheduledExecuterService here in case we need to use it
       in the requirements, it was not clear if I should execute according priority or schedualed time
       so, I scheduled it according to priority, and put the scheduledExecuterService as a reminder
       if we need to add time scheduling later.*/

    private ScheduledExecutorService scheduledExecutorService;
    private ExecutorService priorityJobExecutor;
    private PriorityBlockingQueue<EntityJob> priorityQueue;

    public JobMangmentSystem()
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
        System.out.println("----------------EXECUTION STARTS HERE-------------------");
//        scheduledExecutorService.execute(() -> {
            while (priorityQueue.size() !=0)
                priorityJobExecutor.execute(priorityQueue.poll());
    //    });
    }
    public void scheduleJob(EntityJob job)
    {
        priorityQueue.add(job);
    }

    public static void main( String args [])
    {

        EntityJob entityJob1 = new EntityJob("entityJob1", JobPriority.LOWEST, new JobActionOne());
        EntityJob entityJob2 = new EntityJob("entityJob2", JobPriority.HIGHEST, new JobActionOne());
        EntityJob entityJob3 = new EntityJob("entityJob3", JobPriority.HIGHEST, new JobActionOne());
        EntityJob entityJob4 = new EntityJob("entityJob4", JobPriority.LOWEST, new JobActionTwo());
        EntityJob entityJob5 = new EntityJob("entityJob5", JobPriority.LOWEST, new JobActionOne());


        JobMangmentSystem mangmentSystem = new JobMangmentSystem();

        mangmentSystem.scheduleJob(entityJob1);
        mangmentSystem.scheduleJob(entityJob2);
        mangmentSystem.scheduleJob(entityJob3);
        mangmentSystem.scheduleJob(entityJob4);
        mangmentSystem.scheduleJob(entityJob5);

        mangmentSystem.execute();

    }
    public PriorityBlockingQueue<EntityJob> getPriorityQueue()
    {
        return priorityQueue;
    }
}
