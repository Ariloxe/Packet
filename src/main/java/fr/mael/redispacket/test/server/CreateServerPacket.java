package fr.mael.redispacket.test.server;

import fr.mael.redispacket.protocol.Packet;
import lombok.Getter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Getter
public class CreateServerPacket implements Packet {
    private int id;
    private String name, game, map, state;

    public CreateServerPacket(int id, String name, String game, String map, String state) {
        this.id = id;
        this.name = name;
        this.game = game;
        this.map = map;
        this.state = state;
    }

    @Override
    public void read(DataInput input) throws IOException {
        id = input.readInt();
        name = input.readUTF();
        game = input.readUTF();
        map = input.readUTF();
        state = input.readUTF();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(id);
        output.writeUTF(name);
        output.writeUTF(game);
        output.writeUTF(map);
        output.writeUTF(state);
    }
}