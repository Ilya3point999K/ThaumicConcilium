package com.ilya3point999k.thaumicconcilium.common.world.houses;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thaumcraft.common.config.ConfigBlocks;

import java.util.Random;

public class HouseArtificer extends WorldGenerator {

    private static Block MOD_THAUMCRAFT_BLOCKAIRY = ConfigBlocks.blockAiry; // TODO provide Thaumcraft:blockAiry
    private static Block MOD_THAUMCRAFT_BLOCKCANDLE = ConfigBlocks.blockCandle; // TODO provide Thaumcraft:blockCandle
    private static Block MOD_THAUMCRAFT_BLOCKCOSMETICSLABSTONE = ConfigBlocks.blockSlabStone; // TODO provide Thaumcraft:blockCosmeticSlabStone
    private static Block MOD_THAUMCRAFT_BLOCKCOSMETICSOLID = ConfigBlocks.blockCosmeticSolid; // TODO provide Thaumcraft:blockCosmeticSolid
    private static Block MOD_THAUMCRAFT_BLOCKCRYSTAL = ConfigBlocks.blockCrystal; // TODO provide Thaumcraft:blockCrystal
    private static Block MOD_THAUMCRAFT_BLOCKJAR = ConfigBlocks.blockJar; // TODO provide Thaumcraft:blockJar
    private static Block MOD_THAUMCRAFT_BLOCKMETALDEVICE = ConfigBlocks.blockMetalDevice; // TODO provide Thaumcraft:blockMetalDevice
    private static Block MOD_THAUMCRAFT_BLOCKSTONEDEVICE = ConfigBlocks.blockStoneDevice; // TODO provide Thaumcraft:blockStoneDevice
    private static Block MOD_THAUMCRAFT_BLOCKTABLE = ConfigBlocks.blockTable; // TODO provide Thaumcraft:blockTable
    private static Block MOD_THAUMCRAFT_BLOCKTUBE = ConfigBlocks.blockTube; // TODO provide Thaumcraft:blockTube

    private static final Block[] PALETTE = new Block[] {
            Blocks.grass, // palette[0] = minecraft:grass meta 0
            Blocks.gravel, // palette[1] = minecraft:gravel meta 0
            Blocks.dirt, // palette[2] = minecraft:dirt meta 0
            MOD_THAUMCRAFT_BLOCKCOSMETICSOLID, // palette[3] = Thaumcraft:blockCosmeticSolid meta 3
            Blocks.stonebrick, // palette[4] = minecraft:stonebrick meta 0
            Blocks.stone, // palette[5] = minecraft:stone meta 0
            MOD_THAUMCRAFT_BLOCKCOSMETICSOLID, // palette[6] = Thaumcraft:blockCosmeticSolid meta 7
            MOD_THAUMCRAFT_BLOCKCOSMETICSOLID, // palette[7] = Thaumcraft:blockCosmeticSolid meta 6
            Blocks.sand, // palette[8] = minecraft:sand meta 0
            Blocks.water, // palette[9] = minecraft:water meta 0
            Blocks.air, // palette[10] = minecraft:air meta 0
            Blocks.stone_stairs, // palette[11] = minecraft:stone_stairs meta 2
            Blocks.log, // palette[12] = minecraft:log meta 0
            Blocks.fence, // palette[13] = minecraft:fence meta 0
            MOD_THAUMCRAFT_BLOCKAIRY, // palette[14] = Thaumcraft:blockAiry meta 4
            Blocks.cobblestone_wall, // palette[15] = minecraft:cobblestone_wall meta 0
            Blocks.spruce_stairs, // palette[16] = minecraft:spruce_stairs meta 5
            Blocks.spruce_stairs, // palette[17] = minecraft:spruce_stairs meta 4
            Blocks.stone_stairs, // palette[18] = minecraft:stone_stairs meta 0
            Blocks.spruce_stairs, // palette[19] = minecraft:spruce_stairs meta 2
            Blocks.stone_stairs, // palette[20] = minecraft:stone_stairs meta 1
            MOD_THAUMCRAFT_BLOCKCOSMETICSLABSTONE, // palette[21] = Thaumcraft:blockCosmeticSlabStone meta 0
            Blocks.spruce_stairs, // palette[22] = minecraft:spruce_stairs meta 7
            Blocks.bed, // palette[23] = minecraft:bed meta 0
            MOD_THAUMCRAFT_BLOCKSTONEDEVICE, // palette[24] = Thaumcraft:blockStoneDevice meta 1
            MOD_THAUMCRAFT_BLOCKTABLE, // palette[25] = Thaumcraft:blockTable meta 15
            Blocks.bed, // palette[26] = minecraft:bed meta 8
            Blocks.spruce_stairs, // palette[27] = minecraft:spruce_stairs meta 6
            Blocks.air, // palette[28] = minecraft:air meta 3
            Blocks.air, // palette[29] = minecraft:air meta 7
            Blocks.reeds, // palette[30] = minecraft:reeds meta 0
            Blocks.wooden_slab, // palette[31] = minecraft:wooden_slab meta 9
            MOD_THAUMCRAFT_BLOCKJAR, // palette[32] = Thaumcraft:blockJar meta 0
            Blocks.furnace, // palette[33] = minecraft:furnace meta 2
            Blocks.stone_stairs, // palette[34] = minecraft:stone_stairs meta 3
            Blocks.planks, // palette[35] = minecraft:planks meta 1
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[36] = Thaumcraft:blockCandle meta 10
            Blocks.bookshelf, // palette[37] = minecraft:bookshelf meta 0
            Blocks.chest, // palette[38] = minecraft:chest meta 5
            Blocks.skull, // palette[39] = minecraft:skull meta 1
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[40] = Thaumcraft:blockCandle meta 11
            Blocks.glass, // palette[41] = minecraft:glass meta 0
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[42] = Thaumcraft:blockCandle meta 3
            MOD_THAUMCRAFT_BLOCKTUBE, // palette[43] = Thaumcraft:blockTube meta 0
            MOD_THAUMCRAFT_BLOCKMETALDEVICE, // palette[44] = Thaumcraft:blockMetalDevice meta 1
            Blocks.lit_pumpkin, // palette[45] = minecraft:lit_pumpkin meta 2
            Blocks.spruce_stairs, // palette[46] = minecraft:spruce_stairs meta 0
            Blocks.spruce_stairs, // palette[47] = minecraft:spruce_stairs meta 1
            Blocks.log, // palette[48] = minecraft:log meta 8
            MOD_THAUMCRAFT_BLOCKCRYSTAL, // palette[49] = Thaumcraft:blockCrystal meta 0
            Blocks.log, // palette[50] = minecraft:log meta 4
            Blocks.spruce_stairs, // palette[51] = minecraft:spruce_stairs meta 3
            Blocks.wooden_slab, // palette[52] = minecraft:wooden_slab meta 1
            MOD_THAUMCRAFT_BLOCKAIRY // palette[53] = Thaumcraft:blockAiry meta 1
    };

    private static final byte[] META = new byte[] {
            (byte)0, (byte)0, (byte)0, (byte)3, (byte)0, (byte)0, (byte)7, (byte)6, (byte)0, (byte)0, (byte)0, (byte)2, (byte)0, (byte)0, (byte)4, (byte)0,
            (byte)5, (byte)4, (byte)0, (byte)2, (byte)1, (byte)0, (byte)7, (byte)0, (byte)1, (byte)15, (byte)8, (byte)6, (byte)3, (byte)7, (byte)0, (byte)9,
            (byte)0, (byte)2, (byte)3, (byte)1, (byte)10, (byte)0, (byte)5, (byte)1, (byte)11, (byte)0, (byte)3, (byte)0, (byte)1, (byte)2, (byte)0, (byte)1,
            (byte)8, (byte)0, (byte)4, (byte)3, (byte)1, (byte)1
    };

