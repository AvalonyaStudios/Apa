package eu.avalonya.api.sql;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.sql.migrations.MigrationMapping;

import java.sql.*;

public class MigrationUtils
{

    public static boolean doesTableExist(String tableName)
    {
        try
        {
            Connection connection = AvalonyaAPI.getSqlInstance().getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet resultSet = metaData.getTables(null, null, tableName, null);

           boolean r = resultSet.next();
           resultSet.close();

           return r;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertOrUpdateCurrentMigrationVersion(int newVersion)
    {
        try
        {
            Connection connection = AvalonyaAPI.getSqlInstance().getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;

            // Vérifier si une version de migration existe déjà
            statement = connection.prepareStatement("SELECT * FROM migration_version");
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                // Mettre à jour la version de migration existante
                statement = connection.prepareStatement("UPDATE migration_version SET version = ?");
                statement.setInt(1, newVersion);
                statement.executeUpdate();
            }
            else
            {
                // Insérer une nouvelle ligne pour la version de migration
                statement = connection.prepareStatement("INSERT INTO migration_version (version) VALUES (?)");
                statement.setInt(1, newVersion);
                statement.executeUpdate();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getCurrentMigrationVersion()
   {
       int version = -1;

       try
       {
           Connection connection = AvalonyaAPI.getSqlInstance().getConnection();
           PreparedStatement statement = connection.prepareStatement("SELECT version FROM migration_version");
           ResultSet resultSet = statement.executeQuery();

           if (resultSet.next())
           {
               version = resultSet.getInt("version");
           }
           else
           {
               System.out.println("Aucune ligne trouvée dans la table de migration.");
           }

           statement.close();

       }
       catch (SQLException e)
       {
           e.printStackTrace();
       }

       return version;
   }

   public void executeMigration(Migration migration)
   {
       migration.execute();
   }

    /**
     * Si la table `migration_version` n'existe pas, elle est crée
     * Si elle existe, on récupère la version actuelle
     * Dans tous les cas on tente de migrer la base
     */
   public static void checkCurrentVersion()
   {
       int currentVersion = -1;

       if(MigrationUtils.doesTableExist("migration_version"))
       {
           currentVersion = MigrationUtils.getCurrentMigrationVersion();
           AvalonyaAPI.getInstance().getLogger().info("Current migration version : " + currentVersion);
       }
       else
       {
           AvalonyaAPI.getInstance().getLogger().warning("`migration_version` table does not exists. Run the creation.");
           Migration migration1;
           try
           {
               migration1 = MigrationMapping.createMigrationById(0);
           }
           catch (IllegalAccessException | InstantiationException e)
           {
               throw new RuntimeException(e);
           }
           migration1.execute();
           currentVersion = 0;
           MigrationUtils.insertOrUpdateCurrentMigrationVersion(currentVersion);
       }
       upgradeVersionIfNeeded(currentVersion);
   }

   private static void upgradeVersionIfNeeded(int currentVersion)
   {
       if(currentVersion == MigrationMapping.values().length)
       {
           AvalonyaAPI.getInstance().getLogger().info("Database is up to date");
       }
       else if(currentVersion < MigrationMapping.values().length)
       {
           AvalonyaAPI.getInstance().getLogger().info("Database is not up to date, need to be updated.");
           for(int i = currentVersion + 1; i < (MigrationMapping.values().length); i++)
           {
               Migration migration;
               try
               {
                   migration = MigrationMapping.createMigrationById(i);
               }
               catch (IllegalAccessException | InstantiationException e)
               {
                   throw new RuntimeException(e);
               }
               AvalonyaAPI.getInstance().getLogger().info("Apply migration number : " + i);
               migration.execute();
               MigrationUtils.insertOrUpdateCurrentMigrationVersion(i);
           }
           AvalonyaAPI.getInstance().getLogger().info("Database is up to date.");
       }
       else
       {
           AvalonyaAPI.getInstance().getLogger().severe("Error during migration");
       }
   }


}
