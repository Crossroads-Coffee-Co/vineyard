package net.mwforrest7.vineyard.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.mwforrest7.vineyard.config.ModConfigs;
import net.mwforrest7.vineyard.world.gen.feature.ModVegetationPlacedFeatures;

import java.util.ArrayList;

/**
 * Generally, this class defines where things generate
 */
public class ModWorldGen {
    public static void generateModWorldGen() {
        System.out.println("Generating mod world features");
        generateVegetationPlacedFeatures();
    }

    private static void generateVegetationPlacedFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.categories(parseAndDeserializeBiomeCategoriesFromConfig(ModConfigs.WILD_RED_GRAPE_COMMON_BIOME_CATEGORIES)), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_WILD_RED_GRAPEVINE_COMMON_KEY);
        BiomeModifications.addFeature(BiomeSelectors.categories(parseAndDeserializeBiomeCategoriesFromConfig(ModConfigs.WILD_RED_GRAPE_RARE_BIOME_CATEGORIES)), GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_WILD_RED_GRAPEVINE_RARE_KEY);
    }

    /**
     * Given a delimited list of biome categories from the config, this function
     * will parse out the categories and deserialize them into an array of
     * Biome.Category objects which is used by the Fabric BiomeModifications API
     *
     * @param categories String delimited list of biome categories
     * @return Biome.Category[]
     */
    private static Biome.Category[] parseAndDeserializeBiomeCategoriesFromConfig(String categories){
        ArrayList<Biome.Category> biome_categories = new ArrayList<>();
        for(String category : categories.split(ModConfigs.BIOME_CATEGORY_DELIMITER)){
            biome_categories.add(Biome.Category.byName(category.toLowerCase()));
        }
        return biome_categories.toArray(Biome.Category[]::new);
    }
}
