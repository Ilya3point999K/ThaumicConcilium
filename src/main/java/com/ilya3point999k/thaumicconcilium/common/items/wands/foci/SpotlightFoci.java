package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import baubles.api.BaublesApi;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import java.util.HashMap;

public class SpotlightFoci extends ItemFocusBasic {
	private static final AspectList COST = new AspectList().add(Aspect.FIRE, 550).add(Aspect.ORDER, 100).add(Aspect.AIR, 600);
	private static final AspectList WARMING_COST = new AspectList().add(Aspect.FIRE, 150).add(Aspect.ORDER, 100).add(Aspect.AIR, 150).add(Aspect.WATER, 100).add(Aspect.EARTH, 100);
	private static final AspectList PURE_COST = new AspectList().add(Aspect.FIRE, 50).add(Aspect.ORDER, 150).add(Aspect.AIR, 600).add(Aspect.WATER, 300).add(Aspect.EARTH, 300);
	static HashMap<String, Object> beam;
	long soundDelay = 0L;


	public SpotlightFoci() {
		super();
		this.setCreativeTab(ThaumicConcilium.tabTC);
		this.setTextureName(ThaumicConcilium.MODID+":spotlight_foci");
		this.setUnlocalizedName("SpotlightFoci");
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		this.icon = ir.registerIcon(ThaumicConcilium.MODID+":spotlight_foci");
	}

	public String getSortingHelper (ItemStack stack)
    {
        return "SpLght" + super.getSortingHelper(stack);
    }
	
	public int getFocusColor(ItemStack stack) {
		return 0xFFFF00;
	}

