package fr.mael.redispacket.database;

import fr.mael.redispacket.Main;
import fr.mael.redispacket.api.MainAPI;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.logging.Level;

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
        MainAPI.getMainAPI().getLogger().log(Level.INFO ,"Redis - Successful connection");
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
