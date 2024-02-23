package eu.avalonya.api.models;

import eu.avalonya.api.items.ItemAccess;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Citizen model class that represents a citizen in the Avalonya api.
 */
public class Citizen implements ItemAccess {

    private OfflinePlayer player;
    private ItemStack playerHead;
    private Town town;
    private long joinedAt;
    private Role role;

    public Citizen(OfflinePlayer player) {
        this.player = player;
        this.playerHead = new ItemStack(Material.PLAYER_HEAD);

        final SkullMeta itemMeta = (SkullMeta) playerHead.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setOwningPlayer(player);
            itemMeta.displayName(Component.text("§f" + player.getName()));
            playerHead.setItemMeta(itemMeta);
        }
    }

    public boolean isMayor() {
        return town.getMayor().equals(this);
    }

    public void setTown(Town town) {
        this.town = town;
        this.joinedAt = System.currentTimeMillis();
    }

    public Town getTown() {
        return town;
    }

    public void setJoinedAt(long joinedAt) {
        this.joinedAt = joinedAt;
    }

    public long getJoinedAt() {
        return joinedAt;
    }

    public boolean isPlayer(Player player) {
        final Player p = this.player.getPlayer();

        if (p == null) {
            return false;
        }

        return p.equals(player);
    }

    public Player getPlayer() {
        return player.getPlayer();
    }

    public void setRole(Role role){
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public ItemStack toItemStack() {
        return playerHead;
    }
}
