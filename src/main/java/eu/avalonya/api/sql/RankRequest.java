package eu.avalonya.api.sql;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.models.AvalonyaPlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class manages all rank SQL requests
 */
public class RankRequest
{

    public static void createAvalonyaPlayerInDB(Player p)
    {
        // Double check, just in case
        if(!isPlayerExistsInDB(p))
        {
            AvalonyaAPI.getInstance().getLogger().info("SQL - createPlayer [" + p.getName() + "]");
            PreparedStatement r;
            try
            {
                r = AvalonyaAPI.getSqlInstance().getConnection().prepareStatement("INSERT INTO `player` VALUES"
                        + "('" + p.getUniqueId() + "', "
                        + "'" + 0 + "')");
                r.execute();
                r.close();
            }
            catch (IllegalArgumentException | SQLException e)
            {
                AvalonyaAPI.getInstance().getLogger().severe("SQL - createPlayer - [" + p.getName() + "] : " + e.getMessage());
                e.printStackTrace();
            }
        }
        else
        {
            AvalonyaAPI.getInstance().getLogger().warning("Trying to create an existing user !");
        }
    }

    public static boolean isPlayerExistsInDB(Player p)
    {
        try
        {
            Connection connection = AvalonyaAPI.getSqlInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT UUID FROM `player` WHERE UUID = '" + p.getUniqueId() + "'");
            ResultSet res = statement.executeQuery();
            boolean hasAccount = res.next();
            statement.close();
            return hasAccount;
        }
        catch (SQLException e)
        {
            AvalonyaAPI.getInstance().getLogger().severe("SQL - ifPlayerExists [" + p.getName() + "] : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public static AvalonyaPlayer getPlayerInDB(Player p)
    {
        try
        {
            PreparedStatement r = AvalonyaAPI.getSqlInstance().getConnection().prepareStatement("SELECT * FROM `player` WHERE UUID = '" + p.getUniqueId() +"'");
            ResultSet res = r.executeQuery();
            res.next();

            AvalonyaPlayer avalonyaPlayer = new AvalonyaPlayer(
                    p,
                    p.getUniqueId(),
                    res.getInt("rank_id")
            );
            Cache.avaloniaPlayers.put(p.getUniqueId(), avalonyaPlayer);
            r.close();
            return Cache.avaloniaPlayers.get(p.getUniqueId());
        }
        catch(SQLException e)
        {
            AvalonyaAPI.getInstance().getLogger().severe("SQL - getPlayer [" + p.getName() + "] : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
