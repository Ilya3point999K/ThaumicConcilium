package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.model.SloppyAlchemistModel;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Chort;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class SloppyAlchemistRenderer extends RenderLiving {

    private static final ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/excsi.png");
    private static final ModelBiped free = new ModelBiped(0.5F);
    private static final SloppyAlchemistModel nonfree = new SloppyAlchemistModel();

    public SloppyAlchemistRenderer() {
        super(free, 0.5F);
    }


    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        if (entity.riddenByEntity instanceof Chort){
            this.mainModel = nonfree;
        } else {
            this.mainModel = free;
        }
        super.doRender((EntityLiving)entity, x, y, z, yaw, pitch);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
