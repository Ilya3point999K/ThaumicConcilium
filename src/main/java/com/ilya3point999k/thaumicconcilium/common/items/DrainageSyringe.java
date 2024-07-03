package com.ilya3point999k.thaumicconcilium.common.items;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.InventoryUtils;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class DrainageSyringe extends Item implements IManaGivingItem {
    public IIcon icon;
    public static final int MAXVIS = 100;
    public static final int MAXMANA = 250000;
    public static final int MAXBLOOD = 500000;

    public static final AspectList VIS_COST = new AspectList().add(Aspect.WATER, 5000).add(Aspect.EARTH, 5000).add(Aspect.FIRE, 5000).add(Aspect.AIR, 5000).add(Aspect.ORDER, 5000).add(Aspect.ENTROPY, 5000);

    //0 - VIS, 1 - MANA, 2 - BLOOD
    public DrainageSyringe() {
        this.setUnlocalizedName("DrainageSyringe");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setNoRepair();
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.uncommon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:blank");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return this.icon;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }

    public int getMaxItemUseDuration(ItemStack itemstack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            tag.setInteger("Amount", 0);
            tag.setInteger("Type", 0);
            tag.setBoolean("IsOpen", false);
            stack.setTagCompound(tag);
        } else {
            if (!player.isSneaking()) {
                if (tag.getBoolean("IsOpen")) {
                    player.setItemInUse(stack, Integer.MAX_VALUE);
                } else {
                    Entity e = EntityUtils.getPointedEntity(player.worldObj, player, 0.0, 3.0, 0.1F);
                    if (e instanceof EntityLivingBase) {
                        player.worldObj.playSoundAtEntity(player, ThaumicConcilium.MODID + ":stab", 1.0F, player.worldObj.rand.nextFloat());
                        player.setItemInUse(stack, Integer.MAX_VALUE);
                    }
                }
            } else {
                tag.setBoolean("IsOpen", !tag.getBoolean("IsOpen"));
                stack.setTagCompound(tag);
            }
        }
        return stack;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (!(entityLiving instanceof EntityPlayer)) {
            return super.onEntitySwing(entityLiving, stack);
        }
        EntityPlayer player = (EntityPlayer) entityLiving;
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            tag.setInteger("Amount", 0);
            tag.setInteger("Type", 0);
            tag.setBoolean("IsOpen", false);
            stack.setTagCompound(tag);
        } else {
            if (!player.isSneaking()) {
                return super.onEntitySwing(entityLiving, stack);
            } else {
                if (tag.getInteger("Amount") == 0) {
                    int type = tag.getInteger("Type") + 1;
                    if (type > 2) type = 0;
                    tag.setInteger("Type", type);
                    stack.setTagCompound(tag);
                }
            }
        }
        return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        NBTTagCompound tag = stack.getTagCompound();
        if (!tag.getBoolean("IsOpen")) {
            if (count % 40 == 0) {
                Entity e = EntityUtils.getPointedEntity(player.worldObj, player, 0.0, 3.0, 0.1F);
                if (e instanceof EntityLivingBase) {
                    EntityLivingBase living = (EntityLivingBase) e;
                    if (!player.worldObj.isRemote && !(living instanceof EntityGolem) && living.getHealth() > living.getMaxHealth() / 2.0F && living.attackEntityFrom(DamageSource.causePlayerDamage(player), living.getMaxHealth() / 10.0F)) {
                        int type = tag.getInteger("Type");
                        switch (type) {
                            case 0: {
                                tag.setInteger("Amount", MathHelper.clamp_int(tag.getInteger("Amount") + 10, 0, MAXVIS));
                                break;
                            }
                            case 1: {
                                tag.setInteger("Amount", MathHelper.clamp_int(tag.getInteger("Amount") + 5000, 0, MAXMANA));
                                break;
                            }
                            case 2: {
                                tag.setInteger("Amount", MathHelper.clamp_int(tag.getInteger("Amount") + 20000, 0, MAXBLOOD));
                                break;
                            }
                        }
                        if (living instanceof EntityPlayer) {
                            EntityPlayer tp = (EntityPlayer) living;
                            switch (type) {
                                case 0:
                                    ThaumcraftApiHelper.consumeVisFromInventory(tp, VIS_COST);
                                    break;
                                case 1:
                                    suckMana(tp, 1000000);
                                    break;
                                case 2:
                                    SoulNetworkHandler.syphonAndDamageFromNetwork(tp.getCommandSenderName(), tp, 1000000);
                                    break;
                            }
                        }

                    }
                    stack.setTagCompound(tag);

                }

            }
        } else {
            if (count % 20 == 0) {
                if (!player.worldObj.isRemote) {
                    if (tag.getInteger("Amount") > 0) {
                        player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "random.drink", 1.0F, player.worldObj.rand.nextFloat());
                        int type = tag.getInteger("Type");
                        switch (type) {
                            case 0: {
                                Aspect aspect = Aspect.getPrimalAspects().get(player.worldObj.rand.nextInt(Aspect.getPrimalAspects().size()));
                                int slot = InventoryUtils.isWandInHotbarWithRoom(aspect, 10, player);
                                if (slot >= 0) {
                                    ItemWandCasting wand = (ItemWandCasting) player.inventory.mainInventory[slot].getItem();
                                    wand.addVis(player.inventory.mainInventory[slot], aspect, 10, true);
                                }
                                tag.setInteger("Amount", MathHelper.clamp_int(tag.getInteger("Amount") - 10, 0, MAXVIS));

                                break;
                            }
                            case 1: {
                                tag.setInteger("Amount", MathHelper.clamp_int(tag.getInteger("Amount") - 5000, 0, MAXMANA));
                                ManaItemHandler.dispatchManaExact(stack, player, 5000, true);
                                break;
                            }
                            case 2: {
                                tag.setInteger("Amount", MathHelper.clamp_int(tag.getInteger("Amount") - 20000, 0, MAXBLOOD));
                                SoulNetworkHandler.addCurrentEssenceToMaximum(player.getCommandSenderName(), 20000, SoulNetworkHandler.getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(player.getCommandSenderName())));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    public static int suckMana(EntityPlayer player, int manaToGet) {

        int willConsume = manaToGet;
        IInventory mainInv = player.inventory;
        IInventory baublesInv = BotaniaAPI.internalHandler.getBaublesInventory(player);

        int invSize = mainInv.getSizeInventory();
        int size = invSize;
        if (baublesInv != null)
            size += baublesInv.getSizeInventory();

        for (int i = 0; i < size; i++) {
            if (willConsume <= 0) return 0;
            boolean useBaubles = i >= invSize;
            IInventory inv = useBaubles ? baublesInv : mainInv;
            int slot = i - (useBaubles ? invSize : 0);
            ItemStack stackInSlot = inv.getStackInSlot(slot);


            if (stackInSlot != null && stackInSlot.getItem() instanceof IManaItem) {
                IManaItem manaItem = (IManaItem) stackInSlot.getItem();
                if (manaItem.getMana(stackInSlot) > 0) {
                    int mana = Math.min(willConsume, manaItem.getMana(stackInSlot));
                    willConsume -= mana;
                    manaItem.addMana(stackInSlot, -mana);
                    if (useBaubles)
                        BotaniaAPI.internalHandler.sendBaubleUpdatePacket(player, slot);

                    //return mana;
                }
            }
        }

        return 0;
    }

}
