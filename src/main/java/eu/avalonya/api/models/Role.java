package eu.avalonya.api.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashMap;
import java.util.Map;

@DatabaseTable(tableName = "roles")
public class Role {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private final String name;

    @DatabaseField(columnName = "town_id",foreign = true, foreignAutoRefresh = true)
    private Town town;

    @DatabaseField(canBeNull = false, defaultValue = "0")
    private int permissions = 0;

    private final String color;

    public Role()
    {
        this.name = "CITIZEN";
        this.color = Color.CITIZEN.getColor();
    }

    public Role(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Role(String name, Color color) {
        this(name, color.getColor());
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public enum Color {
        CITIZEN("§9"),
        CUSTOM_ONE("§b"),
        CUSTOM_TWO("§e"),
        CUSTOM_THREE("§c"),
        MAYOR("§6");

        private final String color;

        Color(String color)
        {
            this.color = color;
        }

        public String getColor()
        {
            return this.color;
        }
    }

}
