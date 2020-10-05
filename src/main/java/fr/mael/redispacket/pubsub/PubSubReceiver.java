package fr.mael.redispacket.pubsub;

public interface PubSubReceiver {
    public void receive(String channel, String message);
}
