package fr.mael.redispacket.test.proxy;

import fr.mael.redispacket.protocol.Packet;
import lombok.Getter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Getter
public class BroadcastMessagePacket implements Packet {
    private String sender, message;

    public BroadcastMessagePacket(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public void read(DataInput input) throws IOException {
        sender = input.readUTF();
        message = input.readUTF();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(sender);
        output.writeUTF(message);
    }
}
