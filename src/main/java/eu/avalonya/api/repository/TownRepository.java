package eu.avalonya.api.repository;

import eu.avalonya.api.http.Endpoint;
import eu.avalonya.api.models.Town;

import java.util.List;
import java.util.Map;

public class TownRepository extends AbstractRepository<Town> {
    public TownRepository(List<String> vars) {
        super(Town.class, vars);
    }

    @Override
    public Map<String, Endpoint> getEndpoints() {
        return Map.of(
                "all", Endpoint.TOWNS,
                "create", Endpoint.TOWNS,
                "update", Endpoint.TOWNS_NAME,
                "get", Endpoint.TOWNS_NAME,
                "delete", Endpoint.TOWNS_NAME
        );
    }
}
