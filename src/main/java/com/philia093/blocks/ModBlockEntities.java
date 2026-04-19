package com.philia093.blocks;

import com.philia093.Principia;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<MaceratorBlockEntity> MACERATOR_BLOCK_ENTITY =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    new Identifier(Principia.MOD_ID, "macerator"),
                    FabricBlockEntityTypeBuilder.create(
                            (pos, state)->new MaceratorBlockEntity(pos, state),
                            ModBlocks.MACERATOR
                    ).build()
            );

    public static void registerModBlockEntities(){

    }
}
