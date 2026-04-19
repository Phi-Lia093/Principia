package com.philia093.items;

import com.philia093.Principia;
import com.philia093.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup ORES = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Principia.MOD_ID, "ores"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.ores"))
                    .icon(() -> new ItemStack(ModBlocks.APATITE_ORE))
                    .entries((displayContext, entries) -> {
                        for (Block ore : ModBlocks.ALL_ORES) {
                            entries.add(ore);
                        }
                    }).build());

    public static final ItemGroup MATERIALS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Principia.MOD_ID, "materials"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.materials"))
                    .icon(() -> new ItemStack(ModItems.CRUSHED_MAGNETITE))
                    .entries((displayContext, entries) -> {
                        for (Item item : ModItems.ALL_CRUSHED_ORES) {
                            entries.add(item);
                        }
                    }).build());

    public static void registerModItemGroups(){
        Principia.LOGGER.info("registering itemgroups");
    }
}
