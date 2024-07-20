package com.ilya3point999k.thaumicconcilium.common.entities;

import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketMakeHole;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.foci.ItemFocusPortableHole;

public class UpcomingHoleEntity extends Entity {
    public UpcomingHoleEntity(World w) {
        super(w);
        this.setInvisible(true);
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.worldObj.isRemote) {
            if (ticksExisted >= 30) {
                ItemFocusPortableHole.createHole(worldObj, MathHelper.floor_double(posX), MathHelper.floor_double(posY) - 1, MathHelper.floor_double(posZ), 1, (byte) 33, 120);
                TCPacketHandler.INSTANCE.sendToAllAround(new PacketMakeHole(posX, posY, posZ), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 32.0F));
                this.setDead();
            }
        } else {
            Thaumcraft.proxy.sparkle((float)posX + this.worldObj.rand.nextFloat(), (float)posY+0.1F, (float)posZ + this.worldObj.rand.nextFloat(), 2);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

}
