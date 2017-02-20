package com.xiaomi.be.redisson.obj;

import com.xiaomi.be.redisson.RedissonManager;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

public class ObjectDemo {

    public static void main(String[] args) {
        RedissonClient client = RedissonManager.getClient();
        RMap<String, Object> rMap = client.getMap("demoRMap");
        rMap.put("key1" , "key1.val");
        rMap.put("key2" , "key2.val");
        System.out.println(rMap.get("key1"));
        System.out.println(rMap);
    }
}
