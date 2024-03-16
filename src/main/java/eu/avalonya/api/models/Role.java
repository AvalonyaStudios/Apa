package eu.avalonya.api.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public enum Role
{

    CITIZEN("§9"),
    CUSTOM_ONE("§b"),
    CUSTOM_TWO("§e"),
    CUSTOM_THREE("§c"),
    MAYOR("§6")
    ;

    private final String color;

    Role(String color) {
        this.color = color;
    }

    public String getColor()
    {
        return color;
    }

    public String getName()
    {
        if (this == CITIZEN || this == MAYOR)
        {
            return this.name().toLowerCase();
        }

        try
        {
            return AvalonyaDatabase.getRoleDao().queryBuilder().where().eq("name" ,this.name().toLowerCase()).queryForFirst().getName();
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // TODO: Utiliser le logger
        }
        return null;
    }

    @DatabaseTable(tableName = "roles")
    @Getter
    @Setter
    public static class Custom {

        @DatabaseField(generatedId = true)
        private int id;

        @DatabaseField(canBeNull = false)
        private String name;

        @DatabaseField(columnName = "town_id",foreign = true, foreignAutoRefresh = true)
        private Town town;

        @DatabaseField(canBeNull = false, defaultValue = "0")
        private int permissions = 0;

        public Custom()
        {
            // Required by ORMLite
        }

        public Custom(String name, Town town)
        {
            this.name = name;
            this.town = town;
        }
    }

}
