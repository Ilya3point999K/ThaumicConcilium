package com.ilya3point999k.thaumicconcilium.common.entities.ai;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Samurai;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class SamuraiAttackAI extends EntityAIBase {
    private final Samurai attacker;
    private EntityLivingBase attackTarget;
    private final double moveSpeed;
    private final int attackCooldownDefault;
    private int attackCooldown;
    private final float maxAttackDistance;
    private int seeTime;
    private int meleeCooldown = 0;
    private int strafeTime = 0;
    private int strafeDirection = 1;

    public SamuraiAttackAI(Samurai mob, double speed, int cooldownTicks, float maxDistance) {
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
        if (target == null) {
            this.attacker.setAnger(0);
            return false;
        }
        this.attackTarget = target;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        EntityLivingBase t = this.attacker.getAttackTarget();
        if (t == null) {
            (this.attacker).setAnger(0);
            return false;
        }
        if (!t.isEntityAlive()) {
            (this.attacker).setAnger(0);
            return false;
        }
        double dSq = this.attacker.getDistanceSqToEntity(t);
        if (dSq <= (this.maxAttackDistance * this.maxAttackDistance * 2.5D)){
            return true;
        } else {
            (this.attacker).setAnger(0);
            return false;
        }
    }

    @Override
    public void startExecuting() {
        if (this.attacker.getHealth() > this.attacker.getMaxHealth() / 3) {
            this.attacker.setAnger(1);
        }
        this.seeTime = 0;
        this.attackCooldown = 0;
        this.meleeCooldown = 0;
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
        double dy = this.attacker.posY - this.attackTarget.posY;
        double dz = this.attacker.posZ - this.attackTarget.posZ;
        double distSq = dx * dx + dz * dz;

        boolean canSee = this.attacker.getEntitySenses().canSee(this.attackTarget);
        this.seeTime = canSee ? this.seeTime + 1 : 0;

        if (this.attacker.getHealth() > this.attacker.getMaxHealth() / 3) {
            if (this.attacker.getHealth() > this.attacker.getMaxHealth() / 2 && distSq > 64F && world.rand.nextInt(10) > 7) {
                this.attacker.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10, 3));
            }

            double len = Math.sqrt(dx * dx + dz * dz);
            if (len < 0.0001D) len = 0.0001D;

            double nx = -dx / len;
            double nz = -dz / len;

            this.attacker.motionX = nx * moveSpeed;
            this.attacker.motionZ = nz * moveSpeed;

            if (this.attacker.onGround) this.attacker.motionY = 0.0D;

            this.attacker.getNavigator().tryMoveToEntityLiving(this.attackTarget, moveSpeed);
            this.attacker.setAnger(1);
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
            this.attacker.worldObj.playSoundEffect(this.attacker.posX, this.attacker.posY, this.attacker.posZ, "thaumcraft:shock", 0.25F, 1.0F);
            this.attackTarget.attackEntityFrom(DamageSource.causeMobDamage(this.attacker), 2 * (((Samurai) this.attacker).getType() + 1));
            TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) this.attacker.posX, (float) (this.attacker.posY + this.attacker.height - 0.5), (float) this.attacker.posZ, (float) this.attackTarget.posX, (float) (this.attackTarget.posY + this.attackTarget.height / 2), (float) this.attackTarget.posZ, 0x6666DD, 0.02F), new NetworkRegistry.TargetPoint(world.provider.dimensionId, this.attacker.posX, this.attacker.posY, this.attacker.posZ, 32.0));

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
