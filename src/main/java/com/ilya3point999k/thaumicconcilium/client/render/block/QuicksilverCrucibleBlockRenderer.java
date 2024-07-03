package com.ilya3point999k.thaumicconcilium.client.render.block;

import com.ilya3point999k.thaumicconcilium.common.blocks.QuicksilverCrucibleBlock;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.QuicksilverCrucibleTile;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import thaumcraft.client.renderers.block.BlockRenderer;

public class QuicksilverCrucibleBlockRenderer extends BlockRenderer implements ISimpleBlockRenderingHandler {
    public QuicksilverCrucibleBlockRenderer() {
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        drawFaces(renderer, block, ((QuicksilverCrucibleBlock)block).icon[1], ((QuicksilverCrucibleBlock)block).icon[3], ((QuicksilverCrucibleBlock)block).icon[2], ((QuicksilverCrucibleBlock)block).icon[2], ((QuicksilverCrucibleBlock)block).icon[2], ((QuicksilverCrucibleBlock)block).icon[2], true);
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        IIcon innerSide = ((QuicksilverCrucibleBlock)block).icon[4];
        IIcon bottom = ((QuicksilverCrucibleBlock)block).icon[5];
        float f5 = 0.123F;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof QuicksilverCrucibleTile) {
            setBrightness(world, x, y, z, block);
        }

        renderer.renderFaceXPos(block, (double)((float)x - 1.0F + f5), (double)y, (double)z, innerSide);
        renderer.renderFaceXNeg(block, (double)((float)x + 1.0F - f5), (double)y, (double)z, innerSide);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - 1.0F + f5), innerSide);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z + 1.0F - f5), innerSide);
        renderer.renderFaceYPos(block, (double)x, (double)((float)y - 1.0F + 0.25F), (double)z, bottom);
        renderer.renderFaceYNeg(block, (double)x, (double)((float)y + 1.0F - 0.75F), (double)z, bottom);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return TCBlockRegistry.quicksilverCrucibleID;
    }
}
