package com.life;

import java.io.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

class GameInThreads {
        private static int[][] initPlace(){
           int [][] place = {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
           return place;
        }

        private static void clearScreen(){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }

        static int getCountNeightborn(int [][]place, int x, int y){
            Neightborn[] near = new Neightborn[8];
            near[0] = new Neightborn(x - 1, y - 1);
            near[1] = new Neightborn(x - 1, y );
            near[2] = new Neightborn(x - 1, y + 1);
            near[3] = new Neightborn(x , y - 1);
            near[4] = new Neightborn(x , y + 1);
            near[5] = new Neightborn(x + 1, y - 1);
            near[6] = new Neightborn(x + 1, y );
            near[7] = new Neightborn(x + 1, y + 1);
            int count = 0;
            for (Neightborn neightborn: near) {
                try {
                    if (place[neightborn.getX()][neightborn.getY()] == 1) count++;
                }
                catch (ArrayIndexOutOfBoundsException e) {}
            }
            return count;
        }

        static void printState(int[][] place) {
            for (int[] i: place) {
                for (int j: i) {
                    if (j == 1) {
                        System.out.print((char)59193);
                    }
                    else {
                        System.out.print((char)59195);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

        private static int[][] initPlaceFromFile(String inFile, int sizeOfPlace) throws IOException {
            FileReader file= new FileReader(inFile);
            BufferedReader br = new BufferedReader(file);
            String line;
            int mas[][] = new int[sizeOfPlace][sizeOfPlace];
            int i = 0;
            while ((line = br.readLine()) != null){
                String[] str = line.split(" ");
                for (int j = 0; j < 10; j++) {
                    mas[i][j] = Integer.parseInt(str[j]);
                }
                i++;
            }
            return mas;
        }

        private static void saveGameResultToFile(String outFile, int[][] place) throws IOException {
            FileWriter file = new FileWriter(outFile);
            BufferedWriter bw = new BufferedWriter(file);
            for (int[] line : place) {
                for (int el : line) {
                    bw.write(el + " ");
                }
                bw.write('\n');
            }
            bw.close();
            file.close();
        }

    public static class MyRunnable implements Runnable {
        int i, j;
        int[][] board, place;
        public MyRunnable(int i, int j, int[][] board, int[][] place) {
            this.i = i;
            this.j = j;
            this.board = board;
            this.place = place;

        }

        public void run() {
            if (board[i][j] == 1 && !(getCountNeightborn(board, i, j) == 2 || getCountNeightborn(board, i, j) == 3)) {
                place[i][j] = 0;
            } else if (board[i][j] == 0 && getCountNeightborn(board, i, j) == 3) {
                place[i][j] = 1;
            }
        }
    }

        public static void main(String[] args) throws InterruptedException, IOException {
            long startTime = System.currentTimeMillis();
            String inFile;
            String outFile;
            int lifeIteration;
            int sizeOfPlace  = 10;
            int[][] place;

            if (args.length >= 3) {
                inFile = args[0];
                outFile = args[1];
                lifeIteration = Integer.parseInt(args[2]);
                place = initPlaceFromFile(inFile, sizeOfPlace);
            }
            else{
                inFile =  System.getProperty("user.dir").concat("/in.txt");
                outFile =  System.getProperty("user.dir").concat("/out.txt");
                lifeIteration = 100;
                place = initPlace();
            }
            int[][] board = new int[place.length][place[0].length];
            clearScreen();

            for (int gen = 0; gen < lifeIteration; gen++) {
                for (int i = 0; i < place.length; i++) {
                    for (int j = 0; j < place[i].length; j++) {
                        board[i][j] = place[i][j];
                    }
                }
                printState(board);
                TimeUnit.MILLISECONDS.sleep(500);
                clearScreen();

                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        MyRunnable myRunnable = new MyRunnable(i, j, board, place);
                        Thread thread = new Thread(myRunnable);
                        thread.start();
                        thread.join();

                    }
                }

            }
            saveGameResultToFile(outFile, place);
            long stopTime = System.currentTimeMillis();
            System.out.println(stopTime - startTime);
        }
    }


