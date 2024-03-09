package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public class V05_AddRolesTable extends Migration
{

    private String addRolesTable = """
            CREATE TABLE `roles` (
                `id` INT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(255) NOT NULL,
                `town_id` INT NOT NULL,
                `permissions` TEXT DEFAULT NULL,
                PRIMARY KEY (`id`),
                FOREIGN KEY (`town_id`) REFERENCES `towns`(`id`)
            );
            """;

    @Override
    public void execute()
    {
        this.execute(this.addRolesTable);
    }
}
