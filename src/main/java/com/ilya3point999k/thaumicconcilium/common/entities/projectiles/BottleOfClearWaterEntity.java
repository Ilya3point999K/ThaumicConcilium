package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.common.lib.network.PacketHandler;

import java.util.List;

public class BottleOfClearWaterEntity extends EntityThrowable {
    public BottleOfClearWaterEntity(World world) {
        super(world);
    }

    public BottleOfClearWaterEntity(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    public BottleOfClearWaterEntity(World world, double x, double y, double z) {
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
        if(!this.worldObj.isRemote) {
            List<Entity> list = this.worldObj.getEntitiesWithinAABB(Entity.class, this.boundingBox.expand(5.0, 5.0, 5.0));
            for (Entity e : list) {
                e.extinguish();
                if(e instanceof EntityBlaze || e instanceof EntityMagmaCube){
                    e.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), 10.0f);
                }
            }
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
        }
        this.setDead();
    }
}
