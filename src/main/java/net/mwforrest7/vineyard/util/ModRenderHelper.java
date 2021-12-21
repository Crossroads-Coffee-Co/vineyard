package net.mwforrest7.vineyard.util;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.mwforrest7.vineyard.block.ModBlocks;

public class ModRenderHelper {
    public static void setRenderLayers(){
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_GRAPE_HEAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ATTACHED_RED_GRAPEVINE_TRUNK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_GRAPEVINE_TRUNK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ATTACHED_RED_GRAPE_HEAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_GRAPE_CANOPY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILD_RED_GRAPEVINE, RenderLayer.getCutout());
    }
}
