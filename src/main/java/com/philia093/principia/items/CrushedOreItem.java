package com.philia093.principia.items;

import net.minecraft.item.Item;

public class CrushedOreItem extends Item {
    private final CrushedOreType type;

    public CrushedOreItem(CrushedOreType type, Settings settings) {
        super(settings);
        this.type = type;
    }

    public CrushedOreType getType() {
        return type;
    }
}
