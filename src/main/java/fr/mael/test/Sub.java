package fr.mael.test;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.mael.test.messaging.protocol.IPacket;
import fr.mael.test.redis.pubsub.Subscriber;
import fr.mael.test.messaging.protocol.PacketManager;

/*
 * Class Sub
 *
 * @author Maël - B | "LgRmael40"
 *
 * Copyright (©) | 2019 - 2020 | AmnezionNetWork. All rights reserved
 *  This file is a part of AmnezionNetWork project.
 */
public class Sub implements Subscriber.PacketsReceiver {

    @Override
    public void receive(String channel, String message) {
        try {
            ByteArrayDataInput input = ByteStreams.newDataInput(message.getBytes(Charsets.UTF_8));
            IPacket packet = PacketManager.readPacket(input);
            if (packet != null && SpigotAPI.getInstance().getPacketManager().getHandler() != null) packet.handle(SpigotAPI.getInstance().getPacketManager().getHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
