package eu.avalonya.api.repository;

import eu.avalonya.api.http.Endpoint;
import eu.avalonya.api.models.Plot;

import java.util.List;
import java.util.Map;

public class PlotRepository extends AbstractRepository<Plot> {
    public PlotRepository(List<String> vars) {
        super(Plot.class, vars);
    }

    @Override
    public Map<String, Endpoint> getEndpoints() {
        return Map.of(
                "all", Endpoint.TOWN_PLOTS,
                "create", Endpoint.TOWN_PLOTS,
                "update", Endpoint.TOWN_PLOTS_ID,
                "delete", Endpoint.TOWN_PLOTS_ID
        );
    }
}
