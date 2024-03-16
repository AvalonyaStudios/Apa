package eu.avalonya.api.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import eu.avalonya.api.items.ItemAccess;
import eu.avalonya.api.models.dao.CitizenDao;
import eu.avalonya.api.models.dao.PlotDao;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.sql.SQLException;
import java.util.*;

/**
 * Town model class that represents a town in the Avalonya api.
 */
@DatabaseTable(tableName = "towns")
public class Town implements ItemAccess {

    @DatabaseField(generatedId = true)
    @Getter
    private int id;

    @DatabaseField(canBeNull = false, unique = true)
    @Getter
    @Setter
    private String name;

    @DatabaseField(columnName = "politics", defaultValue = "")
    @Getter
    @Setter
    private String politicalStatus = "";

    @DatabaseField(defaultValue = "0", dataType = DataType.FLOAT)
    @Getter
    private float money = 0.0f;

    @DatabaseField(defaultValue = "0", dataType = DataType.FLOAT)
    @Getter
    @Setter
    private float taxes = 0.0f;

    @DatabaseField(columnName = "taxes_enabled", defaultValue = "false", dataType = DataType.BOOLEAN)
    @Setter
    private boolean taxesEnabled = false;

    @DatabaseField(columnName = "spawn_hostile_mob", defaultValue = "false", dataType = DataType.BOOLEAN)
    @Setter
    private boolean spawnHostileMob = false;

    @DatabaseField(columnName = "fire_spread", defaultValue = "false", dataType = DataType.BOOLEAN)
    @Setter
    private boolean fireSpread = false;

    @DatabaseField(columnName = "explosions", defaultValue = "false", dataType = DataType.BOOLEAN)
    @Setter
    private boolean explosions = false;

    @DatabaseField(columnName = "public", defaultValue = "false", dataType = DataType.BOOLEAN)
    private boolean publicTown = false;

    @DatabaseField(columnName = "friendly_fire", defaultValue = "false", dataType = DataType.BOOLEAN)
    @Setter
    private boolean friendlyFire = false;

    @DatabaseField(columnName = "spawn_location", dataType = DataType.STRING)
    @Getter
    @Setter
    private String spawnLocation;

    @DatabaseField(columnName= "created_at")
    @Getter
    private Date createdAt;

    public Town()
    {
        this.createdAt = new Date();
        // Required by ORMLite
    }

    public Town(String name)
    {
        this.name = name;
        this.createdAt = new Date();
    }

    public float deposit(float amount) {
        return money += amount;
    }

    public float withdraw(float amount) {
        return money -= amount;
    }

    public float increaseTaxes(float amount) {
        this.taxes += amount;

        return taxes;
    }

    public float decreaseTaxes(float amount) {
        this.taxes -= amount;

        return taxes;
    }

    public List<Plot> getPlots()
    {
        try {
            return PlotDao.getPlots(this);
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: Utiliser le logger
        }

        return new ArrayList<>();
    }

    /**
     * Ajoute un chunk à la ville ou change le propriétaire du chunk si il est déjà revendiqué.
     * @param chunk
     */
    public void addPlot(Chunk chunk)
    {
        try
        {
            if (PlotDao.isClaimed(chunk))
            {
                Plot plot = PlotDao.getPlot(chunk);

                if (plot.getTown().equals(this))
                {
                    return;
                }

                plot.setTown(this);
                PlotDao.update(plot);
            }
            else
            {
                PlotDao.create(this, chunk);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // TODO: Utiliser le logger
        }
    }

    /**
     * Action que peut effectuer une ville sur un chunk.
     * Ainsi une sertraine action peut être effectuée sur un chunk.
     * @param player le joueur essayant de revendiquer le chunk
     * @param chunk le chunk visé
     */
    public void claim(Player player, Chunk chunk)
    {
        try
        {
            if (PlotDao.isClaimed(chunk))
            {
                player.sendMessage(Component.text("§cCe chunk est déjà revendiqué."));
                return;
            }

            addPlot(chunk);
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // TODO: Utiliser le logger
        }
    }

    /**
     * Supprime un chunk de la ville.
     * @param chunk le chunk à supprimer
     */
    public void unclaim(Player player, Chunk chunk)
    {
        try
        {
            if (!PlotDao.hasTown(chunk, this))
            {
                player.sendMessage(Component.text("§cCe chunk n'appartient pas à votre ville."));
                return;
            }

            PlotDao.delete(chunk);
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // TODO: Utiliser le logger
        }
    }

    public Citizen getMayor()
    {
        try
        {
            return CitizenDao.getByRole(this, Role.MAYOR.ordinal()).get(0);
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // TODO: Utiliser le logger
        }

        return null;
    }

    public List<Citizen> getCitizens()
    {
        try
        {
            return CitizenDao.getByTown(this);
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // TODO: Utiliser le logger
        }

        return new ArrayList<>();
    }

    public boolean hasTaxesEnabled() {
        return taxesEnabled;
    }

    public boolean hasSpawnHostileMob() {
        return spawnHostileMob;
    }

    public boolean hasFireSpread() {
        return fireSpread;
    }

    public boolean hasExplosions() {
        return explosions;
    }

    public void setPublic(boolean publicTown) {
        this.publicTown = publicTown;
    }

    public boolean isPublic() {
        return publicTown;
    }

    public boolean hasFriendlyFire() {
        return friendlyFire;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(Material.WHITE_BANNER);
        final BannerMeta meta = (BannerMeta) item.getItemMeta();

        //meta.setPatterns(bannerPatterns);
        meta.displayName(Component.text("§f§l" + name + " §7Flag"));
        item.setItemMeta(meta);

        return item;
    }

}
