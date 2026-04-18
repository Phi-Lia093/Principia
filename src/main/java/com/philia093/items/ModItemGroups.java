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
    public static final ItemGroup ORES = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Principia.MOD_ID, "ores"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.ores"))
                    .icon(() -> new ItemStack(ModBlocks.APATITE_ORE))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.APATITE_ORE);
                        entries.add(ModBlocks.BAUXITE_ORE);
                        entries.add(ModBlocks.CASSITERITE_ORE);
                        entries.add(ModBlocks.CHALCOPYRITE_ORE);
                        entries.add(ModBlocks.CHROMITE_ORE);
                        entries.add(ModBlocks.FLUORITE_ORE);
                        entries.add(ModBlocks.GALENA_ORE);
                        entries.add(ModBlocks.ILMENITE_ORE);
                        entries.add(ModBlocks.LIGNITE_ORE);
                        entries.add(ModBlocks.LIMESTONE_ORE);
                        entries.add(ModBlocks.MAGNETITE_ORE);
                        entries.add(ModBlocks.MALACHITE_ORE);
                        entries.add(ModBlocks.MONAZITE_ORE);
                        entries.add(ModBlocks.PENTLANDITE_ORE);
                        entries.add(ModBlocks.PYROLUSITE_ORE);
                        entries.add(ModBlocks.ROCK_SALT_ORE);
                        entries.add(ModBlocks.SPHALERITE_ORE);
                        entries.add(ModBlocks.SULFUR_ORE);
                        entries.add(ModBlocks.VANADINITE_ORE);
                    }).build());

    public static void registerModItemGroups(){
        Principia.LOGGER.info("registering itemgroups");
    }
}
