package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.LesserPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class LesserPortalRenderer extends Render {
    public static final ResourceLocation portaltex = new ResourceLocation("thaumcraft", "textures/misc/cultist_portal.png");

    public LesserPortalRenderer() {
        this.shadowSize = 0.1F;
        this.shadowOpaque = 0.5F;
    }

    public void renderPortal(LesserPortal portal, double px, double py, double pz, float par8, float f) {

        long nt = System.nanoTime();
        long time = nt / 50000000L;
        float scaley = 1.5F;
        int e = (int)Math.min(50.0F, (float)portal.ticksExisted + f);
        double d;
        if (portal.hurtTime > 0) {
            d = Math.sin((double)(portal.hurtTime * 72) * Math.PI / 180.0);
            scaley = (float)((double)scaley - d / 4.0);
            e = (int)((double)e + 6.0 * d);
        }

        if (portal.pulse > 0) {
            d = Math.sin((double)(portal.pulse * 36) * Math.PI / 180.0);
            scaley = (float)((double)scaley + d / 4.0);
            e = (int)((double)e + 12.0 * d);
        }

        float scale = (float)e / 50.0F * 1.3F;
        py += (double)(portal.height / 2.0F);
        float m = (1.0F - portal.getHealth() / portal.getMaxHealth()) / 3.0F;
        float bob = MathHelper.sin((float)portal.ticksExisted / (5.0F - 12.0F * m)) * m + m;
        float bob2 = MathHelper.sin((float)portal.ticksExisted / (6.0F - 15.0F * m)) * m + m;
        float alpha = 1.0F - bob;
        scaley -= bob / 4.0F;
        scale -= bob2 / 3.0F;
        UtilsFX.bindTexture(portaltex);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            Tessellator tessellator = Tessellator.instance;
            float arX = ActiveRenderInfo.rotationX;
            float arZ = ActiveRenderInfo.rotationZ;
            float arYZ = ActiveRenderInfo.rotationYZ;
            float arXY = ActiveRenderInfo.rotationXY;
            float arXZ = ActiveRenderInfo.rotationXZ;
            EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
            double var10000 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
            var10000 = player.prevPosY + (player.posY - player.prevPosY) * (double)f;
            var10000 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
            tessellator.startDrawingQuads();
            tessellator.setBrightness(220);
            tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, alpha);
            Vec3 v1 = Vec3.createVectorHelper((double)(-arX - arYZ), (double)(-arXZ), (double)(-arZ - arXY));
            Vec3 v2 = Vec3.createVectorHelper((double)(-arX + arYZ), (double)arXZ, (double)(-arZ + arXY));
            Vec3 v3 = Vec3.createVectorHelper((double)(arX + arYZ), (double)arXZ, (double)(arZ + arXY));
            Vec3 v4 = Vec3.createVectorHelper((double)(arX - arYZ), (double)(-arXZ), (double)(arZ - arXY));
            int frame = 15 - (int)time % 16;
            float f2 = (float)frame / 16.0F;
            float f3 = f2 + 0.0625F;
            float f4 = 0.0F;
            float f5 = 1.0F;
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            tessellator.addVertexWithUV(px + v1.xCoord * (double)scale, py + v1.yCoord * (double)scaley, pz + v1.zCoord * (double)scale, (double)f3, (double)f4);
            tessellator.addVertexWithUV(px + v2.xCoord * (double)scale, py + v2.yCoord * (double)scaley, pz + v2.zCoord * (double)scale, (double)f3, (double)f5);
            tessellator.addVertexWithUV(px + v3.xCoord * (double)scale, py + v3.yCoord * (double)scaley, pz + v3.zCoord * (double)scale, (double)f2, (double)f5);
            tessellator.addVertexWithUV(px + v4.xCoord * (double)scale, py + v4.yCoord * (double)scaley, pz + v4.zCoord * (double)scale, (double)f2, (double)f4);
            tessellator.draw();
        }

        GL11.glDisable(32826);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderPortal((LesserPortal) par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}