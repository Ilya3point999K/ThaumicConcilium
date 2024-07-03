package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.RedPoweredMindTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.common.lib.CustomSoundType;

import java.util.ArrayList;

public class RedPoweredMindBlock extends Block implements ITileEntityProvider {
    public IIcon icon;

    public RedPoweredMindBlock() {
        super(Material.cake);
        this.setHardness(0.7F);
        this.setResistance(1.0F);
        this.setLightLevel(0.5F);
        this.setBlockBounds(0.15F, 0.22F, 0.15F, 0.85F, 0.8F, 0.85F);
        this.setStepSound(new CustomSoundType("gore", 0.5F, 0.8F));
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }



    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return TCBlockRegistry.redPoweredMindBlockID;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:fleshblock");

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    @Override
    public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(w, x, y, z, block);
        TileEntity tile = w.getTileEntity(x, y, z);
        if (tile instanceof RedPoweredMindTile) {
            ((RedPoweredMindTile) tile).power = w.getBlockPowerInput(x, y, z);
            w.markBlockForUpdate(x, y, z);
            tile.markDirty();
        }
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new RedPoweredMindTile();
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return this.createTileEntity(var1, md);
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int md, int fortune) {
        return null;
    }

}