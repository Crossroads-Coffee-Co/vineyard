package net.mwforrest7.vineyard.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.block.custom.*;
import net.mwforrest7.vineyard.enums.VineType;
import net.mwforrest7.vineyard.item.ModItemGroup;
import net.mwforrest7.vineyard.item.ModItems;

public class ModBlocks {

    public static final Block ATTACHED_RED_GRAPEVINE_TRUNK = registerBlockWithoutBlockItem("attached_red_grapevine_trunk", new AttachedVineTrunkBlock(VineType.RED_GRAPE.toString(), FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.WOOD)));
    public static final Block RED_GRAPEVINE_TRUNK = registerBlockWithoutBlockItem("red_grapevine_trunk", new VineTrunkBlock(VineType.RED_GRAPE.toString(), FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.WOOD)));

    // TODO: review details, add restrictions, add loot table
    public static final Block RED_GRAPE_CANOPY = registerBlockWithoutBlockItem("red_grape_canopy", new RedGrapeBlock(FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));

    public static final Block ATTACHED_RED_GRAPE_HEAD = registerBlockWithoutBlockItem("attached_red_grape_head", new AttachedVineHeadBlock((VineCanopyBlock)RED_GRAPE_CANOPY, FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));
    public static final Block RED_GRAPE_HEAD = registerBlockWithoutBlockItem("red_grape_head", new VineHeadBlock((VineCanopyBlock)RED_GRAPE_CANOPY, (AttachedVineTrunkBlock) ATTACHED_RED_GRAPEVINE_TRUNK, FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));

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