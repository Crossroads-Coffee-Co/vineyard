package net.mwforrest7.vineyard.block.vine;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.enums.VineType;

import java.util.stream.Stream;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

/**
 * The main benefit of this attached block is to pause ticking
 * once the regular trunk block finishing growing a head block. Else,
 * the original block would have to tick indefinitely. This is because
 * the regular block needs to constantly try to spawn a head block once it is max
 * age and has no good sense of when to turn its ticking off.
 */
public class AttachedVineTrunkBlock extends PlantBlock {
    public static final DirectionProperty FACING = Properties.FACING;
    private final String vineType;

    public AttachedVineTrunkBlock(String vineType, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
        this.vineType = vineType;
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
        if(this.vineType.equals(VineType.RED_GRAPE.toString())){
            // If the block above is no longer a red_grape_head then the attached trunk should revert to a non-attached trunk form
            if (!(neighborState.isOf(ModBlocks.RED_GRAPE_HEAD) || neighborState.isOf(ModBlocks.ATTACHED_RED_GRAPE_HEAD)) && direction == state.get(FACING)) {
                return ModBlocks.RED_GRAPEVINE_TRUNK.getDefaultState().with(VineTrunkBlock.AGE, 7);
            }
        }else if(this.vineType.equals(VineType.GREEN_GRAPE.toString())){
            // If the block above is no longer a red_grape_head then the attached trunk should revert to a non-attached trunk form
            if (!(neighborState.isOf(ModBlocks.GREEN_GRAPE_HEAD) || neighborState.isOf(ModBlocks.ATTACHED_GREEN_GRAPE_HEAD)) && direction == state.get(FACING)) {
                return ModBlocks.GREEN_GRAPEVINE_TRUNK.getDefaultState().with(VineTrunkBlock.AGE, 7);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    /**
     * Determines size of the outline shape when hovering mouse over the block
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(0.0, 0.0, 1.0, 15.0, 3.0, 15.0),
                Block.createCuboidShape(5.0, 3.0, 6.0, 9.0, 16.0, 10.0)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
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
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
