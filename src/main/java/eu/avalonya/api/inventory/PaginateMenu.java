package eu.avalonya.api.inventory;

import eu.avalonya.api.items.CustomItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class PaginateMenu extends BaseMenu {

    public final static int ITEMS_PER_PAGE = 28;
    public final static CustomItemStack NEXT_PAGE_ITEM = new CustomItemStack("e02fa3b2dcb11c6639cc9b9146bea54fbc6646d855bdde1dc6435124a11215d", "§fPage suivante");
    public final static CustomItemStack PREVIOUS_PAGE_ITEM = new CustomItemStack("74133f6ac3be2e2499a784efadcfffeb9ace025c3646ada67f3414e5ef3394", "§fPage précédente");

    private int page = 0;

    public PaginateMenu(String title) {
        super(54, title);
    }

    @Override
    public void init() {
        update();
    }

    public abstract List<ItemStack> getItems();

    protected void update() {
        final List<ItemStack> content = getItems();
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min((page + 1) * ITEMS_PER_PAGE, content.size());
        int[] slots = getContent();

        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            // Si l'index est inférieur à la taille de la liste, on ajoute l'item à l'inventaire sinon on ajoute rien
            setItem(slots[i], i + start < end ? content.get(i + start) : null, event -> event.setCancelled(true));
        }

        if (page > 0) {
            setItem(48, PREVIOUS_PAGE_ITEM, event -> {
                page--;
                update();
                this.open(event.getWhoClicked());
            });
        } else {
            setItem(48, BaseMenu.DEFAULT_BACKGROUND_ITEM);
        }

        if (end < content.size()) {
            setItem(50, NEXT_PAGE_ITEM, event -> {
                page++;
                update();
                this.open(event.getWhoClicked());
            });
        } else {
            setItem(50, BaseMenu.DEFAULT_BACKGROUND_ITEM);
        }
    }

}
