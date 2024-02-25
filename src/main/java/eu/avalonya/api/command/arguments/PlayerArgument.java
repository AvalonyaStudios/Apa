package eu.avalonya.api.command.arguments;

import eu.avalonya.api.command.Argument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerArgument extends Argument<Player>
{

    private final boolean onlineOnly;

    public PlayerArgument(boolean onlineOnly)
    {
        super();

        this.onlineOnly = onlineOnly;
    }

    public PlayerArgument()
    {
        this(false);
    }

    @Override
    public boolean test(String input)
    {
        OfflinePlayer player = Bukkit.getOfflinePlayer(input);

        if (onlineOnly)
        {
            return player.isOnline();
        }
        return player.hasPlayedBefore();
    }

    @Override
    public Player get()
    {
        return Bukkit.getOfflinePlayer(getInput()).getPlayer();
    }

    @Override
    public List<String> getCompletions()
    {
        List<String> completions = new ArrayList<>();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers())
        {
            if (onlineOnly && !player.isOnline())
            {
                continue;
            }
            completions.add(player.getName());
        }

        return completions;
    }

    @Override
    public String getUsage()
    {
        return "player";
    }
}
