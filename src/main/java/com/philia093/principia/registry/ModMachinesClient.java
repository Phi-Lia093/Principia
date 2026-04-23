package com.philia093.principia.registry;

import com.philia093.principia.client.render.machines.MaceratorScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ModMachinesClient {

    public static void registerScreens() {
        ScreenRegistry.register(ModMachines.MACERATOR_SCREEN_HANDLER, MaceratorScreen::new);
    }
}