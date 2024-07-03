package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.DestabilizedCrystalTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXSpark;
import thaumcraft.common.blocks.CustomStepSound;

import java.awt.*;
import java.util.Random;

public class DestabilizedCrystalBlock extends BlockContainer {
    private final Random random = new Random();
    public IIcon icon;

    public DestabilizedCrystalBlock() {
        super(Material.glass);
        this.setHardness(0.7F);
        this.setResistance(1.0F);
        this.setLightLevel(0.5F);
        this.setStepSound(new CustomStepSound("crystal", 1.0F, 1.0F));
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }

    /*
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        int size = Aspect.aspects.size();
        for(int var4 = 0; var4 < size; var4++) {
            ItemStack is = new ItemStack(par1, 1);
            par3List.add(is);
        }
        //ItemStack is = new ItemStack().setTagCompound(new NBTTagCompound().se);
    }
     */

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:crystal");
    }

    public IIcon getIcon(int par1, int par2) {
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        //int md = world.getBlockMetadata(i, j, k);
        DestabilizedCrystalTile dct = (DestabilizedCrystalTile) world.getTileEntity(i, j, k);
        if (random.nextInt(2) == 0) {
            FXSpark ef = new FXSpark(world, (double)i + 0.3 + (double)(world.rand.nextFloat() * 0.4F), (double)j + 0.3 + (double)(world.rand.nextFloat() * 0.4F), (double)k + 0.3 + (double)(world.rand.nextFloat() * 0.4F), 0.2F + random.nextFloat() * 0.1F);
            Color c;
            if (dct.aspect != null) {
                c = new Color(Aspect.aspects.get(dct.aspect).getColor());
            }
            else {
                c = new Color(0xFFFFFF);
            }
            ef.setRBGColorF((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F);
            ef.setAlphaF(0.8F);
            ParticleEngine.instance.addEffect(world, ef);
        }

    }

    public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
        int md = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
        DestabilizedCrystalTile dct = (DestabilizedCrystalTile)par1iBlockAccess.getTileEntity(par2, par3, par4);
            if(dct.aspect == null) {
                return 0xFFFFFF;
            } else return Aspect.aspects.get(dct.aspect).getColor();
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return TCBlockRegistry.destabilizedCrystalBlockID;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float hitX, float hitY, float hitZ){
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof DestabilizedCrystalTile) {
                DestabilizedCrystalTile dct = (DestabilizedCrystalTile) tile;
                if(activator.getHeldItem() != null) {
                    dct.handleInputStack(activator, activator.getHeldItem());
                }
            }
        }
        return true;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemById(0);
    }

    public TileEntity createTileEntity(World world, int metadata) {
            return new DestabilizedCrystalTile();
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return this.createTileEntity(var1, md);
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, Block l) {
        super.onNeighborBlockChange(world, i, j, k, l);
        DestabilizedCrystalTile tes;
        if (this.checkIfAttachedToBlock(world, i, j, k)) {
            tes = (DestabilizedCrystalTile) world.getTileEntity(i, j, k);
            int i1 = tes.orientation;
            boolean flag = !world.isSideSolid(i - 1, j, k, ForgeDirection.getOrientation(5)) && i1 == 5;

            if (!world.isSideSolid(i + 1, j, k, ForgeDirection.getOrientation(4)) && i1 == 4) {
                flag = true;
            }

            if (!world.isSideSolid(i, j, k - 1, ForgeDirection.getOrientation(3)) && i1 == 3) {
                flag = true;
            }

            if (!world.isSideSolid(i, j, k + 1, ForgeDirection.getOrientation(2)) && i1 == 2) {
                flag = true;
            }

            if (!world.isSideSolid(i, j - 1, k, ForgeDirection.getOrientation(1)) && i1 == 1) {
                flag = true;
            }

            if (!world.isSideSolid(i, j + 1, k, ForgeDirection.getOrientation(0)) && i1 == 0) {
                flag = true;
            }

            if (flag) {
                //this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
                world.setBlockToAir(i, j, k);
            }

        } /*else if (md == 7) {
            DestabilizedCrystalTile tes = (DestabilizedCrystalTile)world.getTileEntity(i, j, k);
            ForgeDirection fd = ForgeDirection.getOrientation(tes.orientation).getOpposite();
            if (world.isAirBlock(i + fd.offsetX, j + fd.offsetY, k + fd.offsetZ)) {
                this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
                world.setBlockToAir(i, j, k);
            }

        }*/
    }

    private boolean checkIfAttachedToBlock(World world, int i, int j, int k) {
        if (!this.canPlaceBlockAt(world, i, j, k)) {
            //this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
            return false;
        } else {
            return true;
        }
    }

    public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
        if (l == 0 && world.isSideSolid(i, j + 1, k, ForgeDirection.getOrientation(0))) {
            return true;
        } else if (l == 1 && world.isSideSolid(i, j - 1, k, ForgeDirection.getOrientation(1))) {
            return true;
        } else if (l == 2 && world.isSideSolid(i, j, k + 1, ForgeDirection.getOrientation(2))) {
            return true;
        } else if (l == 3 && world.isSideSolid(i, j, k - 1, ForgeDirection.getOrientation(3))) {
            return true;
        } else if (l == 4 && world.isSideSolid(i + 1, j, k, ForgeDirection.getOrientation(4))) {
            return true;
        } else {
            return l == 5 && world.isSideSolid(i - 1, j, k, ForgeDirection.getOrientation(5));
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        if (world.isSideSolid(i - 1, j, k, ForgeDirection.getOrientation(5))) {
            return true;
        } else if (world.isSideSolid(i + 1, j, k, ForgeDirection.getOrientation(4))) {
            return true;
        } else if (world.isSideSolid(i, j, k - 1, ForgeDirection.getOrientation(3))) {
            return true;
        } else if (world.isSideSolid(i, j, k + 1, ForgeDirection.getOrientation(2))) {
            return true;
        } else {
            return world.isSideSolid(i, j - 1, k, ForgeDirection.getOrientation(1)) || world.isSideSolid(i, j + 1, k, ForgeDirection.getOrientation(0));
        }
    }

}
