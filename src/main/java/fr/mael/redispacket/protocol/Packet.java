package fr.mael.redispacket.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Packet {
    void read(DataInput input) throws IOException;

    void write(DataOutput output) throws IOException;
}
