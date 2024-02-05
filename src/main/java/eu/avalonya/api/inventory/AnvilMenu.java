package eu.avalonya.api.inventory;

import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.items.CustomItemStack;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class AnvilMenu {

    public final static ItemStack FIRST_SLOT_ITEM = new CustomItemStack(Material.PAPER, " ");
    public final static ItemStack OUTPUT_SLOT_ITEM = new CustomItemStack(Material.PAPER, " ");

    private final AnvilGUI.Builder builder;

    public AnvilMenu(String title, String text) {
        this.builder = new AnvilGUI.Builder()
                .title(title)
                .text(text)
                .itemLeft(FIRST_SLOT_ITEM)
                .itemOutput(OUTPUT_SLOT_ITEM)
                .onClick((slot, stateSnapshot) -> {
                    return onConfirm(stateSnapshot.getPlayer(), stateSnapshot.getText());
                })
                .plugin(AvalonyaAPI.getInstance());
    }

    public abstract List<AnvilGUI.ResponseAction> onConfirm(Player player, String text);

    public void open(Player player) {
        builder.open(player);
    }

    public void open(HumanEntity humanEntity) {
        open((Player) humanEntity);
    }

}
