package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.BrightestOne;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.List;

public class ChargedWispEntity extends Entity {
    private int age;
    public int lifetime;
    public int power;
    public EntityPlayer caster;

    public ChargedWispEntity(World p_i1762_1_) {
        super(p_i1762_1_);
        this.setSize(0.25F, 0.25F);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(22, "");
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double p_70112_1_) {
        return p_70112_1_ < 4096.0D;
    }

    public ChargedWispEntity(World p_i1763_1_, double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, EntityPlayer p, ItemStack wand) {
        super(p_i1763_1_);
        this.age = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(p_i1763_2_, p_i1763_4_, p_i1763_6_);
        this.yOffset = 0.0F;
        this.caster = p;

        Vec3 look = p.getLookVec();
        this.motionX = look.xCoord;
        this.motionY = look.yCoord;
        this.motionZ = look.zCoord;
        this.lifetime = 20 + this.rand.nextInt(20) + (20 * ((ItemWandCasting) wand.getItem()).getFocusExtend(wand));
        this.power = ((ItemWandCasting) wand.getItem()).getFocusPotency(wand);
    }

    public ChargedWispEntity(World p_i1763_1_, double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, double tx, double ty, double tz) {
        super(p_i1763_1_);
        this.age = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(p_i1763_2_, p_i1763_4_, p_i1763_6_);
        this.yOffset = 0.0F;
        this.caster = null;

        this.motionX = tx;
        this.motionY = ty;
        this.motionZ = tz;
        this.lifetime = 40 + this.rand.nextInt(60);
        this.power = 2;
    }


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

    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

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

        ++this.age;

            AxisAlignedBB boundingBox = this.boundingBox.expand(4.0, 4.0, 4.0);
            List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);
            entities.remove(this.caster);
            int rgb = Aspect.getAspect(getType()).getColor();
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;

            for (EntityLivingBase entity : entities) {
                if (!this.worldObj.isRemote) {

                    if (entity instanceof BrightestOne && entity.boundingBox.intersectsWith(entity.boundingBox) && entity.ticksExisted > 20) {
                        ((BrightestOne) entity).changeType();
                        ((BrightestOne) entity).shortCircuit();
                        //entity.setHealth(entity.getHealth() - 5.0F);
                        this.worldObj.setEntityState(this, (byte) 17);
                        this.setDead();
                        break;
                    }
                    entity.attackEntityFrom(
                            EntityDamageSourceIndirect.causeIndirectMagicDamage(this, this.caster), 10 + this.power);
                    PotionEffect effect = new PotionEffect(Config.potionInfVisExhaustID, 1000 * this.power, 30);
                    effect.getCurativeItems().clear();
                    entity.addPotionEffect(effect);
                    if (rand.nextInt(50) == 0) {
                        worldObj.playSoundAtEntity(this, "thaumcraft:jacobs", 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                    }
                } else {
                    Thaumcraft.proxy.arcLightning(this.worldObj, (float) entity.posX + 0.5f + (-0.5f + rand.nextFloat()), (float) entity.posY + 0.5f + (-0.5f + rand.nextFloat()), (float) entity.posZ + 0.5f + (-0.5f + rand.nextFloat()), (float) this.posX, (float) this.posY + this.getEyeHeight() / 2.0f, (float) this.posZ, red / 255.0F, green / 255.0F, blue / 255.0F, 0.1f);
                }
            }

        //TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) entity.posX + 0.5f + (-0.5f + rand.nextFloat()), (float) entity.posY + 0.5f + (-0.5f + rand.nextFloat()), (float) entity.posZ + 0.5f + (-0.5f + rand.nextFloat()), (float) this.posX, (float) this.posY + this.getEyeHeight() / 2.0f, (float) this.posZ, rgb, 0.1F), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 32.0));

        if (!this.worldObj.isRemote && this.age > this.lifetime) {
            this.worldObj.setEntityState(this, (byte) 17);
            this.setDead();
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte p_70103_1_) {
        if (p_70103_1_ == 17) {
        }
        super.handleHealthUpdate(p_70103_1_);
    }

    public String getType() {
        return this.dataWatcher.getWatchableObjectString(22);
    }

    public void setType(String t) {
        this.dataWatcher.updateObject(22, String.valueOf(t));
    }

    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        p_70014_1_.setInteger("Life", this.age);
        p_70014_1_.setInteger("LifeTime", this.lifetime);
        p_70014_1_.setInteger("power", this.power);
        p_70014_1_.setString("type", this.getType());
    }

    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        this.age = p_70037_1_.getInteger("Life");
        this.lifetime = p_70037_1_.getInteger("LifeTime");
        this.power = p_70037_1_.getInteger("power");
        this.setType(p_70037_1_.getString("type"));
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }

    public float getBrightness(float p_70013_1_) {
        return super.getBrightness(p_70013_1_);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        return super.getBrightnessForRender(p_70070_1_);
    }

    public boolean canAttackWithItem() {
        return false;
    }
}