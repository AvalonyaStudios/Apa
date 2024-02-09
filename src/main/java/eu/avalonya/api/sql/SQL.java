package eu.avalonya.api.sql;

import eu.avalonya.api.utils.MessageUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL
{

    private Connection connection;
    private String urlbase;
    private String host;
    private String db;
    private String user;
    private String pass;

    public SQL(String urlbase, String host, String db, String user, String pass)
    {
        this.urlbase = urlbase;
        this.host = host;
        this.db = db;
        this.user = user;
        this.pass = pass;
    }

    public void connection()
    {
        if(!isConnected())
        {
            try
            {
                this.connection = DriverManager.getConnection(this.urlbase + this.host + "/" + this.db + "?autoreconnect=true", this.user, this.pass);
                MessageUtils.sendConsoleMsg("&2[SQL] Connexion BDD OK!");
            }
            catch (SQLException e)
            {
                //e.printStackTrace();
                MessageUtils.sendConsoleMsg("&c[SQL] Erreur connexion !");
            }
        }
    }

    public void disconnect()
    {
        if(isConnected())
        {
            try
            {
                this.connection.close();
                MessageUtils.sendConsoleMsg("&2[SQL] Deconnexion BDD OK !");
            }
            catch (SQLException e)
            {
                MessageUtils.sendConsoleMsg("&c[SQL] Erreur deconnexion !");
            }
        }
    }

    public boolean isConnected()
    {
        return this.connection != null;
    }

    public Connection getConnection(){
        return this.connection;
    }


}
