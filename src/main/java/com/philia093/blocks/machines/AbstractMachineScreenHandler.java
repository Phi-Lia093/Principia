package com.philia093.blocks.machines;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public abstract class AbstractMachineScreenHandler extends ScreenHandler {
    protected final Inventory inventory;
    protected final PropertyDelegate propertyDelegate;
    protected final int inputSlotCount;
    protected final int outputSlotCount;

    protected AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory,
                                           int inputSlots, int outputSlots) {
        this(type, syncId, playerInventory, new SimpleInventory(inputSlots + outputSlots),
                new ArrayPropertyDelegate(2), inputSlots, outputSlots);
    }

    protected AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory,
                                           Inventory inventory, PropertyDelegate delegate,
                                           int inputSlots, int outputSlots) {
        super(type, syncId);
        this.inventory = inventory;
        this.propertyDelegate = delegate;
        this.inputSlotCount = inputSlots;
        this.outputSlotCount = outputSlots;

        checkSize(inventory, inputSlots + outputSlots);
        inventory.onOpen(playerInventory.player);
        this.addProperties(delegate);

        // 子类需要调用 setupMachineSlots() 和 addPlayerSlots()
    }

    protected void setupMachineSlots(int startX, int startY, int spacingX, int spacingY) {
        // 输入槽
        for (int i = 0; i < inputSlotCount; i++) {
            int x = startX + (i * spacingX);
            int y = startY;
            this.addSlot(new Slot(inventory, i, x, y));
        }

        // 输出槽
        for (int i = 0; i < outputSlotCount; i++) {
            int x = startX + ((inputSlotCount + i) * spacingX);
            int y = startY;
            this.addSlot(new MachineOutputSlot(inventory, inputSlotCount + i, x, y));
        }
    }

    protected void addPlayerSlots(PlayerInventory playerInventory, int playerInvY, int hotbarY) {
        // 玩家背包
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, playerInvY + i * 18));
            }
        }
        // 快捷栏
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, hotbarY));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            // 机器槽位 -> 玩家背包
            if (invSlot < inputSlotCount + outputSlotCount) {
                if (!this.insertItem(originalStack, inputSlotCount + outputSlotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // 玩家背包 -> 机器输入槽
            else {
                if (!this.insertItem(originalStack, 0, inputSlotCount, false)) {
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
        return this.inventory.canPlayerUse(player);
    }

    public int getProgress() { return propertyDelegate.get(0); }
    public int getMaxProgress() { return propertyDelegate.get(1); }

    // 输出槽专用类
    protected static class MachineOutputSlot extends Slot {
        public MachineOutputSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }
    }
}