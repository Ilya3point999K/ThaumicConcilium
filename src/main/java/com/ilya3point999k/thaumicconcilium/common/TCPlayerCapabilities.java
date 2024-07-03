package com.ilya3point999k.thaumicconcilium.common;

import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketSyncPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.FakePlayer;
import thaumcraft.common.lib.FakeThaumcraftPlayer;

public class TCPlayerCapabilities implements IExtendedEntityProperties {
    public final static String EXT_PROP_NAME = ThaumicConcilium.MODID + "ExtendedPlayer";
    public EntityPlayer player;
    public boolean lithographed;
    public boolean relieved;
    public boolean pontifexRobeToggle;
    public String[] monitored;
    public int chainedTime;
    public boolean ethereal;
    public int fleshAmount;
    public TCPlayerCapabilities(EntityPlayer player)
    {
        if(!(player instanceof FakePlayer) && !(player instanceof FakeThaumcraftPlayer))
        {
            this.player = player;
        } else {
            player = null;
        }
        lithographed = false;
        relieved = false;
        chainedTime = 0;
        ethereal = false;
        fleshAmount = 0;
        pontifexRobeToggle = false;
        monitored = new String[4];
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(TCPlayerCapabilities.EXT_PROP_NAME, new TCPlayerCapabilities(player));
    }

    public static final TCPlayerCapabilities get(EntityPlayer player)
    {
        return (TCPlayerCapabilities) player.getExtendedProperties(EXT_PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        for (int i = 0; i < 4; i++){
            if (monitored[i] != null && !monitored[i].isEmpty()) {
                properties.setString("monitored" + i, monitored[i]);
            } else {
                properties.setString("monitored" + i, "!!!NOTHING!!!");
            }
        }
        properties.setBoolean("lithographed", lithographed);
        properties.setBoolean("relieved", relieved);
        properties.setInteger("chainedTime", chainedTime);
        properties.setBoolean("ethereal", ethereal);
        properties.setBoolean("pontifexRobeToggle", pontifexRobeToggle);
        properties.setInteger("fleshAmount", fleshAmount);
        compound.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
        for (int i = 0; i < 4; i++){
            monitored[i] = properties.getString("monitored" + i);
        }
        lithographed = properties.getBoolean("lithographed");
        relieved = properties.getBoolean("relieved");
        chainedTime = properties.getInteger("chainedTime");
        ethereal = properties.getBoolean("ethereal");
        pontifexRobeToggle = properties.getBoolean("pontifexRobeToggle");
        fleshAmount = properties.getInteger("fleshAmount");
    }

    @Override
    public void init(Entity entity, World world) {
        if(!(player instanceof FakePlayer) && !(player instanceof FakeThaumcraftPlayer))
        {
            this.player = player;
        } else {
            player = null;
        }
    }

    public void sync() {
        if (this.player == null)
            return;
        sync(this.player);
    }

    public void sync(EntityPlayer player) {
        if (player == null || player instanceof FakePlayer || player instanceof FakeThaumcraftPlayer)
            return;
        NBTTagCompound data = new NBTTagCompound();
        saveNBTData(data);
        data.setString("SyncName", player.getCommandSenderName());
        TCPacketHandler.INSTANCE.sendTo(new PacketSyncPlayer(data), (EntityPlayerMP)player);
    }

}
