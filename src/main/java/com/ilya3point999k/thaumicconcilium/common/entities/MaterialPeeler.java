package com.ilya3point999k.thaumicconcilium.common.entities;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.tiles.HexOfPredictabilityTile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

public class MaterialPeeler extends Entity {
    public int innerRotation;

    public MaterialPeeler(World w) {
        super(w);
        setSize(1.0f, 1.0f);
        innerRotation = this.rand.nextInt(100000);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        ++this.innerRotation;
        if (!worldObj.isRemote) {
            if (!isDead && ticksExisted % 300 == 0) {
                worldObj.playSoundAtEntity(this, "thaumcraft:evilportal", (float) (0.15000000596046448 + rand.nextGaussian() * 0.066), (float) (0.75 + rand.nextGaussian() * 0.1));
            }
            if (ticksExisted % 5 == 0) {
                TileEntity tile = worldObj.getTileEntity(MathHelper.ceiling_double_int(posX), MathHelper.ceiling_double_int(posY - 2), MathHelper.ceiling_double_int(posZ));
                if (!(tile instanceof HexOfPredictabilityTile)) {
                    Thaumcraft.proxy.burst(this.worldObj, this.posX, this.posY, this.posZ, 2.0F);
                    this.playSound("thaumcraft:craftfail", 1.0F, 1.0F);
                    this.setDead();
                    int x = (int) (this.posX + this.worldObj.rand.nextGaussian() * 1.5);
                    int y = (int) this.posY;
                    int z = (int) (this.posZ + this.worldObj.rand.nextGaussian() * 1.5);

                    for (int a = 0; a < 10; ++a) {
                        int xx = x + (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                        int zz = z + (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                        if (this.worldObj.rand.nextBoolean() && this.worldObj.getBiomeGenForCoords(xx, zz) != ThaumcraftWorldGenerator.biomeTaint) {
                            Utils.setBiomeAt(this.worldObj, xx, zz, ThaumcraftWorldGenerator.biomeTaint);
                            if (this.worldObj.isBlockNormalCubeDefault(xx, y - 1, zz, false) && this.worldObj.getBlock(xx, y, zz).isReplaceable(this.worldObj, xx, y, zz)) {
                                this.worldObj.setBlock(xx, y, zz, ConfigBlocks.blockTaintFibres, 0, 3);
                            }
                            if (this.worldObj.isAirBlock(xx, y + 1, zz)) {
                                if (this.worldObj.rand.nextBoolean()) {
                                    this.worldObj.setBlock(xx, y + 1, zz, ConfigBlocks.blockFluxGas, 0, 3);
                                } else {
                                    this.worldObj.setBlock(xx, y + 1, zz, ConfigBlocks.blockFluxGoo, 0, 3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_) {

    }

    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if (ticksExisted % 20 == 0) {
            if (!player.worldObj.isRemote) {
                TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
                if (!capabilities.pontifexRobeToggle) {
                    capabilities.ethereal = !capabilities.ethereal;
                    capabilities.sync();
                    player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 10));
                }
            } else {
                Thaumcraft.proxy.burst(this.worldObj, player.posX, player.posY, player.posZ, 2.0F);
                player.playSound("thaumcraft:wandfail", 1.0F, 1.0F);
            }
        }
        super.onCollideWithPlayer(player);
    }

    @Override
    public void setFire(int seconds) {
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    protected void entityInit() {

    }
}
