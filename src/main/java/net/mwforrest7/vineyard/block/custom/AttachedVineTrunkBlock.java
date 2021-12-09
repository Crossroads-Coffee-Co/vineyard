package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.enums.VineType;

import java.util.function.Supplier;

public class AttachedVineTrunkBlock extends PlantBlock {
    public static final DirectionProperty FACING = Properties.FACING;
    protected static final float field_30995 = 2.0f;
    private final String vineType;
    private final Supplier<Item> pickBlockItem;

    public AttachedVineTrunkBlock(String vineType, Supplier<Item> pickBlockItem, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
        this.vineType = vineType;
        this.pickBlockItem = pickBlockItem;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(this.vineType.equals(VineType.RED_GRAPE.toString())){
            // If the block above is no longer a red_grape_head then the attached trunk should revert to a non-attached trunk form
            if (!(neighborState.isOf(ModBlocks.RED_GRAPE_HEAD) || neighborState.isOf(ModBlocks.ATTACHED_RED_GRAPE_HEAD)) && direction == state.get(FACING)) {
                return ModBlocks.RED_GRAPEVINE_TRUNK.getDefaultState().with(VineTrunkBlock.AGE, 7);
            }
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.pickBlockItem.get());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