	public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack stack) {
		return ItemFocusBasic.WandFocusAnimation.CHARGE;

	}

	public AspectList getVisCost(ItemStack stack) {
		return this.isUpgradedWith(stack, TCFociUpgrades.warmingUp) ? WARMING_COST : (this.isUpgradedWith(stack, TCFociUpgrades.taintPurification) ? PURE_COST : COST);
	}

	public boolean isVisCostPerTick(ItemStack itemstack) {
		return true;
	}

	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mop) {
		p.setItemInUse(itemstack, Integer.MAX_VALUE);
		return itemstack;
	}

	public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int i) {
		ItemStack ring = BaublesApi.getBaubles(player).getStackInSlot(1);
		ItemStack ring2 = BaublesApi.getBaubles(player).getStackInSlot(2);
		if ((ring != null && ring.getItem() == TCItemRegistry.ringOfBlusteringLight)
				|| (ring2 != null && ring2.getItem() == TCItemRegistry.ringOfBlusteringLight)) {

			ItemWandCasting wand = (ItemWandCasting) stack.getItem();
			if (wand.consumeAllVis(stack, player, getVisCost(stack), !player.worldObj.isRemote, false)) {
				String pp = "R" + player.getCommandSenderName();
				if (!player.worldObj.isRemote) {
					pp = "S" + player.getCommandSenderName();
				}
				if (!player.worldObj.isRemote && this.soundDelay < System.currentTimeMillis()) {
					player.worldObj.playSoundAtEntity(player, ThaumicConcilium.MODID+":hum", 0.5F, 1.0F);
					this.soundDelay = System.currentTimeMillis() + 500L;
				}
				Entity pointedEntity = EntityUtils.getPointedEntity(player.worldObj, player, 0.0F, 50.0D, 0.1F);
				MovingObjectPosition mop = BlockUtils.getTargetBlock(player.worldObj, player, false);
				Vec3 look = player.getLookVec();
				double tx = player.posX + look.xCoord * 50.0D;
				double ty = player.posY + look.yCoord * 50.0D;
				double tz = player.posZ + look.zCoord * 50.0D;
				if (mop != null) {
					tx = mop.hitVec.xCoord;
					ty = mop.hitVec.yCoord;
					tz = mop.hitVec.zCoord;
					if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.taintPurification)){
						int xx = MathHelper.floor_double(tx);
						int zz = MathHelper.floor_double(tz);
						if (player.worldObj.getBiomeGenForCoords(xx, zz) == ThaumcraftWorldGenerator.biomeTaint) {
							if (!player.worldObj.isRemote) {
								Utils.setBiomeAt(player.worldObj, xx, zz, ThaumcraftWorldGenerator.biomeMagicalForest);
							}
						}
					}
				}
				if (pointedEntity != null && !pointedEntity.worldObj.isRemote) {
					if (pointedEntity instanceof EntityLivingBase) {
						if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.warmingUp)) {
							((EntityLivingBase)pointedEntity).addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 5 + wand.getFocusPotency(stack)));
						}else
						if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.taintPurification)) {
							Object entity = null;
							if (pointedEntity instanceof EntityTaintCreeper) {
								entity = new EntityCreeper(pointedEntity.worldObj);
							} else if (pointedEntity instanceof EntityTaintSheep) {
								entity = new EntitySheep(pointedEntity.worldObj);
							} else if (pointedEntity instanceof EntityTaintCow) {
								entity = new EntityCow(pointedEntity.worldObj);
							} else if (pointedEntity instanceof EntityTaintPig) {
								entity = new EntityPig(pointedEntity.worldObj);
							} else if (pointedEntity instanceof EntityTaintChicken) {
								entity = new EntityChicken(pointedEntity.worldObj);
							} else if (pointedEntity instanceof EntityTaintVillager) {
								entity = new EntityVillager(pointedEntity.worldObj);
							} else if (pointedEntity instanceof ITaintedMob || pointedEntity instanceof IEldritchMob) {
								if (pointedEntity.hurtResistantTime == 0 && pointedEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), 0)) {
									((EntityLivingBase) pointedEntity).setHealth(((EntityLivingBase) pointedEntity).getHealth() - (1.0F + ((Integer.MAX_VALUE - i) / 10.0F) * wand.getFocusPotency(stack)));
									if ((((EntityLivingBase) pointedEntity).getHealth() <= 0F) && !pointedEntity.isDead) {
										((EntityLivingBase) pointedEntity).onDeath(DamageSource.causePlayerDamage(player));
									}
								}
							}
							if (entity != null) {
								((Entity)entity).setLocationAndAngles(pointedEntity.posX, pointedEntity.posY, pointedEntity.posZ, pointedEntity.rotationYaw, 0.0F);
								pointedEntity.worldObj.spawnEntityInWorld((Entity)entity);
								pointedEntity.setDead();
							}
						}else {
							if (pointedEntity.hurtResistantTime == 0 && pointedEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), 0)) {
								((EntityLivingBase) pointedEntity).setHealth(((EntityLivingBase) pointedEntity).getHealth() - (1.0F + ((Integer.MAX_VALUE - i) / 20.0F) * wand.getFocusPotency(stack)));
								if ((((EntityLivingBase) pointedEntity).getHealth() <= 0F) && !pointedEntity.isDead){
									((EntityLivingBase) pointedEntity).onDeath(DamageSource.causePlayerDamage(player));
								}
							}
						}
					}
				}
				if (player.worldObj.isRemote) {
					beam.put(pp, Thaumcraft.proxy.beamCont(player.worldObj, player, tx, ty, tz, 1, 0xFFFF00, false, 1.0f, beam.get(pp), 1));
				}
			}
		} else {
			if (!player.worldObj.isRemote) {
				player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("TC.no_ring")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
			}
		}
	}

	@Override
	public void onPlayerStoppedUsingFocus(ItemStack wandstack, World world, EntityPlayer player, int count) {
		WandManager.setCooldown(player, 1000 + (Integer.MAX_VALUE - count) * 100);
		String pp = "R" + player.getCommandSenderName();
		if (!player.worldObj.isRemote) {
			pp = "S" + player.getCommandSenderName();
		}
		beam.put(pp, null);
	}

	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack stack, int rank) {
		switch (rank) {
		case 1:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency };
		case 2:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency };
		case 3:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency };
		case 4:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency };
		case 5:
			return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, TCFociUpgrades.warmingUp, TCFociUpgrades.taintPurification};
		}
		return null;
	}

	static {
		beam = new HashMap<>();
	}
}
