package eu.avalonya.api.models;

import eu.avalonya.api.AvalonyaAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AvalonyaPlayer
{

    private Player player;
    private UUID uuid;
    private Rank rank;

    public AvalonyaPlayer(Player player, UUID uuid, int rank)
    {
        this.player = player;
        this.uuid = uuid;
        this.rank = Rank.rankIdToRank.get(rank);
    }

    public void setPermissions(List<String> permissions)
    {
        for(String perm : permissions)
        {
            if (perm.contains("*")) // Donne au joueur toutes les permissions pour les commandes de bases
            {
                for (Permission perms : getAllDefaultPerms())
                {
                    this.player.addAttachment(AvalonyaAPI.getInstance()).setPermission(perms.getName(), true);
                }
            }
            else
            {
                this.player.addAttachment(AvalonyaAPI.getInstance()).setPermission(perm, true);
            }
        }
        this.player.recalculatePermissions();
        this.player.updateCommands();
    }

    /**
     * Retourne la liste de toutes les permissions de toutes les commandes par default (comme par exemple /gamemode, /stop, etc..)
     * @return Liste de permissions
     */
    public List<Permission> getAllDefaultPerms()
    {
        List<Permission> perms = new ArrayList<>();
        for (Permission perm : Bukkit.getPluginManager().getPermissions())
        {
            if (!perms.contains(perm))
            {
                perms.add(perm);
            }
        }
        return perms;
    }

    public String getChatFormat()
    {
        return this.getRank().getPrefixChat() + this.getPlayer().getName() + this.getRank().getColorChat() + " » ";
    }
    public Player getPlayer()
    {
        return player;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public Rank getRank()
    {
        return rank;
    }

}
