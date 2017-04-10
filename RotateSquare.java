/*
 * Created on: 2017/04/10
 * Author: sangtlai.pq
 *
 * Copyright (C) 2008-2015 Hitachi.
 * CONFIDENTIAL Proprietary to Hitachi.
 */

package jp.co.atst.on.clm13.event;

import java.util.Arrays;

/**
 * <p>Class name : RotateSquare.</p>
 * <p>Outline    : Description.</p>
 *
 * @author GCS sangtlai.pq
 * <pre>
 * Change history
 * ----------------------------------------------------
 * Changed date  Author        Changed content
 * 2017/04/10    GCS sangtlai.pq   New creation
 * </pre>
 */

public class RotateSquare {

    /**
     * Method description
     *
     * @param args
     * @author sangtlai.pq
     */
    public static void main(String[] args) {
        int[][] square = new int[3][3];
        int[][] rotatedSquare = new int[3][3];
        RotateSquare rotateSquare = new RotateSquare();
        rotateSquare.prepareSquare(square);
        rotateSquare.rotate90Degree(square, rotatedSquare);
    }

    public void prepareSquare(int[][] square) {
        int dem = 0;
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square.length; j++) {
                square[j][i] = dem;
                dem++;
            }
        }
        printResult(square);
    }

    public void rotate90Degree(int[][] square, int[][] rotatedSquare) {
        for (int j = 0; j < square.length; j++) {
            for (int i = 0; i < square.length; i++) {
                rotatedSquare[i][j] = square[j][square.length - i - 1];
            }
        }
        System.out.println("The square Rotated");
        printResult(rotatedSquare);
    }

    public static void printResult(int[][] arrayDimen) {
        for (int[] arr : arrayDimen) {
            System.out.println(Arrays.toString(arr));
        }
    }
}
