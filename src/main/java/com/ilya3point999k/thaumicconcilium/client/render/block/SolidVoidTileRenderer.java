package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.client.render.ShaderCallback;
import com.ilya3point999k.thaumicconcilium.client.render.ShaderHelper;
import com.ilya3point999k.thaumicconcilium.client.render.item.AstralMonitorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.ModelCube;

public class SolidVoidTileRenderer extends TileEntitySpecialRenderer {
    private final ShaderCallback shaderCallback;
    private ModelCube model = new ModelCube();
    public SolidVoidTileRenderer(){
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
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float pt) {
        GL11.glPushMatrix();
        ShaderHelper.useShader(ShaderHelper.endShader, shaderCallback);
        GL11.glTranslated(x, y, z);
        Minecraft.getMinecraft().renderEngine.bindTexture(AstralMonitorRenderer.SCREEN);
        model.render();
        GL11.glEnable(32826);
        ShaderHelper.releaseShader();
        GL11.glPopMatrix();
    }
}
