package com.philia093.blocks;

import com.philia093.Principia;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ZINC_ORE = registerBlocks("zinc_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f, 3.0f)));

    private static Block registerBlocksWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Principia.MOD_ID, name), block);
    }

    private static Block registerBlocks(String name, Block block) {
        registerBlockItems(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Principia.MOD_ID, name), block);
    }

    private static void registerBlockItems(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(Principia.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        Principia.LOGGER.info("registering blocks");
    }
}