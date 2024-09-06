package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.CrimsonOrbEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.EtherealShacklesEntity;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AILongRangeAttack;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.items.wands.ItemWandCasting;

public class CrimsonPaladin extends EntityCultist implements IRangedAttackMob {
    public static ItemStack staff = new ItemStack(ConfigItems.itemWandCasting);
    public CrimsonPaladin(World w) {
        super(w);
        ItemWandCasting wand = (ItemWandCasting) staff.getItem();
        wand.setRod(staff, ConfigItems.STAFF_ROD_GREATWOOD);
        wand.setCap(staff, ForbiddenItems.WAND_CAP_ALCHEMICAL);

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AILongRangeAttack(this, 8.0, 1.0, 20, 40, 24.0F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void addRandomArmor() {
        this.setCurrentItemOrArmor(4, new ItemStack(ConfigItems.itemHelmetCultistRobe));
        this.setCurrentItemOrArmor(3, new ItemStack(ConfigItems.itemChestCultistPlate));
        this.setCurrentItemOrArmor(2, new ItemStack(ConfigItems.itemLegsCultistRobe));
        this.setCurrentItemOrArmor(1, new ItemStack(ConfigItems.itemBootsCultist));
        this.setCurrentItemOrArmor(0, staff.copy());
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
            worldObj.playSoundAtEntity(this, ThaumicConcilium.MODID+":spell", 1.0F, 1.0F);
            int rand = worldObj.rand.nextInt(2);
            if (rand == 0) {
                if (entitylivingbase instanceof EntityPlayer) {
                    EtherealShacklesEntity shackles = new EtherealShacklesEntity(entitylivingbase.worldObj, this);
                    double d0 = entitylivingbase.posX + entitylivingbase.motionX - this.posX;
                    double d1 = entitylivingbase.posY - this.posY;
                    double d2 = entitylivingbase.posZ + entitylivingbase.motionZ - this.posZ;
                    shackles.setThrowableHeading(d0, d1, d2, 2.0F, 2.0F);
                    this.worldObj.spawnEntityInWorld(shackles);
                    shackles.playSound(ThaumicConcilium.MODID+":shackles_throw", 0.5F, 1.0F);
                    this.swingItem();
                }
            } else {
                this.faceEntity(entitylivingbase, 100.0F, 100.0F);
                Vec3 look = this.getLook(1.0F);
                CrimsonOrbEntity orb = new CrimsonOrbEntity(worldObj, posX + look.xCoord, posY + this.getEyeHeight(), posZ + look.zCoord, look.xCoord, look.yCoord, look.zCoord, this);
                this.worldObj.spawnEntityInWorld(orb);
                this.swingItem();
            }
        }
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
            this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 18), 1.5F);
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