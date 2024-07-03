package com.ilya3point999k.thaumicconcilium.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.MathHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.foci.ItemFocusPortableHole;

public class PacketMakeHole implements IMessage, IMessageHandler<PacketMakeHole, IMessage> {
    private double x,y,z;
    public PacketMakeHole(){

    }
    public PacketMakeHole(double x, double y, double z){
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

    public IMessage onMessage(PacketMakeHole message, MessageContext ctx) {
        ItemFocusPortableHole.createHole(Thaumcraft.proxy.getClientWorld(), MathHelper.floor_double(message.x), MathHelper.floor_double(message.y)-1, MathHelper.floor_double(message.z), 1, (byte) 33, 120);
        return null;
    }
}
