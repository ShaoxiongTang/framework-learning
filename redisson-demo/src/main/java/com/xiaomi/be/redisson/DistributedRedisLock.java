package com.xiaomi.be.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DistributedRedisLock {
    private static RedissonClient client = RedissonManager.getClient();
    private static final String LOCK_TITLE = "redisLock_test123";

    public static void acquire(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock lock = client.getLock(key);
        lock.lock(2, TimeUnit.MINUTES);
        System.out.println("======lock======" + Thread.currentThread().getName());
    }

    public static void release(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock lock = client.getLock(key);
        lock.unlock();
        System.out.println("======unlock======" + Thread.currentThread().getName());
    }
}
