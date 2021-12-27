package net.mwforrest7.vineyard.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.mwforrest7.vineyard.item.ModItems;

/**
 * Enforces this inventory slot to be used only by Copper Springs
 */
public class BottleSlot extends Slot {
    public BottleSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return isEmptyGlassBottle(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }

    public static boolean isEmptyGlassBottle(ItemStack stack) {
        return stack.isOf(Items.GLASS_BOTTLE);
    }
}
