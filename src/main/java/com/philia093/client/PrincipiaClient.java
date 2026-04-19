package com.philia093.client;

import com.philia093.client.render.CrushedOreItemRenderer;
import com.philia093.client.render.MaceratorScreen;
import com.philia093.client.render.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class PrincipiaClient implements ClientModInitializer {
    @Override
    @Deprecated
    public void onInitializeClient() {
        CrushedOreItemRenderer.register();
        ScreenRegistry.register(
                ModScreenHandlers.MACERATOR_SCREEN_HANDLER,
                MaceratorScreen::new
        );
    }
}
