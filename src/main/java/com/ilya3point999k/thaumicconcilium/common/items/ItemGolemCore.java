package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemGolemCore extends Item {
    @SideOnly(Side.CLIENT)
    public IIcon iconAssistance;
    public IIcon iconValet;

    public ItemGolemCore() {
        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }
    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.uncommon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister ir) {
        this.iconAssistance = ir.registerIcon(ThaumicConcilium.MODID+":assistance_core");
        this.iconValet = ir.registerIcon(ThaumicConcilium.MODID+":valet_core");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List lst) {
        if (Integration.horizons){
            lst.add(new ItemStack(item, 1, 0));
        }
        lst.add(new ItemStack(item, 1, 1));
    }

    public String getItemStackDisplayName(final ItemStack stack) {
        return StatCollector.translateToLocal("item.ItemGolemCore.name");
    }

    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("item.TCItemGolemCore." + stack.getItemDamage() + ".name"));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int md) {
        switch (md) {
            case 0: {
                return this.iconAssistance;
            }
            case 1:{
                return this.iconValet;
            }
            default: {
                return this.iconAssistance;
            }
        }
    }

}