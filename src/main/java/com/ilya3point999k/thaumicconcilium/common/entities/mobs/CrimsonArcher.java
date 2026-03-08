package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.entities.ai.CrimsonArcherAttackAI;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityCultist;

public class CrimsonArcher extends EntityCultist {
    public static ItemStack bow = new ItemStack(Items.bow);

    public CrimsonArcher(World w) {
        super(w);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new CrimsonArcherAttackAI(this, 0.25D, 20, 20.0F));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, true));


    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void addRandomArmor() {
        this.setCurrentItemOrArmor(4, new ItemStack(Integration.archerHelm));
        this.setCurrentItemOrArmor(3, new ItemStack(Integration.archerChest));
        this.setCurrentItemOrArmor(2, new ItemStack(Integration.archerLegs));
        this.setCurrentItemOrArmor(1, new ItemStack(ConfigItems.itemBootsCultist));
        this.setCurrentItemOrArmor(0, bow.copy());
    }

    @Override
    protected void enchantEquipment() {
        float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25F * f) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0F + f * (float)this.rand.nextInt(18)));
        }
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void updateAITasks() {
        super.updateAITasks();
        if (this.ticksExisted % 40 == 0) {
            this.heal(1.0F);
        }

    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        this.addRandomArmor();
        return super.onSpawnWithEgg(p_110161_1_);
    }

    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected float getSoundPitch() {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F;
    }


    @Override
    protected void dropFewItems(boolean flag, int i) {
        int r = this.rand.nextInt(10);
        if (r == 0) {
            this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 9), 1.5F);
        } else if (r <= 1) {
            this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 17), 1.5F);
        } else if (r <= 3 + i) {
            this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1 + r, 18), 1.5F);
            if(this.rand.nextBoolean()){
                this.entityDropItem(new ItemStack(ConfigItems.itemPrimalArrow, 1 + r, this.rand.nextInt(6)), 1.5F);
            }
        }

        super.dropFewItems(flag, i);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
    }

}
