package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.entities.projectile.EntityFrostShard;
import thaumcraft.common.items.wands.ItemWandCasting;

public class CoolingFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.WATER, 3);
    private static final AspectList REFREEZE_COST = new AspectList().add(Aspect.WATER, 100).add(Aspect.AIR, 150);
    public IIcon iconDepth;
    public IIcon iconRefreezeDepth;

    public CoolingFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID + ":cooling_foci");
        this.setUnlocalizedName("CoolingFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID + ":cooling_foci");
        this.iconDepth = ir.registerIcon(ThaumicConcilium.MODID + ":cooling_depth");
        this.iconRefreezeDepth = ir.registerIcon(ThaumicConcilium.MODID + ":refreeze_depth");
    }

    public IIcon getFocusDepthLayerIcon(ItemStack stack) {
        return this.isUpgradedWith(stack, TCFociUpgrades.refreeze) ? this.iconRefreezeDepth : this.iconDepth;
    }

    public String getSortingHelper(ItemStack stack) {
        return "COOLING" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        return 0xEEEEEE;
    }

    public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack stack) {
        return WandFocusAnimation.CHARGE;
    }

    public AspectList getVisCost(ItemStack stack) {
        return this.isUpgradedWith(stack, TCFociUpgrades.refreeze) ? REFREEZE_COST : COST;
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

        if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.refreeze)) {
            if (i % (10 - wand.getFocusPotency(stack)) == 0) {
                if (!player.worldObj.isRemote) {
                    if (wand.consumeAllVis(stack, player, getVisCost(stack), false, false)) {
                        wand.consumeAllVis(stack, player, getVisCost(stack), true, false);
                        EntityFrostShard shard = new EntityFrostShard(player.worldObj, player, 1.5F);
                        shard.setDamage(1.0F);
                        shard.fragile = true;
                        player.worldObj.spawnEntityInWorld(shard);
                        player.worldObj.playSoundAtEntity(shard, "thaumcraft:ice", 0.4F, 1.0F + player.worldObj.rand.nextFloat() * 0.1F);
                    }
                }
            }
        } else {
            if (i % (7 - wand.getFocusPotency(stack)) == 0) {
                if (!player.worldObj.isRemote) {
                    if (wand.consumeAllVis(stack, player, getVisCost(stack), false, false)) {
                        wand.consumeAllVis(stack, player, getVisCost(stack), true, false);
                        player.worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                        player.worldObj.spawnEntityInWorld(new EntitySnowball(player.worldObj, player));
                    }
                }
            }
        }
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack stack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[]{FocusUpgradeType.potency};
            case 2:
                return new FocusUpgradeType[]{FocusUpgradeType.potency};
            case 3:
                return new FocusUpgradeType[]{FocusUpgradeType.potency};
            case 4:
                return new FocusUpgradeType[]{FocusUpgradeType.potency};
            case 5:
                return new FocusUpgradeType[]{FocusUpgradeType.potency, TCFociUpgrades.refreeze};
        }
        return null;
    }

}
