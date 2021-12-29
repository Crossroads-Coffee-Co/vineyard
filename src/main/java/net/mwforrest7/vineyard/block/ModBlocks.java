package net.mwforrest7.vineyard.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.block.custom.FermenterBlock;
import net.mwforrest7.vineyard.block.custom.FruitPressBlock;
import net.mwforrest7.vineyard.block.custom.WildRedGrapevineBlock;
import net.mwforrest7.vineyard.block.vine.*;
import net.mwforrest7.vineyard.enums.VineType;
import net.mwforrest7.vineyard.item.ModItemGroups;

public class ModBlocks {

    // Initialization of blocks
    public static final Block ATTACHED_RED_GRAPEVINE_TRUNK = registerBlockWithoutBlockItem("attached_red_grapevine_trunk", new AttachedVineTrunkBlock(VineType.RED_GRAPE.toString(), FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.WOOD)));
    public static final Block RED_GRAPEVINE_TRUNK = registerBlockWithoutBlockItem("red_grapevine_trunk", new VineTrunkBlock(VineType.RED_GRAPE.toString(), FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.WOOD)));
    public static final Block RED_GRAPE_CANOPY = registerBlockWithoutBlockItem("red_grape_canopy", new RedGrapeBlock(FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));
    public static final Block ATTACHED_RED_GRAPE_HEAD = registerBlockWithoutBlockItem("attached_red_grape_head", new AttachedVineHeadBlock((VineCanopyBlock)RED_GRAPE_CANOPY, FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));
    public static final Block RED_GRAPE_HEAD = registerBlockWithoutBlockItem("red_grape_head", new VineHeadBlock((VineCanopyBlock)RED_GRAPE_CANOPY, (AttachedVineTrunkBlock) ATTACHED_RED_GRAPEVINE_TRUNK, FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));
    public static final Block WILD_RED_GRAPEVINE = registerBlockWithoutBlockItem("wild_red_grapevine", new WildRedGrapevineBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).strength(1.0f).nonOpaque()));
    public static final Block FRUIT_PRESS = registerBlock("fruit_press", new FruitPressBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(2.0f)));
    public static final Block FERMENTER = registerBlock("fermenter", new FermenterBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(2.0f)));

    // Registers a block but not an item (useful for blocks that shouldn't be craftable but may spawn in the world)
    private static Block registerBlockWithoutBlockItem(String name, Block block){
        return Registry.register(Registry.BLOCK, new Identifier(VineyardMod.MOD_ID, name), block);
    }

    // Registers a block and a corresponding item
    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(VineyardMod.MOD_ID, name), block);
    }

    // Registers a block item
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registry.ITEM, new Identifier(VineyardMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(ModItemGroups.VINEYARD)));
    }

    public static void registerModBlocks(){
        System.out.println("Registering ModBlocks for " + VineyardMod.MOD_ID);
    }
}