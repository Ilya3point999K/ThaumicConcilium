package com.ilya3point999k.thaumicconcilium.common.registry;

import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.tiles.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class TCTileRegistry {

    public static void init() {
        GameRegistry.registerTileEntity(DestabilizedCrystalTile.class, "DestabilizedCrystalTile");
        GameRegistry.registerTileEntity(VisCondenserTile.class, "VisCondenserTile");
        GameRegistry.registerTileEntity(HexOfPredictabilityTile.class, "HexOfPredictabilityTile");
        GameRegistry.registerTileEntity(QuicksilverCrucibleTile.class, "QuicksilverCrucibleTile");
        GameRegistry.registerTileEntity(LithographerTile.class, "LithographerTile");
        if(Integration.taintedMagic) {
            GameRegistry.registerTileEntity(FleshCrucibleTile.class, "FleshCrucibleTile");
            GameRegistry.registerTileEntity(SolidVoidTile.class, "SolidVoidTile");
        }
        if (Integration.automagy && Integration.horizons) {
            GameRegistry.registerTileEntity(RedPoweredMindTile.class, "RedPoweredMindTile");
        }

    }
}