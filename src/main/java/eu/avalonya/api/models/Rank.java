package eu.avalonya.api.models;

import eu.avalonya.api.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the different ranks available.
 * Each rank has a name, prefixes for chat and tab, associated colors, a rank ID, etc.
 */
public enum Rank
{

    ADMIN("Administrateur", "§4§lAdministrateur ", "§4[Admin] ", "§f", "§4", "A_admin", 100),
    RESP("Responsable", "§6§lResponsable ", "§6[Resp] ", "§f", "§6", "B_admin", 90),
    SYS_ADMIN("SysAdmin", "§e§lSysAdmin ", "§e[Sysadmin] ", "§f", "§e", "C_admin", 80),
    DEV("Développeur", "§9§lDéveloppeur ", "§9[Dev] ", "§f", "§9", "D_admin", 70),
    CONF("Configurateur", "§b§lConfigurateur ", "§b[Config] ", "§f", "§b", "E_admin", 60),
    BUILDER("Builder", "§a§lBuilder ", "§a[Builder] ", "§f", "§a", "F_admin", 50),
    WRITER("Scénariste", "§2§lScénariste ", "§2[Scénariste] ", "§f", "§2", "G_admin", 40),
    PLAYER("Joueur", "§7§l", "§7", "§7", "§7", "Z_admin", 0);

    private String name;
    private String prefixChat;
    private String prefixTab;
    private String colorChat;
    private String colorName;
    private String orderTab;
    private int rankId;

    // Static map to map rank IDs to corresponding ranks
    public static Map<Integer, Rank> rankIdToRank = new HashMap<>();

    Rank(String name, String prefixChat, String prefixTab, String colorChat, String colorName, String orderTab, int rankId)
    {
        this.name = name;
        this.prefixChat = prefixChat;
        this.prefixTab = prefixTab;
        this.colorChat = colorChat;
        this.colorName = colorName;
        this.orderTab = orderTab;
        this.rankId = rankId;
    }

    // Static block to initialize the rankIdToRank map
    static
    {
        for(Rank rank: Rank.values())
        {
            rankIdToRank.put(rank.getRankId(), rank);
        }
    }

    // For debug purpose only
    private static void printPrefixesToConsole(Player p)
    {
        for (Rank rank : Rank.values()) {
            p.sendMessage(MessageUtils.convertColor(rank.getPrefixChat() + rank.getColorName() + p.getName()));
            p.sendMessage(MessageUtils.convertColor(rank.getPrefixTab() + rank.getColorName() + p .getName()));
        }
    }

    public static int getIdFromName(String name)
    {
        for (Rank rank : Rank.values())
        {
            if (rank.name.equals(name))
            {
                return rank.rankId;
            }
        }
        return -1;
    }

    public static ArrayList<String> getRanksName()
    {
        ArrayList<String> ranks = new ArrayList<>();
        for (Rank rank : Rank.values())
        {
            ranks.add(rank.getName());
        }
        return ranks;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrefixChat()
    {
        return prefixChat;
    }

    public void setPrefixChat(String prefixChat)
    {
        this.prefixChat = prefixChat;
    }

    public String getPrefixTab()
    {
        return prefixTab;
    }

    public void setPrefixTab(String prefixTab)
    {
        this.prefixTab = prefixTab;
    }

    public String getColorChat()
    {
        return colorChat;
    }

    public void setColorChat(String colorChat)
    {
        this.colorChat = colorChat;
    }

    public String getColorName()
    {
        return colorName;
    }

    public void setColorName(String colorName)
    {
        this.colorName = colorName;
    }

    public String getOrderTab()
    {
        return orderTab;
    }

    public void setOrderTab(String orderTab)
    {
        this.orderTab = orderTab;
    }

    public int getRankId()
    {
        return rankId;
    }

    public void setRankId(int rankId)
    {
        this.rankId = rankId;
    }

    public static Map<Integer, Rank> getRankIdToRank()
    {
        return rankIdToRank;
    }

    public static void setRankIdToRank(Map<Integer, Rank> rankIdToRank) {
        Rank.rankIdToRank = rankIdToRank;
    }

}
