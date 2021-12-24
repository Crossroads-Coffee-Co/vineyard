package net.mwforrest7.vineyard;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.mwforrest7.vineyard.screen.FruitPressScreen;
import net.mwforrest7.vineyard.screen.ModScreenHandlers;

public class VineyardClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Client side registration of the screen handler
        ScreenRegistry.register(ModScreenHandlers.FRUIT_PRESS_SCREEN_HANDLER, FruitPressScreen::new);
    }
}