package com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketResearchComplete;

public class Protolimb extends EntityFlying implements IMob {
    private double targetX, targetY, targetZ;
    public int innerRotation;
    EntityPlayer target;
    public Protolimb(World world) {
        super(world);
        this.setSize(0.6F, 0.5F);
        this.experienceValue = 0;
        this.isImmuneToFire = true;
        innerRotation = this.rand.nextInt(100000);
    }

    public Protolimb(World world, int gibType) {
        this(world);
        setType(gibType);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte) 0));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        ++this.innerRotation;

        if (target == null) {
            target = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
        }

        if (target != null) {
            double dx = this.posX - target.posX;
            double dz = this.posZ - target.posZ;
            double dist = MathHelper.sqrt_double(dx * dx + dz * dz);
            if (dist < 4.0D) {
                targetX += dx / dist * 4;
                targetZ += dz / dist * 4;
                targetY += (this.posY - target.posY) * 0.5;
            }
        }

        double dx = targetX - this.posX;
        double dy = targetY - this.posY;
        double dz = targetZ - this.posZ;
        this.motionX += (Math.signum(dx) * 0.1 - this.motionX) * 0.1;
        this.motionY += (Math.signum(dy) * 0.1 - this.motionY) * 0.1;
        this.motionZ += (Math.signum(dz) * 0.1 - this.motionZ) * 0.1;

        if (this.rand.nextInt(100) == 0 || Math.abs(dx) < 1 && Math.abs(dy) < 1 && Math.abs(dz) < 1) {
            targetX = this.posX + (rand.nextDouble() - 0.5) * 16;
            targetY = this.posY + (rand.nextDouble() - 0.5) * 8;
            targetZ = this.posZ + (rand.nextDouble() - 0.5) * 16;
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if (player == null) return;
        if (worldObj.isRemote) return;
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
        capabilities.protolimbs++;
        capabilities.sync();
        if (capabilities.protolimbs == 6) {
            if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "SOLIDVOID")) {
                Thaumcraft.proxy.getResearchManager().completeResearch(player, "SOLIDVOID");
                PacketHandler.INSTANCE.sendTo(new PacketResearchComplete("SOLIDVOID"), (EntityPlayerMP) player);
                player.worldObj.playSoundAtEntity(player, "thaumcraft:heartbeat", 1.0F, 1.0F);
            }
        }
        Thaumcraft.proxy.burst(this.worldObj, this.posX, this.posY, this.posZ, 2.0F);
        this.playSound("thaumcraft:craftfail", 1.0F, 1.0F);
        this.setDead();
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
    protected void updateFallState(double p_updateFallState_1_, boolean p_updateFallState_3_) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() { return false; }

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
