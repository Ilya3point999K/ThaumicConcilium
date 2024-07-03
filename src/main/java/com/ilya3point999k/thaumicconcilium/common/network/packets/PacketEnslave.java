package com.ilya3point999k.thaumicconcilium.common.network.packets;

import com.ilya3point999k.thaumicconcilium.client.events.ClientEvents;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketEnslave implements IMessage, IMessageHandler<PacketEnslave, IMessage> {
    boolean action;
    public PacketEnslave(){

    }
    public PacketEnslave(String name, boolean action){
        this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        action = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(action);
    }

    @Override
    public IMessage onMessage(PacketEnslave message, MessageContext ctx) {
        ClientEvents.isEnslaved = message.action;
        return null;
    }
}
