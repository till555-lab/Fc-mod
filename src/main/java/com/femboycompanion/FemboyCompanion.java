package com.femboycompanion;

import com.femboycompanion.entity.FemboyEntity;
import com.femboycompanion.registry.ModEntities;
import com.femboycompanion.registry.ModItems;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        bus.addListener(this::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
        LOGGER.info("Femboy Companion loaded");
    }

    private void onAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FEMBOY.get(), FemboyEntity.createAttributes().build());
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> EntitySpawnPlacementRegistry.register(ModEntities.FEMBOY.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                FemboyEntity::checkSpawnRules));
    }

    private void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category c = event.getCategory();
        if (c == Biome.Category.MUSHROOM || c == Biome.Category.OCEAN || c == Biome.Category.RIVER
                || c == Biome.Category.NETHER || c == Biome.Category.THEEND || c == Biome.Category.NONE) {
            return;
        }
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
                .add(new MobSpawnInfo.Spawners(ModEntities.FEMBOY.get(), 6, 2, 4));
    }
}
