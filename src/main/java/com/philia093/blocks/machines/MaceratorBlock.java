package com.philia093.blocks.machines;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MaceratorBlock extends AbstractMachineBlock {
    public MaceratorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MaceratorBlockEntity(pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.principia.macerator");
    }

    @Override
    public int getInputSlotCount() {
        return 2;
    }

    @Override
    public int getOutputSlotCount() {
        return 1;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }
        return checkType(type, ModMachines.MACERATOR_BLOCK_ENTITY,
                AbstractMachineBlockEntity::tick);
    }
}