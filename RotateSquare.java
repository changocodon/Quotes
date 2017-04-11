// package jp.co.atst.on.clm13.event;
/*
 * Created on: 2017/04/10
 * Author: sangtlai
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
    	Scanner putData = new Scanner(System.in);
    	System.out.print("Please import size of square: ");
    	squareSize =putData.nextInt();
//    	System.out.print("Please import number of commands: ");
//    	commands = importData.nextInt();
        Integer[][] square = new Integer[squareSize][squareSize];
        Integer[][] rotatedSquare = new Integer[squareSize][squareSize];

        int a =1,b=2,d=4;
        
        RotateSquare rotateSquare = new RotateSquare();
        
        rotateSquare.prepareSquare(square);
        // clone square's data to rotateSquare
        rotatedSquare = cloneArray(square);
        
        rotateSquare.rotate90Degrees(square, rotatedSquare,1,2,4);
        square = cloneArray(rotatedSquare);
        rotateSquare.rotate90Degrees(square, rotatedSquare,2,3,3);
        
        String indexOfValue1 = rotateSquare.getIndexOf(rotatedSquare, 11);
        String indexOfValue2 = rotateSquare.getIndexOf(rotatedSquare, 24);
        System.out.println(indexOfValue1);
        System.out.println(indexOfValue2);
        putData.close();
    }
    
    /**
     * Clones the provided array
     * 
     * @param square
     * @return a new clone of the provided array
     */
    public static Integer[][] cloneArray(Integer[][] src) {
        int length = src.length;
        Integer[][] target = new Integer[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);

        }
        return target;
    }
    /**
     * This method prepare data for 2D square array
     * @param square is 2d array
     */
    public void prepareSquare(Integer[][] square) {
        int count = 0;
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
    public void rotate90Degrees(Integer[][] square, Integer[][] rotatedSquare,int a, int b,int d) {
        int minColumn = b-2;
        for (int i = a-1; i < a+d; i++) {
            int maxRow = a+d;
            minColumn ++;
            for (int j = b-1; j < b+d; j++) {
                rotatedSquare[i][j] = square[maxRow-1][minColumn];
                maxRow--;
                
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
    
    public String getIndexOf(Integer[][] src, int value) {
        String valueNotFound = "Value not found";
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                if(src[i][j] == value){
                    return (i+1) + " "+ (j+1);
                }
            }
        }
        return valueNotFound;
    }
}
