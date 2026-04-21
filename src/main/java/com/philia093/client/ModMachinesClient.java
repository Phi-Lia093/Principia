package com.philia093.client;

import com.philia093.blocks.machines.ModMachines;
import com.philia093.client.render.MaceratorScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ModMachinesClient {

    public static void registerScreens() {
        // 注册 Macerator 的屏幕
        ScreenRegistry.register(ModMachines.MACERATOR_SCREEN_HANDLER, MaceratorScreen::new);
    }
}