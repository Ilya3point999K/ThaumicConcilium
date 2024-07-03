package com.ilya3point999k.thaumicconcilium.common.entities.projectiles;

import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TCFociUpgrades;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.Iterator;
import java.util.List;
public class PositiveBurstOrbEntity extends Entity implements IEntityAdditionalSpawnData {
    public int orbAge = 0;
    public int orbMaxAge = 150;
    public int orbCooldown;
    private int orbHealth = 5;
    public boolean vitaminize;
    public boolean fulfillment;
    private int power;
    private EntityPlayer closestPlayer;
    private EntityPlayer caster;

    public boolean isInRangeToRenderDist(double par1) {
        double d1 = 0.5;
        d1 *= 64.0 * this.renderDistanceWeight;
        return par1 < d1 * d1;
    }

    public PositiveBurstOrbEntity(World world, EntityPlayer caster, double x, double y, double z, ItemStack wand) {
        super(world);
        this.setSize(0.125F, 0.125F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(x, y, z);
        this.caster = caster;
        this.rotationYaw = (float)(Math.random() * 360.0);
        if (wand != null) {
            this.power = ((ItemWandCasting) wand.getItem()).getFocusPotency(wand);
            this.vitaminize = ((ItemFocusBasic) ((ItemWandCasting) wand.getItem()).getFocusItem(wand).getItem()).isUpgradedWith(((ItemWandCasting) wand.getItem()).getFocusItem(wand), TCFociUpgrades.vitaminize);
            this.fulfillment = ((ItemFocusBasic) ((ItemWandCasting) wand.getItem()).getFocusItem(wand).getItem()).isUpgradedWith(((ItemWandCasting) wand.getItem()).getFocusItem(wand), TCFociUpgrades.fulfillment);
        }

        }

    protected boolean canTriggerWalking() {
        return false;
    }

    public PositiveBurstOrbEntity(World par1World) {
        super(par1World);
        this.setSize(0.125F, 0.125F);
        this.yOffset = this.height / 2.0F;
    }

    protected void entityInit() {
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {
        float f1 = 0.5F;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(par1);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.orbCooldown > 0) {
            --this.orbCooldown;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746;
        if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava) {
            this.motionY = 0.20000000298023224;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        double d0 = 8.0;
        if (this.ticksExisted % 5 == 0 && this.closestPlayer == null) {
            List<Entity> targets = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(d0, d0, d0));
            if (targets.size() > 0) {
                targets.remove(caster);
                double distance = Double.MAX_VALUE;
                Iterator i$ = targets.iterator();

                while(i$.hasNext()) {
                    Entity t = (Entity)i$.next();
                    if(!t.equals(caster)) {
                        double d = t.getDistanceSqToEntity(this);
                        if (d < distance) {
                            distance = d;
                            this.closestPlayer = (EntityPlayer) t;
                        }
                    }
                }
            }
        }

        if (this.closestPlayer != null) {
            double d1 = (this.closestPlayer.posX - this.posX) / d0;
            double d2 = (this.closestPlayer.posY + (double)this.closestPlayer.getEyeHeight() - this.posY) / d0;
            double d3 = (this.closestPlayer.posZ - this.posZ) / d0;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0 - d4;
            if (d5 > 0.0) {
                d5 *= d5;
                this.motionX += d1 / d4 * d5 * 0.1;
                this.motionY += d2 / d4 * d5 * 0.1;
                this.motionZ += d3 / d4 * d5 * 0.1;
            }
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;
        if (this.onGround) {
            f = 0.58800006F;
            Block i = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (!i.isAir(this.worldObj, MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ))) {
                f = i.slipperiness * 0.98F;
            }
        }

        this.motionX *= f;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= f;
        if (this.onGround) {
            this.motionY *= -0.8999999761581421;
        }

        ++this.orbAge;
        if (this.orbAge >= this.orbMaxAge) {
            this.setDead();
        }

    }

    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    protected void dealFireDamage(int par1) {
        this.attackEntityFrom(DamageSource.inFire, (float)par1);
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else {
            this.setBeenAttacked();
            this.orbHealth = (int)((float)this.orbHealth - par2);
            if (this.orbHealth <= 0) {
                this.setDead();
            }

            return false;
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("Health", (byte)this.orbHealth);
        par1NBTTagCompound.setShort("Age", (short)this.orbAge);
        par1NBTTagCompound.setBoolean("Fulfillment", this.fulfillment);
        par1NBTTagCompound.setBoolean("Vitaminize", this.vitaminize);
        par1NBTTagCompound.setInteger("Power", this.power);

    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.orbHealth = par1NBTTagCompound.getShort("Health") & 255;
        this.orbAge = par1NBTTagCompound.getShort("Age");
        this.fulfillment = par1NBTTagCompound.getBoolean("Fulfillment");
        this.vitaminize = par1NBTTagCompound.getBoolean("Vitaminize");
        this.power = par1NBTTagCompound.getInteger("Power");
    }

    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isRemote) {
            //if (!par1EntityPlayer.equals(caster)) {
                if (this.orbCooldown == 0 && par1EntityPlayer.xpCooldown == 0 && !par1EntityPlayer.isEntityInvulnerable()) {
                    if(this.fulfillment) {
                        par1EntityPlayer.setAbsorptionAmount((par1EntityPlayer.getAbsorptionAmount() + this.power));
                        if(par1EntityPlayer.getAbsorptionAmount() > 20.0f) {
                            par1EntityPlayer.setAbsorptionAmount(20.0f);
                        }
                    } else if(this.vitaminize){
                        switch (this.rand.nextInt(3)) {
                            case 0:
                                par1EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100 + this.power * 40, this.power));
                                break;
                            case 1:
                                par1EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 100 + this.power * 40, this.power));
                                break;
                            case 2:
                                par1EntityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 100 + this.power * 40, this.power));
                        }

                    } else {
                        par1EntityPlayer.heal(0.5f);
                    }
                    par1EntityPlayer.xpCooldown = 2;
                    this.playSound("random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
                    this.setDead();
                }
           // }
        }

    }
    public boolean canAttackWithItem() {
        return false;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        if(this.fulfillment){
            buffer.writeChar('f');
        } else if(this.vitaminize){
            buffer.writeChar('v');
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        try {
            char c = additionalData.readChar();
            if (c == 'f') {
                this.fulfillment = true;
            } else if (c == 'v') {
                this.vitaminize = true;
            }
        } catch (Exception e){}
    }
}
