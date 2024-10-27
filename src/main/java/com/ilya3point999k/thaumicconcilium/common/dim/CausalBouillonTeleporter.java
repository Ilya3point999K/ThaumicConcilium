package com.ilya3point999k.thaumicconcilium.common.dim;

import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CausalBouillonTeleporter extends Teleporter {
    boolean mode = false;
    public CausalBouillonTeleporter(WorldServer p_i1963_1_, boolean mode) {
        super(p_i1963_1_);
        this.mode = mode;
    }

    @Override
    public boolean makePortal(Entity p_85188_1_) {
        return true;
    }

    @Override
    public void removeStalePortalLocations(long p_85189_1_) {
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float p_77184_8_) {
        return true;
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
    }
}
