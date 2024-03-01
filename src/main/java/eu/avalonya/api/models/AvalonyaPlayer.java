package eu.avalonya.api.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import eu.avalonya.api.AvalonyaAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "player")
@Getter
@Setter
public class AvalonyaPlayer
{

    @DatabaseField(id = true)
    private String uuid;

    @DatabaseField(canBeNull = false)
    private String pseudo;

    @DatabaseField(columnName = "rank_id", canBeNull = false, defaultValue = "0")
    private int rankId;

    @DatabaseField(columnName = "last_login", canBeNull = false)
    private Timestamp lastLogin;

    @DatabaseField(columnName = "first_login", canBeNull = false)
    private Timestamp firstLogin;

    @DatabaseField(columnName = "last_ip", canBeNull = false)
    private String lastIp;

    private Rank rank; // Not stored in db, but set in memory

    private Player player; // Not stored in db, but set in memory

    public AvalonyaPlayer(){}

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

}
