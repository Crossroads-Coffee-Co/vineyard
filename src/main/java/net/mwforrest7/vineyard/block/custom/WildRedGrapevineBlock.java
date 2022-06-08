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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.item.ModItems;

public class WildRedGrapevineBlock extends VineBlock {

    public static final int MAX_AGE = 2;
    public static final IntProperty AGE = Properties.AGE_2;

    // Constructor
    public WildRedGrapevineBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(AGE, 0));
    }

    /**
     * Every tick, this function is executed
     *
     /* @param state the block instance
     /* @param world the world
     /* @param pos the block position
     /* @param random random number generator
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // If age is less than max, and other conditions are correct: age up
        super.randomTick(state, world, pos, random);
        int i = state.get(AGE);
        System.out.println("Age is " + i);
        if (i < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            System.out.println("Aged up!");
        }
    }

    // This is executed when right-clicking on the block
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Get age
        int age = state.get(AGE);

        // If age is equal to max age, set isMaxeAge to true, else false.
        boolean isMaxAge = age == MAX_AGE;

        // If the block is max age, drop 1-3 grapes and set the age to 0
        if (isMaxAge) {
            int j = world.random.nextInt(2);
            WildRedGrapevineBlock.dropStack(world, pos, new ItemStack(ModItems.RED_GRAPE, j + 1));
            world.playSound(null, pos, SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
            world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, UP, NORTH, EAST, SOUTH, WEST);
    }
}
