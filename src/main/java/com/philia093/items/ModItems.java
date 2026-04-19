package com.philia093.items;

import com.philia093.Principia;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item CRUSHED_MAGNETITE = registerCrushedOre(CrushedOreType.MAGNETITE);
    public static final Item CRUSHED_NATIVE_COPPER = registerCrushedOre(CrushedOreType.NATIVE_COPPER);
    public static final Item CRUSHED_MALACHITE = registerCrushedOre(CrushedOreType.MALACHITE);
    public static final Item CRUSHED_GALENA = registerCrushedOre(CrushedOreType.GALENA);
    public static final Item CRUSHED_CASSITERITE = registerCrushedOre(CrushedOreType.CASSITERITE);
    public static final Item CRUSHED_SPHALERITE = registerCrushedOre(CrushedOreType.SPHALERITE);
    public static final Item CRUSHED_VANADINITE = registerCrushedOre(CrushedOreType.VANADINITE);
    public static final Item CRUSHED_PENTLANDITE = registerCrushedOre(CrushedOreType.PENTLANDITE);
    public static final Item CRUSHED_BAUXITE = registerCrushedOre(CrushedOreType.BAUXITE);
    public static final Item CRUSHED_ILMENITE = registerCrushedOre(CrushedOreType.ILMENITE);
    public static final Item CRUSHED_CHROMITE = registerCrushedOre(CrushedOreType.CHROMITE);
    public static final Item CRUSHED_PYROLUSITE = registerCrushedOre(CrushedOreType.PYROLUSITE);
    public static final Item CRUSHED_CHALCOPYRITE = registerCrushedOre(CrushedOreType.CHALCOPYRITE);
    public static final Item CRUSHED_PITCHBLENDE = registerCrushedOre(CrushedOreType.PITCHBLENDE);
    public static final Item CRUSHED_MONAZITE = registerCrushedOre(CrushedOreType.MONAZITE);
    public static final Item CRUSHED_BERYL = registerCrushedOre(CrushedOreType.BERYL);
    public static final Item CRUSHED_MOLYBDENITE = registerCrushedOre(CrushedOreType.MOLYBDENITE);
    public static final Item CRUSHED_BISMUTHINITE = registerCrushedOre(CrushedOreType.BISMUTHINITE);

    public static final Item[] ALL_CRUSHED_ORES = {
            CRUSHED_MAGNETITE, CRUSHED_NATIVE_COPPER, CRUSHED_MALACHITE,
            CRUSHED_GALENA, CRUSHED_CASSITERITE, CRUSHED_SPHALERITE,
            CRUSHED_VANADINITE, CRUSHED_PENTLANDITE, CRUSHED_BAUXITE,
            CRUSHED_ILMENITE, CRUSHED_CHROMITE, CRUSHED_PYROLUSITE,
            CRUSHED_CHALCOPYRITE, CRUSHED_PITCHBLENDE, CRUSHED_MONAZITE,
            CRUSHED_BERYL, CRUSHED_MOLYBDENITE, CRUSHED_BISMUTHINITE
    };

    private static Item registerCrushedOre(CrushedOreType oreType) {
        return register(oreType.name, new CrushedOreItem(oreType, new Item.Settings()));
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("principia", name), item);
    }

    public static void registerModItems() {
        Principia.LOGGER.info("registering items");
    }
}