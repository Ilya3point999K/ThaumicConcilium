package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketAspectPool;
import thaumcraft.common.lib.research.ResearchManager;
import thaumic.tinkerer.common.core.helper.ExperienceHelper;

import java.util.List;

public class ReflectionFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList();
    private IIcon depthIcon = null;

    private static final int XP = 10;
    public ReflectionFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID+":reflection_foci");
        this.setUnlocalizedName("ReflectionFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":reflection_foci");
        this.depthIcon = ir.registerIcon(ThaumicConcilium.MODID+":reflection_foci_depth");
    }

    public IIcon getFocusDepthLayerIcon(ItemStack stack) {
        return this.depthIcon;
    }

    public String getSortingHelper(ItemStack stack) {
        return "REFLECTION" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        return 0;
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

        if (!player.worldObj.isRemote) {
            if (i % 20 == 0) {
                player.worldObj.playSoundAtEntity(player, "thaumcraft:whispers", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }
            if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.oblivion)) {
                if(i % 5 == 0) {
                    Aspect aspect = Aspect.getCompoundAspects().get(player.worldObj.rand.nextInt(Aspect.getCompoundAspects().size()));
                    if (Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(player.getCommandSenderName(), aspect) > 0) {
                        addAspectToKnowledgePool(player, aspect, (short) -1);
                        player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("TC.oblivion_response") + " " + StatCollector.translateToLocal("tc.aspect.help." + aspect.getTag())).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                        Thaumcraft.proxy.getPlayerKnowledge().addWarpTemp(player.getCommandSenderName(), 10 + (wand.getFocusPotency(stack) * 2));
                    }
                }
            } else {
                if (player.experienceTotal >= (XP - wand.getFocusFrugal(stack) + wand.getFocusPotency(stack))) {
                    ExperienceHelper.drainPlayerXP(player, (XP - wand.getFocusFrugal(stack)));
                    Thaumcraft.proxy.getPlayerKnowledge().addWarpTemp(player.getCommandSenderName(), 1 + wand.getFocusPotency(stack));
                }
            }
        }
    }

    public static void addAspectToKnowledgePool(EntityPlayer addedTo, Aspect added, short amount)
    {
        Thaumcraft.proxy.playerKnowledge.addAspectPool(addedTo.getCommandSenderName(), added, amount);
        ResearchManager.scheduleSave(addedTo);
        if(addedTo instanceof EntityPlayerMP)
            PacketHandler.INSTANCE.sendTo(new PacketAspectPool(added.getTag(), Short.valueOf(amount), Short.valueOf(Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(addedTo.getCommandSenderName(), added))), (EntityPlayerMP)addedTo);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        super.addInformation(stack, player, list, par4);
        list.add(StatCollector.translateToLocal("item.Focus.cost2"));
        if(((ItemFocusBasic)stack.getItem()).isUpgradedWith(stack, TCFociUpgrades.oblivion)){
            list.add(" " + EnumChatFormatting.LIGHT_PURPLE + StatCollector.translateToLocal("TC.oblivion_cost") + EnumChatFormatting.WHITE + " x " + 1);
        }else {
            list.add(" " + EnumChatFormatting.GREEN + StatCollector.translateToLocal("ttmisc.experience") + EnumChatFormatting.WHITE + " x " + (XP - ((ItemFocusBasic)stack.getItem()).getUpgradeLevel(stack, FocusUpgradeType.frugal) + ((ItemFocusBasic)stack.getItem()).getUpgradeLevel(stack, FocusUpgradeType.potency)));
        }
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack stack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
            case 2:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
            case 3:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
            case 4:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
            case 5:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, TCFociUpgrades.oblivion};
        }
        return null;
    }
}
