package fr.mael.redispacket.database;

import fr.mael.redispacket.Main;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {
    private String host;
    private JedisPool jedisPool;

    public RedisManager(String host) {
        this.host = "127.0.0.1";
        setupConnection();
    }

    private void setupConnection() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(12);
        jedisPool = new JedisPool(jedisPoolConfig, host, 6379);
        Main.getMain().getLogger().info("Redis - Successful connection");
    }

    public Jedis getResource() {
        return jedisPool.getResource();
    }

    public Jedis getJedisResource() {
        return new Jedis(host, 6379);
    }

    public void destroyConnection() {
        jedisPool.destroy();
    }
}
