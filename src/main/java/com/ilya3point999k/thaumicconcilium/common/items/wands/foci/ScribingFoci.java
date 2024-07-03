package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketRunicCharge;
import thaumcraft.common.lib.utils.EntityUtils;

public class ScribingFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.EARTH, 50).add(Aspect.FIRE, 50).add(Aspect.AIR, 60);
    IIcon iconDepth;
    public ScribingFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setUnlocalizedName("ScribingFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":scribing_foci");
        this.iconDepth = ir.registerIcon(ThaumicConcilium.MODID+":scribing_foci_depth");
    }
    @Override
    public IIcon getFocusDepthLayerIcon(ItemStack focusstack) {
        return iconDepth;
    }

    public String getSortingHelper(ItemStack stack) {
        return "SCRIBING" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        return 0x111111;
    }

    public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack stack) {
        return WandFocusAnimation.WAVE;

    }

    public AspectList getVisCost(ItemStack stack) {
        return COST;
    }

    public boolean isVisCostPerTick(ItemStack itemstack) {
        return true;
    }

    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mop) {
        p.setItemInUse(itemstack, Integer.MAX_VALUE);
        return itemstack;
    }

    public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int i) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
        if (i % 10 == 0) {
            if (wand.consumeAllVis(stack, player, getVisCost(stack), false, false)) {
                Entity pointedEntity = EntityUtils.getPointedEntity(player.worldObj, player, 0.0F, 5.0D + (5.0D * wand.getFocusEnlarge(stack)), 0.1F);
                if (pointedEntity != null) {
                    if (!pointedEntity.worldObj.isRemote) {
                        if (pointedEntity instanceof EntityPlayer) {
                            wand.consumeAllVis(stack, player, getVisCost(stack), true, false);
                            int max = Thaumcraft.instance.runicEventHandler.runicInfo.get(pointedEntity.getEntityId())[0];
                            if (max != 0) {
                                int add = Thaumcraft.instance.runicEventHandler.runicCharge.get(pointedEntity.getEntityId()) + (2 + wand.getFocusPotency(stack));
                                int runicCharge = Math.min(max, add);
                                Thaumcraft.instance.runicEventHandler.runicCharge.put(pointedEntity.getEntityId(), runicCharge);
                                PacketHandler.INSTANCE.sendTo(new PacketRunicCharge(player, (short) runicCharge, Thaumcraft.instance.runicEventHandler.runicInfo.get(pointedEntity.getEntityId())[0]), (EntityPlayerMP) pointedEntity);
                            }
                        }
                    } else {
                        if(pointedEntity instanceof EntityPlayer) {
                            player.worldObj.playSound(player.posX, player.posY + 1, player.posZ, "thaumcraft:write", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F), false);
                            ThaumicConcilium.proxy.runeFlow(player, pointedEntity);
                        }
                    }
                }
            }
        }

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
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
        }
        return null;
    }

}