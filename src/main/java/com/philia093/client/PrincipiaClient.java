package com.philia093.client;

import com.philia093.client.render.CrushedOreItemRenderer;
import net.fabricmc.api.ClientModInitializer;

public class PrincipiaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CrushedOreItemRenderer.register();

        ModMachinesClient.registerScreens();
    }
}