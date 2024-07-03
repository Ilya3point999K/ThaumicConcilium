package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.tiles.LithographerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class LithographerTileRenderer extends TileEntitySpecialRenderer {
    private IModelCustom baseModel;
    private IModelCustom sector1Model;
    private IModelCustom sector2Model;
    private IModelCustom sector3Model;
    private IModelCustom sector4Model;

    private final ResourceLocation baseModelRL = new ResourceLocation(ThaumicConcilium.MODID+":models/lithographer.obj");
    private final ResourceLocation sector1ModelRL = new ResourceLocation(ThaumicConcilium.MODID+":models/lithographer1.obj");
    private final ResourceLocation sector2ModelRL = new ResourceLocation(ThaumicConcilium.MODID+":models/lithographer2.obj");
    private final ResourceLocation sector3ModelRL = new ResourceLocation(ThaumicConcilium.MODID+":models/lithographer3.obj");
    private final ResourceLocation sector4ModelRL = new ResourceLocation(ThaumicConcilium.MODID+":models/lithographer4.obj");

    private final ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID+":textures/models/lithographer.png");

    public LithographerTileRenderer() {
        baseModel = AdvancedModelLoader.loadModel(baseModelRL);
        sector1Model = AdvancedModelLoader.loadModel(sector1ModelRL);
        sector2Model = AdvancedModelLoader.loadModel(sector2ModelRL);
        sector3Model = AdvancedModelLoader.loadModel(sector3ModelRL);
        sector4Model = AdvancedModelLoader.loadModel(sector4ModelRL);
    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        LithographerTile lgr = (LithographerTile) tile;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
        if (!lgr.orientation){
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        }
        //GL11.glScalef(1.0F, 1.5F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        baseModel.renderAll();
        sector1Model.renderAll();
        switch (lgr.state) {
            case 0:
                break;
            case 1: {
                if (lgr.deploy != 0) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, (lgr.deploy / 150.0F), 0.0F);
                    sector2Model.renderAll();
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, (lgr.deploy / 150.0F) * 3.0F, 0.0F);
                    sector3Model.renderAll();
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, (lgr.deploy / 150.0F) * 5.0F, 0.0F);
                    sector4Model.renderAll();
                    GL11.glPopMatrix();

                }
                break;
            }
            case 2:{
                sector2Model.renderAll();
                sector3Model.renderAll();
                sector4Model.renderAll();
                break;
            }
            case 3:{
                if (lgr.deploy != 0) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, ((20 + lgr.deploy) / 150.0F), 0.0F);
                    sector2Model.renderAll();
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, ((20 + lgr.deploy) / 150.0F) * 3.0F, 0.0F);
                    sector3Model.renderAll();
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, ((20 + lgr.deploy) / 150.0F) * 5.0F, 0.0F);
                    sector4Model.renderAll();
                    GL11.glPopMatrix();

                }
                break;
            }
        }
        //sector3Model.renderAll();
        //sector4Model.renderAll();


        GL11.glPopMatrix();
    }
    private float lerp(float min, float max, float val){
        return (1.0F - val) * min + val * max;
    }
}