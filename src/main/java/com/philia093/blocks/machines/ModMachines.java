package com.philia093.blocks.machines;

import com.philia093.Principia;
import com.philia093.client.render.machines.MaceratorScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModMachines {

    public static final Block MACERATOR = new MaceratorBlock(
            FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()
    );

    public static final Item MACERATOR_ITEM = new BlockItem(MACERATOR, new Item.Settings());

    public static final ScreenHandlerType<MaceratorScreenHandler> MACERATOR_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(
                    new Identifier(Principia.MOD_ID, "macerator"),
                    MaceratorScreenHandler::new
            );

    public static final BlockEntityType<MaceratorBlockEntity> MACERATOR_BLOCK_ENTITY =
            FabricBlockEntityTypeBuilder.create(MaceratorBlockEntity::new, MACERATOR).build();

    public static void registerMachines() {
        Registry.register(Registries.BLOCK, new Identifier(Principia.MOD_ID, "macerator"), MACERATOR);

        Registry.register(Registries.ITEM, new Identifier(Principia.MOD_ID, "macerator"), MACERATOR_ITEM);

        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Principia.MOD_ID, "macerator"), MACERATOR_BLOCK_ENTITY);

        Principia.LOGGER.info("Registering machines");
    }
}