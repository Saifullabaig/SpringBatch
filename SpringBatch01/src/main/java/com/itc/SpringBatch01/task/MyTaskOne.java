package com.itc.SpringBatch01.task;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class MyTaskOne implements Tasklet {

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
    {
        System.out.println("My TaskOne Start...");
        // ........code
        System.out.println("TaskOne Completed");
        return RepeatStatus.FINISHED;
    }
}
