package net.mwforrest7.vineyard.block.vine;

import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

/**
 * The main benefit of this attached block is to pause ticking
 * once the regular head block finishing growing canopies. Else,
 * the original block would have to tick indefinitely. This is because
 * the regular block needs to constantly try to spawn canopies once it is max
 * age and has no good sense of when to turn its ticking off.
 */
public class AttachedVineHeadBlock extends PlantBlock {

    private final VineCanopyBlock vineCanopyBlock;

    public AttachedVineHeadBlock(VineCanopyBlock vineCanopyBlock, AbstractBlock.Settings settings) {
        super(settings);
        this.vineCanopyBlock = vineCanopyBlock;
    }

    /**
     * When neighboring blocks cause an update, this function is called.
     *
     * @param state this block instance
     * @param direction the direction of the block that caused the update
     * @param neighborState the neighboring block instance
     * @param world the world
     * @param pos the position of this block instance
     * @param neighborPos the position of the neighboring block instance
     * @return this block instance
     */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // If the block above is no longer a red_grape_head then the attached trunk should revert to a non-attached trunk form
        if ((neighborState.isAir() && (direction == Direction.EAST || direction == Direction.WEST || direction == Direction.NORTH || direction == Direction.SOUTH))
                || neighborState.isIn(BlockTags.FENCES)) {
            return this.vineCanopyBlock.getHeadBlock().getDefaultState().with(VineHeadBlock.AGE, VineHeadBlock.MAX_AGE);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // The block below this block must be an attached grapevine trunk
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.ATTACHED_RED_GRAPEVINE_TRUNK);
    }

    // This block must have proper lighting, must be along a fence, and must be on top
    // of an attached grapevine trunk in order to be placed (and to stay placed)
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos))
                && isAlongFence(world, pos)
                && canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    // Since this is an 'attached' variant of the head block, it should not have ticks
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return false;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
