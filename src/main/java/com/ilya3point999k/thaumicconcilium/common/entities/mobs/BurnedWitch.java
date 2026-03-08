package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.brewing.EntityBrew;
import com.emoniph.witchery.common.ExtendedPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class BurnedWitch extends EntityFlying implements IMob {
    public static ItemStack brewAttack;
    public static ItemStack brewAttackVamps;
    public static ItemStack brewAttackWolfs;
    public static ItemStack brewIllFitting;
    private static final ItemStack[] witchDrops = new ItemStack[]{
            new ItemStack(Items.blaze_powder),
            new ItemStack(Items.ghast_tear),
            new ItemStack(Witchery.Items.GENERIC, 1, 28),
            new ItemStack(Witchery.Items.GENERIC, 1, 30),
            new ItemStack(Witchery.Items.GENERIC, 1, 35),
    };
    public double orbitAngle = 0.0D;
    public double orbitRadius = 0.0D;
    public double orbitHeight = 8.0D;
    public double orbitSpeed = 0.06D;
    public double orbitRadiusMin = 4.0D;
    public double orbitRadiusMax = 8.0D;
    public double orbitBobAmplitude = 0.6D;
    public double orbitBobSpeed = 2.0D;

    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity;

    private int aggroCooldown;
    public int prevAttackCounter;
    public int attackCounter;

    public BurnedWitch(World p_i1735_1_) {
        super(p_i1735_1_);
        this.setSize(1.0F, 2.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 10;

    }

    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!worldObj.isRemote) {
            if (!this.isPotionActive(Witchery.Potions.GAS_MASK)) {
                this.addPotionEffect(new PotionEffect(Witchery.Potions.GAS_MASK.id, 65536, 1));
            }
        }
    }

    protected void updateEntityActionState() {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }

        this.despawnEntity();
        this.prevAttackCounter = this.attackCounter;

        if (this.targetedEntity != null && this.targetedEntity.isDead) {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 100.0D);

            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
                this.orbitRadius = this.orbitRadiusMin + this.rand.nextDouble() * (this.orbitRadiusMax - this.orbitRadiusMin);
                this.orbitAngle = this.rand.nextDouble() * Math.PI * 2.0D;
            }
        }

        if (this.targetedEntity != null) {
            this.orbitAngle += this.orbitSpeed;

            double orbitX = this.targetedEntity.posX + Math.cos(this.orbitAngle) * this.orbitRadius;
            double orbitZ = this.targetedEntity.posZ + Math.sin(this.orbitAngle) * this.orbitRadius;

            double baseY = this.targetedEntity.posY + this.orbitHeight;
            double bob = Math.sin(this.orbitAngle * this.orbitBobSpeed) * this.orbitBobAmplitude;
            double orbitY = baseY + bob;

            this.waypointX = orbitX;
            this.waypointY = orbitY;
            this.waypointZ = orbitZ;

            if (this.courseChangeCooldown > 3) {
                this.courseChangeCooldown = 0;
            }
        } else {
            double d0 = this.waypointX - this.posX;
            double d1 = this.waypointY - this.posY;
            double d2 = this.waypointZ - this.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;

            if (d3 < 1.0D || d3 > 3600.0D) {
                this.waypointX = this.posX + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.waypointY = this.posY + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.waypointZ = this.posZ + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            }
        }

        double dx = this.waypointX - this.posX;
        double dy = this.waypointY - this.posY;
        double dz = this.waypointZ - this.posZ;
        double distSq = dx * dx + dy * dy + dz * dz;

        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
            double dist = (double) MathHelper.sqrt_double(distSq);

            if (dist > 0.0D && this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, dist)) {
                this.motionX += dx / dist * 0.1D;
                this.motionY += dy / dist * 0.1D;
                this.motionZ += dz / dist * 0.1D;
            } else {
                this.waypointX = this.posX;
                this.waypointY = this.posY;
                this.waypointZ = this.posZ;
            }
        }

        double seeRangeSq = 64.0D;
        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < seeRangeSq * seeRangeSq) {
            double tx = this.targetedEntity.posX - this.posX;
            double tz = this.targetedEntity.posZ - this.posZ;
            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(tx, tz)) * 180.0F / (float) Math.PI;

            if (this.canEntityBeSeen(this.targetedEntity)) {
                if (this.attackCounter == 10) {
                    this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1007, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
                }

                ++this.attackCounter;

                if (this.attackCounter == 10) {
                    if (this.targetedEntity instanceof EntityPlayer) {
                        ExtendedPlayer playerEx = ExtendedPlayer.get((EntityPlayer) this.targetedEntity);

                        int r = worldObj.rand.nextInt(3);
                        switch (r) {
                            case 0: {
                                throwPotion(brewIllFitting);
                                break;
                            }
                            case 1: {
                                if (playerEx.isVampire()) {
                                    throwPotion(brewAttackVamps);
                                } else if (playerEx.getWerewolfLevel() > 0) {
                                    throwPotion(brewAttackWolfs);
                                } else {
                                    throwPotion(brewAttack);
                                }
                                break;
                            }
                            case 2: {
                                throwPotion(brewAttack);
                                break;
                            }
                        }
                    }
                    this.attackCounter = -20;
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        } else {
            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float) Math.PI;

            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }
    }

    private void throwPotion(ItemStack potion){
        EntityBrew brew = new EntityBrew(worldObj, this, potion, false);
        double bdx = this.targetedEntity.posX - this.posX;
        double bdz = this.targetedEntity.posZ - this.posZ;
        double bdy = this.targetedEntity.posY + this.targetedEntity.getEyeHeight() - 1.1D - brew.posY;

        float dist = MathHelper.sqrt_double(bdx * bdx + bdz * bdz);

        brew.setThrowableHeading(bdx, bdy + dist * 0.2F, bdz, 0.75F, 8.0F);
        this.worldObj.spawnEntityInWorld(brew);
    }

    private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_) {
        double d4 = (this.waypointX - this.posX) / p_70790_7_;
        double d5 = (this.waypointY - this.posY) / p_70790_7_;
        double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
        AxisAlignedBB axisalignedbb = this.boundingBox.copy();

        for (int i = 1; (double) i < p_70790_7_; ++i) {
            axisalignedbb.offset(d4, d5, d6);

            if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    protected String getLivingSound() {
        return "mob.ghast.moan";
    }

    protected String getHurtSound() {
        return "mob.ghast.scream";
    }

    protected String getDeathSound() {
        return "mob.ghast.death";
    }

    @Override
    protected float getSoundPitch() {
        return 1.5F;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int j = super.rand.nextInt(3) + 1;

        for (int k = 0; k < j; ++k) {
            int l = super.rand.nextInt(3);
            ItemStack i1 = witchDrops[super.rand.nextInt(witchDrops.length)];
            if (par2 > 0) {
                l += super.rand.nextInt(par2 + 1);
            }
            for (int j1 = 0; j1 < l; ++j1) {
                this.entityDropItem(i1, 1.5F);
            }
        }

    }

    @Override
    protected void updateFallState(double y, boolean onGround) {}

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    static {
        brewAttack = new ItemStack(Witchery.Items.BREW);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        NBTTagCompound itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_wart).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 37).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 29).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.diamond).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_star).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 151).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 11).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.fish, 1, 2).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.fermented_spider_eye).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.speckled_melon).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 114).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 35).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        //new ItemStack(Witchery.Items.GENERIC, 1, 111).writeToNBT(itemtag);
        new ItemStack(Witchery.Items.GENERIC, 1, 24).writeToNBT(itemtag);
        //new ItemStack(Items.gunpowder).writeToNBT(itemtag);
        list.appendTag(itemtag);
        tag.setTag("Items", list);

        brewAttack.setTagCompound(tag);


        brewAttackWolfs = new ItemStack(Witchery.Items.BREW);
        tag = new NBTTagCompound();
        list = new NBTTagList();

        itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_wart).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 37).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 29).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.diamond).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_star).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 151).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 11).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.fish, 1, 2).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 157).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 114).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 35).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        //new ItemStack(Witchery.Items.GENERIC, 1, 111).writeToNBT(itemtag);
        new ItemStack(Witchery.Items.GENERIC, 1, 24).writeToNBT(itemtag);
        //new ItemStack(Items.gunpowder).writeToNBT(itemtag);
        list.appendTag(itemtag);
        tag.setTag("Items", list);

        brewAttackWolfs.setTagCompound(tag);

        brewAttackVamps = new ItemStack(Witchery.Items.BREW);
        tag = new NBTTagCompound();
        list = new NBTTagList();

        itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_wart).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 37).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 29).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.diamond).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_star).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 151).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 11).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.fish, 1, 2).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.SEEDS_GARLIC).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 11).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.fish, 1, 2).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.fermented_spider_eye).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.speckled_melon).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 114).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 35).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        //new ItemStack(Witchery.Items.GENERIC, 1, 111).writeToNBT(itemtag);
        new ItemStack(Witchery.Items.GENERIC, 1, 24).writeToNBT(itemtag);
        //new ItemStack(Items.gunpowder).writeToNBT(itemtag);
        list.appendTag(itemtag);
        tag.setTag("Items", list);

        brewAttackVamps.setTagCompound(tag);

        brewIllFitting = new ItemStack(Witchery.Items.BREW);
        tag = new NBTTagCompound();
        list = new NBTTagList();

        itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_wart).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 37).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 29).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.diamond).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.nether_star).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 151).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 11).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Blocks.BRAMBLE, 1, 1).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 114).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 35).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.glowstone_dust).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.blaze_rod).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Blocks.GLINT_WEED).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Witchery.Items.GENERIC, 1, 21).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.dye, 1, 4).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Blocks.end_stone).writeToNBT(itemtag);
        list.appendTag(itemtag);

        itemtag = new NBTTagCompound();
        new ItemStack(Items.gunpowder).writeToNBT(itemtag);
        list.appendTag(itemtag);

        list.appendTag(itemtag);
        tag.setTag("Items", list);

        brewIllFitting.setTagCompound(tag);


    }
}
