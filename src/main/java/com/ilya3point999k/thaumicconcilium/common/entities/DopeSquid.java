package com.ilya3point999k.thaumicconcilium.common.entities;

import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import tb.api.ITobacco;
import thaumcraft.common.Thaumcraft;

public class DopeSquid extends EntityTameable {
    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    /** appears to be rotation in radians; we already have pitch & yaw, so this completes the triumvirate. */
    public float squidRotation;
    /** previous squidRotation in radians */
    public float prevSquidRotation;
    /** angle of the tentacles in radians */
    public float tentacleAngle;
    /** the last calculated angle of the tentacles in radians */
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    /** change in squidRotation in radians. */
    private float rotationVelocity;
    private float field_70871_bB;
    public int charge = 0;

    public DopeSquid(World p_i1693_1_) {
        super(p_i1693_1_);
        this.tasks.addTask(2, this.aiSit);
        this.setSize(0.95F, 0.95F);
        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable p_90011_1_) {
        return null;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(22, new Byte((byte)9));
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected Item getDropItem() {
        return Item.getItemById(0);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
        int j = this.rand.nextInt(3 + p_70628_2_) + 1;

        for (int k = 0; k < j; ++k) {
            this.entityDropItem(new ItemStack(Items.dye, 1, 0), 0.0F);
        }
    }

    public void setTobacco(byte t){
        this.dataWatcher.updateObject(22, t);
    }

    public byte getTobacco() {
        return this.dataWatcher.getWatchableObjectByte(22);
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            ItemStack stack = player.getHeldItem();
            if (isTamed() && getOwner() == player){
                if (stack != null && stack.getItem() instanceof ITobacco){
                    setTobacco((byte) stack.getItemDamage());
                } else {
                    setSitting(!isSitting());
                }
            }
            if (stack != null && stack.getItem() == Integration.tobaccoleaves && stack.getItemDamage() == 7) {
                if (!isTamed()) {
                    this.setTamed(true);
                    this.aiSit.setSitting(true);
                    this.func_152115_b(player.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                } else if (getOwner() == player){
                    this.heal(5.0F);
                    this.playTameEffect(true);
                }
                if (!player.capabilities.isCreativeMode) {
                    --stack.stackSize;
                }
                if (stack.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                }

            }
        }
        return super.interact(player);
    }

    public boolean isInWater() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }
    protected void fall(float p_70069_1_) {}

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double p_70064_1_, boolean p_70064_3_) {}

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;

        if (this.squidRotation > ((float)Math.PI * 2F)) {
            this.squidRotation -= ((float)Math.PI * 2F);

            if (this.rand.nextInt(10) == 0) {
                this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
            }
        }
        if (!this.isSitting()) {
            float f;
            if (this.squidRotation < (float)Math.PI) {
                f = this.squidRotation / (float)Math.PI;
                this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;

                if ((double)f > 0.75D) {
                    this.randomMotionSpeed = 0.9F;
                    this.field_70871_bB = 1.0F;
                } else {
                    this.field_70871_bB *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.field_70871_bB *= 0.99F;
            }
            EntityLivingBase owner = this.getOwner();
            if (ticksExisted % 400 == 0) {
                charge = 10;
                this.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.0F);
            }

            if (charge > 0){
                Vec3 look = this.getLookVec();

                double x = this.posX + look.xCoord / 5;
                double y = this.posY + this.getEyeHeight() + look.yCoord / 5;
                double z = this.posZ + look.zCoord / 5;
                this.worldObj.spawnParticle("explode", x, y, z, look.xCoord / 5, look.yCoord / 5, look.zCoord / 5);
                charge--;
            }
            if (!this.worldObj.isRemote) {
                //System.out.println(getTobacco());
                if (owner instanceof EntityPlayer && owner.dimension == this.dimension) {
                    if (ticksExisted % 400 == 0){
                        EntityPlayer player = (EntityPlayer) owner;
                        final int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(player.getCommandSenderName());
                        if (warp >= 10){
                            Integration.tobacco.performTobaccoEffect(player, getTobacco(), false);
                            Thaumcraft.addWarpToPlayer(player, -10, true);
                        }
                    }
                    double var15 = (owner.posX + (-1 + rand.nextInt(3))) - this.posX;
                    double var16 = (owner.posY + (-1 + rand.nextInt(3))) - this.posY;
                    double var17 = (owner.posZ + (-1 + rand.nextInt(3))) - this.posZ;
                    double var18 = var15 * var15 + var16 * var16 + var17 * var17;
                    var18 = (double) MathHelper.sqrt_double(var18);
                    this.motionX += (var15 / var18 * 0.1D);
                    if (this.posY < owner.posY + 2.0) {
                        this.motionY += (var16 / var18 * 0.1D + 0.1D);
                    } else {
                        this.motionY += (var16 / var18 * 0.1D);
                    }
                    this.motionZ += (var17 / var18 * 0.1D);
                    this.motionX *= randomMotionSpeed;
                    this.motionY *= randomMotionSpeed;
                    this.motionZ *= randomMotionSpeed;
                    if (getDistanceSqToEntity(owner) > 225.0f){
                        this.setPosition((owner.posX + (-1 + rand.nextInt(3))), (owner.posY + (-1 + rand.nextInt(3))), (owner.posZ + (-1 + rand.nextInt(3))));
                    }
                }
            }

            f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw += (float)Math.PI * this.field_70871_bB * 1.5F;
            this.squidPitch += (-((float)Math.atan2((double)f, this.motionY)) * 180.0F / (float)Math.PI - this.squidPitch) * 0.1F;
        } else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;

            if (!this.worldObj.isRemote) {
                this.motionX = 0.0D;
                this.motionY -= 0.08D;
                this.motionY *= 0.9800000190734863D;
                this.motionZ = 0.0D;
            }

            this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
        }
    }


    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setByte("Tobacco", this.getTobacco());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.setTobacco(nbttagcompound.getByte("Tobacco"));
    }


}
