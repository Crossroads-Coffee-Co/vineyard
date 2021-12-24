package net.mwforrest7.vineyard.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.mwforrest7.vineyard.world.gen.feature.ModVegetationPlacedFeatures;

/**
 * Generally, this class defines where things generate
 */
public class ModWorldGen {
    public static void generateModWorldGen() {
        System.out.println("Generating mod world features");
        generateVegetationPlacedFeatures();
    }

    private static void generateVegetationPlacedFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.MUSHROOM, Biome.Category.PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_WILD_RED_GRAPEVINE_COMMON_KEY);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.EXTREME_HILLS, Biome.Category.TAIGA), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_WILD_RED_GRAPEVINE_RARE_KEY);
    }
}
