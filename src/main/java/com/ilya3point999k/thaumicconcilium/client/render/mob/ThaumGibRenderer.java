package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.model.GibbedBipedModel;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.ThaumGib;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.codechicken.lib.math.MathHelper;

public class ThaumGibRenderer extends Render
{
    GibbedBipedModel biped = new GibbedBipedModel();

    ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID + ":textures/entity/thaumaturge.png");
    public ThaumGibRenderer()
    {
        super();
    }



    @Override
    public void doRender(Entity e, double par2, double par4, double par6, float par8, float par9)
    {
        ThaumGib gib = (ThaumGib) e;
        byte type = gib.getType();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPushMatrix();
        bindEntityTexture(gib);

        GL11.glTranslated(par2, par4, par6);

        GL11.glTranslatef(0.0F, type == 0 ? 4F / 16F : 2F / 16F, 0.0F);

        GL11.glRotatef(MathHelper.approachLinear(gib.prevRotationYaw, gib.rotationYaw, par9), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);

        float f = 0.0625F;
        switch (type){
            case 0:{
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                biped.bipedHead.render(f);
                break;
            }
            case 1:{
                biped.bipedLeftArm.render(f);
                break;
            }
            case 2:{
                biped.bipedRightArm.render(f);
                break;
            }
            case 3:{
                biped.bipedBody.render(f);
                break;
            }
            case 4:{
                biped.bipedLeftLeg.render(f);
                break;
            }
            case 5:{
                biped.bipedRightLeg.render(f);
                break;
            }
        }

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return texture;
    }
}