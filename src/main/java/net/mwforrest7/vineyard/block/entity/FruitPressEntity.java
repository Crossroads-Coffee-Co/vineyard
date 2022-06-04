package net.mwforrest7.vineyard.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.item.inventory.ImplementedInventory;
import net.mwforrest7.vineyard.recipe.FruitPressRecipe;
import net.mwforrest7.vineyard.screen.FruitPressScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.mwforrest7.vineyard.block.entity.properties.FruitPressProperties.DISPLAY_NAME;
import static net.mwforrest7.vineyard.block.entity.properties.FruitPressProperties.*;

/**
 * This is like a controller in MVC. The brains of the operation.
 * It reads and writes data, handles game logic, and updates the view (ScreenHandler)
 */
public class FruitPressEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

    // PropertyDelegate facilitates data updates to the ScreenHandler
    protected final PropertyDelegate propertyDelegate;

    // Crafting progress
    private int progress = DEFAULT_CRAFTING_PROGRESS;

    // Required Crafting progress to complete craft
    private int maxProgress = MAX_CRAFTING_PROGRESS;

    // Normally fuel time is based on the fuel used, in this case it is static
    private int fuelTime = DEFAULT_FUEL_TIME;
    private int maxFuelTime = MAX_FUEL_TIME;


    public FruitPressEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FRUIT_PRESS, pos, state);

        // Initialize the PropertyDelegate
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> FruitPressEntity.this.progress;
                    case 1 -> FruitPressEntity.this.maxProgress;
                    case 2 -> FruitPressEntity.this.fuelTime;
                    case 3 -> FruitPressEntity.this.maxFuelTime;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FruitPressEntity.this.progress = value;
                    case 1 -> FruitPressEntity.this.maxProgress = value;
                    case 2 -> FruitPressEntity.this.fuelTime = value;
                    case 3 -> FruitPressEntity.this.maxFuelTime = value;
                }
            }

            public int size() {
                return DELEGATE_PROPERTY_SIZE;
            }
        };
    }

    /**
     * Title to be displayed at top of the Block's inventory screen
     *
     * @return the title as Text
     */
    @Override
    public Text getDisplayName() {return Text.literal(DISPLAY_NAME);}

    /**
     * Creates the ScreenHandler
     *
     * @param syncId id - presumably used to sync between screen and entity
     * @param inv player inventory
     * @param player the player instance
     * @return a new screen handler instance
     */
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FruitPressScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    /**
     * Returns the inventory of this Block
     *
     * @return list of item stacks
     */
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    /**
     * The tick handler function which performs actions every server tick
     * (1/20th of a second)
     *
     * @param world the world
     * @param pos the block position
     * @param state the block instance
     * @param entity the block entity instance
     */
    public static void tick(World world, BlockPos pos, BlockState state, FruitPressEntity entity) {
        // If there is fuel time, subtract from it
        if(isConsumingFuel(entity)) {
            entity.fuelTime--;
        }

        // If there is a recipe that matches the Block's inventory...
        if(hasRecipe(entity)) {
            // If there is fuel available and the fuel time is 0 then consume fuel
            if(hasFuelInFuelSlot(entity) && !isConsumingFuel(entity)) {
                entity.consumeFuel();
            }
            // If there is fuel time, increase crafting progress
            if(isConsumingFuel(entity)) {
                entity.progress++;

                // If crafting progress reaches max, then complete the craft
                if(entity.progress > entity.maxProgress) {
                    craftItem(entity);
                }
            }
        } else {
            // If there is no match, reset the crafting progress
            entity.resetProgress();
        }
    }

    /**
     * Sets crafting progress to 0
     */
    private void resetProgress() {
        this.progress = DEFAULT_CRAFTING_PROGRESS;
    }

    /**
     * If there is fuel in the fuel slot, this will set fuel time properties
     * based on the type of fuel consumed and also consume 1 fuel.
     */
    private void consumeFuel() {
        if(!getStack(InventorySlots.FUEL_SLOT.toInt()).isEmpty()) {
            this.removeStack(InventorySlots.FUEL_SLOT.toInt(), 1);
            this.fuelTime = MAX_FUEL_TIME;
        }
    }

    private static boolean hasFuelInFuelSlot(FruitPressEntity entity) {
        return !entity.getStack(InventorySlots.FUEL_SLOT.toInt()).isEmpty();
    }

    private static boolean isConsumingFuel(FruitPressEntity entity) {
        return entity.fuelTime > 0;
    }

    /**
     * Matches the Block's inventory to a recipe. If there is a match,
     * subtracts ingredients from the Block's inventory and produces
     * the resulting craft.
     *
     * @param entity the block entity instance
     */
    private static void craftItem(FruitPressEntity entity) {
        World world = entity.world;

        // Copies the Block's inventory into a temp inventory var
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        // Attempts to match the inventory items to a recipe
        Optional<FruitPressRecipe> match = world.getRecipeManager().getFirstMatch(FruitPressRecipe.Type.INSTANCE, inventory, world);

        // If there is a match, updates the inventory as appropriate
        if(match.isPresent()) {
            // Removes 1 from each ingredient slot
            entity.removeStack(InventorySlots.FRUIT_INGREDIENT_SLOT.toInt(),1);
            entity.removeStack(InventorySlots.GLASS_BOTTLE_INGREDIENT_SLOT.toInt(),1);

            // Adds or increments the output item
            entity.setStack(InventorySlots.OUTPUT_SLOT.toInt(), new ItemStack(match.get().getOutput().getItem(),
                    entity.getStack(InventorySlots.OUTPUT_SLOT.toInt()).getCount() + 1));

            // Reset crafting progress after completing the craft
            entity.resetProgress();
        }
    }

    /**
     * Checks if there is a matching recipe and also performs
     * a couple of other safety checks.
     *
     * @param entity the block entity instance
     * @return true or false
     */
    private static boolean hasRecipe(FruitPressEntity entity) {
        World world = entity.world;

        // Copies the Block's inventory into a temp inventory var
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        // Attempts to match the inventory items to a recipe
        Optional<FruitPressRecipe> match = world.getRecipeManager().getFirstMatch(FruitPressRecipe.Type.INSTANCE, inventory, world);

        // Returns true if there is a match,
        // and if the output slot is empty or matches the recipe's output item,
        // and if there is enough space left in the output stack
        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput());
    }

    /**
     * Saves the block's inventory and properties when world is shut down
     *
     * @param nbt the data file
     */
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt(NBT_KEY_PROGRESS, progress);
        nbt.putInt(NBT_KEY_FUEL_TIME, fuelTime);
        nbt.putInt(NBT_KEY_MAX_FUEL_TIME, maxFuelTime);
    }

    /**
     * Loads the saved block inventory and properties when world is started
     *
     * @param nbt the data file
     */
    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt(NBT_KEY_PROGRESS);
        fuelTime = nbt.getInt(NBT_KEY_FUEL_TIME);
        maxFuelTime = nbt.getInt(NBT_KEY_MAX_FUEL_TIME);
    }

    /**
     * Checks if the Block's output inventory slot is empty or contains an item that matches
     * the recipe's output item.
     *
     * @param inventory the block's inventory
     * @param output the item which the recipe will output
     * @return true or false
     */
    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(InventorySlots.OUTPUT_SLOT.toInt()).getItem() == output.getItem() || inventory.getStack(InventorySlots.OUTPUT_SLOT.toInt()).isEmpty();
    }

    /**
     * Checks if the Block's output inventory slot has enough space for another item to be added
     * to the stack
     *
     * @param inventory the block's inventory
     * @return true or false
     */
    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(InventorySlots.OUTPUT_SLOT.toInt()).getMaxCount() > inventory.getStack(InventorySlots.OUTPUT_SLOT.toInt()).getCount();
    }
}
