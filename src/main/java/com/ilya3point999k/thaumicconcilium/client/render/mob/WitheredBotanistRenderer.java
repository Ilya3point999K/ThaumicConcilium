package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.block.RedPoweredMindTileRenderer;
import com.ilya3point999k.thaumicconcilium.client.render.model.WitheredBotanistModel;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WitheredBotanistRenderer extends Render {

    public static final ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID+":textures/entity/withered_botanist.png");
    public static final WitheredBotanistModel model = new WitheredBotanistModel();
    public WitheredBotanistRenderer()
    {
        super();
    }

    @Override
    public void doRender(Entity e, double x, double y, double z, float par8, float par9)
    {

        GL11.glPushMatrix();

        GL11.glTranslated(x, y + 0.1, z);
        GL11.glRotated(90.0, 1.0, 0.0, 0.0);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render(e, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        GL11.glPopMatrix();

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return RedPoweredMindTileRenderer.texture;
    }
}