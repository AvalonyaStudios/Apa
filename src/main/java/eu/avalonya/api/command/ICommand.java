package eu.avalonya.api.command;

import org.bukkit.command.CommandSender;

public interface ICommand
{

    void run(CommandSender sender, BaseCommand.SenderType senderType, String[] args);

}
