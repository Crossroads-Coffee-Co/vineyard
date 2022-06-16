package net.mwforrest7.vineyard.world.gen.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

/**
 * Generally class registers features and defines their characteristics
 */
public class ModVegetationConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> TREES_WILD_GRAPEVINE_OAK = ConfiguredFeatures
            .register("trees_wild_grapevine_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(
                            List.of(new RandomFeatureEntry(ModTreePlacedFeatures.FANCY_OAK_RED_GRAPEVINE_TREE, 0.33333334F),
                                    new RandomFeatureEntry(ModTreePlacedFeatures.OAK_GREEN_GRAPEVINE_TREE, 0.5F)),
                            ModTreePlacedFeatures.OAK_RED_GRAPEVINE_TREE));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> TREES_WILD_GRAPEVINE_BIRCH = ConfiguredFeatures
            .register("trees_wild_grapevine_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(
                            List.of(new RandomFeatureEntry(ModTreePlacedFeatures.SUPER_BIRCH_RED_GRAPEVINE_TREE, 0.33333334F),
                                    new RandomFeatureEntry(ModTreePlacedFeatures.BIRCH_GREEN_GRAPEVINE_TREE, 0.5F)),
                            ModTreePlacedFeatures.BIRCH_RED_GRAPEVINE_TREE));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> TREES_WILD_GRAPEVINE_DARK_OAK = ConfiguredFeatures
            .register("trees_wild_grapevine_dark_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(
                            List.of(new RandomFeatureEntry(ModTreePlacedFeatures.DARK_OAK_GREEN_GRAPEVINE_TREE, 0.5F)),
                            ModTreePlacedFeatures.DARK_OAK_RED_GRAPEVINE_TREE));
}
