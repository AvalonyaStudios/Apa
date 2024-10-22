package eu.avalonya.api.http;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public enum Endpoint {

    // POST
    PLAYER("/player"),
    // GET
    PLAYERS("/players"),
    // GET, PUT, DELETE
    PLAYERS_ID("/players/{player_id}"),
    // GET, POST
    PLAYER_WARNS("/player/{player_id}/warns"),
    // GET, POST
    RANKS("/ranks"),
    // GET, PUT, DELETE
    RANKS_NAME("/ranks/{rank_name}"),
    // GET, POST
    BACKPACKS("/backpacks"),
    // GET, PUT, DELETE
    BACKPACKS_ID("/backpacks/{backpack_id}"),
    // GET, POST
    NATIONS("/nations"),
    // GET, PUT, DELETE
    NATIONS_NAME("/nations/{nations_name}"),
    // GET, POST
    NATIONS_TOWNS(NATIONS_NAME.getPath() + "/towns"),
    // GET, POST
    TOWNS("/towns"),
    // GET, PUT, DELETE
    TOWNS_NAME("/towns/{town_name}"),
    // GET, POST
    CITIZENS(TOWNS_NAME.getPath() + "/citizens"),
    // DELETE
    CITIZENS_ID(TOWNS_NAME.getPath() + "/citizens/{citizen_id}"),
    // GET, POST
    TOWN_PLOTS(TOWNS_NAME.getPath() + "/plots"),
    // PUT, DELETE
    TOWN_PLOTS_ID(TOWNS_NAME.getPath() + "/plots/{plot_id}"),
    ;

    private static final String REGEX = "\\{[a-zA-Z0-9_]+}";

    @Getter
    @Setter
    private String path;

    Endpoint(String path) {
        this.path = path;
    }

    public static Endpoint bind(Endpoint endpoint, String... params) {
        String path = endpoint.getPath();
        for (String param : params) {
            path = path.replaceFirst(REGEX, param);
        }

        endpoint.setPath(path);

        return endpoint;
    }

    public static Endpoint bind(Endpoint endpoint, List<String> params) {
        String path = endpoint.getPath();
        for (String param : params) {
            path = path.replaceFirst(REGEX, param);
        }

        endpoint.setPath(path);

        return endpoint;
    }

    public void bindAssoc(Map<String, String> params) {
        String path = this.getPath();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            path.replace(key, value);
        }

        this.setPath(path);

    }
}
