package eu.avalonya.api.command;

import eu.avalonya.api.AvalonyaAPI;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class BaseCommand<T extends CommandSender> implements CommandExecutor, TabCompleter, RunnableCommand<T>
{

    /**
     * Messages
     */
    private final Component INVALID_SENDER_TYPE = Component.text("Vous n'avez pas accès à cette commande.").color(NamedTextColor.RED);
    private final Component INSUFFICIENT_PERMISSION = Component.text("Vous n'avez pas accès à cette commande.").color(NamedTextColor.RED);

    @Getter
    private final String name;
    private final ArgumentCollection arguments = new ArgumentCollection();
    private final ArgumentCollection argumentsGive = new ArgumentCollection();
    private final Map<String, BaseCommand<?>> subCommands = new HashMap<>();
    private final List<UUID> onlyAccess = new ArrayList<>();
    @Getter
    private final List<String> permissions = new ArrayList<>();
    @Getter
    @Setter
    protected int cooldown = 0;
    private final Map<T, Long> cooldowns = new HashMap<>();

    /**
     * Constructors
     */

    public BaseCommand(@NotNull String name)
    {
        this.name = name;
    }

    public void addArgument(Argument<?> argument)
    {
        arguments.add(argument);
    }

    public <U extends Argument<?>> void addArgument(Class<U> argument)
    {
        try
        {
            addArgument(argument.getDeclaredConstructor().newInstance());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public <U extends Argument<?>> void addArgument(Class<U> argument, boolean required)
    {
        try
        {
            Argument<?> arg = argument.getDeclaredConstructor().newInstance();
            arg.setRequired(required);
            addArgument(arg);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addSubCommand(BaseCommand<?> command)
    {
        subCommands.put(command.getName(), command);
    }

    public void addOnlyAccess(UUID ...uuid)
    {
        onlyAccess.addAll(Arrays.asList(uuid));
    }

    public void addPermissions(String ...permissions)
    {
        this.permissions.addAll(Arrays.asList(permissions));
    }

    private boolean canExecute(T sender)
    {
        if (!onlyAccess.isEmpty() && sender instanceof Entity)
        {
            return onlyAccess.contains(((Entity) sender).getUniqueId());
        }
        if (!getPermissions().isEmpty())
        {
            for (String permission : getPermissions())
            {
                if (!sender.hasPermission(permission))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void saveCooldown(T sender)
    {
        if (getCooldown() > 0)
        {
            cooldowns.put(sender, System.currentTimeMillis() + (cooldown * 1000L));
        }
    }

    public boolean inCooldown(T sender)
    {
        return getCooldown() > 0 && cooldowns.containsKey(sender) && cooldowns.get(sender) > System.currentTimeMillis();
    }

    protected String getUsage()
    {
        StringBuilder usage = new StringBuilder();
        usage.append("/").append(getName());
        for (Argument<?> argument : arguments)
        {
            usage.append(" <").append(argument.getUsage()).append(">");
        }
        return usage.toString();
    }

    private boolean hasPermission(T sender) {
        if (!canExecute(sender)) {
            sender.sendMessage(INSUFFICIENT_PERMISSION);
            return false;
        }
        return true;
    }

    private boolean checkCooldown(T sender) {
        if (inCooldown(sender)) {
            sender.sendMessage(
                    Component.text("Vous devez attendre " + (cooldowns.get(sender) - System.currentTimeMillis()) / 1000 + " secondes avant de pouvoir réutiliser cette commande.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }
        return false;
    }

    private boolean handleSubCommand(T sender, Command command, String s, String[] args) {
        if (args.length > 0) {
            BaseCommand<?> subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                subCommand.onCommand(sender, command, s, Arrays.copyOfRange(args, 1, args.length));
                return true;
            }
        }
        return false;
    }

    private boolean validateArguments(T sender, String[] args) {
        int i = 0;

        argumentsGive.clear();

        while (i < this.arguments.size()) {
            final Argument<?> argument = this.arguments.get(i);
            if (args.length <= i) {
                if (argument.isRequired()) {
                    sender.sendMessage(
                            Component.text(getUsage()).color(NamedTextColor.RED)
                    );
                    return false;
                }
                break;
            }
            if (!argument.test(args[i])) {
                sender.sendMessage(
                        Component.text(args[i] + " n'est pas un argument valide.").color(NamedTextColor.RED)
                );
                return false;
            }
            if (args[i] != null) {
                argument.setInput(args[i]);
                argumentsGive.add(argument);
            }
            i++;
        }

        argumentsGive.getRest().clear();

        while (i < args.length) {
            argumentsGive.addRest(args[i]);
            i++;
        }
        return true;
    }

    private void processCommand(T sender, String[] args) {
        saveCooldown(sender);
        run(sender, argumentsGive);
    }

    private void handleException(CommandSender commandSender, ClassCastException e) {
        commandSender.sendMessage(INVALID_SENDER_TYPE);
        AvalonyaAPI.getInstance().getLogger().severe(e.getMessage());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        try
        {
            T sender = (T) commandSender;

            if (!hasPermission(sender)) return true;
            if (checkCooldown(sender)) return true;
            if (handleSubCommand(sender, command, s, args)) return true;
            if (!validateArguments(sender, args)) return true;

            processCommand(sender, args);
        }
        catch (ClassCastException e)
        {
            handleException(commandSender, e);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        final List<String> completions = new ArrayList<>();

        if (strings.length > 0) {
            BaseCommand<?> subCommand = subCommands.get(strings[0]);
            if (subCommand != null) {
                return subCommand.onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            } else {
                if (strings.length == 1) {
                    appendIfStartsWith(completions, subCommands.keySet(), strings[0]);
                }
                if (arguments.size() > strings.length - 1) {
                    Argument<?> argument = arguments.get(strings.length - 1);
                    appendIfStartsWith(completions, argument.getCompletions(), strings[strings.length - 1]);
                }
            }
        }
        return completions;
    }

    public void register(JavaPlugin plugin)
    {
        final PluginCommand pluginCommand = plugin.getCommand(this.getName());

        if (pluginCommand == null) {
            plugin.getLogger().severe("Impossible de trouver la commande " + this.getName());
            return;
        }
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
    }

    /**
     * Append completions to the list if they start with the given start
     * @param from the list to append to
     * @param collection the collection to get completions from
     * @param start the start to check
     */
    private void appendIfStartsWith(List<String> from, Collection<String> collection, String start)
    {
        collection.forEach(completion -> {
            if (completion.startsWith(start))
            {
                from.add(completion);
            }
        });
    }

    public static <T extends CommandSender> BaseCommand<T> newSubCommand(String name, RunnableCommand<T> run)
    {
        return new BaseCommand<>(name)
        {
            @Override
            public void run(T sender, ArgumentCollection args)
            {
                run.run(sender, args);
            }
        };
    }

    /**
     * Cancel the current cooldown on the command if it has one activated
     * @param sender The person who sent the command
     */
    public void cancelCooldown(T sender) {
        this.cooldowns.remove(sender);
    }

}

