package eu.avalonya.api.inventory;

import eu.avalonya.api.items.CustomItemStack;
import fr.mrmicky.fastinv.FastInv;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.IntStream;

public abstract class BaseMenu extends FastInv
{

    public final static ItemStack DEFAULT_BORDER_ITEM = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, " ");
    public final static ItemStack DEFAULT_BACKGROUND_ITEM = new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, " ");
    public final static ItemStack DEFAULT_CLOSE_ITEM = new CustomItemStack(Material.BARRIER, "§cFermer");
    public final static ItemStack BACK_ITEM = new CustomItemStack("86971dd881dbaf4fd6bcaa93614493c612f869641ed59d1c9363a3666a5fa6", "§fRetour");

    public BaseMenu(int size, String title) {
        this(size, title, DEFAULT_BORDER_ITEM);
    }

    public BaseMenu(int size, String title, ItemStack borderItem) {
        super(size, title);
        super.getInventory().toString();

        for (int i : getBorders()) {
            setItem(i, borderItem, e -> e.setCancelled(true));
        }

        for (int i = 0; i < size; i++) {
            if (getInventory().getItem(i) == null)
                setItem(i, DEFAULT_BACKGROUND_ITEM, e -> e.setCancelled(true));
        }

        setItem(8, DEFAULT_CLOSE_ITEM, e -> this.close(e.getWhoClicked()));

        init();
    }

    public abstract void init();


    public int[] getContent() {
        int size = this.getInventory().getSize();

        // On verifie que i ne soit pas un multiple de 9 (bordure gauche)
        // ou un multiple de 9 - 1 (bordure droite)
        // ou inférieur à 9 (bordure haute)
        // ou supérieur à size - 9 (bordure basse)
        return IntStream.range(0, size).filter(i -> !(size < 27 || i < 9
                || i % 9 == 0 || (i - 8) % 9 == 0 || i > size - 9)).toArray();
    }

    public void open(HumanEntity humanEntity)
    {
        Player player = (Player) humanEntity;

        super.open(player);

        History.setHistory(player, getInventory());

        if (History.getHistory().get(player.getUniqueId()).size() > 1)
        {
            setItem(0, BACK_ITEM, e -> openLast(player));
        }
    }

    /*
    *   Close l'inventaire et supprime l'historique du joueur
    */
    public void close(HumanEntity humanEntity)
    {
        Player player = (Player) humanEntity;

        if (History.getHistory().containsKey(player.getUniqueId()))
        {
            History.getHistory().remove(player.getUniqueId());
            super.getInventory().close();
        }
    }


    /**
    *   Ouvrir l'inventaire précédent
    */
    public void openLast(Player player)
    {
        UUID playerUUID = player.getUniqueId();

        int inventoryListSize = History.getHistory().get(playerUUID).size();

        if (inventoryListSize > 1) {
            player.openInventory(History.getHistory().get(playerUUID).get(inventoryListSize - 2));
            History.getHistory().get(playerUUID).remove(inventoryListSize - 1);
        }
    }
}
