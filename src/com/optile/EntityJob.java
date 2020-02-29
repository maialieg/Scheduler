package com.optile;

public class EntityJob implements Runnable {

    private  String name;
    private JobPriority priority;
    private JobState jobState;
    private JobActionInterface jobAction;

    public EntityJob(String jobName, JobPriority priority, JobActionInterface jobAction) {
        this.name = jobName;
        this.priority = priority;
        this.jobState = JobState.QUEUED;
        this.jobAction = jobAction;
        System.out.println("job: " + name + " With Priority: "+ priority + " is currently "+ jobState.toString());
    }

    public int getJobPriority() {
        return priority.ordinal();
    }

    public JobState getJobState() {
        return jobState;
    }

    @Override
    public void run() {
        try
        {
            jobState = JobState.RUNNING;
            jobAction.Action();
            System.out.println("job: " + name + " With Priority: "+ priority  + " is currently "+ "\033[1m"+jobState.toString()+"\033[0m");
        }
        catch (InterruptedException e)
        {
            jobState = JobState.FAILED;
            System.out.println("job: " + name +" With Priority: "+ priority +" is currently "+ jobState.toString());
            e.printStackTrace();
        }
        jobState = JobState.SUCCESS;
        System.out.println("job: " + name + " With Priority: "+ priority +" is currently "+jobState.toString());
    }

}
