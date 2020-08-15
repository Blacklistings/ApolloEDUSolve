package com.example.apolloedusolve;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    private static final ExecutorService executor;
    private Threads(){}

    static {
        executor = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable runnable){
        executor.execute(runnable);
    }

    public static void end(){
        executor.shutdown();
    }
}
