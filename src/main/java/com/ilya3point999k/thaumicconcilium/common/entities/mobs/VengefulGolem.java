package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketEnslave;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AILongRangeAttack;

public class VengefulGolem extends EntityMob implements IRangedAttackMob {

    public VengefulGolem(World w) {
        super(w);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AILongRangeAttack(this, 1.0, 0.5, 20, 40, 24.0F));
        this.tasks.addTask(7, new EntityAIWander(this, 0.4D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Thaumaturge.class, 0, true));
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


    @Override
    protected void addRandomArmor() {
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
        if (this.entityToAttack != null) {
            this.faceEntity(entityToAttack, 100.0F, 100.0F);
        }
        super.onLivingUpdate();
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

    @Override
    protected String getLivingSound() {
        return ThaumicConcilium.MODID + ":gnaw";
    }

    @Override
    protected String getDeathSound() {
        return ThaumicConcilium.MODID + ":gnaw";
    }

    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int r = this.rand.nextInt(2);
        r += i;
        this.entityDropItem(new ItemStack(ConfigItems.itemResource, r, 9), 1.5F);
        super.dropFewItems(flag, i);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        if (target instanceof EntityPlayer) {
            boolean muffed = false;
            if(Integration.witchery){
                ItemStack currentArmor = ((EntityPlayer)target).getCurrentArmor(3);
                if(currentArmor != null && currentArmor.getItem() == Integration.earmuffs){
                    muffed = true;
                }
            }
            String name = target.getCommandSenderName();
            if (!muffed && (rand.nextInt(10) >= 2) && this.canEntityBeSeen(target)) {
                this.swingItem();
                target.worldObj.playSoundAtEntity(target, "random.orb", 0.7F, 1.0F + target.worldObj.rand.nextFloat() * 0.1F);
                if (rand.nextInt(10) > 5) {
                    int r = rand.nextInt(4);
                    ((EntityPlayer) target).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.golem.taunt." + r)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                }
                TCPacketHandler.INSTANCE.sendTo(new PacketEnslave(name, true), (EntityPlayerMP) target);
            } else {
                TCPacketHandler.INSTANCE.sendTo(new PacketEnslave(name, false), (EntityPlayerMP) target);
            }
        } else if (target instanceof EntityLiving && target.worldObj.rand.nextFloat() > 0.1F) {
            double x = target.posX + ((-0.5 + target.worldObj.rand.nextDouble()) * 20.0);
            double y = target.posY + ((-0.5 + target.worldObj.rand.nextDouble()) * 2.0);
            double z = target.posZ + ((-0.5 + target.worldObj.rand.nextDouble()) * 20.0);

            ((EntityLiving) target).getNavigator().tryMoveToXYZ(x, y, z, 0.7);
        }
    }
}