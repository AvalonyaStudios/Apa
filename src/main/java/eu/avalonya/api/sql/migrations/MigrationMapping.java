package eu.avalonya.api.sql.migrations;

import eu.avalonya.api.sql.Migration;

public enum MigrationMapping
{

    v00_MIGRATION_TABLE(0, V00_AddMigrationTable.class),
    V01_ADD_PLAYER(1, V01_AddPlayer.class),
    V02_ADD_RANK(2, V02_AddRankColumn.class),
    V03_ADD_PLAYER_INFO(3, V03_AddPlayerInfo.class),
    V04_ADD_TOWNS_TABLE(4, V04_AddTownsTable.class),
    V05_ADD_ROLES_TABLE(5, V05_AddRolesTable.class),
    V06_ADD_CITIZENS_TABLE(6, V06_AddCitizensTable.class),
    V07_ADD_PLOTS_TABLE(7, V07_AddPlotsTable.class),;

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
