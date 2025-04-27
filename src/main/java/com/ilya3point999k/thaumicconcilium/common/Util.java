package com.ilya3point999k.thaumicconcilium.common;

import baubles.api.BaublesApi;
import com.ilya3point999k.thaumicconcilium.common.dim.WorldProviderCausalBouillonDimension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import thaumcraft.api.IGoggles;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class Util {
    //public static ArrayList<Aspect> AspectArray;
	public static AspectList getPrimals (int amount)
    {
        return new AspectList().add(Aspect.FIRE, amount).add(Aspect.WATER, amount).add(Aspect.EARTH, amount)
                .add(Aspect.AIR, amount).add(Aspect.ORDER, amount).add(Aspect.ENTROPY, amount);
    }

    public static void createDimension() {
        int dimID = TCConfig.causalBouillonID;
        if (!DimensionManager.isDimensionRegistered(dimID)) {
            WorldProviderCausalBouillonDimension provider = new WorldProviderCausalBouillonDimension();
            provider.setDimension(dimID);
            DimensionManager.registerProviderType(dimID, provider.getClass(), true);
            DimensionManager.registerDimension(dimID, dimID);
        }
    }

    public static boolean hasGoggles(Entity e) {
        if (!(e instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer viewer = (EntityPlayer)e;
        ItemStack heldItem = viewer.getHeldItem();
        if(heldItem != null) {
            if (heldItem.getItem() instanceof IGoggles) {
                return true;
            }
        }

        for (int a = 0; a < 4; ++a) {
            if (viewer.inventory.armorInventory[a] != null) {
                if (viewer.inventory.armorInventory[a].getItem() instanceof IGoggles) {
                    return true;
                }
            }
        }
        IInventory baubles = BaublesApi.getBaubles(viewer);
        for (int a2 = 0; a2 < baubles.getSizeInventory(); a2++) {
            if(baubles.getStackInSlot(a2) != null) {
                if (baubles.getStackInSlot(a2).getItem() instanceof IGoggles) {
                    return true;
                }
            }
        }
        return false;
    }

}
