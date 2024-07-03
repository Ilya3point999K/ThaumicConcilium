package com.ilya3point999k.thaumicconcilium.common.containers;

import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import thaumcraft.common.items.wands.ItemWandCasting;

public class AstralMonitorInventory implements IInventory {
    private ItemStack[] stackList = new ItemStack[1];
    private Container eventHandler;

    public AstralMonitorInventory(Container par1Container) {
        this.eventHandler = par1Container;
    }

    public int getSizeInventory() {
        return this.stackList.length;
    }

    public ItemStack getStackInSlot(int par1) {
        return par1 >= this.getSizeInventory() ? null : this.stackList[par1];
    }

    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.stackList[par1] != null) {
            ItemStack var2 = this.stackList[par1];
            this.stackList[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    public ItemStack decrStackSize(int par1, int par2) {
        if (this.stackList[par1] != null) {
            ItemStack var3;
            if (this.stackList[par1].stackSize <= par2) {
                var3 = this.stackList[par1];
                this.stackList[par1] = null;
                this.eventHandler.onCraftMatrixChanged(this);
                return var3;
            } else {
                var3 = this.stackList[par1].splitStack(par2);
                if (this.stackList[par1].stackSize == 0) {
                    this.stackList[par1] = null;
                }

                this.eventHandler.onCraftMatrixChanged(this);
                return var3;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.stackList[par1] = par2ItemStack;
        this.eventHandler.onCraftMatrixChanged(this);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return true;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (itemstack != null && itemstack.hasTagCompound()){

            if (itemstack.getItem() instanceof ItemWandCasting) {
                String s = itemstack.getTagCompound().getString("Xylography");
                if (s != null && !s.isEmpty()) {
                    return true;
                }
            }

            if (itemstack.getItem().getClass().equals(Integration.crystalEye) && itemstack.getTagCompound().hasKey("lookX")){
                return true;
                /*
                NBTTagCompound tag = itemstack.getTagCompound();
                int x = tag.getInteger("x");
                int y = tag.getInteger("y");
                int z = tag.getInteger("z");

                 */
            }
        }
        return false;
    }

    public String getInventoryName() {
        return "container.astralmonitor";
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public void markDirty() {
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }
}