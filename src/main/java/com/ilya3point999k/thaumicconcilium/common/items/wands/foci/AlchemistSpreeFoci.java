package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import baubles.api.BaublesApi;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.DistortionEffectEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.BottleOfClearWaterEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.BottleOfThickTaintEntity;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXTaintsplosion;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.entities.monster.EntityTaintSpider;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.item.kami.foci.ItemFocusShadowbeam;

import java.util.List;

public class AlchemistSpreeFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.WATER, 100).add(Aspect.ENTROPY, 200).add(Aspect.ORDER, 200);
    long soundDelay = 0L;
    IIcon depthIcon = null;

    public AlchemistSpreeFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setUnlocalizedName("AlchemistSpreeFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":alchemist_spree_foci");
        this.depthIcon = ir.registerIcon(ThaumicConcilium.MODID+":alchemist_spree_depth");

    }

    public IIcon getFocusDepthLayerIcon(ItemStack itemstack) {
        return this.depthIcon;
    }

    public String getSortingHelper(ItemStack stack) {
        return "SPREE" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        return 0xEEEEEE;
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
        ItemStack ring = BaublesApi.getBaubles(player).getStackInSlot(1);
        ItemStack ring2 = BaublesApi.getBaubles(player).getStackInSlot(2);
        if ((ring != null && ring.getItem() == TCItemRegistry.ringOfBlusteringLight)
                || (ring2 != null && ring2.getItem() == TCItemRegistry.ringOfBlusteringLight)) {
            if (wand.consumeAllVis(stack, player, getVisCost(stack), false, false)) {
                if (!player.worldObj.isRemote) {

                    List<DistortionEffectEntity> effects = player.worldObj.getEntitiesWithinAABB(DistortionEffectEntity.class, player.boundingBox.expand(2.0, 2.0, 2.0));
                    if (effects.isEmpty()) {
                        DistortionEffectEntity effect = new DistortionEffectEntity(player.worldObj);
                        effect.setOwner(player.getCommandSenderName());
                        effect.setPosition(player.posX, player.posY, player.posZ);
                        player.worldObj.spawnEntityInWorld(effect);
                    }
                    if (this.soundDelay < System.currentTimeMillis()) {
                        player.worldObj.playSoundAtEntity(player, ThaumicConcilium.MODID + ":bubbles", 0.5F, 1.0F);
                        this.soundDelay = System.currentTimeMillis() + 500L;
                    }

                    wand.consumeAllVis(stack, player, getVisCost(stack), true, false);
                    List<IProjectile> list = player.worldObj.getEntitiesWithinAABB(IProjectile.class, player.boundingBox.expand(3.0, 1.5, 3.0));
                    if (!list.isEmpty()) {
                        for (IProjectile e : list) {
                            if (e instanceof EntitySnowball) {
                                EntityFrostShard shard = new EntityFrostShard(player.worldObj, player, 1.5F);
                                shard.setDamage(1.0f + (float) wand.getFocusPotency(stack));
                                if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.primalEssence)) {
                                    shard.setFrosty(1 + wand.getFocusPotency(stack));
                                }
                                player.worldObj.spawnEntityInWorld(shard);
                                ((EntitySnowball)e).setDead();
                            }
                            if (e instanceof EntityEgg) {
                                EntityEgg egg = (EntityEgg) e;
                                EntityTaintSpider taintSpider = new EntityTaintSpider(player.worldObj);
                                taintSpider.setLocationAndAngles(egg.posX, egg.posY, egg.posZ, egg.rotationYaw, 0.0F);
                                egg.setDead();
                                player.worldObj.spawnEntityInWorld(taintSpider);
                                TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXTaintsplosion(taintSpider.posX, taintSpider.posY, taintSpider.posZ), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, taintSpider.posX, taintSpider.posY, taintSpider.posZ, 32.0));
                            }
                            if (e instanceof EntityEmber) {
                                EntityExplosiveOrb orb = new EntityExplosiveOrb(player.worldObj, player);
                                orb.motionX += player.worldObj.rand.nextGaussian() * 0.1;
                                orb.motionZ += player.worldObj.rand.nextGaussian() * 0.1;
                                orb.strength = (wand.getFocusPotency(stack) + 1) * ((EntityEmber) e).damage;
                                orb.onFire = this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.primalEssence);

                                ((EntityEmber)e).setDead();
                                player.worldObj.spawnEntityInWorld(orb);
                            }
                            if (e instanceof EntityPrimalOrb) {
                                ((EntityPrimalOrb)e).setDead();
                                EntityPechBlast orb = new EntityPechBlast(player.worldObj, player, wand.getFocusPotency(stack), wand.getFocusPotency(stack) * 60, this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.primalEssence));
                                player.worldObj.spawnEntityInWorld(orb);
                            }
                            if (e instanceof EntityEldritchOrb) {
                                ((EntityEldritchOrb)e).setDead();
                                ItemFocusShadowbeam.Beam beam = new ItemFocusShadowbeam.Beam(player.worldObj, player, (wand.getFocusPotency(stack) + 1) * 3);
                                beam.updateUntilDead();
                            }
                            if (e instanceof BottleOfClearWaterEntity) {
                                ((BottleOfClearWaterEntity)e).setDead();
                                player.worldObj.spawnEntityInWorld(new BottleOfThickTaintEntity(player.worldObj, player));
                            }
                        }
                    }
                }
            }
        } else {
            if (!player.worldObj.isRemote) {
                player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("TC.no_ring")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
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
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge, TCFociUpgrades.primalEssence};
            case 4:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
            case 5:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
        }
        return null;
    }

}