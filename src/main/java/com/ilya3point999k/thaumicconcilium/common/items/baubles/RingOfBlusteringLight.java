package com.ilya3point999k.thaumicconcilium.common.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ItemRunic;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.InventoryUtils;

public class RingOfBlusteringLight extends ItemRunic implements IBauble {

	public RingOfBlusteringLight() {
		super(50);
		this.setCreativeTab(ThaumicConcilium.tabTC);
		this.setTextureName(ThaumicConcilium.MODID+":ring_of_blustering_light");
		this.setMaxDamage(-1);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("RingOfBlusteringLight");
	}

	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.epic;
	}

	@Override
	public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		// TODO Auto-generated method stub
		return BaubleType.RING;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {
		if (arg1.ticksExisted % 3 == 0) {
			if (arg1 instanceof EntityPlayer) {
				EntityPlayer par1EntityPlayer = (EntityPlayer) arg1;
				if (!par1EntityPlayer.worldObj.isRemote) {
					Aspect aspect = null;
					switch (par1EntityPlayer.worldObj.rand.nextInt(6)) {
						case 0:
							aspect = Aspect.AIR;
							break;
						case 1:
							aspect = Aspect.EARTH;
							break;
						case 2:
							aspect = Aspect.FIRE;
							break;
						case 3:
							aspect = Aspect.WATER;
							break;
						case 4:
							aspect = Aspect.ORDER;
							break;
						case 5:
							aspect = Aspect.ENTROPY;
					}
					int slot = InventoryUtils.isWandInHotbarWithRoom(aspect, 1, par1EntityPlayer);
					if (slot >= 0) {
						ItemWandCasting wand = (ItemWandCasting) par1EntityPlayer.inventory.mainInventory[slot].getItem();
						wand.addVis(par1EntityPlayer.inventory.mainInventory[slot], aspect, 1, true);
					}
				}
			}
		}
	}

	@Override
	public int getRunicCharge(ItemStack itemstack) {
		return 50;
	}
}
