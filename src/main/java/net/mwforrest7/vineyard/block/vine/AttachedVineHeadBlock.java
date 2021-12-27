package net.mwforrest7.vineyard.block.vine;

import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.mwforrest7.vineyard.block.ModBlocks;

import java.util.stream.Stream;

import static net.mwforrest7.vineyard.util.VineUtil.isAlongFence;

/**
 * The main benefit of this attached block is to pause ticking
 * once the regular head block finishing growing canopies. Else,
 * the original block would have to tick indefinitely. This is because
 * the regular block needs to constantly try to spawn canopies once it is max
 * age and has no good sense of when to turn its ticking off.
 */
public class AttachedVineHeadBlock extends PlantBlock {

    private final VineCanopyBlock vineCanopyBlock;

    public AttachedVineHeadBlock(VineCanopyBlock vineCanopyBlock, AbstractBlock.Settings settings) {
        super(settings);
        this.vineCanopyBlock = vineCanopyBlock;
    }

    /**
     * When neighboring blocks cause an update, this function is called.
     *
     * @param state this block instance
     * @param direction the direction of the block that caused the update
     * @param neighborState the neighboring block instance
     * @param world the world
     * @param pos the position of this block instance
     * @param neighborPos the position of the neighboring block instance
     * @return this block instance
     */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // If the block above is no longer a red_grape_head then the attached trunk should revert to a non-attached trunk form
        if ((neighborState.isAir() && (direction == Direction.EAST || direction == Direction.WEST || direction == Direction.NORTH || direction == Direction.SOUTH))
                || neighborState.isIn(BlockTags.FENCES)) {
            return this.vineCanopyBlock.getHeadBlock().getDefaultState().with(VineHeadBlock.AGE, VineHeadBlock.MAX_AGE);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    /**
     * Determines size of the outline shape when hovering mouse over the block
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(4.5, 7, 11.5, 11.5, 12, 16),
                Block.createCuboidShape(4.5, 5.5, 7.5, 7.5, 6.5, 8.5),
                Block.createCuboidShape(8.5, 5.5, 7.5, 11.5, 6.5, 8.5),
                Block.createCuboidShape(7.5, 5.5, 8.5, 8.5, 6.5, 11.5),
                Block.createCuboidShape(7.5, 5.5, 4.5, 8.5, 6.5, 7.5),
                Block.createCuboidShape(0.25, 7.5, 7.5, 2.5, 8.5, 8.5),
                Block.createCuboidShape(13.5, 7.5, 7.5, 15.75, 8.5, 8.5),
                Block.createCuboidShape(7.5, 7.5, 13.5, 8.5, 8.5, 15.75),
                Block.createCuboidShape(7.5, 7.5, 0.25, 8.5, 8.5, 2.5),
                Block.createCuboidShape(7.5, 4, 7.5, 8.5, 14, 8.5),
                Block.createCuboidShape(7, 0, 7, 9, 4, 9),
                Block.createCuboidShape(2, 6.5, 7.5, 5, 7.5, 8.5),
                Block.createCuboidShape(11, 6.5, 7.5, 14, 7.5, 8.5),
                Block.createCuboidShape(7.5, 6.5, 11, 8.5, 7.5, 14),
                Block.createCuboidShape(7.5, 6.5, 2, 8.5, 7.5, 5),
                Block.createCuboidShape(5, 3, 5, 11, 7, 11),
                Block.createCuboidShape(4.5, 7, 4.5, 11.5, 14.5, 11.5),
                Block.createCuboidShape(6, 14.5, 6, 10, 16, 10),
                Block.createCuboidShape(11.5, 7, 4.5, 16, 12, 11.5),
                Block.createCuboidShape(0, 7, 4.5, 4.5, 12, 11.5),
                Block.createCuboidShape(4.5, 7, 0, 11.5, 12, 4.5)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }


    // The block below this block must be an attached grapevine trunk
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.ATTACHED_RED_GRAPEVINE_TRUNK);
    }

    // This block must have proper lighting, must be along a fence, and must be on top
    // of an attached grapevine trunk in order to be placed (and to stay placed)
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos))
                && isAlongFence(world, pos)
                && canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    // Since this is an 'attached' variant of the head block, it should not have ticks
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return false;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
