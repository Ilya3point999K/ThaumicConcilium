package com.ilya3point999k.thaumicconcilium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class RedPoweredMindBlockItem extends ItemBlock {

    public RedPoweredMindBlockItem(Block par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    public int getMetadata(int par1) {
        return par1;
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName();
    }

}