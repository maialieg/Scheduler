package com.optile;

import com.optile.JobActionInterface;

public class JobActionOne implements JobActionInterface
{
    public void Action() throws InterruptedException {
            Thread.sleep(900); //simulate a task
    }
}
