package net.mwforrest7.vineyard.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.mwforrest7.vineyard.config.ModConfigs;
import net.mwforrest7.vineyard.world.gen.feature.ModVegetationPlacedFeatures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Generally, this class defines where things generate
 */
public class ModWorldGen {

    private static final ArrayList<RegistryKey<Biome>> RED_GRAPE_COMMON_BIOME_KEYS = new ArrayList<>(
            List.of(BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST,
                    BiomeKeys.FLOWER_FOREST, BiomeKeys.FOREST));

    private static final ArrayList<RegistryKey<Biome>> RED_GRAPE_RARE_BIOME_KEYS = new ArrayList<>(
            List.of(BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA,
                    BiomeKeys.TAIGA, BiomeKeys.WINDSWEPT_FOREST));

    public static void generateModWorldGen() {
        System.out.println("Generating mod world features");
        generateVegetationPlacedFeatures();
    }

    private static void generateVegetationPlacedFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RED_GRAPE_COMMON_BIOME_KEYS), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_WILD_RED_GRAPEVINE_COMMON_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RED_GRAPE_RARE_BIOME_KEYS), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_WILD_RED_GRAPEVINE_RARE_KEY);
    }
}
