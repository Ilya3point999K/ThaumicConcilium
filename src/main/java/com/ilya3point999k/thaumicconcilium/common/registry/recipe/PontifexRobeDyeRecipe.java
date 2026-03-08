package com.ilya3point999k.thaumicconcilium.common.registry.recipe;

import com.ilya3point999k.thaumicconcilium.common.items.equipment.PontifexRobe;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;

public class PontifexRobeDyeRecipe implements IRecipe {
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        ItemStack itemstack = null;
        ArrayList arraylist = new ArrayList();

        for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(i);
            if(itemstack1 != null) {
                if(itemstack1.getItem() instanceof ItemArmor) {
                    ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();
                    if(!(itemarmor instanceof PontifexRobe) || itemstack != null) {
                        return false;
                    }

                    itemstack = itemstack1;
                } else {
                    if(itemstack1.getItem() != Items.dye) {
                        return false;
                    }

                    arraylist.add(itemstack1);
                }
            }
        }

        return itemstack != null && !arraylist.isEmpty();
    }

    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        ItemStack itemstack = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmor itemarmor = null;

        int k;
        int l;
        float f;
        float f1;
        int var17;
        for(k = 0; k < par1InventoryCrafting.getSizeInventory(); ++k) {
            ItemStack var13 = par1InventoryCrafting.getStackInSlot(k);
            if(var13 != null) {
                if(var13.getItem() instanceof ItemArmor) {
                    itemarmor = (ItemArmor)var13.getItem();
                    if(!(itemarmor instanceof PontifexRobe) || itemstack != null) {
                        return null;
                    }

                    itemstack = var13.copy();
                    itemstack.stackSize = 1;
                    if(itemarmor.hasColor(var13)) {
                        l = itemarmor.getColor(itemstack);
                        f = (float)(l >> 16 & 255) / 255.0F;
                        f1 = (float)(l >> 8 & 255) / 255.0F;
                        float var14 = (float)(l & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, var14)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + var14 * 255.0F);
                        ++j;
                    }
                } else {
                    if(var13.getItem() != Items.dye) {
                        return null;
                    }

                    float[] var171 = EntitySheep.fleeceColorTable[BlockColored.func_150032_b(var13.getItemDamage())];
                    int var15 = (int)(var171[0] * 255.0F);
                    int var16 = (int)(var171[1] * 255.0F);
                    var17 = (int)(var171[2] * 255.0F);
                    i += Math.max(var15, Math.max(var16, var17));
                    aint[0] += var15;
                    aint[1] += var16;
                    aint[2] += var17;
                    ++j;
                }
            }
        }

        if(itemarmor == null) {
            return null;
        } else {
            k = aint[0] / j;
            int var161 = aint[1] / j;
            l = aint[2] / j;
            f = (float)(i / j);
            f1 = (float)Math.max(k, Math.max(var161, l));
            k = (int)((float)k * f / f1);
            var161 = (int)((float)var161 * f / f1);
            l = (int)((float)l * f / f1);
            var17 = (k << 8) + var161;
            var17 = (var17 << 8) + l;
            itemarmor.func_82813_b(itemstack, var17);
            return itemstack;
        }
    }

    public int getRecipeSize() {
        return 10;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }
}