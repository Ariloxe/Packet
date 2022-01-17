package fr.mael.test.messaging.protocol;

import com.google.common.base.Charsets;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.mael.test.Sub;
import fr.mael.test.messaging.IPacketHandler;
import fr.mael.test.SpigotAPI;
import fr.mael.test.messaging.protocol.player.PlayerJoinPacket;

import java.io.DataInput;
import java.io.DataOutput;

/*
 * Class PacketManager
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public final class PacketManager {
    private static final BiMap<Byte, Class<? extends IPacket>> packets = HashBiMap.create();
    private IPacketHandler handler;

    static {
        // Player
        packets.put((byte) 0x01, PlayerJoinPacket.class);
    }

    public PacketManager() {
        SpigotAPI.getInstance().getPubSub().subscribe("server", new Sub());
    }

    public synchronized void writePacket(IPacket packet) {
        try {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            PacketManager.writePacket(output, packet);
            SpigotAPI.getInstance().getPubSub().send("server", new String(output.toByteArray(), Charsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Read a {@link IPacket} from a {@link DataInput}.
     *
     * @param input The input.
     * @return The {@link IPacket}.
     * @throws Exception If there is an exception.
     */
    public static IPacket readPacket(DataInput input) throws Exception {
        byte packetId = input.readByte();


        if (!packets.containsKey(packetId)) {
            throw new RuntimeException("This packet isn't registered!");
        }

        IPacket packet = packets.get(packetId).newInstance();
        packet.read(input);
        return packet;
    }

    /**
     * Write a {@link IPacket} on {@link DataOutput}.
     *
     * @param output The output.
     * @param packet The packet.
     * @throws Exception If there is an exception.
     */

    public static void writePacket(DataOutput output, IPacket packet) throws Exception {
        if (!packets.inverse().containsKey(packet.getClass())) {
            throw new RuntimeException("This packet isn't registered!");
        }
        output.writeByte(packets.inverse().get(packet.getClass()));
        packet.write(output);
    }

    public IPacketHandler getHandler() {
        return handler;
    }

    public void setHandler(IPacketHandler handler) {
        this.handler = handler;
    }
}
