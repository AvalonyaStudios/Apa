package eu.avalonya.api.utils;

import eu.avalonya.api.models.AvalonyaPlayer;
import eu.avalonya.api.models.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TabManager
{

    private static TabManager instance;
    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;

    public TabManager()
    {
        instance = this;
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        for (Rank rank : Rank.values()) {
            registerLeague(rank.getOrderTab(), rank.getPrefixTab());
        }
    }

    public static TabManager get()
    {
        if (instance == null)
            return new TabManager();
        return instance;
    }

    public void setTabRank(AvalonyaPlayer avalonyaPlayer)
    {
        Rank rank = avalonyaPlayer.getRank();
        Player player = avalonyaPlayer.getPlayer();

        if (rank != Rank.PLAYER)
        {
            scoreboard.getTeam(rank.getOrderTab()).addPlayer(player);
            player.setDisplayName(MessageUtils.convertColor(avalonyaPlayer.getRank().getPrefixTab() + player.getName()));
        }
        updateTab();
    }

    private void registerLeague(String teamName, String teamPrefix)
    {
        Team team = scoreboard.registerNewTeam(teamName);
        team.setPrefix(teamPrefix);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
    }

    public void updateTab()
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.setScoreboard(scoreboard);
        }
    }

    public ScoreboardManager getScoreboardManager()
    {
        return this.scoreboardManager;
    }

    public Scoreboard getScoreboard()
    {
        return this.scoreboard;
    }

}


