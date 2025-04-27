package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.SolidVoidTile;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class SolidVoidBlock extends Block implements ITileEntityProvider {
    public static IIcon icon;
    public SolidVoidBlock() {
        super(Material.rock);
        this.setHardness(0.7F);
        this.setResistance(1.0F);
        this.setStepSound(soundTypeStone);
        this.setBlockTextureName(ThaumicConcilium.MODID + ":space");
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return TCBlockRegistry.solidVoidID;
    }
    public TileEntity createTileEntity(World world, int metadata) {
        return new SolidVoidTile();
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return this.createTileEntity(var1, md);
    }

    public int damageDropped(int par1) {
        return par1;
    }

}
