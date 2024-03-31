package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public class V09_UpdateCitizensTable extends Migration
{

    // Remove primary key from 'uuid' column and add a new primary key 'id' column
    private final String updateCitizensTable = """
            ALTER TABLE
                `citizens`
            DROP FOREIGN KEY
                `citizens_ibfk_3`,
            DROP PRIMARY KEY,
            ADD COLUMN
                `id` INT AUTO_INCREMENT PRIMARY KEY FIRST,
            ADD FOREIGN KEY (`uuid`) REFERENCES `player`(`uuid`);
            """;

    @Override
    public void execute()
    {
        this.execute(this.updateCitizensTable);
    }
}
