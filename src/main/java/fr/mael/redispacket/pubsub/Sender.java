package fr.mael.redispacket.pubsub;

import fr.mael.redispacket.api.MainAPI;
import redis.clients.jedis.Jedis;

import java.util.concurrent.LinkedBlockingQueue;

public class Sender implements Runnable {
    private MainAPI mainAPI;
    private Jedis jedis;
    private LinkedBlockingQueue<PendingMessage> pendingMessages;

    Sender(MainAPI mainAPI) {
        this.mainAPI = mainAPI;
        this.pendingMessages = new LinkedBlockingQueue<>();
    }

    public void publish(PendingMessage message) {
        this.pendingMessages.add(message);
    }

    @Override
    public void run() {
        fixDatabase();
        while (true) {
            PendingMessage message;
            try {
                message = pendingMessages.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                jedis.close();
                return;
            }

            boolean published = false;
            while (!published) {
                try {
                    this.jedis.publish(message.getChannel(), message.getMessage());
                    message.runAfter();
                    published = true;
                } catch (Exception e) {
                    fixDatabase();
                }
            }
        }
    }

    private void fixDatabase() {
        try {
            if (this.jedis != null)
                this.jedis.close();

            this.jedis = mainAPI.getRedisManager().getResource();
        } catch (Exception e) {
            MainAPI.getMainAPI().getLogger()
                    .severe("[Publisher] Impossible de se connecter au serveur redis : " + e.getMessage() +
                            ". Réessayer dans 5 secondes.");
            try {
                Thread.sleep(5000);
                fixDatabase();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
