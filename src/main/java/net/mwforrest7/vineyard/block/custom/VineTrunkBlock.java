package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.enums.VineType;

import java.util.Random;
import java.util.function.Supplier;

public class VineTrunkBlock extends CropBlock {
    private final String vineType;
    private final Supplier<Item> pickBlockItem;

    public VineTrunkBlock(String vineType, Supplier<Item> pickBlockItem, AbstractBlock.Settings settings) {
        super(settings);
        this.vineType = vineType;
        this.pickBlockItem = pickBlockItem;
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // If light is insufficient, do nothing
        if (world.getBaseLightLevel(pos, 0) < 9) {
            return;
        }

        // If sufficient moisture...
        float f = CropBlock.getAvailableMoisture(this, world, pos);
        if (random.nextInt((int)(25.0f / f) + 1) == 0) {

            if (!isMature(state)) {
                // If immature - age up
                System.out.println("Aging up!");
                state = state.with(AGE, state.get(AGE) + 1);
                world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
            } else {
                // Else if mature and has space, create vine head block above
                System.out.println("Is mature");
                Direction direction = Direction.UP;
                BlockPos blockAbovePos = pos.offset(direction);
                if (world.getBlockState(blockAbovePos).isAir()) {
                    System.out.println("Block above is air");
                    if (this.vineType.equals(VineType.RED_GRAPE.toString())) {
                        System.out.println("Vine is type red grape");
                        world.setBlockState(blockAbovePos, ModBlocks.RED_GRAPE_HEAD.getDefaultState());
                        world.setBlockState(pos, ModBlocks.ATTACHED_RED_GRAPEVINE_TRUNK.getDefaultState().with(Properties.FACING, direction));
                    }
                }
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.pickBlockItem.get());
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(7, state.get(AGE) + MathHelper.nextInt(world.random, 2, 5));
        BlockState blockState = state.with(AGE, i);
        world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
        if (i == 7) {
            blockState.randomTick(world, pos, world.random);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
