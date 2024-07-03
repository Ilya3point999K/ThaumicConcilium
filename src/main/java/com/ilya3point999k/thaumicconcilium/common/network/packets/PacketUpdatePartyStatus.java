package com.ilya3point999k.thaumicconcilium.common.network.packets;

import com.ilya3point999k.thaumicconcilium.client.events.ClientEvents;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class PacketUpdatePartyStatus implements IMessage, IMessageHandler<PacketUpdatePartyStatus, IMessage> {

    private NBTTagCompound tag;

    public PacketUpdatePartyStatus(){

    }
    public PacketUpdatePartyStatus(NBTTagCompound tag){
        this.tag = tag;
    }

    public void toBytes(ByteBuf buffer) {
        ByteBufUtils.writeTag(buffer, this.tag);
    }

    public void fromBytes(ByteBuf buffer) {
        tag = ByteBufUtils.readTag(buffer);
    }

    public IMessage onMessage(PacketUpdatePartyStatus message, MessageContext ctx) {
        ClientEvents.partyHealth = message.tag.getIntArray("partyHealth");
        ClientEvents.partyBlood = message.tag.getIntArray("partyBlood");
        ClientEvents.partyMana = message.tag.getIntArray("partyMana");
        ClientEvents.partyRunes = message.tag.getIntArray("partyRunes");
        ClientEvents.brains = message.tag.getIntArray("brains");
        return null;
    }
}
