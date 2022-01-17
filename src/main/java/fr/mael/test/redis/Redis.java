package fr.mael.test.redis;

import org.bukkit.Bukkit;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
 * Class Redis
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class Redis {

    private static JedisPool jedis;

    public static JedisPool getJedis() {
        if (jedis == null)
            connect();
        return jedis;
    }

    public static void close() {
        jedis.close();
        jedis = null;
    }

    public static JedisPool getNewConnection() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(-1);
        config.setJmxEnabled(false);
        return new JedisPool(config, "127.0.0.1", 6379, 0, null, 1);

    }

    public static void connect() {
        System.out.println("[Redis] Try to connect to redis...");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(-1);
        config.setJmxEnabled(false);
        jedis = new JedisPool(config, "127.0.0.1", 6379, 0, null, 1);
        System.out.println("[Redis] Connected..");
    }
}
