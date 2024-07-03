package com.ilya3point999k.thaumicconcilium.common.entities;

import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.AlchemistSpreeFoci;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;

public class DistortionEffectEntity extends Entity implements IEntityOwnable {

    public int age;

    public DistortionEffectEntity(World w) {
        super(w);
        this.setInvisible(true);
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        age = this.rand.nextInt(100000);
    }
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, "");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!worldObj.isRemote) {
            Entity owner = getOwner();
            if (owner == null){
                this.setDead();
                return;
            }
            if (owner.getDistanceSqToEntity(this) > 4.0F){
                this.setDead();
                return;
            }
            if (!((EntityPlayer) owner).isUsingItem()){
                this.setDead();
                return;
            }
            if (((EntityPlayer) owner).getHeldItem() == null){
                this.setDead();
                return;
            }
            ItemStack stack = ((EntityPlayer) owner).getHeldItem();
            if (!(stack.getItem() instanceof ItemWandCasting)){
                this.setDead();
                return;
            }
            ItemStack foci = ((ItemWandCasting)stack.getItem()).getFocusItem(stack);
            if (foci == null){
                this.setDead();
                return;
            }
            if (!(foci.getItem() instanceof AlchemistSpreeFoci)){
                this.setDead();
                return;
            }
            setPosition(owner.posX, owner.posY, owner.posZ);
            age++;
        }
        //if (!this.worldObj.isRemote && age <= 0) this.setDead();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        String s = nbttagcompound.getString("Owner");
        if (!s.isEmpty()) {
            this.setOwner(s);
        }
        this.age = nbttagcompound.getInteger("age");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("age", this.age);
        if (this.func_152113_b() == null) {
            nbttagcompound.setString("Owner", "");
        } else {
            nbttagcompound.setString("Owner", this.func_152113_b());
        }
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }

    public boolean canAttackWithItem() {
        return false;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    public String func_152113_b() {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    public void setOwner(String par1Str) {
        this.dataWatcher.updateObject(17, par1Str);
    }

    public Entity getOwner() {
        return this.getOwnerEntity();
    }

    public EntityLivingBase getOwnerEntity() {
        return this.worldObj.getPlayerEntityByName(this.func_152113_b());
    }
}