/ **
    waitTime (5s totally) <<< leaseTime (30s per node)
    
 **/ 
public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
    long waitTime = -1;
    if (leaseTime == -1) { // 默认 waitTime 5
        waitTime = 5;
        unit = TimeUnit.SECONDS;
    } else {
		// 限制执行时长
        waitTime = unit.toMillis(leaseTime);　// 小于2000ms 
        if (waitTime <= 2000) {
            waitTime = 2000;
        } else if (waitTime <= 5000) { // 小于5000ms
            waitTime = ThreadLocalRandom.current().nextLong(waitTime/2, waitTime);
        } else { // 大于5000ms
            waitTime = ThreadLocalRandom.current().nextLong(5000, waitTime);
        }
        waitTime = unit.convert(waitTime, TimeUnit.MILLISECONDS);
    }
    
    while (true) {
        if (tryLock(waitTime, leaseTime, unit)) {
            return;
        }
    }
}

public boolean tryLock(long waitTime /* 执行时长 */, long leaseTime /* 加锁时长 */, TimeUnit unit) throws InterruptedException {
    long newLeaseTime = -1;
    if (leaseTime != -1) {
        newLeaseTime = waitTime*2; // 增大初次加锁 失效时长
    }
    
    long time = System.currentTimeMillis();
    long remainTime = -1; // 执行剩余时间
    if (waitTime != -1) {
        remainTime = unit.toMillis(waitTime);
    }
    int failedLocksLimit = failedLocksLimit(); // 最大失败锁数量
    List<RLock> lockedLocks = new ArrayList<RLock>(locks.size());
    for (ListIterator<RLock> iterator = locks.listIterator(); iterator.hasNext();) {
        RLock lock = iterator.next();
        boolean lockAcquired;
        try {
            if (waitTime == -1 && leaseTime == -1) {
                lockAcquired = lock.tryLock();
            } else {
                long awaitTime = unit.convert(remainTime, TimeUnit.MILLISECONDS);
                lockAcquired = lock.tryLock(awaitTime, newLeaseTime, unit); // 执行时长 节点加锁时长 
            }
        } catch (Exception e) {
            lockAcquired = false;
        }
        
        if (lockAcquired) { // 当前锁加锁成功
            lockedLocks.add(lock);
        } else { // 当前锁加锁失败
            if (locks.size() - lockedLocks.size() == failedLocksLimit()) {
                break;
            }

            if (failedLocksLimit == 0) {//超过最大加锁失败数量 全部解锁
                unlockInner(lockedLocks); 
                if (waitTime == -1 && leaseTime == -1) {
                    return false;
                }
                failedLocksLimit = failedLocksLimit();
                lockedLocks.clear();
                // reset iterator
                while (iterator.hasPrevious()) {
                    iterator.previous();
                }
            } else {
                failedLocksLimit--; // 减少计数
            }
        }
        
        if (remainTime != -1) { // 执行剩余时间
            remainTime -= (System.currentTimeMillis() - time);
            time = System.currentTimeMillis();
            if (remainTime <= 0) { // 执行加锁过程中已经超时（默认5s）
                unlockInner(lockedLocks);
                return false;
            }
        }
    }
    if (leaseTime != -1) {  // 获取锁成功，重新设置失效时长
        List<RFuture<Boolean>> futures = new ArrayList<RFuture<Boolean>>(lockedLocks.size());
        for (RLock rLock : lockedLocks) {
            RFuture<Boolean> future = rLock.expireAsync(unit.toMillis(leaseTime), TimeUnit.MILLISECONDS);
            futures.add(future); 
        }
        
        for (RFuture<Boolean> rFuture : futures) {
            rFuture.syncUninterruptibly();
        }
    }
    return true;
}
