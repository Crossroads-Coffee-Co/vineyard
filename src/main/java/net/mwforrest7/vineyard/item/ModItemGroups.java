package net.mwforrest7.vineyard.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.mwforrest7.vineyard.VineyardMod;

public class ModItemGroups {
    // This allows all the mod-added items to be viewable in its own group within the inventory menu
    public static final ItemGroup VINEYARD = FabricItemGroupBuilder.build(new Identifier(VineyardMod.MOD_ID, "vineyard"),
            () -> new ItemStack(null));
}
