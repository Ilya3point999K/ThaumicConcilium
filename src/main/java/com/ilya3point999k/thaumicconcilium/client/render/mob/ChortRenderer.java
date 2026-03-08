package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.model.ChortModel;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class ChortRenderer extends RenderLiving {

    private static final ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/chort.png");

    public ChortRenderer() {
        super(new ChortModel(), 0.5F);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        super.doRender((EntityLiving)entity, x, y, z, yaw, pitch);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }

}