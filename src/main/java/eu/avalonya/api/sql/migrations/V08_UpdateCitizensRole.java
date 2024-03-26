package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public class V08_UpdateCitizensRole extends Migration
{

    private String updateCitizensRole = """
            ALTER TABLE
                `citizens`
            DROP FOREIGN KEY
                `citizens_ibfk_2`,
            CHANGE COLUMN
                `role_id` `role` INT DEFAULT 0;
            """;

    public void execute()
    {
        this.execute(this.updateCitizensRole);
    }

}
