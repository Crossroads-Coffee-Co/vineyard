package net.mwforrest7.vineyard.world.gen.feature;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.config.ModConfigs;

import java.util.List;

/**
 * Generally, this class defines how the configured features generate
 */
public class ModVegetationPlacedFeatures {
    public static final RegistryKey<PlacedFeature> PATCH_WILD_RED_GRAPEVINE_COMMON_KEY = registerKey("patch_wild_red_grapevine_common");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_RED_GRAPEVINE_RARE_KEY = registerKey("patch_wild_red_grapevine_rare");

    public static final RegistryEntry<PlacedFeature> PATCH_WILD_RED_GRAPEVINE_COMMON = PlacedFeatures.register(PATCH_WILD_RED_GRAPEVINE_COMMON_KEY.getValue().toString(), ModVegetationConfiguredFeatures.PATCH_WILD_RED_GRAPEVINE, RarityFilterPlacementModifier.of(ModConfigs.WILD_RED_GRAPE_COMMON_SPAWN_CHANCE), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> PATCH_WILD_RED_GRAPEVINE_RARE = PlacedFeatures.register(PATCH_WILD_RED_GRAPEVINE_RARE_KEY.getValue().toString(), ModVegetationConfiguredFeatures.PATCH_WILD_RED_GRAPEVINE, RarityFilterPlacementModifier.of(ModConfigs.WILD_RED_GRAPE_RARE_SPAWN_CHANCE), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    private static RegistryKey<PlacedFeature> registerKey(String id) {
        return RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(VineyardMod.MOD_ID, id));
    }
}
