package com.ilya3point999k.thaumicconcilium.common.entities;

import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.WrathAttractionFoci;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.foci.ItemFocusExcavation;
import thaumcraft.common.lib.utils.BlockUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GolemBydlo extends EntityGolem {
    static final ItemStack pick = new ItemStack(ConfigItems.itemPrimalCrusher);
    public boolean inactive;
    public int action;

    public int leftArm;
    public int rightArm;

    public GolemBydlo(World p_i1686_1_) {
        super(p_i1686_1_);
        this.setSize(2.0F, 3.5F);
        this.inactive = false;
        this.action = 0;
        this.leftArm = 0;
        this.rightArm = 0;
        this.tasks.taskEntries.clear();
    }

    protected void entityInit() {
        super.entityInit();

    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (10 + this.rand.nextInt(15)));

        if (flag) {
            p_70652_1_.motionY += 0.4000000059604645D;
        }

        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.action > 0) {
            --this.action;
        }

        if (this.leftArm > 0) {
            --this.leftArm;
        }

        if (this.rightArm > 0) {
            --this.rightArm;
        }

        int y1 = MathHelper.floor_double(this.posY);
        if (y1 < 0 || this.riddenByEntity == null) {
            this.inactive = true;
        } else {
            this.inactive = false;
        }
        if (!this.worldObj.isRemote) {

            if (this.getDistanceSq((double) this.getHomePosition().posX, (double) this.getHomePosition().posY, (double) this.getHomePosition().posZ) >= 2304.0 || this.isEntityInsideOpaqueBlock()) {
                int var1 = MathHelper.floor_double((double) this.getHomePosition().posX);
                int var2 = MathHelper.floor_double((double) this.getHomePosition().posZ);
                int var3 = MathHelper.floor_double((double) this.getHomePosition().posY);

                for (int var0 = 1; var0 >= -1; --var0) {
                    for (int var4 = -1; var4 <= 1; ++var4) {
                        for (int var5 = -1; var5 <= 1; ++var5) {
                            World var10000 = this.worldObj;
                            if (World.doesBlockHaveSolidTopSurface(this.worldObj, var1 + var4, var3 - 1 + var0, var2 + var5) && !this.worldObj.isBlockNormalCubeDefault(var1 + var4, var3 + var0, var2 + var5, false)) {
                                this.setLocationAndAngles((double) ((float) (var1 + var4) + 0.5F), (double) var3 + (double) var0, (double) ((float) (var2 + var5) + 0.5F), this.rotationYaw, this.rotationPitch);
                                this.getNavigator().clearPathEntity();
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (this.riddenByEntity != null) {
            if (this.riddenByEntity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) this.riddenByEntity;
                if (player.isSwingInProgress && this.action == 0) {
                    ItemStack wandstack = player.getHeldItem();
                    if (wandstack != null) {
                        if (!(wandstack.getItem() instanceof ItemWandCasting)) {
                            return;
                        }
                        ItemStack focus = ((ItemWandCasting) wandstack.getItem()).getFocusItem(wandstack);
                        if (focus != null) {
                            if (focus.getItem() instanceof ItemFocusExcavation) {
                                int fortune = ((ItemFocusExcavation) focus.getItem()).getUpgradeLevel(focus, FocusUpgradeType.treasure);
                                int silktouch = ((ItemFocusExcavation) focus.getItem()).getUpgradeLevel(focus, FocusUpgradeType.silktouch);
                                MovingObjectPosition mop = BlockUtils.getTargetBlock(player.worldObj, player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, false, 5.0);
                                if (mop != null) {
                                    this.action = 6;
                                    if (!this.worldObj.isRemote) {
                                        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
                                        int x = mop.blockX;
                                        int y = mop.blockY;
                                        int z = mop.blockZ;
                                        int side = mop.sideHit;
                                        int md = player.worldObj.getBlockMetadata(x, y, z);
                                        Block bi = player.worldObj.getBlock(x, y, z);
                                        if (ForgeHooks.isToolEffective(pick, bi, md)) {
                                            for (int aa = -2; aa <= 2; ++aa) {
                                                for (int bb = -2; bb <= 2; ++bb) {
                                                    int xx = 0;
                                                    int yy = 0;
                                                    int zz = 0;
                                                    if (side <= 1) {
                                                        xx = aa;
                                                        zz = bb;
                                                    } else if (side <= 3) {
                                                        xx = aa;
                                                        yy = bb;
                                                    } else {
                                                        zz = aa;
                                                        yy = bb;
                                                    }

                                                    if (player.worldObj.canMineBlock(player, x + xx, y + yy, z + zz)) {
                                                        Block bl = player.worldObj.getBlock(x + xx, y + yy, z + zz);
                                                        md = player.worldObj.getBlockMetadata(x + xx, y + yy, z + zz);
                                                        if (bl.getBlockHardness(player.worldObj, x + xx, y + yy, z + zz) >= 0.0F && (ForgeHooks.isToolEffective(pick, bl, md))) {
                                                            if (silktouch == 0) {
                                                                BlockUtils.dropBlockAsItemWithChance(player.worldObj, bl, x + xx, y + yy, z + zz, md, 1.0F, fortune, player);
                                                                bl.dropXpOnBlockBreak(player.worldObj, x + xx, y + yy, z + zz, bl.getExpDrop(player.worldObj, md, fortune));
                                                            } else if (bl.canSilkHarvest(player.worldObj, player, x + xx, y + yy, z + zz, md)) {
                                                                ArrayList<ItemStack> items = new ArrayList();
                                                                ItemStack itemstack = BlockUtils.createStackedBlock(bl, md);
                                                                if (itemstack != null) {
                                                                    items.add(itemstack);
                                                                }

                                                                ForgeEventFactory.fireBlockHarvesting(items, player.worldObj, bl, x + xx, y + yy, z + zz, md, 0, 1.0F, true, player);
                                                                Iterator i$ = items.iterator();

                                                                while (i$.hasNext()) {
                                                                    ItemStack is = (ItemStack) i$.next();
                                                                    BlockUtils.dropBlockAsItem(player.worldObj, x + xx, y + yy, z + zz, is, bl);
                                                                }
                                                            }
                                                            player.worldObj.setBlockToAir(x + xx, y + yy, z + zz);
                                                            player.worldObj.playAuxSFX(2001, x + xx, y + yy, z + zz, Block.getIdFromBlock(bl) + (md << 12));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (focus.getItem() instanceof WrathAttractionFoci) {
                                this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
                                this.action = 6;
                                List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(2.0, 1.0, 2.0));
                                list.remove(this);
                                list.remove(player);
                                for (EntityLivingBase e : list){
                                    this.attackEntityAsMob(e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
            this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            p_70612_1_ = ((EntityLivingBase) this.riddenByEntity).moveStrafing * 0.5F;
            p_70612_2_ = ((EntityLivingBase) this.riddenByEntity).moveForward;


            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if (!this.worldObj.isRemote) {
                this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

            if (f4 > 1.0F) {
                f4 = 1.0F;
            }

            this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        } else {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
        }
    }

    public void updateRiderPosition() {
        super.updateRiderPosition();
        float f = MathHelper.sin(this.renderYawOffset * (float) Math.PI / 180.0F);
        float f1 = MathHelper.cos(this.renderYawOffset * (float) Math.PI / 180.0F);
        float f2 = 0.5F;
        float f3 = 0.15F;

        this.riddenByEntity.setPosition(this.posX + (double) (f2 * f), this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (double) f3, this.posZ - (double) (f2 * f1));

        if (this.riddenByEntity instanceof EntityLivingBase) {
            ((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
        }
    }

    public boolean interact(EntityPlayer player) {
        if (!player.isSneaking() && (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemNameTag))) {
            if (this.riddenByEntity == null) {
                if (!this.worldObj.isRemote) {
                    player.mountEntity(this);
                }
                return true;
            } else {
                return super.interact(player);
            }
        } else {
            return false;
        }
    }


    public boolean isAIEnabled() {
        return this.inactive;
    }

    protected boolean canDespawn() {
        return false;
    }

    protected void despawnEntity() {
    }

    protected String getHurtSound() {
        return "mob.irongolem.hit";
    }

    protected String getDeathSound() {
        return "mob.irongolem.death";
    }


    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }


    public int getGolemType() {
        return 0;
    }

    public int getActionTimer() {
        // return 3 - Math.abs(this.action - 3);
        return this.action;
    }


}
