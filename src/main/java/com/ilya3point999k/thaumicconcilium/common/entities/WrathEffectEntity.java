package com.ilya3point999k.thaumicconcilium.common.entities;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class WrathEffectEntity extends Entity implements IEntityAdditionalSpawnData {
    public int age;
    public boolean burnout;
    public static final int[][] iraImage = {{0, 5}, {1, 4}, {2, 3}, {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {8, 3}, {9, 3}, {10, 3}, {11, 3}, {12, 4}, {13, 4}, {14, 1}, {14, 5},
            {14, 10}, {14, 12}, {15, 2}, {15, 12}, {16, 3}, {16, 13}, {17, 1}, {17, 4}, {17, 9}, {17, 14}, {18, 2}, {18, 5}, {18, 7}, {18, 9}, {18, 13}, {18, 16}, {19, 3}, {19, 11},
            {19, 14}, {20, 3}, {20, 12}, {20, 14}, {21, 3}, {21, 12}, {21, 14}, {22, 3}, {22, 12}, {22, 14}, {23, 3}, {25, 2}, {26, 1}, {24, 3}, {23, 2}, {22, 1}};

    private final int death = iraImage.length;

    public WrathEffectEntity(World w) {
        super(w);
        this.setInvisible(true);
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
    }
    public WrathEffectEntity(World w, EntityPlayer p, boolean burnout){
        this(w);
        this.rotationYaw = p.rotationYaw;
        this.burnout = burnout;
    }
    @Override
    protected void entityInit() {
        
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        age+=2;
        if(!this.worldObj.isRemote) {
            if (age >= death) {
                this.setDead();
            }
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        this.burnout = p_70037_1_.getBoolean("Burnout");
        this.age = p_70037_1_.getInteger("LifeTime");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        p_70014_1_.setBoolean("Burnout", this.burnout);
        p_70014_1_.setInteger("LifeTime", this.age);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
            buffer.writeBoolean(this.burnout);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        try {
            this.burnout = additionalData.readBoolean();
        } catch (Exception e){}
    }
}
