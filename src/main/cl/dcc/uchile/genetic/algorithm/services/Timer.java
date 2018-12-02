package main.cl.dcc.uchile.genetic.algorithm.services;

public class Timer {
    private static long initTime;

    public static void start(){
        initTime = System.currentTimeMillis();
    }

    public static long end(){
        return System.currentTimeMillis() - initTime;
    }
}
