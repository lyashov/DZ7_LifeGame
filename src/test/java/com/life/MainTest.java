package com.life;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest extends Main {
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
        System.out.println(stopTime - startTime);

        startTime = System.currentTimeMillis();
        GameInThreads.playGame(isFile, inFile,  outFile, sizeOfPlace, lifeIteration);
        stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
    }

}
