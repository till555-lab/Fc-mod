package com.femboycompanion.client;

import com.femboycompanion.entity.FemboyEntity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class FemboyRenderer extends BipedRenderer<FemboyEntity, BipedModel<FemboyEntity>> {
    private static final ResourceLocation WHITE =
            new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation BLACK =
            new ResourceLocation("textures/entity/enderman/enderman.png");

    public FemboyRenderer(EntityRendererManager manager) {
        super(manager, new BipedModel<>(0.0F), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(FemboyEntity entity) {
        return entity.getVariant() == FemboyEntity.VARIANT_BLACK ? BLACK : WHITE;
    }
}
