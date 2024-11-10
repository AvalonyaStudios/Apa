package eu.avalonya.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class History implements Listener
{

    private static Map<UUID, List<Inventory>> history = new HashMap<>();

    public History() {}

    public static Map<UUID, List<Inventory>> getHistory()
    {
        return history;
    }

    /**
    *   Verifier si l'historique contient le joueur et si non le rajouter avec l'invantaire actuel
    */
    public static void setHistory(Player player, Inventory inventory)
    {
        UUID playerUUID = player.getUniqueId();

        if (!history.containsKey(playerUUID))
        {
            history.put(playerUUID, new LinkedList<>());
        }

        history.get(playerUUID).add(inventory);
    }

    /**
    *   Verifier si l'invantaire est fermer puis qu'il en ouvre un nouveux si oui l'historique du joueur est supprimer
    */
    @EventHandler
    public void playerCloseInv(InventoryCloseEvent event)
    {
        UUID playerUUID = event.getPlayer().getUniqueId();

        if (history.containsKey(playerUUID))
        {
            if (!event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW))
            {
                history.remove(playerUUID);
            }
        }
    }

}