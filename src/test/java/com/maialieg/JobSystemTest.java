package com.maialieg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JobSystemTest {
    JobManagementSystem managementSystem;
     EntityJob entityJob1;
     EntityJob entityJob2;
     EntityJob entityJob3;

    @BeforeEach
    void setUp() {
        entityJob1 = new EntityJob("entityJob1", JobPriority.LOWEST, new JobActionOne());
        entityJob2 = new EntityJob("entityJob2", JobPriority.HIGHEST, new JobActionOne());
        entityJob3 = new EntityJob("entityJob3", JobPriority.HIGHEST, new JobActionTwo());

        managementSystem = new JobManagementSystem();

        managementSystem.scheduleJob(entityJob1);
        managementSystem.scheduleJob(entityJob2);
        managementSystem.scheduleJob(entityJob3);
    }

    @Test
    public void whenAddingDiffrentJobs_thenTheJobWithCorrectPriorityIsPicked() {
        int jobPriority = managementSystem.getPriorityQueue().poll().getJobPriority();
        assertEquals(1,jobPriority);
    }
    @Test
    public void whenAddingDiffrentJobsAndExecutionIsDone_thenTheQueueShouldLeftEmpty() {
        managementSystem.execute();
        int queueSize = managementSystem.getPriorityQueue().size();
        assertEquals(0 , queueSize);
    }
    @Test
    public void whenAddingDiffrentJobsAndExecutionIsNotQueuedOrRunning_thenTheJobStateShouldBeSuccessOrFaild() throws InterruptedException {
        managementSystem.execute();
        Thread.sleep(1000);
        assertTrue(
                entityJob1.getJobState() == JobState.SUCCESS ||
                        entityJob1.getJobState() == JobState.FAILED,
                "State is success or failed"
        );
    }


    @Test
    public void whenAddingDiffrentJobsAndBeforeExecution_thenTheJobStateShouldBeQueued() {
        assertEquals(JobState.QUEUED, entityJob1.getJobState());
        assertEquals(JobState.QUEUED, entityJob2.getJobState());
        assertEquals(JobState.QUEUED, entityJob3.getJobState());
    }
}