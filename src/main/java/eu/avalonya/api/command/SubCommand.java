package eu.avalonya.api.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCommand implements ICommand
{

    private final String name;
    private final ICommand command;
    private final List<String> permissions = new ArrayList<>();
    private int cooldown = 0;
    private final Map<CommandSender, Long> cooldowns = new HashMap<>();

    public SubCommand(String name, ICommand command)
    {
        this.name = name;
        this.command = command;
    }

    public String getName()
    {
        return name;
    }

    public ICommand getCommand()
    {
        return command;
    }

    public List<String> getPermissions()
    {
        return permissions;
    }

    public void addPermissions(String... permissions)
    {
        this.permissions.addAll(List.of(permissions));
    }

    public boolean hasCooldown()
    {
        return cooldown > 0;
    }

    public void setCooldown(int cooldown)
    {
        this.cooldown = cooldown;
    }

    public boolean canExecute(CommandSender sender)
    {
        return permissions.stream().anyMatch(sender::hasPermission) || permissions.isEmpty();
    }

    @Override
    public void run(CommandSender sender, BaseCommand.SenderType senderType, String[] args)
    {
        if (hasCooldown())
        {
            if (cooldowns.containsKey(sender))
            {
                final long time = cooldowns.get(sender);
                final long remaining = time - System.currentTimeMillis();

                if (remaining > 0)
                {
                    sender.sendMessage("§cVous devez attendre " + remaining / 1000 + " secondes avant de pouvoir réutiliser cette commande.");
                    return;
                }
            }

            cooldowns.put(sender, System.currentTimeMillis() + cooldown * 1000);
        }

        if (!canExecute(sender))
        {
            sender.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande.");
            return;
        }

        command.run(sender, senderType, args);
    }
}
