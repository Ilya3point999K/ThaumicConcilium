package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
import thaumcraft.common.entities.ai.combat.AILongRangeAttack;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.entities.projectile.EntityPrimalArrow;

public class CrimsonArcher extends EntityCultist implements IRangedAttackMob {
    public static ItemStack bow = new ItemStack(Items.bow);
    private final EntityAIArrowAttack aiBlastAttack = new EntityAIArrowAttack(this, 1.0, 10, 25, 15.0F);
    private final AIAttackOnCollide aiMeleeAttack = new AIAttackOnCollide(this, EntityLivingBase.class, 0.6, false);
    public boolean updateAINextTick = false;

    public CrimsonArcher(World w) {
        super(w);

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, false));
        if (w != null && !w.isRemote) {
            this.setCombatTask();
        }

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
        this.enchantEquipment();
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void setCombatTask() {
        this.tasks.removeTask(this.aiBlastAttack);
        this.tasks.removeTask(this.aiMeleeAttack);
        ItemStack itemstack = this.getHeldItem();
        if (itemstack != null && itemstack.getItem() == ConfigItems.itemBowBone) {
            this.tasks.addTask(1, this.aiBlastAttack);
        } else {
            this.tasks.addTask(2, this.aiMeleeAttack);
        }
    }
    public void updateAITasks() {
        if (this.updateAINextTick) {
            this.updateAINextTick = false;
            this.setCombatTask();
        }

        super.updateAITasks();
        if (this.ticksExisted % 40 == 0) {
            this.heal(1.0F);
        }

    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        this.addRandomArmor();
        this.tasks.addTask(1, this.aiBlastAttack);
        return super.onSpawnWithEgg(p_110161_1_);
    }

    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float p_82196_2_) {
        EntityPrimalArrow entityarrow = new EntityPrimalArrow(this.worldObj, this, 0.8F, this.worldObj.rand.nextInt(6));
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityarrow.setDamage((double)(2.0 + this.worldObj.rand.nextInt(8)));

        if (i > 0) {
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0) {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0) {
            entityarrow.setFire(100);
        }
        double d0 = entitylivingbase.posX + entitylivingbase.motionX - this.posX;
        double d1 = entitylivingbase.posY - this.posY;
        double d2 = entitylivingbase.posZ + entitylivingbase.motionZ - this.posZ;

        entityarrow.setThrowableHeading(d0, d1, d2, 1.5F, 1.0F);

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

        this.worldObj.spawnEntityInWorld(entityarrow);
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
    protected float getSoundPitch() {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F;
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
    public void addEquipment(){
        this.addRandomArmor();
    }

}
