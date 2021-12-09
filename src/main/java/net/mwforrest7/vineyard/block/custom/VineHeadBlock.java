package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.Random;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

public class VineHeadBlock extends CropBlock {
    private final VineCanopyBlock vineCanopyBlock;
    private final AttachedVineTrunkBlock attachedVineTrunkBlock;

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
            } else {
                // Else if mature, grow vine canopies in all horizontal directions where there is open space & a fence
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    BlockPos adjacentBlock = pos.offset(direction);
                    if (world.getBlockState(adjacentBlock).isAir() && isAlongFence(world, adjacentBlock)) {
                        world.setBlockState(adjacentBlock, this.vineCanopyBlock.getDefaultState());
                    }
                }
                // After canopies are grown, turn into an attached head block
                world.setBlockState(pos, this.vineCanopyBlock.getAttachedHeadBlock().getDefaultState());
            }
        }
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
