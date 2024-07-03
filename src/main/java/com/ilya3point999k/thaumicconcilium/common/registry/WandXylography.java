package com.ilya3point999k.thaumicconcilium.common.registry;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;

public class WandXylography implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean foundWand = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() instanceof ItemWandCasting) {
                    foundWand = true;
                    NBTTagCompound wandtag = stack.getTagCompound();
                    if (wandtag == null) {
                        foundWand = false;
                    }
                } else return false;
            }
        }
        return foundWand;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack wand = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() instanceof ItemWandCasting)
                    wand = stack;
            }
        }
        ItemStack copy = wand.copy();
        copy.stackSize = 1;
        NBTTagCompound copytag = copy.getTagCompound();
        if (copytag != null) {
            copytag.setString("Xylography", " ");
            copy.setTagCompound(copytag);
        }
        return copy;
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
