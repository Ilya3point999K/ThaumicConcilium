package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.entities.UpcomingHoleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.foci.ItemFocusPortableHole;

public class Dissolved extends EntityMob {
    public Dissolved(World w) {
        super(w);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.6D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.4D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.4D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, MadThaumaturge.class, 0, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, true));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityCultist.class, 0, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);

    }


    @Override
    protected boolean interact(EntityPlayer player) {

        if (player.getHeldItem() == null) return false;
        ItemStack stack = player.getHeldItem();
        if (!(stack.getItem() instanceof ItemWandCasting)) return false;
        ItemStack focus = ((ItemWandCasting) stack.getItem()).getFocusItem(stack);
        if (focus == null) return false;
        if (focus.getItem() instanceof ItemFocusPortableHole) {
            this.attackEntityFrom(DamageSource.outOfWorld, 9000F);
            return true;
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource p_70645_1_) {
        for (int i = 0; i < 15; i++) {
            Thaumcraft.proxy.sparkle((float) (this.posX + (-0.5 + this.worldObj.rand.nextFloat())), (float)(this.posY + (this.worldObj.rand.nextFloat() * 2)), (float) ((float) this.posZ + (-0.5 + this.worldObj.rand.nextFloat())), 2);
        }

        super.onDeath(p_70645_1_);
    }


    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void addRandomArmor() {
    }

    public boolean canPickUpLoot() {
        return false;
    }


    protected boolean isAIEnabled() {
        return true;
    }


    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        return super.onSpawnWithEgg(par1EntityLivingData);
    }

    protected String getLivingSound() {
        return "thaumcraft:egidle";
    }

    protected String getDeathSound() {
        return "thaumcraft:egdeath";
    }

    public int getTalkInterval() {
        return 100;
    }

    @Override
    public void onLivingUpdate() {
        if ((ticksExisted % 200 == 0) && (getAttackTarget() != null)) {
            EntityLivingBase target = getAttackTarget();
            UpcomingHoleEntity hole = new UpcomingHoleEntity(worldObj);
            hole.setPositionAndRotation(target.posX, target.posY, target.posZ, worldObj.rand.nextFloat(), worldObj.rand.nextFloat());
            worldObj.spawnEntityInWorld(hole);
        }
        super.onLivingUpdate();

    }

    @Override
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        this.swingItem();
        return p_70652_1_.attackEntityFrom(DamageSource.outOfWorld, f);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int r = this.rand.nextInt(6);
        r += i;
        this.entityDropItem(new ItemStack(ConfigItems.itemResource, r, 17), 1.5F);
        super.dropFewItems(flag, i);
    }

    @Override
    public void performHurtAnimation() {
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
