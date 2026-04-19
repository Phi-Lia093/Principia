package com.philia093.client.render;

import com.philia093.items.CrushedOreItem;
import com.philia093.items.CrushedOreType;
import com.philia093.items.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemStack;

public class CrushedOreItemRenderer implements ItemColorProvider {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof CrushedOreItem item) {
            CrushedOreType type = item.getType();
            return switch (tintIndex) {
                case 0 -> type.color;
                default -> 0xFFFFFF;
            };
        }
        return 0xFFFFFF;
    }

    public static void register() {
        ColorProviderRegistry.ITEM.register(
                new CrushedOreItemRenderer(),
                ModItems.ALL_CRUSHED_ORES
        );
    }
}