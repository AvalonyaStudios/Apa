package eu.avalonya.api.command.admin;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.command.BaseCommand;
import eu.avalonya.api.models.Rank;
import eu.avalonya.api.sql.RankRequest;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetRankCommand extends BaseCommand implements TabCompleter {

    public SetRankCommand()
    {
        super("setrank", SenderType.ALL);
        this.addPermissions("avalonya.admin");
    }
    @Override
    public void run(CommandSender sender, SenderType senderType, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(getHelp());
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore())
        {
            sender.sendMessage("§c➤ Joueur introuvable");
            return;
        }

        String rank = args[1];
        if (Rank.getRanksName().contains(rank))
        {
            int rankId = Rank.getIdFromName(rank);
            if (rankId == -1)
            {
                sender.sendMessage("§c➤ Rang introuvable");
                return;
            }
            try
            {
                RankRequest.setRankInDb(target, rankId);
                sender.sendMessage("§2➤ Mise à jour du rang du joueur §l" + target.getName() + "§r§2 à §l" + rank);
                if (target.isOnline()) ((Player) target).kick(Component.text("§2Votre rang vient d'être mit à jour à §l" + rank));
            }
            catch (Exception e)
            {
                sender.sendMessage("§c➤ Une erreur est survenue : " + e.getMessage());
            }
        }
        else
        {
            sender.sendMessage("§c➤ Rang introuvable");
            sender.sendMessage(getHelp());
        }

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        List<String> completions = new ArrayList<>();

        if (strings.length == 1)
        {
            for(Player p : AvalonyaAPI.getInstance().getServer().getOnlinePlayers())
            {
                completions.add(p.getName());
            }
        }

        if (strings.length == 2)
        {
            completions.addAll(Rank.getRanksName());
        }

        return new ArrayList<>(completions);
    }

    public String getHelp()
    {
        StringBuilder usage = new StringBuilder("§c➤ Utilisation : /setrank <player> <rank>\n");
        usage.append("Rangs disponibles : \n");
        for(Rank r : Rank.values())
        {
            usage.append("    §6- " + r.getColorName() + r.getName() + "§r\n");
        }
        return usage.toString();
    }

}
