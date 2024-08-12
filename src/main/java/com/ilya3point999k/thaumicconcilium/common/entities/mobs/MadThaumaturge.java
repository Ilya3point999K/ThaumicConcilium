package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.PontifexRobe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.entities.monster.EntityCultistCleric;
import thaumcraft.common.entities.monster.EntityCultistKnight;
import thaumcraft.common.lib.research.ResearchManager;

public class MadThaumaturge extends EntityMob {

    public MadThaumaturge(World w) {
        super(w);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCultist.class, 0, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    public double getYOffset() {
        return this.isRiding() ? -1.5 : 0.0;
    }

    public int getTotalArmorValue() {
        int i = super.getTotalArmorValue() + 2;

        if (i > 20) {
            i = 20;
        }

        return i;
    }
    public boolean canPickUpLoot() {
        return false;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID+":sad";
    }

    @Override
    protected String getDeathSound() {
        return ThaumicConcilium.MODID+":sad";
    }

    @Override
    public int getTalkInterval() {
        return 100;
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F;
    }

    @Override
    protected void addRandomArmor() {
        if (this.worldObj.rand.nextBoolean()) {
            this.setCurrentItemOrArmor(0, new ItemStack(ConfigItems.itemHoeVoid));
        }
        else {
            this.setCurrentItemOrArmor(0, new ItemStack(ConfigItems.itemShovelVoid));
        }
        this.equipmentDropChances[0] = 0.05F;
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        this.addRandomArmor();
        return super.onSpawnWithEgg(par1EntityLivingData);
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        if (!Integration.taintedMagic) return true;
        if (player.getHeldItem() == null) return true;
        if (PontifexRobe.isFullSet(player) && player.getHeldItem().getItem() == Integration.crimsonDagger){
            if (!player.worldObj.isRemote) {
                this.setDead();
                EntityCultist cultist = null;
                int rand = this.rand.nextInt(3);
                switch (rand) {
                    case 0: {
                        cultist = new EntityCultistKnight(this.worldObj);
                        break;
                    }
                    case 1: {
                        cultist = new EntityCultistCleric(this.worldObj);
                        break;
                    }
                    case 2: {
                        cultist = new CrimsonPaladin(this.worldObj);
                        break;
                    }
                }
                ((EntityCultist) cultist).setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                player.worldObj.spawnEntityInWorld(cultist);
                ((EntityCultist) cultist).playSound("thaumcraft:craftfail", 1.0F, 1.0F);
            } else {
                for (int i = 0; i < 4; i++) {
                    ThaumicConcilium.proxy.bloodinitiation(player, this);
                }
            }
        }
        return true;
    }

    @Override
    public boolean allowLeashing() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.worldObj.isRemote) {
            if (this.ridingEntity == null && this.getAITarget() != null && this.getAITarget().riddenByEntity == null && !this.getAITarget().isDead && this.getDistanceSqToEntity(this.getAITarget()) < 4.0 && this.worldObj.rand.nextInt(100) > 90) {
                this.mountEntity(this.getAITarget());
            }
            if (this.ridingEntity != null && this.ridingEntity.isDead){
                this.dismountEntity(this.ridingEntity);
            }
        }

        if (!this.worldObj.isRemote && this.ridingEntity != null && this.attackTime <= 0) {
            this.attackTime = 10 + this.rand.nextInt(10);
            this.attackEntityAsMob(this.ridingEntity);
            if (this.ridingEntity != null && (double)this.rand.nextFloat() < 0.2) {
                this.dismountEntity(this.ridingEntity);
            }
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int d = this.rand.nextInt(10);
        if (d > 7) {
            int r = this.rand.nextInt(10);
            if (r <= 3) {
                this.entityDropItem(ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "REFLECTIONFOCI", this.worldObj), 1.5f);
            } else if (r <= 5) {
                this.entityDropItem(ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "BOTTLEOFTHICKTAINT", this.worldObj), 1.5f);
            } else if (r <= 7) {
                this.entityDropItem(ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "RUNICWINDINGS", this.worldObj), 1.5f);
            } else {
                this.entityDropItem(ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "DESTCRYSTAL", this.worldObj), 1.5f);
            }
        } else {
            int r = this.rand.nextInt(2);
            r += i;
            this.entityDropItem(new ItemStack(ConfigItems.itemResource, r, 9), 1.5F);
        }
        super.dropFewItems(flag, i);
    }

    public void addEquipment(){
        this.addRandomArmor();
    }
}
