package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.TaintSpiderSmart;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXTaintsplosion;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.BlockCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.monster.EntityMindSpider;
import thaumcraft.common.entities.monster.EntityTaintSpider;
import thaumcraft.common.entities.monster.EntityTaintSwarm;
import thaumcraft.common.entities.monster.EntityTaintacle;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;
import thaumcraft.common.lib.utils.EntityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaintAnimationFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.WATER, 100).add(Aspect.EARTH, 100).add(Aspect.ENTROPY, 100);
    private static final AspectList THOUGHT_COST = new AspectList().add(Aspect.WATER, 130).add(Aspect.EARTH, 130).add(Aspect.ENTROPY, 130);
    private static final AspectList VAPOR_COST = new AspectList().add(Aspect.WATER, 130).add(Aspect.EARTH, 130).add(Aspect.ENTROPY, 130).add(Aspect.FIRE, 50);

    public IIcon iconDepth;
    public IIcon iconOrnament;

    public TaintAnimationFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID+":taint_animation_foci");
        this.setUnlocalizedName("TaintAnimationFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":taint_animation_foci");
        this.iconDepth = ir.registerIcon(ThaumicConcilium.MODID+":taint_animation_depth");
        this.iconOrnament = ir.registerIcon(ThaumicConcilium.MODID+":taint_animation_orn");
    }

    @Override
    public IIcon getFocusDepthLayerIcon(ItemStack focusstack) {
        return iconDepth;
    }

    @Override
    public IIcon getOrnament(ItemStack focusstack) {
        return iconOrnament;
    }

    public String getSortingHelper(ItemStack stack) {
        return "TAINTANIMATION" + super.getSortingHelper(stack);
    }

    public int getFocusColor() {
        return 0xEEEEEE;
    }

    public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack stack) {
        return WandFocusAnimation.WAVE;
    }

    public AspectList getVisCost(ItemStack stack) {
        return this.isUpgradedWith(stack, TCFociUpgrades.thoughtManifestation) ? THOUGHT_COST : (this.isUpgradedWith(stack, TCFociUpgrades.fluxVaporization) ? VAPOR_COST : COST);
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
        if(i % 10 == 0) {
            if (wand.consumeAllVis(stack, player, getVisCost(stack), false, false)) {
                if (!player.worldObj.isRemote) {
                    wand.consumeAllVis(stack, player, getVisCost(stack), true, false);
                    int distance = 4 + 2 * this.getUpgradeLevel(wand.getFocusItem(stack), FocusUpgradeType.enlarge);
                    if(player.isSneaking()){
                        List<EntityMob> list = player.worldObj.getEntitiesWithinAABB(EntityMob.class, player.boundingBox.expand(16.0, 16.0, 16.0));
                        final Entity look = EntityUtils.getPointedEntity(player.worldObj, player, 0.0D, 32.0D, 0.2F);
                        for(EntityMob e : list){
                            if(e instanceof ITaintedMob){
                                if (e instanceof EntityTaintSpider && !(e instanceof TaintSpiderSmart)){
                                    TaintSpiderSmart spider = new TaintSpiderSmart(player.worldObj);
                                    spider.setLocationAndAngles(e.posX, e.posY, e.posZ, e.rotationYaw, e.rotationPitch);
                                    player.worldObj.spawnEntityInWorld(spider);
                                    e.setDead();
                                }
                                if(!e.equals(look) && look instanceof EntityLivingBase && !(look instanceof ITaintedMob)) {
                                    e.setTarget(look);
                                    int potency = this.getUpgradeLevel(wand.getFocusItem(stack), FocusUpgradeType.potency);
                                    if (potency > 0) {
                                        int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(player.getCommandSenderName());
                                        potency = MathHelper.clamp_int((warp / 10) * potency, 1, 200);
                                        e.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 100, potency));
                                    }
                                }
                            }
                        }
                    }
                    else {
                        if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.thoughtManifestation)){
                            List<EntityMindSpider> list = player.worldObj.getEntitiesWithinAABB(EntityMindSpider.class, player.boundingBox.expand(16.0, 16.0, 16.0));
                            if (!list.isEmpty()) {
                                Collections.shuffle(list);
                                EntityTaintSpider taintSpider = new TaintSpiderSmart(player.worldObj);
                                EntityMindSpider spider = list.get(0);
                                taintSpider.setLocationAndAngles(spider.posX, spider.posY, spider.posZ, spider.rotationYaw, 0.0F);
                                spider.setDead();
                                player.worldObj.spawnEntityInWorld(taintSpider);
                                TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXTaintsplosion(taintSpider.posX, taintSpider.posY, taintSpider.posZ), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, taintSpider.posX, taintSpider.posY, taintSpider.posZ, 32.0));
                            }
                        }
                        ArrayList<BlockCoordinates> checklist = new ArrayList();
                        int xCoord = (int) player.posX;
                        int yCoord = (int) player.posY;
                        int zCoord = (int) player.posZ;
                        for (int x = xCoord - distance; x <= xCoord + distance; x++) {
                            for (int y = yCoord - distance; y <= yCoord + distance; y++) {
                                for (int z = zCoord - distance; z <= zCoord + distance; z++) {
                                    Block block = player.worldObj.getBlock(x, y, z);
                                    if (block == ConfigBlocks.blockFluxGas || block == ConfigBlocks.blockTaintFibres || block == ConfigBlocks.blockFluxGoo) {
                                        checklist.add(new BlockCoordinates(x, y, z));
                                    }
                                }
                            }
                        }
                        if (checklist.isEmpty()) return;
                        Collections.shuffle(checklist);
                        int x = checklist.get(0).x;
                        int y = checklist.get(0).y;
                        int z = checklist.get(0).z;
                        if (!player.worldObj.isAirBlock(x, y, z) && player.getDistance((double) x + 0.5, (double) y + 0.5, (double) z + 0.5) < (double) (distance * distance)) {
                            if (player.worldObj.getBlock(x, y, z) == ConfigBlocks.blockTaintFibres) {
                                final Entity look = EntityUtils.getPointedEntity(player.worldObj, player, 0.0D, 32.0D, 1.1F);
                                int lmd = player.worldObj.getBlockMetadata(x, y, z);
                                if (lmd > 0) {
                                    player.worldObj.setBlockMetadataWithNotify(x, y, z, lmd - 1, 3);
                                } else {
                                    player.worldObj.setBlockToAir(x, y, z);
                                }
                                EntityTaintacle taintacle = new EntityTaintacle(player.worldObj);
                                taintacle.setLocationAndAngles(x, y + 0.5, z, player.worldObj.rand.nextFloat() * 360.0F, 0.0F);
                                taintacle.setTarget(look);
                                player.worldObj.spawnEntityInWorld(taintacle);
                                PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y, z, 14483711), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, x, y, z, 32.0));
                                return;
                            }
                            if (player.worldObj.getBlock(x, y, z) == ConfigBlocks.blockFluxGas) {
                                final Entity look = EntityUtils.getPointedEntity(player.worldObj, player, 0.0D, 32.0D, 1.1F);
                                player.worldObj.setBlockToAir(x, y, z);
                                EntityTaintSwarm swarm = new EntityTaintSwarm(player.worldObj);
                                swarm.setLocationAndAngles(x, y + 0.5, z, player.worldObj.rand.nextFloat() * 360.0F, 0.0F);
                                swarm.setTarget(look);
                                player.worldObj.spawnEntityInWorld(swarm);
                                PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y, z, 14483711), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, x, y, z, 32.0));
                            }
                            if (this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.fluxVaporization)){
                                if (player.worldObj.getBlock(x, y, z) == ConfigBlocks.blockFluxGoo){
                                    player.worldObj.setBlockToAir(x, y, z);
                                    player.worldObj.setBlock(x, y, z, ConfigBlocks.blockFluxGas, 0 ,3);
                                }
                            }
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
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge, TCFociUpgrades.fluxVaporization, TCFociUpgrades.thoughtManifestation};
        }
        return null;
    }
}
