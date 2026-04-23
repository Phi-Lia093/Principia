package com.philia093.principia.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class AbstractBatteryItem extends Item {
    private static final String ENERGY_TAG = "Energy";

    private final long maxEnergy;
    private final boolean allowCharge;
    private final boolean allowDischarge;

    public AbstractBatteryItem(Settings settings, long maxEnergy, boolean allowCharge, boolean allowDischarge) {
        super(settings.maxDamage((int) maxEnergy));
        this.maxEnergy = maxEnergy;
        this.allowCharge = allowCharge;
        this.allowDischarge = allowDischarge;
    }


    public AbstractBatteryItem(Settings settings, long maxEnergy) {
        this(settings, maxEnergy, true, true);
    }


    public long getEnergy(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(ENERGY_TAG)) {
            return nbt.getLong(ENERGY_TAG);
        }
        return maxEnergy;
    }

    private void setEnergy(ItemStack stack, long energy) {
        energy = Math.max(0, Math.min(energy, maxEnergy));
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putLong(ENERGY_TAG, energy);

        stack.setDamage((int) (maxEnergy - energy));
    }

    public long insertEnergy(ItemStack stack, long maxAmount, boolean simulate) {
        if (!allowCharge) return 0;

        long current = getEnergy(stack);
        long received = Math.min(maxEnergy - current, maxAmount);

        if (!simulate && received > 0) {
            setEnergy(stack, current + received);
        }
        return received;
    }

    public long extractEnergy(ItemStack stack, long maxAmount, boolean simulate) {
        if (!allowDischarge) return 0;

        long current = getEnergy(stack);
        long extracted = Math.min(current, maxAmount);

        if (!simulate && extracted > 0) {
            setEnergy(stack, current - extracted);
        }
        return extracted;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        long energy = getEnergy(stack);
        tooltip.add(Text.literal(String.format("energy %,d / %,d", energy, maxEnergy))
                .formatted(Formatting.GRAY));

        if (!allowCharge && !allowDischarge) {
            tooltip.add(Text.literal("LOCKED").formatted(Formatting.RED));
        } else if (!allowCharge) {
            tooltip.add(Text.literal("DISCHARGE ONLY").formatted(Formatting.GOLD));
        } else if (!allowDischarge) {
            tooltip.add(Text.literal("CHARGE ONLY").formatted(Formatting.GOLD));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) { return true; }

    @Override
    public int getItemBarColor(ItemStack stack) {
        float percent = (float) getEnergy(stack) / maxEnergy;
        if (percent > 0.66f) return 0x00FF00;
        if (percent > 0.33f) return 0xFFAA00;
        return 0xFF3300;
    }

    public long getMaxEnergy() { return maxEnergy; }
    public boolean isAllowCharge() { return allowCharge; }
    public boolean isAllowDischarge() { return allowDischarge; }
}
