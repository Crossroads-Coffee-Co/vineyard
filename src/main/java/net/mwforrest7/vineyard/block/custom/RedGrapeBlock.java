package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.mwforrest7.vineyard.block.ModBlocks;

public class RedGrapeBlock extends VineCanopyBlock{
    public RedGrapeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player2, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!world.isClient) {
                Direction direction = hit.getSide();
                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player2.getHorizontalFacing().getOpposite() : direction;
                world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, (BlockState) Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, direction2), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + 0.5 + (double) direction2.getOffsetX() * 0.65, (double) pos.getY() + 0.1, (double) pos.getZ() + 0.5 + (double) direction2.getOffsetZ() * 0.65, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itemEntity.setVelocity(0.05 * (double) direction2.getOffsetX() + world.random.nextDouble() * 0.02, 0.05, 0.05 * (double) direction2.getOffsetZ() + world.random.nextDouble() * 0.02);
                world.spawnEntity(itemEntity);
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
                world.emitGameEvent((Entity) player2, GameEvent.SHEAR, pos);
                player2.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
            }
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player2, hand, hit);
    }

    @Override
    public VineHeadBlock getHeadBlock() {
        return (VineHeadBlock)ModBlocks.RED_GRAPE_HEAD;
    }

    @Override
    public AttachedVineHeadBlock getAttachedHeadBlock() {
        return (AttachedVineHeadBlock) ModBlocks.ATTACHED_RED_GRAPE_HEAD;
    }
}
