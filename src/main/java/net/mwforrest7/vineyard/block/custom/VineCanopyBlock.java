package net.mwforrest7.vineyard.block.custom;

import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;

public abstract class VineCanopyBlock extends PlantBlock implements Fertilizable {
    public VineCanopyBlock(Settings settings) {
        super(settings);
    }

    public abstract VineHeadBlock getHeadBlock();

    public abstract AttachedVineHeadBlock getAttachedHeadBlock();
}
