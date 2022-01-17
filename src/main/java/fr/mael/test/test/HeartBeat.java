package fr.mael.test.test;

import fr.mael.test.SpigotAPI;
import fr.mael.test.messaging.protocol.player.PlayerJoinPacket;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Class HeartBeat
 *
 * This cert class tested the packet system on this project.
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class HeartBeat extends Thread {

    private static AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    @Override
    public void run() {
        while (atomicBoolean.get()) {
            SpigotAPI.getInstance().getPacketManager().writePacket(new PlayerJoinPacket(UUID.randomUUID(), "LgRmael40"));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disable() {
        atomicBoolean.set(false);
    }

}
