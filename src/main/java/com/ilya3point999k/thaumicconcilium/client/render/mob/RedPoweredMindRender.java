package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.block.RedPoweredMindTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RedPoweredMindRender extends Render
{

    public RedPoweredMindRender()
    {
        super();
    }

    @Override
    public void doRender(Entity e, double x, double y, double z, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)x, (float)y + 0.3F, (float)z);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float bob = MathHelper.sin((float) Minecraft.getMinecraft().thePlayer.ticksExisted / 14.0F) * 0.03F + 0.03F;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.8F + bob, 0.0F);

        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(RedPoweredMindTileRenderer.texture);
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        RedPoweredMindTileRenderer.brain.render(true);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();

        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return RedPoweredMindTileRenderer.texture;
    }

}