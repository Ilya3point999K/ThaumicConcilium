package com.ilya3point999k.thaumicconcilium.client.render;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.DistortionEffectEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.ModelCube;

public class DistortionEffectRenderer  extends Render {

    private final static ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/noise.png");
    private final ModelCube model;
    private final ShaderCallback emptyCallback;
    public DistortionEffectRenderer() {
        Minecraft mc = Minecraft.getMinecraft();
        model = new ModelCube();
        this.shadowSize = 0.0F;
        emptyCallback = new ShaderCallback() {
            @Override
            public void call(int shader) {
            }
        };
    }

    public void renderEntityAt(DistortionEffectEntity entity, double x, double y, double z, float fq, float pticks) {
        Minecraft mc = Minecraft.getMinecraft();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTranslatef((float) x - 1.5F, (float) y - 0.5F, (float) z - 1.5F);
        GL11.glScalef(3.0F, 3.0F, 3.0F);
        ShaderHelper.useShader(ShaderHelper.distortionShader, emptyCallback);
        mc.renderEngine.bindTexture(texture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        model.render();
        ShaderHelper.releaseShader();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderEntityAt((DistortionEffectEntity) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }

    public static void renderQuad() {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, 1.0, 1.0);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
    }

}