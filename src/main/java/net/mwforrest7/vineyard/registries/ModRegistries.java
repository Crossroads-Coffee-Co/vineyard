package net.mwforrest7.vineyard.registries;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.item.ModItems;

public class ModRegistries {
    public static void registerModFuels() {
        System.out.println("Now registering fuels for " + VineyardMod.MOD_ID);
        //FuelRegistry registry = FuelRegistry.INSTANCE;
        //registry.add(ModItems.COPPER_SPRING, 300); // x / 20
    }
}
