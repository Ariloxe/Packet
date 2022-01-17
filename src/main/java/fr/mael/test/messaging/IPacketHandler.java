package fr.mael.test.messaging;

import fr.mael.test.messaging.protocol.player.PlayerJoinPacket;

/*
 * Class IPacketHandler
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public abstract class IPacketHandler {
    public abstract void handle(PlayerJoinPacket packet);
}
