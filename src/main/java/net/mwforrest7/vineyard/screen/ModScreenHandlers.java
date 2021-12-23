package net.mwforrest7.vineyard.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.mwforrest7.vineyard.VineyardMod;

/**
 * Registers screen handlers
 */
public class ModScreenHandlers {
    public static ScreenHandlerType<GrapePressScreenHandler> GRAPE_PRESS_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(VineyardMod.MOD_ID, "grape_press"), GrapePressScreenHandler::new);
}
