package fr.mael.redispacket.api;

import fr.mael.redispacket.Main;
import fr.mael.redispacket.database.RedisManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

@Getter
public class MainAPI {
    private static MainAPI mainAPI;

    private RedisManager redisManager;

    public MainAPI(RedisManager redisManager) {
        mainAPI = this;
        this.redisManager = redisManager;
    }

    public static MainAPI getMainAPI() {
        return mainAPI;
    }

    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), runnable);
    }

    public Logger getLogger() {
        return Main.getMain().getLogger();
    }
}
