package com.philia093.principia.blocks.machines;

import com.philia093.principia.registry.ModMachines;
import com.philia093.principia.registry.ModRecipeTypes;
import com.philia093.principia.client.render.machines.MaceratorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class MaceratorBlockEntity extends AbstractMachineBlockEntity {
    public MaceratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.MACERATOR_BLOCK_ENTITY, pos, state, 2, 1);
    }

    @Override
    public net.minecraft.recipe.RecipeType<?> getRecipeType() {
        return ModRecipeTypes.MACHINE;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new MaceratorScreenHandler(syncId, inv, this, this.getPropertyDelegate());
    }
}