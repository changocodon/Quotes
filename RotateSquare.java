/*
 * Created on: 2017/04/13
 * Author: sangtlai
 * Copyright (C) 2016-2017 SangLaiTan.
 */

package com.vn.entrypoint;

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
 * 2017/04/13    sangtlai.pq   New creation
 *         </pre>
 */

public class RotateMatrix {

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
     * The entry point execute application.
     * 
     * @param args
     * @author sangtlai
     */
    public static void main(String[] args) {

        putData = new Scanner(System.in);
        /* user fill size of square */
        putOneElement("size of square", 10, 7, squareSize);
        /* user fill number of commands and coordinates */
        putNumberOfCommands();
        /* user fill number of queries and values need to find */
        putNumberOfQueries();

        int[][] squareTemp = new int[squareSize][squareSize];
        int[][] rotatedSquare = new int[squareSize][squareSize];
        /*Fill data from 0 to square's size to the power 2 into matrix squareTemp*/
        prepareSquare(squareTemp);

        /*Clone square's data to rotateSquare*/
        rotatedSquare = cloneArray(squareTemp);

        /* rotate sub-square 90 degree clockwise base on number of coordinates */
        for (int i = 0; i < row.length; i++) {
            rotate90Degrees(squareTemp, rotatedSquare, row[i], column[i], diagonal[i]);
            squareTemp = cloneArray(rotatedSquare);
        }
        /* search the values and print value's position in console */
        for (int i = 0; i < valuesToFind.length; i++) {
            System.out.println(getIndexOf(rotatedSquare, valuesToFind[i]));
        }
        putData.close();
    }

    /**
     * This method require user fill data
     *
     * @param elementName name of data filled by user
     * @param base
     * @param exponent
     * @param dataInput is data filled by user
     * @author sangtlai.pq
     */
    public static int putOneElement(String elementName, int base, int exponent, int dataInput) {
        while (true) {
            /*
             * If user fill data isn't correct, inform user must fill data again
             */
            try {
                System.out.println("Please fill " + elementName + " :");
                dataInput = Integer.parseInt(putData.nextLine());
                checkDataLimit(dataInput, base, exponent);
                return dataInput;
            } catch (NumberFormatException | DataInvalidException e) {
                System.out.println(
                        "You fill " + elementName + " not correct(it must Integer, < " + base + "^" + exponent + ")!");
            }
        }
    }

    /**
     * This method require insert data munber of commands and cordinates of sub-square
     * @author sangtlai
     */
    public static void putNumberOfCommands() {
        int numberOfCommands = 0;
        numberOfCommands = putOneElement("number of commands", 10, 5, numberOfCommands);

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

            } catch (NumberFormatException e) {
                System.out.println(
                        "You fill coordinates not correct (it must be Integer) please fill follow format (row,column,diagonal): ");
            } catch (DataInvalidException e) {
                System.out.println(
                        "You fill coordinates not correct with contraints please fill follow format (row,column,diagonal): ");
            }

        }
    }

    /**
     * This method require user insert number of values need to find position
     * @author sangtlai
     */
    public static void putNumberOfQueries() {
        int numberOfQueries = 0;
        numberOfQueries = putOneElement("number of queries", 10, 5, numberOfQueries);
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
                System.out.println("You fill values need to find not correct(it must be Integer, <" + "squareSize"
                        + "^2) please fill again!");
            }

        }
    }

    /**
     * This method check the value must greater than 0 and less than a exponential number
     *
     * @param valueNeedToCheck 
     * @param base
     * @param exponent
     * @throws DataInvalidException when square's size greater than 10^7
     * @author sangtlai
     */
    public static void checkDataLimit(int valueNeedToCheck, int base, int exponent) throws DataInvalidException {

        if (valueNeedToCheck < 0 || valueNeedToCheck > Math.pow(base, exponent)) {
            throw new DataInvalidException();
        }
    }

    /**
     * This method check constraints of sub-square
     * @param subSquareCurrent position of sub-square currently
     * @throws DataInvalidException Exception occur when coordinates of sub-square incorrect with constraints
     * @author sangtlai
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
     * Clone the provided array.
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
     * @param square is 2d array
     * @author sangtlai
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
        /* printResult(square); */
    }

    /**
     * This method perform rotate 90 degree the sub-square
     * @param square is 2D array original
     * @param rotatedSquare is 2D array after rotate 90 degrees
     * @param a is the row
     * @param b is the column
     * @param c is the diagonal 
     * @author sangtlai
     */
    public static void rotate90Degrees(int[][] square, int[][] rotatedSquare, int a, int b, int d) {
        /*
         * Replace value of rotatedSquare matrix base on square matrix
         * Because the sub-square rotate 90 degree clockwise,
         * we have first row of sub-square of rotatedSquare matrix is corresponding with first
         * column of sub-square square matrix
         */
        int minColumn = b - 2;

        for (int i = a - 1; i < a + d; i++) {
            int maxRow = a + d;
            minColumn++;
            for (int j = b - 1; j < b + d; j++) {
                rotatedSquare[i][j] = square[maxRow - 1][minColumn];
                maxRow--;
            }
        }

        /*
         * System.out.println("The square Rotated");
         * printResult(rotatedSquare);
         */
    }

    /**
     * This method print 2D-array at console
     * @param array2D
     * @author sangtlai
     */
    public static void printResult(int[][] array2D) {

        /* Print 2D array to console */
        for (int[] arr : array2D) {
            System.out.println(Arrays.toString(arr));
        }

    }

    /**
     * This method search the value and return position of value
     * @param src is the array contain data after rotate
     * @param value is the value need to find position
     * @return position of the value need to find. if src don't contain value
     *         need to file this method will inform the value not found.
     * @author sangtlai
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
     * @author sangtlai
     *
     */
    public static class DataInvalidException extends Exception {

        private static final long serialVersionUID = 1L;

        /* When user fill data of sub-square incorrect with constraints exception will occur */
        public DataInvalidException() {
            super();
        }
    }
}
