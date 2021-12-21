package net.mwforrest7.vineyard.block.vine;

import net.minecraft.block.*;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.enums.VineType;

import java.util.Random;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

public class VineTrunkBlock extends CropBlock {
    private final String vineType;

    public VineTrunkBlock(String vineType, AbstractBlock.Settings settings) {
        super(settings);
        this.vineType = vineType;
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // If light is insufficient, do nothing
        if (world.getBaseLightLevel(pos, 0) < 9) {
            return;
        }

        // If sufficient moisture...
        float availableMoisture = CropBlock.getAvailableMoisture(this, world, pos);
        if (random.nextInt((int)(25.0f / availableMoisture) + 1) == 0) {

            if (!isMature(state)) {
                // If immature - age up
                state = state.with(AGE, state.get(AGE) + 1);
                world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
            } else {
                // Else if mature, has space, and has fence support above, create vine head block above
                Direction direction = Direction.UP;
                BlockPos blockAbovePos = pos.offset(direction);
                if (world.getBlockState(blockAbovePos).isAir() && isAlongFence(world, blockAbovePos)) {
                    if (this.vineType.equals(VineType.RED_GRAPE.toString())) {
                        world.setBlockState(blockAbovePos, ModBlocks.RED_GRAPE_HEAD.getDefaultState());
                        world.setBlockState(pos, ModBlocks.ATTACHED_RED_GRAPEVINE_TRUNK.getDefaultState().with(Properties.FACING, direction));
                    }
                }
            }
        }
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
