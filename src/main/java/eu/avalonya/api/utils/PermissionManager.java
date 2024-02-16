package eu.avalonya.api.utils;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.models.Rank;
import eu.avalonya.api.sql.Cache;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class PermissionManager
{

    public static void loadPermissionsFromConfigFileToCache()
    {
        FileConfiguration configFile = ConfigFilesManager.getFile("permissions").get();

        ConfigurationSection ranksSection = configFile.getConfigurationSection("ranks");

        if (ranksSection != null)
        {
            for (String rank : ranksSection.getKeys(false))
            {
                Rank r = Rank.rankIdToRank.get(configFile.getInt("ranks." + rank + ".rankId"));
                List<String> permissionsFromFile = configFile.getStringList("ranks." + rank + ".permissions");
                Cache.permissions.put(r, permissionsFromFile);
            }
        }
        else
        {
            AvalonyaAPI.getInstance().getLogger().warning("La section 'ranks' n'existe pas dans le fichier de configuration.");
        }
    }

}
