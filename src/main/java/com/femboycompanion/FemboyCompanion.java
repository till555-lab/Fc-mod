package com.femboycompanion;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FemboyCompanion.MOD_ID)
public class FemboyCompanion {
    public static final String MOD_ID = "femboycompanion";
    public static final Logger LOGGER = LogManager.getLogger();

    public FemboyCompanion() {
        LOGGER.info("Femboy Companion loaded");
    }
}
