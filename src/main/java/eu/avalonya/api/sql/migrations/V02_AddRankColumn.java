package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.sql.Migration;

import javax.accessibility.AccessibleValue;

public class V02_AddRankColumn extends Migration
{

    private String addUniqueToUuidColumn = """
            ALTER TABLE `player`
            ADD CONSTRAINT PLAYER_unique UNIQUE(uuid);
            """;
    private String addRankColumn = """
            ALTER TABLE `player`
            ADD rank_id int NOT NULL DEFAULT(0)
            """;

    @Override
    public void execute()
    {
        AvalonyaAPI.getInstance().getLogger().info("Executing AddRankColumn");
        this.execute(this.addUniqueToUuidColumn);
        this.execute(this.addRankColumn);
    }

}
