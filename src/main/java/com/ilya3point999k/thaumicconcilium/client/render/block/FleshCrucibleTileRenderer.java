package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.FleshCrucibleTile;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.lib.UtilsFX;

public class FleshCrucibleTileRenderer extends TileEntitySpecialRenderer {
    public FleshCrucibleTileRenderer() {
    }

    public void renderEntityAt(FleshCrucibleTile cr, double x, double y, double z, float fq) {
        this.renderFluid(cr, x, y, z);
    }

    public void renderFluid(FleshCrucibleTile cr, double x, double y, double z) {
        IIcon icon = TCBlockRegistry.FLESH_CRUCIBLE.getIcon(0, 10);
        GL11.glPushMatrix();

        GL11.glTranslated(x, y + (0.3 + (double)cr.aspects.getAmount(Aspect.FLESH) / 100.0), z + 1.0);
        GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
        if (cr.aspects.getAmount(Aspect.FLESH) > 0) {
            UtilsFX.renderQuadFromIcon(true, icon, 1.0F, 1.0F, 1.0F, 1.0F, TCBlockRegistry.FLESH_CRUCIBLE.getMixedBrightnessForBlock(cr.getWorldObj(), cr.xCoord, cr.yCoord, cr.zCoord), GL11.GL_ONE_MINUS_SRC_ALPHA, 1.0F);
        }

        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity te, double d, double d1, double d2, float f) {
        this.renderEntityAt((FleshCrucibleTile) te, d, d1, d2, f);
    }
}
