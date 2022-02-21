package net.mwforrest7.vineyard.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.block.custom.WineCaskBlock;
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
    private final ViewerCountManager stateManager;

    // PropertyDelegate facilitates data updates to the ScreenHandler
    protected final PropertyDelegate propertyDelegate;

    // Crafting progress
    private int progress = DEFAULT_CRAFTING_PROGRESS;

    // Required Crafting progress to complete craft
    private int maxProgress = MAX_CRAFTING_PROGRESS;

    public WineCaskEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WINE_CASK, pos, state);

        this.stateManager = new ViewerCountManager() {
            // Below functions implement abstract functions in the ViewerCountManager abstract class

            // Invoked by the openContainer() function in the ViewerCountManager abstract class
            // Order of operations: ScreenHandler.open() --> BlockEntity.onOpen() --> ViewerCountManager.openContainer()
            // --> ViewCountManager.onContainerOpen() --> BlockEntity.playSound & BlockEntity.setOpen()
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                // Plays sound and then updates the blockstate's OPEN value
                WineCaskEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
                WineCaskEntity.this.setOpen(state, true);
            }

            // Invoked by the closeContainer() function in the ViewerCountManager abstract class
            // Order of operations: ScreenHandler.close() --> BlockEntity.onClose() --> ViewerCountManager.closeContainer()
            // --> ViewCountManager.onContainerClose() --> BlockEntity.playSound & BlockEntity.setClose()
            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                // Plays sound and then updates the blockstate's OPEN value
                WineCaskEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
                WineCaskEntity.this.setOpen(state, false);
            }

            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            protected boolean isPlayerViewing(PlayerEntity player) {
                if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                    return inventory == WineCaskEntity.this;
                } else {
                    return false;
                }
            }
        };

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

    /**
     * Overrides onOpen() in the Inventory interface
     * Invoked via the WineCaskScreenHandler constructor
     *
     * @param player
     */
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            // Invokes the openContainer function in the ViewerCountManager abstract class
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    /**
     * Overrides onClose() in the Inventory interface
     *
     * Invoked via the WineCaskScreenHandler close() function
     *
     * @param player
     */
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            // Invokes the closeContainer function in the ViewerCountManager abstract class
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    /**
     * Updates the value of the block's OPEN state
     *
     * @param state
     * @param open
     */
    void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), state.with(WineCaskBlock.OPEN, open), 3);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.get(WineCaskBlock.FACING).getVector();
        double d = (double)this.pos.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double e = (double)this.pos.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double f = (double)this.pos.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }
}
