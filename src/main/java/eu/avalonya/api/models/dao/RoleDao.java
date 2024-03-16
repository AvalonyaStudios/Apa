package eu.avalonya.api.models.dao;

import eu.avalonya.api.models.AvalonyaDatabase;
import eu.avalonya.api.models.Role;
import eu.avalonya.api.models.Town;

import java.sql.SQLException;

public class RoleDao
{

    public static void create(String name, Town town) throws SQLException
    {
        Role.Custom role = new Role.Custom(name.toLowerCase(), town);

        AvalonyaDatabase.getRoleDao().create(role);
    }

}
