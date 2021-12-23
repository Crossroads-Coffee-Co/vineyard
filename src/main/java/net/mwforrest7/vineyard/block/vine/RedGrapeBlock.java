package net.mwforrest7.vineyard.block.vine;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.item.ModItems;

import java.util.Random;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

public class RedGrapeBlock extends VineCanopyBlock{
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;
    private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    private static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public RedGrapeBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(AGE) == 0) {
            return SMALL_SHAPE;
        }
        if (state.get(AGE) < MAX_AGE) {
            return LARGE_SHAPE;
        }
        return super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int currAge = state.get(AGE);
        if (currAge < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, currAge + 1), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean bl;
        int currAge = state.get(AGE);
        boolean bl2 = bl = currAge == MAX_AGE;
        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        }
        if (currAge == MAX_AGE) {
            int j = 1 + world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(ModItems.RED_GRAPE_BUNCH, j + (bl ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
            world.setBlockState(pos, state.with(AGE, 1), Block.NOTIFY_LISTENERS);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isAir();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return (isAlongFence(world, pos)
                && isAlongVineHead(world, pos)
                && canPlantOnTop(world.getBlockState(blockPos), world, blockPos));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(FACING);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(MAX_AGE, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), Block.NOTIFY_LISTENERS);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public VineHeadBlock getHeadBlock() {
        return (VineHeadBlock)ModBlocks.RED_GRAPE_HEAD;
    }

    @Override
    public AttachedVineHeadBlock getAttachedHeadBlock() {
        return (AttachedVineHeadBlock) ModBlocks.ATTACHED_RED_GRAPE_HEAD;
    }

    private boolean isAlongVineHead(WorldView world, BlockPos pos){
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockState adjacentBlock = world.getBlockState(pos.offset(direction));
            if (adjacentBlock.isOf(ModBlocks.ATTACHED_RED_GRAPE_HEAD) || adjacentBlock.isOf(ModBlocks.RED_GRAPE_HEAD)) {
                return true;
            }
        }
        return false;
    }
}
