package com.ilya3point999k.thaumicconcilium.common.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class RedPoweredMindTile extends TileEntity {
    public float rota;
    public float rotb;
    public float field_40066_q;

    public int power;

    public RedPoweredMindTile(){
        this.power = 0;
    }

    public boolean canUpdate() {
        return true;
    }


    public void updateEntity() {

        super.updateEntity();
        if (this.worldObj.isRemote) {
            this.rotb = this.rota;
            EntityPlayer entity = null;
            entity = this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 6.0);

            double d;
            double d1;
            if (entity != null) {
                d = entity.posX - (double)((float)this.xCoord + 0.5F);
                d1 = entity.posZ - (double)((float)this.zCoord + 0.5F);
                this.field_40066_q = (float)Math.atan2(d1, d);
            } else {
                this.field_40066_q += 0.01F;
            }

            while(this.rota >= 3.141593F) {
                this.rota -= 6.283185F;
            }

            while(this.rota < -3.141593F) {
                this.rota += 6.283185F;
            }

            while(this.field_40066_q >= 3.141593F) {
                this.field_40066_q -= 6.283185F;
            }

            while(this.field_40066_q < -3.141593F) {
                this.field_40066_q += 6.283185F;
            }

            float f;
            for(f = this.field_40066_q - this.rota; f >= 3.141593F; f -= 6.283185F) {
            }

            while(f < -3.141593F) {
                f += 6.283185F;
            }

            this.rota += f * 0.04F;
        }

    }


    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        power = nbttagcompound.getInteger("Power");
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("Power", power);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord - 1.0, this.zCoord, this.xCoord + 1.0, this.yCoord + 2.0, this.zCoord + 1.0);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        if (worldObj.blockExists(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e())) {
            TileEntity tile = worldObj.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
            if (tile instanceof RedPoweredMindTile) {
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
}
