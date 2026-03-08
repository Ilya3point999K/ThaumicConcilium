package com.ilya3point999k.thaumicconcilium.common.entities.ai;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import thaumcraft.common.entities.projectile.EntityPrimalArrow;

public class CrimsonArcherAttackAI extends EntityAIBase {
    private final EntityLiving attacker;
    private EntityLivingBase attackTarget;
    private final double moveSpeed;
    private final int attackCooldownDefault;
    private int attackCooldown;
    private final float maxAttackDistance;
    private int seeTime;
    private int meleeCooldown = 0;
    private int strafeTime = 0;
    private int strafeDirection = 1;

    public CrimsonArcherAttackAI(EntityLiving mob, double speed, int cooldownTicks, float maxDistance) {
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
        this.meleeCooldown = 0;
    }

    @Override
    public void resetTask() {
        this.attackCooldown = 0;
        this.attackTarget = null;
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
        double dy = this.attacker.posY - this.attackTarget.posY;
        double dz = this.attacker.posZ - this.attackTarget.posZ;
        double distSq = dx * dx + dz * dz;

        boolean canSee = this.attacker.getEntitySenses().canSee(this.attackTarget);
        this.seeTime = canSee ? this.seeTime + 1 : 0;

        if (!hasRangedWeapon()) {
            double len = Math.sqrt(dx * dx + dz * dz);
            if (len < 0.0001D) len = 0.0001D;

            double nx = -dx / len;
            double nz = -dz / len;

            this.attacker.motionX = nx * moveSpeed;
            this.attacker.motionZ = nz * moveSpeed;
            if (this.attacker.onGround) this.attacker.motionY = 0.0D;

            this.attacker.getNavigator().tryMoveToEntityLiving(this.attackTarget, moveSpeed);

            if (meleeCooldown > 0) meleeCooldown--;
            if (distSq <= 4 && (dy * dy) <= 4 && meleeCooldown <= 0) {
                this.attacker.swingItem();
                this.attacker.attackEntityAsMob(this.attackTarget);
                meleeCooldown = 20;
            }
            return;
        }

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
            EntityPrimalArrow entityarrow = new EntityPrimalArrow(this.attacker.worldObj, this.attacker, 0.8F, world.rand.nextInt(6));
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.attacker.getHeldItem());
            int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.attacker.getHeldItem());
            entityarrow.setDamage((double)(2.0 + world.rand.nextInt(8)));

            if (i > 0) {
                entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
            }

            if (j > 0) {
                entityarrow.setKnockbackStrength(j);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.attacker.getHeldItem()) > 0) {
                entityarrow.setFire(100);
            }
            double tx = this.attackTarget.posX + this.attackTarget.motionX - this.attacker.posX;
            double ty = this.attackTarget.posY - this.attacker.posY;
            double tz = this.attackTarget.posZ + this.attackTarget.motionZ - this.attacker.posZ;

            entityarrow.setThrowableHeading(tx, ty, tz, 2.5F, 1.5F);

            this.attacker.playSound("random.bow", 1.0F, 1.0F / (this.attacker.getRNG().nextFloat() * 0.4F + 0.8F));

            world.spawnEntityInWorld(entityarrow);

            attackCooldown = 0;
        }
    }

    private boolean hasRangedWeapon() {
        return attacker.getHeldItem() != null;
    }

    private float normalizeAngle(float angle) {
        while (angle >= 180.0F) angle -= 360.0F;
        while (angle < -180.0F) angle += 360.0F;
        return angle;
    }
}