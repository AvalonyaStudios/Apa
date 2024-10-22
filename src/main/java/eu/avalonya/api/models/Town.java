package eu.avalonya.api.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import eu.avalonya.api.items.ItemAccess;
import eu.avalonya.api.models.dao.PlotDao;
import eu.avalonya.api.models.enums.TownPermission;
import eu.avalonya.api.repository.CitizenRepository;
import eu.avalonya.api.repository.PlotRepository;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Getter
@Setter
@NoArgsConstructor
public class Town extends AbstractModel implements ItemAccess {

    private int id;
    private String name;
    private String politicalStatus = "";
    private float money = 0.0f;
    private float taxes = 0.0f;
    private int permissions = 0;
    private String spawnLocation;
    private Date createdAt;

    public CitizenRepository getCitizens() {
        return new CitizenRepository(List.of(this.name));
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

    public PlotRepository getPlots() {
        return new PlotRepository(List.of(this.name));
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

            this.getPlots().save(new Plot(chunk, this));
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

    public boolean hasPermission(TownPermission permission) {
        return (this.permissions & (1 << permission.ordinal())) != 0;
    }

    public void addPermission(TownPermission permission) {
        this.permissions |= (1 << permission.ordinal());
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

    @Override
    public Pair<String, String> getId() {
        return Pair.of("town_name", this.name);
    }

    @Override
    public Map<String, String> getRepositoryAttributes() {
        return Map.of(
                "town_name", this.name
        );
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "name", this.name
        );
    }
}
