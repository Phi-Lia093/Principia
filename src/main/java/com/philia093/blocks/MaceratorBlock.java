package com.philia093.blocks;

import com.philia093.blocks.machines.AbstractMachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

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
}