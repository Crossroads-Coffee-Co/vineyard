package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.block.entity.ModBlockEntities;
import net.mwforrest7.vineyard.block.entity.FruitPressEntity;
import org.jetbrains.annotations.Nullable;

/**
 * This block is the visual representation of a Grape Press and facilitates interaction
 * with the GrapePressEntity and GrapePressScreenHandler
 */
public class FruitPressBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public FruitPressBlock(Settings settings) {
        super(settings);
    }

    /**
     * Gets the direction that is opposite of the direction which the player is
     * facing. That way, the block's facing direction is toward the player.
     *
     * @param ctx the context under which the item is placed
     * @return the block instance
     */
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**
     * Invisible by default, must be overridden to make the block visible
     *
     * @param state the block instance
     * @return BlockRenderType
     */
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /**
     * Drops the BlockEntity's inventory on the ground when the block is destroyed
     *
     * @param state the previous block instance
     * @param world the world
     * @param pos the block position
     * @param newState the new block instance which is replacing the previous block
     * @param moved true if moved, false if not
     */
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FruitPressEntity) {
                ItemScatterer.spawn(world, pos, (FruitPressEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    /**
     * Opens the screen handler when right-clicking on the block
     *
     * @param state the block instance
     * @param world the world
     * @param pos the block position
     * @param player the player instance
     * @param hand the player's hand
     * @param hit BlockHitResult
     * @return ActionResult
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            // This is just the GrapePressEntity (BlockEntity) which implements NamedScreenHandlerFactory.
            // This entity is also where the relationship to the corresponding ScreenHandler is established.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FruitPressEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // Makes sure the given BlockEntityType matches the expected type (GrapePressEntity in this case)
        // then gets the corresponding ticker
        return checkType(type, ModBlockEntities.FRUIT_PRESS, FruitPressEntity::tick);
    }
}
