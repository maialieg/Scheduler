package com.maialieg;//Any new job needed to be implemented, it should implement this interface
//Examples are, JobActionOne and JobActionTwo

@FunctionalInterface
public interface JobActionInterface {
    void execute() throws InterruptedException;
}