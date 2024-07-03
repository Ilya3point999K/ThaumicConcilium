package com.ilya3point999k.thaumicconcilium.common.golems.ai;

import fox.spiteful.forbidden.compat.Compat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.common.blocks.BlockJarItem;
import thaumcraft.common.entities.golems.EntityGolemBase;

public class GolemAssistantAI extends EntityAIBase {
    World worldObj;
    EntityGolemBase theGolem;
    EntityLivingBase entityTarget;
    int attackTick = 0;
    PathEntity entityPathEntity;
    private int counter;
    Item jarItem;

    public GolemAssistantAI(EntityGolemBase par1EntityLiving) {
        this.theGolem = par1EntityLiving;
        this.worldObj = par1EntityLiving.worldObj;
        this.setMutexBits(3);
        try {
            this.jarItem = Compat.getItem("ThaumicHorizons", "soulJar");
        } catch (Compat.ItemNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean shouldExecute() {
        EntityLivingBase var1 = this.theGolem.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (theGolem.getCarried() == null) {
            return false;
        }
        if (!(theGolem.getCarried().getItem() instanceof BlockJarItem)) {
            return false;
        }
        /*else if (!isValidTarget(var1)) {
            this.theGolem.setAttackTarget((EntityLivingBase)null);
            return false;
        }*/
        else {
            this.entityTarget = var1;
            this.entityPathEntity = this.theGolem.getNavigator().getPathToEntityLiving(this.entityTarget);
            return this.entityPathEntity != null;
        }
    }

    public boolean continueExecuting() {
        return this.shouldExecute() && !this.theGolem.getNavigator().noPath() && this.theGolem.itemCarried != null;
    }

    public void startExecuting() {
        this.theGolem.getNavigator().setPath(this.entityPathEntity, (double) this.theGolem.getAIMoveSpeed());
        this.counter = 0;
    }

    public void resetTask() {
        this.entityTarget = null;
        this.theGolem.getNavigator().clearPathEntity();
    }

    public void updateTask() {
        this.theGolem.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
        if (this.theGolem.getEntitySenses().canSee(this.entityTarget) && --this.counter <= 0) {
            this.counter = 4 + this.theGolem.getRNG().nextInt(7);
            this.theGolem.getNavigator().tryMoveToEntityLiving(this.entityTarget, (double) this.theGolem.getAIMoveSpeed());
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        double attackRange = (double) (this.entityTarget.width * 2.0F * this.entityTarget.width * 2.0F) + 1.0;
        if (this.theGolem.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ) <= attackRange && this.attackTick <= 0) {
            this.attackTick = this.theGolem.getAttackSpeed();
            if (this.theGolem.getHeldItem() != null) {
                this.theGolem.swingItem();
            } else {
                this.theGolem.startActionTimer();
            }

            if (!theGolem.worldObj.isRemote) {
                if (((float)this.theGolem.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() < this.entityTarget.getHealth())) {
                    this.theGolem.attackEntityAsMob(this.entityTarget);
                } else if (theGolem.itemCarried != null) {
                    if (theGolem.itemCarried.getItem() instanceof BlockJarItem) {
                        if (theGolem.itemCarried.stackSize > 0) {
                            final NBTTagCompound entityData = new NBTTagCompound();
                            entityData.setString("id", EntityList.getEntityString(this.entityTarget));
                            this.entityTarget.writeToNBT(entityData);
                            jarEntity(entityData, this.entityTarget.getCommandSenderName());
                            ItemStack copy = theGolem.itemCarried.copy();
                            copy.stackSize--;
                            if (copy.stackSize <= 0){
                                copy = null;
                            }
                            theGolem.setCarried(copy);
                            this.theGolem.worldObj.playSoundEffect(this.entityTarget.posX, this.entityTarget.posY + (this.entityTarget.boundingBox.maxY - this.entityTarget.boundingBox.minY) / 2.0, this.entityTarget.posZ, "thaumcraft:craftfail", 1.0f, 1.0f);
                            this.theGolem.worldObj.removeEntity(this.entityTarget);
                        }
                    }
                }
            }
        }

    }

    public boolean isValidTarget(Entity target) {
        if (!target.isEntityAlive()) {
            return false;
        } else if (target instanceof EntityPlayer && ((EntityPlayer) target).getCommandSenderName().equals(theGolem.getOwnerName())) {
            return false;
        } else if (!theGolem.isWithinHomeDistance(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ))) {
            return false;
        } else {
            if ((target instanceof EntityAnimal || target instanceof IAnimals) && !(target instanceof IMob) && (!(target instanceof EntityTameable) || !((EntityTameable) target).isTamed()) && !(target instanceof EntityGolem)) {
                if (target instanceof EntityAnimal && ((EntityAnimal) target).isChild()) {
                    return false;
                }

                return true;
            }
            if (theGolem.canAttackCreepers() && theGolem.getUpgradeAmount(4) > 0 && target instanceof EntityCreeper) {
                return true;
            }

            if (theGolem.canAttackHostiles() && (target instanceof EntityMob || target instanceof IMob) && !(target instanceof EntityCreeper)) {
                return true;
            }

            if (theGolem.canAttackAnimals() && theGolem.getUpgradeAmount(4) > 0 && (target instanceof EntityAnimal || target instanceof IAnimals) && !(target instanceof IMob) && (!(target instanceof EntityTameable) || !((EntityTameable) target).isTamed()) && !(target instanceof EntityGolem)) {
                return true;
            }

            if (target instanceof EntityPlayer) {
                return false;
            }


            return false;
        }
    }

    void jarEntity(final NBTTagCompound tag, final String name) {
        final ItemStack jar = new ItemStack(jarItem);
        jar.setTagCompound(tag);
        jar.getTagCompound().setString("jarredCritterName", name);
        jar.getTagCompound().setBoolean("isSoul", false);
        this.theGolem.entityDropItem(jar, 1.0f);
    }


}