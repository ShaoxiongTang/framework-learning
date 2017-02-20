package com.xiaomi.be.redisson;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.connection.balancer.RandomLoadBalancer;

import java.io.IOException;

public class RedissonManager {

    private static volatile RedissonClient client = null;

    public static RedissonClient getClient() {
        if (client == null) {
            synchronized (RedissonManager.class) {
                if (client == null) {
                    try {
                        init();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return client;
    }

    public static void init() throws IOException, ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./redisson-cluster.properties");
        String masterName = config.getString("redis.masterName");
        String masterAddress = config.getString("redis.masteAddress");
        int deployment_model = config.getInt("redis.deploymentModel");
        String hosts = config.getString("redis.hosts");

        //redis部署模式 SingleHost 1 MasterSlave 2 Sentinel 3  Cluster 4
        switch (deployment_model) {
            case 1:
                client = getSingleClient(hosts);//单机
                break;
            case 2:
                client = getMasterSlaveClient(masterAddress, hosts);
                break;
            case 3:
                client = getSentinelClient(masterName, hosts);
                break;
            case 4:
                client = getClusterClient(hosts);
                break;
        }
    }

    private static RedissonClient getSingleClient(String host) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(host)
                .setConnectionPoolSize(1000)
        ;

        return Redisson.create(config);
    }

    //Master/Slave servers connection:
    private static RedissonClient getMasterSlaveClient(String add, String hosts) {
        Config config = new Config();
        String[] hostarr = hosts.split(",");
        config.useMasterSlaveServers()
                .setMasterAddress(add)
                .setLoadBalancer(new RandomLoadBalancer()) // RoundRobinLoadBalancer used by default

                .addSlaveAddress(hostarr)
                .setMasterConnectionPoolSize(10000)
                .setSlaveConnectionPoolSize(10000);

        return Redisson.create(config);
    }

    //Sentinel servers connection:
    private static RedissonClient getSentinelClient(String masterName, String hosts) {
        String[] hostarr = hosts.split(",");
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName(masterName)
                .addSentinelAddress(hostarr)
                .setMasterConnectionPoolSize(10000)
                .setSlaveConnectionPoolSize(10000);

        return Redisson.create(config);
    }

    //Cluster nodes connections:
    private static RedissonClient getClusterClient(String hosts) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // sets cluster state scan interval
                .addNodeAddress(StringUtils.split(hosts, ","))
                .setMasterConnectionPoolSize(10000)
                .setSlaveConnectionPoolSize(10000);
        return Redisson.create(config);
    }
}
