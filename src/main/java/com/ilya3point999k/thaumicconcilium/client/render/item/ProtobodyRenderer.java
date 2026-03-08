package com.ilya3point999k.thaumicconcilium.client.render.item;

import com.ilya3point999k.thaumicconcilium.client.render.RiftRenderer;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderCallback;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class ProtobodyRenderer implements IItemRenderer {
    ModelBiped corpse;
    private final ShaderCallback shaderCallback;

    public ProtobodyRenderer(){
        corpse = new ModelBiped();
        shaderCallback = new ShaderCallback() {
            @Override
            public void call(int shader) {
                Minecraft mc = Minecraft.getMinecraft();
                int x = ARBShaderObjects.glGetUniformLocationARB(shader, "yaw");
                ARBShaderObjects.glUniform1fARB(x, (float) (mc.thePlayer.rotationYaw * 2.0f * 3.141592653589793 / 360.0));
                int z = ARBShaderObjects.glGetUniformLocationARB(shader, "pitch");
                ARBShaderObjects.glUniform1fARB(z, -(float) (mc.thePlayer.rotationPitch * 2.0f * 3.141592653589793 / 360.0));
            }
        };
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != IItemRenderer.ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        final ItemRenderer ir = RenderManager.instance.itemRenderer;
        GL11.glPushMatrix();
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        if (type != IItemRenderer.ItemRenderType.INVENTORY) {
            if (type == IItemRenderer.ItemRenderType.ENTITY) {
                GL11.glTranslated(0.0, -3.0, 0.0);
            }
            else {
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                GL11.glTranslated(-1.0, -4.0, -1.0);
            }
        }
        else {
            GL11.glTranslated(0.0, -0.9, 0.0);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
        }
        UtilsFX.bindTexture(RiftRenderer.starsTexture);
        ShaderHelper.useShader(ShaderHelper.endScreenShader, shaderCallback);
        this.corpse.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.125f);
        ShaderHelper.releaseShader();
        GL11.glPopMatrix();
    }
}
