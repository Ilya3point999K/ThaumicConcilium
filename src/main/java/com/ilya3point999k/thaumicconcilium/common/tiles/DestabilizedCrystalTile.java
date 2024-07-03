package com.ilya3point999k.thaumicconcilium.common.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.config.ConfigItems;

public class DestabilizedCrystalTile extends TileEntity implements IAspectContainer {
    public short orientation;
    public String aspect;
    public int capacity;
    public boolean draining;

    public DestabilizedCrystalTile(){
        this.orientation = 1;
        this.aspect = null;
        this.capacity = 1;
        this.draining = false;
    }
    public boolean isUseableByPlayer(final EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.orientation = nbttagcompound.getShort("orientation");
        this.capacity = nbttagcompound.getInteger("capacity");
        if (nbttagcompound.hasKey("aspect")) {
            final String asp = nbttagcompound.getString("aspect");
            this.aspect = asp;
        }
        this.draining = nbttagcompound.getBoolean("draining");
        final String de = nbttagcompound.getString("drainer");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("orientation", this.orientation);
        nbttagcompound.setInteger("capacity", this.capacity);
        nbttagcompound.setBoolean("draining", this.draining);
        if(this.aspect != null){
            nbttagcompound.setString("aspect", this.aspect);
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        if (worldObj.blockExists(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e())) {
            TileEntity tile = worldObj.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
            if (tile instanceof DestabilizedCrystalTile) {
                tile.readFromNBT(pkt.func_148857_g());
            }
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
    }

    @Override
    public AspectList getAspects() {
        if (this.aspect == null) {
            return null;
        }
        return new AspectList().add(Aspect.aspects.get(this.aspect), this.capacity);
    }

    @Override
    public void setAspects(AspectList aspects) {

    }

    public void updateAmount(){
        if(this.capacity > 0){
            this.capacity -= 1;
        }

    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return false;
    }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        return false;
    }

    @Override
    public int containerContains(Aspect tag) {
        return 0;
    }

    public void handleInputStack(EntityPlayer player, ItemStack stack){
        if(stack.getItem() == ConfigItems.itemCrystalEssence) {
            AspectList aspects = new AspectList();
            aspects.readFromNBT(stack.getTagCompound());
            Aspect crystalAspect = aspects.getAspects()[0];
            if(crystalAspect != null) {
                if(this.aspect != null && this.aspect.equals(crystalAspect.getTag())){
                    this.capacity += 1;
                    if (!player.capabilities.isCreativeMode) {
                        stack.stackSize--;
                    }
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    this.markDirty();
                }
                else if (this.aspect == null || this.capacity == 0) {
                    if(aspects.size() > 0){
                        this.aspect = aspects.getAspects()[0].getTag();
                        if (!player.capabilities.isCreativeMode) {
                            stack.stackSize--;
                            if (stack.stackSize == 0){
                                stack = null;
                            }
                        }
                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                        this.markDirty();
                    }
                }
            }
        }
    }
}
