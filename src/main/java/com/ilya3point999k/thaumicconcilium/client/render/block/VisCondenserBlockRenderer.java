package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.VisCondenserTile;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.block.BlockRenderer;

public class VisCondenserBlockRenderer extends BlockRenderer implements ISimpleBlockRenderingHandler {
    public VisCondenserBlockRenderer() {
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.3f, -0.6f, -0.5f);
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        VisCondenserTile tile = new VisCondenserTile();
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0, 0.0, 0.0, 0.0F);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return TCBlockRegistry.visCondenserBlockID;
    }
}