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
    public static final RegistryKey<PlacedFeature> BIRCH_RED_GRAPEVINE_TREE_KEY = registerKey("birch_red_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> SUPER_BIRCH_RED_GRAPEVINE_TREE_KEY = registerKey("super_birch_red_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> DARK_OAK_RED_GRAPEVINE_TREE_KEY = registerKey("dark_oak_red_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> OAK_GREEN_GRAPEVINE_TREE_KEY = registerKey("oak_green_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> FANCY_OAK_GREEN_GRAPEVINE_TREE_KEY = registerKey("fancy_oak_green_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> BIRCH_GREEN_GRAPEVINE_TREE_KEY = registerKey("birch_green_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> SUPER_BIRCH_GREEN_GRAPEVINE_TREE_KEY = registerKey("super_birch_green_grapevine_tree_key");
    public static final RegistryKey<PlacedFeature> DARK_OAK_GREEN_GRAPEVINE_TREE_KEY = registerKey("dark_oak_green_grapevine_tree_key");

    public static final RegistryEntry<PlacedFeature> OAK_RED_GRAPEVINE_TREE = PlacedFeatures.register(OAK_RED_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.OAK_RED_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
    public static final RegistryEntry<PlacedFeature> FANCY_OAK_RED_GRAPEVINE_TREE = PlacedFeatures.register(FANCY_OAK_RED_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.FANCY_OAK_RED_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
    public static final RegistryEntry<PlacedFeature> BIRCH_RED_GRAPEVINE_TREE = PlacedFeatures.register(BIRCH_RED_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.BIRCH_RED_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    public static final RegistryEntry<PlacedFeature> SUPER_BIRCH_RED_GRAPEVINE_TREE = PlacedFeatures.register(SUPER_BIRCH_RED_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.SUPER_BIRCH_RED_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    public static final RegistryEntry<PlacedFeature> DARK_OAK_RED_GRAPEVINE_TREE = PlacedFeatures.register(DARK_OAK_RED_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.DARK_OAK_RED_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.DARK_OAK_SAPLING));
    public static final RegistryEntry<PlacedFeature> OAK_GREEN_GRAPEVINE_TREE = PlacedFeatures.register(OAK_GREEN_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.OAK_GREEN_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
    public static final RegistryEntry<PlacedFeature> FANCY_OAK_GREEN_GRAPEVINE_TREE = PlacedFeatures.register(FANCY_OAK_GREEN_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.FANCY_OAK_GREEN_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
    public static final RegistryEntry<PlacedFeature> BIRCH_GREEN_GRAPEVINE_TREE = PlacedFeatures.register(BIRCH_GREEN_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.BIRCH_GREEN_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    public static final RegistryEntry<PlacedFeature> SUPER_BIRCH_GREEN_GRAPEVINE_TREE = PlacedFeatures.register(SUPER_BIRCH_GREEN_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.SUPER_BIRCH_GREEN_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    public static final RegistryEntry<PlacedFeature> DARK_OAK_GREEN_GRAPEVINE_TREE = PlacedFeatures.register(DARK_OAK_GREEN_GRAPEVINE_TREE_KEY.getValue().toString(), ModTreeConfiguredFeatures.DARK_OAK_GREEN_GRAPEVINE_TREE, PlacedFeatures.wouldSurvive(Blocks.DARK_OAK_SAPLING));

    private static RegistryKey<PlacedFeature> registerKey(String id) {
        return RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(VineyardMod.MOD_ID, id));
    }
}
