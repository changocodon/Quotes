package jp.co.atst.on.clm13.event;

/*
 * Created on: 2017/04/10
 * Author: sangtlai
 * Copyright (C) 2016-2017 SangLaiTan.
 */

import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>
 * * Class name : RotateSquare. *
 * </p>
 * <p>
 * * Outline : Description. *
 * </p>
 *
 * @author sangtlai
 * 
 *         <pre>
 * Change history
 * ----------------------------------------------------
 * Changed date  Author        Changed content
 * 2017/04/10    sangtlai.pq   New creation
 *         </pre>
 */

public class RotateSquare {

    /**
     * Array store values of row of sub-square
     */
    private static int[] row;
    /**
     * Array store values of column of sub-square
     */
    private static int[] column;

    /**
     * Array store values of diagonal of sub-square
     */
    private static int[] diagonal;

    /**
     * Array store values need to find of sub-square
     */
    private static int[] valuesToFind;

    /**
     * A simple text scanner which can parse primitive types and strings using regular expressions. 
     */
    private static Scanner putData;

    /**
     * squareSize is size of the square
     */
    private static int squareSize;
    /**
     * numberOfCommands is number of commands
     */
    private static int numberOfCommands;
    /**
     * numberOfQueries is number of queries
     */
    private static int numberOfQueries;

    /**
     * The entry point execute application.
     * 
     * @param args
     * @author sangtlai.pq
     */
    public static void main(String[] args) {
        putData = new Scanner(System.in);

        putSizeOfSquare();
        putNumberOfCommands();
        putNumberOfQueries();

        int[][] squareTemp = new int[squareSize][squareSize];
        int[][] rotatedSquare = new int[squareSize][squareSize];

        prepareSquare(squareTemp);

        // clone square's data to rotateSquare
        rotatedSquare = cloneArray(squareTemp);

        for (int i = 0; i < row.length; i++) {
            rotate90Degrees(squareTemp, rotatedSquare, row[i], column[i], diagonal[i]);
            squareTemp = cloneArray(rotatedSquare);
        }
        for (int i = 0; i < valuesToFind.length; i++) {
            System.out.println(getIndexOf(rotatedSquare, valuesToFind[i]));
        }
        putData.close();
    }

    /**
     * this method require insert data of size of square
     */
    public static void putSizeOfSquare() {

        while (true) {
            /*
             * if user fill data isn't correct, inform user must fill data again
             */
            try {
                System.out.println("Please fill size of square: ");
                squareSize = Integer.parseInt(putData.nextLine());
                checkDataLimit(squareSize,10,7);
                break;
            } catch (NumberFormatException | DataInvalidException e) {
                System.out.println("You fill size of square not correct!");
            }
        }
    }

    
    public static void putNumberOfCommands() {
        while (true) {
            /*
             * If user fill data isn't correct, inform user must fill data again
             */
            try {
                /* Store data number of commands */
                System.out.println("Please number of commands: ");
                numberOfCommands = Integer.parseInt(putData.nextLine());
                
                checkDataLimit(numberOfCommands,10,5);
                
                break;
            } catch (NumberFormatException | DataInvalidException e) {
                System.out.println("You fill number of commands not correct!");
            }
        }

        /* Declare 3 array to store coordinates of sub-square */
        row = new int[numberOfCommands];
        column = new int[numberOfCommands];
        diagonal = new int[numberOfCommands];

        int countNumberOfCommands = 0;
        System.out.println("Please fill coordinates: ");

        /* Store coordinates of sub-square to array */
        while (countNumberOfCommands < numberOfCommands) {
            /*
             * If user fill data isn't correct, inform user must fill data again
             */
            try {

                row[countNumberOfCommands] = Integer.parseInt(putData.nextLine());
                column[countNumberOfCommands] = Integer.parseInt(putData.nextLine());
                diagonal[countNumberOfCommands] = Integer.parseInt(putData.nextLine());

                /*
                 * Check constraint when user filled data of less two sub-square
                 */
                if (countNumberOfCommands >= 1)
                    checkConstraints(countNumberOfCommands);

                countNumberOfCommands++;

            } catch (NumberFormatException | DataInvalidException e) {
                System.out.println("You fill coordinates not correct please fill follow format (row,column,diagonal): ");
            }

        }
    }

