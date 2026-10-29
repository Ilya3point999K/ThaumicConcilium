package com.ilya3point999k.thaumicconcilium.common.world;

import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.world.houses.*;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class ThaumicConciliumWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int x, int z, World world, IChunkProvider iChunkProvider, IChunkProvider iChunkProvider1) {
        if(world.provider.dimensionId != 0)
            return;
        if (Integration.dyes && TCConfig.generateCrimsonAvanposts) {
            if (random.nextInt(TCConfig.crimsonAvanpostGenChance) == 0) {
                CultistTowerWorldGen tower = new CultistTowerWorldGen();
                int randPosX = x * 16 + random.nextInt(16);
                int randPosZ = z * 16 + random.nextInt(16);
                int randPosY = world.getHeightValue(randPosX, randPosZ);
                tower.generate(world, random, randPosX, randPosY, randPosZ);
            }
        }
        /*if (random.nextInt(20) == 0) {
            HouseResearcher house = new HouseResearcher();
            int randPosX = x * 16 + random.nextInt(16);
            int randPosZ = z * 16 + random.nextInt(16);
            int randPosY = MathHelper.clamp_int(world.getHeightValue(randPosX, randPosZ) - 1, 0, 255 - HouseResearcher.getHeight());
            house.generate(world, random, randPosX, randPosY, randPosZ);
        }*/
        /*if (random.nextInt(20) == 0) {
            HouseArtificer house = new HouseArtificer();
            int randPosX = x * 16 + random.nextInt(16);
            int randPosZ = z * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX, randPosZ) - 1;
            house.generate(world, random, randPosX, randPosY, randPosZ);
        }
        if (random.nextInt(20) == 0) {
            HouseParanoidFortress house = new HouseParanoidFortress();
            int randPosX = x * 16 + random.nextInt(16);
            int randPosZ = z * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX, randPosZ) - 1;
            house.generate(world, random, randPosX, randPosY, randPosZ);
        }*/
        /*if (random.nextInt(20) == 0) {
            HouseGolemancer house = new HouseGolemancer();
            int randPosX = x * 16 + random.nextInt(16);
            int randPosZ = z * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX, randPosZ) - 1;
            house.generate(world, random, randPosX, randPosY, randPosZ);
        }*/
        /*if (random.nextInt(20) == 0) {
            HouseTower house = new HouseTower();
            int randPosX = x * 16 + random.nextInt(16);
            int randPosZ = z * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX, randPosZ) - 1;
            house.generate(world, random, randPosX, randPosY, randPosZ);
        }*/
        /*if (random.nextInt(20) == 0) {
            HouseRuins house = new HouseRuins();
            int randPosX = x * 16 + random.nextInt(16);
            int randPosZ = z * 16 + random.nextInt(16);
            int randPosY = world.getHeightValue(randPosX, randPosZ);
            house.generate(world, random, randPosX, randPosY, randPosZ);
        }*/
    }
}
