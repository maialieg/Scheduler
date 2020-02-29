package com.maialieg;

public class JobActionOne implements JobActionInterface
{
    public void execute() throws InterruptedException {
            Thread.sleep(900); //simulate a task
    }
}
