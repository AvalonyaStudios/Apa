package eu.avalonya.api;

import fr.mrmicky.fastinv.FastInvManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AvalonyaAPI extends JavaPlugin
{

    private static AvalonyaAPI instance;

    @Override
    public void onEnable()
    {
        instance = this;

        FastInvManager.register(this);
    }

    @Override
    public void onDisable()
    {

    }

    public static AvalonyaAPI getInstance() {
        return instance;
    }
}