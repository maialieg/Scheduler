# Job Scheduler

A priority-based concurrent job scheduler built in Java.

## Overview
Implements a thread-safe job management system where jobs are executed
according to their priority using Java's concurrency utilities.

## Features
- Priority queue with custom comparator
- Thread pool execution via ExecutorService
- Job lifecycle management (QUEUED → RUNNING → SUCCESS/FAILED)
- Extensible job actions via interface
- Unit tested with JUnit 

## Tech Stack
Java · ExecutorService · PriorityBlockingQueue · JUnit 5

## Running
```bash
javac -cp . src/com/maialieg/*.java
java com.Main
```