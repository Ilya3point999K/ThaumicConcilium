package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.tiles.VisCondenserTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class VisCondenserTileRenderer extends TileEntitySpecialRenderer {
    private IModelCustom model;
    private final ResourceLocation modelRL = new ResourceLocation(ThaumicConcilium.MODID+":models/vis_condenser.obj");
    private final ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/vis_condenser.png");

    public VisCondenserTileRenderer() {
        model = AdvancedModelLoader.loadModel(modelRL);

    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        VisCondenserTile te = (VisCondenserTile) tile;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.renderAll();
        GL11.glPopMatrix();
    }

}