package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.client.render.model.RedPoweredMindModel;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.tiles.RedPoweredMindTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RedPoweredMindTileRenderer extends TileEntitySpecialRenderer {
    public static final RedPoweredMindModel brain = new RedPoweredMindModel();
    public static final ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/red_powered_mind.png");
    public RedPoweredMindTileRenderer() {


    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        RedPoweredMindTile te = (RedPoweredMindTile) tile;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.3F, (float)z + 0.5F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float bob = MathHelper.sin((float)Minecraft.getMinecraft().thePlayer.ticksExisted / 14.0F) * 0.03F + 0.03F;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.8F + bob, 0.0F);

        float f2;
        for(f2 = te.rota - te.rotb; f2 >= 3.141593F; f2 -= 6.283185F) {
        }

        while(f2 < -3.141593F) {
            f2 += 6.283185F;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        float f3 = te.rotb + f2 * f;
        GL11.glRotatef(f3 * 180.0F / 3.141593F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        this.brain.render(false);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();

        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

}