package com.ilya3point999k.thaumicconcilium.common.containers;

import com.ilya3point999k.thaumicconcilium.common.items.AstralMonitor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SlotActiveMonitor extends Slot {

    public SlotActiveMonitor(IInventory par2IInventory, int par3, int par4, int par5) {
        super(par2IInventory, par3, par4, par5);
    }


    public boolean isItemValid(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof AstralMonitor && stack.hasTagCompound()){
            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) return false;
            NBTTagCompound stacktag = tag.getCompoundTag("wand");
            if (stacktag == null) return false;
            NBTTagCompound wandtag = stacktag.getCompoundTag("tag");
            if (wandtag == null) return false;
            String xyl = wandtag.getString("Xylography");

            if (xyl != null && !xyl.isEmpty()) {
                return true;
            }
            if (wandtag.hasKey("lookX")){
                return true;
            }
        }
        return false;
    }

    public int getSlotStackLimit() {
        return 1;
    }
}
