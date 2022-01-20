package fr.mael.test.event;

import fr.mael.test.messaging.IPacketHandler;
import fr.mael.test.messaging.protocol.player.PlayerJoinPacket;
import org.bukkit.Bukkit;

/*
 * Class IPacketCoreHanlder
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class IPacketCoreHandler extends IPacketHandler {

    @Override
    public void handle(PlayerJoinPacket packet) {
        Bukkit.broadcastMessage("Packet player join" + packet.getUsername());
    }
}
