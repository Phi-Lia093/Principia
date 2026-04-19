package com.philia093.client.render;

import com.philia093.blocks.MaceratorBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class MaceratorScreenHandler extends ScreenHandler {
    public MaceratorBlockEntity blockEntity;

    private static final Inventory EMPTY_INVENTORY = new SimpleInventory(3);

    public MaceratorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public MaceratorScreenHandler(int syncId, PlayerInventory playerInventory, MaceratorBlockEntity blockEntity) {
        super(ModScreenHandlers.MACERATOR_SCREEN_HANDLER, syncId);
        this.blockEntity = blockEntity;

        Inventory inventory = blockEntity != null ? blockEntity : EMPTY_INVENTORY;

        this.addSlot(new Slot(inventory, 0, 44, 35));
        this.addSlot(new Slot(inventory, 1, 62, 35));
        this.addSlot(new Slot(inventory, 2, 116, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                if (blockEntity != null) {
                    ItemStack input1 = blockEntity.getStack(0);
                    ItemStack input2 = blockEntity.getStack(1);

                    if (!input1.isEmpty()) input1.decrement(1);
                    if (!input2.isEmpty()) input2.decrement(1);

                    blockEntity.markDirty();
                }
                super.onTakeItem(player, stack);
            }
        });

        addPlayerInventory(playerInventory);

        if (blockEntity != null) {
            blockEntity.onOpen(playerInventory.player);
        }
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < 3) {
                if (!this.insertItem(originalStack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if (!this.insertItem(originalStack, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.blockEntity == null || this.blockEntity.canPlayerUse(player);
    }
}