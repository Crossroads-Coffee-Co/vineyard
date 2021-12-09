package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.enums.VineType;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

public class AttachedVineTrunkBlock extends PlantBlock {
    public static final DirectionProperty FACING = Properties.FACING;
    private final String vineType;

    public AttachedVineTrunkBlock(String vineType, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
        this.vineType = vineType;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(this.vineType.equals(VineType.RED_GRAPE.toString())){
            // If the block above is no longer a red_grape_head then the attached trunk should revert to a non-attached trunk form
            if (!(neighborState.isOf(ModBlocks.RED_GRAPE_HEAD) || neighborState.isOf(ModBlocks.ATTACHED_RED_GRAPE_HEAD)) && direction == state.get(FACING)) {
                return ModBlocks.RED_GRAPEVINE_TRUNK.getDefaultState().with(VineTrunkBlock.AGE, 7);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
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

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
