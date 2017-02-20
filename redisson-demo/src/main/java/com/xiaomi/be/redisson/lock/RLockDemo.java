package com.xiaomi.be.redisson.lock;

import com.xiaomi.be.redisson.RedissonManager;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RLockDemo {
    private static RedissonClient client = RedissonManager.getClient();

    public static void main(String[] args) throws InterruptedException {
        RLock lock = client.getLock("rlock1");
        lock.lock();
        lock.lock(10 , TimeUnit.SECONDS);

        boolean locked = lock.tryLock(200 , TimeUnit.MICROSECONDS);
        lock.unlock();
    }
}
