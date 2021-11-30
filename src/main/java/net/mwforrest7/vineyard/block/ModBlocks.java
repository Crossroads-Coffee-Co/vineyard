package net.mwforrest7.vineyard.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.item.ModItemGroup;

public class ModBlocks {
    private static Block registerBlockWithoutBlockItem(String name, Block block){
        return Registry.register(Registry.BLOCK, new Identifier(VineyardMod.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(VineyardMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(VineyardMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(ModItemGroup.VINEYARD)));
    }

    public static void registerModBlocks(){
        System.out.println("Registering ModBlocks for " + VineyardMod.MOD_ID);
    }
}
