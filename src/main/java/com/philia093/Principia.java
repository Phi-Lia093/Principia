package com.philia093;

import com.philia093.blocks.ModBlocks;
import com.philia093.blocks.machines.ModMachines;
import com.philia093.blocks.machines.ModRecipeTypes;
import com.philia093.util.ChemicalFormulaTooltip;
import com.philia093.util.ModItemGroups;
import com.philia093.items.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Principia implements ModInitializer {
	public static final String MOD_ID = "principia";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// 先注册配方类型
		ModRecipeTypes.registerRecipeTypes();

		// 注册机器（会注册方块和方块实体）
		ModMachines.registerMachines();

		// 注册普通方块
		ModBlocks.registerModBlocks();

		if(FabricLoader.getInstance().getEnvironmentType()== EnvType.CLIENT){
			ChemicalFormulaTooltip.registerChemicalFormula();
		}

		ModItemGroups.registerModItemGroups();
		ModItems.registerModItems();
	}
}