    // x, y, z, sizeX, sizeY, sizeZ, palette index
    private static final short[] BOXES = new short[] {
            (short)0, (short)0, (short)0, (short)6, (short)1, (short)1, (short)0, (short)6, (short)0, (short)0, (short)3, (short)1, (short)1, (short)1, (short)9, (short)0,
            (short)0, (short)6, (short)1, (short)1, (short)0, (short)0, (short)0, (short)1, (short)4, (short)1, (short)1, (short)0, (short)4, (short)0, (short)1, (short)2,
            (short)1, (short)1, (short)2, (short)6, (short)0, (short)1, (short)3, (short)1, (short)1, (short)3, (short)9, (short)0, (short)1, (short)2, (short)1, (short)1,
            (short)2, (short)11, (short)0, (short)1, (short)4, (short)1, (short)1, (short)0, (short)0, (short)0, (short)2, (short)3, (short)1, (short)1, (short)0, (short)3,
            (short)0, (short)2, (short)1, (short)1, (short)1, (short)2, (short)4, (short)0, (short)2, (short)7, (short)1, (short)1, (short)4, (short)11, (short)0, (short)2,
            (short)1, (short)1, (short)1, (short)2, (short)12, (short)0, (short)2, (short)3, (short)1, (short)1, (short)0, (short)0, (short)0, (short)3, (short)2, (short)1,
            (short)1, (short)0, (short)2, (short)0, (short)3, (short)1, (short)1, (short)1, (short)2, (short)3, (short)0, (short)3, (short)2, (short)1, (short)1, (short)4,
            (short)5, (short)0, (short)3, (short)2, (short)1, (short)2, (short)5, (short)7, (short)0, (short)3, (short)1, (short)1, (short)3, (short)4, (short)8, (short)0,
            (short)3, (short)2, (short)1, (short)2, (short)5, (short)10, (short)0, (short)3, (short)2, (short)1, (short)1, (short)4, (short)12, (short)0, (short)3, (short)1,
            (short)1, (short)1, (short)2, (short)13, (short)0, (short)3, (short)2, (short)1, (short)1, (short)0, (short)0, (short)0, (short)4, (short)1, (short)1, (short)2,
            (short)0, (short)1, (short)0, (short)4, (short)1, (short)1, (short)2, (short)2, (short)2, (short)0, (short)4, (short)2, (short)1, (short)1, (short)4, (short)4,
            (short)0, (short)4, (short)1, (short)1, (short)3, (short)5, (short)10, (short)0, (short)4, (short)1, (short)1, (short)3, (short)5, (short)11, (short)0, (short)4,
            (short)2, (short)1, (short)1, (short)4, (short)13, (short)0, (short)4, (short)1, (short)1, (short)2, (short)2, (short)14, (short)0, (short)4, (short)1, (short)1,
            (short)2, (short)0, (short)2, (short)0, (short)5, (short)1, (short)1, (short)6, (short)4, (short)3, (short)0, (short)5, (short)1, (short)1, (short)2, (short)5,
            (short)5, (short)0, (short)5, (short)1, (short)1, (short)1, (short)5, (short)6, (short)0, (short)5, (short)1, (short)1, (short)1, (short)4, (short)8, (short)0,
            (short)5, (short)1, (short)1, (short)1, (short)4, (short)9, (short)0, (short)5, (short)1, (short)1, (short)1, (short)5, (short)11, (short)0, (short)5, (short)1,
            (short)1, (short)2, (short)5, (short)12, (short)0, (short)5, (short)1, (short)1, (short)6, (short)4, (short)0, (short)0, (short)6, (short)1, (short)1, (short)3,
            (short)2, (short)1, (short)0, (short)6, (short)1, (short)1, (short)3, (short)4, (short)5, (short)0, (short)6, (short)1, (short)1, (short)3, (short)4, (short)6,
            (short)0, (short)6, (short)1, (short)1, (short)1, (short)6, (short)7, (short)0, (short)6, (short)1, (short)1, (short)1, (short)7, (short)8, (short)0, (short)6,
            (short)1, (short)1, (short)1, (short)5, (short)9, (short)0, (short)6, (short)1, (short)1, (short)3, (short)4, (short)13, (short)0, (short)6, (short)1, (short)1,
            (short)3, (short)4, (short)14, (short)0, (short)6, (short)1, (short)1, (short)3, (short)2, (short)3, (short)0, (short)7, (short)2, (short)1, (short)1, (short)4,
            (short)6, (short)0, (short)7, (short)1, (short)1, (short)1, (short)7, (short)7, (short)0, (short)7, (short)1, (short)1, (short)1, (short)5, (short)8, (short)0,
            (short)7, (short)1, (short)1, (short)1, (short)7, (short)10, (short)0, (short)7, (short)2, (short)1, (short)1, (short)4, (short)3, (short)0, (short)8, (short)2,
            (short)1, (short)2, (short)5, (short)6, (short)0, (short)8, (short)1, (short)1, (short)1, (short)5, (short)7, (short)0, (short)8, (short)1, (short)1, (short)1,
            (short)7, (short)8, (short)0, (short)8, (short)1, (short)1, (short)1, (short)5, (short)10, (short)0, (short)8, (short)2, (short)1, (short)2, (short)5, (short)0,
            (short)0, (short)9, (short)1, (short)1, (short)5, (short)0, (short)1, (short)0, (short)9, (short)1, (short)1, (short)2, (short)2, (short)5, (short)0, (short)9,
            (short)1, (short)1, (short)3, (short)5, (short)6, (short)0, (short)9, (short)3, (short)1, (short)1, (short)4, (short)9, (short)0, (short)9, (short)1, (short)1,
            (short)3, (short)5, (short)13, (short)0, (short)9, (short)1, (short)1, (short)2, (short)2, (short)14, (short)0, (short)9, (short)1, (short)1, (short)5, (short)0,
            (short)3, (short)0, (short)10, (short)1, (short)1, (short)1, (short)4, (short)4, (short)0, (short)10, (short)1, (short)1, (short)1, (short)5, (short)6, (short)0,
            (short)10, (short)1, (short)1, (short)2, (short)5, (short)7, (short)0, (short)10, (short)1, (short)1, (short)3, (short)4, (short)8, (short)0, (short)10, (short)1,
            (short)1, (short)2, (short)5, (short)10, (short)0, (short)10, (short)1, (short)1, (short)1, (short)5, (short)11, (short)0, (short)10, (short)1, (short)1, (short)2,
            (short)4, (short)1, (short)0, (short)11, (short)1, (short)1, (short)3, (short)0, (short)2, (short)0, (short)11, (short)1, (short)1, (short)1, (short)2, (short)3,
            (short)0, (short)11, (short)1, (short)1, (short)1, (short)8, (short)4, (short)0, (short)11, (short)1, (short)1, (short)2, (short)4, (short)10, (short)0, (short)11,
            (short)1, (short)1, (short)2, (short)4, (short)12, (short)0, (short)11, (short)1, (short)1, (short)1, (short)2, (short)13, (short)0, (short)11, (short)1, (short)1,
            (short)3, (short)0, (short)2, (short)0, (short)12, (short)1, (short)1, (short)2, (short)0, (short)3, (short)0, (short)12, (short)1, (short)1, (short)1, (short)9,
            (short)5, (short)0, (short)12, (short)2, (short)1, (short)1, (short)4, (short)8, (short)0, (short)12, (short)2, (short)1, (short)1, (short)4, (short)11, (short)0,
            (short)12, (short)1, (short)1, (short)1, (short)2, (short)12, (short)0, (short)12, (short)1, (short)1, (short)2, (short)0, (short)3, (short)0, (short)13, (short)1,
            (short)1, (short)1, (short)0, (short)4, (short)0, (short)13, (short)7, (short)1, (short)1, (short)2, (short)11, (short)0, (short)13, (short)1, (short)1, (short)1,
            (short)0, (short)0, (short)1, (short)0, (short)15, (short)8, (short)1, (short)10, (short)0, (short)1, (short)1, (short)3, (short)8, (short)1, (short)10, (short)3,
            (short)1, (short)1, (short)1, (short)1, (short)1, (short)11, (short)4, (short)1, (short)1, (short)1, (short)5, (short)1, (short)12, (short)5, (short)1, (short)1,
            (short)1, (short)4, (short)1, (short)4, (short)6, (short)1, (short)1, (short)1, (short)2, (short)1, (short)13, (short)7, (short)1, (short)1, (short)1, (short)2,
            (short)1, (short)14, (short)8, (short)1, (short)1, (short)1, (short)2, (short)1, (short)13, (short)9, (short)1, (short)1, (short)1, (short)4, (short)1, (short)4,
            (short)10, (short)1, (short)1, (short)1, (short)5, (short)1, (short)12, (short)11, (short)1, (short)1, (short)1, (short)1, (short)1, (short)11, (short)12, (short)1,
            (short)1, (short)3, (short)8, (short)1, (short)10, (short)0, (short)1, (short)2, (short)2, (short)8, (short)1, (short)10, (short)2, (short)1, (short)2, (short)1,
            (short)1, (short)1, (short)15, (short)3, (short)1, (short)2, (short)1, (short)1, (short)1, (short)4, (short)4, (short)1, (short)2, (short)1, (short)1, (short)2,
            (short)7, (short)5, (short)1, (short)2, (short)4, (short)3, (short)4, (short)10, (short)9, (short)1, (short)2, (short)1, (short)1, (short)1, (short)16, (short)10,
            (short)1, (short)2, (short)1, (short)1, (short)1, (short)17, (short)11, (short)1, (short)2, (short)1, (short)1, (short)1, (short)4, (short)12, (short)1, (short)2,
            (short)1, (short)1, (short)1, (short)15, (short)13, (short)1, (short)2, (short)2, (short)8, (short)1, (short)10, (short)0, (short)1, (short)3, (short)1, (short)8,
            (short)3, (short)10, (short)1, (short)1, (short)3, (short)1, (short)1, (short)1, (short)18, (short)2, (short)1, (short)3, (short)1, (short)1, (short)1, (short)4,
            (short)3, (short)1, (short)3, (short)1, (short)1, (short)2, (short)7, (short)9, (short)1, (short)3, (short)1, (short)5, (short)9, (short)10, (short)10, (short)1,
            (short)3, (short)1, (short)1, (short)1, (short)19, (short)11, (short)1, (short)3, (short)1, (short)4, (short)9, (short)10, (short)12, (short)1, (short)3, (short)1,
            (short)1, (short)1, (short)4, (short)13, (short)1, (short)3, (short)1, (short)1, (short)1, (short)20, (short)14, (short)1, (short)3, (short)1, (short)8, (short)3,
            (short)10, (short)1, (short)1, (short)4, (short)1, (short)5, (short)1, (short)12, (short)2, (short)1, (short)4, (short)1, (short)1, (short)1, (short)16, (short)4,
            (short)1, (short)4, (short)1, (short)5, (short)3, (short)10, (short)10, (short)1, (short)4, (short)1, (short)5, (short)3, (short)10, (short)12, (short)1, (short)4,
            (short)1, (short)1, (short)1, (short)17, (short)13, (short)1, (short)4, (short)1, (short)5, (short)1, (short)12, (short)1, (short)1, (short)5, (short)2, (short)4,
            (short)1, (short)4, (short)3, (short)1, (short)5, (short)1, (short)4, (short)6, (short)10, (short)12, (short)1, (short)5, (short)1, (short)3, (short)5, (short)10,
            (short)13, (short)1, (short)5, (short)1, (short)4, (short)1, (short)4, (short)0, (short)1, (short)6, (short)1, (short)2, (short)1, (short)12, (short)1, (short)1,
            (short)6, (short)1, (short)1, (short)1, (short)16, (short)2, (short)1, (short)6, (short)1, (short)3, (short)3, (short)10, (short)5, (short)1, (short)6, (short)1,
            (short)5, (short)6, (short)10, (short)6, (short)1, (short)6, (short)1, (short)1, (short)1, (short)21, (short)7, (short)1, (short)6, (short)1, (short)8, (short)1,
            (short)10, (short)8, (short)1, (short)6, (short)1, (short)1, (short)1, (short)6, (short)13, (short)1, (short)6, (short)1, (short)1, (short)1, (short)22, (short)14,
            (short)1, (short)6, (short)1, (short)2, (short)1, (short)12, (short)0, (short)1, (short)7, (short)1, (short)2, (short)1, (short)4, (short)1, (short)1, (short)7,
            (short)1, (short)1, (short)1, (short)23, (short)4, (short)1, (short)7, (short)1, (short)1, (short)1, (short)7, (short)6, (short)1, (short)7, (short)1, (short)8,
            (short)1, (short)10, (short)7, (short)1, (short)7, (short)1, (short)1, (short)1, (short)24, (short)8, (short)1, (short)7, (short)1, (short)8, (short)1, (short)10,
            (short)10, (short)1, (short)7, (short)1, (short)1, (short)1, (short)24, (short)13, (short)1, (short)7, (short)1, (short)1, (short)1, (short)25, (short)14, (short)1,
            (short)7, (short)1, (short)2, (short)1, (short)4, (short)0, (short)1, (short)8, (short)1, (short)2, (short)1, (short)12, (short)1, (short)1, (short)8, (short)1,
            (short)1, (short)1, (short)26, (short)4, (short)1, (short)8, (short)1, (short)5, (short)1, (short)10, (short)6, (short)1, (short)8, (short)1, (short)1, (short)1,
            (short)6, (short)7, (short)1, (short)8, (short)1, (short)4, (short)4, (short)10, (short)8, (short)1, (short)8, (short)1, (short)1, (short)1, (short)6, (short)10,
            (short)1, (short)8, (short)1, (short)4, (short)4, (short)10, (short)13, (short)1, (short)8, (short)1, (short)1, (short)1, (short)27, (short)14, (short)1, (short)8,
            (short)1, (short)2, (short)1, (short)12, (short)0, (short)1, (short)9, (short)1, (short)8, (short)5, (short)10, (short)1, (short)1, (short)9, (short)2, (short)4,
            (short)1, (short)4, (short)4, (short)1, (short)9, (short)1, (short)1, (short)1, (short)28, (short)6, (short)1, (short)9, (short)1, (short)5, (short)3, (short)10,
            (short)8, (short)1, (short)9, (short)1, (short)8, (short)1, (short)10, (short)13, (short)1, (short)9, (short)1, (short)4, (short)1, (short)4, (short)14, (short)1,
            (short)9, (short)1, (short)8, (short)5, (short)10, (short)1, (short)1, (short)10, (short)1, (short)5, (short)1, (short)12, (short)2, (short)1, (short)10, (short)1,
            (short)1, (short)1, (short)16, (short)4, (short)1, (short)10, (short)1, (short)4, (short)2, (short)10, (short)8, (short)1, (short)10, (short)1, (short)1, (short)1,
            (short)29, (short)12, (short)1, (short)10, (short)1, (short)1, (short)1, (short)17, (short)13, (short)1, (short)10, (short)1, (short)5, (short)1, (short)12, (short)1,
            (short)1, (short)11, (short)1, (short)1, (short)1, (short)18, (short)2, (short)1, (short)11, (short)1, (short)1, (short)1, (short)4, (short)3, (short)1, (short)11,
            (short)1, (short)3, (short)1, (short)30, (short)8, (short)1, (short)11, (short)1, (short)3, (short)2, (short)10, (short)12, (short)1, (short)11, (short)1, (short)1,
            (short)1, (short)4, (short)13, (short)1, (short)11, (short)1, (short)1, (short)1, (short)20, (short)1, (short)1, (short)12, (short)1, (short)8, (short)2, (short)10,
            (short)2, (short)1, (short)12, (short)1, (short)1, (short)1, (short)15, (short)3, (short)1, (short)12, (short)1, (short)1, (short)1, (short)4, (short)4, (short)1,
            (short)12, (short)1, (short)1, (short)1, (short)31, (short)5, (short)1, (short)12, (short)1, (short)1, (short)1, (short)27, (short)6, (short)1, (short)12, (short)1,
            (short)1, (short)1, (short)32, (short)7, (short)1, (short)12, (short)1, (short)1, (short)1, (short)33, (short)9, (short)1, (short)12, (short)1, (short)1, (short)1,
            (short)27, (short)10, (short)1, (short)12, (short)1, (short)1, (short)1, (short)31, (short)11, (short)1, (short)12, (short)1, (short)1, (short)1, (short)4, (short)12,
            (short)1, (short)12, (short)1, (short)1, (short)1, (short)15, (short)13, (short)1, (short)12, (short)1, (short)8, (short)2, (short)10, (short)2, (short)1, (short)13,
            (short)1, (short)8, (short)1, (short)10, (short)3, (short)1, (short)13, (short)1, (short)1, (short)1, (short)34, (short)4, (short)1, (short)13, (short)1, (short)5,
            (short)1, (short)12, (short)5, (short)1, (short)13, (short)5, (short)1, (short)1, (short)4, (short)10, (short)1, (short)13, (short)1, (short)5, (short)1, (short)12,
            (short)11, (short)1, (short)13, (short)1, (short)1, (short)1, (short)34, (short)12, (short)1, (short)13, (short)1, (short)8, (short)1, (short)10, (short)3, (short)2,
            (short)1, (short)1, (short)7, (short)1, (short)10, (short)11, (short)2, (short)1, (short)1, (short)7, (short)1, (short)10, (short)2, (short)2, (short)2, (short)1,
            (short)2, (short)1, (short)13, (short)3, (short)2, (short)2, (short)1, (short)2, (short)1, (short)35, (short)4, (short)2, (short)2, (short)1, (short)1, (short)1,
            (short)7, (short)9, (short)2, (short)2, (short)1, (short)1, (short)1, (short)36, (short)10, (short)2, (short)2, (short)1, (short)2, (short)2, (short)10, (short)11,
            (short)2, (short)2, (short)1, (short)2, (short)1, (short)35, (short)12, (short)2, (short)2, (short)1, (short)2, (short)1, (short)13, (short)1, (short)2, (short)3,
            (short)1, (short)7, (short)1, (short)10, (short)2, (short)2, (short)3, (short)1, (short)2, (short)1, (short)35, (short)3, (short)2, (short)3, (short)1, (short)2,
            (short)1, (short)7, (short)4, (short)2, (short)3, (short)1, (short)3, (short)1, (short)10, (short)12, (short)2, (short)3, (short)1, (short)2, (short)1, (short)35,
            (short)13, (short)2, (short)3, (short)1, (short)7, (short)1, (short)10, (short)2, (short)2, (short)4, (short)1, (short)2, (short)1, (short)37, (short)3, (short)2,
            (short)4, (short)1, (short)3, (short)1, (short)10, (short)12, (short)2, (short)4, (short)1, (short)2, (short)1, (short)37, (short)1, (short)2, (short)6, (short)1,
            (short)1, (short)1, (short)38, (short)6, (short)2, (short)6, (short)1, (short)7, (short)1, (short)10, (short)8, (short)2, (short)6, (short)1, (short)1, (short)1,
            (short)7, (short)13, (short)2, (short)6, (short)1, (short)1, (short)1, (short)39, (short)1, (short)2, (short)7, (short)1, (short)1, (short)2, (short)10, (short)4,
            (short)2, (short)7, (short)1, (short)3, (short)1, (short)10, (short)7, (short)2, (short)7, (short)1, (short)5, (short)1, (short)10, (short)10, (short)2, (short)7,
            (short)1, (short)3, (short)1, (short)10, (short)13, (short)2, (short)7, (short)1, (short)1, (short)1, (short)6, (short)6, (short)2, (short)8, (short)1, (short)1,
            (short)1, (short)7, (short)8, (short)2, (short)8, (short)1, (short)1, (short)1, (short)7, (short)13, (short)2, (short)8, (short)1, (short)1, (short)1, (short)40,
            (short)4, (short)2, (short)9, (short)1, (short)4, (short)1, (short)10, (short)2, (short)2, (short)10, (short)1, (short)2, (short)1, (short)37, (short)8, (short)2,
            (short)10, (short)1, (short)4, (short)1, (short)10, (short)12, (short)2, (short)10, (short)1, (short)2, (short)1, (short)37, (short)1, (short)2, (short)11, (short)1,
            (short)7, (short)1, (short)10, (short)2, (short)2, (short)11, (short)1, (short)2, (short)1, (short)35, (short)12, (short)2, (short)11, (short)1, (short)2, (short)1,
            (short)35, (short)13, (short)2, (short)11, (short)1, (short)7, (short)1, (short)10, (short)2, (short)2, (short)12, (short)1, (short)2, (short)1, (short)13, (short)3,
            (short)2, (short)12, (short)1, (short)2, (short)1, (short)35, (short)4, (short)2, (short)12, (short)1, (short)1, (short)1, (short)41, (short)5, (short)2, (short)12,
            (short)1, (short)1, (short)1, (short)42, (short)6, (short)2, (short)12, (short)1, (short)1, (short)1, (short)43, (short)7, (short)2, (short)12, (short)1, (short)1,
            (short)1, (short)44, (short)9, (short)2, (short)12, (short)1, (short)1, (short)1, (short)10, (short)10, (short)2, (short)12, (short)1, (short)1, (short)1, (short)45,
            (short)11, (short)2, (short)12, (short)1, (short)2, (short)1, (short)35, (short)12, (short)2, (short)12, (short)1, (short)2, (short)1, (short)13, (short)3, (short)2,
            (short)13, (short)1, (short)7, (short)1, (short)10, (short)5, (short)2, (short)13, (short)1, (short)3, (short)1, (short)4, (short)6, (short)2, (short)13, (short)3,
            (short)2, (short)1, (short)35, (short)9, (short)2, (short)13, (short)1, (short)3, (short)1, (short)4, (short)11, (short)2, (short)13, (short)1, (short)7, (short)1,
            (short)10, (short)6, (short)3, (short)1, (short)1, (short)1, (short)1, (short)46, (short)7, (short)3, (short)1, (short)1, (short)1, (short)1, (short)31, (short)8,
            (short)3, (short)1, (short)1, (short)1, (short)1, (short)47, (short)4, (short)3, (short)2, (short)1, (short)1, (short)1, (short)10, (short)9, (short)3, (short)2,
            (short)1, (short)2, (short)1, (short)10, (short)0, (short)3, (short)6, (short)1, (short)1, (short)1, (short)46, (short)1, (short)3, (short)6, (short)1, (short)1,
            (short)1, (short)16, (short)8, (short)3, (short)6, (short)1, (short)6, (short)1, (short)10, (short)13, (short)3, (short)6, (short)1, (short)1, (short)1, (short)17,
            (short)14, (short)3, (short)6, (short)1, (short)1, (short)1, (short)47, (short)0, (short)3, (short)7, (short)1, (short)1, (short)1, (short)48, (short)1, (short)3,
            (short)7, (short)1, (short)1, (short)1, (short)31, (short)13, (short)3, (short)7, (short)1, (short)1, (short)1, (short)31, (short)14, (short)3, (short)7, (short)1,
            (short)1, (short)1, (short)48, (short)0, (short)3, (short)8, (short)1, (short)1, (short)1, (short)46, (short)1, (short)3, (short)8, (short)1, (short)1, (short)1,
            (short)16, (short)6, (short)3, (short)8, (short)1, (short)6, (short)1, (short)10, (short)8, (short)3, (short)8, (short)1, (short)1, (short)1, (short)21, (short)13,
            (short)3, (short)8, (short)1, (short)1, (short)1, (short)17, (short)14, (short)3, (short)8, (short)1, (short)1, (short)1, (short)47, (short)4, (short)3, (short)12,
            (short)1, (short)1, (short)1, (short)31, (short)5, (short)3, (short)12, (short)1, (short)1, (short)1, (short)27, (short)6, (short)3, (short)12, (short)1, (short)1,
            (short)1, (short)10, (short)7, (short)3, (short)12, (short)1, (short)1, (short)1, (short)11, (short)9, (short)3, (short)12, (short)1, (short)1, (short)1, (short)27,
            (short)10, (short)3, (short)12, (short)1, (short)1, (short)1, (short)31, (short)6, (short)4, (short)1, (short)3, (short)1, (short)1, (short)4, (short)2, (short)4,
            (short)2, (short)1, (short)1, (short)1, (short)15, (short)3, (short)4, (short)2, (short)1, (short)1, (short)1, (short)4, (short)4, (short)4, (short)2, (short)1,
            (short)1, (short)1, (short)22, (short)5, (short)4, (short)2, (short)1, (short)1, (short)4, (short)10, (short)6, (short)4, (short)2, (short)1, (short)1, (short)1,
            (short)31, (short)7, (short)4, (short)2, (short)1, (short)1, (short)1, (short)22, (short)8, (short)4, (short)2, (short)1, (short)1, (short)1, (short)31, (short)10,
            (short)4, (short)2, (short)1, (short)1, (short)1, (short)22, (short)11, (short)4, (short)2, (short)1, (short)1, (short)1, (short)4, (short)12, (short)4, (short)2,
            (short)1, (short)1, (short)1, (short)15, (short)2, (short)4, (short)3, (short)1, (short)1, (short)1, (short)4, (short)3, (short)4, (short)3, (short)1, (short)1,
            (short)1, (short)10, (short)6, (short)4, (short)3, (short)3, (short)1, (short)3, (short)10, (short)10, (short)4, (short)3, (short)1, (short)1, (short)1, (short)10,
            (short)12, (short)4, (short)3, (short)1, (short)1, (short)1, (short)4, (short)2, (short)4, (short)4, (short)1, (short)1, (short)1, (short)16, (short)12, (short)4,
            (short)4, (short)1, (short)1, (short)1, (short)17, (short)12, (short)4, (short)5, (short)1, (short)1, (short)1, (short)10, (short)0, (short)4, (short)6, (short)1,
            (short)5, (short)1, (short)10, (short)1, (short)4, (short)6, (short)1, (short)1, (short)3, (short)4, (short)2, (short)4, (short)6, (short)1, (short)1, (short)1,
            (short)31, (short)12, (short)4, (short)6, (short)1, (short)1, (short)1, (short)31, (short)13, (short)4, (short)6, (short)1, (short)1, (short)3, (short)4, (short)14,
            (short)4, (short)6, (short)1, (short)5, (short)1, (short)10, (short)0, (short)4, (short)7, (short)1, (short)1, (short)1, (short)46, (short)2, (short)4, (short)7,
            (short)1, (short)1, (short)1, (short)16, (short)12, (short)4, (short)7, (short)1, (short)1, (short)1, (short)17, (short)14, (short)4, (short)7, (short)1, (short)1,
            (short)1, (short)47, (short)0, (short)4, (short)8, (short)1, (short)5, (short)1, (short)10, (short)2, (short)4, (short)8, (short)1, (short)1, (short)1, (short)31,
            (short)8, (short)4, (short)8, (short)1, (short)5, (short)1, (short)10, (short)12, (short)4, (short)8, (short)1, (short)1, (short)1, (short)31, (short)14, (short)4,
            (short)8, (short)1, (short)5, (short)1, (short)10, (short)12, (short)4, (short)9, (short)1, (short)1, (short)1, (short)10, (short)2, (short)4, (short)10, (short)1,
            (short)1, (short)1, (short)16, (short)12, (short)4, (short)10, (short)1, (short)1, (short)1, (short)17, (short)2, (short)4, (short)11, (short)1, (short)1, (short)1,
            (short)4, (short)3, (short)4, (short)11, (short)1, (short)1, (short)1, (short)10, (short)8, (short)4, (short)11, (short)1, (short)2, (short)1, (short)10, (short)12,
            (short)4, (short)11, (short)1, (short)1, (short)1, (short)4, (short)2, (short)4, (short)12, (short)1, (short)1, (short)1, (short)15, (short)3, (short)4, (short)12,
            (short)1, (short)1, (short)1, (short)4, (short)4, (short)4, (short)12, (short)2, (short)1, (short)1, (short)32, (short)6, (short)4, (short)12, (short)1, (short)1,
            (short)1, (short)31, (short)7, (short)4, (short)12, (short)1, (short)1, (short)1, (short)27, (short)8, (short)4, (short)12, (short)1, (short)1, (short)1, (short)31,
            (short)9, (short)4, (short)12, (short)1, (short)1, (short)1, (short)49, (short)10, (short)4, (short)12, (short)1, (short)1, (short)1, (short)32, (short)11, (short)4,
            (short)12, (short)1, (short)1, (short)1, (short)4, (short)12, (short)4, (short)12, (short)1, (short)1, (short)1, (short)15, (short)6, (short)4, (short)13, (short)3,
            (short)1, (short)1, (short)4, (short)5, (short)5, (short)1, (short)5, (short)1, (short)1, (short)19, (short)2, (short)5, (short)2, (short)1, (short)4, (short)1,
            (short)10, (short)3, (short)5, (short)2, (short)1, (short)1, (short)1, (short)19, (short)4, (short)5, (short)2, (short)1, (short)1, (short)1, (short)48, (short)5,
            (short)5, (short)2, (short)5, (short)1, (short)1, (short)50, (short)10, (short)5, (short)2, (short)1, (short)1, (short)1, (short)48, (short)11, (short)5, (short)2,
            (short)1, (short)1, (short)1, (short)19, (short)12, (short)5, (short)2, (short)1, (short)4, (short)1, (short)10, (short)2, (short)5, (short)3, (short)1, (short)1,
            (short)1, (short)46, (short)3, (short)5, (short)3, (short)1, (short)2, (short)1, (short)41, (short)4, (short)5, (short)3, (short)1, (short)1, (short)1, (short)22,
            (short)5, (short)5, (short)3, (short)2, (short)1, (short)3, (short)10, (short)7, (short)5, (short)3, (short)1, (short)1, (short)1, (short)22, (short)8, (short)5,
            (short)3, (short)1, (short)1, (short)3, (short)10, (short)10, (short)5, (short)3, (short)1, (short)1, (short)1, (short)22, (short)11, (short)5, (short)3, (short)1,
            (short)2, (short)1, (short)41, (short)12, (short)5, (short)3, (short)1, (short)1, (short)1, (short)47, (short)2, (short)5, (short)4, (short)1, (short)1, (short)1,
            (short)50, (short)3, (short)5, (short)4, (short)1, (short)1, (short)1, (short)16, (short)7, (short)5, (short)4, (short)1, (short)1, (short)1, (short)31, (short)11,
            (short)5, (short)4, (short)1, (short)1, (short)1, (short)17, (short)12, (short)5, (short)4, (short)1, (short)1, (short)1, (short)50, (short)1, (short)5, (short)5,
            (short)1, (short)1, (short)5, (short)46, (short)2, (short)5, (short)5, (short)1, (short)1, (short)5, (short)48, (short)3, (short)5, (short)5, (short)1, (short)1,
            (short)2, (short)10, (short)7, (short)5, (short)5, (short)1, (short)1, (short)1, (short)10, (short)11, (short)5, (short)5, (short)1, (short)1, (short)2, (short)10,
            (short)12, (short)5, (short)5, (short)1, (short)1, (short)5, (short)48, (short)13, (short)5, (short)5, (short)1, (short)1, (short)5, (short)47, (short)0, (short)5,
            (short)7, (short)1, (short)4, (short)1, (short)10, (short)3, (short)5, (short)7, (short)1, (short)1, (short)1, (short)16, (short)4, (short)5, (short)7, (short)1,
            (short)1, (short)1, (short)31, (short)10, (short)5, (short)7, (short)1, (short)1, (short)1, (short)31, (short)11, (short)5, (short)7, (short)1, (short)1, (short)1,
            (short)17, (short)14, (short)5, (short)7, (short)1, (short)4, (short)1, (short)10, (short)3, (short)5, (short)8, (short)1, (short)1, (short)2, (short)10, (short)7,
            (short)5, (short)8, (short)1, (short)1, (short)2, (short)10, (short)10, (short)5, (short)8, (short)2, (short)1, (short)2, (short)10, (short)2, (short)5, (short)10,
            (short)1, (short)1, (short)1, (short)50, (short)3, (short)5, (short)10, (short)1, (short)1, (short)1, (short)16, (short)4, (short)5, (short)10, (short)1, (short)1,
            (short)1, (short)10, (short)7, (short)5, (short)10, (short)1, (short)1, (short)1, (short)31, (short)10, (short)5, (short)10, (short)1, (short)1, (short)1, (short)10,
            (short)11, (short)5, (short)10, (short)1, (short)1, (short)1, (short)17, (short)12, (short)5, (short)10, (short)1, (short)1, (short)1, (short)50, (short)2, (short)5,
            (short)11, (short)1, (short)1, (short)1, (short)46, (short)3, (short)5, (short)11, (short)1, (short)2, (short)1, (short)41, (short)4, (short)5, (short)11, (short)1,
            (short)1, (short)1, (short)27, (short)7, (short)5, (short)11, (short)1, (short)1, (short)1, (short)27, (short)10, (short)5, (short)11, (short)1, (short)1, (short)1,
            (short)27, (short)11, (short)5, (short)11, (short)1, (short)2, (short)1, (short)41, (short)12, (short)5, (short)11, (short)1, (short)1, (short)1, (short)47, (short)2,
            (short)5, (short)12, (short)1, (short)4, (short)1, (short)10, (short)3, (short)5, (short)12, (short)1, (short)1, (short)1, (short)51, (short)4, (short)5, (short)12,
            (short)1, (short)1, (short)1, (short)48, (short)5, (short)5, (short)12, (short)5, (short)1, (short)1, (short)50, (short)10, (short)5, (short)12, (short)1, (short)1,
            (short)1, (short)48, (short)11, (short)5, (short)12, (short)1, (short)1, (short)1, (short)51, (short)12, (short)5, (short)12, (short)1, (short)4, (short)1, (short)10,
            (short)5, (short)5, (short)13, (short)5, (short)1, (short)1, (short)51, (short)4, (short)6, (short)1, (short)1, (short)1, (short)1, (short)52, (short)5, (short)6,
            (short)1, (short)5, (short)3, (short)1, (short)10, (short)10, (short)6, (short)1, (short)1, (short)1, (short)1, (short)52, (short)3, (short)6, (short)2, (short)1,
            (short)3, (short)1, (short)10, (short)4, (short)6, (short)2, (short)1, (short)1, (short)1, (short)12, (short)5, (short)6, (short)2, (short)2, (short)1, (short)1,
            (short)19, (short)7, (short)6, (short)2, (short)1, (short)1, (short)1, (short)12, (short)8, (short)6, (short)2, (short)2, (short)1, (short)1, (short)19, (short)10,
            (short)6, (short)2, (short)1, (short)1, (short)1, (short)12, (short)11, (short)6, (short)2, (short)1, (short)3, (short)1, (short)10, (short)2, (short)6, (short)3,
            (short)1, (short)3, (short)1, (short)10, (short)4, (short)6, (short)3, (short)1, (short)1, (short)1, (short)48, (short)5, (short)6, (short)3, (short)2, (short)1,
            (short)1, (short)41, (short)7, (short)6, (short)3, (short)1, (short)1, (short)2, (short)48, (short)8, (short)6, (short)3, (short)2, (short)1, (short)1, (short)41,
            (short)10, (short)6, (short)3, (short)1, (short)1, (short)1, (short)48, (short)12, (short)6, (short)3, (short)1, (short)3, (short)1, (short)10, (short)1, (short)6,
            (short)4, (short)1, (short)1, (short)1, (short)52, (short)2, (short)6, (short)4, (short)1, (short)1, (short)1, (short)12, (short)3, (short)6, (short)4, (short)1,
            (short)1, (short)1, (short)50, (short)4, (short)6, (short)4, (short)2, (short)1, (short)1, (short)41, (short)6, (short)6, (short)4, (short)1, (short)1, (short)1,
            (short)50, (short)8, (short)6, (short)4, (short)1, (short)1, (short)1, (short)50, (short)9, (short)6, (short)4, (short)2, (short)1, (short)1, (short)41, (short)11,
            (short)6, (short)4, (short)1, (short)1, (short)1, (short)50, (short)12, (short)6, (short)4, (short)1, (short)1, (short)1, (short)12, (short)13, (short)6, (short)4,
            (short)1, (short)1, (short)1, (short)52, (short)1, (short)6, (short)5, (short)1, (short)3, (short)5, (short)10, (short)2, (short)6, (short)5, (short)1, (short)1,
            (short)2, (short)46, (short)3, (short)6, (short)5, (short)2, (short)1, (short)1, (short)41, (short)5, (short)6, (short)5, (short)1, (short)1, (short)1, (short)35,
            (short)6, (short)6, (short)5, (short)1, (short)3, (short)1, (short)10, (short)7, (short)6, (short)5, (short)1, (short)1, (short)1, (short)22, (short)8, (short)6,
            (short)5, (short)1, (short)3, (short)1, (short)10, (short)9, (short)6, (short)5, (short)1, (short)1, (short)1, (short)35, (short)10, (short)6, (short)5, (short)2,
            (short)1, (short)1, (short)41, (short)12, (short)6, (short)5, (short)1, (short)1, (short)2, (short)47, (short)13, (short)6, (short)5, (short)1, (short)3, (short)5,
            (short)10, (short)3, (short)6, (short)6, (short)1, (short)1, (short)1, (short)41, (short)4, (short)6, (short)6, (short)1, (short)1, (short)1, (short)48, (short)5,
            (short)6, (short)6, (short)1, (short)3, (short)1, (short)10, (short)9, (short)6, (short)6, (short)1, (short)3, (short)1, (short)10, (short)10, (short)6, (short)6,
            (short)1, (short)1, (short)1, (short)48, (short)11, (short)6, (short)6, (short)1, (short)1, (short)1, (short)41, (short)2, (short)6, (short)7, (short)1, (short)1,
            (short)1, (short)12, (short)3, (short)6, (short)7, (short)2, (short)1, (short)1, (short)50, (short)5, (short)6, (short)7, (short)1, (short)1, (short)1, (short)16,
            (short)9, (short)6, (short)7, (short)1, (short)1, (short)1, (short)17, (short)10, (short)6, (short)7, (short)2, (short)1, (short)1, (short)50, (short)12, (short)6,
            (short)7, (short)1, (short)1, (short)1, (short)12, (short)2, (short)6, (short)8, (short)1, (short)1, (short)2, (short)46, (short)3, (short)6, (short)8, (short)1,
            (short)1, (short)2, (short)41, (short)4, (short)6, (short)8, (short)1, (short)1, (short)1, (short)48, (short)5, (short)6, (short)8, (short)1, (short)3, (short)1,
            (short)10, (short)7, (short)6, (short)8, (short)1, (short)3, (short)1, (short)10, (short)9, (short)6, (short)8, (short)1, (short)3, (short)1, (short)10, (short)10,
            (short)6, (short)8, (short)1, (short)1, (short)1, (short)48, (short)11, (short)6, (short)8, (short)1, (short)1, (short)2, (short)41, (short)12, (short)6, (short)8,
            (short)1, (short)1, (short)2, (short)47, (short)4, (short)6, (short)9, (short)1, (short)1, (short)2, (short)41, (short)5, (short)6, (short)9, (short)1, (short)1,
            (short)1, (short)35, (short)6, (short)6, (short)9, (short)1, (short)3, (short)1, (short)10, (short)7, (short)6, (short)9, (short)1, (short)1, (short)1, (short)27,
            (short)9, (short)6, (short)9, (short)1, (short)1, (short)1, (short)35, (short)10, (short)6, (short)9, (short)1, (short)1, (short)2, (short)41, (short)1, (short)6,
            (short)10, (short)1, (short)1, (short)1, (short)52, (short)2, (short)6, (short)10, (short)1, (short)1, (short)1, (short)12, (short)3, (short)6, (short)10, (short)1,
            (short)1, (short)1, (short)50, (short)5, (short)6, (short)10, (short)1, (short)1, (short)2, (short)41, (short)6, (short)6, (short)10, (short)1, (short)1, (short)1,
            (short)50, (short)7, (short)6, (short)10, (short)1, (short)1, (short)2, (short)48, (short)8, (short)6, (short)10, (short)1, (short)1, (short)1, (short)50, (short)9,
            (short)6, (short)10, (short)1, (short)1, (short)2, (short)41, (short)11, (short)6, (short)10, (short)1, (short)1, (short)1, (short)50, (short)12, (short)6, (short)10,
            (short)1, (short)1, (short)1, (short)12, (short)13, (short)6, (short)10, (short)1, (short)1, (short)1, (short)52, (short)2, (short)6, (short)11, (short)1, (short)3,
            (short)1, (short)10, (short)4, (short)6, (short)11, (short)1, (short)1, (short)1, (short)48, (short)6, (short)6, (short)11, (short)1, (short)1, (short)1, (short)41,
            (short)8, (short)6, (short)11, (short)1, (short)1, (short)1, (short)41, (short)10, (short)6, (short)11, (short)1, (short)1, (short)1, (short)48, (short)12, (short)6,
            (short)11, (short)1, (short)3, (short)1, (short)10, (short)3, (short)6, (short)12, (short)1, (short)3, (short)1, (short)10, (short)4, (short)6, (short)12, (short)1,
            (short)1, (short)1, (short)12, (short)5, (short)6, (short)12, (short)2, (short)1, (short)1, (short)51, (short)7, (short)6, (short)12, (short)1, (short)1, (short)1,
            (short)12, (short)8, (short)6, (short)12, (short)2, (short)1, (short)1, (short)51, (short)10, (short)6, (short)12, (short)1, (short)1, (short)1, (short)12, (short)11,
            (short)6, (short)12, (short)1, (short)3, (short)1, (short)10, (short)4, (short)6, (short)13, (short)1, (short)1, (short)1, (short)52, (short)5, (short)6, (short)13,
            (short)5, (short)3, (short)1, (short)10, (short)10, (short)6, (short)13, (short)1, (short)1, (short)1, (short)52, (short)4, (short)7, (short)1, (short)1, (short)2,
            (short)1, (short)10, (short)10, (short)7, (short)1, (short)1, (short)2, (short)1, (short)10, (short)4, (short)7, (short)2, (short)1, (short)1, (short)1, (short)52,
            (short)5, (short)7, (short)2, (short)2, (short)2, (short)3, (short)10, (short)7, (short)7, (short)2, (short)1, (short)1, (short)1, (short)19, (short)8, (short)7,
            (short)2, (short)2, (short)2, (short)3, (short)10, (short)10, (short)7, (short)2, (short)1, (short)1, (short)1, (short)52, (short)3, (short)7, (short)3, (short)2,
            (short)2, (short)4, (short)10, (short)7, (short)7, (short)3, (short)1, (short)1, (short)3, (short)52, (short)10, (short)7, (short)3, (short)2, (short)2, (short)4,
            (short)10, (short)1, (short)7, (short)4, (short)1, (short)2, (short)1, (short)10, (short)2, (short)7, (short)4, (short)1, (short)1, (short)1, (short)52, (short)12,
            (short)7, (short)4, (short)1, (short)1, (short)1, (short)52, (short)13, (short)7, (short)4, (short)1, (short)2, (short)1, (short)10, (short)2, (short)7, (short)5,
            (short)1, (short)2, (short)2, (short)10, (short)5, (short)7, (short)5, (short)1, (short)2, (short)1, (short)10, (short)9, (short)7, (short)5, (short)1, (short)2,
            (short)1, (short)10, (short)12, (short)7, (short)5, (short)1, (short)2, (short)2, (short)10, (short)2, (short)7, (short)7, (short)1, (short)1, (short)1, (short)46,
            (short)3, (short)7, (short)7, (short)3, (short)1, (short)1, (short)52, (short)7, (short)7, (short)7, (short)1, (short)1, (short)1, (short)53, (short)9, (short)7,
            (short)7, (short)3, (short)1, (short)1, (short)52, (short)12, (short)7, (short)7, (short)1, (short)1, (short)1, (short)47, (short)2, (short)7, (short)8, (short)3,
            (short)2, (short)2, (short)10, (short)10, (short)7, (short)8, (short)3, (short)2, (short)2, (short)10, (short)5, (short)7, (short)9, (short)1, (short)2, (short)4,
            (short)10, (short)7, (short)7, (short)9, (short)1, (short)1, (short)3, (short)52, (short)9, (short)7, (short)9, (short)1, (short)2, (short)4, (short)10, (short)1,
            (short)7, (short)10, (short)1, (short)2, (short)1, (short)10, (short)2, (short)7, (short)10, (short)1, (short)1, (short)1, (short)52, (short)3, (short)7, (short)10,
            (short)2, (short)2, (short)2, (short)10, (short)6, (short)7, (short)10, (short)1, (short)2, (short)3, (short)10, (short)8, (short)7, (short)10, (short)1, (short)2,
            (short)3, (short)10, (short)10, (short)7, (short)10, (short)2, (short)2, (short)2, (short)10, (short)12, (short)7, (short)10, (short)1, (short)1, (short)1, (short)52,
            (short)13, (short)7, (short)10, (short)1, (short)2, (short)1, (short)10, (short)4, (short)7, (short)12, (short)1, (short)1, (short)1, (short)52, (short)7, (short)7,
            (short)12, (short)1, (short)1, (short)1, (short)51, (short)10, (short)7, (short)12, (short)1, (short)1, (short)1, (short)52, (short)4, (short)7, (short)13, (short)1,
            (short)2, (short)1, (short)10, (short)10, (short)7, (short)13, (short)1, (short)2, (short)1, (short)10, (short)4, (short)8, (short)2, (short)1, (short)1, (short)1,
            (short)10, (short)7, (short)8, (short)2, (short)1, (short)1, (short)1, (short)53, (short)10, (short)8, (short)2, (short)1, (short)1, (short)1, (short)10, (short)7,
            (short)8, (short)3, (short)1, (short)1, (short)3, (short)10, (short)2, (short)8, (short)4, (short)1, (short)1, (short)1, (short)10, (short)12, (short)8, (short)4,
            (short)1, (short)1, (short)1, (short)10, (short)2, (short)8, (short)7, (short)1, (short)1, (short)1, (short)53, (short)3, (short)8, (short)7, (short)3, (short)1,
            (short)1, (short)10, (short)7, (short)8, (short)7, (short)1, (short)1, (short)1, (short)10, (short)9, (short)8, (short)7, (short)3, (short)1, (short)1, (short)10,
            (short)12, (short)8, (short)7, (short)1, (short)1, (short)1, (short)53, (short)7, (short)8, (short)9, (short)1, (short)1, (short)3, (short)10, (short)2, (short)8,
            (short)10, (short)1, (short)1, (short)1, (short)10, (short)12, (short)8, (short)10, (short)1, (short)1, (short)1, (short)10, (short)4, (short)8, (short)12, (short)1,
            (short)1, (short)1, (short)10, (short)7, (short)8, (short)12, (short)1, (short)1, (short)1, (short)53, (short)10, (short)8, (short)12, (short)1, (short)1, (short)1,
            (short)10
    };


    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        for (int i = 0; i < BOXES.length; i += 7) {
            int dx = BOXES[i];
            int dy = BOXES[i + 1];
            int dz = BOXES[i + 2];
            int sx = BOXES[i + 3];
            int sy = BOXES[i + 4];
            int sz = BOXES[i + 5];
            int pi = BOXES[i + 6];
            Block block = PALETTE[pi];
            int meta = META[pi] & 15;
            for (int yy = 0; yy < sy; yy++)
                for (int zz = 0; zz < sz; zz++)
                    for (int xx = 0; xx < sx; xx++)
                        world.setBlock(x + dx + xx, y + dy + yy, z + dz + zz, block, meta, 2);
        }


