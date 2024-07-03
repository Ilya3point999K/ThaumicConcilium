package com.ilya3point999k.thaumicconcilium.common;

import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TCCreativeTab extends CreativeTabs{
	public TCCreativeTab() {
		super(ThaumicConcilium.MODID);
	}

	@Override
	public Item getTabIconItem() {
		// TODO Auto-generated method stub
		return TCItemRegistry.itemEntityIcon;
	}
}
