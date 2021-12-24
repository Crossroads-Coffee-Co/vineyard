package net.mwforrest7.vineyard.block.vine;

import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;

/**
 * Abstract class to facilitate grape type polymorphism.
 * VineCanopyBlocks are where the actual grapes will grow
 */
public abstract class VineCanopyBlock extends PlantBlock implements Fertilizable {
    public VineCanopyBlock(Settings settings) {
        super(settings);
    }

    public abstract VineHeadBlock getHeadBlock();

    public abstract AttachedVineHeadBlock getAttachedHeadBlock();
}
