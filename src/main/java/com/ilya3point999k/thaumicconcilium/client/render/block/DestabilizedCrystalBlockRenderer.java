package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.DestabilizedCrystalTile;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.block.BlockRenderer;

public class DestabilizedCrystalBlockRenderer extends BlockRenderer implements ISimpleBlockRenderingHandler {
    public DestabilizedCrystalBlockRenderer() {
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
            GL11.glScalef(3.0F, 3.0F, 3.0F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.2F, -0.5F);
            DestabilizedCrystalTile tc = new DestabilizedCrystalTile();
            tc.blockMetadata = metadata;
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tc, 0.0, 0.0, 0.0, 0.0F);
            GL11.glEnable(32826);

        /*if (metadata == 7) {
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEldritchCrystal(), 0.0, 0.0, 0.0, 0.0F);
            GL11.glEnable(32826);
        }*/

    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return TCBlockRegistry.destabilizedCrystalBlockID;
    }
}
