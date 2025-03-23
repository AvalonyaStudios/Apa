package eu.avalonya.api;

import eu.avalonya.api.inventory.History;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AvalonyaAPI extends JavaPlugin {

    @Getter
    private static AvalonyaAPI instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new History(), this);
    }

}