package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public class V10_AddAllies extends Migration {

    private final String addAlliesTable = """
            CREATE TABLE `town_allies` (
                `id` INT NOT NULL AUTO_INCREMENT,
                `town_sender` INT NOT NULL,
                `town_receiver` INT NOT NULL,
                `pending` BOOLEAN NOT NULL DEFAULT FALSE,
                `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (`town_sender`) REFERENCES `towns`(`id`),
                FOREIGN KEY (`town_receiver`) REFERENCES `towns`(`id`),
                PRIMARY KEY (`id`)
            );
            """;

    @Override
    public void execute() {
        this.execute(addAlliesTable);
    }
}
