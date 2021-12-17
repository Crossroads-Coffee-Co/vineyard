package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.Random;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

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
            System.out.println("Age is: " + state.get(AGE));

            if (!isMature(state)) {
                System.out.println("is not mature... aging up");
                // If immature - age up
                state = state.with(AGE, state.get(AGE) + 1);
                world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
            }else {
                System.out.println("Is mature.");
                // Else if mature, grow vine canopies in all horizontal directions where there is open space & a fence
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    BlockPos adjacentBlock = pos.offset(direction);
                    if (world.getBlockState(adjacentBlock).isAir() && isAlongFence(world, adjacentBlock)) {
                        System.out.println("Adjacent block is air and along fence. Setting to canopy.");
                        world.setBlockState(adjacentBlock, this.vineCanopyBlock.getDefaultState());
                    }
                }
                // After canopies are grown, turn into an attached head block
                System.out.println("Canopies set. Changing this block to attached head.");
                world.setBlockState(pos, this.vineCanopyBlock.getAttachedHeadBlock().getDefaultState());
                System.out.println("Finished.");
            }
        }
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

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
