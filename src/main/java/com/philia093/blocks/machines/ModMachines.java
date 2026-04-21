package com.philia093.blocks.machines;

import com.philia093.Principia;
import com.philia093.blocks.MaceratorBlock;
import com.philia093.blocks.MaceratorBlockEntity;
import com.philia093.client.render.MaceratorScreenHandler;
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
    // 机器方块
    public static final Block MACERATOR = registerMachine("macerator",
            new MaceratorBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()),
            MaceratorBlockEntity::new);

    // ✅ 暴露物品实例供 ItemGroup 使用
    public static final Item MACERATOR_ITEM = MACERATOR.asItem();

    // ScreenHandler 注册
    public static final ScreenHandlerType<MaceratorScreenHandler> MACERATOR_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(
                    new Identifier(Principia.MOD_ID, "macerator"),
                    (syncId, inventory) -> new MaceratorScreenHandler(syncId, inventory)
            );

    // BlockEntity 类型
    public static BlockEntityType<MaceratorBlockEntity> MACERATOR_BLOCK_ENTITY;

    private static Block registerMachine(String name, Block block,
                                         FabricBlockEntityTypeBuilder.Factory<? extends AbstractMachineBlockEntity> entityFactory) {
        // 注册方块
        Registry.register(Registries.BLOCK, new Identifier(Principia.MOD_ID, name), block);

        // ✅ 注册物品 (BlockItem)
        Registry.register(Registries.ITEM, new Identifier(Principia.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));

        // 注册方块实体
        BlockEntityType<?> entityType = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Principia.MOD_ID, name),
                FabricBlockEntityTypeBuilder.create(entityFactory, block).build()
        );

        // 存储特定类型的 BlockEntity
        if (block instanceof MaceratorBlock) {
            MACERATOR_BLOCK_ENTITY = (BlockEntityType<MaceratorBlockEntity>) entityType;
        }

        return block;
    }

    public static void registerMachines() {
        Principia.LOGGER.info("Registering machines");
    }
}