package com.philia093.util;

import com.philia093.blocks.ModBlocks;
import com.philia093.items.CrushedOreItem;
import com.philia093.items.ModItems;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

public class ChemicalFormulaTooltip {
    private static final Map<Item, String> CHEMICAL_FORMULAS = new HashMap<>();

    static{
        // chem formula of item ores
        CHEMICAL_FORMULAS.put(ModBlocks.APATITE_ORE.asItem(), "Ca₅(PO₄)₃(F,Cl,OH)");
        CHEMICAL_FORMULAS.put(ModBlocks.BAUXITE_ORE.asItem(), "Al(OH)₃");
        CHEMICAL_FORMULAS.put(ModBlocks.CASSITERITE_ORE.asItem(), "SnO₂");
        CHEMICAL_FORMULAS.put(ModBlocks.CHALCOPYRITE_ORE.asItem(), "CuFeS₂");
        CHEMICAL_FORMULAS.put(ModBlocks.CHROMITE_ORE.asItem(), "FeCr₂O₄");
        CHEMICAL_FORMULAS.put(ModBlocks.FLUORITE_ORE.asItem(), "CaF₂");
        CHEMICAL_FORMULAS.put(ModBlocks.GALENA_ORE.asItem(), "PbS");
        CHEMICAL_FORMULAS.put(ModBlocks.ILMENITE_ORE.asItem(), "FeTiO₃");
        CHEMICAL_FORMULAS.put(ModBlocks.LIGNITE_ORE.asItem(), "C");
        CHEMICAL_FORMULAS.put(ModBlocks.LIMESTONE_ORE.asItem(), "CaCO₃");
        CHEMICAL_FORMULAS.put(ModBlocks.MAGNETITE_ORE.asItem(), "Fe₃O₄");
        CHEMICAL_FORMULAS.put(ModBlocks.MALACHITE_ORE.asItem(), "Cu₂CO₃(OH)₂");
        CHEMICAL_FORMULAS.put(ModBlocks.MONAZITE_ORE.asItem(), "(Ce,La,Nd,Th)PO₄");
        CHEMICAL_FORMULAS.put(ModBlocks.PENTLANDITE_ORE.asItem(), "(Fe,Ni)₉S₈");
        CHEMICAL_FORMULAS.put(ModBlocks.PYROLUSITE_ORE.asItem(), "MnO₂");
        CHEMICAL_FORMULAS.put(ModBlocks.ROCK_SALT_ORE.asItem(), "NaCl");
        CHEMICAL_FORMULAS.put(ModBlocks.SPHALERITE_ORE.asItem(), "ZnS");
        CHEMICAL_FORMULAS.put(ModBlocks.SULFUR_ORE.asItem(), "S");
        CHEMICAL_FORMULAS.put(ModBlocks.VANADINITE_ORE.asItem(), "Pb₅(VO₄)₃Cl");

        // "vanilla" items
        CHEMICAL_FORMULAS.put(Items.SUGAR, "C₆H₁₂O₆");
        CHEMICAL_FORMULAS.put(Items.BLAZE_ROD, "S");
        CHEMICAL_FORMULAS.put(Items.BLAZE_POWDER, "S");
        CHEMICAL_FORMULAS.put(Items.GOLD_BLOCK, "Au");
        CHEMICAL_FORMULAS.put(Items.GOLD_INGOT, "Au");
        CHEMICAL_FORMULAS.put(Items.GOLD_NUGGET, "Au");
        CHEMICAL_FORMULAS.put(Items.GOLD_ORE, "Au");
        CHEMICAL_FORMULAS.put(Items.IRON_ORE, "Fe₂O₃");
        CHEMICAL_FORMULAS.put(Items.IRON_BLOCK, "Fe");
        CHEMICAL_FORMULAS.put(Items.IRON_INGOT, "Fe");
        CHEMICAL_FORMULAS.put(Items.IRON_NUGGET, "Fe");
        CHEMICAL_FORMULAS.put(Items.DIAMOND, "C");
        CHEMICAL_FORMULAS.put(Items.DIAMOND_BLOCK, "C");
        CHEMICAL_FORMULAS.put(Items.DIAMOND_ORE, "C");
        CHEMICAL_FORMULAS.put(Items.COAL, "C");
        CHEMICAL_FORMULAS.put(Items.COAL_BLOCK, "C");
        CHEMICAL_FORMULAS.put(Items.COAL_ORE, "C");
        CHEMICAL_FORMULAS.put(Items.CHARCOAL, "C");
        CHEMICAL_FORMULAS.put(Items.REDSTONE, "HgO");

        for(Item item : ModItems.ALL_CRUSHED_ORES){
            if(item instanceof CrushedOreItem crushedOreItem){
                CHEMICAL_FORMULAS.put(crushedOreItem, crushedOreItem.getType().formula);
            }
        }
    }

    public static void registerChemicalFormula(){
        ItemTooltipCallback.EVENT.register((stack, context, list) -> {
            Item item = stack.getItem();
            String formula = CHEMICAL_FORMULAS.get(item);
            if (formula != null) {
                list.add(Text.literal(formula).styled(style -> style.withColor(Formatting.BLUE).withItalic(true)));
            }
        });
    }
}
