package com.philia093.blocks.machines;

import com.philia093.Principia;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class ModRecipeSerializers {
    public static final RecipeSerializer<MachineRecipe> MACHINE = new RecipeSerializer<>() {
        @Override
        public MachineRecipe read(Identifier id, JsonObject json) {
            List<Ingredient> inputs = new ArrayList<>();
            if (json.has("inputs")) {
                JsonArray inputsArray = JsonHelper.getArray(json, "inputs");
                for (int i = 0; i < inputsArray.size(); i++) {
                    inputs.add(Ingredient.fromJson(inputsArray.get(i)));
                }
            } else if (json.has("input")) {
                inputs.add(Ingredient.fromJson(JsonHelper.getObject(json, "input")));
            }

            List<ItemStack> outputs = new ArrayList<>();
            if (json.has("outputs")) {
                JsonArray outputsArray = JsonHelper.getArray(json, "outputs");
                for (int i = 0; i < outputsArray.size(); i++) {
                    JsonObject outputObj = outputsArray.get(i).getAsJsonObject();
                    String itemId = JsonHelper.getString(outputObj, "item");
                    int count = JsonHelper.getInt(outputObj, "count", 1);
                    ItemStack stack = new ItemStack(Registries.ITEM.get(new Identifier(itemId)), count);
                    outputs.add(stack);
                }
            } else if (json.has("output")) {
                String itemId = JsonHelper.getString(json, "output");
                int count = JsonHelper.getInt(json, "count", 1);
                outputs.add(new ItemStack(Registries.ITEM.get(new Identifier(itemId)), count));
            }

            int processingTime = JsonHelper.getInt(json, "processingTime", 100);

            int powerRequired = JsonHelper.getInt(json, "power", 0);

            return new MachineRecipe(id, inputs, outputs, processingTime, powerRequired);
        }

        @Override
        public MachineRecipe read(Identifier id, PacketByteBuf buf) {
            int inputCount = buf.readInt();
            List<Ingredient> inputs = new ArrayList<>();
            for (int i = 0; i < inputCount; i++) {
                inputs.add(Ingredient.fromPacket(buf));
            }

            int outputCount = buf.readInt();
            List<ItemStack> outputs = new ArrayList<>();
            for (int i = 0; i < outputCount; i++) {
                outputs.add(buf.readItemStack());
            }

            int processingTime = buf.readInt();
            int powerRequired = buf.readInt();

            return new MachineRecipe(id, inputs, outputs, processingTime, powerRequired);
        }

        @Override
        public void write(PacketByteBuf buf, MachineRecipe recipe) {
            buf.writeInt(recipe.getInputs().size());
            for (Ingredient ingredient : recipe.getInputs()) {
                ingredient.write(buf);
            }

            buf.writeInt(recipe.getAllOutputs().size());
            for (ItemStack stack : recipe.getAllOutputs()) {
                buf.writeItemStack(stack);
            }

            buf.writeInt(recipe.getProcessingTime());
            buf.writeInt(recipe.getPowerRequired());
        }
    };

    public static void registerSerializers() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                new Identifier(Principia.MOD_ID, "machine"), MACHINE);
        Principia.LOGGER.info("Registering recipe serializers");
    }
}