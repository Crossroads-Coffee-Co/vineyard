package net.mwforrest7.vineyard.world.gen.feature;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.block.custom.WildRedGrapevineBlock;

import java.util.List;

public class ModVegetationConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_WILD_RED_GRAPEVINE = ConfiguredFeatures.register("patch_wild_red_grapevine", Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WILD_RED_GRAPEVINE.getDefaultState().with(WildRedGrapevineBlock.AGE, WildRedGrapevineBlock.MAX_AGE)))), List.of(Blocks.GRASS_BLOCK), 20)));
}
