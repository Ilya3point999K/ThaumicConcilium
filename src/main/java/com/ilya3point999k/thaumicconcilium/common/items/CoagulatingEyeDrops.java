package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class CoagulatingEyeDrops extends Item {
    public IIcon icon;

    public CoagulatingEyeDrops() {
        this.setTextureName(ThaumicConcilium.MODID+":coagulating_eye_drops");
        this.setUnlocalizedName("CoagulatingEyeDrops");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setMaxDamage(100);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 60;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, 60);
        return stack;
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.uncommon;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if(!player.worldObj.isRemote){
            if (count == 1){
                TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
                if (capabilities == null) return;
                if (!capabilities.lithographed) return;
                capabilities.relieved = !capabilities.relieved;
                capabilities.sync();
                stack.damageItem(1, player);
                player.worldObj.playSoundAtEntity(player, "random.fizz", 1.0F, 1.0F + player.worldObj.rand.nextFloat() * 0.1F);
            }
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }
}
