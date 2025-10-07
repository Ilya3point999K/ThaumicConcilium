package com.ilya3point999k.thaumicconcilium.common.items;

import am2.api.ArsMagicaApi;
import am2.lore.ArcaneCompendium;
import am2.texture.SpellIconManager;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.forbidden.compat.Compat;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import scala.Int;

import java.util.HashMap;

public class ItemSpellIcon extends Item {

    @SideOnly(Side.CLIENT)
    private HashMap<String, IIcon> icons;

    public ItemSpellIcon() {

    }

    public void registerIcons(final IIconRegister register) {

        icons = new HashMap<String, IIcon>();
        for (int i = 0; i < Integration.spellNames.length; ++i) {
            icons.put(Integration.spellNames[i], register.registerIcon(ThaumicConcilium.MODID + ":spells/" + Integration.spellNames[i]));
            SpellIconManager.instance.registerIcon(Integration.spellNames[i], icons.get(Integration.spellNames[i]));
        }


        if (icons.size() > 0) this.itemIcon = icons.values().iterator().next();
    }

}