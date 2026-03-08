package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.SolidVoidTile;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SolidVoidBlock extends Block implements ITileEntityProvider{
    public static IIcon icon;
    public SolidVoidBlock() {
        super(Material.rock);
        this.setHardness(0.0F);
        this.setResistance(6000.0F);
        this.setStepSound(soundTypeStone);
        this.setBlockTextureName(":cosmos");
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {
        icon = ir.registerIcon(ThaumicConcilium.MODID + ":cosmos");
    }

    @Override
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_) {
        return icon;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return icon;
    }

    public static IIcon getIcon() {
        return icon;
    }

    public int getRenderType() {
        return TCBlockRegistry.solidVoidID;
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new SolidVoidTile();
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return this.createTileEntity(var1, md);
    }
}
