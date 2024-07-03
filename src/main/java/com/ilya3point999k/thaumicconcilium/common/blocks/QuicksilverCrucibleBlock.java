package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.QuicksilverCrucibleTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.entities.EntitySpecialItem;

import java.util.List;
import java.util.Random;

public class QuicksilverCrucibleBlock extends BlockContainer {
    public IIcon[] icon = new IIcon[7];
    private int delay = 0;

    public QuicksilverCrucibleBlock() {
        super(Material.iron);
        this.setHardness(3.0F);
        this.setResistance(17.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setCreativeTab(ThaumicConcilium.tabTC);

    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {

        for(int a = 0; a <= 5; ++a) {
            this.icon[a] = ir.registerIcon("thaumcraft:crucible" + (a + 1));
        }
        this.icon[2] = ir.registerIcon(ThaumicConcilium.MODID+":quicksilver_crucible");
        this.icon[6] = ir.registerIcon(ThaumicConcilium.MODID+":quicksilver");
    }

    public IIcon getIcon(IBlockAccess iblockaccess, int i, int j, int k, int side) {
        if (side == 1) {
            return this.icon[0];
        } else if (side == 0) {
            return this.icon[1];
        } else {
            return this.icon[2];
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == 10){
            return icon[6];
        }
        return super.getIcon(side, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World w, int i, int j, int k, Random r) {
        if (r.nextInt(10) == 0) {
            TileEntity te = w.getTileEntity(i, j, k);
            if (te instanceof QuicksilverCrucibleTile && ((QuicksilverCrucibleTile)te).aspects.getAmount(Aspect.EXCHANGE) > 0) {
                w.playSound((double)i, (double)j, (double)k, ThaumicConcilium.MODID + ":melted", 1.1F + r.nextFloat() * 0.1F, 1.2F + r.nextFloat() * 0.2F, false);
            }
        }

    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
        if (!world.isRemote) {
                QuicksilverCrucibleTile tile = (QuicksilverCrucibleTile)world.getTileEntity(i, j, k);
                if (tile != null && entity instanceof EntityItem && !(entity instanceof EntitySpecialItem)) {
                    tile.attemptSmelt((EntityItem)entity);
                } else {
                    ++this.delay;
                    if (this.delay < 10) {
                        return;
                    }

                    this.delay = 0;
                    if (entity instanceof EntityLivingBase && tile != null) {
                        entity.attackEntityFrom(DamageSource.inFire, 1.0F);
                        world.playSoundEffect((double)i, (double)j, (double)k, "random.fizz", 0.4F, 2.0F + world.rand.nextFloat() * 0.4F);
                    }
                }
        }

    }

    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBoundsForItemRender();
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int rs) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof QuicksilverCrucibleTile) {
            float var10000 = (float)((QuicksilverCrucibleTile)te).aspects.visSize();
            ((QuicksilverCrucibleTile)te).getClass();
            float r = var10000 / 100.0F;
            return MathHelper.floor_float(r * 14.0F) + (((QuicksilverCrucibleTile)te).aspects.visSize() > 0 ? 1 : 0);
        }
        return 0;
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block nbid) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof QuicksilverCrucibleTile) {
            ((QuicksilverCrucibleTile)te).getBellows();
        }

        super.onNeighborBlockChange(world, x, y, z, nbid);
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return TCBlockRegistry.quicksilverCrucibleID;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new QuicksilverCrucibleTile();
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return null;
    }
}
