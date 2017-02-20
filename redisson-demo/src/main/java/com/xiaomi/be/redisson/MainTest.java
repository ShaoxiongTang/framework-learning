package com.xiaomi.be.redisson;

public class MainTest {
    private static void redisLock() {
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String key = "test123";
                        DistributedRedisLock.acquire(key);
                        Thread.sleep(1000); //获得锁之后可以进行相应的处理
                        System.err.println("======获得锁后进行相应的操作======");
                        DistributedRedisLock.release(key);
                        System.err.println("=============================");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }

    public static void main(String[] args) {
        redisLock();
    }
}
