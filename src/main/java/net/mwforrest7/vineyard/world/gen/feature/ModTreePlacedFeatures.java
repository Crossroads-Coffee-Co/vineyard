package net.mwforrest7.vineyard.world.gen.feature;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.mwforrest7.vineyard.VineyardMod;

public class ModTreePlacedFeatures {
    public static final RegistryKey<PlacedFeature> OAK_RED_GRAPEVINE_TREE_KEY = registerKey("oak_red_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> FANCY_OAK_RED_GRAPEVINE_TREE_KEY = registerKey("fancy_oak_red_grapevine_tree_key");
    public static final RegistryEntry<PlacedFeature> OAK_RED_GRAPEVINE_TREE = PlacedFeatures.register(OAK_RED_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.OAK_RED_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
    public static final RegistryEntry<PlacedFeature> FANCY_OAK_RED_GRAPEVINE_TREE = PlacedFeatures.register(FANCY_OAK_RED_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.FANCY_OAK_RED_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));

    private static RegistryKey<PlacedFeature> registerKey(String id) {
        return RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(VineyardMod.MOD_ID, id));
    }
}
