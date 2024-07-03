package com.ilya3point999k.thaumicconcilium.client.render.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumic.tinkerer.common.core.helper.MiscHelper;

public class RuneFlowFX extends EntityFX {
    double ofx = 0.0;
    double ofy = 0.0;
    double tx, ty, tz;
    float rotation = 0.0F;
    int runeIndex = 0;

    public RuneFlowFX(World world, Entity e, double d, double d1, double d2, float f1, float f2, float f3, int m) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        if (f1 == 0.0F) {
            f1 = 1.0F;
        }
        this.tx = e.posX;
        this.ty = e.posY + 1.0;
        this.tz = e.posZ;
        this.rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        this.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        //this.rotation = 180.0f - Minecraft.getMinecraft().thePlayer.rotationYaw;
        this.particleRed = f1;
        this.particleGreen = f2;
        this.particleBlue = f3;
        this.particleGravity = 0.0F;
        this.motionX = this.motionY = this.motionZ = 0.0;
        this.particleMaxAge = 3 * m;
        this.noClip = false;
        this.setSize(0.01F, 0.01F);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.noClip = true;
        this.runeIndex = (int)(Math.random() * 16.0 + 224.0);
        this.ofx = (double)this.rand.nextFloat() * 0.2;
        this.ofy = -0.3 + (double)this.rand.nextFloat() * 0.6;
        this.particleScale = (float)(1.0 + this.rand.nextGaussian() * 0.10000000149011612);
        this.particleAlpha = 0.0F;
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        tessellator.draw();
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha / 2.0F);
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f - interpPosZ);
        GL11.glTranslated(var13, var14, var15);
        GL11.glRotatef(-this.rotationYaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.rotationPitch, 1.0f, 0.0f, 0.0f);
        GL11.glTranslated(this.ofx, this.ofy, -0.51);
        float var8 = (float)(this.runeIndex % 16) / 16.0F;
        float var9 = var8 + 0.0624375F;
        float var10 = 0.375F;
        float var11 = var10 + 0.0624375F;
        float var12 = 0.3F * this.particleScale;
        float var16 = 1.0F;
        GL11.glDepthMask(false);
        tessellator.startDrawingQuads();
        tessellator.setBrightness(255);
        tessellator.setColorRGBA_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.8f);
        tessellator.addVertexWithUV(-0.5 * (double)var12, 0.5 * (double)var12, 0.0, var9, var11);
        tessellator.addVertexWithUV(0.5 * (double)var12, 0.5 * (double)var12, 0.0, var9, var10);
        tessellator.addVertexWithUV(0.5 * (double)var12, -0.5 * (double)var12, 0.0, var8, var10);
        tessellator.addVertexWithUV(-0.5 * (double)var12, -0.5 * (double)var12, 0.0, var8, var11);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        tessellator.startDrawingQuads();
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float threshold = (float)this.particleMaxAge / 5.0F;
        if ((float)this.particleAge <= threshold) {
            this.particleAlpha = (float)this.particleAge / threshold;
        } else {
            this.particleAlpha = (float)(this.particleMaxAge - this.particleAge) / (float)this.particleMaxAge;
        }



        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        if(this.getDistanceSq(tx, ty, tz) < 3.5){
            this.setDead();
        }

        MiscHelper.setEntityMotionFromVector(this, new Vector3(tx, ty, tz), 0.5f);
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }
}