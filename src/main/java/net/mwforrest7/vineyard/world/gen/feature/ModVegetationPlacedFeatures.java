package net.mwforrest7.vineyard.world.gen.feature;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.config.ModConfigs;

import static net.minecraft.world.gen.feature.VegetationPlacedFeatures.modifiers;

/**
 * Generally, this class defines how the configured features generate
 */
public class ModVegetationPlacedFeatures {
    public static final RegistryKey<PlacedFeature> TREE_OAK_GRAPEVINE_KEY = registerKey("tree_oak_grapevine");
    public static final RegistryKey<PlacedFeature> TREE_BIRCH_GRAPEVINE_KEY = registerKey("tree_birch_grapevine");
    public static final RegistryKey<PlacedFeature> TREE_DARK_OAK_GRAPEVINE_KEY = registerKey("tree_dark_oak_grapevine");

    public static final RegistryEntry<PlacedFeature> TREE_OAK_GRAPEVINE = PlacedFeatures
            .register(TREE_OAK_GRAPEVINE_KEY.getValue().toString(),
            ModVegetationConfiguredFeatures.TREES_WILD_GRAPEVINE_OAK,
            modifiers(RarityFilterPlacementModifier.of(ModConfigs.WILD_GRAPE_SPAWN_CHANCE)));

    public static final RegistryEntry<PlacedFeature> TREE_BIRCH_GRAPEVINE = PlacedFeatures
            .register(TREE_BIRCH_GRAPEVINE_KEY.getValue().toString(),
                    ModVegetationConfiguredFeatures.TREES_WILD_GRAPEVINE_BIRCH,
                    modifiers(RarityFilterPlacementModifier.of(ModConfigs.WILD_GRAPE_SPAWN_CHANCE)));

    public static final RegistryEntry<PlacedFeature> TREE_DARK_OAK_GRAPEVINE = PlacedFeatures
            .register(TREE_DARK_OAK_GRAPEVINE_KEY.getValue().toString(),
                    ModVegetationConfiguredFeatures.TREES_WILD_GRAPEVINE_DARK_OAK,
                    modifiers(RarityFilterPlacementModifier.of(ModConfigs.WILD_GRAPE_SPAWN_CHANCE)));

    private static RegistryKey<PlacedFeature> registerKey(String id) {
        return RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(VineyardMod.MOD_ID, id));
    }
}
