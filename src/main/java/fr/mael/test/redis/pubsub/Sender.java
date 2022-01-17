package fr.mael.test.redis.pubsub;

import fr.mael.test.SpigotAPI;
import fr.mael.test.redis.Redis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * Class Sender
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class Sender implements Runnable {

	private LinkedBlockingQueue<PendingMessage> pendingMessages = new LinkedBlockingQueue<>();
	private JedisPool jedis;

	public void publish(PendingMessage message) {
		pendingMessages.add(message);
	}

	@Override
	public void run() {
		fixDatabase();
		while (true) {

			PendingMessage message;
			try {
				message = pendingMessages.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				jedis.close();
				return;
			}

			boolean published = false;
			while (!published) {
				try {
					jedis.getResource().publish(message.getChannel(), message.getMessage());
					message.runAfter();
					published = true;
				} catch (Exception e) {
					fixDatabase();
				}
			}
		}
	}

	private void fixDatabase() {
		try {
			jedis = Redis.getNewConnection();
		} catch (Exception e) {
			SpigotAPI.getInstance().getLogger().severe("[Publisher] Impossible de se connecter au serveur redis : " + e.getMessage() + ". Réessayer dans 5 secondes.");
			try {
				Thread.sleep(5000);
				fixDatabase();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
