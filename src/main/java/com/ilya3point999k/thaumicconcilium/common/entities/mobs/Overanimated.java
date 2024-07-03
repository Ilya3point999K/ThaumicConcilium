package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXBloodsplosion;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import thaumcraft.common.entities.monster.EntityCultist;

public class Overanimated extends EntityMob {
    public Overanimated(World w) {
        super(w);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.6D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityCultist.class, 0, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected void onDeathUpdate() {
        if (!this.worldObj.isRemote) {
            for (int i = 0; i < 6; i++) {
                ThaumGib gib = new ThaumGib(this.worldObj, this, i);
                this.worldObj.spawnEntityInWorld(gib);
                gib.setType(i);
            }
            TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXBloodsplosion(posX, posY, posZ), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 32.0));
        }
        this.setDead();
    }

    @Override
    public void onDeath(DamageSource p_70645_1_) {
    }

    @Override
    protected void addRandomArmor() {
    }

    @Override
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        this.swingItem();
        return super.attackEntityAsMob(p_70652_1_);
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public EntityLivingBase getAttackTarget() {
        return super.getAttackTarget();
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        return super.onSpawnWithEgg(par1EntityLivingData);
    }

    @Override
    public void onLivingUpdate() {
        if (this.entityToAttack != null){
            this.faceEntity(entityToAttack, 100.0F, 100.0F);
        }
        super.onLivingUpdate();
    }

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID+":gargle_many";
    }

    @Override
    public int getTalkInterval() {
        return 65;
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
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