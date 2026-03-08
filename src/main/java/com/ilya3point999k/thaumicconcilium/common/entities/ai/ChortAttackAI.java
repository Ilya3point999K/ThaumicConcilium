package com.ilya3point999k.thaumicconcilium.common.entities.ai;

import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ChortSpitEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class ChortAttackAI extends EntityAIBase {

    final EntityLiving attacker;
    EntityLivingBase target;

    int attackCooldown;

    final float attackRange = 16.0f;
    final float projectileVelocity = 1.8F;
    final float spreadDegrees = 4.0F;

    public ChortAttackAI(EntityLiving attacker) {
        this.attacker = attacker;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase possibleTarget = attacker.getAttackTarget();

        if (possibleTarget == null || !possibleTarget.isEntityAlive())
            return false;

        if (attacker.getDistanceSqToEntity(possibleTarget) > attackRange * attackRange)
            return false;

        this.target = possibleTarget;
        return true;
    }

    @Override
    public void startExecuting() {
        attackCooldown = 0;
    }

    @Override
    public boolean continueExecuting() {
        return target != null && target.isEntityAlive();
    }

    @Override
    public void updateTask() {

        attacker.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

        if (attackCooldown > 0) {
            attackCooldown--;
            return;
        }

        for (int i = -1; i < 2; i++) {
            ChortSpitEntity spit = new ChortSpitEntity(attacker.worldObj, attacker, target, spreadDegrees * i, projectileVelocity, 1.0f);
            attacker.worldObj.spawnEntityInWorld(spit);
        }

        attackCooldown = 40;
    }

}
