package com.ilya3point999k.thaumicconcilium.common.network.packets;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.PontifexRobe;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketTogglePontifexRobe implements IMessage, IMessageHandler<PacketTogglePontifexRobe, IMessage> {
    private int dim;
    private int playerID;

    public PacketTogglePontifexRobe() {
    }

    public PacketTogglePontifexRobe(EntityPlayer player) {
        this.dim = player.worldObj.provider.dimensionId;
        this.playerID = player.getEntityId();
    }

    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.dim);
        buffer.writeInt(this.playerID);
    }

    public void fromBytes(ByteBuf buffer) {
        this.dim = buffer.readInt();
        this.playerID = buffer.readInt();
    }

    public IMessage onMessage(PacketTogglePontifexRobe message, MessageContext ctx) {
        World world = DimensionManager.getWorld(message.dim);
        if (world != null && (ctx.getServerHandler().playerEntity == null || ctx.getServerHandler().playerEntity.getEntityId() == message.playerID)) {
            Entity player = world.getEntityByID(message.playerID);
            if (player instanceof EntityPlayer) {
                if (!PontifexRobe.isFullSet((EntityPlayer) player)) return null;
                TCPlayerCapabilities capabilities = TCPlayerCapabilities.get((EntityPlayer) player);
                if (capabilities == null) return null;
                if (!capabilities.pontifexRobeToggle && capabilities.ethereal) return null;
                capabilities.pontifexRobeToggle = !capabilities.pontifexRobeToggle;
                capabilities.ethereal = !capabilities.ethereal;
                if (capabilities.ethereal) {
                    world.playSoundAtEntity(player, ThaumicConcilium.MODID + ":shackles", 0.9F, 1.0F);
                }
                capabilities.sync();
            }
            return null;
        } else {
            return null;
        }
    }
}
