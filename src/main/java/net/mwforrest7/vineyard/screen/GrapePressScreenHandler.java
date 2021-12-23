package net.mwforrest7.vineyard.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.block.entity.GrapePressProperties;
import net.mwforrest7.vineyard.screen.slot.ModFuelSlot;
import net.mwforrest7.vineyard.screen.slot.ModResultSlot;

/**
 * This class facilitates screen interaction and updates
 */
public class GrapePressScreenHandler extends ScreenHandler {
    // Player inventory
    private final Inventory inventory;

    // Not being used, but have it just in-case
    private final World world;

    // Data from GrapePressEntity so that we can update the screen appropriately
    private final PropertyDelegate propertyDelegate;

    public GrapePressScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(GrapePressProperties.INVENTORY_SIZE), new ArrayPropertyDelegate(GrapePressProperties.INVENTORY_SIZE));
    }

    public GrapePressScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.GRAPE_PRESS_SCREEN_HANDLER, syncId);
        checkSize(inventory, GrapePressProperties.INVENTORY_SIZE);
        this.inventory = inventory;
        this.world = playerInventory.player.world;
        inventory.onOpen(playerInventory.player);
        propertyDelegate = delegate;

        // Add the Block's inventory slots, (x, y) is where on the screen the slot should be added
        this.addSlot(new ModFuelSlot(inventory, GrapePressProperties.InventorySlots.FUEL_SLOT.toInt(), 18, 50));
        this.addSlot(new Slot(inventory, GrapePressProperties.InventorySlots.INGREDIENT_SLOT_1.toInt(), 66, 16));
        this.addSlot(new Slot(inventory, GrapePressProperties.InventorySlots.INGREDIENT_SLOT_2.toInt(), 66, 50));
        this.addSlot(new ModResultSlot(inventory, GrapePressProperties.InventorySlots.OUTPUT_SLOT.toInt(), 114, 33));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(delegate);
    }

    /**
     * Checks if there is crafting in progress
     *
     * @return
     */
    public boolean isCrafting() {
        return propertyDelegate.get(GrapePressProperties.DelegateProperties.PROGRESS.toInt()) > 0;
    }

    /**
     * Checks if there is fuel time available
     *
     * @return
     */
    public boolean hasFuel() {
        return propertyDelegate.get(GrapePressProperties.DelegateProperties.FUEL_TIME.toInt()) > 0;
    }

    /**
     * Determines how much of the crafting progress texture should be drawn based on crafting progress
     *
     * @return true or false
     */
    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(GrapePressProperties.DelegateProperties.PROGRESS.toInt());
        int maxProgress = this.propertyDelegate.get(GrapePressProperties.DelegateProperties.MAX_PROGRESS.toInt());
        int progressArrowSize = 26; // This is the width in pixels of the arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    /**
     * Determines how much of the fuel progress texture should be drawn based on remaining fuel
     *
     * @return true or false
     */
    public int getScaledFuelProgress() {
        int fuelProgress = this.propertyDelegate.get(GrapePressProperties.DelegateProperties.FUEL_TIME.toInt());
        int maxFuelProgress = this.propertyDelegate.get(GrapePressProperties.DelegateProperties.MAX_FUEL_TIME.toInt());
        int fuelProgressSize = 14;

        return maxFuelProgress != 0 ? (int)(((float)fuelProgress / (float)maxFuelProgress) * fuelProgressSize) : 0;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    /**
     * Handles the shift + click functionality that swaps items between inventories
     *
     * @param player
     * @param invSlot
     * @return
     */
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    /**
     * Adds the player inventory slots to the screen GUI
     * @param playerInventory
     */
    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    /**
     * Adds the player hot bar slots to the screen GUI
     * @param playerInventory
     */
    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
