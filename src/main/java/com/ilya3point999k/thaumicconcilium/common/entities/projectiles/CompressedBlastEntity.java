package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TCFociUpgrades;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.util.List;

public class CompressedBlastEntity extends Entity implements IEntityAdditionalSpawnData {
    private int fireworkAge;
    /**
     * The lifetime of the firework in ticks. When the age reaches the lifetime the firework explodes.
     */
    public int lifetime;

    private ItemStack foci;
    private int power;
    private boolean vacuum;
    private boolean pneumoStrike;
    private boolean flashbang;

    public CompressedBlastEntity(World p_i1762_1_) {
        super(p_i1762_1_);
        this.setSize(0.25F, 0.25F);
    }

    protected void entityInit() {
        this.dataWatcher.addObjectByDataType(8, 5);
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double p_70112_1_) {
        return p_70112_1_ < 4096.0D;
    }

    public CompressedBlastEntity(World p_i1763_1_, double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, EntityPlayer p, ItemStack p_i1763_8_, ItemStack wand) {
        super(p_i1763_1_);
        this.fireworkAge = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(p_i1763_2_, p_i1763_4_, p_i1763_6_);
        this.yOffset = 0.0F;
        int i = 1;

        if (p_i1763_8_ != null && p_i1763_8_.hasTagCompound()) {
            this.dataWatcher.updateObject(8, p_i1763_8_);
            NBTTagCompound nbttagcompound = p_i1763_8_.getTagCompound();
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Fireworks");

            if (nbttagcompound1 != null) {
                i += nbttagcompound1.getByte("Flight");
            }
        }
        if (wand != null) {
            this.power = ((ItemWandCasting) wand.getItem()).getFocusPotency(wand);
            this.vacuum = ((ItemFocusBasic) ((ItemWandCasting) wand.getItem()).getFocusItem(wand).getItem()).isUpgradedWith(((ItemWandCasting) wand.getItem()).getFocusItem(wand), TCFociUpgrades.vacuum);
            this.pneumoStrike = ((ItemFocusBasic) ((ItemWandCasting) wand.getItem()).getFocusItem(wand).getItem()).isUpgradedWith(((ItemWandCasting) wand.getItem()).getFocusItem(wand), TCFociUpgrades.pneumoStrike);
            this.flashbang = ((ItemFocusBasic) ((ItemWandCasting) wand.getItem()).getFocusItem(wand).getItem()).isUpgradedWith(((ItemWandCasting) wand.getItem()).getFocusItem(wand), TCFociUpgrades.flashbang);
        }
        /*
        this.motionX = this.rand.nextGaussian() * 0.001D;
        this.motionZ = this.rand.nextGaussian() * 0.001D;
        this.motionY = this.rand.nextGaussian() * 0.001D;;
         */
        Vec3 look = p.getLookVec();
        this.motionX = look.xCoord;
        this.motionY = look.yCoord;
        this.motionZ = look.zCoord;
        this.lifetime = 5 * i + this.rand.nextInt(20) + (10 * ((ItemWandCasting) wand.getItem()).getFocusExtend(wand));
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    @SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(p_70016_3_, f) * 180.0D / Math.PI);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        /*this.motionX *= 0.5D;
        this.motionZ *= 0.5D;
        this.motionY *= 0.5D;

         */
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

        /*if (this.fireworkAge == 0) {
            this.worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
        }
         */

        ++this.fireworkAge;

