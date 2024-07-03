package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.DestabilizedCrystalTile;
import com.ilya3point999k.thaumicconcilium.common.tiles.VisCondenserTile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class VisCondenserBlock extends Block implements ITileEntityProvider {
    public IIcon icon;

    public VisCondenserBlock() {
        super(Material.iron);
        this.setHardness(0.7F);
        this.setResistance(1.0F);
        this.setLightLevel(0.5F);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.7F, 1.0F);
        this.setStepSound(soundTypeMetal);
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        VisCondenserTile condenserTile = (VisCondenserTile) world.getTileEntity(i, j, k);
        if (condenserTile.cooldown != 0){
            for (int iter = 0; iter < 5; iter++) {
                final EntityReddustFX fx = new EntityReddustFX(world, (double) i + 0.5, (double) j + 1.0, (double) k + 0.5, 3.0F, 150.0F / 255.0F, 0.0F, 0.0F);
                FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
            }
        }
        TileEntity tileEntity = world.getTileEntity(i, j + 1, k);

        if (tileEntity != null && tileEntity instanceof DestabilizedCrystalTile) {
            DestabilizedCrystalTile tile = (DestabilizedCrystalTile) tileEntity;
            Color c;
            if (tile.aspect != null) {
                c = new Color(Aspect.aspects.get(tile.aspect).getColor());
            }
            else {
                c = new Color(0xFFFFFF);
            }

            if (random.nextInt(2) == 0)
                Thaumcraft.proxy.arcLightning(world, (double) i, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k, (double)i + 0.9, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 0.01f);
            if(random.nextInt(2) == 0)
                Thaumcraft.proxy.arcLightning(world, (double)i, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k+0.9, (double) i, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 0.01f);
            if(random.nextInt(2) == 0)
                Thaumcraft.proxy.arcLightning(world, (double) i + 0.9, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k, (double) i + 0.9, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k + 0.9, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 0.01f);
            if (random.nextInt(2) == 0)
                Thaumcraft.proxy.arcLightning(world, (double) i + 0.9, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k + 0.9, (double)i, (double)j + 1.2 + (-0.5 + random.nextDouble()), (double)k + 0.9, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 0.01f);

        }

    }

    @Override
    public boolean canProvidePower() {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        VisCondenserTile tile = (VisCondenserTile) world.getTileEntity(x, y, z);
        TileEntity tile1 = world.getTileEntity(x, y + 1, z);
        if (!(tile1 instanceof DestabilizedCrystalTile)) return;
        DestabilizedCrystalTile crystal = (DestabilizedCrystalTile) tile1;
        if (crystal.aspect == null) return;
        if (crystal.capacity == 0) return;
        if (tile.cooldown != 0) return;
        //System.out.println("TR...");
        //System.out.println("1 = " + world.isBlockProvidingPowerTo(x+1, y, z, 5));
        //System.out.println("2 = " + world.isBlockProvidingPowerTo(x-1, y, z, 4));
        //System.out.println("3 = " + world.isBlockProvidingPowerTo(x, y, z+1, 3));
       // System.out.println("4 = " + world.isBlockProvidingPowerTo(x, y, z-1, 2));

        MovingObjectPosition mop;
        int power = world.isBlockProvidingPowerTo(x+1, y, z, 5);
        if (power != 0){
            mop = world.rayTraceBlocks(Vec3.createVectorHelper(x - 1, y + 1, z), Vec3.createVectorHelper(x - power, y + 1, z), true);
            if (mop == null){
                tile.shoot(crystal, x - power, y + 1, z, power);
            } else {
                tile.shoot(crystal, mop.blockX, mop.blockY, mop.blockZ, power);
            }
            tile.cooldown = 40;
        }
        power = world.isBlockProvidingPowerTo(x-1, y, z, 4);
        if (power != 0){
            mop = world.rayTraceBlocks(Vec3.createVectorHelper(x + 1, y + 1, z), Vec3.createVectorHelper(x + power, y + 1, z), true);
            if (mop == null){
                tile.shoot(crystal, x + power, y + 1, z, power);
            } else {
                tile.shoot(crystal, mop.blockX, mop.blockY, mop.blockZ, power);
            }
            tile.cooldown = 40;

        }
        power = world.isBlockProvidingPowerTo(x, y, z+1, 3);
        if (power != 0){
            mop = world.rayTraceBlocks(Vec3.createVectorHelper(x, y + 1, z - 1), Vec3.createVectorHelper(x, y + 1, z - power), true);
            if (mop == null){
                tile.shoot(crystal, x, y + 1, z - power, power);
            } else {
                tile.shoot(crystal, mop.blockX, mop.blockY, mop.blockZ, power);
            }
            tile.cooldown = 40;

        }
        power = world.isBlockProvidingPowerTo(x, y, z-1, 2);
        if (power != 0){
            mop = world.rayTraceBlocks(Vec3.createVectorHelper(x, y + 1, z + 1), Vec3.createVectorHelper(x, y + 1, z + power), true);
            if (mop == null){
                tile.shoot(crystal, x, y + 1, z + power, power);
            } else {
                tile.shoot(crystal, mop.blockX, mop.blockY, mop.blockZ, power);
            }
            tile.cooldown = 40;
        }

        power = world.isBlockProvidingPowerTo(x, y-1, z, 0);
        if (power != 0 && crystal.capacity != 0){
            tile.sendEssentia(crystal);
        }
        tile.markDirty();
        world.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return TCBlockRegistry.visCondenserBlockID;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("minecraft:gold_block");

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.UP;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new VisCondenserTile();
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return this.createTileEntity(var1, md);
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int md, int fortune) {
        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(TCBlockRegistry.VIS_CONDENSER_BLOCK));
        return list;
    }

}
