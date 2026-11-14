package com.femboycompanion;

import com.femboycompanion.entity.FemboyEntity;
import com.femboycompanion.registry.ModEntities;
import com.femboycompanion.registry.ModItems;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FemboyCompanion.MOD_ID)
public class FemboyCompanion {
    public static final String MOD_ID = "femboycompanion";
    public static final Logger LOGGER = LogManager.getLogger();

    public FemboyCompanion() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.register(bus);
        ModItems.register(bus);
        bus.addListener(this::onAttributes);
        LOGGER.info("Femboy Companion loaded");
    }

    private void onAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FEMBOY.get(), FemboyEntity.createAttributes().build());
    }
}
