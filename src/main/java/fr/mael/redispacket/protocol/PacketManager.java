package fr.mael.redispacket.protocol;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.mael.redispacket.test.proxy.BroadcastMessagePacket;
import fr.mael.redispacket.test.server.CreateServerPacket;

import java.io.DataInput;
import java.io.DataOutput;

public class PacketManager {
    private static final BiMap<Byte, Class<? extends Packet>> PACKETS = HashBiMap.create();
    private PacketHandler handler;

    static {

        //Server
        PACKETS.put((byte) 0x01, CreateServerPacket.class);

        //Proxy
        PACKETS.put((byte) 0x10, BroadcastMessagePacket.class);
    }

    public static Packet readPacket(DataInput input) throws Exception {
        byte packetId = input.readByte();

        if (!PACKETS.containsKey(packetId))
            throw new RuntimeException("This packet isn't registred !");

        Packet packet = PACKETS.get(packetId).newInstance();
        packet.read(input);
        return packet;
    }

    public synchronized void writePacket(Packet packet) {
        try {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            PacketManager.writePacket(output, packet);
            //PUBSUB
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void writePacket(DataOutput output, Packet packet) throws Exception {
        Byte packetID = PACKETS.inverse().get(packet.getClass());

        Preconditions.checkNotNull(packetID, "This packet isn't registred !");
        output.write(packetID);
        packet.write(output);
    }

    public PacketHandler getHandler() {
        return handler;
    }

    public void setHandler(PacketHandler handler) {
        this.handler = handler;
    }
}
