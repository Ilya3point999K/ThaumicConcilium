package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumic.tinkerer.common.ThaumicTinkerer;

public class Shadowbeam extends EntityThrowable {

    private int initialOffset = 2;
    private int length = 298;
    private int maxTicks = initialOffset + length;
    private int size = 4;

    private int potency;
    private Vector3 movementVector;

    private EntityLivingBase player;

    public Shadowbeam(World world, EntityLivingBase player, int potency) {
        super(world, player);
        this.potency = potency;
        this.player = player;
        setProjectileVelocity(motionX / 10, motionY / 10, motionZ / 10);
        movementVector = new Vector3(motionX, motionY, motionZ);

    }

    // Copy of setVelocity, because that is client only for some reason
    public void setProjectileVelocity(double par1, double par3, double par5) {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, (double) f) * 180.0D / Math.PI);
        }
    }

    @Override
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
        super.setThrowableHeading(par1, par3, par5, par7, par8);
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= f2;
        par3 /= f2;
        par5 /= f2;
        par1 += 0.007499999832361937 * par8;
        par3 += 0.007499999832361937 * par8;
        par5 += 0.007499999832361937 * par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        motionX = par1;
        motionY = par3;
        motionZ = par5;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition == null)
            return;

        if (movingobjectposition.entityHit != null) {
            if (movingobjectposition.entityHit != getThrower() && !movingobjectposition.entityHit.worldObj.isRemote)
                movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(getThrower()).setMagicDamage(), 8 + potency);
            return;
        }

        Vector3 movementVec = new Vector3(motionX, motionY, motionZ);
        ForgeDirection dir = ForgeDirection.getOrientation(movingobjectposition.sideHit);
        Vector3 normalVector = new Vector3(dir.offsetX, dir.offsetY, dir.offsetZ).normalize();

        movementVector = normalVector.multiply(-2 * movementVec.dotProduct(normalVector)).add(movementVec);

        motionX = movementVector.x;
        motionY = movementVector.y;
        motionZ = movementVector.z;
    }

    @Override
    public void onUpdate() {
        motionX = movementVector.x;
        motionY = movementVector.y;
        motionZ = movementVector.z;

        super.onUpdate();

        if (ticksExisted > initialOffset)
            ThaumicTinkerer.proxy.shadowSparkle(worldObj, (float) posX, (float) posY, (float) posZ, size);

        ++ticksExisted;

        if (ticksExisted >= maxTicks)
            setDead();
    }

    public void updateUntilDead() {
        while (!isDead)
            onUpdate();
    }

    @Override
    protected float getGravityVelocity() {
        return 0F;
    }
}
