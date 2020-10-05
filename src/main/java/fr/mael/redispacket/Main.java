package fr.mael.redispacket;

import fr.mael.redispacket.api.MainAPI;
import fr.mael.redispacket.database.RedisManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    private static Main instance;
    private static MainAPI mainAPI;

    @Override
    public void onEnable() {
        instance = this;

        RedisManager redisManager = new RedisManager(null);
        mainAPI = new MainAPI(redisManager);
    }

    @Override
    public void onDisable() {
        MainAPI.getMainAPI().getRedisManager().destroyConnection();
    }

    public static Main getMain() {
        return instance;
    }

}
