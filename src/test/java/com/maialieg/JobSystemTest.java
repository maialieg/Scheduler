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
    public void whenAddingDifferentJobs_thenJobWithHighestPriorityIsPickedFirst() {
        EntityJob firstJob = managementSystem.getPriorityQueue().poll();
        assertEquals(JobPriority.HIGHEST, firstJob.getJobPriority());
    }

    @Test
    public void whenExecutionIsDone_thenQueueShouldBeEmpty() {
        managementSystem.execute();
        assertEquals(0, managementSystem.getPriorityQueue().size());
    }

    @Test
    public void whenExecutionCompletes_thenJobStateShouldBeSuccessOrFailed() throws InterruptedException {
        managementSystem.execute();
        Thread.sleep(3000);
        assertTrue(
                entityJob1.getJobState() == JobState.SUCCESS ||
                        entityJob1.getJobState() == JobState.FAILED,
                "State should be SUCCESS or FAILED"
        );
    }

    @Test
    public void beforeExecution_thenJobStateShouldBeQueued() {
        assertEquals(JobState.QUEUED, entityJob1.getJobState());
        assertEquals(JobState.QUEUED, entityJob2.getJobState());
        assertEquals(JobState.QUEUED, entityJob3.getJobState());
    }

    @Test
    public void whenJobCreatedWithCustomRetries_thenJobIsQueued() {
        EntityJob retryJob = new EntityJob("retryJob", JobPriority.HIGHEST, new JobActionOne(), 5);
        assertEquals(JobState.QUEUED, retryJob.getJobState());
    }

    @Test
    public void whenShutdownCalled_thenQueueRemainsAccessible() {
        managementSystem.execute();
        managementSystem.shutdown();
        assertNotNull(managementSystem.getPriorityQueue());
    }
}