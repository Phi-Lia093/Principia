package com.philia093.principia.client.render.machines;

import com.philia093.principia.blocks.machines.AbstractMachineScreenHandler;
import com.philia093.principia.registry.ModMachines;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PropertyDelegate;

public class MaceratorScreenHandler extends AbstractMachineScreenHandler {
    public MaceratorScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModMachines.MACERATOR_SCREEN_HANDLER, syncId, playerInventory, 2, 1);
        setupMachineSlots(44, 35, 18, 0);
        addPlayerSlots(playerInventory, 84, 142);
    }

    public MaceratorScreenHandler(int syncId, PlayerInventory playerInventory,
                                  Inventory inventory, PropertyDelegate delegate) {
        super(ModMachines.MACERATOR_SCREEN_HANDLER, syncId, playerInventory,
                inventory, delegate, 2, 1);
        setupMachineSlots(44, 35, 18, 0);
        addPlayerSlots(playerInventory, 84, 142);
    }
}