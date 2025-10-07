package com.ilya3point999k.thaumicconcilium.client.render.fx;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumic.tinkerer.common.core.helper.MiscHelper;

public class LeafFX extends EntityFX {
    double ofx = 0.0;
    double ofy = 0.0;
    double tx, ty, tz;
    private final float rotationSpeed; // radians per tick
    private float rotationAngle;       // current rotation
    private final double driftX, driftY, driftZ; // gentle constant drift
    private double swayPhase = 0.0; // oscillation phase

    static ResourceLocation leaf = new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/leaf.png");
    public static final ResourceLocation resource = new ResourceLocation("textures/particle/particles.png");

    public LeafFX(World world, double x, double y, double z, double d, double d1, double d2, float f1, float f2, float f3, int m) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        if (f1 == 0.0F) {
            f1 = 1.0F;
        }
        this.tx = d;
        this.ty = d1;
        this.tz = d2;
        this.rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        this.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        this.rotationSpeed = (rand.nextBoolean() ? 1 : -1) * 0.05F;
        this.rotationAngle = rand.nextFloat() * (float)Math.PI * 2F;

        this.particleRed = f1;
        this.particleGreen = f2;
        this.particleBlue = f3;
        this.particleGravity = 0.0F;

        // small random initial motion so particles drift from spawn
        this.motionX = (rand.nextDouble() * 2.0 - 1.0) * 0.01;
        this.motionY = (rand.nextDouble() * 2.0 - 1.0) * 0.01;
        this.motionZ = (rand.nextDouble() * 2.0 - 1.0) * 0.01;

        // gentle persistent drift component (very small)
        this.driftX = (rand.nextDouble() * 2.0 - 1.0) * 0.0525;
        this.driftY = (rand.nextDouble() * 2.0 - 1.0) * 0.0525;
        this.driftZ = (rand.nextDouble() * 2.0 - 1.0) * 0.0525;

        this.particleMaxAge = 3 * m;
        this.noClip = false;
        this.setSize(0.01F, 0.01F);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.noClip = true;
        this.ofx = (double)this.rand.nextFloat() * 0.2;
        this.ofy = -0.3 + (double)this.rand.nextFloat() * 0.6;
        this.particleScale = (float)(1.0 + this.rand.nextGaussian() * 0.10000000149011612);
        this.particleAlpha = 0.0F;
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float var12 = 0.1F * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f - interpPosZ);
        tessellator.draw();

        if (Minecraft.getMinecraft().renderViewEntity.getDistance(this.posX, this.posY, this.posZ) < 64) {
            GL11.glPushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture(leaf);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_CULL_FACE);

            tessellator.startDrawingQuads();
            tessellator.setBrightness(240);
            tessellator.setColorRGBA_F(1F, 1F, 1F, this.particleAlpha);

            float cos = (float)Math.cos(rotationAngle);
            float sin = (float)Math.sin(rotationAngle);

            // Rotate the two camera-facing basis vectors (f1/f4 are X/Y screen axes)
            float rf1 = f1 * cos - f4 * sin;
            float rf4 = f1 * sin + f4 * cos;

            tessellator.addVertexWithUV(var13 - rf1 * var12 - rf4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12, 1, 1);
            tessellator.addVertexWithUV(var13 - rf1 * var12 + rf4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12, 1, 0);
            tessellator.addVertexWithUV(var13 + rf1 * var12 + rf4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12, 0, 0);
            tessellator.addVertexWithUV(var13 + rf1 * var12 - rf4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12, 0, 1);

            tessellator.draw();
            GL11.glEnable(GL11.GL_CULL_FACE);

            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
        } else {
            this.particleMaxAge = 0;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
        tessellator.startDrawingQuads();
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        rotationAngle += rotationSpeed;

        // fade in/out
        float threshold = (float)this.particleMaxAge / 5.0F;
        if ((float)this.particleAge <= threshold) {
            this.particleAlpha = (float)this.particleAge / threshold;
        } else {
            this.particleAlpha = (float)(this.particleMaxAge - this.particleAge) / (float)this.particleMaxAge;
        }

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
            return;
        }

        if (this.getDistanceSq(tx, ty, tz) < 3.5) {
            this.setDead();
            return;
        }

        // gentle sway oscillator
        swayPhase += 0.05;
        double swayX = Math.sin(swayPhase * 0.7) * 0.0015;
        double swayY = Math.cos(swayPhase * 0.9) * 0.0012;
        double swayZ = Math.sin(swayPhase * 1.3) * 0.0015;

        // add a tiny drift + sway to motion so particle slowly wanders
        this.motionX += driftX + swayX;
        this.motionY += driftY + swayY;
        this.motionZ += driftZ + swayZ;

        // attract toward target slowly (reduced speed)
        MiscHelper.setEntityMotionFromVector(this, new Vector3(tx, ty, tz), 0.12f);

        // apply damping to slow down overall movement
        this.motionX *= 0.88;
        this.motionY *= 0.92;
        this.motionZ *= 0.88;

        // finally apply motion
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
    }

    @Override
    public int getFXLayer() {
        return 0;
    }
}
