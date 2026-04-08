package com.maialieg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

public class JobManagementSystem {

    private static final Logger log = LoggerFactory.getLogger(JobManagementSystem.class);

    private static final int MAX_THREADS = 20;
    private static final int MAX_QUEUED = 20;

    private final ScheduledExecutorService scheduledExecutorService;
    private final ExecutorService priorityJobExecutor;
    private final PriorityBlockingQueue<EntityJob> priorityQueue;

    public JobManagementSystem() {
        scheduledExecutorService = Executors.newScheduledThreadPool(MAX_QUEUED);
        priorityJobExecutor = Executors.newFixedThreadPool(MAX_THREADS);
        priorityQueue = new PriorityBlockingQueue<>(MAX_QUEUED,
                (o1, o2) -> o1.getJobPriority().compareTo(o2.getJobPriority()));
    }

    public void execute() {
        log.info("---------------- EXECUTION STARTS ----------------");
        while (!priorityQueue.isEmpty()) {
            priorityJobExecutor.execute(priorityQueue.poll());
        }
    }

    public void scheduleJob(EntityJob job) {
        priorityQueue.add(job);
    }

    public void shutdown() {
        log.info("---------------- SHUTTING DOWN ----------------");
        priorityJobExecutor.shutdown();
        scheduledExecutorService.shutdown();
        try {
            if (!priorityJobExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                priorityJobExecutor.shutdownNow();
                log.warn("Executor forced shutdown after timeout");
            }
        } catch (InterruptedException e) {
            priorityJobExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("---------------- SHUTDOWN COMPLETE ----------------");
    }

    public PriorityBlockingQueue<EntityJob> getPriorityQueue() {
        return priorityQueue;
    }
}