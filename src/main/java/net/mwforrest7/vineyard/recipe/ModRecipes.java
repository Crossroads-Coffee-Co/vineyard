package net.mwforrest7.vineyard.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;

/**
 * Registers Recipe Serializers as well as Recipe Types
 */
public class ModRecipes {
    public static void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(VineyardMod.MOD_ID, FruitPressRecipe.Serializer.ID), FruitPressRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(VineyardMod.MOD_ID, FruitPressRecipe.Type.ID), FruitPressRecipe.Type.INSTANCE);
    }
}
