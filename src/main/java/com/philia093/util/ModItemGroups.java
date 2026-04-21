package com.philia093.util;

import com.philia093.Principia;
import com.philia093.blocks.ModBlocks;
import com.philia093.blocks.machines.ModMachines;
import com.philia093.items.ModItems;
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

    public static final ItemGroup TOOLS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Principia.MOD_ID, "tools"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.tools"))
                    .icon(() -> new ItemStack(ModItems.DRY_BATTERY))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.DRY_BATTERY);
                    }).build());

    public static final ItemGroup MACHINES = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Principia.MOD_ID, "machines"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.machines"))
                    .icon(() -> new ItemStack(ModMachines.MACERATOR_ITEM))
                    .entries((displayContext, entries) -> {
                        entries.add(ModMachines.MACERATOR_ITEM);
                    }).build());



    public static void registerModItemGroups(){
        Principia.LOGGER.info("registering itemgroups");
    }
}
