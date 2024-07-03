package com.ilya3point999k.thaumicconcilium.common.network.packets;

import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.EtherealManipulatorFoci;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import thaumcraft.common.items.wands.ItemWandCasting;

public class PacketChangeEtherealRange implements IMessage, IMessageHandler<PacketChangeEtherealRange, IMessage> {
    private int dim;
    private int playerID;
    private int dwheel;

    public PacketChangeEtherealRange() {
    }

    public PacketChangeEtherealRange(EntityPlayer player, int dwheel) {
        this.dim = player.worldObj.provider.dimensionId;
        this.playerID = player.getEntityId();
        this.dwheel = dwheel;
    }

    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.dim);
        buffer.writeInt(this.playerID);
        buffer.writeInt(this.dwheel);
    }

    public void fromBytes(ByteBuf buffer) {
        this.dim = buffer.readInt();
        this.playerID = buffer.readInt();
        this.dwheel = buffer.readInt();
    }

    public IMessage onMessage(PacketChangeEtherealRange message, MessageContext ctx) {
        World world = DimensionManager.getWorld(message.dim);
        if (world != null && (ctx.getServerHandler().playerEntity == null || ctx.getServerHandler().playerEntity.getEntityId() == message.playerID)) {
            Entity player = world.getEntityByID(message.playerID);
            if (player != null && player instanceof EntityPlayer && ((EntityPlayer)player).getHeldItem() != null && ((EntityPlayer)player).getHeldItem().getItem() instanceof ItemWandCasting && ((ItemWandCasting)((EntityPlayer)player).getHeldItem().getItem()).getFocusItem(((EntityPlayer)player).getHeldItem()).getItem() instanceof EtherealManipulatorFoci) {
                ItemStack wand = ((EntityPlayer) player).getHeldItem();
                NBTTagCompound fociTag = wand.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
                fociTag.setFloat("Range", MathHelper.clamp_float(fociTag.getFloat("Range") + message.dwheel / 120.0F, 1.0F, 16.0F));
                wand.getTagCompound().getCompoundTag("focus").setTag("tag", fociTag);
            }
            return null;
        } else {
            return null;
        }
    }
}