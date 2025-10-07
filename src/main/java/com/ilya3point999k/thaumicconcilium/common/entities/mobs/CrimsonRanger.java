package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.CrimsonOrbEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.EtherealShacklesEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.PositiveBurstOrbEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ShardPowderEntity;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AILongRangeAttack;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.items.wands.ItemWandCasting;

public class CrimsonRanger extends EntityCultist implements IRangedAttackMob {

    public CrimsonRanger(World w) {
        super(w);

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AILongRangeAttack(this, 1.0, 0.3, 20, 40, 24.0F));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void addRandomArmor() {
        this.setCurrentItemOrArmor(4, new ItemStack(Integration.rangerHelm));
        this.setCurrentItemOrArmor(3, new ItemStack(Integration.rangerChest));
        this.setCurrentItemOrArmor(2, new ItemStack(Integration.rangerLegs));
        this.setCurrentItemOrArmor(1, new ItemStack(ConfigItems.itemBootsCultist));
    }

    protected boolean isAIEnabled() {
        return true;
    }


    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        this.addRandomArmor();
        return super.onSpawnWithEgg(p_110161_1_);
    }

    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        this.swingItem();
        return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f) {
        if (!this.worldObj.isRemote) {
            this.worldObj.playSoundAtEntity(this, "thaumcraft:ice", 0.3F, 0.8F + this.worldObj.rand.nextFloat() * 0.1F);
            this.faceEntity(entitylivingbase, 100.0F, 100.0F);
            Vec3 look = this.getLookVec();
            int orbs = 4 + worldObj.rand.nextInt(6);
            for (int i = 0; i < orbs; i++) {
                ShardPowderEntity orb = new ShardPowderEntity(this, this.posX + (look.xCoord) , this.posY + this.getEyeHeight() + (look.yCoord), this.posZ + (look.zCoord), worldObj.rand.nextInt(2));
                orb.posX -= (double) (Math.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                orb.posY -= 0.5;
                orb.posZ -= (double) (Math.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                orb.setPosition(orb.posX, orb.posY, orb.posZ);
                orb.yOffset = 0.0F;
                float ff = 0.4F;
                orb.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * ff);
                orb.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * ff);
                orb.motionY = (double)(-MathHelper.sin((this.rotationPitch) / 180.0F * (float)Math.PI) * ff);
                float f2 = MathHelper.sqrt_double(orb.motionX * orb.motionX + orb.motionY * orb.motionY + orb.motionZ * orb.motionZ);
                orb.motionX /= f2;
                orb.motionY /= f2;
                orb.motionZ /= f2;
                orb.motionX += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                orb.motionY += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                orb.motionZ += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                this.worldObj.spawnEntityInWorld(orb);
            }
        }
        this.swingItem();
    }

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID+":chant";
    }

    @Override
    protected String getDeathSound() {
        return ThaumicConcilium.MODID+":growl";
    }

    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public int getTalkInterval() {
        return 450;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int r = this.rand.nextInt(10);
        if (r == 0) {
            this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 9), 1.5F);
        } else if (r <= 1) {
            this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 17), 1.5F);
        } else if (r <= 3 + i) {
            if (rand.nextBoolean()) {
                this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1 + this.rand.nextInt(i + 3), 18), 1.5F);
            } else {
                this.entityDropItem(new ItemStack(ConfigItems.itemShard, 1 + this.rand.nextInt(i + 3), rand.nextInt(6)), 1.5F);
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
    public void addEquipment(){
        this.addRandomArmor();
    }

}