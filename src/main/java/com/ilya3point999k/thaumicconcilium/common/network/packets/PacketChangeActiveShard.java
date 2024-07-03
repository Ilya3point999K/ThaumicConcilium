package com.ilya3point999k.thaumicconcilium.common.network.packets;

import com.ilya3point999k.thaumicconcilium.common.items.ShardMill;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketChangeActiveShard implements IMessage, IMessageHandler<PacketChangeActiveShard, IMessage> {
    private int dim;
    private int playerid;
    private int shard;

    public PacketChangeActiveShard(){

    }


    public PacketChangeActiveShard(EntityPlayer player, int shard) {
        this.dim = player.worldObj.provider.dimensionId;
        this.playerid = player.getEntityId();
        this.shard = shard;
    }

    public void fromBytes(ByteBuf buf) {
        this.dim = buf.readInt();
        this.playerid = buf.readInt();
        this.shard = buf.readInt();
    }


    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.dim);
        buf.writeInt(this.playerid);
        buf.writeInt(this.shard);
    }

    public IMessage onMessage(PacketChangeActiveShard message, MessageContext ctx) {
        World world = DimensionManager.getWorld(message.dim);
        if (world != null && (ctx.getServerHandler().playerEntity == null || ctx.getServerHandler().playerEntity.getEntityId() == message.playerid)) {
            Entity player = world.getEntityByID(message.playerid);
            if (player instanceof EntityPlayer && ((EntityPlayer) player).getHeldItem() != null && ((EntityPlayer) player).getHeldItem().getItem() instanceof ShardMill) {
                NBTTagCompound tag = ((EntityPlayer) player).getHeldItem().getTagCompound();
                if (tag == null){
                    tag = new NBTTagCompound();
                    tag.setInteger("Amount", 0);
                    tag.setInteger("Loaded", -1);
                    tag.setBoolean("IsOpen", true);
                }
                tag.setInteger("Type", message.shard);
                ((EntityPlayer) player).getHeldItem().setTagCompound(tag);
            }
            return null;
        } else {
            return null;
        }
    }
}
