package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.MiscHelper;

public class EtherealManipulatorFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.FIRE, 30).add(Aspect.ORDER, 10).add(Aspect.AIR, 30);
    public IIcon iconOrnament;

    public EtherealManipulatorFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID + ":icon");
        this.setUnlocalizedName("EtherealManipulatorFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID + ":ethereal_manipulator_foci");
        this.iconOrnament = ir.registerIcon(ThaumicConcilium.MODID + ":ethereal_manipulator_orn");
    }

    @Override
    public IIcon getOrnament(ItemStack focusstack) {
        return iconOrnament;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int renderPass) {
        return renderPass == 1 ? this.iconOrnament : this.icon;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }


    public String getSortingHelper(ItemStack stack) {
        return "Ethereal" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        return 0xFF00FF;
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

    @Override
    public void onPlayerStoppedUsingFocus(ItemStack stack, World world, EntityPlayer player, int count) {
        NBTTagCompound fociTag = stack.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
        fociTag.setFloat("Range", 5.0F);
        stack.getTagCompound().getCompoundTag("focus").setTag("tag", fociTag);
    }

    public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int i) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
        NBTTagCompound fociTag = stack.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
        if (!player.worldObj.isRemote) {
            if (!fociTag.hasKey("Range")) {
                fociTag.setFloat("Range", 5.0F);
                stack.getTagCompound().getCompoundTag("focus").setTag("tag", fociTag);
            }
        }
        Vector3 target = Vector3.fromEntityCenter(player);

        final float range = fociTag.getFloat("Range");
        final double distance = range - 1;
        target.add(new Vector3(player.getLookVec()).multiply(distance));
        target.y += 0.5;
        Entity e = EntityUtils.getPointedEntity(player.worldObj, player, 1.0, range, 3.0F, true);
        if (e == null) return;
        if (!(e instanceof EntityItem || e instanceof IProjectile || e instanceof EntityFireball)) return;
        if (wand.consumeAllVis(stack, player, getVisCost(stack), !player.worldObj.isRemote, false)) {
                MiscHelper.setEntityMotionFromVector(e, target, 0.3333F);
                ThaumicTinkerer.tcProxy.sparkle((float) e.posX, (float) e.posY, (float) e.posZ, 0);
        }
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack stack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
            case 2:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
            case 3:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
            case 4:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
            case 5:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
        }
        return null;
    }

}