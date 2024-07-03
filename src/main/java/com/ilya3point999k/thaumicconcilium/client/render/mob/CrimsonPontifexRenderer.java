package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.CrimsonPontifex;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class CrimsonPontifexRenderer extends RenderBiped {

    private final ResourceLocation texture;

    public CrimsonPontifexRenderer(ModelBiped model, ResourceLocation texture, float shadowSize) {
        super(model, shadowSize);
        this.texture = texture;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        super.doRender((EntityLiving)entity, x, y, z, yaw, pitch);
        BossStatus.setBossStatus((CrimsonPontifex)entity, true);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return texture;
    }
}
