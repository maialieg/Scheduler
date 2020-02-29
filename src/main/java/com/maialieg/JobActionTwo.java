package com.maialieg;

public class JobActionTwo implements JobActionInterface
{
    public void execute() throws InterruptedException {
            Thread.sleep(900); //simulate a task
    }
}
