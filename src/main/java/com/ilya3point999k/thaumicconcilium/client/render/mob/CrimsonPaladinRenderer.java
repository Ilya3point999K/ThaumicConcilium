package com.ilya3point999k.thaumicconcilium.client.render.mob;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class CrimsonPaladinRenderer  extends RenderBiped {

    private final ResourceLocation texture;

    public CrimsonPaladinRenderer(ModelBiped model, ResourceLocation texture, float shadowSize) {
        super(model, shadowSize);
        this.texture = texture;
        this.modelBipedMain.bipedHeadwear.isHidden = true;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        super.doRender((EntityLiving) entity, x, y, z, yaw, pitch);
    }

    @Override
    protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        super.renderModel(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return texture;
    }

}