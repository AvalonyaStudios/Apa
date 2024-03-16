package eu.avalonya.api.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import lombok.Getter;

import java.sql.SQLException;

public class AvalonyaDatabase
{

    @Getter
    private static Dao<AvalonyaPlayer, String> playerDao = null;
    @Getter
    private static Dao<Town, Integer> townDao = null;
    @Getter
    private static Dao<Citizen, Integer> citizenDao = null;
    @Getter
    private static Dao<Plot, Integer> plotDao = null;
    @Getter
    private static Dao<Role.Custom, Integer> roleDao = null;

    public AvalonyaDatabase(String path, String user, String password) throws SQLException
    {
        ConnectionSource connectionSource = new JdbcConnectionSource(path, user, password);
        playerDao = DaoManager.createDao(connectionSource, AvalonyaPlayer.class);
        townDao = DaoManager.createDao(connectionSource, Town.class);
        citizenDao = DaoManager.createDao(connectionSource, Citizen.class);
        plotDao = DaoManager.createDao(connectionSource, Plot.class);
        roleDao = DaoManager.createDao(connectionSource, Role.Custom.class);
    }

}
