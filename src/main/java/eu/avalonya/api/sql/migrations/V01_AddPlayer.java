package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.sql.Migration;

public class V01_AddPlayer extends Migration
{

    private String createTablePlayer = """
            CREATE TABLE `player` (
              `uuid` varchar(255) NOT NULL)
    """;

    @Override
    public void execute()
    {
        AvalonyaAPI.getInstance().getLogger().info("Executing AddPlayerMigration");
        this.execute(this.createTablePlayer);
    }

}
