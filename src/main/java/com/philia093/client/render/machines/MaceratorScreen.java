package com.philia093.client.render.machines;

import com.philia093.blocks.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MaceratorScreen extends AbstractMachineScreen<MaceratorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("principia", "textures/gui/macerator_gui.png");

    public MaceratorScreen(MaceratorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.progressBarX = 79;
        this.progressBarY = 35;
    }
}