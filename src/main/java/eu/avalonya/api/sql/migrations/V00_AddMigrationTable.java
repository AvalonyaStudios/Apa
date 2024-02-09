package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.sql.Migration;

public class V00_AddMigrationTable extends Migration
{

    private String createTableRequest = """
            CREATE TABLE `migration_version` (
              `version` int(11) NOT NULL)
    """;

    private String uniqueKeyRequest = """               
            ALTER TABLE `migration_version`
              ADD UNIQUE KEY `i_version` (`version`);
    """;

    @Override
    public void execute()
    {
        AvalonyaAPI.getInstance().getLogger().info("Executing AddMigrationVersion");
        this.execute(this.createTableRequest);
        this.execute(this.uniqueKeyRequest);
    }

}
