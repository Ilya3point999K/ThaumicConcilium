package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import baubles.api.BaublesApi;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ConcentratedWarpChargeEntity;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.util.ArrayList;

public class WarpExtractionFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.WATER, 50).add(Aspect.ENTROPY, 60)
            .add(Aspect.AIR, 50);
    IIcon depthIcon = null;

    public WarpExtractionFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setUnlocalizedName("WarpExtractionFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":warp_extraction_foci");
        this.depthIcon = ir.registerIcon(ThaumicConcilium.MODID+":warp_extraction_depth");
    }

    public IIcon getFocusDepthLayerIcon(ItemStack itemstack) {
        return this.depthIcon;
    }

    public String getSortingHelper(ItemStack stack) {
        return "WrExtr" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        return 0x5D016B;
    }

    public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack stack) {
        return ItemFocusBasic.WandFocusAnimation.CHARGE;
    }

    public AspectList getVisCost(ItemStack stack) {
        return COST;
    }

    public boolean isVisCostPerTick(ItemStack itemstack) {
        return true;
    }

    @Override
    public int getActivationCooldown(ItemStack focusstack) {
        return 0;
    }

    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mop) {
        ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
        ItemStack ring = BaublesApi.getBaubles(p).getStackInSlot(1);
        ItemStack ring2 = BaublesApi.getBaubles(p).getStackInSlot(2);
        if ((ring != null && ring.getItem() == TCItemRegistry.ringOfBlusteringLight)
                || (ring2 != null && ring2.getItem() == TCItemRegistry.ringOfBlusteringLight)) {
            if (p.isSneaking()) {
                ArrayList<ConcentratedWarpChargeEntity> list = (ArrayList<ConcentratedWarpChargeEntity>) p.worldObj.getEntitiesWithinAABB(ConcentratedWarpChargeEntity.class, p.boundingBox.expand(10, 10, 10));
                if (!list.isEmpty()) {
                    for (ConcentratedWarpChargeEntity e : list) {
                        if (!p.worldObj.isRemote) {
                            if (e.getOwner().getCommandSenderName().equals(p.getCommandSenderName())) {
                                e.setDead();
                            }
                        }
                        p.swingItem();
                        return itemstack;
                    }
                }
                if (wand.consumeAllVis(itemstack, p, getVisCost(itemstack), true, false)) {
                    world.playSoundAtEntity(p, ThaumicConcilium.MODID + ":breath", 0.9F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                    if (!p.worldObj.isRemote) {
                        ConcentratedWarpChargeEntity charge = new ConcentratedWarpChargeEntity(p.posX, p.posY, p.posZ, p, itemstack);
                        charge.setOwner(p.getCommandSenderName());
                        p.worldObj.spawnEntityInWorld(charge);
                    }
                }
                p.swingItem();
                return itemstack;
            }
            p.setItemInUse(itemstack, Integer.MAX_VALUE);
        } else {
            if (!world.isRemote) {
                p.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("TC.no_ring")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
            }
        }
        return itemstack;
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int count) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
        if (!player.worldObj.isRemote) {
            Entity pointedEntity = EntityUtils.getPointedEntity(player.worldObj, player, 0.0F, 32.0D, 2.0f);
            if (pointedEntity != null) {
                if (pointedEntity instanceof ConcentratedWarpChargeEntity) {
                    ConcentratedWarpChargeEntity charge = (ConcentratedWarpChargeEntity) pointedEntity;
                    if (charge.byForce) return;
                    if (charge.getOwner() != null) {
                        if (charge.getOwner().getCommandSenderName().equals(player.getCommandSenderName())) {
                            if (!wand.consumeAllVis(stack, player, COST, true, false)) {
                                return;
                            }
                            Vec3 look = player.getLookVec();
                            double tx = player.posX + look.xCoord * 1.5D;
                            double ty = player.posY + look.yCoord * 1.5D + 2.0;
                            double tz = player.posZ + look.zCoord * 1.5D;
                            MiscHelper.setEntityMotionFromVector(charge, new Vector3(tx, ty, tz), 1.0f);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onPlayerStoppedUsingFocus(ItemStack wandstack, World world, EntityPlayer player, int count) {
        super.onPlayerStoppedUsingFocus(wandstack, world, player, count);
        if (!player.worldObj.isRemote) {
            ArrayList<ConcentratedWarpChargeEntity> list = (ArrayList<ConcentratedWarpChargeEntity>) player.worldObj.getEntitiesWithinAABB(ConcentratedWarpChargeEntity.class, player.boundingBox.expand(10, 10, 10));
            if (!list.isEmpty()) {
                for (ConcentratedWarpChargeEntity e : list) {
                    if (e.getOwner().getCommandSenderName().equals(player.getCommandSenderName())) {
                        Vec3 look = player.getLookVec();
                        double tx = look.xCoord * 2.5D;
                        double ty = look.yCoord * 2.5D;
                        double tz = look.zCoord * 2.5D;
                        e.motionX = tx;
                        e.motionY = ty;
                        e.motionZ = tz;
                    }
                }
            }
        }
		/*
		player.addPotionEffect(new PotionEffect(Config.potionVisExhaustID, 2000, 5));
		player.addPotionEffect(new PotionEffect(Config.potionThaumarhiaID, 2000, 5));
		player.addPotionEffect(new PotionEffect(Config.potionUnHungerID, 5000, 5));
		player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 1000, 3));
		*/
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack stack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
            case 2:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
            case 3:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
            case 4:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
            case 5:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, TCFociUpgrades.massHysteria, TCFociUpgrades.selfFlagellation};
        }
        return null;
    }


}
