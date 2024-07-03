package com.ilya3point999k.thaumicconcilium.common.golems.ai;

import fox.spiteful.forbidden.compat.Compat;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import thaumcraft.common.entities.ai.combat.AINearestAttackableTargetSorter;
import thaumcraft.common.entities.golems.EntityGolemBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AINearestAnyTarget extends EntityAITarget {
    EntityGolemBase theGolem;
    EntityLivingBase target;
    int targetChance;
    private final IEntitySelector entitySelector;
    private float targetDistance;
    private AINearestAttackableTargetSorter theNearestAttackableTargetSorter;
    private Item bloodSyringe;

    public AINearestAnyTarget(EntityGolemBase par1EntityLiving, int par4, boolean par5) {
        this(par1EntityLiving, 0.0F, par4, par5, false, (IEntitySelector)null);
    }

    public AINearestAnyTarget(EntityGolemBase par1, float par3, int par4, boolean par5, boolean par6, IEntitySelector par7IEntitySelector) {
        super(par1, par5, par6);
        try {
            bloodSyringe = Compat.getItem("ThaumicHorizons", "syringeBloodSample");
        } catch (Compat.ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.targetDistance = 0.0F;
        this.theGolem = par1;
        this.targetDistance = 0.0F;
        this.targetChance = par4;
        this.theNearestAttackableTargetSorter = new AINearestAttackableTargetSorter(this, par1);
        this.entitySelector = par7IEntitySelector;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        this.targetDistance = this.theGolem.getRange();
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        } else {
            List var5 = this.taskOwner.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.taskOwner.boundingBox.expand((double)this.targetDistance, 4.0, (double)this.targetDistance), this.entitySelector);
            Collections.sort(var5, this.theNearestAttackableTargetSorter);
            Iterator var2 = var5.iterator();

            Entity var3;
            EntityLivingBase var4;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                var3 = (Entity)var2.next();
                var4 = (EntityLivingBase)var3;
            } while(!isValidTarget(var3));
            this.target = var4;
            return true;
        }
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.target);
        super.startExecuting();
    }

    public boolean isValidTarget(Entity target) {
        if (!target.isEntityAlive()) {
            return false;
        } else if (target instanceof EntityPlayer && ((EntityPlayer)target).getCommandSenderName().equals(theGolem.getOwnerName())) {
            return false;
        } else if (!theGolem.isWithinHomeDistance(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ))) {
            return false;
        } else {
            if (this.theGolem.getUpgradeAmount(4) > 0 && ((float)this.theGolem.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() < ((EntityLivingBase) target).getHealth())){
                return false;
            }
            ArrayList<String> wanted = new ArrayList<>();
            for (ItemStack stack : theGolem.inventory.inventory){
                if (stack == null) continue;
                if (stack.getItem() == bloodSyringe && stack.hasTagCompound() && stack.stackTagCompound.getCompoundTag("critter") != null && stack.stackTagCompound.getCompoundTag("critter").getCompoundTag("ForgeData") != null) {
                    Entity entity = EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag("critter"), theGolem.worldObj);
                    wanted.add(EntityList.getEntityString(entity));
                }
            }
            if (!wanted.isEmpty()){
                if (wanted.contains(EntityList.getEntityString(target))){
                    return true;
                } else return false;
            }
            if (target instanceof IBossDisplayData){
                return false;
            }
            if ((target instanceof EntityAnimal || target instanceof IAnimals) && !(target instanceof IMob) && (!(target instanceof EntityTameable) || !((EntityTameable)target).isTamed()) && !(target instanceof EntityGolem)) {
                if (target instanceof EntityAnimal && ((EntityAnimal)target).isChild()) {
                    return false;
                }

                return true;
            }

            if (theGolem.getUpgradeAmount(5) > 0 && (target instanceof EntityMob || target instanceof IMob)) {
                return true;
            }

            if (theGolem.canAttackAnimals() && theGolem.getUpgradeAmount(4) > 0 && (target instanceof EntityAnimal || target instanceof IAnimals) && !(target instanceof IMob) && (!(target instanceof EntityTameable) || !((EntityTameable)target).isTamed()) && !(target instanceof EntityGolem)) {
                return true;
            }
            if (target instanceof EntityPlayer) {
                return false;
            }


            return false;
        }
    }
}