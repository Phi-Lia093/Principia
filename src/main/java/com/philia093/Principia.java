package com.philia093;

import com.philia093.blocks.ModBlocks;
import com.philia093.blocks.machines.ModMachines;
import com.philia093.blocks.machines.ModRecipeSerializers;
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
		// 注册配方系统
		ModRecipeTypes.registerRecipeTypes();
		ModRecipeSerializers.registerSerializers();

		// 注册机器
		ModMachines.registerMachines();
		ModBlocks.registerModBlocks();



		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			ChemicalFormulaTooltip.registerChemicalFormula();
		}

		ModItemGroups.registerModItemGroups();
		ModItems.registerModItems();
	}
}