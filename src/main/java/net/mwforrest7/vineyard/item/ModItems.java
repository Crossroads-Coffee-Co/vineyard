package net.mwforrest7.vineyard.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.block.ModBlocks;

public class ModItems {

    // Initialization of items
    public static final Item RED_GRAPE = registerItem("red_grape",
            new Item(new FabricItemSettings()
                    .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).build())
                    .group(ModItemGroups.VINEYARD)));

    public static final Item RED_GRAPE_BUNCH = registerItem("red_grape_bunch",
            new Item(new FabricItemSettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build())
                    .group(ModItemGroups.VINEYARD)));

    public static final Item RED_GRAPE_SEEDS = registerItem("red_grape_seeds",
            new AliasedBlockItem(ModBlocks.RED_GRAPEVINE_TRUNK,
                    new FabricItemSettings().group(ModItemGroups.VINEYARD)));

    public static final Item COPPER_SPRING = registerItem("copper_spring",
            new Item(new FabricItemSettings()
                    .group(ModItemGroups.VINEYARD)));

    // Registers items
    private static Item registerItem(String name, Item item){
        System.out.println("Registering " + VineyardMod.MOD_ID + ":" + name);
        return Registry.register(Registry.ITEM, new Identifier(VineyardMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        System.out.println("Registering mod items for " + VineyardMod.MOD_ID);
    }
}
