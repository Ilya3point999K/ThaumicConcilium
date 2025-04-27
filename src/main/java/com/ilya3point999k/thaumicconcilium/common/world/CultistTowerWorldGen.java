package com.ilya3point999k.thaumicconcilium.common.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileBanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CultistTowerWorldGen extends WorldGenerator {

    public static final List<Block> validTerrainList = new ArrayList<Block>(){{
        add(Blocks.dirt);
        add(Blocks.grass);
        add(Blocks.sand);
        add(Blocks.stone);
    }};

    public boolean hasValidPlatform(int x, int y, int z,World world) {
        for (int j = z-7; j < z+3; j++) {
            for (int i = x; i < x + 11; i++) {
                if(!validTerrainList.contains(world.getBlock(i,y,j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if(hasValidPlatform(x-2,y-1,z+3,world)) {
            setColumn(x, y, z, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 1, y, z + 1, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 3, y, z + 2, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 5, y, z + 1, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 6, y, z, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 7, y, z - 2, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 6, y, z - 4, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 5, y, z - 5, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 3, y, z - 6, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 1, y, z - 5, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x, y, z - 4, 4, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x - 1, y, z - 2, 4, ConfigBlocks.blockMagicalLog, world, 0);

            setColumn(x + 2, y, z + 1, 3, ConfigBlocks.blockCosmeticSolid, world, 12);
            setColumn(x + 4, y, z + 1, 3, ConfigBlocks.blockCosmeticSolid, world, 12);

            setColumn(x + 6, y, z - 1, 3, ConfigBlocks.blockCosmeticSolid, world, 12);
            setColumn(x + 6, y, z - 3, 3, ConfigBlocks.blockCosmeticSolid, world, 12);

            setColumn(x + 4, y, z - 5, 3, ConfigBlocks.blockCosmeticSolid, world, 12);
            setColumn(x + 2, y, z - 5, 3, ConfigBlocks.blockCosmeticSolid, world, 12);

            setColumn(x, y, z - 1, 3, ConfigBlocks.blockCosmeticSolid, world, 12);
            setColumn(x, y, z - 3, 3, ConfigBlocks.blockCosmeticSolid, world, 12);

            setColumn(x + 1, y + 4, z, 6, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 5, y + 4, z, 6, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 1, y + 4, z - 4, 6, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 5, y + 4, z - 4, 6, ConfigBlocks.blockMagicalLog, world, 0);

            setColumn(x + 3, y + 4, z + 1, 6, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 6, y + 4, z - 2, 6, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x, y + 4, z - 2, 6, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 3, y + 4, z - 5, 6, ConfigBlocks.blockMagicalLog, world, 0);


            setColumn(x + 2, y + 5, z, 5, Blocks.stained_hardened_clay, world, 14);
            setColumn(x + 4, y + 5, z, 5, Blocks.stained_hardened_clay, world, 14);
            setColumn(x + 5, y + 5, z - 1, 5, Blocks.stained_hardened_clay, world, 14);
            setColumn(x + 5, y + 5, z - 3, 5, Blocks.stained_hardened_clay, world, 14);
            setColumn(x + 2, y + 5, z - 4, 5, Blocks.stained_hardened_clay, world, 14);
            setColumn(x + 4, y + 5, z - 4, 5, Blocks.stained_hardened_clay, world, 14);
            setColumn(x + 1, y + 5, z - 1, 5, Blocks.stained_hardened_clay, world, 14);
            setColumn(x + 1, y + 5, z - 3, 5, Blocks.stained_hardened_clay, world, 14);

            setRowX(x + 2, y + 3, z + 1, 3, ConfigBlocks.blockMagicalLog, world, 4);
            setRowX(x + 2, y + 3, z - 5, 3, ConfigBlocks.blockMagicalLog, world, 4);
            setRowZ(x, y + 3, z, 3, ConfigBlocks.blockMagicalLog, world, 8);
            setRowZ(x + 6, y + 3, z, 3, ConfigBlocks.blockMagicalLog, world, 8);

            setRowX(x + 2, y + 4, z, 3, ConfigBlocks.blockMagicalLog, world, 4);
            setRowX(x + 2, y + 4, z - 4, 3, ConfigBlocks.blockMagicalLog, world, 4);
            setRowZ(x + 1, y + 4, z, 3, ConfigBlocks.blockMagicalLog, world, 8);
            setRowZ(x + 5, y + 4, z, 3, ConfigBlocks.blockMagicalLog, world, 8);

            world.setBlock(x + 3, y + 6, z + 1, ConfigBlocks.blockStairsGreatwood, 3, 3);
            world.setBlock(x + 3, y + 7, z + 1, ConfigBlocks.blockStairsGreatwood, 7, 3);

            world.setBlock(x, y + 6, z - 2, ConfigBlocks.blockStairsGreatwood, 0, 3);
            world.setBlock(x, y + 7, z - 2, ConfigBlocks.blockStairsGreatwood, 4, 3);

            world.setBlock(x + 3, y + 6, z - 5, ConfigBlocks.blockStairsGreatwood, 2, 3);
            world.setBlock(x + 3, y + 7, z - 5, ConfigBlocks.blockStairsGreatwood, 6, 3);

            world.setBlock(x + 6, y + 6, z - 2, ConfigBlocks.blockStairsGreatwood, 1, 3);
            world.setBlock(x + 6, y + 7, z - 2, ConfigBlocks.blockStairsGreatwood, 5, 3);

            world.setBlock(x + 1, y + 4, z + 1, ConfigBlocks.blockStairsGreatwood, 3, 3);
            world.setBlock(x + 3, y + 4, z + 2, ConfigBlocks.blockStairsGreatwood, 3, 3);
            world.setBlock(x + 5, y + 4, z + 1, ConfigBlocks.blockStairsGreatwood, 3, 3);

            world.setBlock(x + 1, y + 4, z - 5, ConfigBlocks.blockStairsGreatwood, 2, 3);
            world.setBlock(x + 3, y + 4, z - 6, ConfigBlocks.blockStairsGreatwood, 2, 3);
            world.setBlock(x + 5, y + 4, z - 5, ConfigBlocks.blockStairsGreatwood, 2, 3);

            world.setBlock(x, y + 4, z, ConfigBlocks.blockStairsGreatwood, 0, 3);
            world.setBlock(x - 1, y + 4, z - 2, ConfigBlocks.blockStairsGreatwood, 0, 3);
            world.setBlock(x, y + 4, z - 4, ConfigBlocks.blockStairsGreatwood, 0, 3);

            world.setBlock(x + 6, y + 4, z, ConfigBlocks.blockStairsGreatwood, 1, 3);
            world.setBlock(x + 7, y + 4, z - 2, ConfigBlocks.blockStairsGreatwood, 1, 3);
            world.setBlock(x + 6, y + 4, z - 4, ConfigBlocks.blockStairsGreatwood, 1, 3);

            setColumn(x + 1, y + 10, z + 1, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x, y + 10, z, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 3, y + 10, z + 2, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 5, y + 10, z + 1, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 6, y + 10, z, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 7, y + 10, z - 2, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 6, y + 10, z - 4, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 5, y + 10, z - 5, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 3, y + 10, z - 6, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x + 1, y + 10, z - 5, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x, y + 10, z - 4, 2, ConfigBlocks.blockMagicalLog, world, 0);
            setColumn(x - 1, y + 10, z - 2, 2, ConfigBlocks.blockMagicalLog, world, 0);


            world.setBlock(x + 1, y + 9, z + 1, ConfigBlocks.blockStairsGreatwood, 7, 3);
            world.setBlock(x + 3, y + 9, z + 2, ConfigBlocks.blockStairsGreatwood, 7, 3);
            world.setBlock(x + 5, y + 9, z + 1, ConfigBlocks.blockStairsGreatwood, 7, 3);

            world.setBlock(x + 1, y + 9, z - 5, ConfigBlocks.blockStairsGreatwood, 6, 3);
            world.setBlock(x + 3, y + 9, z - 6, ConfigBlocks.blockStairsGreatwood, 6, 3);
            world.setBlock(x + 5, y + 9, z - 5, ConfigBlocks.blockStairsGreatwood, 6, 3);

            world.setBlock(x, y + 9, z, ConfigBlocks.blockStairsGreatwood, 4, 3);
            world.setBlock(x - 1, y + 9, z - 2, ConfigBlocks.blockStairsGreatwood, 4, 3);
            world.setBlock(x, y + 9, z - 4, ConfigBlocks.blockStairsGreatwood, 4, 3);

            world.setBlock(x + 6, y + 9, z, ConfigBlocks.blockStairsGreatwood, 5, 3);
            world.setBlock(x + 7, y + 9, z - 2, ConfigBlocks.blockStairsGreatwood, 5, 3);
            world.setBlock(x + 6, y + 9, z - 4, ConfigBlocks.blockStairsGreatwood, 5, 3);

            setRowZ(x, y + 10, z, 3, ConfigBlocks.blockMagicalLog, world, 8);
            setRowZ(x + 6, y + 10, z, 3, ConfigBlocks.blockMagicalLog, world, 8);
            setRowX(x + 2, y + 10, z + 1, 3, ConfigBlocks.blockMagicalLog, world, 4);
            setRowX(x + 2, y + 10, z - 5, 3, ConfigBlocks.blockMagicalLog, world, 4);

            world.setBlock(x + 3, y + 8, z + 3, ConfigBlocks.blockWoodenDevice, 8, 3);
            TileEntity te = world.getTileEntity(x + 3, y + 8, z + 3);
            if(te != null && te instanceof TileBanner) {
                ((TileBanner) te).setFacing((byte) 0);
                ((TileBanner) te).setWall(true);
            }

            world.setBlock(x + 3, y + 8, z - 7, ConfigBlocks.blockWoodenDevice, 8, 3);
            te = world.getTileEntity(x + 3, y + 8, z - 7);
            if(te != null && te instanceof TileBanner) {
                ((TileBanner) te).setFacing((byte) 8);
                ((TileBanner) te).setWall(true);
            }

            world.setBlock(x - 2, y + 8, z - 2, ConfigBlocks.blockWoodenDevice, 8, 3);
            te = world.getTileEntity(x - 2, y + 8, z - 2);
            if(te != null && te instanceof TileBanner) {
                ((TileBanner) te).setFacing((byte) 4);
                ((TileBanner) te).setWall(true);
            }

            world.setBlock(x + 8, y + 8, z - 2, ConfigBlocks.blockWoodenDevice, 8, 3);
            te = world.getTileEntity(x + 8, y + 8, z - 2);
            if(te != null && te instanceof TileBanner) {
                ((TileBanner) te).setFacing((byte) 12);
                ((TileBanner) te).setWall(true);
            }

            return true;
        }else {
            return false;
        }
    }

    public void setColumn(int x, int y, int z, int expand, Block block, World world, int meta) {
        for(int i = y; i < y+expand; i++) {
            world.setBlock(x,i,z,block,meta,3);
        }
    }

    public void setRowX(int x, int y, int z, int expandX, Block block, World world, int meta) {
        for(int i = x; i < x+expandX; i++) {
            world.setBlock(i, y, z, block, meta, 3);
        }
    }

    public void setRowZ(int x, int y, int z, int expandZ, Block block, World world, int meta) {
        for(int i = z-expandZ; i < z; i++) {
            world.setBlock(x, y, i, block, meta, 3);
        }
    }
}
