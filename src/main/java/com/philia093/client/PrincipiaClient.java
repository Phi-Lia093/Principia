package com.philia093.client;

import com.philia093.client.render.CrushedOreItemRenderer;
import net.fabricmc.api.ClientModInitializer;

public class PrincipiaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CrushedOreItemRenderer.register();

        // 注册机器的屏幕
        ModMachinesClient.registerScreens();
    }
}