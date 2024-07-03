package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.ShardPowderEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.EntityAspectOrb;

import java.util.List;

public class ShardMill extends Item {
    public IIcon icon;
    long soundDelay = 0L;

    /*
    0 - AIR
    1 - FIRE
    2 - WATER
    3 - EARTH
    4 - ORDER
    5 - ENTROPY
    6 - BALANCED
    7 - WRATH
    8 - ENVY
    9 - TAINT
    10 - PRIDE
    11 - LUST
    12 - SLOTH
    13 - GREED
    14 - GLUTTONY
    */
    public ShardMill() {
        this.setUnlocalizedName("ShardMill");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setNoRepair();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List lst) {
        lst.add(new ItemStack(item, 1, 0));
        lst.add(new ItemStack(item, 1, 1));
    }


    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:blank");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return this.icon;
    }

    @Override
    public EnumRarity getRarity(ItemStack p) {
        return EnumRarity.uncommon;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 0: return StatCollector.translateToLocal("item.ShardMill.name");
            case 1: return StatCollector.translateToLocal("item.SilverShardMill.name");
            default: return StatCollector.translateToLocal("item.ShardMill.name");
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return Integer.MAX_VALUE;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            tag.setInteger("Amount", 0);
            tag.setInteger("Type", 0);
            tag.setInteger("Loaded", -1);
            tag.setBoolean("IsOpen", true);
            stack.setTagCompound(tag);
        } else {
            if (!player.isSneaking()) {
                if (!tag.getBoolean("IsOpen")) {
                    player.setItemInUse(stack, Integer.MAX_VALUE);
                } else if (tag.getInteger("Amount") == 0) {
                    ItemStack shard = null;
                    for (int a = 0; a < 36; a++) {
                        ItemStack item = player.inventory.mainInventory[a];
                        if (item != null) {
                            switch (stack.getItemDamage()) {
                                case 0: {
                                    if (item.getItem() == ConfigItems.itemShard && item.getItemDamage() == tag.getInteger("Type")) {
                                        shard = item;
                                    }
                                    break;
                                }
                                case 1: {
                                    if (item.getItem() == ForbiddenItems.deadlyShards && item.getItemDamage() == tag.getInteger("Type") - 7) {
                                        shard = item;
                                    } else if (item.getItem() == ForbiddenItems.gluttonyShard) {
                                        shard = item;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (shard != null) {
                        player.setItemInUse(stack, 20);
                    }
                }
            } else {
                player.worldObj.playSoundAtEntity(player, ThaumicConcilium.MODID+":clack", 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                tag.setBoolean("IsOpen", !tag.getBoolean("IsOpen"));
                stack.setTagCompound(tag);
            }
        }
        return stack;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        NBTTagCompound tag = stack.getTagCompound();
        int amount = tag.getInteger("Amount");
        int type = tag.getInteger("Type");
        int loaded = tag.getInteger("Loaded");
        int duration = getMaxItemUseDuration(stack) - count;
        if (!tag.getBoolean("IsOpen") && amount > 0 && duration < amount && loaded != -1) {
            if (!player.worldObj.isRemote && this.soundDelay < System.currentTimeMillis()) {
                player.worldObj.playSoundAtEntity(player, ThaumicConcilium.MODID+":mill", 0.5F, 1.0F);
                this.soundDelay = System.currentTimeMillis() + 500L;
            }

            Vec3 look = player.getLookVec();
                int orbs = 2 + player.worldObj.rand.nextInt(3);
                if (!player.worldObj.isRemote) {
                    if (type == 6) {
                        for (int i = 0; i < orbs; i++) {
                            EntityAspectOrb orb = new EntityAspectOrb(player.worldObj, player.posX + look.xCoord, player.posY + player.getEyeHeight(), player.posZ + look.zCoord, Aspect.getPrimalAspects().get(player.worldObj.rand.nextInt(6)), 1);
                            orb.posX -= (double) (Math.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.32F);
                            orb.posY -= 0.5;
                            orb.posZ -= (double) (Math.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.32F);
                            orb.setPosition(orb.posX, orb.posY, orb.posZ);
                            orb.yOffset = 0.0F;
                            float f = 0.4F;
                            orb.motionX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                            orb.motionZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                            orb.motionY = (double)(-MathHelper.sin((player.rotationPitch) / 180.0F * (float)Math.PI) * f);
                            float f2 = MathHelper.sqrt_double(orb.motionX * orb.motionX + orb.motionY * orb.motionY + orb.motionZ * orb.motionZ);
                            orb.motionX /= f2;
                            orb.motionY /= f2;
                            orb.motionZ /= f2;
                            orb.motionX += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                            orb.motionY += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                            orb.motionZ += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                            player.worldObj.spawnEntityInWorld(orb);
                        }
                    } else {
                        for (int i = 0; i < orbs; i++) {
                            ShardPowderEntity orb = new ShardPowderEntity(player, player.posX + (look.xCoord) , player.posY + player.getEyeHeight() + (look.yCoord), player.posZ + (look.zCoord), loaded);
                            orb.posX -= (double) (Math.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.32F);
                            orb.posY -= 0.5;
                            orb.posZ -= (double) (Math.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.32F);
                            orb.setPosition(orb.posX, orb.posY, orb.posZ);
                            orb.yOffset = 0.0F;
                            float f = 0.4F;
                            orb.motionX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                            orb.motionZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f);
                            orb.motionY = (double)(-MathHelper.sin((player.rotationPitch) / 180.0F * (float)Math.PI) * f);
                            float f2 = MathHelper.sqrt_double(orb.motionX * orb.motionX + orb.motionY * orb.motionY + orb.motionZ * orb.motionZ);
                            orb.motionX /= f2;
                            orb.motionY /= f2;
                            orb.motionZ /= f2;
                            orb.motionX += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                            orb.motionY += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                            orb.motionZ += orb.worldObj.rand.nextGaussian() * 0.007499999832361937D * (double)12;
                            player.worldObj.spawnEntityInWorld(orb);
                        }
                    }
                }

        } else {
            if (count == 1) {
                if (tag.getBoolean("IsOpen")) {
                    ItemStack shard = null;
                    boolean found = false;
                    int slot = -1;
                    for (int a = 0; a < 36; a++) {
                        ItemStack item = player.inventory.mainInventory[a];
                        if (item != null) {
                            switch (stack.getItemDamage()) {
                                case 0: {
                                    if (item.getItem() == ConfigItems.itemShard && item.getItemDamage() == tag.getInteger("Type")) {
                                        shard = item;
                                        found = true;
                                        slot = a;
                                    }
                                    break;
                                }
                                case 1: {
                                    if (item.getItem() == ForbiddenItems.deadlyShards && item.getItemDamage() == tag.getInteger("Type") - 7) {
                                        shard = item;
                                        found = true;
                                        slot = a;
                                    }
                                    if (item.getItem() == ForbiddenItems.gluttonyShard && !found) {
                                        shard = item;
                                        found = true;
                                        slot = a;
                                    }
                                    break;
                                }
                            }
                            if (found) break;
                        }
                    }
                    if (shard != null) {
                        player.inventory.mainInventory[slot].stackSize--;
                        if (player.inventory.mainInventory[slot].stackSize == 0) {
                            player.inventory.mainInventory[slot] = null;
                        }
                        player.worldObj.playSoundAtEntity(player, ThaumicConcilium.MODID+":shard_drop", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                        tag.setInteger("Amount", 20);
                        tag.setInteger("Loaded", tag.getInteger("Type"));
                    }
                    stack.setTagCompound(tag);
                }
            }
        }
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count) {
        NBTTagCompound tag = stack.getTagCompound();
        int duration = getMaxItemUseDuration(stack) - count;
        tag.setInteger("Amount", MathHelper.clamp_int(tag.getInteger("Amount") - duration, 0, 20));
        if (tag.getInteger("Amount") == 0){
            tag.setInteger("Loaded", -1);
        }
        stack.setTagCompound(tag);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

}