package com.ilya3point999k.thaumicconcilium.client.render.fx;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.fx.WRVector3;
import thaumcraft.client.lib.UtilsFX;

import java.util.Iterator;

public class FXColoredLightning extends EntityFX {
    private int type = 0;
    private float width = 0.03F;
    private FXColoredLightningCommon main;

    public FXColoredLightning(World world, WRVector3 jammervec, WRVector3 targetvec, long seed) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, jammervec, targetvec, seed);
        this.setupFromMain();
    }

    public FXColoredLightning(World world, Entity detonator, Entity target, long seed) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, detonator, target, seed);
        this.setupFromMain();
    }

    public FXColoredLightning(World world, Entity detonator, Entity target, long seed, int speed) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, detonator, target, seed, speed);
        this.setupFromMain();
    }

    public FXColoredLightning(World world, TileEntity detonator, Entity target, long seed) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, detonator, target, seed);
        this.setupFromMain();
    }

    public FXColoredLightning(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, x1, y1, z1, x, y, z, seed, duration, multi);
        this.setupFromMain();
    }

    public FXColoredLightning(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi, int speed) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, x1, y1, z1, x, y, z, seed, duration, multi, speed);
        this.setupFromMain();
    }

    public FXColoredLightning(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, x1, y1, z1, x, y, z, seed, duration, 1.0F);
        this.setupFromMain();
    }

    public FXColoredLightning(World world, TileEntity detonator, double x, double y, double z, long seed) {
        super(world, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.main = new FXColoredLightningCommon(world, detonator, x, y, z, seed);
        this.setupFromMain();
    }

    private void setupFromMain() {
        this.particleAge = this.main.particleMaxAge;
        this.setPosition((double)this.main.start.x, (double)this.main.start.y, (double)this.main.start.z);
        this.setVelocity(0.0, 0.0, 0.0);
    }

    public void defaultFractal() {
        this.main.defaultFractal();
    }

    public void fractal(int splits, float amount, float splitchance, float splitlength, float splitangle) {
        this.main.fractal(splits, amount, splitchance, splitlength, splitangle);
    }

    public void finalizeBolt() {
        this.main.finalizeBolt();
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(this);
    }

    public void setType(int type) {
        this.type = type;
        this.main.type = type;
    }

    public void setMultiplier(float m) {
        this.main.multiplier = m;
    }

    public void setWidth(float m) {
        this.width = m;
    }

    public void onUpdate() {
        this.main.onUpdate();
        if (this.main.particleAge >= this.main.particleMaxAge) {
            this.setDead();
        }

    }

    private static WRVector3 getRelativeViewVector(WRVector3 pos) {
        EntityPlayer renderentity = FMLClientHandler.instance().getClient().thePlayer;
        return new WRVector3((double)((float)renderentity.posX - pos.x), (double)((float)renderentity.posY - pos.y), (double)((float)renderentity.posZ - pos.z));
    }

    private void renderBolt(Tessellator tessellator, float partialframe, float cosyaw, float cospitch, float sinyaw, float cossinpitch, int pass, float mainalpha) {
        WRVector3 playervec = new WRVector3((double)(sinyaw * -cospitch), (double)(-cossinpitch / cosyaw), (double)(cosyaw * cospitch));
        float boltage = this.main.particleAge >= 0 ? (float)this.main.particleAge / (float)this.main.particleMaxAge : 0.0F;
        if (pass == 0) {
            mainalpha = (1.0F - boltage) * 0.4F;
        } else {
            mainalpha = 1.0F - boltage * 0.5F;
        }

        int renderlength = (int)(((float)this.main.particleAge + partialframe + (float)((int)(this.main.length * 3.0F))) / (float)((int)(this.main.length * 3.0F)) * (float)this.main.numsegments0);
        Iterator iterator = this.main.segments.iterator();

        while(iterator.hasNext()) {
            FXColoredLightningCommon.Segment rendersegment = (FXColoredLightningCommon.Segment)iterator.next();
            if (rendersegment.segmentno <= renderlength) {
                float width = this.width * (getRelativeViewVector(rendersegment.startpoint.point).length() / 5.0F + 1.0F) * (1.0F + rendersegment.light) * 0.5F;
                WRVector3 diff1 = WRVector3.crossProduct(playervec, rendersegment.prevdiff).scale(width / rendersegment.sinprev);
                WRVector3 diff2 = WRVector3.crossProduct(playervec, rendersegment.nextdiff).scale(width / rendersegment.sinnext);
                WRVector3 startvec = rendersegment.startpoint.point;
                WRVector3 endvec = rendersegment.endpoint.point;
                float rx1 = (float)((double)startvec.x - interpPosX);
                float ry1 = (float)((double)startvec.y - interpPosY);
                float rz1 = (float)((double)startvec.z - interpPosZ);
                float rx2 = (float)((double)endvec.x - interpPosX);
                float ry2 = (float)((double)endvec.y - interpPosY);
                float rz2 = (float)((double)endvec.z - interpPosZ);
                tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light);
                tessellator.addVertexWithUV((double)(rx2 - diff2.x), (double)(ry2 - diff2.y), (double)(rz2 - diff2.z), 0.5, 0.0);
                tessellator.addVertexWithUV((double)(rx1 - diff1.x), (double)(ry1 - diff1.y), (double)(rz1 - diff1.z), 0.5, 0.0);
                tessellator.addVertexWithUV((double)(rx1 + diff1.x), (double)(ry1 + diff1.y), (double)(rz1 + diff1.z), 0.5, 1.0);
                tessellator.addVertexWithUV((double)(rx2 + diff2.x), (double)(ry2 + diff2.y), (double)(rz2 + diff2.z), 0.5, 1.0);
                WRVector3 roundend;
                float rx3;
                float ry3;
                float rz3;
                if (rendersegment.next == null) {
                    roundend = rendersegment.endpoint.point.copy().add(rendersegment.diff.copy().normalize().scale(width));
                    rx3 = (float)((double)roundend.x - interpPosX);
                    ry3 = (float)((double)roundend.y - interpPosY);
                    rz3 = (float)((double)roundend.z - interpPosZ);
                    tessellator.addVertexWithUV((double)(rx3 - diff2.x), (double)(ry3 - diff2.y), (double)(rz3 - diff2.z), 0.0, 0.0);
                    tessellator.addVertexWithUV((double)(rx2 - diff2.x), (double)(ry2 - diff2.y), (double)(rz2 - diff2.z), 0.5, 0.0);
                    tessellator.addVertexWithUV((double)(rx2 + diff2.x), (double)(ry2 + diff2.y), (double)(rz2 + diff2.z), 0.5, 1.0);
                    tessellator.addVertexWithUV((double)(rx3 + diff2.x), (double)(ry3 + diff2.y), (double)(rz3 + diff2.z), 0.0, 1.0);
                }

                if (rendersegment.prev == null) {
                    roundend = rendersegment.startpoint.point.copy().sub(rendersegment.diff.copy().normalize().scale(width));
                    rx3 = (float)((double)roundend.x - interpPosX);
                    ry3 = (float)((double)roundend.y - interpPosY);
                    rz3 = (float)((double)roundend.z - interpPosZ);
                    tessellator.addVertexWithUV((double)(rx1 - diff1.x), (double)(ry1 - diff1.y), (double)(rz1 - diff1.z), 0.5, 0.0);
                    tessellator.addVertexWithUV((double)(rx3 - diff1.x), (double)(ry3 - diff1.y), (double)(rz3 - diff1.z), 0.0, 0.0);
                    tessellator.addVertexWithUV((double)(rx3 + diff1.x), (double)(ry3 + diff1.y), (double)(rz3 + diff1.z), 0.0, 1.0);
                    tessellator.addVertexWithUV((double)(rx1 + diff1.x), (double)(ry1 + diff1.y), (double)(rz1 + diff1.z), 0.5, 1.0);
                }
            }
        }

    }

    public void renderParticle(Tessellator tessellator, float partialframe, float cosyaw, float cospitch, float sinyaw, float sinsinpitch, float cossinpitch) {
        EntityPlayer renderentity = FMLClientHandler.instance().getClient().thePlayer;
        int visibleDistance = 100;
        if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) {
            visibleDistance = 50;
        }

        if (!(renderentity.getDistance(this.posX, this.posY, this.posZ) > (double)visibleDistance)) {
            tessellator.draw();
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            float ma = 1.0F;
            switch (this.type) {
                case 0:
                    this.particleRed = 0.6F;
                    this.particleGreen = 0.3F;
                    this.particleBlue = 0.6F;
                    GL11.glBlendFunc(770, 1);
                    break;
                case 1:
                    this.particleRed = 0.6F;
                    this.particleGreen = 0.6F;
                    this.particleBlue = 0.1F;
                    GL11.glBlendFunc(770, 1);
                    break;
                case 2:
                    this.particleRed = 0.1F;
                    this.particleGreen = 0.1F;
                    this.particleBlue = 0.6F;
                    GL11.glBlendFunc(770, 1);
                    break;
                case 3:
                    this.particleRed = 0.1F;
                    this.particleGreen = 1.0F;
                    this.particleBlue = 0.1F;
                    GL11.glBlendFunc(770, 1);
                    break;
                case 4:
                    this.particleRed = 0.6F;
                    this.particleGreen = 0.1F;
                    this.particleBlue = 0.1F;
                    GL11.glBlendFunc(770, 1);
                    break;
                case 5:
                    this.particleRed = 0.6F;
                    this.particleGreen = 0.2F;
                    this.particleBlue = 0.6F;
                    GL11.glBlendFunc(770, 771);
                    break;
                case 6:
                    this.particleRed = 0.75F;
                    this.particleGreen = 1.0F;
                    this.particleBlue = 1.0F;
                    ma = 0.2F;
                    GL11.glBlendFunc(770, 771);
            }

            UtilsFX.bindTexture("textures/misc/p_large.png");
            tessellator.startDrawingQuads();
            tessellator.setBrightness(15728880);
            this.renderBolt(tessellator, partialframe, cosyaw, cospitch, sinyaw, cossinpitch, 0, ma);
            tessellator.draw();
            switch (this.type) {
                case 0:
                    this.particleRed = 1.0F;
                    this.particleGreen = 0.6F;
                    this.particleBlue = 1.0F;
                    break;
                case 1:
                    this.particleRed = 1.0F;
                    this.particleGreen = 1.0F;
                    this.particleBlue = 0.1F;
                    break;
                case 2:
                    this.particleRed = 0.1F;
                    this.particleGreen = 0.1F;
                    this.particleBlue = 1.0F;
                    break;
                case 3:
                    this.particleRed = 0.1F;
                    this.particleGreen = 0.6F;
                    this.particleBlue = 0.1F;
                    break;
                case 4:
                    this.particleRed = 1.0F;
                    this.particleGreen = 0.1F;
                    this.particleBlue = 0.1F;
                    break;
                case 5:
                    this.particleRed = 0.0F;
                    this.particleGreen = 0.0F;
                    this.particleBlue = 0.0F;
                    GL11.glBlendFunc(770, 771);
                    break;
                case 6:
                    this.particleRed = 0.75F;
                    this.particleGreen = 1.0F;
                    this.particleBlue = 1.0F;
                    ma = 0.2F;
                    GL11.glBlendFunc(770, 771);
            }

            UtilsFX.bindTexture("textures/misc/p_small.png");
            tessellator.startDrawingQuads();
            tessellator.setBrightness(15728880);
            this.renderBolt(tessellator, partialframe, cosyaw, cospitch, sinyaw, cossinpitch, 1, ma);
            tessellator.draw();
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getParticleTexture());
            tessellator.startDrawingQuads();
        }
    }

    public int getRenderPass() {
        return 2;
    }
}