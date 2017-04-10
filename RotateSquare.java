/*
 * Created on: 2017/04/10
 * Author: sangtlai
 *
 * Copyright (C) 2016-2017 SangLaiTan.
 */

import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>Class name : RotateSquare.</p>
 * <p>Outline    : Description.</p>
 *
 * @author sangtlai
 * <pre>
 * Change history
 * ----------------------------------------------------
 * Changed date  Author        Changed content
 * 2017/04/10    sangtlai.pq   New creation
 * </pre>
 */

public class RotateSquare {
	/**
	 * n is Size of the square 
	 */
	private int sizeOfS;
	
	/**
	 * squareSize is Size of the square 
	 */
	private static int squareSize;
	/**
	 * squareSize is Size of the square 
	 */
	private static int commands;
    /**
     * The entry point execute.
     *
     * @param args
     * @author sangtlai.pq
     */
    public static void main(String[] args) {
    	Scanner importData = new Scanner(System.in);
    	System.out.print("Please import size of square: ");
    	squareSize =importData.nextInt();
    	System.out.print("Please import number of commands: ");
    	commands = importData.nextInt();
        Integer[][] square = new Integer[squareSize][squareSize];
        Integer[][] rotatedSquare = new Integer[squareSize][squareSize];
        RotateSquare rotateSquare = new RotateSquare();
        rotateSquare.prepareSquare(square);
        rotateSquare.rotate90Degrees(square, rotatedSquare);
        
        importData.close();
    }
    

    /**
     * This method prepare data for 2D square array
     * @param square is 2d array
     */
    public void prepareSquare(Integer[][] square) {
        int count = 1;
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square.length; j++) {
                square[i][j] = count;
                count++;
            }
        }
        printResult(square);
    }

    /**
     * This method perform rotate 90 degrees the sub-square
     * @param square is 2D array original
     * @param rotatedSquare is 2D array after rotate 90 degrees
     */
    public void rotate90Degrees(Integer[][] square, Integer[][] rotatedSquare) {
        for (int i = 0; i < rotatedSquare.length; i++) {
            for (int j = 0; j < rotatedSquare.length; j++) {
                rotatedSquare[i][j] = square[square.length - j - 1][i];
                System.out.println(square[square.length - j - 1][i]);
            }
        }
        System.out.println("The square Rotated");
        printResult(rotatedSquare);
    }

    /**
     * This method print 2D-array at console
     * @param array2D
     */
    public static void printResult(Integer[][] array2D) {
        for (Integer[] arr : array2D) {
            System.out.println(Arrays.toString(arr));
        }
    }
}
