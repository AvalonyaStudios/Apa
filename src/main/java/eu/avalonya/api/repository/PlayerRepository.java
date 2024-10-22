package eu.avalonya.api.repository;

import eu.avalonya.api.http.Endpoint;
import eu.avalonya.api.models.Player;

import java.util.List;
import java.util.Map;

public class PlayerRepository extends AbstractRepository<Player> {

    public PlayerRepository(List<String> vars) {
        super(Player.class, vars);
    }

    @Override
    public Map<String, Endpoint> getEndpoints() {
        return Map.of(
                "all", Endpoint.PLAYERS,
                "get", Endpoint.PLAYERS_ID,
                "create", Endpoint.PLAYER,
                "update", Endpoint.PLAYERS_ID,
                "delete", Endpoint.PLAYERS_ID
        );
    }


}
