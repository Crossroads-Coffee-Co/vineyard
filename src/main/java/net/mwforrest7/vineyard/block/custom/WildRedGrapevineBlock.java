package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.item.ModItems;

import java.util.Random;
import java.util.stream.Stream;

public class WildRedGrapevineBlock extends PlantBlock implements Fertilizable {
    public static final int MAX_AGE = 2;
    public static final IntProperty AGE = Properties.AGE_2;

    // Constructor
    public WildRedGrapevineBlock(AbstractBlock.Settings settings) {
        super(settings);

        // New grapevines should start at age 0
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    /**
     * Block has updates every 1/20th of a second so long as age is
     * less than max.
     *
     * @param state the block instance
     * @return true or false
     */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    /**
     * Every tick, this function is executed
     *
     * @param state the block instance
     * @param world the world
     * @param pos the block position
     * @param random random number generator
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // If age is less than max, and other conditions are correct: age up
        int i = state.get(AGE);
        if (i < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
        }
    }

    // This is executed when right-clicking on the block
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Get age
        int age = state.get(AGE);

        // If age is equal to max age, set isMaxeAge to true, else false.
        boolean isMaxAge = age == MAX_AGE;

        // If the block is not max age, then allow for the use of bone meal when right-clicking
        if (!isMaxAge && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        }
        // If the block is max age, drop 1-3 grapes and set the age to 0
        if (isMaxAge) {
            int j = world.random.nextInt(2);
            WildRedGrapevineBlock.dropStack(world, pos, new ItemStack(ModItems.RED_GRAPE, j + 1));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
            world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    /**
     * Determines size of the outline shape when hovering mouse over the block
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0),
                Block.createCuboidShape(0.0, 7.0, 5.0, 16.0, 11.0, 11.0),
                Block.createCuboidShape(5.0, 7.0, 0.0, 11.0, 11.0, 16.0)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    // This is an implementation of the Fertilizable interface - I believe this is called when applying bone meal
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(MAX_AGE, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), Block.NOTIFY_LISTENERS);
    }
}
