package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

public class AttachedVineHeadBlock extends PlantBlock {

    private final VineCanopyBlock vineCanopyBlock;

    public AttachedVineHeadBlock(VineCanopyBlock vineCanopyBlock, AbstractBlock.Settings settings) {
        super(settings);
        this.vineCanopyBlock = vineCanopyBlock;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // If the block above is no longer a red_grape_head then the attached trunk should revert to a non-attached trunk form
        if ((neighborState.isAir() && (direction == Direction.EAST || direction == Direction.WEST || direction == Direction.NORTH || direction == Direction.SOUTH))
                || neighborState.isIn(BlockTags.FENCES)) {
            return this.vineCanopyBlock.getHeadBlock().getDefaultState().with(VineHeadBlock.AGE, 7);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.ATTACHED_RED_GRAPEVINE_TRUNK);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos))
                && isAlongFence(world, pos)
                && canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return false;
    }
}
