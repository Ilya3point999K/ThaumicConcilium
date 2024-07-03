package com.ilya3point999k.thaumicconcilium.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumcraft.common.Thaumcraft;

import java.awt.*;

public class PacketFXLightning implements IMessage, IMessageHandler<PacketFXLightning, IMessage> {
    private float x;
    private float y;
    private float z;
    private float dx;
    private float dy;
    private float dz;
    private int color;
    private float h;


    public PacketFXLightning() {
    }

    public PacketFXLightning(float x, float y, float z, float dx, float dy, float dz, int color, float h) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.color = color;
        this.h = h;
    }

    public void toBytes(ByteBuf buffer) {
        buffer.writeFloat(this.x);
        buffer.writeFloat(this.y);
        buffer.writeFloat(this.z);
        buffer.writeFloat(this.dx);
        buffer.writeFloat(this.dy);
        buffer.writeFloat(this.dz);
        buffer.writeInt(this.color);
        buffer.writeFloat(this.h);
    }

    public void fromBytes(ByteBuf buffer) {
        this.x = buffer.readFloat();
        this.y = buffer.readFloat();
        this.z = buffer.readFloat();
        this.dx = buffer.readFloat();
        this.dy = buffer.readFloat();
        this.dz = buffer.readFloat();
        this.color = buffer.readInt();
        this.h = buffer.readFloat();
    }

    public IMessage onMessage(PacketFXLightning message, MessageContext ctx) {
        Color c = new Color(message.color);
        float r = (float) c.getRed() / 255.0F;
        float g = (float) c.getGreen() / 255.0F;
        float b = (float) c.getBlue() / 255.0F;

        Thaumcraft.proxy.arcLightning(Thaumcraft.proxy.getClientWorld(), message.x, message.y, message.z, message.dx, message.dy, message.dz, r, g, b, message.h);
        return null;
    }
}
