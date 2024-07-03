package com.ilya3point999k.thaumicconcilium.common.network.packets;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumcraft.common.Thaumcraft;

public class PacketFXTaintsplosion implements IMessage, IMessageHandler<PacketFXTaintsplosion, IMessage> {
    private double x;
    private double y;
    private double z;

    public PacketFXTaintsplosion() {
    }

    public PacketFXTaintsplosion(double x, double y, double z) {
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

    public IMessage onMessage(PacketFXTaintsplosion message, MessageContext ctx) {
        for (int i = 0; i < 50; i++) {
            ThaumicConcilium.proxy.taintsplosion(Thaumcraft.proxy.getClientWorld(), message.x, message.y, message.z);
        }
        return null;
    }
}
