package net.mwforrest7.vineyard.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;

/**
 * Registers Recipe Serializers as well as Recipe Types
 */
public class ModRecipes {
    public static void register() {
        // Fruit press
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(VineyardMod.MOD_ID, FruitPressRecipe.Serializer.ID), FruitPressRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(VineyardMod.MOD_ID, FruitPressRecipe.Type.ID), FruitPressRecipe.Type.INSTANCE);

        // Fermenter
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(VineyardMod.MOD_ID, FermenterRecipe.Serializer.ID), FermenterRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(VineyardMod.MOD_ID, FermenterRecipe.Type.ID), FermenterRecipe.Type.INSTANCE);

        // Wine cask
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(VineyardMod.MOD_ID, WineCaskRecipe.Serializer.ID), WineCaskRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(VineyardMod.MOD_ID, WineCaskRecipe.Type.ID), WineCaskRecipe.Type.INSTANCE);
    }
}
