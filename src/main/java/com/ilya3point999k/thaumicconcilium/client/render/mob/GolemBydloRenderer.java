package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.model.GolemBydloModel;
import com.ilya3point999k.thaumicconcilium.common.entities.other.GolemBydlo;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class GolemBydloRenderer extends RenderLiving {
    ModelBase damage;
    IIcon icon = null;
    private IModelCustom model;
    private static final ResourceLocation thaumium = new ResourceLocation("thaumcraft", "textures/models/golem_thaumium.png");

    public GolemBydloRenderer(ModelBase par1ModelBase) {
        super(par1ModelBase, 1.5F);
        GolemBydloModel mg = new GolemBydloModel(false);
        mg.pass = 2;
        this.damage = mg;
    }

    public void render(GolemBydlo e, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(e, par2, par4, par6, par8, par9);
    }


    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.render((GolemBydlo)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.render((GolemBydlo)par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        switch (((GolemBydlo)entity).getGolemType()) {
            case 0:
                return thaumium;
            default:
                return AbstractClientPlayer.locationStevePng;
        }
    }
}
