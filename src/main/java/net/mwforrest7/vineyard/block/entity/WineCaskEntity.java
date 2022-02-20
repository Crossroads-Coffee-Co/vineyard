package net.mwforrest7.vineyard.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.item.inventory.ImplementedInventory;
import net.mwforrest7.vineyard.recipe.WineCaskRecipe;
import net.mwforrest7.vineyard.screen.WineCaskScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.mwforrest7.vineyard.block.entity.properties.WineCaskProperties.*;

/**
 * This is like a controller in MVC. The brains of the operation.
 * It reads and writes data, handles game logic, and updates the view (ScreenHandler)
 */
public class WineCaskEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

    // PropertyDelegate facilitates data updates to the ScreenHandler
    protected final PropertyDelegate propertyDelegate;

    // Crafting progress
    private int progress = DEFAULT_CRAFTING_PROGRESS;

    // Required Crafting progress to complete craft
    private int maxProgress = MAX_CRAFTING_PROGRESS;

    public WineCaskEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WINE_CASK, pos, state);

        System.out.println("In WineCaskEntity constructor");

        // Initialize the PropertyDelegate
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> WineCaskEntity.this.progress;
                    case 1 -> WineCaskEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WineCaskEntity.this.progress = value;
                    case 1 -> WineCaskEntity.this.maxProgress = value;
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
    public Text getDisplayName() {
        return new LiteralText(DISPLAY_NAME);
    }

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
        return new WineCaskScreenHandler(syncId, inv, this, this.propertyDelegate);
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
    public static void tick(World world, BlockPos pos, BlockState state, WineCaskEntity entity) {
        // If there is a recipe that matches the Block's inventory...
        if(hasRecipe(entity)) {
            // Increase crafting progress
            entity.progress++;

            // If crafting progress reaches max, then complete the craft
            if(entity.progress > entity.maxProgress) {
                craftItem(entity);
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
     * Matches the Block's inventory to a recipe. If there is a match,
     * subtracts ingredients from the Block's inventory and produces
     * the resulting craft.
     *
     * @param entity the block entity instance
     */
    private static void craftItem(WineCaskEntity entity) {
        World world = entity.world;

        // Copies the Block's inventory into a temp inventory var
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        // Attempts to match the inventory items to a recipe
        Optional<WineCaskRecipe> match = world.getRecipeManager().getFirstMatch(WineCaskRecipe.Type.INSTANCE, inventory, world);

        // If there is a match, updates the inventory as appropriate
        if(match.isPresent()) {
            // Removes 1 from each ingredient slot
            entity.removeStack(InventorySlots.INGREDIENT_SLOT_1.toInt(),1);

            // Replace block with aged cask
            BlockPos pos = entity.getPos();
            world.removeBlock(pos, false);
            world.removeBlockEntity(pos);
            world.setBlockState(pos, Blocks.BARREL.getDefaultState().with(Properties.FACING, entity.getCachedState().get(Properties.FACING)));
        }
    }

    /**
     * Checks if there is a matching recipe and also performs
     * a couple of other safety checks.
     *
     * @param entity the block entity instance
     * @return true or false
     */
    private static boolean hasRecipe(WineCaskEntity entity) {
        World world = entity.world;

        // Copies the Block's inventory into a temp inventory var
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        // Attempts to match the inventory items to a recipe
        Optional<WineCaskRecipe> match = world.getRecipeManager().getFirstMatch(WineCaskRecipe.Type.INSTANCE, inventory, world);

        // Returns true if there is a match
        return match.isPresent();
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
    }
}
