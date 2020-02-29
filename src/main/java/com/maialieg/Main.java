package com.maialieg;

public class Main {

    public static void main(String[] args) {

        EntityJob entityJob1 = new EntityJob("entityJob1", JobPriority.LOWEST, new JobActionOne());
        EntityJob entityJob2 = new EntityJob("entityJob2", JobPriority.HIGHEST, new JobActionOne());
        EntityJob entityJob3 = new EntityJob("entityJob3", JobPriority.HIGHEST, new JobActionOne());
        EntityJob entityJob4 = new EntityJob("entityJob4", JobPriority.LOWEST, new JobActionTwo());
        EntityJob entityJob5 = new EntityJob("entityJob5", JobPriority.LOWEST, new JobActionOne());

        JobManagementSystem managementSystem = new JobManagementSystem();
        managementSystem.scheduleJob(entityJob1);
        managementSystem.scheduleJob(entityJob2);
        managementSystem.scheduleJob(entityJob3);
        managementSystem.scheduleJob(entityJob4);
        managementSystem.scheduleJob(entityJob5);

        managementSystem.execute();
    }
}