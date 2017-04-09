package com.xiaomi.be;

import java.util.Arrays;

public class LISProblem {
    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 4, 8, 6, 7};
        int[][] count = new int[arr.length][arr.length];
//        for (int i = 0; i < arr.length + 1; i++) {
//            count[0][i] = 1;
//            count[i][0] = 1;
//        }
        System.out.println(buildCount(count, arr));
    }

    private static int buildCount(int[][] count, int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                for (int k = i -1; k >= 0; k--) {
                    if (arr[k] <= arr[i]) {
                        if (count[i][j] > max) {
                            max = count[i][j];
                        }
                        break;
                    }
                }
                if (count[i][j] == 0) {
                    count[i][j] = 1;
                }
            }
        }
        System.out.println(Arrays.deepToString(count));
        return max;
    }
}
