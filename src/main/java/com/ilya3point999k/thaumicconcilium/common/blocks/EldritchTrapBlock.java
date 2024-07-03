package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.tiles.DestabilizedCrystalTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileEldritchTrap;

public class EldritchTrapBlock extends Block {
    public IIcon icon;

    public EldritchTrapBlock() {
        super(Material.rock);
        this.setResistance(20000.0F);
        this.setHardness(50.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setLightOpacity(0);
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:es_5");
    }

    public IIcon getIcon(int par1, int par2) {
        return this.icon;
    }
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return false;
    }
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    public int getRenderType() {
        return ConfigBlocks.blockEldritchRI;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEldritchTrap();
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return this.createTileEntity(var1, md);
    }

}
