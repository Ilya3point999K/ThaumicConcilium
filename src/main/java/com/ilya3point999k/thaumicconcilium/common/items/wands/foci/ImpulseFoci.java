package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.CompressedBlastEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ImpulseFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.FIRE, 500).add(Aspect.AIR, 500).add(Aspect.ENTROPY, 600);
    private static final AspectList VACUUM_COST = new AspectList().add(Aspect.FIRE, 2000).add(Aspect.AIR, 2000).add(Aspect.ENTROPY, 3500);
    private static final AspectList PNEUMATIC_COST = new AspectList().add(Aspect.FIRE, 200).add(Aspect.AIR, 750).add(Aspect.ENTROPY, 300);
    public IIcon iconOrnament;
    public IIcon iconOrnamentDiluted;
    public IIcon iconOrnamentInversed;
    public IIcon depthIcon;
    public IIcon invertedDepthIcon;
    public IIcon pneumoDepthIcon;

    public ImpulseFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID+":impulse_foci");
        this.setUnlocalizedName("ImpulseFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":impulse_foci");
        this.iconOrnament = ir.registerIcon(ThaumicConcilium.MODID+":impulse_orn");
        this.iconOrnamentDiluted = ir.registerIcon(ThaumicConcilium.MODID+":impulse_orn_diluted");
        this.iconOrnamentInversed = ir.registerIcon(ThaumicConcilium.MODID+":impulse_orn_inversed");

        this.depthIcon = ir.registerIcon(ThaumicConcilium.MODID+":impulse_depth");
        this.invertedDepthIcon = ir.registerIcon(ThaumicConcilium.MODID+":vacuum_impulse_depth");
        this.pneumoDepthIcon = ir.registerIcon(ThaumicConcilium.MODID+":pneumo_depth");
    }

    public IIcon getFocusDepthLayerIcon(ItemStack stack) {
        return this.isUpgradedWith(stack, TCFociUpgrades.vacuum) ? this.invertedDepthIcon : (this.isUpgradedWith(stack, TCFociUpgrades.pneumoStrike) ? this.pneumoDepthIcon : this.depthIcon);
    }

    public String getSortingHelper(ItemStack stack) {
        return "IMPULSE" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        return this.isUpgradedWith(stack, TCFociUpgrades.pneumoStrike) ? 0xFF9999 : 0xFF3333;
    }

    @Override
    public IIcon getOrnament(ItemStack focusstack) {
        return this.isUpgradedWith(focusstack, TCFociUpgrades.vacuum) ? this.iconOrnamentInversed : (this.isUpgradedWith(focusstack, TCFociUpgrades.pneumoStrike) ? this.iconOrnamentDiluted : this.iconOrnament);
    }

    public int getActivationCooldown(ItemStack focusstack) {
        return 1000;
    }

    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mob) {
        ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
        if (!world.isRemote) {
            if (wand.consumeAllVis(itemstack, p, this.getVisCost(itemstack), false, false)) {
                    wand.consumeAllVis(itemstack, p, this.getVisCost(itemstack), true, false);
                    p.worldObj.spawnEntityInWorld(new CompressedBlastEntity(p.worldObj, p.posX, p.posY + p.getEyeHeight(), p.posZ, p, null, itemstack));
            }
        }
        p.swingItem();
        return itemstack;
    }


    public AspectList getVisCost(ItemStack itemstack) {
        return this.isUpgradedWith(itemstack, TCFociUpgrades.vacuum) ? VACUUM_COST : (this.isUpgradedWith(itemstack, TCFociUpgrades.pneumoStrike) ? PNEUMATIC_COST : COST);
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.extend};
            case 2:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.extend, FocusUpgradeType.potency};
            case 3:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
            case 4:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
            case 5:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, TCFociUpgrades.pneumoStrike, TCFociUpgrades.vacuum};
            default:
                return null;
        }
    }
}
