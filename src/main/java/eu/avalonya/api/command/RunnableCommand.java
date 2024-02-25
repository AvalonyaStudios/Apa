package eu.avalonya.api.command;

import org.bukkit.command.CommandSender;

public interface RunnableCommand<T extends CommandSender>
{

    void run(T sender, ArgumentCollection args);

}
