package fr.mael.test.messaging.protocol;

import fr.mael.test.messaging.IPacketHandler;

import java.io.DataInput;
import java.io.DataOutput;

/*
 * Class IPacket
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public interface IPacket {

    /**
     * Read the packet from a {@link DataInput}.
     *
     * @param input The input.
     * @throws Exception If there an exception.
     */
    void read(DataInput input) throws Exception;

    /**
     * Write the packet on a {@link DataOutput}.
     *
     * @param output The output.
     * @throws Exception If there an exception.
     */
    void write(DataOutput output) throws Exception;

    /**
     * Handling the packet on {@link IPacketHandler}.
     *
     * @param handler The handler.
     */
    void handle(IPacketHandler handler);
}