        if (!this.worldObj.isRemote) {
            if (this.vacuum) {
                AxisAlignedBB boundingBox = this.boundingBox.expand(4.0, 4.0, 4.0);
                List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);
                for (EntityLivingBase entity : entities) {
                    if (!(entity instanceof EntityPlayer)) {
                        MiscHelper.setEntityMotionFromVector(entity, new Vector3(this.posX, this.posY, this.posZ), 2.0f);
                    }
                }
            }
        }

        if (this.worldObj.isRemote && this.fireworkAge % 2 < 2) {
            this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D);
        }

        if (this.worldObj.isRemote && this.fireworkAge % 5 == 0) {
            //this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY - 0.3D, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D);
            ThaumicConcilium.proxy.smeltFX(this.posX, this.posY, this.posZ, this.worldObj, 1);
        }

        if (this.fireworkAge % 5 == 0) {
            this.worldObj.playSoundAtEntity(this, ThaumicConcilium.MODID+":thump", 0.9F, 2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F);
        }

        if (!this.worldObj.isRemote && this.fireworkAge > this.lifetime) {
            this.worldObj.setEntityState(this, (byte) 17);
            ItemStack itemstack = this.dataWatcher.getWatchableObjectItemStack(8);
            if (itemstack == null || !itemstack.hasTagCompound()) {
                if (!this.pneumoStrike) {
                    boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                    this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 1.0f + this.power, var2);
                } else {
                    AxisAlignedBB boundingBox = this.boundingBox.expand(10.0, 10.0, 10.0);
                    List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);
                    for (EntityLivingBase entity : entities) {
                        Vector3 look = new Vector3(entity.posX - this.posX, 0, entity.posZ - this.posZ).normalize();
                        entity.motionX = look.x * (0.5 * this.power);
                        entity.motionY = 0.5 * this.power;
                        entity.motionZ = look.z * (0.5 * this.power);
                    }
                }
            } else if (this.flashbang) {
                AxisAlignedBB boundingBox = this.boundingBox.expand(10.0, 10.0, 10.0);
                List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);

                for (EntityLivingBase entity : entities) {
                    entity.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 100, 3));
                }

            }
            this.setDead();

        }
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte p_70103_1_) {
        if (p_70103_1_ == 17) {
            ItemStack itemstack = this.dataWatcher.getWatchableObjectItemStack(8);
            if (this.worldObj.isRemote) {
                NBTTagCompound nbttagcompound = null;

                if (itemstack != null && itemstack.hasTagCompound()) {
                    nbttagcompound = itemstack.getTagCompound().getCompoundTag("Fireworks");
                    this.worldObj.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, nbttagcompound);
                } else if(this.pneumoStrike){
                    this.worldObj.spawnParticle("largeexplode", this.posX, this.posY, this.posZ, 0, 0, 0);
                }
            }
        }

        super.handleHealthUpdate(p_70103_1_);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        p_70014_1_.setInteger("Life", this.fireworkAge);
        p_70014_1_.setInteger("LifeTime", this.lifetime);
        p_70014_1_.setBoolean("vacuum", this.vacuum);
        p_70014_1_.setBoolean("pneumoStrike", this.pneumoStrike);
        p_70014_1_.setBoolean("flashbang", this.flashbang);
        p_70014_1_.setInteger("power", this.power);
        ItemStack itemstack = this.dataWatcher.getWatchableObjectItemStack(8);

        if (itemstack != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            itemstack.writeToNBT(nbttagcompound1);
            p_70014_1_.setTag("FireworksItem", nbttagcompound1);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        this.fireworkAge = p_70037_1_.getInteger("Life");
        this.lifetime = p_70037_1_.getInteger("LifeTime");
        this.vacuum = p_70037_1_.getBoolean("vacuum");
        this.pneumoStrike = p_70037_1_.getBoolean("pneumoStrike");
        this.flashbang = p_70037_1_.getBoolean("flashbang");
        this.power = p_70037_1_.getInteger("power");
        NBTTagCompound nbttagcompound1 = p_70037_1_.getCompoundTag("FireworksItem");

        if (nbttagcompound1 != null) {
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);

            if (itemstack != null) {
                this.dataWatcher.updateObject(8, itemstack);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float p_70013_1_) {
        return super.getBrightness(p_70013_1_);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        return super.getBrightnessForRender(p_70070_1_);
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem() {
        return false;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        if(this.pneumoStrike){
            buffer.writeBoolean(this.pneumoStrike);
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        try {
            this.pneumoStrike = additionalData.readBoolean();
        } catch (Exception e){}
    }

    /*
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote) {
            if(this.ticksExisted % 2 == 0){
                this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY - 0.3D, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D);
            }
            if (this.ticksExisted % 15 == 0){
                ThaumicConcilium.proxy.smeltFX(this.posX, this.posY, this.posZ, this.worldObj, 5);
            }
            if(this.ticksExisted >= 100){
                this.setDead();
            }
        } else {
            if (this.ticksExisted % 15 == 0) {
                this.worldObj.playSoundAtEntity(this,  ThaumicConcilium.MODID+":thump", 0.9F, 2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F);
            }
        }

    }

    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (firework == null) {
                boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                this.worldObj.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 2.0F, var2);
            }
            this.setDead();
        }
        if(this.worldObj.isRemote) {
            if (firework != null) {
                if (firework.hasTagCompound()) {
                    NBTTagCompound nbttagcompound = null;
                    nbttagcompound = firework.getTagCompound().getCompoundTag("Fireworks");
                    this.worldObj.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, nbttagcompound);
                }
            }
        }

    }

    public float getShadowSize() {
        return 0.1F;
    }
     */
}
