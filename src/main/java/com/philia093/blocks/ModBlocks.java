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
    // ores
    public static final Block APATITE_ORE = registerBlocks("apatite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block BAUXITE_ORE = registerBlocks("bauxite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block CASSITERITE_ORE = registerBlocks("cassiterite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(6.0f)));
    public static final Block CHALCOPYRITE_ORE = registerBlocks("chalcopyrite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block CHROMITE_ORE = registerBlocks("chromite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).strength(6.0f)));
    public static final Block FLUORITE_ORE = registerBlocks("fluorite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block GALENA_ORE = registerBlocks("galena_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block ILMENITE_ORE = registerBlocks("ilmenite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).strength(6.0f)));
    public static final Block LIGNITE_ORE = registerBlocks("lignite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.0f)));
    public static final Block LIMESTONE_ORE = registerBlocks("limestone_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block MAGNETITE_ORE = registerBlocks("magnetite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block MALACHITE_ORE = registerBlocks("malachite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block MONAZITE_ORE = registerBlocks("monazite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).strength(6.0f)));
    public static final Block PENTLANDITE_ORE = registerBlocks("pentlandite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).strength(3.0f)));
    public static final Block PYROLUSITE_ORE = registerBlocks("pyrolusite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block ROCK_SALT_ORE = registerBlocks("rock_salt_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block SPHALERITE_ORE = registerBlocks("sphalerite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f)));
    public static final Block SULFUR_ORE = registerBlocks("sulfur_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(1.0f)));
    public static final Block VANADINITE_ORE = registerBlocks("vanadinite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(4.0f)));


    public static final Block[] ALL_ORES = {
            APATITE_ORE, BAUXITE_ORE, CASSITERITE_ORE, CHALCOPYRITE_ORE,
            CHROMITE_ORE, FLUORITE_ORE, GALENA_ORE, ILMENITE_ORE,
            LIGNITE_ORE, LIMESTONE_ORE, MAGNETITE_ORE, MALACHITE_ORE,
            MONAZITE_ORE, PENTLANDITE_ORE, PYROLUSITE_ORE, ROCK_SALT_ORE,
            SPHALERITE_ORE, SULFUR_ORE, VANADINITE_ORE
    };

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