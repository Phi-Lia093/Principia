package com.philia093.client.render.machines;

import com.philia093.blocks.machines.ModMachines;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ModMachinesClient {

    public static void registerScreens() {
        ScreenRegistry.register(ModMachines.MACERATOR_SCREEN_HANDLER, MaceratorScreen::new);
    }
}