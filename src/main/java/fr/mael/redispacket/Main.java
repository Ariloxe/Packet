package fr.mael.redispacket;

import fr.mael.redispacket.api.MainAPI;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    private static Main instance;
    public static MainAPI mainAPI;

    @Override
    public void onEnable() {
        instance = this;
        mainAPI = new MainAPI();
    }

    @Override
    public void onDisable() {

    }

    public static Main getMain() {
        return instance;
    }
}
