package com.ilya3point999k.thaumicconcilium.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumcraft.common.Thaumcraft;

public class PacketFXBurst implements IMessage, IMessageHandler<PacketFXBurst, IMessage> {
    private double x;
    private double y;
    private double z;

    public PacketFXBurst() {
    }

    public PacketFXBurst(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void toBytes(ByteBuf buffer) {
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
    }

    public void fromBytes(ByteBuf buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    public IMessage onMessage(PacketFXBurst message, MessageContext ctx) {
        Thaumcraft.proxy.burst(Thaumcraft.proxy.getClientWorld(), message.x, message.y, message.z, 2.0F);

        return null;
    }
}
