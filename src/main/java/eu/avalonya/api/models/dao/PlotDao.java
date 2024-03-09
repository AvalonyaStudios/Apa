package eu.avalonya.api.models.dao;

import eu.avalonya.api.models.AvalonyaDatabase;
import eu.avalonya.api.models.Plot;
import eu.avalonya.api.models.Town;
import org.bukkit.Chunk;

import java.sql.SQLException;
import java.util.List;

public class PlotDao
{

    public static Plot create(Town town, Chunk chunk) throws SQLException
    {
        Plot plot = new Plot(chunk, town);

        AvalonyaDatabase.getPlotDao().create(plot);

        return plot;
    }

    public static Plot getPlot(Chunk chunk) throws SQLException
    {
        return AvalonyaDatabase.getPlotDao().queryBuilder().where().eq("x", chunk.getX()).and().eq("z", chunk.getZ()).queryForFirst();
    }

    public static List<Plot> getPlots(Town town) throws SQLException
    {
        return AvalonyaDatabase.getPlotDao().queryForEq("town_id", town.getId());
    }

    public static boolean isClaimed(Chunk chunk) throws SQLException
    {
        return getPlot(chunk) != null;
    }

    public static boolean hasTown(Chunk chunk, Town town) throws SQLException
    {
        Plot plot = getPlot(chunk);
        return plot != null && plot.getTown().getId() == town.getId();
    }

    public static void delete(Plot plot) throws SQLException
    {
        AvalonyaDatabase.getPlotDao().delete(plot);
    }

    public static void delete(Chunk chunk) throws SQLException
    {
        AvalonyaDatabase.getPlotDao().delete(getPlot(chunk));
    }

    public static void update(Plot plot) throws SQLException
    {
        AvalonyaDatabase.getPlotDao().update(plot);
    }

}
