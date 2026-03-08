package com.ilya3point999k.thaumicconcilium.common.entities.ai;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Chort;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class SloppyAlchemistAttackAI extends EntityAIBase {

    private final EntityLiving attacker;
    private EntityLivingBase target;

    private final double evadeSpeed;
    private final double ramSpeed;
    private final float maxDistance;

    private int strafeTime = 0;
    private int strafeDirection = 1;
    private final double strafeFactor = 0.1D;

    private int chargeTime = 0;
    private final int chargeInterval;
    private boolean isRamming = false;
    private boolean isWindingUp = false;

    private int windupTicks = 0;
    private static final int WINDUP_DURATION = 12;

    private boolean ramHit = false;
    private double ramTargetX;
    private double ramTargetZ;

    private static final double KNOCKBACK_HORIZONTAL = 2.5D;
    private static final double KNOCKBACK_VERTICAL = 0.65D;

    private static final double RAM_OVERSHOOT_DISTANCE = 3.0D;

    public SloppyAlchemistAttackAI(EntityLiving mob,
                                       double evadeSpeed,
                                       double ramSpeed,
                                       int chargeInterval,
                                       float maxDistance) {
        this.attacker = mob;
        this.evadeSpeed = evadeSpeed;
        this.ramSpeed = ramSpeed;
        this.chargeInterval = chargeInterval;
        this.maxDistance = maxDistance;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (!(this.attacker.riddenByEntity instanceof Chort)) return false;
        EntityLivingBase t = attacker.getAttackTarget();
        if (t == null) return false;
        target = t;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        return target != null && target.isEntityAlive() && (this.attacker.riddenByEntity instanceof Chort);
    }

    @Override
    public void resetTask() {
        target = null;
        isRamming = false;
        isWindingUp = false;
        ramHit = false;
        chargeTime = 0;
        windupTicks = 0;
        attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        if (target == null) return;
        if(!(this.attacker.riddenByEntity instanceof Chort)) return;

        World world = attacker.worldObj;

        double lx = target.posX - attacker.posX;
        double lz = target.posZ - attacker.posZ;
        float yaw = (float)(Math.atan2(lz, lx) * 180.0D / Math.PI) - 90.0F;
        attacker.rotationYaw = attacker.prevRotationYaw = yaw;
        attacker.rotationYawHead = attacker.renderYawOffset = yaw;

        double dx = attacker.posX - target.posX;
        double sqdy = attacker.posY - target.posY;
        sqdy = sqdy * sqdy;
        double dz = attacker.posZ - target.posZ;
        double distSq = dx * dx + dz * dz;

        if (isWindingUp) {
            windupTicks++;

            attacker.motionX = 0.0D;
            attacker.motionZ = 0.0D;

            if (windupTicks == 1 && attacker.onGround) {
                attacker.motionY = 0.45D;
            }

            if (windupTicks >= WINDUP_DURATION) {
                isWindingUp = false;
                isRamming = true;
                ramHit = false;

                double rx = target.posX - attacker.posX;
                double rz = target.posZ - attacker.posZ;
                double len = Math.sqrt(rx * rx + rz * rz);
                if (len < 0.0001D) len = 0.0001D;

                double nx = rx / len;
                double nz = rz / len;

                ramTargetX = target.posX + nx * RAM_OVERSHOOT_DISTANCE;
                ramTargetZ = target.posZ + nz * RAM_OVERSHOOT_DISTANCE;
            }
            return;
        }

        if (isRamming) {
            double rx = ramTargetX - attacker.posX;
            double rz = ramTargetZ - attacker.posZ;
            double len = Math.sqrt(rx * rx + rz * rz);
            if (len < 0.0001D) len = 0.0001D;

            attacker.motionX = (rx / len) * ramSpeed;
            attacker.motionZ = (rz / len) * ramSpeed;

            if (!ramHit && distSq < 2.2D && sqdy < 2.2D) {
                attacker.attackEntityAsMob(target);

                double kx = target.posX - attacker.posX;
                double kz = target.posZ - attacker.posZ;
                double klen = Math.sqrt(kx * kx + kz * kz);
                if (klen > 0.0D) {
                    target.addVelocity(
                            (kx / klen) * KNOCKBACK_HORIZONTAL,
                            KNOCKBACK_VERTICAL,
                            (kz / klen) * KNOCKBACK_HORIZONTAL
                    );
                }

                ramHit = true;
            }

            if (ramHit || len < 0.6D) {
                isRamming = false;
                chargeTime = 0;
            }
            return;
        }

        if (distSq < maxDistance * maxDistance) {
            double len = Math.sqrt(dx * dx + dz * dz);
            if (len < 0.0001D) len = 0.0001D;

            double bx = dx / len;
            double bz = dz / len;

            double sx = -bz;
            double sz = bx;

            if (strafeTime <= 0) {
                strafeTime = 30;
                strafeDirection = attacker.getRNG().nextBoolean() ? 1 : -1;
            } else {
                strafeTime--;
            }

            attacker.motionX =
                    bx * evadeSpeed +
                            sx * strafeFactor * strafeDirection;
            attacker.motionZ =
                    bz * evadeSpeed +
                            sz * strafeFactor * strafeDirection;

            attacker.getNavigator().clearPathEntity();
        }

        if (attacker.getEntitySenses().canSee(target)) {
            chargeTime++;
        } else {
            chargeTime = 0;
        }

        if (chargeTime >= chargeInterval) {
            isWindingUp = true;
            windupTicks = 0;
            chargeTime = 0;
        }
    }
}
