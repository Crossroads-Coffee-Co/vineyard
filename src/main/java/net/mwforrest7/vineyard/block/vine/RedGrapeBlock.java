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
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.item.ModItems;

import java.util.Random;
import java.util.stream.Stream;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

/**
 * Grows red grapes
 */
public class RedGrapeBlock extends VineCanopyBlock{
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;

    private static final VoxelShape SHAPE_N_S_0 = Stream.of(
            Block.createCuboidShape(0, 7, 5.5, 3.5, 11, 10.5),
            Block.createCuboidShape(4.5, 5.5, 7.5, 8, 6.5, 8.5),
            Block.createCuboidShape(8, 5.5, 7.5, 11.5, 6.5, 8.5),
            Block.createCuboidShape(0.25, 7.5, 7.5, 2.5, 8.5, 8.5),
            Block.createCuboidShape(13.5, 7.5, 7.5, 15.75, 8.5, 8.5),
            Block.createCuboidShape(2, 6.5, 7.5, 5, 7.5, 8.5),
            Block.createCuboidShape(11, 6.5, 7.5, 14, 7.5, 8.5),
            Block.createCuboidShape(6.5, 5, 5.5, 9.5, 9, 10.5),
            Block.createCuboidShape(9.5, 6, 5.5, 12.5, 10, 10.5),
            Block.createCuboidShape(12.5, 7, 5.5, 16, 11, 10.5),
            Block.createCuboidShape(3.5, 6, 5.5, 6.5, 10, 10.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_W_E_0 = Stream.of(
            Block.createCuboidShape(5.5, 7, 12.5, 10.5, 11, 16),
            Block.createCuboidShape(7.5, 5.5, 8, 8.5, 6.5, 11.5),
            Block.createCuboidShape(7.5, 5.5, 4.5, 8.5, 6.5, 8),
            Block.createCuboidShape(7.5, 7.5, 13.5, 8.5, 8.5, 15.75),
            Block.createCuboidShape(7.5, 7.5, 0.25, 8.5, 8.5, 2.5),
            Block.createCuboidShape(7.5, 6.5, 11, 8.5, 7.5, 14),
            Block.createCuboidShape(7.5, 6.5, 2, 8.5, 7.5, 5),
            Block.createCuboidShape(5.5, 5, 6.5, 10.5, 9, 9.5),
            Block.createCuboidShape(5.5, 6, 3.5, 10.5, 10, 6.5),
            Block.createCuboidShape(5.5, 7, 0, 10.5, 11, 3.5),
            Block.createCuboidShape(5.5, 6, 9.5, 10.5, 10, 12.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public RedGrapeBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(FACING, Direction.NORTH));
    }

    /**
     * Determines size of the outline shape when hovering mouse over the block
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH){
            return SHAPE_W_E_0;

        }else{
            return SHAPE_N_S_0;
        }
    }

    // Has tick updates so long as age is less than max
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    // Executed every server tick
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int currAge = state.get(AGE);

        // If age is less than max age & conditions are right, random chance of aging up
        if (currAge < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, currAge + 1), Block.NOTIFY_LISTENERS);
        }
    }

    // This is executed when right-clicking on the block
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Get age
        int currAge = state.get(AGE);

        // If age = max age, then bl is set to true
        boolean isMaxAge = currAge == MAX_AGE;

        // If not max age, allow use of bone meal when right-clicking
        if (!isMaxAge && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        }
        // If max age, drop 1-3 grape bunches when right-clicking, then reset age to 1
        if (currAge == MAX_AGE) {
            int j = world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(ModItems.RED_GRAPE_BUNCH, j + 1));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
            world.setBlockState(pos, state.with(AGE, 1), Block.NOTIFY_LISTENERS);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    // Grape canopies should be above an air block
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isAir();
    }

    // Grape canopies should be along a fence, along a vine head, and over air
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

    // TODO: Is this needed in addition to the randomTick function?
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(MAX_AGE, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), Block.NOTIFY_LISTENERS);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    // This maintains an association between this block and a vine head block that is of the red grape type
    @Override
    public VineHeadBlock getHeadBlock() {
        return (VineHeadBlock)ModBlocks.RED_GRAPE_HEAD;
    }

    // This maintains an association between this block and an attached vine head block that is of the red grape type
    @Override
    public AttachedVineHeadBlock getAttachedHeadBlock() {
        return (AttachedVineHeadBlock) ModBlocks.ATTACHED_RED_GRAPE_HEAD;
    }

    // Helper function to check that this block is adjacent to a vine head block of red grape type
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
