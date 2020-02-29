package com.optile;

import com.optile.JobActionInterface;

public class JobActionTwo implements JobActionInterface
{
    public void Action() throws InterruptedException {
            Thread.sleep(900); //simulate a task
    }
}
