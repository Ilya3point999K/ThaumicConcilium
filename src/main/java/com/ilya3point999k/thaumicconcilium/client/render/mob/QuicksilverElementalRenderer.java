package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.ShaderCallback;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class QuicksilverElementalRenderer extends RenderBiped {

    private final ResourceLocation texture;
    private final ShaderCallback shaderCallback;

    public QuicksilverElementalRenderer(ModelBiped model, ResourceLocation texture, float shadowSize) {
        super(model, shadowSize);
        this.texture = texture;
        shaderCallback = new ShaderCallback() {
            @Override
            public void call(int shader) {
            }
        };
    }

    @Override
    protected void preRenderCallback(EntityLivingBase e, float p_77041_2_) {
        float s = e.getHealth() / 30.0F;
        GL11.glScalef(s, s, s);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        super.doRender((EntityLiving) entity, x, y, z, yaw, pitch);
    }

    @Override
    protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        ShaderHelper.useShader(ShaderHelper.wiggleShader, shaderCallback);
        super.renderModel(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        ShaderHelper.releaseShader();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return texture;
    }

}