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
import net.mwforrest7.vineyard.block.entity.FruitPressProperties;

public class FruitPressRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;

    public FruitPressRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
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
        if(recipeItems.get(0).test(inventory.getStack(FruitPressProperties.InventorySlots.GLASS_BOTTLE_INGREDIENT_SLOT.toInt()))) {
            return recipeItems.get(1).test(inventory.getStack(FruitPressProperties.InventorySlots.FRUIT_INGREDIENT_SLOT.toInt()));
        }
        return false;
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

    public static class Type implements RecipeType<FruitPressRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fruit_press";
    }

    /**
     * Deserializes the JSON recipes
     */
    public static class Serializer implements RecipeSerializer<FruitPressRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "fruit_press";

        /**
         * Reads the JSON and converts to a GrapePressRecipe
         *
         * @param id id
         * @param json the recipe data from the recipe json file
         * @return the FruitPressRecipe java object representation of the json
         */
        @Override
        public FruitPressRecipe read(Identifier id, JsonObject json) {
            // Get the output item from the JSON recipe
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            // Get the ingredients from the JSON recipe
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");

            // Build a list of ingredients, populated from the JsonArray of ingredients
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(FruitPressProperties.NUM_OF_INGREDIENT_SLOTS, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            // Return the deserialized recipe
            return new FruitPressRecipe(id, output, inputs);
        }

        /**
         * Note: not sure when this gets used vs the above read method
         *
         * Reads recipe data off a PacketByteBuf (sounds networking related)
         * and builds a GrapePressRecipe object from it
         *
         * @param id id
         * @param buf buffered byte recipe data
         * @return the FruitPressRecipe representation of the recipe
         */
        @Override
        public FruitPressRecipe read(Identifier id, PacketByteBuf buf) {
            // Get the ingredients/inputs for the recipe from the buffer
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            // Get the recipe output from the buffer
            ItemStack output = buf.readItemStack();

            // Return recipe
            return new FruitPressRecipe(id, output, inputs);
        }

        /**
         * Note: not sure when or where this is used
         *
         * Serializes a GrapePressRecipe object into buffered packet data
         *
         * @param buf a data buffer
         * @param recipe the FruitPressRecipe representation of the recipe
         */
        @Override
        public void write(PacketByteBuf buf, FruitPressRecipe recipe) {
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
