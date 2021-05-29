package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /*Global variable is used to trace back the pointer*/

    static int count = 0;

    public static void main(String[] args) throws IOException {

        double score = 0;

        // read input file
        BufferedReader input = new BufferedReader(new FileReader("test2.seq"));
        String str;
        List<String> list = new ArrayList<String>();
        while ((str = input.readLine()) != null) {
            list.add(str);
        }

        String[] stringArr = list.toArray(new String[0]);

        String firstS = stringArr[0];
        String secondS = stringArr[1];

        int firstLen = firstS.length();
        int secondLen = secondS.length();

        int[][] arr1 = new int[firstLen + 1][secondLen + 1];
        int[][] arr2 = new int[firstLen + 1][secondLen + 1];


        // matrixs' initialization
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < secondLen + 1; j++) {
                arr1[i][j] = -1 * j;    // for new gap
                arr2[i][j] = 1;         // for backtracking
            }
        }

        for (int j = 0; j < 1; j++) {
            for (int i = 0; i < firstLen + 1; i++) {
                arr1[i][j] = -1 * i;
                arr2[i][j] = 1;
            }
        }

       /*
       Match score = 2
       Mismatch score = -1
       Open gap = -1
        */
        for (int i = 1; i < firstLen + 1; i++) {
            for (int j = 1; j < secondLen + 1; j++) {
                int s1 = -1;
                int s2 ;
                int s3 = -1;

                // if sequences matches
                if (firstS.charAt(i - 1) == secondS.charAt(j - 1)) {
                    s2 = 2;
                } else {
                    s2 = -1;
                }

                arr1[i][j] = optimumAlignment(arr1[i - 1][j] + s1, arr1[i - 1][j - 1] + s2, arr1[i][j - 1] + s3);
                arr2[i][j] = count;     // bu array geri dönüşte hangi yolu seçceğimizi söylüyor
            }
            score = arr1[firstLen][secondLen];  // final score
        }

        System.out.print("First sequence:   ");
        String result = backtracking(firstS, secondS, arr2);
        for (int i = 0; i < result.length(); i++) {
            System.out.print(result.charAt(i) + " ");
        }

        System.out.print("\nSecond sequence:  ");
        for (int i = 0; i < secondS.length(); i++) {
            System.out.print(secondS.charAt(i) + " ");
        }

        System.out.println("\n._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._");
        System.out.println("Final score is " + score);
        System.out.println("._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._");

        System.out.println("Score matrix:   ");
        for (int i = 0; i < firstLen + 1; i++) {
            for (int j = 0; j < secondLen + 1; j++) {
                System.out.printf("%4d", arr1[i][j]);
                if (j != 0 && j % secondLen == 0) {
                    System.out.println();
                }
            }
        }
    }

    /*How to find the maximum value*/
    public static int optimumAlignment(int mismatch, int match, int gap) {
        int maxScore = mismatch;
        count = 1;

        if (mismatch < match) {
            maxScore = match;
            count = 2;

            if (match < gap) {
                maxScore = gap;
                count = 3;
            }

        } else if (mismatch < gap) {
            maxScore = gap;
            count = 3;
        }

        if (maxScore == mismatch && maxScore == match) {
            count = 4;

        } else if (maxScore == mismatch && maxScore == gap) {
            count = 5;

        } else if (maxScore == match && maxScore == gap) {
            count = 6;
        }

        if (maxScore == mismatch && maxScore == match && maxScore == gap) {
            count = 7;
        }
        return maxScore;
    }

    // Backtracking
    public static String backtracking(String firstS, String secondS, int[][] arr2) {

        int f = firstS.length();
        int s = secondS.length();

        StringBuilder resultStr = new StringBuilder();

        while (f > 0 && s > 0) {
            int index = arr2[f][s];
            if (index == 1 || index == 5) {
                resultStr.insert(0, firstS.charAt(f - 1));
                f = f - 1;
            } else if (index == 2 || index == 4 || index == 6 || index == 7) {
                resultStr.insert(0, firstS.charAt(f - 1));
                f = f - 1;
                s = s - 1;
            } else {
                resultStr.insert(0, '_');
                s = s - 1;
            }
        }
        return resultStr.toString();
    }
}