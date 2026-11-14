package com.femboycompanion.registry;

import com.femboycompanion.FemboyCompanion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FemboyCompanion.MOD_ID);

    public static final ItemGroup TAB = new ItemGroup("femboycompanion") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.FEMBOY_SPAWN_EGG.get());
        }
    };

    public static final RegistryObject<Item> FEMBOY_SPAWN_EGG = ITEMS.register("femboy_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FEMBOY, 0xF2F2F5, 0x2B2B32,
                    new Item.Properties().tab(TAB)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
