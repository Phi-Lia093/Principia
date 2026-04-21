package com.philia093.blocks.machines;

import com.philia093.Principia;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeTypes {
    public static final RecipeType<MachineRecipe> MACHINE = register("machine");

    private static <T extends Recipe<?>> RecipeType<T> register(String id) {
        return Registry.register(Registries.RECIPE_TYPE,
                new Identifier(Principia.MOD_ID, id),
                new RecipeType<T>() {
                    @Override
                    public String toString() {
                        return new Identifier(Principia.MOD_ID, id).toString();
                    }
                });
    }

    public static void registerRecipeTypes() {
        Principia.LOGGER.info("Registering recipe types");
    }
}