package com.philia093.blocks;

import com.philia093.blocks.machines.AbstractMachineBlockEntity;
import com.philia093.blocks.machines.ModMachines;
import com.philia093.blocks.machines.ModRecipeTypes;
import com.philia093.client.render.MaceratorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class MaceratorBlockEntity extends AbstractMachineBlockEntity implements NamedScreenHandlerFactory {
    public MaceratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.MACERATOR_BLOCK_ENTITY, pos, state, 2, 1);
    }

    @Override
    public RecipeType<?> getRecipeType() {
        return ModRecipeTypes.MACERATOR_RECIPE_TYPE;
    }

    @Override
    public int getProcessingTime() {
        return 100;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.principia.macerator");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new MaceratorScreenHandler(syncId, inv, this, new ArrayPropertyDelegate(2));
    }
}