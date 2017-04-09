package com.xiaomi.be;

import java.util.HashSet;
import java.util.Set;

public class CoinProblem {
    public static void main(String[] args) {
        Set<Integer> coins = new HashSet<Integer>();
        coins.add(1);
        coins.add(3);
        coins.add(5);
        System.out.println(calculateMinCoin(5, coins));
    }

    private static int calculateMinCoin(int target, Set<Integer> coins) {
        if (target == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (Integer coin : coins) {
            if (coin <= target) {
                min = Math.min(calculateMinCoin(target - coin, coins) + 1, min);
            }
        }
        return min;
    }
}
