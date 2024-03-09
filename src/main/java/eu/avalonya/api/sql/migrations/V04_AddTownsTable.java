package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public class V04_AddTownsTable extends Migration
{

    private String addTownTable = """
            CREATE TABLE `towns` (
                `id` INT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(255) NOT NULL,
                `politics` TEXT DEFAULT NULL,
                `taxes` FLOAT NOT NULL DEFAULT 0,
                `money` FLOAT NOT NULL DEFAULT 0,
                `spawn_location` VARCHAR(255) DEFAULT NULL,
                `taxes_enabled` BOOLEAN NOT NULL DEFAULT FALSE,
                `spawn_hostile_mob` BOOLEAN NOT NULL DEFAULT FALSE,
                `fire_spread` BOOLEAN NOT NULL DEFAULT FALSE,
                `explosions` BOOLEAN NOT NULL DEFAULT FALSE,
                `public` BOOLEAN NOT NULL DEFAULT FALSE,
                `friendly_fire` BOOLEAN NOT NULL DEFAULT FALSE,
                `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                PRIMARY KEY (`id`)
            );
            """;

    @Override
    public void execute()
    {
        this.execute(this.addTownTable);
    }
}
