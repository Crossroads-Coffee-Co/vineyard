package net.mwforrest7.vineyard.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.mwforrest7.vineyard.VineyardMod;

/**
 * Registers screen handlers
 */
public class ModScreenHandlers {
    public static ScreenHandlerType<FruitPressScreenHandler> FRUIT_PRESS_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(VineyardMod.MOD_ID, "fruit_press"), FruitPressScreenHandler::new);

    public static ScreenHandlerType<FermenterScreenHandler> FERMENTER_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(VineyardMod.MOD_ID, "fermenter"), FermenterScreenHandler::new);

    public static ScreenHandlerType<WineCaskScreenHandler> WINE_CASK_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(VineyardMod.MOD_ID, "wine_cask"), WineCaskScreenHandler::new);
}
