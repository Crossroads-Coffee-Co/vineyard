package net.mwforrest7.vineyard;

import net.fabricmc.api.ModInitializer;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.block.entity.ModBlockEntities;
import net.mwforrest7.vineyard.config.ModConfigs;
import net.mwforrest7.vineyard.item.ModItems;
import net.mwforrest7.vineyard.recipe.ModRecipes;
import net.mwforrest7.vineyard.registries.ModRegistries;
import net.mwforrest7.vineyard.util.ModRenderHelper;
import net.mwforrest7.vineyard.world.gen.ModWorldGen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VineyardMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "vineyard";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModConfigs.registerConfigs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerAllEntities();
		ModRegistries.registerModFuels();
		ModRecipes.register();
		ModWorldGen.generateModWorldGen();

		LOGGER.info("Hello from Vineyard mod!");
	}
}
