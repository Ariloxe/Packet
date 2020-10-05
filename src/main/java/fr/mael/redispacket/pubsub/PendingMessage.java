package fr.mael.redispacket.pubsub;

import lombok.Getter;

@Getter
public class PendingMessage {
    private String channel;
    private String message;
    private Runnable callback;

    public PendingMessage(String channel, String message) {
        this.channel = channel;
        this.message = message;
        this.callback = null;
    }
    public PendingMessage(String channel, String message, Runnable callback) {
        this.channel = channel;
        this.message = message;
        this.callback = callback;
    }

    public void runAfter() {
        try {
            if (callback != null)
                callback.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
