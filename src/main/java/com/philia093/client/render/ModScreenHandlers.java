package com.philia093.client.render;

import com.philia093.Principia;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    @Deprecated
    public static final ScreenHandlerType<MaceratorScreenHandler> MACERATOR_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(
                    new Identifier(Principia.MOD_ID, "macerator"),
                    (syncId, playerInventory) -> new MaceratorScreenHandler(syncId, playerInventory)
            );

    public static void registerModScreenHandlers(){}
}
