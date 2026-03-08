package com.ilya3point999k.thaumicconcilium.common.registry.recipe;

import com.ilya3point999k.thaumicconcilium.common.items.ItemResource;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;

public class NetherMembraneRecipe implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting var1, World var2) {
        boolean foundMembrane = false;
        boolean foundItem = false;

        for(int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack stack = var1.getStackInSlot(i);
            if(stack != null) {
                if(stack.getItem() == TCItemRegistry.resource && stack.getItemDamage() == 4)
                    foundMembrane = true;
                else if(!foundItem && !(ItemNBTHelper.detectNBT(stack) && ItemNBTHelper.getBoolean(stack, ItemResource.TAG_MEMBRANE, false)) && !stack.getItem().hasContainerItem(stack))
                    foundItem = true;
                else return false;
            }
        }

        return foundMembrane && foundItem;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        ItemStack item = null;

        for(int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack stack = var1.getStackInSlot(i);
            if(stack != null && stack.getItem() != TCItemRegistry.resource && stack.getItemDamage() != 4)
                item = stack;
        }

        ItemStack copy = item.copy();
        ItemNBTHelper.setBoolean(copy, ItemResource.TAG_MEMBRANE, true);
        copy.stackSize = 1;
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
