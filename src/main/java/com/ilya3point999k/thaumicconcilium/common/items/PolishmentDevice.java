package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class PolishmentDevice extends Item {

    public PolishmentDevice() {
        this.setTextureName(ThaumicConcilium.MODID+":icon");
        this.setUnlocalizedName("PolishmentDevice");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        return stack;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int targetX, int targetY, int targetZ, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        super.addInformation(stack, player, list, par4);
        if (stack != null) {
            list.add(StatCollector.translateToLocal("tc.tooltip.polishment"));
            list.add(StatCollector.translateToLocal("tc.tooltip.creative"));

        }
    }

}
