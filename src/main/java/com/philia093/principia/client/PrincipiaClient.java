package com.philia093.principia.client;

import com.philia093.principia.client.render.CrushedOreItemRenderer;
import com.philia093.principia.registry.ModMachinesClient;
import net.fabricmc.api.ClientModInitializer;

public class PrincipiaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CrushedOreItemRenderer.register();

        ModMachinesClient.registerScreens();
    }
}