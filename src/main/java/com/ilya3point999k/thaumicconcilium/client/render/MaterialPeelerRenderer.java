package com.ilya3point999k.thaumicconcilium.client.render;

import com.ilya3point999k.thaumicconcilium.common.entities.MaterialPeeler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.ModelCube;

public class MaterialPeelerRenderer extends Render {

    ModelCube model = new ModelCube();
    private static final ResourceLocation rl = new ResourceLocation("textures/entity/end_portal.png");
    private final ShaderCallback shaderCallback;

    public MaterialPeelerRenderer(){
        super();
        shaderCallback = new ShaderCallback() {
            @Override
            public void call(int shader) {
                Minecraft mc = Minecraft.getMinecraft();
                int x = ARBShaderObjects.glGetUniformLocationARB(shader, "yaw");
                ARBShaderObjects.glUniform1fARB(x, (float)(mc.thePlayer.rotationYaw * 2.0f * 3.141592653589793 / 360.0));
                int z = ARBShaderObjects.glGetUniformLocationARB(shader, "pitch");
                ARBShaderObjects.glUniform1fARB(z, -(float)(mc.thePlayer.rotationPitch * 2.0f * 3.141592653589793 / 360.0));
            }
        };


    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x - 0.5F, (float)y, (float)z - 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(rl);
        ShaderHelper.useShader(ShaderHelper.endShader, shaderCallback);
        GL11.glEnable(3042);
        for (int q = 0; q <= 3; ++q) {
            if (q < 3) {
                GL11.glDepthMask(false);
            }
            GL11.glBlendFunc(770, (q < 3) ? 1 : 771);
            GL11.glPushMatrix();
            float f2 = (((MaterialPeeler)entity).innerRotation + partialTickTime)*0.5F;
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            GL11.glRotatef(f2, 0.7071F, 0.0F, 0.7071F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            if (q < 3){
                GL11.glTranslatef(-0.1F, -0.1F, -0.1F);
                GL11.glScalef((float) Math.pow(1.1, q), (float) Math.pow(1.1, q), (float) Math.pow(1.1, q));
            }
            model.render();
            GL11.glPopMatrix();
            if (q < 3) {
                GL11.glDepthMask(true);
            }
        }
        GL11.glDisable(3042);
        ShaderHelper.releaseShader();
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return rl;
    }

}