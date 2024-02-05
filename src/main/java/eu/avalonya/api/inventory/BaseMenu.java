package eu.avalonya.api.inventory;

import eu.avalonya.api.items.CustomItemStack;
import fr.mrmicky.fastinv.FastInv;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public abstract class BaseMenu extends FastInv {

    public final static ItemStack DEFAULT_BORDER_ITEM = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, " ");
    public final static ItemStack DEFAULT_BACKGROUND_ITEM = new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, " ");
    public final static ItemStack DEFAULT_CLOSE_ITEM = new CustomItemStack(Material.BARRIER, "§cFermer");

    public BaseMenu(int size, String title) {
        this(size, title, DEFAULT_BORDER_ITEM);
    }

    public BaseMenu(int size, String title, ItemStack borderItem) {
        super(size, title);

        for (int i : getBorders()) {
            setItem(i, borderItem, e -> e.setCancelled(true));
        }

        for (int i = 0; i < size; i++) {
            if (getInventory().getItem(i) == null)
                setItem(i, DEFAULT_BACKGROUND_ITEM, e -> e.setCancelled(true));
        }

        setItem(8, DEFAULT_CLOSE_ITEM, e -> e.getInventory().close());

        init();
    }

    public abstract void init();

    public void open(HumanEntity humanEntity) {
        super.open((Player) humanEntity);
    }

    public int[] getContent() {
        int size = this.getInventory().getSize();

        // On verifie que i ne soit pas un multiple de 9 (bordure gauche)
        // ou un multiple de 9 - 1 (bordure droite)
        // ou inférieur à 9 (bordure haute)
        // ou supérieur à size - 9 (bordure basse)
        return IntStream.range(0, size).filter(i -> !(size < 27 || i < 9
                || i % 9 == 0 || (i - 8) % 9 == 0 || i > size - 9)).toArray();
    }

}
