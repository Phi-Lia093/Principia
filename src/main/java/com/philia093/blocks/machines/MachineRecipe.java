package com.philia093.blocks.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.world.World;

public interface MachineRecipe extends Recipe<AbstractMachineBlockEntity> {
    @Override
    default boolean matches(AbstractMachineBlockEntity inventory, World world) {
        // 基础匹配逻辑，子类可重写
        return true;
    }

    @Override
    default ItemStack craft(AbstractMachineBlockEntity inventory, net.minecraft.registry.DynamicRegistryManager registryManager) {
        return getResult();
    }

    @Override
    default boolean fits(int width, int height) {
        return true;
    }

    ItemStack getResult();
}