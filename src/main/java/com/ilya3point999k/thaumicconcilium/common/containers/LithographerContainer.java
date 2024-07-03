package com.ilya3point999k.thaumicconcilium.common.containers;

import com.ilya3point999k.thaumicconcilium.common.tiles.LithographerTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LithographerContainer extends Container {
    private World worldObj;

    public LithographerTile tile;
    EntityPlayer player = null;

    public LithographerContainer(InventoryPlayer iinventory, LithographerTile tile) {
        this.worldObj = tile.getWorldObj();
        this.player = iinventory.player;
        this.tile = tile;
        //this.blockSlot = iinventory.currentItem + 28;


        this.addSlotToContainer(new SlotActiveMonitor(this.tile, 0, 52, 30));
        this.addSlotToContainer(new SlotActiveMonitor(this.tile, 1, 80, 2));
        this.addSlotToContainer(new SlotActiveMonitor(this.tile, 2, 108, 30));
        this.addSlotToContainer(new SlotActiveMonitor(this.tile, 3, 80, 58));

        this.bindPlayerInventory(iinventory);

        this.onCraftMatrixChanged(tile);
    }


    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }

    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot)this.inventorySlots.get(slot);
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            if (slot <= 3) {
                if (!this.mergeItemStack(stackInSlot, 4, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else {
                if (tile.isItemValidForSlot(slot, stackInSlot)) {
                    if (!this.mergeItemStack(stackInSlot, 0, 4, false)) {
                        return null;
                    }
                }
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack((ItemStack)null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
        }

        return stack;
    }

    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }

}
