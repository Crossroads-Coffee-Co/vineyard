package net.mwforrest7.vineyard.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;

/**
 * Registers Recipe Serializers as well as Recipe Types
 */
public class ModRecipes {
    public static void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(VineyardMod.MOD_ID, GrapePressRecipe.Serializer.ID), GrapePressRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(VineyardMod.MOD_ID, GrapePressRecipe.Type.ID), GrapePressRecipe.Type.INSTANCE);
    }
}
