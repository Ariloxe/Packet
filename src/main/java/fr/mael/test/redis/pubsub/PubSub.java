package fr.mael.test.redis.pubsub;

import fr.mael.test.redis.Redis;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

/*
 * Class PubSub
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class PubSub {

	private Subscriber subscriber;
	private Sender     sender;
	private boolean continueSub = true;

	public PubSub() {
		subscriber = new Subscriber();
		new Thread(() -> {
			while (continueSub) {
				JedisPool jedis = Redis.getNewConnection();
				try {
					jedis.getResource().psubscribe(subscriber, "*");
				} catch (Exception e) {
					jedis.close();
					e.printStackTrace();
				}

				Bukkit.getLogger().info("Déconnecté du master.");
			}
		}).start();

		Bukkit.getLogger().info("En attente du subscribing...");
		while (!subscriber.isSubscribed())
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		Bukkit.getLogger().info("Correctement subscribed.");

		sender = new Sender();
		new Thread(sender, "SenderThread").start();
	}

	public void subscribe(String channel, Subscriber.PacketsReceiver receiver) {
		subscriber.registerReceiver(channel, receiver);
	}

	public void subscribe(String pattern, Subscriber.PatternReceiver receiver) {
		subscriber.registerPattern(pattern, receiver);
	}

	public void send(String channel, String message) {
		sender.publish(new PendingMessage(channel, message));
	}

	public void send(PendingMessage message) {
		sender.publish(message);
	}

	public Sender getSender() {
		return sender;
	}

	public void disable() {
		continueSub = false;
		subscriber.unsubscribe();
		subscriber.punsubscribe();
		try {
			Thread.sleep(500);
		} catch (Exception ignored) {}
	}
}
