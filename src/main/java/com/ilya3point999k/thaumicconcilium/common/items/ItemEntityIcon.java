package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemEntityIcon extends Item {
    @SideOnly(Side.CLIENT)
    public IIcon iconThaumaturge;
    public IIcon iconMadThaumaturge;
    public IIcon iconGolem;
    public IIcon iconRedMind;
    public IIcon iconQuicksilver;
    public IIcon iconSquid;
    public IIcon iconDopeSquid;


    public ItemEntityIcon() {
        this.setCreativeTab((CreativeTabs)null);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister ir) {
        this.iconThaumaturge = ir.registerIcon(ThaumicConcilium.MODID+":iconThaumaturge");
        this.iconMadThaumaturge = ir.registerIcon(ThaumicConcilium.MODID+":iconMadThaumaturge");
        this.iconGolem = ir.registerIcon(ThaumicConcilium.MODID+":iconGolem");
        this.iconRedMind = ir.registerIcon(ThaumicConcilium.MODID+":iconRedMind");
        this.iconQuicksilver = ir.registerIcon(ThaumicConcilium.MODID+":iconQuicksilver");
        this.iconSquid = ir.registerIcon(ThaumicConcilium.MODID+":iconSquid");
        this.iconDopeSquid = ir.registerIcon(ThaumicConcilium.MODID+":iconDopeSquid");

    }

    public String getItemStackDisplayName(final ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 0: {
                return StatCollector.translateToLocal("entity.ThaumicConcilium.Thaumaturge.name");
            }
            case 1: {
                return StatCollector.translateToLocal("entity.ThaumicConcilium.MadThaumaturge.name");
            }
            case 2:{
                return StatCollector.translateToLocal("entity.ThaumicConcilium.GolemBydlo.name");
            }
            case 3:{
                return StatCollector.translateToLocal("entity.ThaumicConcilium.RedPoweredMind.name");
            }
            case 4:{
                return StatCollector.translateToLocal("entity.ThaumicConcilium.QuicksilverElemental.name");
            }
            case 5:{
                return StatCollector.translateToLocal("entity.Squid.name");
            }
            case 6:{
                return StatCollector.translateToLocal("entity.ThaumicConcilium.DopeSquid.name");
            }
            default: {
                return "Creature";
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int md) {
        switch (md) {
            case 0: {
                return this.iconThaumaturge;
            }
            case 1: {
                return this.iconMadThaumaturge;
            }
            case 2:{
                return this.iconGolem;
            }
            case 3:{
                return this.iconRedMind;
            }
            case 4:{
                return this.iconQuicksilver;
            }
            case 5:{
                return this.iconSquid;
            }
            case 6:{
                return this.iconDopeSquid;
            }
            default: {
                return this.iconThaumaturge;
            }
        }
    }

}
