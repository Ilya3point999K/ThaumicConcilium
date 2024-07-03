package com.ilya3point999k.thaumicconcilium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class EldritchTrapBlockItem extends ItemBlock {
    public EldritchTrapBlockItem(Block par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName();
    }
}