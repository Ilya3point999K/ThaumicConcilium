package com.ilya3point999k.thaumicconcilium.common.world;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class ThaumicConciliumWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int x, int z, World world, IChunkProvider iChunkProvider, IChunkProvider iChunkProvider1) {
        if(world.provider.dimensionId != 0)
            return;
        if (random.nextInt(10) == 0) {
            CultistTowerWorldGen tower = new CultistTowerWorldGen();
            int randPosX = x * 16 + random.nextInt(16);
            int randPosZ = z * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX, randPosZ);
            tower.generate(world,random,randPosX,randPosY,randPosZ);
        }
    }
}
