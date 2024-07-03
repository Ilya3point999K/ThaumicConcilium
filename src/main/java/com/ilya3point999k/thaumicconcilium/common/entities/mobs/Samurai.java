package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.ai.SamuraiAttackAI;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityCultist;

public class Samurai extends EntityMob {

    public Samurai(World w) {
        super(w);

        this.tasks.addTask(0, new EntityAISwimming(this));
        //this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.6D, false));
        this.tasks.addTask(2, new SamuraiAttackAI(this, EntityLivingBase.class, 1D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1D));
        this.tasks.addTask(7, new EntityAIWander(this, 1D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityCultist.class, 0, true));

        this.setSize(1.0F, 2.0F);

    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte) rand.nextInt(3)));
        this.dataWatcher.addObject(14, new Short((short)0));
    }
    @Override
    protected void addRandomArmor() {
        //super.addRandomArmor();
    }

    @Override
    public int getTotalArmorValue() {
        return 20;
    }

    protected boolean isAIEnabled() {
        return true;
    }
    public boolean canPickUpLoot() {
        return false;
    }

    public boolean isOnSameTeam(EntityLivingBase el) {
        return el instanceof Samurai;
    }

    @Override
    protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {
        if (!this.isEntityInvulnerable()) {
            p_70665_2_ = ForgeHooks.onLivingHurt(this, p_70665_1_, p_70665_2_);
            if (p_70665_2_ <= 0) return;
            int type = getType();
            p_70665_2_ /= type == 2 ? 25.0F : type == 1 ? 20.0F : 15.0F;
            p_70665_2_ = this.applyPotionDamageCalculations(p_70665_1_, p_70665_2_);
            float f1 = p_70665_2_;
            p_70665_2_ = Math.max(p_70665_2_ - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f1 - p_70665_2_));

            if (p_70665_2_ != 0.0F) {
                float f2 = this.getHealth();
                this.setHealth(f2 - p_70665_2_);
                this.func_110142_aN().func_94547_a(p_70665_1_, f2, p_70665_2_);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - p_70665_2_);
                if (worldObj.rand.nextInt(10) > 7){
                    this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60, 3));
                }
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;
        int type = getType();
        f += type == 2 ? 15.0F : type == 1 ? 10.0F : 7.0F;
        if (p_70652_1_ instanceof EntityLivingBase) {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_70652_1_);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_70652_1_);
        }

        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
            if (i > 0) {
                p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0) {
                p_70652_1_.setFire(j * 4);
            }

            if (p_70652_1_ instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, this);
            }

            EnchantmentHelper.func_151385_b(this, p_70652_1_);
        }

        return flag;
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        this.setType(this.worldObj.rand.nextInt(3));
        return super.onSpawnWithEgg(par1EntityLivingData);
    }

    public byte getType() {
        return this.dataWatcher.getWatchableObjectByte(13);
    }
    public void setType(int par1) {
        this.dataWatcher.updateObject(13, (byte)par1);
    }

    public int getAnger() {
        return this.dataWatcher.getWatchableObjectShort(14);
    }
    public void setAnger(int par1) {
        this.dataWatcher.updateObject(14, (short)par1);
    }

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID+":speech";
    }

    @Override
    protected String getDeathSound() {
        return ThaumicConcilium.MODID+":uagh";
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
        int r = this.rand.nextInt(3);
        int a = this.rand.nextInt(10);
        if (a % 3 == 0) {
            this.entityDropItem(new ItemStack(ConfigItems.itemBaubleBlanks, 1, r), 1.5F);
        }
        super.dropFewItems(flag, i);
    }

    @Override
    public void onUpdate() {
        if (getAnger() > 0){
            this.setAnger(this.getAnger() - 1);
        }
        super.onUpdate();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("Anger", getAnger());
        p_70014_1_.setByte("Type", (byte) getType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        setAnger(p_70037_1_.getInteger("Anger"));
        setType(p_70037_1_.getByte("Type"));
    }
}