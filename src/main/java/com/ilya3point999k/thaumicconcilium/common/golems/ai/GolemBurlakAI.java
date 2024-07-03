package com.ilya3point999k.thaumicconcilium.common.golems.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityMinecart;
import thaumcraft.common.entities.golems.EntityGolemBase;

public class GolemBurlakAI extends EntityAIBase {
    private EntityGolemBase golem;

    public GolemBurlakAI(EntityGolemBase golem){
        this.golem = golem;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        Entity riding = golem.ridingEntity;
        if (riding != null){
            if (riding instanceof EntityMinecart){
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        Entity riding = golem.ridingEntity;
        if (riding != null){
            if (riding instanceof EntityMinecart){
                if (golem.ticksExisted % 10 == 0) {
                    //((EntityMinecart) riding).moveMinecartOnRail(MathHelper.floor_double(riding.posX), MathHelper.floor_double(riding.posY) - 1, MathHelper.floor_double(riding.posZ), 2.0);
                    //riding.addVelocity(golem.getToggles()[0] ? 0.5 : -0.5, 0.0, golem.getToggles()[1] ? 0.5 : -0.5);
                }
            }
        }
    }

    @Override
    public boolean continueExecuting() {
        Entity riding = golem.ridingEntity;
        if (riding != null){
            if (riding instanceof EntityMinecart){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateTask() {
        Entity riding = golem.ridingEntity;
        if (riding != null){
            if (riding instanceof EntityMinecart){
                if (golem.ticksExisted % 10 == 0) {
                    riding.motionX *= 1.4;
                    riding.motionZ *= 1.4;
                    //riding.addVelocity(golem.getToggles()[0] ? 0.5 : -0.5, 0.0, golem.getToggles()[1] ? 0.5 : -0.5);
                }
            }
        }
    }
}
