package com.ilya3point999k.thaumicconcilium.client.render.projectile;

import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ChargedWispEntity;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.lib.UtilsFX;

import java.awt.*;

public class ChargedWispRenderer extends Render {
    int size1 = 0;
    int size2 = 0;

    public ChargedWispRenderer() {
        this.shadowSize = 0.0F;
    }

    public void renderEntityAt(Entity entity, double x, double y, double z, float fq, float pticks) {
        float f1 = ActiveRenderInfo.rotationX;
        float f2 = ActiveRenderInfo.rotationXZ;
        float f3 = ActiveRenderInfo.rotationZ;
        float f4 = ActiveRenderInfo.rotationYZ;
        float f5 = ActiveRenderInfo.rotationXY;
        float f10 = 1.0F;
        float f11 = (float) x;
        float f12 = (float) y + 0.45F;
        float f13 = (float) z;
        Tessellator tessellator = Tessellator.instance;
        Color color = new Color(Aspect.getAspect(((ChargedWispEntity) entity).getType()).getColor());

        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        UtilsFX.bindTexture("textures/misc/wisp.png");
        int i = entity.ticksExisted % 16;
        float size4 = (float) (this.size1 * 4);
        float float_sizeMinus0_01 = (float) this.size1 - 0.01F;
        float float_texNudge = 1.0F / ((float) this.size1 * (float) this.size1 * 2.0F);
        float float_reciprocal = 1.0F / (float) this.size1;
        float x0 = ((float) (i % 4 * this.size1) + 0.0F) / size4;
        float x1 = ((float) (i % 4 * this.size1) + float_sizeMinus0_01) / size4;
        float x2 = ((float) (i / 4 * this.size1) + 0.0F) / size4;
        float x3 = ((float) (i / 4 * this.size1) + float_sizeMinus0_01) / size4;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(240);

        tessellator.setColorRGBA_F((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, 1.0F);

        tessellator.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, x1, x3);
        tessellator.addVertexWithUV(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, x1, x2);
        tessellator.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, x0, x2);
        tessellator.addVertexWithUV(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, x0, x3);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glAlphaFunc(516, 0.003921569F);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        UtilsFX.bindTexture(ParticleEngine.particleTexture);
        int qq = entity.ticksExisted % 16;
        float size8 = 16.0F;
        x0 = (float) qq / size8;
        x1 = (float) (qq + 1) / size8;
        x2 = 5.0F / size8;
        x3 = 6.0F / size8;
        float var11 = MathHelper.sin(((float) entity.ticksExisted + pticks) / 10.0F) * 0.1F;
        f10 = 0.4F + var11;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, x1, x3);
        tessellator.addVertexWithUV(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, x1, x2);
        tessellator.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, x0, x2);
        tessellator.addVertexWithUV(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, x0, x3);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glAlphaFunc(516, 0.1F);
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        if (this.size1 == 0) {
            this.size1 = UtilsFX.getTextureSize("textures/misc/wisp.png", 64);
        }

        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}