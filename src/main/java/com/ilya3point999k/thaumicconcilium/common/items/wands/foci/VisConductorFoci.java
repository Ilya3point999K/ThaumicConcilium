package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.client.render.fx.FXColoredLightning;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.MaterialPeeler;
import com.ilya3point999k.thaumicconcilium.common.entities.RiftEntity;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ChargedWispEntity;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import com.ilya3point999k.thaumicconcilium.common.tiles.DestabilizedCrystalTile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class VisConductorFoci extends ItemFocusBasic {

    private static final AspectList COST = (new AspectList()).add(Aspect.WATER, 5).add(Aspect.AIR, 5).add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 5);
    private static final Random rand = new Random(System.currentTimeMillis());
    public static HashMap<String, Entity> hold = new HashMap<>();
    long soundDelay = 0L;
    public IIcon iconDepth;
    public IIcon iconOrnament;


    public VisConductorFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID + ":icon");
        this.setUnlocalizedName("VisConductorFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":vis_conductor_foci");
        this.iconDepth = ir.registerIcon(ThaumicConcilium.MODID+":vis_conductor_depth");
        this.iconOrnament = ir.registerIcon(ThaumicConcilium.MODID+":vis_conductor_orn");
    }

    @Override
    public IIcon getFocusDepthLayerIcon(ItemStack focusstack) {
        return iconDepth;
    }

    @Override
    public IIcon getOrnament(ItemStack focusstack) {
        return iconOrnament;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int renderPass) {
        return renderPass == 1 ? this.icon : this.iconOrnament;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }


    public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack stack) {
        return ItemFocusBasic.WandFocusAnimation.CHARGE;
    }

    public boolean isVisCostPerTick(ItemStack itemstack) {
        return true;
    }

    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mop) {
        p.setItemInUse(itemstack, Integer.MAX_VALUE);
        return itemstack;
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int time) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();

        if (!wand.consumeAllVis(stack, p, COST, false, false)) {
            return;
        }


        if (!this.isUpgradedWith(wand.getFocusItem(stack), TCFociUpgrades.wispLauncher)) {
            NBTTagCompound fociTag = stack.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
            MovingObjectPosition pos = BlockUtils.getTargetBlock(p.worldObj, p, false);
            if (fociTag.hasKey("blockX")) {
                int x = fociTag.getInteger("blockX");
                int y = fociTag.getInteger("blockY");
                int z = fociTag.getInteger("blockZ");
                if (p.getDistanceSq(x, y, z) <= 64.0) {

                    DestabilizedCrystalTile crystal = (DestabilizedCrystalTile) p.worldObj.getTileEntity(x, y, z);
                    if (crystal != null) {
                        if(!crystal.aspect.equalsIgnoreCase(fociTag.getString("aspect"))) p.stopUsingItem();
                        if (!p.worldObj.isRemote && this.soundDelay < System.currentTimeMillis()) {
                            p.worldObj.playSoundAtEntity(p, "thaumcraft:jacobs", 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                            this.soundDelay = System.currentTimeMillis() + 500L;
                        }
                        int potency = wand.getFocusPotency(stack);
                        if (time % 3 == 0) {
                            if (!p.worldObj.isRemote) {
                                wand.consumeAllVis(stack, p, COST, true, false);
                                crystal.capacity = MathHelper.clamp_int( crystal.capacity - (1 + potency), 0, Integer.MAX_VALUE);
                                p.worldObj.markBlockForUpdate(x, y, z);
                                crystal.markDirty();
                            }
                        }
                        if (p.worldObj.isRemote) {
                            int rgb = Aspect.aspects.get(crystal.aspect).getColor();
                            shootLightning(p.worldObj, p, (float) x + 0.5f + (-0.5f + rand.nextFloat()), (float) y + 0.5f + (-0.5f + rand.nextFloat()), z + 0.5f + (-0.5f + rand.nextFloat()), rgb);
                        }
                    }
                } else {
                    p.stopUsingItem();
                }
            } else if (pos != null) {
                if (p.worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) instanceof DestabilizedCrystalTile) {
                    DestabilizedCrystalTile crystal = (DestabilizedCrystalTile) p.worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
                    if (crystal.aspect != null && crystal.capacity > 0) {
                        p.worldObj.markBlockForUpdate(pos.blockX, pos.blockY, pos.blockZ);
                        crystal.markDirty();
                        fociTag.setInteger("blockX", pos.blockX);
                        fociTag.setInteger("blockY", pos.blockY);
                        fociTag.setInteger("blockZ", pos.blockZ);
                        fociTag.setInteger("amount", crystal.capacity);
                        fociTag.setString("aspect", crystal.aspect);
                        stack.getTagCompound().getCompoundTag("focus").setTag("tag", fociTag);
                    }
                }
            }
        } else {
            Entity pointedEntity = EntityUtils.getPointedEntity(p.worldObj, p, 0.0F, 32.0D, 2.0f);
            if (pointedEntity instanceof EntityWisp) {
                if (!p.worldObj.isRemote && this.soundDelay < System.currentTimeMillis()) {
                    p.worldObj.playSoundAtEntity(p, "thaumcraft:jacobs", 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                    this.soundDelay = System.currentTimeMillis() + 500L;
                }
                final String pp = "R" + p.getDisplayName();
                Vec3 look = p.getLookVec();
                double tx = p.posX + look.xCoord * 1.5D;
                double ty = p.posY + look.yCoord * 1.5D + 1.0D;
                double tz = p.posZ + look.zCoord * 1.5D;
                if (!p.worldObj.isRemote) {
                    wand.consumeAllVis(stack, p, COST, true, false);
                    MiscHelper.setEntityMotionFromVector(pointedEntity, new Vector3(tx, ty, tz), 1.0f);
                    NBTTagCompound fociTag = stack.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
                    if (hold.get(pp) == null || pointedEntity.getEntityId() != hold.get(pp).getEntityId()) {
                        hold.put(pp, pointedEntity);
                        fociTag.setBoolean("hold", true);
                    }
                }
                if (p.worldObj.isRemote) {
                    int rgb = Aspect.getAspect(((EntityWisp) pointedEntity).getType()).getColor();
                    shootLightning(p.worldObj, p, (float) pointedEntity.posX + 0.5f + (-0.5f + rand.nextFloat()), (float) pointedEntity.posY + 0.5f + (-0.5f + rand.nextFloat()), pointedEntity.posZ + 0.5f + (-0.5f + rand.nextFloat()), rgb);
                }
            }

        }
    }

    @Override
    public void onPlayerStoppedUsingFocus(ItemStack wandstack, World world, EntityPlayer player, int count) {
        ItemWandCasting wand = (ItemWandCasting) wandstack.getItem();
        NBTTagCompound fociTag = wandstack.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
        if (fociTag.hasKey("hold")) {
            final String pp = "R" + player.getDisplayName();
            if (!world.isRemote) {
                Entity e = hold.get(pp);
                if (e.getDistanceSqToEntity(player) < 6.0f) {
                    ChargedWispEntity wisp = new ChargedWispEntity(world, e.posX, e.posY, e.posZ, player, wandstack);
                    wisp.setType(((EntityWisp) e).getType());
                    e.setDead();
                    world.spawnEntityInWorld(wisp);
                }
                hold.remove(pp);
                fociTag.removeTag("hold");
            }
        } else if (fociTag.hasKey("blockX")) {
                int blockX = fociTag.getInteger("blockX");
                int blockY = fociTag.getInteger("blockY");
                int blockZ = fociTag.getInteger("blockZ");
                if (player.worldObj.getTileEntity(blockX, blockY, blockZ) instanceof DestabilizedCrystalTile) {
                    DestabilizedCrystalTile crystal = (DestabilizedCrystalTile) player.worldObj.getTileEntity(blockX, blockY, blockZ);
                    final Entity look = EntityUtils.getPointedEntity(player.worldObj, player, 0.0D, 32.0D, 1.1F, true);
                    if (look == null) {
                        if (!world.isRemote) {
                            player.worldObj.setBlock(blockX, blockY, blockZ, Blocks.air, 0, 7);
                            player.worldObj.removeTileEntity(blockX, blockY, blockZ);
                            boolean var2 = player.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                            player.worldObj.createExplosion(null, blockX, blockY, blockZ, 2.0F, var2);
                        }
                    } else {
                        if (this.isUpgradedWith(wand.getFocusItem(wandstack), TCFociUpgrades.dematerialization)) {
                            int amount = fociTag.getInteger("amount");
                            int rgb = Aspect.aspects.get(fociTag.getString("aspect")).getColor();
                            if (look instanceof EntityLivingBase) {
                                if (!world.isRemote) {
                                    look.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), (amount - crystal.capacity) / (6.0f - wand.getFocusPotency(wandstack)));
                                    player.worldObj.playSoundAtEntity(player, "thaumcraft:shock", 0.8F, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                                    TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) look.posX, (float) look.posY + 0.5f, (float) look.posZ, (float) player.posX, (float) player.posY - 0.5f, (float) player.posZ, rgb, 0.2F), new NetworkRegistry.TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));
                                }
                            }
                            if (crystal.aspect.equals(Aspect.AURA.getTag())) {
                                if (look instanceof RiftEntity) {
                                    if (!world.isRemote){
                                        ((RiftEntity) look).setCollapsing(((RiftEntity) look).getRiftSize() - (amount * (1 + wand.getFocusPotency(wandstack))));
                                        TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) look.posX, (float) look.posY + 0.5f, (float) look.posZ, (float) player.posX, (float) player.posY + 0.5f, (float) player.posZ, rgb, 0.2F), new NetworkRegistry.TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));
                                    }
                                }
                                if (look instanceof MaterialPeeler) {
                                    if (!world.isRemote) {
                                        look.playSound("thaumcraft:craftfail", 1.0F, 1.0F);
                                        int x = (int) (look.posX + look.worldObj.rand.nextGaussian() * 1.5);
                                        int y = (int) look.posY;
                                        int z = (int) (look.posZ + look.worldObj.rand.nextGaussian() * 1.5);

                                        for (int a = 0; a < 10; ++a) {
                                            int xx = x + (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                                            int zz = z + (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                                            if (look.worldObj.rand.nextBoolean() && look.worldObj.getBiomeGenForCoords(xx, zz) != ThaumcraftWorldGenerator.biomeTaint) {
                                                Utils.setBiomeAt(look.worldObj, xx, zz, ThaumcraftWorldGenerator.biomeTaint);
                                                if (look.worldObj.isBlockNormalCubeDefault(xx, y - 1, zz, false) && look.worldObj.getBlock(xx, y, zz).isReplaceable(look.worldObj, xx, y, zz)) {
                                                    look.worldObj.setBlock(xx, y, zz, ConfigBlocks.blockTaintFibres, 0, 3);
                                                }
                                                if (look.worldObj.isAirBlock(xx, y + 1, zz)) {
                                                    if (look.worldObj.rand.nextBoolean()) {
                                                        look.worldObj.setBlock(xx, y + 1, zz, ConfigBlocks.blockFluxGas, 0, 3);
                                                    } else {
                                                        look.worldObj.setBlock(xx, y + 1, zz, ConfigBlocks.blockFluxGoo, 0, 3);
                                                    }
                                                }
                                            }
                                        }
                                        TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning((float) look.posX, (float) look.posY + 0.5f, (float) look.posZ, (float) player.posX, (float) player.posY + 0.5f, (float) player.posZ, rgb, 0.2F), new NetworkRegistry.TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0));
                                        look.setDead();
                                    } else {
                                        Thaumcraft.proxy.burst(player.worldObj, look.posX, look.posY, look.posZ, 2.0F);
                                    }
                                }
                            }
                        }
                    }
                    player.worldObj.markBlockForUpdate(crystal.xCoord, crystal.yCoord, crystal.zCoord);
                    crystal.markDirty();
                }
                fociTag.removeTag("blockX");
                fociTag.removeTag("blockY");
                fociTag.removeTag("blockZ");
                fociTag.removeTag("amount");
                fociTag.removeTag("aspect");

        }
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "VISCONDUCTOR" + super.getSortingHelper(itemstack);
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0xAAAAAA;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        return COST;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        super.addInformation(stack, player, list, par4);
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack stack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, TCFociUpgrades.wispLauncher, TCFociUpgrades.dematerialization};
            case 2:
            case 3:
            case 4:
            case 5:
                if (this.isUpgradedWith(stack, TCFociUpgrades.wispLauncher)) {
                    return new FocusUpgradeType[]{FocusUpgradeType.potency, FocusUpgradeType.frugal, FocusUpgradeType.extend};
                } else {
                    return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
                }
        }
        return null;
    }

    @Override
    public boolean canApplyUpgrade(ItemStack focusstack, EntityPlayer player, FocusUpgradeType type, int rank) {
        return !type.equals(TCFociUpgrades.dematerialization) || ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "DEMATUPGRADE");
    }

    public static void shootLightning(World world, EntityLivingBase entityplayer, double xx, double yy, double zz, int rgb) {
        double px = entityplayer.posX;
        double py = entityplayer.posY;
        double pz = entityplayer.posZ;

        if (entityplayer.getEntityId() != FMLClientHandler.instance().getClient().thePlayer.getEntityId()) {
            py = entityplayer.boundingBox.minY + (double) (entityplayer.height / 2.0F) + 0.25;
        }

        px += (double) (-MathHelper.cos(entityplayer.rotationYaw / 180.0F * 3.141593F) * 0.06F);
        py += -0.05999999865889549;
        pz += (double) (-MathHelper.sin(entityplayer.rotationYaw / 180.0F * 3.141593F) * 0.06F);
        if (entityplayer.getEntityId() != FMLClientHandler.instance().getClient().thePlayer.getEntityId()) {
            py = entityplayer.boundingBox.minY + (double) (entityplayer.height / 2.0F) + 0.25;
        }

        Vec3 vec3d = entityplayer.getLook(1.0F);
        px += vec3d.xCoord * 0.3;
        py += vec3d.yCoord * 0.3;
        pz += vec3d.zCoord * 0.3;

        FXColoredLightning bolt = new FXColoredLightning(world, px, py, pz, xx, yy, zz, world.rand.nextLong(), 6, 0.5F, 8);
        bolt.defaultFractal();
        bolt.setType(10);
        bolt.setWidth(0.125F);

        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        bolt.setRBGColorF(red / 255.0F, green / 255.0F, blue / 255.0F);
        bolt.finalizeBolt();
    }

}