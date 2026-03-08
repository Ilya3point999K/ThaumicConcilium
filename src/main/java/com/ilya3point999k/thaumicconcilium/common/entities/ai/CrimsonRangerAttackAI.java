package com.ilya3point999k.thaumicconcilium.common.entities.ai;

import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ShardPowderEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CrimsonRangerAttackAI extends EntityAIBase {
    private final EntityLiving attacker;
    private EntityLivingBase attackTarget;
    private final double moveSpeed;
    private final int attackCooldownDefault;
    private int attackCooldown;
    private final float maxAttackDistance;
    private int seeTime;
    private int strafeTime = 0;
    private int strafeDirection = 1;

    public CrimsonRangerAttackAI(EntityLiving mob, double speed, int cooldownTicks, float maxDistance) {
        this.attacker = mob;
        this.moveSpeed = speed;
        this.attackCooldownDefault = cooldownTicks;
        this.attackCooldown = 0;
        this.maxAttackDistance = maxDistance;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.attacker.getAttackTarget();
        if (target == null) return false;
        this.attackTarget = target;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        EntityLivingBase t = this.attacker.getAttackTarget();
        if (t == null) return false;
        if (!t.isEntityAlive()) return false;
        double dSq = this.attacker.getDistanceSqToEntity(t);
        return dSq <= (this.maxAttackDistance * this.maxAttackDistance * 2.5D);
    }

    @Override
    public void startExecuting() {
        this.seeTime = 0;
        this.attackCooldown = 0;
    }

    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.attackCooldown = 0;
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        if (this.attackTarget == null) return;

        World world = this.attacker.worldObj;

        double dxLook = this.attackTarget.posX - this.attacker.posX;
        double dzLook = this.attackTarget.posZ - this.attacker.posZ;
        float desiredYaw = (float)(Math.atan2(dzLook, dxLook) * 180.0D / Math.PI) - 90.0F;
        this.attacker.rotationYaw = this.attacker.prevRotationYaw = normalizeAngle(desiredYaw);
        this.attacker.rotationYawHead = this.attacker.renderYawOffset = this.attacker.rotationYaw;

        double dx = this.attacker.posX - this.attackTarget.posX;
        double dz = this.attacker.posZ - this.attackTarget.posZ;
        double distSq = dx * dx + dz * dz;

        boolean canSee = this.attacker.getEntitySenses().canSee(this.attackTarget);
        this.seeTime = canSee ? this.seeTime + 1 : 0;

        double maxDistSq = maxAttackDistance * maxAttackDistance;

        if (distSq < maxDistSq * 0.85D) {
            double len = Math.sqrt(dx * dx + dz * dz);
            if (len < 0.0001D) len = 0.0001D;

            double nx = dx / len;
            double nz = dz / len;

            double sx = -nz;
            double sz = nx;

            if (strafeTime <= 0) {
                strafeTime = 40;
                strafeDirection = this.attacker.getRNG().nextBoolean() ? 1 : -1;
            } else {
                strafeTime--;
            }

            this.attacker.motionX = nx * moveSpeed + sx * 0.1 * strafeDirection;
            this.attacker.motionZ = nz * moveSpeed + sz * 0.1 * strafeDirection;

            if (this.attacker.onGround) this.attacker.motionY = 0.0D;

            this.attacker.getNavigator().clearPathEntity();
        } else {
            double len = Math.sqrt(dx * dx + dz * dz);
            double nx = dx / len;
            double nz = dz / len;

            this.attacker.getNavigator().tryMoveToXYZ(
                    this.attacker.posX + nx * 6.0D,
                    this.attacker.posY,
                    this.attacker.posZ + nz * 6.0D,
                    moveSpeed
            );
        }

        if (attackCooldown < attackCooldownDefault) {
            attackCooldown++;
        }

        if (seeTime > 0 && distSq <= maxDistSq && attackCooldown >= attackCooldownDefault) {
            world.playSoundAtEntity(this.attacker, "thaumcraft:ice", 0.3F, 0.8F + world.rand.nextFloat() * 0.1F);
            Vec3 look = this.attacker.getLookVec();
            int orbs = 4 + world.rand.nextInt(6);
            for (int i = 0; i < orbs; i++) {
                ShardPowderEntity orb = new ShardPowderEntity(this.attacker, this.attacker.posX + (look.xCoord), this.attacker.posY + this.attacker.getEyeHeight() + (look.yCoord), this.attacker.posZ + (look.zCoord), world.rand.nextInt(2), 20);
                orb.posX -= (double) (Math.cos(this.attacker.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                orb.posY -= 0.5;
                orb.posZ -= (double) (Math.sin(this.attacker.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                orb.setPosition(orb.posX, orb.posY, orb.posZ);
                orb.yOffset = 0.0F;
                float ff = 0.4F;
                orb.motionX = (double) (-MathHelper.sin(this.attacker.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.attacker.rotationPitch / 180.0F * (float) Math.PI) * ff);
                orb.motionZ = (double) (MathHelper.cos(this.attacker.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.attacker.rotationPitch / 180.0F * (float) Math.PI) * ff);
                orb.motionY = (double) (-MathHelper.sin((this.attacker.rotationPitch) / 180.0F * (float) Math.PI) * ff);
                float f2 = MathHelper.sqrt_double(orb.motionX * orb.motionX + orb.motionY * orb.motionY + orb.motionZ * orb.motionZ);
                orb.motionX /= f2;
                orb.motionY /= f2;
                orb.motionZ /= f2;
                orb.motionX += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double) 12;
                orb.motionY += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double) 12;
                orb.motionZ += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double) 12;
                world.spawnEntityInWorld(orb);
            }

            attackCooldown = 0;
        }
    }

    private float normalizeAngle(float angle) {
        while (angle >= 180.0F) angle -= 360.0F;
        while (angle < -180.0F) angle += 360.0F;
        return angle;
    }
}
