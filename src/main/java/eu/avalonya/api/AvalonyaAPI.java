package eu.avalonya.api;

import eu.avalonya.api.sql.MigrationUtils;
import eu.avalonya.api.sql.SQL;
import eu.avalonya.api.utils.ConfigFilesManager;
import eu.avalonya.api.utils.CustomConfigFile;
import org.bukkit.configuration.file.FileConfiguration;
import fr.mrmicky.fastinv.FastInvManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AvalonyaAPI extends JavaPlugin
{

    private static AvalonyaAPI instance;
    private static SQL sqlInstance;

    @Override
    public void onEnable()
    {
        instance = this;

        CustomConfigFile sqlConfig = new CustomConfigFile(AvalonyaAPI.getInstance(), "database.yml", "sql");

        FileConfiguration fSql = ConfigFilesManager.getFile("sql").get();

        AvalonyaAPI.sqlInstance = new SQL("jdbc:mysql://", fSql.getString("host"), fSql.getString("database"), fSql.getString("user"), fSql.getString("password"));
        AvalonyaAPI.sqlInstance.connection();

        manageMigration();
    }

    public void manageMigration()
    {
        MigrationUtils.checkCurrentVersion();

        FastInvManager.register(this);
    }

    @Override
    public void onDisable()
    {
        AvalonyaAPI.sqlInstance.disconnect();
    }

    public static AvalonyaAPI getInstance()
    {
        return instance;
    }

    public static SQL getSqlInstance()
    {
        return sqlInstance;
    }

}