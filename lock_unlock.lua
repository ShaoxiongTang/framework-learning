


# create lock or  rantreentlock
if (redis.call('exists', $uuid_tid) == 0) then
    redis.call('hset', $uuid_tid, $lock_name, 1); 
    redis.call('pexpire', $uuid_tid, $lease_time); 
    return nil; 
end; 
if (redis.call('hexists', $uuid_tid, $lock_name) == 1) then 
    redis.call('hincrby', $uuid_tid, $lock_name, 1); 
    redis.call('pexpire', $uuid_tid, $lease_time); 
    return nil; 
end; 
return redis.call('pttl', $uuid_tid);


# unlock   
if (redis.call('exists', $uuid_tid) == 0) then 
    redis.call('publish', $channelId, $publish_msg); 
    return 1; 
end;
if (redis.call('hexists', $uuid_tid, $lease_time) == 0) then 
    return nil;
end; 
local counter = redis.call('hincrby', $uuid_tid, $lock_name, -1); 
if (counter > 0) then 
    redis.call('pexpire', $uuid_tid , $lease_time); 
    return 0; 
else 
    redis.call('del', $uuid_tid); 
    redis.call('publish', $channelId, $publish_msg); 
    return 1;
end; 
return nil