package net.mwforrest7.vineyard.screen.slot;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.mwforrest7.vineyard.item.ModItems;

/**
 * Enforces this inventory slot to be used only by fuels
 */
public class ModFuelSlot extends Slot {
    public ModFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return isCopperSpring(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }

    public static boolean isCopperSpring(ItemStack stack) {
        return stack.isOf(ModItems.COPPER_SPRING);
    }
}
