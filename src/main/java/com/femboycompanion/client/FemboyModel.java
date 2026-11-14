package com.femboycompanion.client;

import com.femboycompanion.entity.FemboyEntity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class FemboyModel extends BipedModel<FemboyEntity> {
    private final ModelRenderer earLeft;
    private final ModelRenderer earRight;
    private final ModelRenderer tail;

    public FemboyModel() {
        super(0.0F);
        this.hat.visible = false;

        this.earLeft = new ModelRenderer(this, 32, 0);
        this.earLeft.addBox(-1.5F, -12.0F, -0.5F, 3.0F, 12.0F, 1.0F);
        this.earLeft.setPos(2.5F, -8.0F, 0.0F);
        this.head.addChild(this.earLeft);

        this.earRight = new ModelRenderer(this, 32, 0);
        this.earRight.addBox(-1.5F, -12.0F, -0.5F, 3.0F, 12.0F, 1.0F);
        this.earRight.setPos(-2.5F, -8.0F, 0.0F);
        this.head.addChild(this.earRight);

        this.tail = new ModelRenderer(this, 48, 0);
        this.tail.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F);
        this.tail.setPos(0.0F, 10.0F, 2.0F);
        this.body.addChild(this.tail);
    }

    @Override
    public void setupAnim(FemboyEntity entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float wiggle = MathHelper.sin(ageInTicks * 0.09F) * 0.05F;
        this.earLeft.zRot = 0.12F + wiggle;
        this.earRight.zRot = -0.12F - wiggle;
        this.tail.xRot = 0.5F;
        this.tail.yRot = MathHelper.sin(ageInTicks * 0.2F) * (0.25F + limbSwingAmount * 0.3F);
    }
}