    /**
     * This method require user insert number of values need to find position
     */
    public static void putNumberOfQueries() {
        while (true) {
            /*
             * If user fill data isn't correct, inform user must fill data again
             */
            try {
                /* Store data number of queries */
                System.out.println("Please fill number of queries: ");
                numberOfQueries = Integer.parseInt(putData.nextLine());
                checkDataLimit(numberOfQueries, 10, 5);
                break;
            } catch (NumberFormatException | DataInvalidException e) {
                System.out.println("You fill number of queries not correct!");
            }
        }

        /* Declare a array to store the values need to find */
        valuesToFind = new int[numberOfQueries];
        int countNumberOfValues = 0;

        /* Store the values need to find to array */
        System.out.println("Please fill values need to find: ");
        while (countNumberOfValues < numberOfQueries) {
            /*
             * If user fill data isn't correct, inform user must fill data again
             */
            try {

                valuesToFind[countNumberOfValues] = Integer.parseInt(putData.nextLine());
                checkDataLimit(valuesToFind[countNumberOfValues], squareSize, 2);
                countNumberOfValues++;

            } catch (NumberFormatException | DataInvalidException e) {
                System.out.println("You fill values need to find not correct please fill again!");
            }

        }
    }
    /**
     * This method check the value must greater than 0 and less than a exponential number
     * @throws DataInvalidException when square's size greater than 10^7
     */
    public static void checkDataLimit(int valueNeedToCheck, int base ,int exponent) throws DataInvalidException{

        if(valueNeedToCheck < 0 || valueNeedToCheck > Math.pow(base, exponent)){
            throw new DataInvalidException();
        }
    }
    


    /**
     * This method check constraints of sub-square
     * @param subSquareCurrent position of sub-square currently
     * @throws DataInvalidException Exception occur when coordinates of sub-square incorrect with constraints
     */
    public static void checkConstraints(int subSquareCurrent) throws DataInvalidException {
        /*
         * If coordinates of subSquareCurrent correct with constraints as below,
         * exception will occur
         */
        /*
         * First constraint is coordinate of top-left corner of sub-square must
         * greater than 0 and 0 <= value of diagonal < size of square
         */
        if (row[subSquareCurrent] < 1 || column[subSquareCurrent] < 1 || squareSize < diagonal[subSquareCurrent]
                || 0 > diagonal[subSquareCurrent]) {
            throw new DataInvalidException();
        }
        /*
         * Next constraint is coordinate of top-left corner of sub-square
         * current must less than before sub-square.
         */
        else if (row[subSquareCurrent - 1] > row[subSquareCurrent] || row[subSquareCurrent]
                + diagonal[subSquareCurrent] > row[subSquareCurrent - 1] + diagonal[subSquareCurrent - 1]) {
            throw new DataInvalidException();
        } else if (column[subSquareCurrent - 1] > column[subSquareCurrent] || column[subSquareCurrent]
                + diagonal[subSquareCurrent] > column[subSquareCurrent - 1] + diagonal[subSquareCurrent - 1]) {
            throw new DataInvalidException();
        }
    }

    /**
     * Clone the provided array
     * 
     * @param src is array to specify matrix square
     * @return target is a new clone of the provided array
     */
    public static int[][] cloneArray(int[][] src) {
        int length = src.length;
        /* Clone data of src to target array */
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);

        }
        return target;
    }

    /**
     * This method prepare data for 2D square array
     * 
     * @param square is 2d array
     */
    public static void prepareSquare(int[][] square) {
        /* Store incremental value to each element of array */
        int count = 0;
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square.length; j++) {
                square[i][j] = count;
                count++;
            }
        }
                /*printResult(square);*/
    }

    /**
     * This method perform rotate 90 degrees the sub-square
     * 
     * @param square is 2D array original
     * @param rotatedSquare is 2D array after rotate 90 degrees
     */
    public static void rotate90Degrees(int[][] square, int[][] rotatedSquare, int a, int b, int d) {
        int minColumn = b - 2;
        
        for (int i = a - 1; i < a + d; i++) {
            int maxRow = a + d;
            minColumn++;
            for (int j = b - 1; j < b + d; j++) {
                rotatedSquare[i][j] = square[maxRow - 1][minColumn];
                maxRow--;
            }
        }

        /*      System.out.println("The square Rotated");
                printResult(rotatedSquare);*/
    }

    /**
     * This method print 2D-array at console
     * 
     * @param array2D
     */
    public static void printResult(int[][] array2D) {
        
        /* Print 2D array to console */
        for (int[] arr : array2D) {
            System.out.println(Arrays.toString(arr));
        }
        
    }

    /**
     * @param src is the array contain data after rotate
     * @param value is the value need to find position
     * @return position of the value need to find. if src don't contain value
     *         need to file this method will inform the value not found.
     */
    public static String getIndexOf(int[][] src, int value) {
        String valueNotFound = "Value not found";
        
        /* Loop array and check the value of each element */
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                if (src[i][j] == value) {
                    return (i + 1) + " " + (j + 1);
                }
            }
        }
        return valueNotFound;
    }

    /**
     * This class is custom exception
     * @author sanglt
     *
     */
    public static class DataInvalidException extends Exception {

        private static final long serialVersionUID = 1L;

        /*When user fill data of sub-square incorrect with constraints exception will occur*/
        public DataInvalidException() {
            super();
        }
    }
}
