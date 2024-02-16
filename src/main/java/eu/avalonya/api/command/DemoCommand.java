package eu.avalonya.api.command;

import eu.avalonya.api.AvalonyaAPI;
import org.bukkit.command.CommandSender;

public class DemoCommand extends BaseCommand
{

    public DemoCommand()
    {
        super("demo");

        addSubCommand("sub", this::subRun);
        addSubCommand("sub2", this::subRun2, "demo.sub2");
        addSubCommand("give", this::giveRun, 5);
        setCooldown(5);
    }

    @Override
    public void run(CommandSender sender, SenderType senderType, String[] args)
    {
        sender.sendMessage("Hello, world!");
    }

    private void subRun(CommandSender sender, SenderType senderType, String[] args)
    {
        sender.sendMessage("Hello, sub world!");
    }

    private void subRun2(CommandSender sender, SenderType senderType, String[] args)
    {
        sender.sendMessage("Hello, sub world 2!");
    }

    private void giveRun(CommandSender sender, SenderType senderType, String[] args)
    {
        sender.addAttachment(AvalonyaAPI.getInstance(), "demo.sub2", true, 1000);
    }

}
