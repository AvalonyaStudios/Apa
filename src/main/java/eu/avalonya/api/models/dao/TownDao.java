package eu.avalonya.api.models.dao;

import eu.avalonya.api.exceptions.CitizenAlreadyHasTownException;
import eu.avalonya.api.models.AvalonyaDatabase;
import eu.avalonya.api.models.Citizen;
import eu.avalonya.api.models.Role;
import eu.avalonya.api.models.Town;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public class TownDao
{

    public static Town create(String name, Player player) throws SQLException, CitizenAlreadyHasTownException
    {
        Town town = new Town();
        town.setName(name);
        Citizen mayor = CitizenDao.find(player);

        town.setSpawnLocation(player.getLocation().toString());

        if(mayor == null)
        {
            AvalonyaDatabase.getTownDao().create(town);
            CitizenDao.create(player, town, Role.MAYOR);
        }
        else if (mayor.getTown() != null)
        {
            throw new CitizenAlreadyHasTownException();
        }
        else
        {
            mayor.setRole(Role.MAYOR);
            mayor.setTown(town);

            AvalonyaDatabase.getTownDao().create(town);
            AvalonyaDatabase.getCitizenDao().update(mayor);
        }

        return town;
    }

    public static @Nullable Town getTown(int id) throws SQLException
    {
        return AvalonyaDatabase.getTownDao().queryForId(id);
    }

    public static @Nullable Town getTown(String name) throws SQLException
    {
        return AvalonyaDatabase.getTownDao().queryBuilder().where().eq("name", name).queryForFirst();
    }

    public static void update(Town town) throws SQLException
    {
        AvalonyaDatabase.getTownDao().update(town);
    }

    public static void delete(Town town) throws SQLException
    {
        AvalonyaDatabase.getTownDao().delete(town);
    }

    public static void delete(String name) throws SQLException
    {
        AvalonyaDatabase.getTownDao().delete(AvalonyaDatabase.getTownDao().queryBuilder().where().eq("name", name).queryForFirst());
    }

}