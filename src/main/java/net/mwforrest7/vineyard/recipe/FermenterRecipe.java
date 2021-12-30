package net.mwforrest7.vineyard.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.mwforrest7.vineyard.block.entity.FermenterProperties;

public class FermenterRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;

    public FermenterRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    /**
     * Provides the recipe matching logic - this is what would
     * become the most custom component of this record class.
     *
     * @param inventory BlockEntity inventory
     * @param world world
     * @return true if match found, else false
     */
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        // TLDR: If recipe ingredient 1 matches slot 1 and recipe ingredient 2 matches slot 2
        // or vice-versa, then we have a match.
        return (recipeItems.get(0).test(inventory.getStack(FermenterProperties.InventorySlots.INGREDIENT_SLOT_1.toInt()))
                && recipeItems.get(1).test(inventory.getStack(FermenterProperties.InventorySlots.INGREDIENT_SLOT_2.toInt())))
                || (recipeItems.get(0).test(inventory.getStack(FermenterProperties.InventorySlots.INGREDIENT_SLOT_2.toInt()))
                && recipeItems.get(1).test(inventory.getStack(FermenterProperties.InventorySlots.INGREDIENT_SLOT_1.toInt())));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FermenterRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fermenter";
    }

    /**
     * Deserializes the JSON recipes
     */
    public static class Serializer implements RecipeSerializer<FermenterRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "fermenter";

        /**
         * Reads the JSON and converts to a FermenterRecipe
         *
         * @param id id
         * @param json the recipe data from the recipe json file
         * @return the FermenterRecipe java object representation of the json
         */
        @Override
        public FermenterRecipe read(Identifier id, JsonObject json) {
            // Get the output item from the JSON recipe
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            // Get the ingredients from the JSON recipe
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");

            // Build a list of ingredients, populated from the JsonArray of ingredients
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(FermenterProperties.NUM_OF_INGREDIENT_SLOTS, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            // Return the deserialized recipe
            return new FermenterRecipe(id, output, inputs);
        }

        /**
         * Note: not sure when this gets used vs the above read method
         *
         * Reads recipe data off a PacketByteBuf (sounds networking related)
         * and builds a FermenterRecipe object from it
         *
         * @param id id
         * @param buf buffered byte recipe data
         * @return the FermenterRecipe representation of the recipe
         */
        @Override
        public FermenterRecipe read(Identifier id, PacketByteBuf buf) {
            // Get the ingredients/inputs for the recipe from the buffer
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            // Get the recipe output from the buffer
            ItemStack output = buf.readItemStack();

            // Return recipe
            return new FermenterRecipe(id, output, inputs);
        }

        /**
         * Note: not sure when or where this is used
         *
         * Serializes a FermenterRecipe object into buffered packet data
         *
         * @param buf a data buffer
         * @param recipe the FermenterRecipe representation of the recipe
         */
        @Override
        public void write(PacketByteBuf buf, FermenterRecipe recipe) {
            // Sets the buffer size
            buf.writeInt(recipe.getIngredients().size());

            // Writes the recipe ingredients to the buffer
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            // Writes the recipe output to the buffer
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
