package net.mwforrest7.vineyard.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
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
import net.minecraft.world.event.GameEvent;
import net.mwforrest7.vineyard.block.entity.properties.WineCaskProperties;
import net.mwforrest7.vineyard.screen.slot.ModResultSlot;

/**
 * This class facilitates screen interaction and updates
 */
public class WineCaskScreenHandler extends ScreenHandler {
    // Ingredient Slot 1 Coordinates
    private static final int X_INGREDIENT_1 = 79;
    private static final int Y_INGREDIENT_1 = 32;

    // Player Inventory & Hot Bar Coordinates
    private static final int X_INVENTORY = 8;
    private static final int Y_INVENTORY = 84;
    private static final int X_HOTBAR = 8;
    private static final int Y_HOTBAR = 142;

    // Player inventory
    private final Inventory inventory;

    // Data from WineCaskEntity so that we can update the screen appropriately
    private final PropertyDelegate propertyDelegate;

    public WineCaskScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(WineCaskProperties.INVENTORY_SIZE), new ArrayPropertyDelegate(WineCaskProperties.DELEGATE_PROPERTY_SIZE));
    }

    public WineCaskScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.WINE_CASK_SCREEN_HANDLER, syncId);
        checkSize(inventory, WineCaskProperties.INVENTORY_SIZE);
        this.inventory = inventory;

        // Calls the onOpen function in the WineCaskEntity class
        inventory.onOpen(playerInventory.player);

        propertyDelegate = delegate;

        // Add the Block's inventory slots, (x, y) is where on the screen the slot should be added
        this.addSlot(new Slot(inventory, WineCaskProperties.InventorySlots.INGREDIENT_SLOT_1.toInt(), X_INGREDIENT_1, Y_INGREDIENT_1));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(delegate);
    }

    /**
     * Checks if there is crafting in progress
     *
     * @return true or false
     */
    public boolean isCrafting() {
        int progress = propertyDelegate.get(WineCaskProperties.DelegateProperties.PROGRESS.toInt());
        int maxProgress = propertyDelegate.get(WineCaskProperties.DelegateProperties.MAX_PROGRESS.toInt());

        // Close the GUI when crafting completes
        if(progress >= maxProgress){
            MinecraftClient.getInstance().player.closeScreen();
        }

        return progress > 0;
    }

    /**
     * Determines how much of the crafting progress texture should be drawn based on crafting progress
     *
     * @return true or false
     */
    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(WineCaskProperties.DelegateProperties.PROGRESS.toInt());
        int maxProgress = this.propertyDelegate.get(WineCaskProperties.DelegateProperties.MAX_PROGRESS.toInt());
        int progressArrowSize = 49; // This is the height in pixels of the cask animation

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    /**
     * Handles the shift + click functionality that swaps items between inventories
     *
     * @param player the player instance
     * @param invSlot the inventory slot
     * @return
     */
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
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

    // Called when the screen is closed - overrides close() in ScreenHandler class
    public void close(PlayerEntity player) {
        super.close(player);

        // Calls the onClose() function in the WineCaskEntity class
        this.inventory.onClose(player);
    }

    /**
     * Adds the player inventory slots to the screen GUI
     * @param playerInventory the player inventory
     */
    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, X_INVENTORY + l * 18, Y_INVENTORY + i * 18));
            }
        }
    }

    /**
     * Adds the player hot bar slots to the screen GUI
     * @param playerInventory the player inventory
     */
    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, X_HOTBAR + i * 18, Y_HOTBAR));
        }
    }
}
