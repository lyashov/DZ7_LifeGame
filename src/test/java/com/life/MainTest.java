package com.life;

import org.junit.Test;

import java.io.IOException;

public class MainTest {
    @Test
    public void test() throws IOException, InterruptedException {
        String inFile =  System.getProperty("user.dir").concat("/in.txt");
        String outFile =  System.getProperty("user.dir").concat("/out.txt");
        int sizeOfPlace  = 10;
        int lifeIteration = 30;
        boolean isFile = false;

        long startTime = System.currentTimeMillis();
        Game.playGame(isFile, inFile,  outFile, sizeOfPlace, lifeIteration);
        long stopTime = System.currentTimeMillis();

        long startTimeThreads = System.currentTimeMillis();
        GameInThreads.playGame(isFile, inFile,  outFile, sizeOfPlace, lifeIteration);
        long stopTimeThreads = System.currentTimeMillis();
        System.out.println("Game in main-thread " + (stopTime - startTime));
        System.out.println("Game with threads " + (stopTimeThreads - startTimeThreads));
    }

}
