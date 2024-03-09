package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public class V06_AddCitizensTable extends Migration
{

    private String addCitizensTable = """
            CREATE TABLE `citizens` (
                `uuid` VARCHAR(255) NOT NULL,
                `town_id` INT DEFAULT NULL,
                `role_id` INT DEFAULT NULL,
                `money` FLOAT NOT NULL DEFAULT 0,
                `joined_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                PRIMARY KEY (`uuid`),
                FOREIGN KEY (`town_id`) REFERENCES `towns`(`id`),
                FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
                FOREIGN KEY (`uuid`) REFERENCES `player`(`uuid`)
            );
            """;

    @Override
    public void execute()
    {
        this.execute(this.addCitizensTable);
    }
}
