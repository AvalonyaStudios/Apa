package eu.avalonya.api.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import eu.avalonya.api.items.ItemAccess;
import it.unimi.dsi.fastutil.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Date;
import java.util.Map;

/**
 * Citizen model class that represents a citizen in the Avalonya api.
 */
@Getter
@Setter
@NoArgsConstructor
public class Citizen extends AbstractModel implements ItemAccess {

    private String uuid;
    private Town town;
    private long joinedAt;

    // Obsolete properties
    private int id;
    private AvalonyaPlayer player;
    private float money;
    private int role;
    private ItemStack playerHead;

    public Citizen(String uuid, Town town, long joinedAt) {
        this.uuid = uuid;
        this.town = town;
        this.joinedAt = joinedAt;
    }

    @Override
    public Pair<String, String> getId() {
        return Pair.of("citizen_id", uuid);
    }

    @Override
    public Map<String, String> getRepositoryAttributes() {
        return Map.of(
                "name", town.getName()
        );
    }

    public static Citizen deserialize(Map<String, Object> map) {
        final Citizen citizen = new Citizen();

        citizen.setUuid((String) map.get("uuid"));
        citizen.setJoinedAt(Double.valueOf((double) map.get("joined_at")).longValue());

        return citizen;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "uuid", this.uuid,
                "joined_at", joinedAt
        );
    }

    @Override
    public ItemStack toItemStack() {
        return null;
    }

    @Deprecated
    public void setRole(Role role) {
        this.role = role.ordinal();
    }

    public Role getRole() {
        return Role.values()[this.role];
    }

    public Date getJoinedAt() {
        return new Date(joinedAt);
    }

    @Deprecated
    public boolean isMayor() {
        return getRole() == Role.MAYOR;
    }
}
