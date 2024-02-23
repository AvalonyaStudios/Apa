package eu.avalonya.api.models;

import java.util.HashMap;
import java.util.Map;

public class Role {

    private final String name;
    private final String color;

    private Map<String, Boolean> permissions = new HashMap<>();

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

    public void setPermission(String name, boolean value){
        permissions.put(name, value);
    }

    public boolean getPermission(String name){
        return permissions.getOrDefault(name, false);
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
