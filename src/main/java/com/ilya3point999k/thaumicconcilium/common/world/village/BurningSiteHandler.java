package com.ilya3point999k.thaumicconcilium.common.world.village;

import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class BurningSiteHandler implements VillagerRegistry.IVillageCreationHandler {
    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
        return new StructureVillagePieces.PieceWeight(
                BurningSite.class,
                TCConfig.burningSitesGenChance,
                1
        );
    }

    @Override
    public Class<?> getComponentClass() {
        return BurningSite.class;
    }

    @Override
    public Object buildComponent(StructureVillagePieces.PieceWeight weight,
                                 StructureVillagePieces.Start start,
                                 List pieces,
                                 Random rand,
                                 int x, int y, int z,
                                 int coordBaseMode,
                                 int type) {
        return BurningSite.buildComponent(
                start, pieces, rand, x, y, z, coordBaseMode, type);
    }
}
