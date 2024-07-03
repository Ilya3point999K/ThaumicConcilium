package com.ilya3point999k.thaumicconcilium.common.tiles;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.items.AstralMonitor;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLithographerZap;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import thaumcraft.api.aspects.Aspect;

public class LithographerTile extends TileEntity implements IInventory {
    public int deploy;
    public boolean orientation;
    public int state;
    public ItemStack[] inventory;

    public LithographerTile() {
        deploy = 0;
        state = 0;
        orientation = true;
        inventory = new ItemStack[getSizeInventory()];
    }

    public boolean canUpdate() {
        return true;
    }

    public void updateEntity() {
        if (deploy > 0) {
            --deploy;
        }
        if (deploy < 0) {
            ++deploy;
        }
        if (state == 1 && deploy == 0) {
            deploy = 20;
            state = 2;
            if (!worldObj.isRemote) {
                this.markDirty();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

                if (!(worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof VisCondenserTile)) return;
                TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 2, zCoord);
                if (!(tile instanceof DestabilizedCrystalTile)) return;
                DestabilizedCrystalTile crystal = (DestabilizedCrystalTile) tile;
                if (crystal.aspect == null || crystal.aspect.isEmpty()) return;
                if (!crystal.aspect.equals(Aspect.SENSES.getTag())) return;
                if (crystal.capacity < 32) return;
                Block block = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
                if (block == Blocks.bed) {
                    if (!BlockBed.isBlockHeadOfBed(worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord))) return;
                    EntityPlayer player = worldObj.getClosestPlayer(xCoord, yCoord - 1, zCoord, 2.0);
                    if (player == null) return;
                    if (!player.isPlayerSleeping()) return;
                    TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
                    if (capabilities == null) return;
                    String[] data = new String[4];

                    for (int i = 0; i < 4; i++) {
                        data[i] = "!!!NOTHING!!!";
                        ItemStack itemstack = inventory[i];
                        if (itemstack != null && itemstack.hasTagCompound()) {
                            if (itemstack.getItem() instanceof AstralMonitor) {
                                NBTTagCompound tag = itemstack.getTagCompound();
                                if (tag == null) continue;
                                NBTTagCompound stacktag = tag.getCompoundTag("wand");
                                if (stacktag == null) continue;
                                NBTTagCompound wandtag = stacktag.getCompoundTag("tag");
                                if (wandtag == null) continue;
                                String xyl = wandtag.getString("Xylography");

                                if (xyl != null && !xyl.isEmpty()) {
                                    data[i] = xyl;
                                    continue;
                                }
                                if (wandtag.hasKey("lookX")) {
                                    data[i] = wandtag.getInteger("lookDim") + ";" + wandtag.getInteger("lookX") + ";" + wandtag.getInteger("lookY") + ";" + wandtag.getInteger("lookZ");
                                }

                            }
                        }
                    }
                    capabilities.lithographed = true;
                    capabilities.monitored = data;
                    capabilities.sync(player);
                    crystal.capacity = MathHelper.clamp_int(crystal.capacity - 32, 0, crystal.capacity);
                    crystal.markDirty();
                    worldObj.markBlockForUpdate(xCoord, yCoord + 2, zCoord);
                    worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "thaumcraft:zap", 1.0F, 1.0F);
                    TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLithographerZap(this.xCoord, this.yCoord, this.zCoord), new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 32.0));
                }
                //Thaumcraft.proxy.arcLightning(this.worldObj, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord - 0.5, this.zCoord + 0.5, r, g, b, 0.01F);
            }
        }
        if (state == 2 && deploy == 0) {
            deploy = -20;
            state = 3;
            if (!worldObj.isRemote) {
                this.markDirty();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
        if (state == 3 && deploy == 0) {
            state = 0;
            if (!worldObj.isRemote) {
                this.markDirty();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
        super.updateEntity();

    }

    public void project() {
        if (!worldObj.isRemote) {
            deploy = 20;
            state = 1;
            this.markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        deploy = nbttagcompound.getInteger("deploy");
        orientation = nbttagcompound.getBoolean("orientation");
        state = nbttagcompound.getInteger("state");

        NBTTagList var2 = nbttagcompound.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); var3++) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 >= 0 && var5 < this.inventory.length) {
                this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("deploy", deploy);
        nbttagcompound.setBoolean("orientation", orientation);
        nbttagcompound.setInteger("state", state);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.inventory.length; ++var3) {
            if (this.inventory[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.inventory[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        nbttagcompound.setTag("Items", var2);

        super.writeToNBT(nbttagcompound);

    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (worldObj.blockExists(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e())) {
            TileEntity tile = worldObj.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
            if (tile instanceof LithographerTile) {
                tile.readFromNBT(pkt.func_148857_g());
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        if (var1 >= inventory.length) {
            return null;
        }
        return inventory[var1];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (inventory[i] != null) {
            if (inventory[i].stackSize <= j) {
                ItemStack itemstack = inventory[i];
                inventory[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = inventory[i].splitStack(j);
            if (inventory[i].stackSize == 0) {
                inventory[i] = null;
            }
            return itemstack1;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (inventory[i] != null) {
            ItemStack itemstack = inventory[i];
            inventory[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }

    }

    @Override
    public String getInventoryName() {
        return "inventory.lithographer";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
            return false;
        }
        return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {

        if (itemstack != null && itemstack.hasTagCompound()) {
            if (itemstack.getItem() instanceof AstralMonitor) {
                NBTTagCompound tag = itemstack.getTagCompound();
                if (tag == null) return false;
                NBTTagCompound stacktag = tag.getCompoundTag("wand");
                if (stacktag == null) return false;
                NBTTagCompound wandtag = stacktag.getCompoundTag("tag");
                if (wandtag == null) return false;
                String xyl = wandtag.getString("Xylography");

                if (xyl != null && !xyl.isEmpty()) {
                    return true;
                }
                if (wandtag.hasKey("lookX")) {
                    return true;
                }

            }
        }
        return false;
    }
}
