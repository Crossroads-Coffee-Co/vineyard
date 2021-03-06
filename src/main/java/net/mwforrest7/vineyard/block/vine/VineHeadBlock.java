package net.mwforrest7.vineyard.block.vine;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.Random;
import java.util.stream.Stream;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

/**
 * Top-most middle block of the grapevine. From this block spawns the grape canopies where
 * grapes will grow.
 */
public class VineHeadBlock extends CropBlock {
    private final VineCanopyBlock vineCanopyBlock;
    private final AttachedVineTrunkBlock attachedVineTrunkBlock;
    public static final int MAX_AGE = Properties.AGE_1_MAX;
    public static final IntProperty AGE = Properties.AGE_1;

    public VineHeadBlock(VineCanopyBlock vineCanopyBlock, AttachedVineTrunkBlock attachedVineTrunkBlock, AbstractBlock.Settings settings) {
        super(settings);
        this.vineCanopyBlock = vineCanopyBlock;
        this.attachedVineTrunkBlock = attachedVineTrunkBlock;
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        // If light is insufficient, do nothing
        if (world.getBaseLightLevel(pos, 0) < 9) {
            return;
        }

        // If sufficient moisture...
        float f = CropBlock.getAvailableMoisture(this.attachedVineTrunkBlock, world, pos.down());
        if (random.nextInt((int)(25.0f / f) + 1) == 0) {

            if (!isMature(state)) {
                // If immature - age up
                state = state.with(AGE, state.get(AGE) + 1);
                world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
            }else {
                // Else if mature, grow vine canopies in all horizontal directions where there is open space & a fence
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    BlockPos adjacentBlock = pos.offset(direction);
                    if (world.getBlockState(adjacentBlock).isAir() && isAlongFence(world, adjacentBlock)) {
                        world.setBlockState(adjacentBlock, this.vineCanopyBlock.getDefaultState().with(Properties.HORIZONTAL_FACING, direction));
                    }
                }
                // After canopies are grown, turn into an attached head block
                world.setBlockState(pos, this.vineCanopyBlock.getAttachedHeadBlock().getDefaultState());
            }
        }
    }

    /**
     * Determines size of the outline shape when hovering mouse over the block
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(6.0, 4.0, 6.0, 10.0, 14.0, 10.0),
                Block.createCuboidShape(0.0, 4.0, 6.0, 16.0, 9.0, 10.0),
                Block.createCuboidShape(6.0, 4.0, 0.0, 10.0, 9.0, 16.0),
                Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 4.0, 9.0)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    // Must be on an attached trunk block
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(this.attachedVineTrunkBlock);
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
        return true;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
