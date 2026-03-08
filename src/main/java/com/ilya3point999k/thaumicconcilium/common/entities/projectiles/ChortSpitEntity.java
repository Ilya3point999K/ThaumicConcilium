package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ChortSpitEntity extends EntityThrowable {

    public ChortSpitEntity(World w){
        super(w);
    }

    public ChortSpitEntity(World w, EntityLivingBase thrower) {
        super(w, thrower);
    }

    public ChortSpitEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public ChortSpitEntity(World world, EntityLivingBase thrower, EntityLivingBase target, float yawOffsetDeg, float velocity, float inaccuracy) {
        super(world, thrower);

        if (target != null && thrower != null) {
            double dx = target.posX - thrower.posX;
            double dy = (target.posY + target.getEyeHeight()) - (thrower.posY + thrower.getEyeHeight());
            double dz = target.posZ - thrower.posZ;

            double yawRad = Math.toRadians(yawOffsetDeg);
            double cos = Math.cos(yawRad);
            double sin = Math.sin(yawRad);
            double rx = dx * cos - dz * sin;
            double rz = dx * sin + dz * cos;

            this.setThrowableHeading(rx, dy, rz, velocity, inaccuracy);
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.01F;
    }

    @Override
    protected void onImpact(MovingObjectPosition mop) {
        EntityLivingBase thrower = getThrower();
        if (mop.entityHit != null && thrower != null && mop.entityHit != thrower.ridingEntity && mop.entityHit != thrower) {
            double var2 = thrower.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();

            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), (float)var2);
        }
        worldObj.playSoundAtEntity(this, "mob.slime.small", 0.5F, 1.0F);

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
