package com.ilya3point999k.thaumicconcilium.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ConcentratedWarpChargeModel extends ModelBase {
    ModelRenderer cube;
    ModelRenderer cube2;

    public ConcentratedWarpChargeModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.cube = new ModelRenderer(this, 0, 0);
        this.cube.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        this.cube.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cube2 = new ModelRenderer(this, 0, 32);
        this.cube2.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        this.cube2.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    public void render(Entity entity){
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        float f1 = 0.9F;
        //float pulse = 0.025F * MathHelper.sin((float)entity.ticksExisted * 0.075F);
        //GL11.glTranslatef(0.0F, 1.6F, 0.0F);
        GL11.glScalef(f1 , f1 , f1);
        //GL11.glTranslatef(0.0F, (f1 + pulse) / 2.0F, 0.0F);
        int j = 15728880;
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k, (float) l);
        this.cube.render(0.0624F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        j = entity.getBrightnessForRender(1F);
        k = j % 65536;
        l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k, (float) l);
        this.cube2.render(0.0625F);
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glPopMatrix();

    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
        float intensity = 0.02F;
        if (((EntityLivingBase)entity).hurtTime > 0) {
            intensity = 0.04F;
        }

        this.cube.rotateAngleX = intensity * MathHelper.sin(par3 * 0.05F);
        this.cube.rotateAngleZ = intensity * MathHelper.sin(par3 * 0.1F);
    }
}
