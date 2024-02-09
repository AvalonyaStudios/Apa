package eu.avalonya.api.sql;

import eu.avalonya.api.AvalonyaAPI;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public abstract class Migration
{

    public abstract void execute();

    public void execute(String sql)
    {
        try
        {
            PreparedStatement r = AvalonyaAPI.getSqlInstance().getConnection().prepareStatement(sql);
            r.execute();
            r.close();
        }
        catch (SQLException e)
        {
            AvalonyaAPI.getInstance().getLogger().severe(Arrays.toString(e.getStackTrace()));
        }
    }

}
