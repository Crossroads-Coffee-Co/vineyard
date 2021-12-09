package net.mwforrest7.vineyard.util;

import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class VineUtil {

    public static boolean isAlongFence(WorldView world, BlockPos pos){
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockState adjacentBlock = world.getBlockState(pos.offset(direction));
            if (adjacentBlock.isIn(BlockTags.FENCES)) {
                return true;
            }
        }
        return false;
    }
}
