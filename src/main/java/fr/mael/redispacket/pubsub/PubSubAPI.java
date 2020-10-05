package fr.mael.redispacket.pubsub;

import fr.mael.redispacket.api.MainAPI;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class PubSubAPI {
    private Subscriber subscriber;
    private Sender sender;
    private boolean continueSub = true;

    public PubSubAPI() {
        this.subscriber = new Subscriber();
        new Thread(() -> {
            while (continueSub) {
                try (Jedis jedis = MainAPI.getMainAPI().getRedisManager().getJedisResource()) {
                    jedis.psubscribe(subscriber, "*");
                } catch (JedisException e) {
                    e.printStackTrace();
                }
                MainAPI.getMainAPI().getLogger().info("Redis - Disconnected from master.");
            }
        }).start();
        MainAPI.getMainAPI().getLogger().info("Redis - Waiting for subscribing...");
        while (!subscriber.isSubscribed()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MainAPI.getMainAPI().getLogger().info("Redis - Correctly subscribed.");
        sender = new Sender(MainAPI.getMainAPI());
        new Thread(sender, "SenderThread").start();
    }

    public void subscribe(String pattern, PubSubReceiver receiver) {
        subscriber.registerPattern(pattern, receiver);
    }

    public void unsubscribe(String pattern) {
        subscriber.unsubscribePattern(pattern);
    }

    public void send(String channel, String message) {
        sender.publish(new PendingMessage(channel, message));
    }
}
