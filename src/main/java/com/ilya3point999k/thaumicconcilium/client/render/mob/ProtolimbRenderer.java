package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.RiftRenderer;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderCallback;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderHelper;
import com.ilya3point999k.thaumicconcilium.client.render.model.GibbedBipedModel;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse.Protolimb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

public class ProtolimbRenderer extends Render
{
    GibbedBipedModel biped = new GibbedBipedModel();

    private final ShaderCallback shaderCallback;

    public ProtolimbRenderer()
    {
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
        Protolimb gib = (Protolimb) entity;
        byte type = gib.getType();

        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);

        Minecraft.getMinecraft().renderEngine.bindTexture(RiftRenderer.starsTexture);
        ShaderHelper.useShader(ShaderHelper.endShader, shaderCallback);

        GL11.glEnable(GL11.GL_BLEND);

        float rotation = gib.innerRotation + partialTickTime;
        float f = 0.0625f;

        for (int q = 0; q <= 3; ++q) {
            if (q < 3) GL11.glDepthMask(false);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, (q < 3) ? GL11.GL_ONE : GL11.GL_ONE_MINUS_SRC_ALPHA);

            float scale = (q < 3) ? (float)Math.pow(1.1, q) : 1.0f;

            switch (type) {
                case 0:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(biped.bipedHead.rotationPointX * f, biped.bipedHead.rotationPointY * f, biped.bipedHead.rotationPointZ * f);
                    GL11.glRotatef(rotation, 0.7071F, 0.0F, 0.7071F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(-biped.bipedHead.rotationPointX * f, -biped.bipedHead.rotationPointY * f, -biped.bipedHead.rotationPointZ * f);
                    biped.bipedHead.render(f);
                    GL11.glPopMatrix();
                    break;
                case 1:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(biped.bipedLeftArm.rotationPointX * f, biped.bipedLeftArm.rotationPointY * f, biped.bipedLeftArm.rotationPointZ * f);
                    GL11.glRotatef(rotation, 0.7071F, 0.0F, 0.7071F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(-biped.bipedLeftArm.rotationPointX * f, -biped.bipedLeftArm.rotationPointY * f, -biped.bipedLeftArm.rotationPointZ * f);
                    biped.bipedLeftArm.render(f);
                    GL11.glPopMatrix();
                    break;
                case 2:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(biped.bipedRightArm.rotationPointX * f, biped.bipedRightArm.rotationPointY * f, biped.bipedRightArm.rotationPointZ * f);
                    GL11.glRotatef(rotation, 0.7071F, 0.0F, 0.7071F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(-biped.bipedRightArm.rotationPointX * f, -biped.bipedRightArm.rotationPointY * f, -biped.bipedRightArm.rotationPointZ * f);
                    biped.bipedRightArm.render(f);
                    GL11.glPopMatrix();
                    break;
                case 3:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(biped.bipedBody.rotationPointX * f, biped.bipedBody.rotationPointY * f, biped.bipedBody.rotationPointZ * f);
                    GL11.glRotatef(rotation, 0.7071F, 0.0F, 0.7071F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(-biped.bipedBody.rotationPointX * f, -biped.bipedBody.rotationPointY * f, -biped.bipedBody.rotationPointZ * f);
                    biped.bipedBody.render(f);
                    GL11.glPopMatrix();
                    break;
                case 4:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(biped.bipedLeftLeg.rotationPointX * f, biped.bipedLeftLeg.rotationPointY * f, biped.bipedLeftLeg.rotationPointZ * f);
                    GL11.glRotatef(rotation, 0.7071F, 0.0F, 0.7071F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(-biped.bipedLeftLeg.rotationPointX * f, -biped.bipedLeftLeg.rotationPointY * f, -biped.bipedLeftLeg.rotationPointZ * f);
                    biped.bipedLeftLeg.render(f);
                    GL11.glPopMatrix();
                    break;
                case 5:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(biped.bipedRightLeg.rotationPointX * f, biped.bipedRightLeg.rotationPointY * f, biped.bipedRightLeg.rotationPointZ * f);
                    GL11.glRotatef(rotation, 0.7071F, 0.0F, 0.7071F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(-biped.bipedRightLeg.rotationPointX * f, -biped.bipedRightLeg.rotationPointY * f, -biped.bipedRightLeg.rotationPointZ * f);
                    biped.bipedRightLeg.render(f);
                    GL11.glPopMatrix();
                    break;
                default:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(biped.bipedHead.rotationPointX * f, biped.bipedHead.rotationPointY * f, biped.bipedHead.rotationPointZ * f);
                    GL11.glRotatef(rotation, 0.7071F, 0.0F, 0.7071F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(-biped.bipedHead.rotationPointX * f, -biped.bipedHead.rotationPointY * f, -biped.bipedHead.rotationPointZ * f);
                    biped.bipedHead.render(f);
                    GL11.glPopMatrix();
                    break;
            }

            if (q < 3) GL11.glDepthMask(true);
        }

        GL11.glDisable(GL11.GL_BLEND);
        ShaderHelper.releaseShader();
        GL11.glPopMatrix();
    }
    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return RiftRenderer.starsTexture;
    }
}