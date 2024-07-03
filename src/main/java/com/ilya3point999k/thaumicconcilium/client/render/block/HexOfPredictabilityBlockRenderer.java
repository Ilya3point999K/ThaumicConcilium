package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.HexOfPredictabilityTile;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import thaumcraft.client.renderers.block.BlockRenderer;

public class HexOfPredictabilityBlockRenderer  extends BlockRenderer implements ISimpleBlockRenderingHandler {

    public HexOfPredictabilityBlockRenderer() {
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        HexOfPredictabilityTile tile = new HexOfPredictabilityTile();
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0, 0.0, 0.0, 0.0F);

    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return TCBlockRegistry.hexOfPredictabilityID;
    }
}