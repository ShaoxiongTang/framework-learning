package com.xiaomi.be.redisson.lock;

import com.google.common.collect.ImmutableMap;
import com.xiaomi.be.redisson.RedissonManager;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedLockDemo {
    private static final String ACCOUNT_PREFIX = "acc_";
    private static RedissonClient client = RedissonManager.getClient();
    private static Map<String, Integer> balanceMap = new HashMap<String, Integer>();
    static {
        balanceMap.put( "Jack", 100);
        balanceMap.put( "David", 50);
    }

    public static void main(String[] args) throws InterruptedException {
        transfer("Jack", "David", 10);
    }

    public static void transfer(String fromAccountId, String toAccountId, Integer amount) throws InterruptedException {
        RLock fromLock = client.getLock(ACCOUNT_PREFIX + fromAccountId);
        RLock toLock = client.getLock(ACCOUNT_PREFIX + toAccountId);
        RLock trxLock = client.getLock(ACCOUNT_PREFIX + "trx");
        RedissonRedLock redLock = new RedissonRedLock(fromLock, toLock, trxLock);
        redLock.lock(100000 , TimeUnit.SECONDS);
        try {
            Integer fromBalance = balanceMap.get(fromAccountId);
            if (fromBalance < amount) {
                throw new IllegalStateException("balance not enough");
            }
            balanceMap.put(fromAccountId, fromBalance - amount);
            balanceMap.put(toAccountId, balanceMap.get(toAccountId) + amount);
        } finally {
            redLock.unlock();
        }
    }
}
