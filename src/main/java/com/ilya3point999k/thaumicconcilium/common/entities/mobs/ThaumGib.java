package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumic.tinkerer.common.core.helper.MiscHelper;

public class ThaumGib extends EntityMob {

    public ThaumGib(World world) {
        super(world);
        this.setSize(0.6F, 0.5F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.63F));
        this.tasks.addTask(3, new AIAttackOnCollide(this, EntityLivingBase.class, 0.6D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityCultist.class, 0, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.85D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte) 0));
    }

    public ThaumGib(World world, EntityLivingBase parent, int gibType) {
        this(world);
        setType(gibType);
        float f = 0.0f;
        switch (gibType) {
            case 0: {
                f = 1.5f;
                break;
            }
            case 1:
            case 2:
            case 3: {
                f = 1.0f;
                break;
            }

        }
        setLocationAndAngles(parent.posX, parent.boundingBox.minY + f, parent.posZ, parent.rotationYaw, parent.rotationPitch);
        MiscHelper.setEntityMotionFromVector(this, new Vector3(this.posX + (-0.5 + world.rand.nextGaussian()) * 2.0, this.posY, this.posZ + (-0.5 + world.rand.nextGaussian()) * 2.0), 0.5f);
    }

    public byte getType() {
        return this.dataWatcher.getWatchableObjectByte(13);
    }

    public void setType(int par1) {
        this.dataWatcher.updateObject(13, (byte) par1);
    }
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected void attackEntity(Entity p_70785_1_, float p_70785_2_) {
        if (p_70785_2_ > 2.0F && p_70785_2_ < 6.0F && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                double d0 = p_70785_1_.posX - this.posX;
                double d1 = p_70785_1_.posZ - this.posZ;
                float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
                this.motionX = d0 / (double) f2 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
                this.motionZ = d1 / (double) f2 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
                this.motionY = 0.4000000059604645D;
            }
        } else {
            super.attackEntity(p_70785_1_, p_70785_2_);
        }

    }

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID+":gargle_one";
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int r = this.rand.nextInt(10);
        int a = this.rand.nextInt(2);
        if (r <= 3) {
            this.entityDropItem(new ItemStack(ConfigItems.itemNuggetBeef, a + i), 1.5F);
        } else if (r <= 6) {
            this.entityDropItem(new ItemStack(ConfigItems.itemNuggetChicken, a + i), 1.5F);
        } else {
            this.entityDropItem(new ItemStack(ConfigItems.itemNuggetPork, a + i), 1.5F);
        }

        super.dropFewItems(flag, i);
    }

    @Override
    public int getTalkInterval() {
        return 65;
    }

    protected String getDeathSound() {
        return ThaumicConcilium.MODID+":gargle_pain";
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setByte("Type", (byte) getType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        setType(p_70037_1_.getByte("Type"));
    }
}
