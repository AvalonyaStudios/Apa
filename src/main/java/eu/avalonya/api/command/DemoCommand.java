package eu.avalonya.api.command;

import eu.avalonya.api.command.arguments.BooleanArgument;
import eu.avalonya.api.command.arguments.IntegerArgument;
import eu.avalonya.api.command.arguments.PlayerArgument;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DemoCommand extends BaseCommand<Player>
{

    public DemoCommand()
    {
        super("demo");

        // Add sub commands
        addSubCommand(BaseCommand.newSubCommand("sub", (sender, args) -> {
            sender.sendMessage("sub command");
        }));
        addSubCommand(sub2());

        // Add arguments
        addArgument(PlayerArgument.class, true);
        addArgument(BooleanArgument.class, true);
    }

    @Override
    public void run(Player sender, ArgumentCollection args)
    {
        sender.sendMessage("oui");
        sender.sendMessage("Hello " + args.get(0, OfflinePlayer.class).getName());
        sender.sendMessage("You are " + args.get(1, Boolean.class) + " years old");

        sender.sendMessage("Il y a " + args.getRest().size() + " arguments restants");
    }

    private BaseCommand<ConsoleCommandSender> sub2()
    {
        BaseCommand<ConsoleCommandSender> sub = BaseCommand.newSubCommand("sub2", (sender, args) -> {
            sender.sendMessage("sub2 command");
        });

        sub.setCooldown(5);
        sub.addArgument(BooleanArgument.class, true);

        return sub;
    }
}
