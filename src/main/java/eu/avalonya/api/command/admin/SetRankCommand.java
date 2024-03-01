package eu.avalonya.api.command.admin;

import eu.avalonya.api.command.ArgumentCollection;
import eu.avalonya.api.command.BaseCommand;
import eu.avalonya.api.command.arguments.ChoiceArgument;
import eu.avalonya.api.command.arguments.PlayerArgument;
import eu.avalonya.api.models.Rank;
import eu.avalonya.api.models.dao.PlayerAvalonyaDao;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class SetRankCommand extends BaseCommand<CommandSender> implements TabCompleter {

    public SetRankCommand()
    {
        super("setrank");
        this.addPermissions("avalonya.admin");
        this.addArgument(PlayerArgument.class, true);
        this.addArgument(new ChoiceArgument(Rank.getRanksName(), true));
    }
    @Override
    public void run(CommandSender sender, ArgumentCollection args)
    {
        Player target = args.get(0, Player.class);

        String rank = args.get(1, String.class);

        int rankId = Rank.getIdFromName(rank);
        if (rankId == -1)
        {
            sender.sendMessage("§c➤ Rang introuvable");
            return;
        }
        try
        {
            PlayerAvalonyaDao.updateRank(target, rankId);
            sender.sendMessage("§2➤ Mise à jour du rang du joueur §l" + target.getName() + "§r§2 à §l" + rank);
            if (target.isOnline()) target.kick(Component.text("§2Votre rang vient d'être mit à jour à §l" + rank));
        }
        catch (Exception e)
        {
            sender.sendMessage("§c➤ Une erreur est survenue : " + e.getMessage());
        }
    }

    @Override
    public String getUsage()
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
