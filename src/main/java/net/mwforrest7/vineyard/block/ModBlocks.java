package net.mwforrest7.vineyard.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.block.custom.*;
import net.mwforrest7.vineyard.block.vine.*;
import net.mwforrest7.vineyard.enums.VineType;
import net.mwforrest7.vineyard.item.ModItemGroups;

public class ModBlocks {

    // Initialization of blocks
    public static final Block WILD_GREEN_GRAPEVINE = registerBlock(BlockNames.WILD_GREEN_GRAPEVINE_KEY, new WildGreenGrapevineBlock(AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().ticksRandomly().strength(0.2F).sounds(BlockSoundGroup.VINE)));
    public static final Block WILD_RED_GRAPEVINE = registerBlock(BlockNames.WILD_RED_GRAPEVINE_KEY, new WildRedGrapevineBlock(AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().ticksRandomly().strength(0.2F).sounds(BlockSoundGroup.VINE)));
    public static final Block ATTACHED_RED_GRAPEVINE_TRUNK = registerBlockWithoutBlockItem(BlockNames.ATTACHED_RED_GRAPEVINE_TRUNK_KEY, new AttachedVineTrunkBlock(VineType.RED_GRAPE.toString(), FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.WOOD)));
    public static final Block RED_GRAPEVINE_TRUNK = registerBlockWithoutBlockItem(BlockNames.RED_GRAPEVINE_TRUNK_KEY, new VineTrunkBlock(VineType.RED_GRAPE.toString(), FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.WOOD)));
    public static final Block RED_GRAPE_CANOPY = registerBlockWithoutBlockItem(BlockNames.RED_GRAPE_CANOPY_KEY, new RedGrapeBlock(FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));
    public static final Block ATTACHED_RED_GRAPE_HEAD = registerBlockWithoutBlockItem(BlockNames.ATTACHED_RED_GRAPE_HEAD_KEY, new AttachedVineHeadBlock((VineCanopyBlock)RED_GRAPE_CANOPY, FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));
    public static final Block RED_GRAPE_HEAD = registerBlockWithoutBlockItem(BlockNames.RED_GRAPE_HEAD_KEY, new VineHeadBlock((VineCanopyBlock)RED_GRAPE_CANOPY, (AttachedVineTrunkBlock) ATTACHED_RED_GRAPEVINE_TRUNK, FabricBlockSettings.of(Material.PLANT).strength(1.0f).nonOpaque().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));
    //public static final Block WILD_RED_GRAPEVINE = registerBlockWithoutBlockItem(BlockNames.WILD_RED_GRAPEVINE_KEY, new WildRedGrapevineBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).strength(1.0f).nonOpaque()));
    public static final Block FRUIT_PRESS = registerBlock(BlockNames.FRUIT_PRESS_KEY, new FruitPressBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(2.5f).sounds(BlockSoundGroup.METAL)));
    public static final Block FERMENTER = registerBlock(BlockNames.FERMENTER_KEY, new FermenterBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(2.5f).sounds(BlockSoundGroup.METAL)));
    public static final Block WINE_CASK = registerBlock(BlockNames.WINE_CASK_KEY, new WineCaskBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().strength(2.5f).sounds(BlockSoundGroup.WOOD)));
    public static final Block AGED_FRUITY_RED_WINE_CASK = registerBlock(BlockNames.AGED_FRUITY_RED_WINE_CASK_KEY, new AgedRedWineBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().strength(2.5f).sounds(BlockSoundGroup.WOOD)));

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