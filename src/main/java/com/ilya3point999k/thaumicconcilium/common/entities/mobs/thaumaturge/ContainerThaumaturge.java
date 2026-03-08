package com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge;

import com.ilya3point999k.thaumicconcilium.common.registry.Thaumonomicon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.container.SlotOutput;
import thaumcraft.common.items.ItemResearchNotes;
import thaumcraft.common.lib.research.ResearchManager;

import java.util.ArrayList;
import java.util.List;

public class ContainerThaumaturge extends Container {
    private final Thaumaturge thaumaturge;
    private final InventoryThaumaturge inventory;
    private final EntityPlayer player;
    private final World theWorld;

    public ContainerThaumaturge(InventoryPlayer par1InventoryPlayer, World par3World, Thaumaturge par2IMerchant) {
        this.thaumaturge = par2IMerchant;
        this.theWorld = par3World;
        this.player = par1InventoryPlayer.player;
        this.inventory = new InventoryThaumaturge(par1InventoryPlayer.player, par2IMerchant, this);
        this.thaumaturge.trading = true;
        this.addSlotToContainer(new Slot(this.inventory, 0, 36, 29));

        int i;
        int j;
        for(i = 0; i < 2; ++i) {
            for(j = 0; j < 2; ++j) {
                this.addSlotToContainer(new SlotOutput(this.inventory, 1 + j + i * 2, 106 + 18 * j, 20 + 18 * i));
            }
        }

        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
        }

    }

    public InventoryThaumaturge getMerchantInventory() {
        return this.inventory;
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2) {
        if (par2 == 0) {
            this.generateContents();
            return true;
        } else {
            return super.enchantItem(par1EntityPlayer, par2);
        }
    }

    private void generateContents() {
        if (!this.theWorld.isRemote && this.inventory.getStackInSlot(0) != null && this.inventory.getStackInSlot(1) == null && this.inventory.getStackInSlot(2) == null && this.inventory.getStackInSlot(3) == null && this.inventory.getStackInSlot(4) == null && this.thaumaturge.isValued(this.inventory.getStackInSlot(0))) {
            ItemStack offer = this.inventory.getStackInSlot(0);
            if (offer.getItem() instanceof ItemResearchNotes){
                if (ResearchManager.getData(offer) != null){
                    if (ResearchManager.getData(offer).isComplete()){
                        ItemStack research = null;
                        int choise = this.theWorld.rand.nextInt(4);
                        switch (choise){
                            case 0:
                                research = ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "VISCONDUCTOR", this.theWorld);
                                research.getTagCompound().setBoolean(Thaumonomicon.visConductorHintTag, true);
                                this.mergeItemStack(research, 1, 5, false);
                                this.inventory.decrStackSize(0, 1);
                                this.player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.thaumaturge.trade." + (theWorld.rand.nextInt(3) + 1))).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                                break;
                            case 1:
                            case 2:
                                research = ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "POSITIVEBURSTFOCI", this.theWorld);
                                research.getTagCompound().setBoolean(Thaumonomicon.positiveBurstHintTag, true);
                                this.mergeItemStack(research, 1, 5, false);
                                this.inventory.decrStackSize(0, 1);
                                this.player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.thaumaturge.trade." + (theWorld.rand.nextInt(3) + 1))).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                                break;
                            case 3:
                                research = ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "IMPULSEFOCI", this.theWorld);
                                research.getTagCompound().setBoolean(Thaumonomicon.impulseHintTag, true);
                                this.mergeItemStack(research, 1, 5, false);
                                this.inventory.decrStackSize(0, 1);
                                this.player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.thaumaturge.trade." + (theWorld.rand.nextInt(3) + 1))).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                                break;
                            default:
                                research = ResearchManager.createNote(new ItemStack(ConfigItems.itemResearchNotes), "VISCONDUCTOR", this.theWorld);
                                research.getTagCompound().setBoolean(Thaumonomicon.visConductorHintTag, true);
                                this.mergeItemStack(research, 1, 5, false);
                                this.inventory.decrStackSize(0, 1);
                                this.player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.thaumaturge.trade." + (theWorld.rand.nextInt(3) + 1))).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                                break;
                        }
                        return;
                    }
                }
            }
            int value = this.thaumaturge.getValue(this.inventory.getStackInSlot(0));

            if (this.theWorld.rand.nextInt(5) == 0) {
                value += this.theWorld.rand.nextInt(2);
            } else if (this.theWorld.rand.nextBoolean()) {
                value -= this.theWorld.rand.nextInt(2);
            }

            ArrayList<List> pos = Thaumaturge.tradeInventory;

            int sum_of_weight = 0;
            for(int i=0; i<pos.size(); i++) {
                sum_of_weight += (Integer)pos.get(i).get(0);
            }
            this.inventory.decrStackSize(0, 1);
            int rnd = this.theWorld.rand.nextInt(sum_of_weight);
            for(int i=0; i<pos.size(); i++) {
                if(rnd < (Integer)pos.get(i).get(0)) {
                    if(value > this.theWorld.rand.nextInt(10)) {
                        this.mergeItemStack(((ItemStack) pos.get(i).get(1)).copy(), 1, 5, false);
                        if (value < this.theWorld.rand.nextInt(10)) {
                            return;
                        }
                    }
                }
                rnd -= (Integer)pos.get(i).get(0);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.thaumaturge.getAnger() == 0;
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(itemstack1, 5, 41, true)) {
                    return null;
                }
            } else if (par2 >= 1 && par2 < 5) {
                if (!this.mergeItemStack(itemstack1, 5, 41, true)) {
                    return null;
                }
            } else if (par2 != 0 && par2 >= 5 && par2 < 41 && !this.mergeItemStack(itemstack1, 0, 1, true)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.thaumaturge.trading = false;
        if (!this.theWorld.isRemote) {
            for(int a = 0; a < 5; ++a) {
                ItemStack itemstack = this.inventory.getStackInSlotOnClosing(a);
                if (itemstack != null) {
                    EntityItem ei = par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                    if (ei != null) {
                        ei.func_145799_b("ThaumaturgeDrop");
                    }
                }
            }
        }

    }

    protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
        boolean flag1 = false;
        int k = p_75135_2_;
        if (p_75135_4_) {
            k = p_75135_3_ - 1;
        }

        Slot slot;
        ItemStack itemstack1;
        if (p_75135_1_.isStackable()) {
            while(p_75135_1_.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)) {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();
                if (itemstack1 != null && itemstack1.getItem() == p_75135_1_.getItem() && (!p_75135_1_.getHasSubtypes() || p_75135_1_.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(p_75135_1_, itemstack1)) {
                    int l = itemstack1.stackSize + p_75135_1_.stackSize;
                    if (l <= p_75135_1_.getMaxStackSize()) {
                        p_75135_1_.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < p_75135_1_.getMaxStackSize()) {
                        p_75135_1_.stackSize -= p_75135_1_.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = p_75135_1_.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (p_75135_4_) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (p_75135_1_.stackSize > 0) {
            if (p_75135_4_) {
                k = p_75135_3_ - 1;
            } else {
                k = p_75135_2_;
            }

            while(!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_) {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();
                if (itemstack1 == null) {
                    slot.putStack(p_75135_1_.copy());
                    slot.onSlotChanged();
                    p_75135_1_.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (p_75135_4_) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        return flag1;
    }
}
