package fr.mael.test;

import fr.mael.test.event.IPacketCoreHandler;
import fr.mael.test.messaging.protocol.PacketManager;
import fr.mael.test.redis.Redis;
import fr.mael.test.redis.pubsub.PubSub;
import fr.mael.test.test.HeartBeat;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Class SpigotAPI
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class SpigotAPI extends JavaPlugin {

    private static SpigotAPI instance;
    private PubSub pubSub;
    private PacketManager packetManager;
    private boolean testMode = false;

    @Override
    public void onLoad() {
        instance = this;

        Redis.connect();
        pubSub = new PubSub();

        packetManager = new PacketManager();
        packetManager.setHandler(new IPacketCoreHandler());

    }

    public void onEnable() {
        if (testMode)
            new Thread(new HeartBeat()).start();
    }

    @Override
    public void onDisable() {
        getPubSub().disable();
        Redis.close();
    }

    public static SpigotAPI getInstance() {
        return instance;
    }

    public PacketManager getPacketManager(){
        return packetManager;
    }

    public PubSub getPubSub() {
        return pubSub;
    }
}
