package com.ilya3point999k.thaumicconcilium.client.render.mob;

import com.ilya3point999k.thaumicconcilium.client.render.model.NetherExplorerModel;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse.NetherExplorer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class NetherExplorerRenderer extends Render {

    public static final ResourceLocation texture = new ResourceLocation(ThaumicConcilium.MODID+":textures/entity/nether_explorer.png");
    public static final NetherExplorerModel model = new NetherExplorerModel();
    public NetherExplorerRenderer()
    {
        super();
    }

    @Override
    public void doRender(Entity e, double x, double y, double z, float par8, float par9)
    {
        NetherExplorer explorer = (NetherExplorer) e;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glTranslated(x, y + 0.1, z);
        GL11.glRotated(explorer.rotationYaw, 0.0, 1.0, 0.0);
        GL11.glRotated(90.0, 1.0, 0.0, 0.0);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render(e, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return texture;
    }
}