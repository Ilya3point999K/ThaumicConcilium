package com.ilya3point999k.thaumicconcilium.common.dim;

import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CausalBouillonTeleporter extends Teleporter {
    private final boolean mode;
    public CausalBouillonTeleporter(WorldServer world, boolean mode) {
        super(world);
        this.mode = mode;
    }

    @Override
    public boolean makePortal(Entity entity) {
        return false;
    }

    @Override
    public void removeStalePortalLocations(long p_85189_1_) { }

    @Override
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float p_77184_8_) {
        return false;
    }

    @Override
    public void placeInPortal(Entity entity, double x, double y, double z, float p_77185_8_) {
        if(entity.dimension != TCConfig.causalBouillonID){
            if(mode){
                entity.setLocationAndAngles(x / 10.0, y, z / 10.0, entity.rotationPitch, entity.rotationYaw);
            } else {
                entity.setLocationAndAngles(x * 10.0, y, z * 10.0, entity.rotationPitch, entity.rotationYaw);
            }
        }
        else {
            entity.setLocationAndAngles(x, 5, z, entity.rotationPitch, entity.rotationYaw);
        }
        entity.motionX = 0;
        entity.motionY = 0;
        entity.motionZ = 0;
    }
}