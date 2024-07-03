package com.ilya3point999k.thaumicconcilium.common.items.equipment;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.lib.utils.Utils;

public class DumpJackboots extends ItemArmor {

    public static String texture = ThaumicConcilium.MODID + ":textures/models/armor/dump_jackboots.png";


    public DumpJackboots() {
        super(ThaumcraftApi.armorMatSpecial, 0, 3);
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("DumpJackboots");
        this.setTextureName(ThaumicConcilium.MODID + ":dump_jackboots");
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.rare;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return texture;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer p, ItemStack itemStack) {
        if (p.ticksExisted % 2 == 0) {
            if (p.isSneaking()) {
                ItemStack stack = p.getHeldItem();
                if (stack != null) {
                    if (stack.getItem() instanceof ItemBlock) {
                        int x = MathHelper.floor_double(p.posX);
                        int y = MathHelper.floor_double(p.posY) - 1;
                        int z = MathHelper.floor_double(p.posZ);
                        Block block = ((ItemBlock) stack.getItem()).field_150939_a;
                        if (block.canPlaceBlockAt(p.worldObj, x, y, z) && p.worldObj.getBlock(x, y, z).isReplaceable(p.worldObj, x, y, z)) {
                            p.motionX = 0;
                            p.motionY = 0.5;
                            p.motionZ = 0;
                            p.fallDistance = 0F;
                            if (p instanceof EntityPlayerMP) {
                                Utils.resetFloatCounter((EntityPlayerMP) p);
                            }
                        }
                        if (!p.worldObj.isRemote) {
                            if (block.canPlaceBlockAt(p.worldObj, x, y, z) && p.worldObj.getBlock(x, y, z).isReplaceable(p.worldObj, x, y, z)) {
                                p.worldObj.setBlock(x, y, z, block, stack.getItemDamage(), 3);
                                if (!p.capabilities.isCreativeMode) {
                                    p.inventory.consumeInventoryItem(stack.getItem());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
