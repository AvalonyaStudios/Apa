package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.sql.Migration;

public class V03_AddPlayerInfo extends Migration
{

    private String addPlayerInfoColumns = """
            ALTER TABLE `player`
            ADD COLUMN `pseudo` VARCHAR(255) NOT NULL AFTER `uuid`,
            ADD COLUMN `last_login` DATETIME NOT NULL AFTER `rank_id`,
            ADD COLUMN `first_login` DATETIME NOT NULL AFTER `last_login`,
            ADD COLUMN `last_ip` VARCHAR(255) NOT NULL AFTER `first_login`;
            """;

    @Override
    public void execute()
    {
        AvalonyaAPI.getInstance().getLogger().info("Executing AddPlayerInfo");
        this.execute(this.addPlayerInfoColumns);
    }

}
