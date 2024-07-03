package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.WrathEffectEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.ArrayList;

public class WrathAttractionFoci extends ItemFocusBasic {
	private static final AspectList COST = new AspectList().add(Aspect.FIRE, 500).add(Aspect.ENTROPY, 1000);
	public IIcon iconDepth;

	public WrathAttractionFoci() {
		super();
		this.setCreativeTab(ThaumicConcilium.tabTC);
		this.setTextureName(ThaumicConcilium.MODID+":wrath_attraction_foci");
		this.setUnlocalizedName("WrathAttractionFoci");
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		this.icon = ir.registerIcon(ThaumicConcilium.MODID+":wrath_attraction_foci");
		this.iconDepth = ir.registerIcon(ThaumicConcilium.MODID+":wrath_attraction_depth");
	}

	@Override
	public IIcon getFocusDepthLayerIcon(ItemStack focusstack) {
		return this.iconDepth;
	}

	public String getSortingHelper(ItemStack stack) {
		return "WrAttr" + super.getSortingHelper(stack);
	}

	public int getFocusColor(ItemStack stack) {
		return 0xFF4500;
	}

	public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack stack) {
		return ItemFocusBasic.WandFocusAnimation.WAVE;
	}

	public AspectList getVisCost(ItemStack stack) {
		return COST;
	}

	public int getActivationCooldown(ItemStack stack) {
		return 50;
	}

	public boolean isVisCostPerTick(ItemStack itemstack) {
		return false;
	}

	public ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer player, MovingObjectPosition mop) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		if (wand.consumeAllVis(stack, player, getVisCost(stack), !player.worldObj.isRemote, false)) {
			ArrayList<EntityMob> entities;
			world.playSoundAtEntity(player, ThaumicConcilium.MODID+":ira", 0.9F, 1.0F);
			if (!player.worldObj.isRemote) {
				boolean burnout = this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.burnout);
				WrathEffectEntity effect = new WrathEffectEntity(player.worldObj, player, burnout);
				effect.setPosition(player.posX, player.posY, player.posZ);
				player.worldObj.spawnEntityInWorld(effect);
				double range = 5.0 + 5.0 * this.getUpgradeLevel(wand.getFocusItem(stack), FocusUpgradeType.enlarge);
				entities = (ArrayList<EntityMob>) player.worldObj.getEntitiesWithinAABB(EntityMob.class, player.boundingBox.expand(range, range, range));
				for (EntityMob en : entities) {
					en.setAttackTarget(player);
					if(burnout){
						en.addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 3));
					}
				}
			}

			player.swingItem();
		}
		return stack;
	}

	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack stack, int rank) {
		switch (rank) {
		case 1:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
		case 2:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
		case 3:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
		case 4:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
		case 5:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, TCFociUpgrades.burnout };
		}
		return null;
	}
}
