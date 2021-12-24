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
import net.mwforrest7.vineyard.block.entity.FruitPressProperties;
import net.mwforrest7.vineyard.screen.slot.ModFuelSlot;
import net.mwforrest7.vineyard.screen.slot.ModResultSlot;

/**
 * This class facilitates screen interaction and updates
 */
public class FruitPressScreenHandler extends ScreenHandler {
    // Fuel Slot Coordinates
    private static final int X_FUEL = 56;
    private static final int Y_FUEL = 53;

    // Ingredient Slot Coordinates
    private static final int X_INPUT = 56;
    private static final int Y_INPUT = 17;

    // Output Slot Coordinates
    private static final int X_OUTPUT = 116;
    private static final int Y_OUTPUT = 35;

    // Player Inventory & Hot Bar Coordinates
    private static final int X_INVENTORY = 7;
    private static final int Y_INVENTORY = 83;
    private static final int X_HOTBAR = 7;
    private static final int Y_HOTBAR = 141;

    // Player inventory
    private final Inventory inventory;

    // Not being used, but have it just in-case
    private final World world;

    // Data from GrapePressEntity so that we can update the screen appropriately
    private final PropertyDelegate propertyDelegate;

    public FruitPressScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(FruitPressProperties.INVENTORY_SIZE), new ArrayPropertyDelegate(FruitPressProperties.DELEGATE_PROPERTY_SIZE));
    }

    public FruitPressScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.FRUIT_PRESS_SCREEN_HANDLER, syncId);
        System.out.println("In handler construct");
        checkSize(inventory, FruitPressProperties.INVENTORY_SIZE);
        this.inventory = inventory;
        this.world = playerInventory.player.world;
        inventory.onOpen(playerInventory.player);
        propertyDelegate = delegate;

        // Add the Block's inventory slots, (x, y) is where on the screen the slot should be added
        this.addSlot(new ModFuelSlot(inventory, FruitPressProperties.InventorySlots.FUEL_SLOT.toInt(), X_FUEL, Y_FUEL));
        this.addSlot(new Slot(inventory, FruitPressProperties.InventorySlots.INGREDIENT_SLOT_1.toInt(), X_INPUT, Y_INPUT));
        //this.addSlot(new Slot(inventory, FruitPressProperties.InventorySlots.INGREDIENT_SLOT_2.toInt(), 66, 50));
        this.addSlot(new ModResultSlot(inventory, FruitPressProperties.InventorySlots.OUTPUT_SLOT.toInt(), X_OUTPUT, Y_OUTPUT));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(delegate);
        System.out.println("Leaving handler construct");
    }

    /**
     * Checks if there is crafting in progress
     *
     * @return true or false
     */
    public boolean isCrafting() {
        return propertyDelegate.get(FruitPressProperties.DelegateProperties.PROGRESS.toInt()) > 0;
    }

    /**
     * Checks if there is fuel time available
     *
     * @return true or false
     */
    public boolean hasFuel() {
        return propertyDelegate.get(FruitPressProperties.DelegateProperties.FUEL_TIME.toInt()) > 0;
    }

    /**
     * Determines how much of the crafting progress texture should be drawn based on crafting progress
     *
     * @return true or false
     */
    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(FruitPressProperties.DelegateProperties.PROGRESS.toInt());
        int maxProgress = this.propertyDelegate.get(FruitPressProperties.DelegateProperties.MAX_PROGRESS.toInt());
        int progressArrowSize = 26; // This is the width in pixels of the arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    /**
     * Determines how much of the fuel progress texture should be drawn based on remaining fuel
     *
     * @return true or false
     */
    public int getScaledFuelProgress() {
        for(int i = 0; i < propertyDelegate.size(); i++){
            System.out.println(i + ": " + propertyDelegate.get(i));
        }
        int fuelProgress = this.propertyDelegate.get(FruitPressProperties.DelegateProperties.FUEL_TIME.toInt());
        int maxFuelProgress = this.propertyDelegate.get(FruitPressProperties.DelegateProperties.MAX_FUEL_TIME.toInt());
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
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, X_INVENTORY + l * 18, Y_INVENTORY + i * 18));
            }
        }
    }

    /**
     * Adds the player hot bar slots to the screen GUI
     * @param playerInventory
     */
    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, X_HOTBAR + i * 18, Y_HOTBAR));
        }
    }
}
