package net.mwforrest7.vineyard.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.world.gen.ModWorldGen;

public class TrunkGreenGrapevineTreeDecorator extends TreeDecorator {
    public static final TrunkGreenGrapevineTreeDecorator INSTANCE = new TrunkGreenGrapevineTreeDecorator();

    public static final Codec<TrunkGreenGrapevineTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    public TrunkGreenGrapevineTreeDecorator() {
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModWorldGen.TRUNK_GREEN_GRAPEVINE_TREE_DECORATOR;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        generator.getLogPositions().forEach((pos) -> {
            BlockPos blockPos;
            if (random.nextInt(3) > 0) {
                blockPos = pos.west();
                if (generator.isAir(blockPos)) {
                    replaceWithVine(blockPos, VineBlock.EAST, generator);
                }
            }

            if (random.nextInt(3) > 0) {
                blockPos = pos.east();
                if (generator.isAir(blockPos)) {
                    replaceWithVine(blockPos, VineBlock.WEST, generator);
                }
            }

            if (random.nextInt(3) > 0) {
                blockPos = pos.north();
                if (generator.isAir(blockPos)) {
                    replaceWithVine(blockPos, VineBlock.SOUTH, generator);
                }
            }

            if (random.nextInt(3) > 0) {
                blockPos = pos.south();
                if (generator.isAir(blockPos)) {
                    replaceWithVine(blockPos, VineBlock.NORTH, generator);
                }
            }

        });
    }

    private void replaceWithVine(BlockPos pos, BooleanProperty faceProperty, Generator generator) {
        generator.replace(pos, ModBlocks.WILD_GREEN_GRAPEVINE.getDefaultState().with(faceProperty, true));
    }
}
