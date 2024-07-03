package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.PositiveBurstOrbEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.Random;

public class PositiveBurstFoci extends ItemFocusBasic {
    IIcon depthIcon = null;

    public PositiveBurstFoci() {
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setUnlocalizedName("PositiveBurstFoci");
    }

    public String getSortingHelper(ItemStack itemstack) {
        return "PositiveBurst" + super.getSortingHelper(itemstack);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":positive_burst_foci");
        this.depthIcon = ir.registerIcon(ThaumicConcilium.MODID+":positive_burst_depth");
    }

    public IIcon getFocusDepthLayerIcon(ItemStack itemstack) {
        return this.depthIcon;
    }

    public int getActivationCooldown(ItemStack focusstack) {
        return 500;
    }

    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mob) {
        ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
        Vec3 look = p.getLookVec();
        if (!world.isRemote && wand.consumeAllVis(itemstack, p, this.getVisCost(itemstack), true, false)) {
            int amount = 4 + p.worldObj.rand.nextInt(5+wand.getFocusPotency(itemstack));
            for (int i = 0; i < amount; i++){
                PositiveBurstOrbEntity orb = new PositiveBurstOrbEntity(p.worldObj, p, p.posX + look.xCoord, p.posY + p.getEyeHeight(), p.posZ + look.zCoord, itemstack);
                orb.posX -= (double) (Math.cos(p.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                orb.posY -= 0.5;
                orb.posZ -= (double) (Math.sin(p.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
                orb.setPosition(orb.posX, orb.posY, orb.posZ);
                orb.yOffset = 0.0F;
                float f = 0.4F;
                orb.motionX = (double)(-MathHelper.sin(p.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(p.rotationPitch / 180.0F * (float)Math.PI) * f);
                orb.motionZ = (double)(MathHelper.cos(p.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(p.rotationPitch / 180.0F * (float)Math.PI) * f);
                orb.motionY = (double)(-MathHelper.sin((p.rotationPitch) / 180.0F * (float)Math.PI) * f);
                float f2 = MathHelper.sqrt_double(orb.motionX * orb.motionX + orb.motionY * orb.motionY + orb.motionZ * orb.motionZ);
                orb.motionX /= f2;
                orb.motionY /= f2;
                orb.motionZ /= f2;
                orb.motionX += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                orb.motionY += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                orb.motionZ += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                orb.moveEntity(orb.motionX, orb.motionY, orb.motionZ);
                world.spawnEntityInWorld(orb);
            }
            world.playSoundAtEntity(p, "thaumcraft:ice", 0.3F, 0.8F + world.rand.nextFloat() * 0.1F);
        }
        p.swingItem();
        return itemstack;
    }

    public int getFocusColor(ItemStack itemstack) {
        return 10854849;
    }

    public AspectList getVisCost(ItemStack itemstack) {
        Random rand = new Random(System.currentTimeMillis() / 200L);
        AspectList cost = (new AspectList()).add(Aspect.WATER, 300 + rand.nextInt(5 + (2 * this.getUpgradeLevel(itemstack, TCFociUpgrades.vitaminize))) * 50).add(Aspect.EARTH, 300 + rand.nextInt(5 + (2 * this.getUpgradeLevel(itemstack, TCFociUpgrades.fulfillment))) * 50).add(Aspect.ORDER, 400 + rand.nextInt(5 + (2 * this.getUpgradeLevel(itemstack, TCFociUpgrades.vitaminize))) * 50);
        return cost;
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank) {
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
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, TCFociUpgrades.vitaminize, TCFociUpgrades.fulfillment};
            default:
                return null;
        }
    }
}
