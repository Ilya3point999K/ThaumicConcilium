package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.HexOfPredictabilityTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.config.ConfigBlocks;

import java.util.Random;

public class HexOfPredictabilityBlock extends Block implements ITileEntityProvider {
    public IIcon icon;

    public HexOfPredictabilityBlock() {
        super(Material.rock);
        this.setHardness(0.7F);
        this.setResistance(100.0F);
        this.setLightLevel(0.5F);
        this.setStepSound(soundTypeStone);
        //this.setCreativeTab(ThaumicConcilium.tabTC);
    }


    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return TCBlockRegistry.hexOfPredictabilityID;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:es_1");

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icon;
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                world.notifyBlocksOfNeighborChange(x + xx, y, z + zz, this);
                if (!world.isRemote ) {
                    TileEntity tile = world.getTileEntity(x + xx, y, z + zz);
                    if (tile instanceof HexOfPredictabilityTile) {
                        if (((HexOfPredictabilityTile) tile).isMaster) {
                            restoreBlocks(world, x, y, z);
                        }
                    }
                }
            }
        }
        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    @Override
    public Item getItemDropped(int meta, Random r, int p_149650_3_) {
        return Item.getItemById(0);
    }

    private void restoreBlocks(World w, int x, int y, int z) {
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                Block block = w.getBlock(x + xx, y, z + zz);
                if (block == this) {
                    w.setBlock(x + xx, y, z + zz, ConfigBlocks.blockCosmeticSolid, 11, 3);
                    w.notifyBlocksOfNeighborChange(x + xx, y, z + zz, w.getBlock(x + xx, y, z + zz));
                    w.markBlockForUpdate(x + xx, y, z + zz);
                }
            }
        }
        TileEntity tile = w.getTileEntity(x, y, z);
        if (tile instanceof HexOfPredictabilityTile) {
            if (!w.isRemote && ((HexOfPredictabilityTile) tile).isMaster) {
                w.setBlock(x, y, z, Blocks.bedrock);
            }
        }


    }

    @Override
    public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
        /*
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1 && (zz != 0 || xx != 0); ++zz) {
                Block b = w.getBlock(x + xx, y, z + zz);
                if (block != this) {
                    this.restoreBlocks(w, x, y, z);
                    w.setBlockToAir(x, y, z);
                    w.notifyBlocksOfNeighborChange(x, y, z, w.getBlock(x, y, z));
                    w.markBlockForUpdate(x, y, z);
                    return;
                }
            }
        }

         */
    }

    public static boolean checkStructure(World w, int x, int y, int z){
        int count = 0;
        boolean flag = false;
        for (int xx = -2; xx <= 2; ++xx) {
            for (int zz = -2; zz <= 2; ++zz) {
                Block b = w.getBlock(x + xx, y, z + zz);
                if(w.getTileEntity(x + xx, y, z + zz) instanceof HexOfPredictabilityTile){
                    flag = true;
                    break;
                }
                if (b == ConfigBlocks.blockCosmeticSolid && w.getBlockMetadata(x + xx, y, z + zz) == 11 ){
                    count++;
                }
            }
        }
        return count == 8 && !flag;
    }

    public static boolean checkTiles(World w, int x, int y, int z){
        int count = 0;
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                if (xx == 0 && zz == 0) continue;
                TileEntity tile = w.getTileEntity(x + xx, y, z + zz);
                if (tile instanceof HexOfPredictabilityTile && !((HexOfPredictabilityTile) tile).isMaster){
                    count++;
                }
            }
        }
        if (count == 8){
            for (int xx = -1; xx <= 1; ++xx) {
                for (int zz = -1; zz <= 1; ++zz) {
                    if (xx == 0 && zz == 0) continue;
                    TileEntity tile = w.getTileEntity(x + xx, y, z + zz);
                    if (tile instanceof HexOfPredictabilityTile && !((HexOfPredictabilityTile) tile).isSlave){
                        ((HexOfPredictabilityTile) tile).isSlave = true;
                        w.markBlockForUpdate(x + xx, y, z + zz);
                        tile.markDirty();
                    }
                }
            }
        }
        return count == 8;

    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.UP;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float hitX, float hitY, float hitZ){

        return true;
    }
    public TileEntity createTileEntity(World world, int metadata) {
        return new HexOfPredictabilityTile();
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return this.createTileEntity(var1, md);
    }

    public int damageDropped(int par1) {
        return par1;
    }

}