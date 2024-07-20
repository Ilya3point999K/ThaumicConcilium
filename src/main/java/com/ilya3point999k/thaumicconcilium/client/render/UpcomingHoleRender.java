package com.ilya3point999k.thaumicconcilium.client.render;

import com.ilya3point999k.thaumicconcilium.common.entities.UpcomingHoleEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class UpcomingHoleRender extends Render {
    public UpcomingHoleRender() {
        this.shadowSize = 0.0F;
    }

    public void renderEntityAt(UpcomingHoleEntity entity, double xx, double y, double zz, float fq, float pticks) {
    }


    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderEntityAt((UpcomingHoleEntity) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }

}
