package net.mwforrest7.vineyard.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.mwforrest7.vineyard.mixin.TreeDecoratorTypeMixin;
import net.mwforrest7.vineyard.world.gen.feature.ModVegetationPlacedFeatures;
import net.mwforrest7.vineyard.world.gen.treedecorator.TrunkGreenGrapevineTreeDecorator;
import net.mwforrest7.vineyard.world.gen.treedecorator.TrunkRedGrapevineTreeDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Generally, this class defines where things generate
 */
public class ModWorldGen {
    public static final TreeDecoratorType<TrunkRedGrapevineTreeDecorator> TRUNK_RED_GRAPEVINE_TREE_DECORATOR = TreeDecoratorTypeMixin
            .callRegister("trunk_red_grapevine_tree_decorator", TrunkRedGrapevineTreeDecorator.CODEC);

    public static final TreeDecoratorType<TrunkGreenGrapevineTreeDecorator> TRUNK_GREEN_GRAPEVINE_TREE_DECORATOR = TreeDecoratorTypeMixin
            .callRegister("trunk_green_grapevine_tree_decorator", TrunkGreenGrapevineTreeDecorator.CODEC);

    private static final ArrayList<RegistryKey<Biome>> RED_GRAPE_OAK_COMMON_BIOME_KEYS = new ArrayList<>(
            List.of(BiomeKeys.FLOWER_FOREST, BiomeKeys.FOREST, BiomeKeys.DARK_FOREST));

    private static final ArrayList<RegistryKey<Biome>> RED_GRAPE_BIRCH_COMMON_BIOME_KEYS = new ArrayList<>(
            List.of(BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.FLOWER_FOREST));

    private static final ArrayList<RegistryKey<Biome>> RED_GRAPE_DARK_OAK_COMMON_BIOME_KEYS = new ArrayList<>(
            List.of(BiomeKeys.DARK_FOREST));

    /*
    private static final ArrayList<RegistryKey<Biome>> RED_GRAPE_RARE_BIOME_KEYS = new ArrayList<>(
            List.of(BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA,
                    BiomeKeys.TAIGA, BiomeKeys.WINDSWEPT_FOREST));
     */

    public static void generateModWorldGen() {
        System.out.println("Generating mod world features");
        generateVegetationPlacedFeatures();
    }

    private static void generateVegetationPlacedFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RED_GRAPE_OAK_COMMON_BIOME_KEYS), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.TREE_OAK_GRAPEVINE_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RED_GRAPE_BIRCH_COMMON_BIOME_KEYS), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.TREE_BIRCH_GRAPEVINE_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RED_GRAPE_DARK_OAK_COMMON_BIOME_KEYS), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.TREE_DARK_OAK_GRAPEVINE_KEY);
    }

}
