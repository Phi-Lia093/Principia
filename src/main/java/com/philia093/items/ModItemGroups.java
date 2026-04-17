package com.philia093.items;

import com.philia093.Principia;
import com.philia093.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MATERIALS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Principia.MOD_ID, "materials"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.materials"))
                    .icon(() -> new ItemStack(ModBlocks.ZINC_ORE))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.ZINC_ORE);
                    }).build());

    public static void registerModItemGroups(){
        Principia.LOGGER.info("registering itemgroups");
    }
}
