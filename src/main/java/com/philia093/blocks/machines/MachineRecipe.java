package com.philia093.blocks.machines;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class MachineRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final List<Ingredient> inputs;      // 多个输入
    private final List<ItemStack> outputs;      // 多个输出
    private final int processingTime;            // 加工时间
    private final int powerRequired;             // 所需能量（为供电接口预留）

    public MachineRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs,
                         int processingTime, int powerRequired) {
        this.id = id;
        this.inputs = inputs;
        this.outputs = outputs;
        this.processingTime = processingTime;
        this.powerRequired = powerRequired;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        // 检查所有输入槽位是否匹配
        for (int i = 0; i < inputs.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!inputs.get(i).test(stack)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        // 返回第一个输出（用于显示）
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0).copy();
    }

    public List<ItemStack> getAllOutputs() {
        return outputs.stream().map(ItemStack::copy).toList();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0).copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MACHINE;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.MACHINE;
    }

    // Getters
    public List<Ingredient> getInputs() { return inputs; }
    public int getProcessingTime() { return processingTime; }
    public int getPowerRequired() { return powerRequired; }
}