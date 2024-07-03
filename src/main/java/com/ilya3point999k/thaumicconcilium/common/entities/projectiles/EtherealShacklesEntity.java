package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EtherealShacklesEntity extends EntityThrowable {
    public EtherealShacklesEntity(World world) {
        super(world);
    }

    public EtherealShacklesEntity(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    public EtherealShacklesEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected float getGravityVelocity() {
        return 0.0F;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > 100) {
            this.setDead();
        }

    }

    @Override
    protected void onImpact(MovingObjectPosition mop) {
        if (mop.entityHit instanceof EntityPlayer) {
            if (!this.worldObj.isRemote) {
                TCPlayerCapabilities capabilities = TCPlayerCapabilities.get((EntityPlayer) mop.entityHit);
                capabilities.chainedTime = 100;
                capabilities.sync();
                worldObj.playSoundAtEntity(mop.entityHit, ThaumicConcilium.MODID+":shackles", 0.9F, 0.9F);
            }
        } else if (mop.entityHit instanceof EntityLiving){
            ((EntityLivingBase) mop.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 4));
        }
        this.setDead();
    }
}
