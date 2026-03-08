package com.ilya3point999k.thaumicconcilium.common.integration;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.items.wands.Bracelets;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import org.apache.logging.log4j.Level;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

public class TaintedMagicIntegration {
    public static void register() {
        ItemStack is = new ItemStack(TCItemRegistry.resource, 1, 3);
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(is, 1, 1, 8));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(is, 1, 1, 8));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(is, 1, 1, 8));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(is, 1, 1, 4));
        ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(is, 1, 1, 4));
        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(is, 1, 1, 4));
        ChestGenHooks.addItem("strongholdLibrary", new WeightedRandomChestContent(is, 1, 1, 10));
        ThaumcraftApi.addLootBagItem(is, 30, 2);
        Item i = GameRegistry.findItem("TaintedMagic", "ItemCrystalDagger");
        if (i == null) {
            i = GameRegistry.findItem("TaintedMagic", "ItemHollowDagger");
        }
        if (i != null) {
            Integration.crimsonDagger = i;
        } else {
            ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Tainted Magic's crimson dagger, what a mess");
        }

        Integration.tmMaterial = GameRegistry.findItem("TaintedMagic", "ItemMaterial");
        if (Integration.tmMaterial == null){
            ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Tainted Magic's material, what a mess");
        }
        if(Integration.thaumicBases) {
            WandCap shadowmetalCap = WandCap.caps.get("shadowmetal");
            WandRod warpwoodRod = StaffRod.rods.get("warpwood");
            Bracelets.caps[6] = shadowmetalCap;
            Bracelets.rods[6] = warpwoodRod;
        }
    }
}