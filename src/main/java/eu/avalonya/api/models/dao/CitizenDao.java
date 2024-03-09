package eu.avalonya.api.models.dao;

import eu.avalonya.api.models.AvalonyaDatabase;
import eu.avalonya.api.models.AvalonyaPlayer;
import eu.avalonya.api.models.Citizen;
import eu.avalonya.api.models.Town;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class CitizenDao
{

    public static Citizen create(Player player, Town town) throws SQLException
    {
        AvalonyaPlayer avalonyaPlayer = PlayerAvalonyaDao.getAvalonyaPlayer(player);
        Citizen citizen = new Citizen(avalonyaPlayer);

        citizen.setTown(town);
        AvalonyaDatabase.getCitizenDao().create(citizen);

        return find(player);
    }

    public static Citizen find(Player player) throws SQLException
    {
        return AvalonyaDatabase.getCitizenDao().queryBuilder().where().eq("uuid", player.getUniqueId().toString()).queryForFirst();
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
