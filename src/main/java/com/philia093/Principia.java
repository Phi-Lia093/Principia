package com.philia093;

import com.philia093.blocks.ModBlocks;
import com.philia093.items.ModItemGroups;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Principia implements ModInitializer {
	public static final String MOD_ID = "principia";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItemGroups.registerModItemGroups();
	}
}
