package eu.avalonya.api.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.avalonya.api.AvalonyaAPI;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

@Getter
@Setter
@NoArgsConstructor
public class Player extends AbstractModel {

    private String uuid;
    private String pseudo;
    private int rankId;
    private long firstLogin;
    private String lastIp;
    private double money;

    public Rank getRank() {
        return Rank.values()[this.rankId];
    }

    public org.bukkit.entity.Player getBukkitPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public void setPermissions(List<String> permissions) {

        org.bukkit.entity.Player bukkitPlayer = getBukkitPlayer();

        for (String perm : permissions) {
            if (perm.contains("*")) // Donne au joueur toutes les permissions pour les commandes de bases
            {
                for (Permission perms : getAllDefaultPerms()) {
                    bukkitPlayer.addAttachment(AvalonyaAPI.getInstance()).setPermission(perms.getName(), true);
                }
            } else {
                bukkitPlayer.addAttachment(AvalonyaAPI.getInstance()).setPermission(perm, true);
            }
        }
        bukkitPlayer.recalculatePermissions();
        bukkitPlayer.updateCommands();
    }

    /**
     * Retourne la liste de toutes les permissions de toutes les commandes par default (comme par exemple /gamemode, /stop, etc..)
     *
     * @return Liste de permissions
     */
    public List<Permission> getAllDefaultPerms() {
        List<Permission> perms = new ArrayList<>();
        for (Permission perm : Bukkit.getPluginManager().getPermissions()) {
            if (!perms.contains(perm)) {
                perms.add(perm);
            }
        }
        return perms;
    }

    public String getChatFormat() {
        return this.getRank().getPrefixChat() + this.getBukkitPlayer().getName() + this.getRank().getColorChat() + " » ";
    }

    @Override
    public Pair<String, String> getId() {
        return Pair.of("player_id", this.uuid);
    }

    @Override
    public Map<String, String> getRepositoryAttributes() {
        return Map.of();
    }

    public static Player deserialize(Map<String, Object> data) {
        final Player player = new Player();

        player.setUuid((String) data.get("uuid"));
        player.setPseudo((String) data.get("pseudo"));
        player.setRankId((int) data.get("rank"));
        player.setFirstLogin((long) data.get("first_login"));
        player.setLastIp((String) data.get("last_ip"));
        player.setMoney((double) data.get("money"));

        return player;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "uuid", this.uuid,
                "pseudo", this.pseudo,
                "rank_id", this.rankId,
                "first_login", this.firstLogin,
                "last_ip", this.lastIp,
                "money", this.money
        );
    }
}
