package com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class WitheredBotanist extends EntityLiving {
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    public double boatYaw;
    private double boatPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;

    public WitheredBotanist(World world) {
        super(world);
        setSize(2.0F, 0.3F);
    }

    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }
    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void onUpdate() {
        this.onEntityUpdate();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final byte b0 = 5;
        double d0 = 0.0;
        for (int i = 0; i < b0; ++i) {
            final double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 0) / b0 - 0.125;
            final double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 1) / b0 - 0.125;
            final AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d2, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0 / b0;
            }
            else if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.lava)) {
                d0 += 1.0 / b0;
            }
        }
        final double d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (this.worldObj.isRemote) {
            if (this.boatPosRotationIncrements > 0) {
                final double d5 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                final double d6 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                final double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                final double d12 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                this.rotationYaw += (float)(d12 / this.boatPosRotationIncrements);
                this.rotationPitch += (float)((this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(d5, d6, d11);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                final double d5 = this.posX + this.motionX;
                final double d6 = this.posY + this.motionY;
                final double d11 = this.posZ + this.motionZ;
                this.setPosition(d5, d6, d11);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
        }
        else {
            if (d0 < 1.0) {
                final double d5 = d0 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * d5;
            }
            else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007000000216066837;
            }
            double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (d5 > 0.45) {
                final double d6 = 0.45 / d5;
                this.motionX *= d6;
                this.motionZ *= d6;
                d5 = 0.45;
            }
            if (d5 > d4 && this.speedMultiplier < 0.5) {
                this.speedMultiplier += (0.5 - this.speedMultiplier) / 50.0;
                if (this.speedMultiplier > 0.5) {
                    this.speedMultiplier = 0.5;
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - 0.1) / 50.0;
                if (this.speedMultiplier < 0.1) {
                    this.speedMultiplier = 0.1;
                }
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9900000095367432;
            this.motionY *= 0.949999988079071;
            this.motionZ *= 0.9900000095367432;
            this.rotationPitch = 0.0f;
            double d6 = this.rotationYaw;
            final double d11 = this.prevPosX - this.posX;
            final double d12 = this.prevPosZ - this.posZ;
            if (d11 * d11 + d12 * d12 > 0.001) {
                d6 = (float)(Math.atan2(d12, d11) * 180.0 / 3.141592653589793);
            }
            double d13 = MathHelper.wrapAngleTo180_double(d6 - this.rotationYaw);
            if (d13 > 20.0) {
                d13 = 20.0;
            }
            if (d13 < -20.0) {
                d13 = -20.0;
            }
            this.setRotation(this.rotationYaw += (float)d13, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
                if (list != null && !list.isEmpty()) {
                    for (int k2 = 0; k2 < list.size(); ++k2) {
                        final Entity entity = (Entity) list.get(k2);
                        if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
                            entity.applyEntityCollision((Entity)this);
                        }
                    }
                }
            }
        }
    }
    protected void updateFallState(final double p_70064_1_, final boolean p_70064_3_) {
        final int i = MathHelper.floor_double(this.posX);
        final int j = MathHelper.floor_double(this.posY);
        final int k = MathHelper.floor_double(this.posZ);
        if (!p_70064_3_) {
            if (this.worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && this.worldObj.getBlock(i, j - 1, k).getMaterial() != Material.lava && p_70064_1_ < 0.0) {
                this.fallDistance -= (float)p_70064_1_;
            }
        }
    }


    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double p_70056_1_, final double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        this.boatPosRotationIncrements = p_70056_9_ + 5;

        this.boatX = p_70056_1_;
        this.boatY = p_70056_3_;
        this.boatZ = p_70056_5_;
        this.boatYaw = p_70056_7_;
        this.boatPitch = p_70056_8_;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(final double p_70016_1_, final double p_70016_3_, final double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.velocityX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.velocityY = p_70016_3_;
        this.motionZ = p_70016_5_;
        this.velocityZ = p_70016_5_;
    }
    @Override
    protected void addRandomArmor() {
    }

    @Override
    public boolean getCanSpawnHere() {
        return (this.worldObj.provider.dimensionId == Integration.alfheimDimension || this.worldObj.provider.dimensionId == 0) && worldObj.getEntitiesWithinAABB(WitheredBotanist.class, this.boundingBox.expand(32.0, 32.0, 32.0)).isEmpty() && super.getCanSpawnHere();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0);
    }


    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        return super.onSpawnWithEgg(par1EntityLivingData);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float f) {
        fertilize();
        return true;
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        fertilize();
        return true;
    }

    public void fertilize(){
        final int range = 3;
        if(!this.worldObj.isRemote) {
            List<ChunkCoordinates> validCoords = new ArrayList();

            for(int i = -range - 1; i < range; i++)
                for(int j = -range - 1; j < range; j++) {
                    for(int k = 2; k >= -2; k--) {
                        int x = MathHelper.floor_double(posX) + i + 1;
                        int y = MathHelper.floor_double(posY) + k + 1;
                        int z = MathHelper.floor_double(posZ) + j + 1;
                        if(this.worldObj.isAirBlock(x, y, z) && (!this.worldObj.provider.hasNoSky || y < 255) && ModBlocks.flower.canBlockStay(this.worldObj, x, y, z))
                            validCoords.add(new ChunkCoordinates(x, y, z));
                    }
                }

            int flowerCount = Math.min(validCoords.size(), this.worldObj.rand.nextBoolean() ? 3 : 4);
            for(int i = 0; i < flowerCount; i++) {
                ChunkCoordinates coords = validCoords.get(this.worldObj.rand.nextInt(validCoords.size()));
                validCoords.remove(coords);
                this.worldObj.setBlock(coords.posX, coords.posY, coords.posZ, ModBlocks.flower, this.worldObj.rand.nextInt(16), 1 | 2);
            }
            this.worldObj.playSoundAtEntity(this, "thaumcraft:wind", 0.5F, 0.9F + this.worldObj.rand.nextFloat() * 0.2F);
            this.setDead();
        } else {
            ThaumicConcilium.proxy.leaves(this);
            for(int i = 0; i < 15; i++) {
                double x = MathHelper.floor_double(posX) - range + this.worldObj.rand.nextInt(range * 2 + 1) + Math.random();
                double y = MathHelper.floor_double(posY) + 1;
                double z = MathHelper.floor_double(posZ) - range + this.worldObj.rand.nextInt(range * 2 + 1) + Math.random();
                float red = (float) Math.random();
                float green = (float) Math.random();
                float blue = (float) Math.random();
                Botania.proxy.wispFX(this.worldObj, x, y, z, red, green, blue, 0.15F + (float) Math.random() * 0.25F, -(float) Math.random() * 0.1F - 0.05F);
            }
        }
    }
}