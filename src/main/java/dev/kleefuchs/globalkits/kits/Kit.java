package dev.kleefuchs.globalkits.kits;

import org.bukkit.inventory.ItemStack;

public class Kit {
    private ItemStack[] items;

    public ItemStack[] getItems() {
        return this.items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public Kit(ItemStack[] items) {
        this.setItems(items);
    }
}
