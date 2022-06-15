package net.mwforrest7.vineyard.world.gen.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.mwforrest7.vineyard.world.gen.treedecorator.TrunkRedGrapevineTreeDecorator;

import java.util.List;
import java.util.OptionalInt;

public class ModTreeConfiguredFeatures {

    private static TreeFeatureConfig.Builder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log), new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight), BlockStateProvider.of(leaves), new BlobFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeFeatureConfig.Builder birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 0, 2);
    }

    private static TreeFeatureConfig.Builder superBirch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 6, 2);
    }

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> OAK_RED_GRAPEVINE_TREE =
            ConfiguredFeatures.register("oak_red_grapevine_tree", Feature.TREE, (new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new BendingTrunkPlacer(4, 2, 0, 3, UniformIntProvider.create(1, 2)),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
            new TwoLayersFeatureSize(1, 0, 1)))
            .decorators(ImmutableList.of(TrunkRedGrapevineTreeDecorator.INSTANCE)).build());

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FANCY_OAK_RED_GRAPEVINE_TREE =
            ConfiguredFeatures.register("fancy_oak_red_grapevine_tree", Feature.TREE, (new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(Blocks.OAK_LOG),
                    new LargeOakTrunkPlacer(3, 11, 0),
                    BlockStateProvider.of(Blocks.OAK_LEAVES),
                    new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                    .decorators(ImmutableList.of(TrunkRedGrapevineTreeDecorator.INSTANCE)).build());

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> BIRCH_RED_GRAPEVINE_TREE =
            ConfiguredFeatures.register("birch_red_grapevine_tree",
            Feature.TREE,
            birch().decorators(List.of(TrunkRedGrapevineTreeDecorator.INSTANCE)).build());

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> SUPER_BIRCH_RED_GRAPEVINE_TREE =
            ConfiguredFeatures.register("super_birch_red_grapevine_tree",
                    Feature.TREE,
                    superBirch().decorators(List.of(TrunkRedGrapevineTreeDecorator.INSTANCE)).build());

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> DARK_OAK_RED_GRAPEVINE_TREE =
            ConfiguredFeatures.register("dark_oak_red_grapevine_tree", Feature.TREE,
                    (new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.DARK_OAK_LOG),
                            new DarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
                            new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                            new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())))
                            .decorators(List.of(TrunkRedGrapevineTreeDecorator.INSTANCE)).build());

}
