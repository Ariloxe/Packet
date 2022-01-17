package fr.mael.test.messaging.protocol.player;

import fr.mael.test.messaging.IPacketHandler;
import fr.mael.test.messaging.protocol.IPacket;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

/*
 * Class PlayerJoinPacket
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class PlayerJoinPacket implements IPacket {

    private UUID uuid;
    private String username;

    public PlayerJoinPacket(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public PlayerJoinPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        uuid = UUID.fromString(input.readUTF());
        username = input.readUTF();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(uuid.toString());
        output.writeUTF(username);
    }

    @Override
    public void handle(IPacketHandler handler) {
        handler.handle(this);
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }
}
