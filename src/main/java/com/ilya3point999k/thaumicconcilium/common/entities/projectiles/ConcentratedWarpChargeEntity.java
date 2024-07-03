package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.CrimsonPontifex;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TCFociUpgrades;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConcentratedWarpChargeEntity extends Entity implements IEntityOwnable, IEntityAdditionalSpawnData {
    public int power;
    public int range;
    public boolean massHysteria = false;
    public boolean selfFlagellation = false;
    public boolean byForce = false;

    public ConcentratedWarpChargeEntity(World p_i1762_1_) {
        super(p_i1762_1_);

        this.setSize(1.0f, 1.0f);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(17, "");
    }


    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double p_70112_1_) {
        return p_70112_1_ < 4096.0D;
    }

    public ConcentratedWarpChargeEntity(double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, EntityPlayer p, ItemStack wand) {
        super(p.worldObj);
        this.setPosition(p_i1763_2_, p_i1763_4_, p_i1763_6_);
        this.yOffset = 0.0F;
        this.power = ((ItemWandCasting) wand.getItem()).getFocusPotency(wand) + 1;
        this.range = ((ItemWandCasting) wand.getItem()).getFocusEnlarge(wand);
        this.massHysteria = ((ItemFocusBasic) ((ItemWandCasting) wand.getItem()).getFocusItem(wand).getItem()).isUpgradedWith(((ItemWandCasting) wand.getItem()).getFocusItem(wand), TCFociUpgrades.massHysteria);
        this.selfFlagellation = ((ItemFocusBasic) ((ItemWandCasting) wand.getItem()).getFocusItem(wand).getItem()).isUpgradedWith(((ItemWandCasting) wand.getItem()).getFocusItem(wand), TCFociUpgrades.selfFlagellation);
    }

    public ConcentratedWarpChargeEntity(double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, EntityPlayer p) {
        super(p.worldObj);
        this.setPosition(p_i1763_2_, p_i1763_4_, p_i1763_6_);
        this.yOffset = 0.0F;
        this.power = 5;
        this.range = 1;
        this.byForce = true;
    }

    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        this.motionX *= 0.7;
        this.motionY *= 0.7;
        this.motionZ *= 0.7;
        float maxDist = byForce ? 9.0F : 49.0F;
        super.onUpdate();
        if (!byForce && (ticksExisted % 120 == 0 || ticksExisted == 0)){
            worldObj.playSoundAtEntity(this, ThaumicConcilium.MODID+":whispers", 0.1F, 1.0F);
        }
        if (!this.worldObj.isRemote) {
            if (getOwner() == null){
                this.setDead();
            }
            if (byForce && ticksExisted > 200){
                this.setDead();
            }
            if (getOwner() != null) {
                EntityPlayer owner = (EntityPlayer) getOwnerEntity();
                if (owner.isDead || this.dimension != owner.dimension) {
                    this.setDead();
                }
                if (this.getDistanceSqToEntity(owner) > 225.0f) {
                    this.setPosition(owner.posX, owner.posY, owner.posZ);
                }
                if (this.getDistanceSqToEntity(owner) > maxDist) {
                    MiscHelper.setEntityMotionFromVector(this, new Vector3(owner.posX, owner.posY, owner.posZ), 1.0f);
                }
                if (!owner.capabilities.isFlying) {
                    this.motionY -= 0.1;
                }
                this.moveEntity(this.motionX, this.motionY, this.motionZ);

                if (this.massHysteria && this.ticksExisted % 10 == 0) {
                    Collection<PotionEffect> potions = owner.getActivePotionEffects();
                    if (!potions.isEmpty()) {
                        for (PotionEffect potion : potions) {
                            int id = potion.getPotionID();

                            boolean badEffect = ReflectionHelper.getPrivateValue(Potion.class, Potion.potionTypes[id], new String[]{"isBadEffect", "field_76418_K"});
                            if (badEffect) {
                                int d = 5 + 4 * this.range;
                                List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(d, d, d));
                                entities.remove(getOwnerEntity());
                                Collections.shuffle(entities);
                                if (!entities.isEmpty()) {
                                    entities.get(0).addPotionEffect(potion);
                                    owner.removePotionEffect(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (this.selfFlagellation && this.ticksExisted % 10 == 0) {
                    int d = 5 + 4 * this.range;
                    List<EntityPlayer> entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(d, d, d));
                    entities.remove(getOwnerEntity());
                    if (!entities.isEmpty()) {
                        for (EntityPlayer p : entities) {
                            Collection<PotionEffect> potions = owner.getActivePotionEffects();
                            if (!potions.isEmpty()) {
                                for (PotionEffect potion : potions) {
                                    int id = potion.getPotionID();
                                    boolean badEffect = ReflectionHelper.getPrivateValue(Potion.class, Potion.potionTypes[id], new String[]{"isBadEffect", "field_76418_K"});
                                    if (badEffect) {
                                        owner.addPotionEffect(potion);
                                        p.removePotionEffect(id);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(getOwnerEntity().getCommandSenderName());
                int twarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(getOwnerEntity().getCommandSenderName());
                if (twarp == 0 && !byForce) {
                    ((EntityPlayer)getOwnerEntity()).addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("TC.no_warp")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                    this.setDead();
                    return;
                }
                List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);
                if (!byForce) {
                    entities.remove(getOwnerEntity());
                }
                for (EntityLivingBase entity : entities) {
                    if (entity instanceof CrimsonPontifex) continue;
                    entity.attackEntityFrom(
                            EntityDamageSourceIndirect.causeIndirectMagicDamage(this, getOwnerEntity()), warp / 10.0F * power);
                    if (!byForce) {
                        Thaumcraft.addWarpToPlayer((EntityPlayer) getOwnerEntity(), -1, true);
                    }
                }

            }
        }
        if (this.worldObj.isRemote) {
            if (getOwner() != null) {
                if (this.ticksExisted % 3 == 0) {
                    ThaumicConcilium.proxy.warpchain((EntityPlayer) getOwner(), this.posX, this.posY + 0.5, this.posZ);
                }
                if (this.massHysteria && this.worldObj.rand.nextInt() % 8 == 0) {
                    for (int i = 0; i < 10; i++) {
                        ThaumicConcilium.proxy.taintsplosion(this.worldObj, this.posX, this.posY, this.posZ);
                    }
                }
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        if (this.func_152113_b() == null) {
            nbttagcompound.setString("Owner", "");
        } else {
            nbttagcompound.setString("Owner", this.func_152113_b());
        }
        nbttagcompound.setInteger("power", this.power);
        nbttagcompound.setInteger("range", this.range);
        nbttagcompound.setBoolean("massHysteria", this.massHysteria);
        nbttagcompound.setBoolean("selfFlagellation", this.selfFlagellation);
        nbttagcompound.setBoolean("byForce", this.byForce);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        String s = nbttagcompound.getString("Owner");
        if (s.length() > 0) {
            this.setOwner(s);
        }
        this.power = nbttagcompound.getInteger("power");
        this.range = nbttagcompound.getInteger("range");
        this.massHysteria = nbttagcompound.getBoolean("massHysteria");
        this.selfFlagellation = nbttagcompound.getBoolean("selfFlagellation");
        this.byForce = nbttagcompound.getBoolean("byForce");
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
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

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        if (this.massHysteria) {
            buffer.writeInt(1);
        } else if (this.selfFlagellation) {
            buffer.writeInt(2);
        } else if (this.byForce){
            buffer.writeInt(3);
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        try {
            int val = additionalData.readInt();
            if (val == 1) this.massHysteria = true;
            if (val == 2) this.selfFlagellation = true;
            if (val == 3) this.byForce = true;
        } catch (Exception e) {
        }
    }

}