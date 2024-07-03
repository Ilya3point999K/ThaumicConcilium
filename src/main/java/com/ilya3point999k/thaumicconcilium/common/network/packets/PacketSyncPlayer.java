package com.ilya3point999k.thaumicconcilium.common.network.packets;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;

public class PacketSyncPlayer implements IMessage, IMessageHandler<PacketSyncPlayer, IMessage> {

    private NBTTagCompound data;

    public PacketSyncPlayer(){

    }
    public PacketSyncPlayer(NBTTagCompound data){
        this.data = data;
    }

    public void toBytes(ByteBuf buffer) {
        ByteBufUtils.writeTag(buffer, this.data);
    }

    public void fromBytes(ByteBuf buffer) {
        this.data = ByteBufUtils.readTag(buffer);
    }

    public IMessage onMessage(PacketSyncPlayer message, MessageContext ctx) {
        World world = Thaumcraft.proxy.getClientWorld();
        if (world != null) {
                EntityPlayer player = world.getPlayerEntityByName(message.data.getString("SyncName"));
                if (player != null) {
                    TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
                    if (capabilities != null) {
                        capabilities.loadNBTData(message.data);
                    }
                }
            }

        return null;
    }
}