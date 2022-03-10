package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.*;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

/**
 * This block is the visual representation of a Wine Cask and facilitates interaction
 * with the WineCaskEntity and WineCaskScreenHandler
 */
public class AgedRedWineBlock extends FacingBlock {

    public AgedRedWineBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
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
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
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
}
