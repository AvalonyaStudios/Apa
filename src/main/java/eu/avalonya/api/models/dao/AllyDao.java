package eu.avalonya.api.models.dao;

import eu.avalonya.api.models.Ally;
import eu.avalonya.api.models.AvalonyaDatabase;
import eu.avalonya.api.models.Town;

import java.sql.SQLException;
import java.util.List;

public class AllyDao {

    public static void create(Town sender, Town receiver) throws SQLException
    {
        Ally ally = new Ally(sender, receiver);
        AvalonyaDatabase.getAllyDao().create(ally);
    }

    public static void delete(Ally ally) throws SQLException
    {
        AvalonyaDatabase.getAllyDao().delete(ally);
    }

    public static void update(Ally ally) throws SQLException
    {
        AvalonyaDatabase.getAllyDao().update(ally);
    }

    public static Ally getAlly(int id) throws SQLException
    {
        return AvalonyaDatabase.getAllyDao().queryForId(id);
    }

    public boolean inPienging(Ally ally) throws SQLException
    {
        return AvalonyaDatabase.getAllyDao().queryForSameId(ally).isPending();
    }

    public List<Ally> getAllies() throws SQLException
    {
        return AvalonyaDatabase.getAllyDao().queryForAll();
    }
}
