package com.ilya3point999k.thaumicconcilium.client.render.projectile;

import com.ilya3point999k.thaumicconcilium.client.render.ShaderCallback;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderHelper;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.CrimsonOrbEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.ModelCube;

public class CrimsonOrbEntityRenderer extends Render {

    private final static ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/noise.png");
    private final ModelCube model;
    private final ShaderCallback emptyCallback;
    public CrimsonOrbEntityRenderer() {
        Minecraft mc = Minecraft.getMinecraft();
        model = new ModelCube();
        this.shadowSize = 0.0F;
        emptyCallback = new ShaderCallback() {
            @Override
            public void call(int shader) {
            }
        };
    }

    public void renderEntityAt(CrimsonOrbEntity entity, double x, double y, double z, float fq, float pticks) {
        Minecraft mc = Minecraft.getMinecraft();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTranslatef((float) x - 0.3F, (float) y, (float) z - 0.3F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        ShaderHelper.useShader(ShaderHelper.crimsonShader, emptyCallback);
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
        this.renderEntityAt((CrimsonOrbEntity) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}