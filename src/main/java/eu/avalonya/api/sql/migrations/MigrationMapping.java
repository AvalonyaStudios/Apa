package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public enum MigrationMapping
{

    v00_MIGRATION_TABLE(0, V00_AddMigrationTable.class),
    V01_ADD_PLAYER(1, V01_AddPlayer.class);

    private final int id;
    private final Class<? extends Migration> migrationClass;

    MigrationMapping(int id, Class<? extends Migration> migrationClass)
    {
        this.id = id;
        this.migrationClass = migrationClass;
    }


    public int getId() {
        return id;
    }

    public Class<? extends Migration> getMigrationClass() {
        return migrationClass;
    }

    public static Migration createMigrationById(int id) throws IllegalAccessException, InstantiationException
    {
        for (MigrationMapping migrationEnum : values())
        {
            if (migrationEnum.getId() == id)
            {
                return migrationEnum.getMigrationClass().newInstance();
            }
        }
        throw new IllegalArgumentException("Invalid migration ID: " + id);
    }

}
