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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
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
                Block.createCuboidShape(4.5, 4.5, 7.5, 7.5, 5.5, 8.5),
                Block.createCuboidShape(8.5, 4.5, 7.5, 11.5, 5.5, 8.5),
                Block.createCuboidShape(7.5, 4.5, 8.5, 8.5, 5.5, 11.5),
                Block.createCuboidShape(7.5, 4.5, 4.5, 8.5, 5.5, 7.5),
                Block.createCuboidShape(0.25, 6.5, 7.5, 2.5, 7.5, 8.5),
                Block.createCuboidShape(13.5, 6.5, 7.5, 15.75, 7.5, 8.5),
                Block.createCuboidShape(7.5, 6.5, 13.5, 8.5, 7.5, 15.75),
                Block.createCuboidShape(7.5, 6.5, 0.25, 8.5, 7.5, 2.5),
                Block.createCuboidShape(7.5, 4, 7.5, 8.5, 13.75, 8.5),
                Block.createCuboidShape(7, 0, 7, 9, 4, 9),
                Block.createCuboidShape(2, 5.5, 7.5, 5, 6.5, 8.5),
                Block.createCuboidShape(11, 5.5, 7.5, 14, 6.5, 8.5),
                Block.createCuboidShape(7.5, 5.5, 11, 8.5, 6.5, 14),
                Block.createCuboidShape(7.5, 5.5, 2, 8.5, 6.5, 5),
                Block.createCuboidShape(6.5, 4, 6.5, 9.5, 7, 9.5),
                Block.createCuboidShape(6.5, 8, 6.5, 9.5, 11.5, 9.5),
                Block.createCuboidShape(7, 11.5, 7, 9, 14, 9),
                Block.createCuboidShape(9.5, 5, 6.5, 12.5, 8, 9.5),
                Block.createCuboidShape(12.5, 6, 6.5, 16, 9, 9.5),
                Block.createCuboidShape(3.5, 5, 6.5, 6.5, 8, 9.5),
                Block.createCuboidShape(0, 6, 6.5, 3.5, 9, 9.5),
                Block.createCuboidShape(6.5, 5, 9.5, 9.5, 8, 12.5),
                Block.createCuboidShape(6.5, 5, 3.5, 9.5, 8, 6.5),
                Block.createCuboidShape(6.5, 6, 0, 9.5, 9, 3.5),
                Block.createCuboidShape(6.5, 6, 12.5, 9.5, 9, 16)
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
