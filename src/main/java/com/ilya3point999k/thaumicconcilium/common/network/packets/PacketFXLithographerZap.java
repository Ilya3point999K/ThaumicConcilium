package com.ilya3point999k.thaumicconcilium.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumcraft.common.Thaumcraft;

public class PacketFXLithographerZap implements IMessage, IMessageHandler<PacketFXLithographerZap, IMessage> {
    private double x;
    private double y;
    private double z;

    public PacketFXLithographerZap() {
    }

    public PacketFXLithographerZap(double x, double y, double z) {
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

    public IMessage onMessage(PacketFXLithographerZap message, MessageContext ctx) {
        float r = 0.0F;
        float g = 217.0F / 255.0F;
        float b = 1.0f;
        Thaumcraft.proxy.arcLightning(Thaumcraft.proxy.getClientWorld(), message.x + 0.5, message.y + 0.5, message.z + 0.5, message.x + 0.5, message.y - 0.5, message.z + 0.5, r, g, b, 0.01F);
        return null;
    }
}
