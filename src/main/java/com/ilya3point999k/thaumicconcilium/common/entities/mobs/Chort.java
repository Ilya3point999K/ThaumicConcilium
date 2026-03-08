package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.brewing.WitcheryBrewRegistry;
import com.ilya3point999k.thaumicconcilium.common.entities.ai.ChortAttackAI;
import com.ilya3point999k.thaumicconcilium.common.entities.ai.ImpDealTargetAI;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class Chort extends EntityMob {
    public static ItemStack brewMoonshine;
    public int chatCooldown = 0;

    public Chort(World w) {
        super(w);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new ChortAttackAI(this));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new ImpDealTargetAI(this));
        this.isImmuneToFire = true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }

    protected boolean isAIEnabled() {
        return true;
    }

    protected String getLivingSound() {
        return "mob.pig.say";
    }

    protected String getHurtSound() {
        return "mob.pig.say";
    }

    protected String getDeathSound() {
        return "mob.pig.death";
    }

    @Override
    protected float getSoundPitch() {
        return 0.5f;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int r = this.rand.nextInt(10);
        if (r < 3) {
            this.entityDropItem(Witchery.Items.GENERIC.itemFoulFume.createStack(this.rand.nextInt(4)), 1.5F);
        } else if (r < 6) {
            this.entityDropItem(Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack(this.rand.nextInt(4)), 1.5F);
        } else {
            this.entityDropItem(brewMoonshine.copy(), 1.5F);
        }

        super.dropFewItems(flag, i);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (chatCooldown > 0){
            chatCooldown--;
        }
        if (this.ridingEntity instanceof SloppyAlchemist) {
            SloppyAlchemist mount = (SloppyAlchemist) this.ridingEntity;
            EntityLivingBase mountTarget = mount.getAttackTarget();
            if (mountTarget != null) {
                this.setAttackTarget(mountTarget);
            }
        }
    }

    static {
        brewMoonshine = new ItemStack(Witchery.Items.BREW);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        NBTTagCompound itemtag = new NBTTagCompound();
        Witchery.Items.GENERIC.itemMandrakeRoot.createStack(1).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.wheat).writeToNBT(itemtag);
        list.appendTag(itemtag);


        tag.setTag("Items", list);
        tag.setInteger("Color", 0xFEC20C);
        tag.setString("BrewName", WitcheryBrewRegistry.INSTANCE.getBrewName(tag));

        WitcheryBrewRegistry.INSTANCE.updateBrewInformation(tag);

        brewMoonshine.setTagCompound(tag);
    }
}
