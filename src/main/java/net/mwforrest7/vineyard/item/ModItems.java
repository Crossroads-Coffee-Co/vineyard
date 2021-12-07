package net.mwforrest7.vineyard.item;

import net.minecraft.block.Blocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.block.ModBlocks;

public class ModItems {

    public static final Item RED_GRAPE_SEEDS = registerItem("red_grape_seeds", new AliasedBlockItem(ModBlocks.RED_GRAPE_HEAD, new Item.Settings().group(ItemGroup.MATERIALS)));

    private static Item registerItem(String name, Item item){
        System.out.println("Registering " + VineyardMod.MOD_ID + ":" + name);
        return Registry.register(Registry.ITEM, new Identifier(VineyardMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        System.out.println("Registering mod items for " + VineyardMod.MOD_ID);
    }
}
