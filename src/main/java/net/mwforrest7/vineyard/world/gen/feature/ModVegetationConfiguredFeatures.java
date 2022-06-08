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
                            List.of(new RandomFeatureEntry(ModTreePlacedFeatures.FANCY_OAK_RED_GRAPEVINE_TREE, 0.33333334F)),
                            ModTreePlacedFeatures.OAK_RED_GRAPEVINE_TREE));
}
