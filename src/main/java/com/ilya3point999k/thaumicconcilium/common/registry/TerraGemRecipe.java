package com.ilya3point999k.thaumicconcilium.common.registry;

import com.ilya3point999k.thaumicconcilium.common.items.RiftGem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TerraGemRecipe implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean foundTerra = false;
        boolean foundRift = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() == TCItemRegistry.terraCastGem && stack.getItemDamage() == 1) {
                    if (foundTerra) return false;
                    foundTerra = true;
                    NBTTagCompound terratag = stack.getTagCompound();
                    if (terratag == null) {
                        foundTerra = false;
                    } else if (!terratag.hasKey("name")) foundTerra = false;
                }
                else if (!foundRift)
                    foundRift = true;
                else return false;
            }
        }

        return foundRift && foundTerra;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack rift = null;
        ItemStack terra = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() == TCItemRegistry.terraCastGem && stack.getItemDamage() == 1)
                    terra = stack;
                if (stack.getItem() == TCItemRegistry.riftGem && stack.getItemDamage() == 1)
                    rift = stack;
            }
        }

        ItemStack copy = rift.copy();
        copy.stackSize = 1;
        NBTTagCompound terratag = terra.getTagCompound();
        if (terratag != null) {
            if (terratag.hasKey("name")) {
                NBTTagCompound tag = copy.getTagCompound();
                if (tag == null) {
                    tag = new NBTTagCompound();
                    tag.setInteger("MAX_GEMS", 1);
                    tag.setInteger("CURR", 0);
                    tag.setString("NAME" + "0", terratag.getString("name"));
                    tag.setInteger("META" + "0", terratag.getInteger("meta"));
                    tag.setInteger("DIM" + "0", terratag.getInteger("dim"));
                    copy.setTagCompound(tag);
                } else {
                    int max = tag.getInteger("MAX_GEMS") + 1;
                    if (max > RiftGem.MAX_GEMS){
                        return null;
                    }

                    tag.setInteger("MAX_GEMS", max);
                    tag.setInteger("CURR", max - 1);
                    tag.setString("NAME" + (max - 1), terratag.getString("name"));
                    tag.setInteger("META" + (max - 1), terratag.getInteger("meta"));
                    tag.setInteger("DIM" + (max - 1), terratag.getInteger("dim"));
                    copy.setTagCompound(tag);
                }
            }
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
