package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.util.ParticleEffect;
import com.emoniph.witchery.util.SoundEffect;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.ai.ImpDealTargetAI;
import com.ilya3point999k.thaumicconcilium.common.entities.ai.SloppyAlchemistAttackAI;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class SloppyAlchemist extends EntityMob {
    boolean wasFreed = false;

    public SloppyAlchemist(World w) {
        super(w);
        this.setSize(1.0F, 0.8F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new SloppyAlchemistAttackAI(this, 0.22D, 1.1D, 60, 16.0F));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new ImpDealTargetAI(this));
        this.stepHeight = 1.0f;
        this.isImmuneToFire = true;
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        if ((this.riddenByEntity instanceof Chort)) return true;
        if (worldObj.isRemote) {
            for (int i = 0; i < 20; i++) {
                double x = posX + (rand.nextDouble() - 0.5D) * width;
                double y = posY + rand.nextDouble() * height;
                double z = posZ + (rand.nextDouble() - 0.5D) * width;

                worldObj.spawnParticle(
                        "flame",
                        x, y, z,
                        0.0D, 0.02D, 0.0D
                );
            }
            if (wasFreed) {
                player.addChatMessage(new ChatComponentText(
                        StatCollector.translateToLocal("tc.sloppy_alchemist.thanks" + (1 + worldObj.rand.nextInt(3)))
                ).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)));
            } else {
                player.addChatMessage(new ChatComponentText(
                        StatCollector.translateToLocal("tc.sloppy_alchemist.confused")
                ).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)));
            }
        }

        if (!player.worldObj.isRemote) {
            this.playSound(ThaumicConcilium.MODID + ":breath", 1.0F, 1.0F);
            if (!wasFreed){
                this.setDead();
                return true;
            }
            ItemStack reward;
            int rand = worldObj.rand.nextInt(3);
            switch (rand){
                case 0:{
                    reward = Witchery.Items.GENERIC.itemInfernalBlood.createStack(worldObj.rand.nextInt(3) + 1);
                    break;
                }
                case 1:{
                    reward = Witchery.Items.GENERIC.itemBloodWarm.createStack(worldObj.rand.nextInt(3) + 1);
                    break;
                }
                case 2:{
                    if (Integration.bloodArsenal) {
                        reward = new ItemStack(Integration.bloodMoney, worldObj.rand.nextInt(3) + 1, 0);
                    } else {
                        reward = Witchery.Items.GENERIC.itemInfernalBlood.createStack(worldObj.rand.nextInt(3) + 1);
                    }
                    break;
                }
                default:{
                    reward = Witchery.Items.GENERIC.itemInfernalBlood.createStack(worldObj.rand.nextInt(3) + 1);
                    break;
                }
            }

            if (!player.inventory.addItemStackToInventory(reward)){
                EntityItem f = new EntityItem(player.worldObj, this.posX, this.posY + 0.5, this.posZ, reward);
                f.delayBeforeCanPickup = 40;
                f.motionY += 0.3f;
                worldObj.spawnEntityInWorld(f);
            }
            this.setDead();
        }

        return true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        Chort chort = new Chort(this.worldObj);
        chort.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        chort.onSpawnWithEgg(null);
        this.worldObj.spawnEntityInWorld(chort);
        chort.mountEntity(this);
        this.dataWatcher.addObject(13, (byte) 1);
        return super.onSpawnWithEgg(data);
    }


    @Override
    protected void addRandomArmor() {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.riddenByEntity instanceof Chort) {
            this.wasFreed = true;
            this.setSize(1.0F, 0.8F);
            EntityLivingBase riderTarget = ((Chort) this.riddenByEntity).getAttackTarget();
            if (riderTarget != null) {
                this.setAttackTarget(riderTarget);
            }
        } else {
            this.setSize(0.5F, 1.8F);
        }
    }

    @Override
    public boolean canPickUpLoot() {
        return false;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public double getMountedYOffset() {
        return 0.3;
    }
}