        world.setBlockMetadataWithNotify(x + 7, y + 1, z + 12, 2, 2);

        TileEntity furnace = world.getTileEntity(x + 7, y + 1, z + 12);
        if (furnace instanceof TileEntityFurnace) {
            furnace.markDirty();
        }

        world.markBlockForUpdate(x + 7, y + 1, z + 12);


        TileEntity te = world.getTileEntity(x + 13, y + 2, z + 6);
        if (te instanceof TileEntitySkull) {
            TileEntitySkull skull = (TileEntitySkull) te;
            skull.func_145903_a(11);
            skull.markDirty();
            world.markBlockForUpdate(x + 13, y + 2, z + 6);
        }

        if (rand.nextBoolean()) {
            Thaumaturge thaumaturge = new Thaumaturge(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        } else {
            MadThaumaturge thaumaturge = new MadThaumaturge(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        }

        return true;
    }


    public static int getWidth() { return 15; }
    public static int getHeight() { return 9; }
    public static int getLength() { return 14; }

    /*
     * TILE ENTITY TEMPLATE STUBS - SEARCHABLE / MANUAL
     *
     * These are intentionally comments. Copy the useful data into real 1.7.10
     * TileEntity setup code after block placement. Names and coordinates are
     * preserved so chests, spawners, signs, mod machines, etc. can be restored
     * manually without guessing which block each tile belongs to.
     */

    // TILE_ENTITY_000
    // id: TileWardingStone
    // block: Thaumcraft:blockCosmeticSolid meta 3 numericId 182
    // pos: (6, 0, 1), world: (x + 6, y + 0, z + 1)
    // TODO TILE STUB NAME: TileWardingStone
    // TODO BLOCK STUB NAME: Thaumcraft:blockCosmeticSolid
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 0, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 6
    //     y: 0
    //     z: 1
    //     id: 'TileWardingStone'
    //   }

    // TILE_ENTITY_001
    // id: TileWardingStone
    // block: Thaumcraft:blockCosmeticSolid meta 3 numericId 182
    // pos: (7, 0, 1), world: (x + 7, y + 0, z + 1)
    // TODO TILE STUB NAME: TileWardingStone
    // TODO BLOCK STUB NAME: Thaumcraft:blockCosmeticSolid
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 0, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 0
    //     z: 1
    //     id: 'TileWardingStone'
    //   }

    // TILE_ENTITY_002
    // id: TileWardingStoneFence
    // block: Thaumcraft:blockAiry meta 4 numericId 198
    // pos: (7, 1, 1), world: (x + 7, y + 1, z + 1)
    // TODO TILE STUB NAME: TileWardingStoneFence
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 1, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 1
    //     z: 1
    //     id: 'TileWardingStoneFence'
    //   }

    // TILE_ENTITY_003
    // id: TileWardingStoneFence
    // block: Thaumcraft:blockAiry meta 4 numericId 198
    // pos: (7, 2, 1), world: (x + 7, y + 2, z + 1)
    // TODO TILE STUB NAME: TileWardingStoneFence
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 2, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 2
    //     z: 1
    //     id: 'TileWardingStoneFence'
    //   }

    // TILE_ENTITY_004
    // id: TileWardingStone
    // block: Thaumcraft:blockCosmeticSolid meta 3 numericId 182
    // pos: (8, 0, 1), world: (x + 8, y + 0, z + 1)
    // TODO TILE STUB NAME: TileWardingStone
    // TODO BLOCK STUB NAME: Thaumcraft:blockCosmeticSolid
    // TODO example: TileEntity te = world.getTileEntity(x + 8, y + 0, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 8
    //     y: 0
    //     z: 1
    //     id: 'TileWardingStone'
    //   }

    // TILE_ENTITY_005
    // id: Chest
    // block: minecraft:chest meta 5 numericId 54
    // pos: (1, 2, 6), world: (x + 1, y + 2, z + 6)
    // TODO TILE STUB NAME: Chest
    // TODO BLOCK STUB NAME: minecraft:chest
    // TODO example: TileEntity te = world.getTileEntity(x + 1, y + 2, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 1
    //     y: 2
    //     z: 6
    //   Items: list[0]
    //     id: 'Chest'
    //   }

    // TILE_ENTITY_006
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (2, 8, 7), world: (x + 2, y + 8, z + 7)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 2, y + 8, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 2
    //     y: 8
    //     z: 7
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_007
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (4, 4, 12), world: (x + 4, y + 4, z + 12)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 4, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 4
    //     y: 4
    //     facing: 2
    //     z: 12
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_008
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (5, 4, 12), world: (x + 5, y + 4, z + 12)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 5, y + 4, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 5
    //     y: 4
    //     facing: 2
    //     z: 12
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_009
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (6, 1, 12), world: (x + 6, y + 1, z + 12)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 1, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 6
    //     y: 1
    //     facing: 2
    //     z: 12
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_010
    // id: TileTube
    // block: Thaumcraft:blockTube meta 0 numericId 184
    // pos: (6, 2, 12), world: (x + 6, y + 2, z + 12)
    // TODO TILE STUB NAME: TileTube
    // TODO BLOCK STUB NAME: Thaumcraft:blockTube
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 2, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     amount: 0
    //     side: 2
    //     x: 6
    //     y: 2
    //     z: 12
    //     id: 'TileTube'
    //     samount: 0
    //     open: <byte[6]>
    //   }

    // TILE_ENTITY_011
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (7, 8, 2), world: (x + 7, y + 8, z + 2)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 8, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 8
    //     z: 2
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_012
    // id: TilePedestal
    // block: Thaumcraft:blockStoneDevice meta 1 numericId 187
    // pos: (7, 1, 7), world: (x + 7, y + 1, z + 7)
    // TODO TILE STUB NAME: TilePedestal
    // TODO BLOCK STUB NAME: Thaumcraft:blockStoneDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 1, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 1
    //     z: 7
    //   Items: list[0]
    //     id: 'TilePedestal'
    //   }

    // TILE_ENTITY_013
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (7, 7, 7), world: (x + 7, y + 7, z + 7)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 7, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 7
    //     z: 7
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_014
    // id: Furnace
    // block: minecraft:furnace meta 2 numericId 61
    // pos: (7, 1, 12), world: (x + 7, y + 1, z + 12)
    // TODO TILE STUB NAME: Furnace
    // TODO BLOCK STUB NAME: minecraft:furnace
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 1, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     CookTime: 0
    //     x: 7
    //     BurnTime: 0
    //     y: 1
    //     z: 12
    //   Items: list[0]
    //     id: 'Furnace'
    //   }

    // TILE_ENTITY_015
    // id: TileSiphon
    // block: Thaumcraft:blockMetalDevice meta 1 numericId 185
    // pos: (7, 2, 12), world: (x + 7, y + 2, z + 12)
    // TODO TILE STUB NAME: TileSiphon
    // TODO BLOCK STUB NAME: Thaumcraft:blockMetalDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 2, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     amount: 0
    //     x: 7
    //     y: 2
    //     facing: 2
    //     z: 12
    //     id: 'TileSiphon'
    //   }

    // TILE_ENTITY_016
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (7, 8, 12), world: (x + 7, y + 8, z + 12)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 8, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 8
    //     z: 12
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_017
    // id: TileCrystal
    // block: Thaumcraft:blockCrystal meta 0 numericId 183
    // pos: (9, 4, 12), world: (x + 9, y + 4, z + 12)
    // TODO TILE STUB NAME: TileCrystal
    // TODO BLOCK STUB NAME: Thaumcraft:blockCrystal
    // TODO example: TileEntity te = world.getTileEntity(x + 9, y + 4, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     orientation: 1
    //     x: 9
    //     y: 4
    //     z: 12
    //     id: 'TileCrystal'
    //   }

    // TILE_ENTITY_018
    // id: TilePedestal
    // block: Thaumcraft:blockStoneDevice meta 1 numericId 187
    // pos: (10, 1, 7), world: (x + 10, y + 1, z + 7)
    // TODO TILE STUB NAME: TilePedestal
    // TODO BLOCK STUB NAME: Thaumcraft:blockStoneDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 1, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 10
    //     y: 1
    //     z: 7
    //   Items: list[0]
    //     id: 'TilePedestal'
    //   }

    // TILE_ENTITY_019
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (10, 4, 12), world: (x + 10, y + 4, z + 12)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 4, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 10
    //     y: 4
    //     facing: 2
    //     z: 12
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_020
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (12, 8, 7), world: (x + 12, y + 8, z + 7)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 12, y + 8, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 12
    //     y: 8
    //     z: 7
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_021
    // id: Skull
    // block: minecraft:skull meta 1 numericId 144
    // pos: (13, 2, 6), world: (x + 13, y + 2, z + 6)
    // TODO TILE STUB NAME: Skull
    // TODO BLOCK STUB NAME: minecraft:skull
    // TODO example: TileEntity te = world.getTileEntity(x + 13, y + 2, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Rot: 11
    //     x: 13
    //     y: 2
    //     z: 6
    //     id: 'Skull'
    //     SkullType: 0
    //   }

    // TILE_ENTITY_022
    // id: TileArcaneWorkbench
    // block: Thaumcraft:blockTable meta 15 numericId 189
    // pos: (13, 1, 7), world: (x + 13, y + 1, z + 7)
    // TODO TILE STUB NAME: TileArcaneWorkbench
    // TODO BLOCK STUB NAME: Thaumcraft:blockTable
    // TODO example: TileEntity te = world.getTileEntity(x + 13, y + 1, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 13
    //     y: 1
    //     z: 7
    //     id: 'TileArcaneWorkbench'
    //   Inventory: list[0]
    //   }
}

