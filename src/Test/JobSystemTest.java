package Test;

import com.optile.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JobSystemTest {
    JobMangmentSystem mangmentSystem;
     EntityJob entityJob1;
     EntityJob entityJob2;
     EntityJob entityJob3;

    @BeforeEach
    void setUp() {
        entityJob1 = new EntityJob("entityJob1", JobPriority.LOWEST, new JobActionOne());
        entityJob2 = new EntityJob("entityJob2", JobPriority.HIGHEST, new JobActionOne());
        entityJob3 = new EntityJob("entityJob3", JobPriority.HIGHEST, new JobActionTwo());

        mangmentSystem = new JobMangmentSystem();

        mangmentSystem.scheduleJob(entityJob1);
        mangmentSystem.scheduleJob(entityJob2);
        mangmentSystem.scheduleJob(entityJob3);
    }

    @Test
    public void whenAddingDiffrentJobs_thenTheJobWithCorrectPriorityIsPicked() {
        int jobPriority = mangmentSystem.getPriorityQueue().poll().getJobPriority();
        assertEquals(1,jobPriority);
    }
    @Test
    public void whenAddingDiffrentJobsAndExecutionIsDone_thenTheQueueShouldLeftEmpty() {
        mangmentSystem.execute();
        int queueSize = mangmentSystem.getPriorityQueue().size();
        assertEquals(0 , queueSize);
    }
    @Test
    public void whenAddingDiffrentJobsAndExecutionIsNotQueuedOrRunning_thenTheJobStateShouldBeSuccessOrFaild() throws InterruptedException {
        mangmentSystem.execute();
        Thread.sleep(1000);
        Assert.assertTrue ("State is success or fail",
                entityJob1.getJobState() == JobState.SUCCESS || entityJob1.getJobState() == JobState.FAILED);
    }


    @Test
    public void whenAddingDiffrentJobsAndBeforeExecution_thenTheJobStateShouldBeQueued() {
        assertEquals(JobState.QUEUED, entityJob1.getJobState());
        assertEquals(JobState.QUEUED, entityJob2.getJobState());
        assertEquals(JobState.QUEUED, entityJob3.getJobState());
    }
}