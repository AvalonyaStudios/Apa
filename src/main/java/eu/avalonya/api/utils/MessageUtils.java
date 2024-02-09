package eu.avalonya.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageUtils {


    /**
     * Send a message to the console with a message
     * @param msg Printed message
     */
    public static void sendConsoleMsg(String msg)
    {
        Bukkit.getConsoleSender().sendMessage( msg.replace("&", "§"));
    }

    public static void sendPlayerMessage(Player p, String msg)
    {
        p.sendMessage(msg.replace("&", "§"));
    }

    public static String convertColor(String msg)
    {
        return msg.replace("&", "§");
    }

}
