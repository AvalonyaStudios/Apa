package eu.avalonya.api.items;

import org.bukkit.inventory.ItemStack;

/**
 * Interface that represents an item that can be converted to an ItemStack.
 */
public interface ItemAccess {

    public ItemStack toItemStack();

}
