package fr.mael.test.redis.pubsub;

import fr.mael.test.SpigotAPI;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

/*
 * Class Subscriber
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class Subscriber extends JedisPubSub {
	protected HashMap<String, HashSet<PacketsReceiver>> packetsReceivers = new HashMap<>();
	protected HashMap<String, HashSet<PatternReceiver>> patternsReceivers = new HashMap<>();

	public void registerReceiver(String channel, PacketsReceiver receiver) {
		HashSet<PacketsReceiver> receivers = packetsReceivers.get(channel);
		if (receivers == null)
			receivers = new HashSet<>();
		receivers.add(receiver);
		this.subscribe(channel);
		packetsReceivers.put(channel, receivers);
	}

	public void registerPattern(String pattern, PatternReceiver receiver) {
		HashSet<PatternReceiver> receivers = patternsReceivers.get(pattern);
		if (receivers == null)
			receivers = new HashSet<>();
		receivers.add(receiver);
		this.psubscribe(pattern);
		patternsReceivers.put(pattern, receivers);
	}

	@Override
	public void onMessage(String channel, String message) {
		try {
			HashSet<PacketsReceiver> receivers = packetsReceivers.get(channel);
			if (receivers != null)
				receivers.forEach((PacketsReceiver receiver) -> receiver.receive(channel, message));
			else
				SpigotAPI.getInstance().getLogger().log(Level.WARNING, "{PubSub} Received message on a channel, but no packetsReceivers were found.");
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		try {
			HashSet<PatternReceiver> receivers = patternsReceivers.get(pattern);
			if (receivers != null)
				receivers.forEach((PatternReceiver receiver) -> receiver.receive(pattern, channel, message));
			else
				SpigotAPI.getInstance().getLogger().log(Level.WARNING, "{PubSub} Received pmessage on a channel, but no packetsReceivers were found.");
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}

	public interface PacketsReceiver {

		void receive(String channel, String packet);
	}

	public interface PatternReceiver {

		void receive(String pattern, String channel, String packet);
	}
}
