package com.life;
import java.io.*;
import java.util.concurrent.*;

class Game {
    /**
     * init a default state of place
     * @return
     */
    private static int[][] initPlace() {
        int[][] place = {
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

    /**
     * cls screen
     */
    private static void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * if coordinate < 0 then coordinate is end of place
     * @param coord
     * @param sizeOfLife
     * @return
     */
    private static int lockLife(int coord, int sizeOfLife) {
        if (coord > sizeOfLife - 1) {
            return 0;
        }
        if (coord < 0){
            return sizeOfLife - 1;
        }
        return coord;

    }

    /**
     * Get count life's neighborns
     * @param place
     * @param x
     * @param y
     * @return
     */
    static int getCountNeightborn(int[][] place, int x, int y) {
        int sizeOfLife = place.length + 1;
        Neightborn[] near = new Neightborn[8];
        near[0] = new Neightborn(lockLife(x - 1, sizeOfLife - 1), lockLife(y - 1, sizeOfLife - 1));
        near[1] = new Neightborn(lockLife(x - 1, sizeOfLife - 1), lockLife(y, sizeOfLife - 1));
        near[2] = new Neightborn(lockLife(x - 1, sizeOfLife - 1), lockLife(y + 1, sizeOfLife - 1));
        near[3] = new Neightborn(lockLife(x, sizeOfLife - 1), lockLife(y - 1, sizeOfLife - 1));
        near[4] = new Neightborn(lockLife(x, sizeOfLife - 1), lockLife(y + 1, sizeOfLife - 1));
        near[5] = new Neightborn(lockLife(x + 1, sizeOfLife - 1), lockLife(y - 1, sizeOfLife - 1));
        near[6] = new Neightborn(lockLife(x + 1, sizeOfLife - 1), lockLife(y, sizeOfLife - 1));
        near[7] = new Neightborn(lockLife(x + 1, sizeOfLife - 1), lockLife(y + 1, sizeOfLife - 1));
        int count = 0;
        for (Neightborn neightborn : near) {
            try {
                if (place[neightborn.getX()][neightborn.getY()] == 1) count++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        return count;
    }

    /**
     * print one circle of life
     * @param place
     */
    static void printState(int[][] place) {
        for (int[] i : place) {
            for (int j : i) {
                if (j == 1) {
                    System.out.print((char) 59193);
                } else {
                    System.out.print((char) 59195);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * read
     * @param inFile
     * @param sizeOfPlace
     * @return
     * @throws IOException
     */
    private static int[][] initPlaceFromFile(String inFile, int sizeOfPlace) throws IOException {
        FileReader file = new FileReader(inFile);
        BufferedReader br = new BufferedReader(file);
        String line;
        int mas[][] = new int[sizeOfPlace][sizeOfPlace];
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] str = line.split(" ");
            for (int j = 0; j < 10; j++) {
                mas[i][j] = Integer.parseInt(str[j]);
            }
            i++;
        }
        return mas;
    }

    /**
     * save result to file
     * @param outFile
     * @param place
     * @throws IOException
     */
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

    /**
     * play game without multi
     * @param isFile
     * @param inFile
     * @param outFile
     * @param sizeOfPlace
     * @param lifeIteration
     * @throws IOException
     * @throws InterruptedException
     */
    public static void playGame(boolean isFile, String inFile, String outFile, int sizeOfPlace, int lifeIteration) throws IOException, InterruptedException {
        int[][] place;

        if (isFile) place = initPlaceFromFile(inFile, sizeOfPlace);
        else place = initPlace();

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
                    if (board[i][j] == 1 && !(getCountNeightborn(board, i, j) == 2 || getCountNeightborn(board, i, j) == 3)) {
                        place[i][j] = 0;
                    } else if (board[i][j] == 0 && getCountNeightborn(board, i, j) == 3) {
                        place[i][j] = 1;
                    }
                }
            }

        }
        saveGameResultToFile(outFile, place);
    }




        public static void main(String[] args) throws InterruptedException, IOException {
            long startTime = System.currentTimeMillis();

            String inFile;
            String outFile;
            int lifeIteration;
            int sizeOfPlace  = 10;
            boolean isFile;

            if (args.length >= 3) {
                inFile = args[0];
                outFile = args[1];
                lifeIteration = Integer.parseInt(args[2]);
                isFile = true;
            }
            else{
                inFile =  System.getProperty("user.dir").concat("/in.txt");
                outFile =  System.getProperty("user.dir").concat("/out.txt");
                lifeIteration = 30;
                isFile = false;
            }

            playGame(isFile, inFile,  outFile, sizeOfPlace, lifeIteration);

            long stopTime = System.currentTimeMillis();
            System.out.println(stopTime - startTime);
        }

    }


