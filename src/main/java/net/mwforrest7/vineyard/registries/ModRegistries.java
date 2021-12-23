package net.mwforrest7.vineyard.registries;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.mwforrest7.vineyard.VineyardMod;

public class ModRegistries {
    public static void registerModFuels() {
        System.out.println("Now registering fuels for " + VineyardMod.MOD_ID);
        //FuelRegistry registry = FuelRegistry.INSTANCE;

        // Register items here... example:
        //registry.add(ModItems.IRON_WOOL, 300); // x / 20
    }
}
