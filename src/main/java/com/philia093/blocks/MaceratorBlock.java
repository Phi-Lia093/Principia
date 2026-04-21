package com.philia093.blocks;

import com.philia093.blocks.machines.AbstractMachineBlock;
import com.philia093.blocks.machines.AbstractMachineBlockEntity;
import com.philia093.blocks.machines.ModMachines;
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

    // ✅ 重写 getTicker 方法，提供具体的 BlockEntityType
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }
        // 使用具体的 MACERATOR_BLOCK_ENTITY 和 AbstractMachineBlockEntity.tick 方法
        return checkType(type, ModMachines.MACERATOR_BLOCK_ENTITY,
                AbstractMachineBlockEntity::tick);
    }
}