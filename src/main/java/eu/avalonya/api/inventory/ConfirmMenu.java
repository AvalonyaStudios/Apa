package eu.avalonya.api.inventory;

import eu.avalonya.api.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ConfirmMenu extends BaseMenu {

    public static final ItemStack CONFIRM_ITEM = new CustomItemStack("a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6", "§a§lConfirmer");
    public static final ItemStack CANCEL_ITEM = new CustomItemStack("beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7", "§c§lAnnuler");
    public final static ItemStack RED_BORDER_ITEM = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, " ");


    public ConfirmMenu(String name, boolean borderRed){

        super(27, name, borderRed ? BaseMenu.DEFAULT_BORDER_ITEM : RED_BORDER_ITEM);

        setItem(14, CONFIRM_ITEM, event -> {
            onConfirm((Player) event.getWhoClicked());
        });

        setItem(15, CANCEL_ITEM, event -> {
            onCancel((Player) event.getWhoClicked());
        });
    }

    protected abstract void onConfirm(Player player);
    protected abstract void onCancel(Player player);

}
