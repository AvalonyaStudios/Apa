package eu.avalonya.api.models.dao;

import eu.avalonya.api.models.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class CitizenDao
{

    public static Citizen create(Player player, Town town) throws SQLException
    {
        AvalonyaPlayer avalonyaPlayer = PlayerAvalonyaDao.getAvalonyaPlayer(player);
        Citizen citizen = new Citizen(avalonyaPlayer.getUuid(), town, System.currentTimeMillis());

        citizen.setTown(town);
        AvalonyaDatabase.getCitizenDao().create(citizen);

        return find(player);
    }

    public static void create(Player player, Town town, Role role) throws SQLException
    {
        AvalonyaPlayer avalonyaPlayer = PlayerAvalonyaDao.getAvalonyaPlayer(player);
        Citizen citizen = new Citizen(avalonyaPlayer.getUuid(), town, System.currentTimeMillis());

        citizen.setTown(town);
        citizen.setRole(role);
        AvalonyaDatabase.getCitizenDao().create(citizen);
    }

    public static Citizen find(Player player) throws SQLException
    {
        return AvalonyaDatabase.getCitizenDao().queryBuilder().where().eq("uuid", player.getUniqueId().toString()).queryForFirst();
    }

    public static List<Citizen> getByRole(Town town, int role) throws SQLException
    {
        return AvalonyaDatabase.getCitizenDao().queryBuilder().where().eq("town_id", town.getId()).and().eq("role", role).query();
    }

    public static List<Citizen> getByTown(Town town) throws SQLException
    {
        return AvalonyaDatabase.getCitizenDao().queryForEq("town_id", town.getId());
    }

    public static void update(Citizen citizen) throws SQLException
    {
        AvalonyaDatabase.getCitizenDao().update(citizen);
    }

}
