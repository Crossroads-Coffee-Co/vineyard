package net.mwforrest7.vineyard.world.gen.feature;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.mwforrest7.vineyard.VineyardMod;

/**
 * Generally, this class defines how the configured features generate
 */
public class ModVegetationPlacedFeatures {
    public static final RegistryKey<PlacedFeature> PATCH_WILD_RED_GRAPEVINE_COMMON_KEY = registerKey("patch_wild_red_grapevine_common");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_RED_GRAPEVINE_RARE_KEY = registerKey("patch_wild_red_grapevine_rare");

    public static final PlacedFeature PATCH_WILD_RED_GRAPEVINE_COMMON = register(PATCH_WILD_RED_GRAPEVINE_COMMON_KEY, ModVegetationConfiguredFeatures.PATCH_WILD_RED_GRAPEVINE.withPlacement(RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final PlacedFeature PATCH_WILD_RED_GRAPEVINE_RARE = register(PATCH_WILD_RED_GRAPEVINE_RARE_KEY, ModVegetationConfiguredFeatures.PATCH_WILD_RED_GRAPEVINE.withPlacement(RarityFilterPlacementModifier.of(384), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));

    public static PlacedFeature register(RegistryKey<PlacedFeature> registryKey, PlacedFeature feature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, registryKey, feature);
    }

    private static RegistryKey<PlacedFeature> registerKey(String id) {
        return RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(VineyardMod.MOD_ID, id));
    }

}
