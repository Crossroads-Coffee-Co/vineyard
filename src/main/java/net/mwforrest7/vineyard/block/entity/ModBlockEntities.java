package net.mwforrest7.vineyard.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.block.ModBlocks;

/**
 * Registers BlockEntity objects which also associates a BlockEntity to a corresponding Block
 */
public class ModBlockEntities {
    public static BlockEntityType<GrapePressEntity> GRAPE_PRESS;

    public static void registerAllEntities() {
        GRAPE_PRESS = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(VineyardMod.MOD_ID, "grape_press"), FabricBlockEntityTypeBuilder.create(GrapePressEntity::new, ModBlocks.GRAPE_PRESS).build(null));
    }
}
