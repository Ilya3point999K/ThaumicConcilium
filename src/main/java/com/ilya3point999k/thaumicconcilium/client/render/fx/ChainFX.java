package com.ilya3point999k.thaumicconcilium.client.render.fx;

import com.ilya3point999k.thaumicconcilium.client.render.RiftRenderer;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class ChainFX extends EntityFX {
    public int particle = 16;
    private double offset = 0.0;
    private double tX = 0.0;
    private double tY = 0.0;
    private double tZ = 0.0;
    private double ptX = 0.0;
    private double ptY = 0.0;
    private double ptZ = 0.0;
    private float length = 0.0F;
    private float rotYaw = 0.0F;
    private float rotPitch = 0.0F;
    private float prevYaw = 0.0F;
    private float prevPitch = 0.0F;
    private Entity targetEntity = null;
    private float opacity = 0.3F;
    private float prevSize = 0.0F;

    public ChainFX(World par1World, double px, double py, double pz, double tx, double ty, double tz, float red, float green, float blue, int age) {
        super(par1World, px, py, pz, 0.0, 0.0, 0.0);
        this.particleRed = 0.5F;
        this.particleGreen = 0.5F;
        this.particleBlue = 0.5F;
        this.setSize(0.02F, 0.02F);
        this.noClip = true;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.tX = tx;
        this.tY = ty;
        this.tZ = tz;
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        this.particleMaxAge = age;
        EntityLivingBase renderentity = FMLClientHandler.instance().getClient().renderViewEntity;
        int visibleDistance = 50;
        if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) {
            visibleDistance = 25;
        }

        if (renderentity != null && renderentity.getDistance(this.posX, this.posY, this.posZ) > (double)visibleDistance) {
            this.particleMaxAge = 0;
        }

    }

    public void updateBeam(double xx, double yy, double zz, double x, double y, double z) {
        this.setPosition(xx, yy, zz);
        this.tX = x;
        this.tY = y;

        for(this.tZ = z; this.particleMaxAge - this.particleAge < 4; ++this.particleMaxAge) {
        }

    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY + this.offset;
        this.prevPosZ = this.posZ;
        this.ptX = this.tX;
        this.ptY = this.tY;
        this.ptZ = this.tZ;
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        float xd = (float)(this.posX - this.tX);
        float yd = (float)(this.posY - this.tY);
        float zd = (float)(this.posZ - this.tZ);
        this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
        double var7 = (double)MathHelper.sqrt_double((double)(xd * xd + zd * zd));
        this.rotYaw = (float)(Math.atan2((double)xd, (double)zd) * 180.0 / Math.PI);
        this.rotPitch = (float)(Math.atan2((double)yd, var7) * 180.0 / Math.PI);
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        if (this.opacity > 0.3F) {
            this.opacity -= 0.025F;
        }

        if (this.opacity < 0.3F) {
            this.opacity = 0.3F;
        }

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

    }

    public void setRGB(float r, float g, float b) {
        this.particleRed = r;
        this.particleGreen = g;
        this.particleBlue = b;
    }

    public void setPulse(boolean pulse, float r, float g, float b) {
        this.setRGB(r, g, b);
        if (pulse) {
            this.opacity = 0.8F;
        }

    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        Minecraft mc = Minecraft.getMinecraft();
        tessellator.draw();
        GL11.glPushMatrix();
        float var9 = 1.0F;
        float slide = (float) Minecraft.getMinecraft().thePlayer.ticksExisted;
        float size = 0.7F;

        mc.renderEngine.bindTexture(RiftRenderer.FLUC_ON);
        GL11.glTexParameterf(3553, 10242, 10497.0F);
        GL11.glTexParameterf(3553, 10243, 10497.0F);
        GL11.glDisable(2884);
        float var11 = slide + f;
        float var12 = -var11 * 0.2F - (float)MathHelper.floor_float(-var11 * 0.1F);
        GL11.glBlendFunc(770, 1);
        GL11.glDepthMask(false);
        float xx = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f - interpPosX);
        float yy = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f - interpPosY);
        float zz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f - interpPosZ);
        GL11.glTranslated((double)xx, (double)yy, (double)zz);
        float ry = (float)((double)this.prevYaw + (double)(this.rotYaw - this.prevYaw) * (double)f);
        float rp = (float)((double)this.prevPitch + (double)(this.rotPitch - this.prevPitch) * (double)f);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
        GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);
        double var44 = -0.15 * (double)size;
        double var17 = 0.15 * (double)size;
        float opmod = 0.1F;
        EntityLivingBase v = FMLClientHandler.instance().getClient().renderViewEntity;
        opmod = 1.0F;

        for(int t = 0; t < 2; ++t) {
            double var29 = (double)(this.length * var9);
            double var31 = 0.0;
            double var33 = 1.0;
            double var35 = (double)(-1.0F + var12 + (float)t / 3.0F);
            double var37 = (double)(this.length * var9) + var35;
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.setBrightness(200);
            tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.opacity * opmod);
            tessellator.addVertexWithUV(var44, var29, 0.0, var33, var37);
            tessellator.addVertexWithUV(var44, 0.0, 0.0, var33, var35);
            tessellator.addVertexWithUV(var17, 0.0, 0.0, var31, var35);
            tessellator.addVertexWithUV(var17, var29, 0.0, var31, var37);
            tessellator.draw();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getParticleTexture());
        tessellator.startDrawingQuads();
        this.prevSize = size;
    }
}
