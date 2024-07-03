package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.EntityAspectOrb;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import java.util.Iterator;
import java.util.List;

public class BottleOfThickTaintEntity extends EntityThrowable {
    public BottleOfThickTaintEntity(World world) {
        super(world);
    }

    public BottleOfThickTaintEntity(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    public BottleOfThickTaintEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected float getGravityVelocity() {
        return 0.05F;
    }
    protected float func_70182_d() {
        return 0.5F;
    }

    protected float func_70183_g() {
        return -20.0F;
    }

    @Override
    protected void onImpact(MovingObjectPosition mop) {
        if (!this.worldObj.isRemote) {
            List ents = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(5.0, 5.0, 5.0));
            if (ents.size() > 0) {
                Iterator i$ = ents.iterator();

                while(i$.hasNext()) {
                    Object ent = i$.next();
                    EntityLivingBase el = (EntityLivingBase)ent;
                    if (!(el instanceof ITaintedMob) && !el.isEntityUndead()) {
                        el.addPotionEffect(new PotionEffect(Config.potionTaintPoisonID, 100, 0, false));
                    }
                }
            }

            int x = (int)this.posX;
            int y = (int)this.posY;
            int z = (int)this.posZ;

            for(int a = 0; a < 10; ++a) {
                int xx = x + (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                int zz = z + (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                if (this.worldObj.rand.nextBoolean() && this.worldObj.getBiomeGenForCoords(xx, zz) != ThaumcraftWorldGenerator.biomeTaint) {
                    Utils.setBiomeAt(this.worldObj, xx, zz, ThaumcraftWorldGenerator.biomeTaint);
                    if (this.worldObj.isBlockNormalCubeDefault(xx, y - 1, zz, false) && this.worldObj.getBlock(xx, y, zz).isReplaceable(this.worldObj, xx, y, zz)) {
                        this.worldObj.setBlock(xx, y, zz, ConfigBlocks.blockTaintFibres, 0, 3);
                    }
                    if(this.worldObj.isAirBlock(xx, y + 1, zz)){
                        this.worldObj.setBlock(xx, y + 1, zz, ConfigBlocks.blockFluxGas, 0 ,3);
                    }
                }
            }

            this.setDead();
        } else {
            for(int a = 0; a < Thaumcraft.proxy.particleCount(100); ++a) {
                Thaumcraft.proxy.taintsplosionFX(this);
            }

            Thaumcraft.proxy.bottleTaintBreak(this.worldObj, this.posX, this.posY, this.posZ);
        }
    }
}
