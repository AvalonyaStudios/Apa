package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public class V07_AddPlotsTable extends Migration
{

    private final String addPlotsTable = """
            CREATE TABLE `plots` (
                `id` INT NOT NULL AUTO_INCREMENT,
                `town_id` INT NOT NULL,
                `x` INT NOT NULL,
                `z` INT NOT NULL,
                `name` VARCHAR(255) DEFAULT NULL,
                `type` INT NOT NULL DEFAULT 0,
                `permissions` INT NOT NULL DEFAULT 0,
                `is_outpost` BOOLEAN NOT NULL DEFAULT 0,
                PRIMARY KEY (`id`),
                FOREIGN KEY (`town_id`) REFERENCES `towns`(`id`)
            );
            """;

    @Override
    public void execute()
    {
        this.execute(this.addPlotsTable);
    }
}
