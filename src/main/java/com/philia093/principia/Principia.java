package com.philia093.principia;

import com.philia093.principia.registry.ModBlocks;
import com.philia093.principia.registry.ModMachines;
import com.philia093.principia.registry.ModRecipeSerializers;
import com.philia093.principia.registry.ModRecipeTypes;
import com.philia093.principia.util.ChemicalFormulaTooltip;
import com.philia093.principia.util.ModItemGroups;
import com.philia093.principia.registry.ModItems;
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
		ModRecipeTypes.registerRecipeTypes();
		ModRecipeSerializers.registerSerializers();

		ModMachines.registerMachines();
		ModBlocks.registerModBlocks();



		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			ChemicalFormulaTooltip.registerChemicalFormula();
		}

		ModItemGroups.registerModItemGroups();
		ModItems.registerModItems();
	}
}