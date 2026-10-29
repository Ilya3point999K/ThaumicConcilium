package com.ilya3point999k.thaumicconcilium.common.world.houses;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.CrimsonArcher;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thaumcraft.common.config.ConfigBlocks;

import java.util.Random;

public class HouseResearcher extends WorldGenerator {

    private static Block MOD_THAUMCRAFT_BLOCKAIRY = ConfigBlocks.blockAiry; // TODO provide Thaumcraft:blockAiry
    private static Block MOD_THAUMCRAFT_BLOCKCANDLE = ConfigBlocks.blockCandle; // TODO provide Thaumcraft:blockCandle
    private static Block MOD_THAUMCRAFT_BLOCKCOSMETICSOLID = ConfigBlocks.blockCosmeticSolid; // TODO provide Thaumcraft:blockCosmeticSolid
    private static Block MOD_THAUMCRAFT_BLOCKCRYSTAL = ConfigBlocks.blockCrystal; // TODO provide Thaumcraft:blockCrystal
    private static Block MOD_THAUMCRAFT_BLOCKCUSTOMPLANT = ConfigBlocks.blockCustomPlant; // TODO provide Thaumcraft:blockCustomPlant
    private static Block MOD_THAUMCRAFT_BLOCKJAR = ConfigBlocks.blockJar; // TODO provide Thaumcraft:blockJar
    private static Block MOD_THAUMCRAFT_BLOCKMETALDEVICE = ConfigBlocks.blockMetalDevice; // TODO provide Thaumcraft:blockMetalDevice
    private static Block MOD_THAUMCRAFT_BLOCKTABLE = ConfigBlocks.blockTable; // TODO provide Thaumcraft:blockTable
    private static Block MOD_THAUMCRAFT_BLOCKWOODENDEVICE = ConfigBlocks.blockWoodenDevice; // TODO provide Thaumcraft:blockWoodenDevice


    private static final Block[] PALETTE = new Block[] {
            Blocks.grass, // palette[0] = minecraft:grass meta 0
            Blocks.gravel, // palette[1] = minecraft:gravel meta 0
            Blocks.dirt, // palette[2] = minecraft:dirt meta 0
            Blocks.cobblestone, // palette[3] = minecraft:cobblestone meta 0
            Blocks.planks, // palette[4] = minecraft:planks meta 1
            Blocks.planks, // palette[5] = minecraft:planks meta 0
            MOD_THAUMCRAFT_BLOCKAIRY, // palette[6] = Thaumcraft:blockAiry meta 1
            Blocks.water, // palette[7] = minecraft:water meta 0
            Blocks.sand, // palette[8] = minecraft:sand meta 0
            Blocks.air, // palette[9] = minecraft:air meta 0
            Blocks.cobblestone_wall, // palette[10] = minecraft:cobblestone_wall meta 0
            Blocks.double_plant, // palette[11] = minecraft:double_plant meta 5
            Blocks.double_plant, // palette[12] = minecraft:double_plant meta 4
            Blocks.log, // palette[13] = minecraft:log meta 0
            Blocks.wooden_door, // palette[14] = minecraft:wooden_door meta 3
            Blocks.red_flower, // palette[15] = minecraft:red_flower meta 5
            Blocks.wooden_slab, // palette[16] = minecraft:wooden_slab meta 9
            Blocks.red_flower, // palette[17] = minecraft:red_flower meta 6
            Blocks.spruce_stairs, // palette[18] = minecraft:spruce_stairs meta 5
            Blocks.carpet, // palette[19] = minecraft:carpet meta 0
            Blocks.carpet, // palette[20] = minecraft:carpet meta 3
            Blocks.spruce_stairs, // palette[21] = minecraft:spruce_stairs meta 2
            Blocks.furnace, // palette[22] = minecraft:furnace meta 5
            Blocks.carpet, // palette[23] = minecraft:carpet meta 4
            Blocks.red_flower, // palette[24] = minecraft:red_flower meta 4
            MOD_THAUMCRAFT_BLOCKJAR, // palette[25] = Thaumcraft:blockJar meta 0
            MOD_THAUMCRAFT_BLOCKTABLE, // palette[26] = Thaumcraft:blockTable meta 15
            MOD_THAUMCRAFT_BLOCKMETALDEVICE, // palette[27] = Thaumcraft:blockMetalDevice meta 0
            Blocks.glass_pane, // palette[28] = minecraft:glass_pane meta 0
            Blocks.reeds, // palette[29] = minecraft:reeds meta 0
            Blocks.fence, // palette[30] = minecraft:fence meta 0
            Blocks.double_plant, // palette[31] = minecraft:double_plant meta 10
            Blocks.wooden_door, // palette[32] = minecraft:wooden_door meta 8
            Blocks.hay_block, // palette[33] = minecraft:hay_block meta 4
            Blocks.flower_pot, // palette[34] = minecraft:flower_pot meta 8
            Blocks.spruce_stairs, // palette[35] = minecraft:spruce_stairs meta 7
            Blocks.wall_sign, // palette[36] = minecraft:wall_sign meta 5
            Blocks.spruce_stairs, // palette[37] = minecraft:spruce_stairs meta 6
            Blocks.spruce_stairs, // palette[38] = minecraft:spruce_stairs meta 4
            Blocks.wooden_slab, // palette[39] = minecraft:wooden_slab meta 1
            Blocks.air, // palette[40] = minecraft:air meta 1
            Blocks.log, // palette[41] = minecraft:log meta 4
            Blocks.log, // palette[42] = minecraft:log meta 8
            Blocks.spruce_stairs, // palette[43] = minecraft:spruce_stairs meta 1
            Blocks.glass, // palette[44] = minecraft:glass meta 0
            Blocks.spruce_stairs, // palette[45] = minecraft:spruce_stairs meta 0
            Blocks.spruce_stairs, // palette[46] = minecraft:spruce_stairs meta 3
            Blocks.chest, // palette[47] = minecraft:chest meta 3
            Blocks.ladder, // palette[48] = minecraft:ladder meta 5
            Blocks.stone_button, // palette[49] = minecraft:stone_button meta 4
            Blocks.lever, // palette[50] = minecraft:lever meta 2
            MOD_THAUMCRAFT_BLOCKWOODENDEVICE, // palette[51] = Thaumcraft:blockWoodenDevice meta 6
            Blocks.lever, // palette[52] = minecraft:lever meta 1
            MOD_THAUMCRAFT_BLOCKCUSTOMPLANT, // palette[53] = Thaumcraft:blockCustomPlant meta 5
            Blocks.bookshelf, // palette[54] = minecraft:bookshelf meta 0
            Blocks.heavy_weighted_pressure_plate, // palette[55] = minecraft:heavy_weighted_pressure_plate meta 0
            Blocks.redstone_torch, // palette[56] = minecraft:redstone_torch meta 5
            MOD_THAUMCRAFT_BLOCKCOSMETICSOLID, // palette[57] = Thaumcraft:blockCosmeticSolid meta 7
            Blocks.stone_pressure_plate, // palette[58] = minecraft:stone_pressure_plate meta 0
            MOD_THAUMCRAFT_BLOCKCRYSTAL, // palette[59] = Thaumcraft:blockCrystal meta 6
            Blocks.trapdoor, // palette[60] = minecraft:trapdoor meta 0
            Blocks.stone_stairs, // palette[61] = minecraft:stone_stairs meta 2
            Blocks.stone_stairs, // palette[62] = minecraft:stone_stairs meta 7
            Blocks.stone_stairs, // palette[63] = minecraft:stone_stairs meta 6
            Blocks.stone_stairs, // palette[64] = minecraft:stone_stairs meta 3
            Blocks.stone_stairs, // palette[65] = minecraft:stone_stairs meta 0
            Blocks.stone_stairs, // palette[66] = minecraft:stone_stairs meta 5
            Blocks.stone_stairs, // palette[67] = minecraft:stone_stairs meta 4
            Blocks.stone_stairs, // palette[68] = minecraft:stone_stairs meta 1
            Blocks.oak_stairs, // palette[69] = minecraft:oak_stairs meta 1
            Blocks.oak_stairs, // palette[70] = minecraft:oak_stairs meta 7
            Blocks.trapdoor, // palette[71] = minecraft:trapdoor meta 7
            Blocks.bed, // palette[72] = minecraft:bed meta 3
            Blocks.bed, // palette[73] = minecraft:bed meta 11
            Blocks.oak_stairs, // palette[74] = minecraft:oak_stairs meta 3
            Blocks.oak_stairs, // palette[75] = minecraft:oak_stairs meta 5
            Blocks.wooden_slab, // palette[76] = minecraft:wooden_slab meta 8
            Blocks.oak_stairs, // palette[77] = minecraft:oak_stairs meta 4
            Blocks.oak_stairs, // palette[78] = minecraft:oak_stairs meta 0
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[79] = Thaumcraft:blockCandle meta 0
            Blocks.stone_slab // palette[80] = minecraft:stone_slab meta 3
    };

    private static final byte[] META = new byte[] {
            (byte)0, (byte)0, (byte)0, (byte)0, (byte)1, (byte)0, (byte)1, (byte)0, (byte)0, (byte)0, (byte)0, (byte)5, (byte)4, (byte)0, (byte)3, (byte)5,
            (byte)9, (byte)6, (byte)5, (byte)0, (byte)3, (byte)2, (byte)5, (byte)4, (byte)4, (byte)0, (byte)15, (byte)0, (byte)0, (byte)0, (byte)0, (byte)10,
            (byte)8, (byte)4, (byte)8, (byte)7, (byte)5, (byte)6, (byte)4, (byte)1, (byte)1, (byte)4, (byte)8, (byte)1, (byte)0, (byte)0, (byte)3, (byte)3,
            (byte)5, (byte)4, (byte)2, (byte)6, (byte)1, (byte)5, (byte)0, (byte)0, (byte)5, (byte)7, (byte)0, (byte)6, (byte)0, (byte)2, (byte)7, (byte)6,
            (byte)3, (byte)0, (byte)5, (byte)4, (byte)1, (byte)1, (byte)7, (byte)7, (byte)3, (byte)11, (byte)3, (byte)5, (byte)8, (byte)4, (byte)0, (byte)0,
            (byte)3
    };

    // x, y, z, sizeX, sizeY, sizeZ, palette index
    private static final short[] BOXES = new short[] {
            (short)0, (short)0, (short)0, (short)6, (short)1, (short)1, (short)0, (short)6, (short)0, (short)0, (short)3, (short)1, (short)2, (short)1, (short)9, (short)0,
            (short)0, (short)6, (short)1, (short)1, (short)0, (short)0, (short)0, (short)1, (short)5, (short)1, (short)1, (short)0, (short)5, (short)0, (short)1, (short)1,
            (short)1, (short)1, (short)1, (short)9, (short)0, (short)1, (short)1, (short)1, (short)1, (short)1, (short)10, (short)0, (short)1, (short)5, (short)1, (short)1,
            (short)0, (short)0, (short)0, (short)2, (short)3, (short)1, (short)11, (short)0, (short)3, (short)0, (short)2, (short)4, (short)1, (short)1, (short)2, (short)7,
            (short)0, (short)2, (short)1, (short)1, (short)1, (short)3, (short)8, (short)0, (short)2, (short)4, (short)1, (short)1, (short)2, (short)12, (short)0, (short)2,
            (short)3, (short)1, (short)11, (short)0, (short)3, (short)0, (short)3, (short)1, (short)1, (short)6, (short)2, (short)4, (short)0, (short)3, (short)7, (short)1,
            (short)1, (short)4, (short)11, (short)0, (short)3, (short)1, (short)1, (short)4, (short)2, (short)4, (short)0, (short)4, (short)1, (short)1, (short)4, (short)4,
            (short)5, (short)0, (short)4, (short)5, (short)1, (short)3, (short)5, (short)10, (short)0, (short)4, (short)1, (short)1, (short)3, (short)4, (short)5, (short)0,
            (short)7, (short)5, (short)1, (short)1, (short)4, (short)10, (short)0, (short)7, (short)1, (short)1, (short)1, (short)6, (short)11, (short)0, (short)7, (short)1,
            (short)2, (short)1, (short)3, (short)4, (short)0, (short)8, (short)1, (short)1, (short)1, (short)2, (short)5, (short)0, (short)8, (short)6, (short)1, (short)1,
            (short)3, (short)11, (short)0, (short)8, (short)1, (short)1, (short)1, (short)2, (short)3, (short)0, (short)9, (short)1, (short)1, (short)4, (short)0, (short)4,
            (short)0, (short)9, (short)1, (short)1, (short)3, (short)3, (short)5, (short)0, (short)9, (short)1, (short)1, (short)3, (short)7, (short)6, (short)0, (short)9,
            (short)1, (short)1, (short)3, (short)8, (short)7, (short)0, (short)9, (short)1, (short)1, (short)3, (short)4, (short)8, (short)0, (short)9, (short)1, (short)1,
            (short)3, (short)8, (short)9, (short)0, (short)9, (short)1, (short)1, (short)3, (short)7, (short)10, (short)0, (short)9, (short)1, (short)1, (short)3, (short)3,
            (short)11, (short)0, (short)9, (short)1, (short)1, (short)4, (short)0, (short)4, (short)0, (short)12, (short)1, (short)1, (short)1, (short)2, (short)5, (short)0,
            (short)12, (short)5, (short)1, (short)1, (short)3, (short)10, (short)0, (short)12, (short)1, (short)1, (short)1, (short)2, (short)0, (short)1, (short)0, (short)15,
            (short)6, (short)1, (short)9, (short)0, (short)1, (short)1, (short)3, (short)4, (short)1, (short)9, (short)3, (short)1, (short)1, (short)1, (short)1, (short)1,
            (short)10, (short)4, (short)1, (short)1, (short)1, (short)1, (short)1, (short)11, (short)5, (short)1, (short)1, (short)5, (short)3, (short)1, (short)9, (short)10,
            (short)1, (short)1, (short)1, (short)1, (short)1, (short)12, (short)11, (short)1, (short)1, (short)1, (short)1, (short)1, (short)10, (short)12, (short)1, (short)1,
            (short)3, (short)4, (short)1, (short)9, (short)0, (short)1, (short)2, (short)2, (short)7, (short)11, (short)9, (short)2, (short)1, (short)2, (short)1, (short)1,
            (short)1, (short)10, (short)3, (short)1, (short)2, (short)1, (short)3, (short)1, (short)13, (short)4, (short)1, (short)2, (short)2, (short)1, (short)1, (short)3,
            (short)6, (short)1, (short)2, (short)1, (short)3, (short)1, (short)13, (short)7, (short)1, (short)2, (short)1, (short)1, (short)1, (short)14, (short)8, (short)1,
            (short)2, (short)1, (short)3, (short)1, (short)13, (short)9, (short)1, (short)2, (short)2, (short)1, (short)1, (short)3, (short)11, (short)1, (short)2, (short)1,
            (short)3, (short)1, (short)13, (short)12, (short)1, (short)2, (short)1, (short)1, (short)1, (short)10, (short)13, (short)1, (short)2, (short)2, (short)7, (short)11,
            (short)9, (short)2, (short)1, (short)3, (short)1, (short)1, (short)1, (short)15, (short)3, (short)1, (short)3, (short)1, (short)1, (short)5, (short)3, (short)4,
            (short)1, (short)3, (short)1, (short)1, (short)1, (short)16, (short)5, (short)1, (short)3, (short)6, (short)3, (short)1, (short)9, (short)11, (short)1, (short)3,
            (short)1, (short)1, (short)4, (short)3, (short)12, (short)1, (short)3, (short)1, (short)1, (short)1, (short)17, (short)2, (short)1, (short)4, (short)1, (short)1,
            (short)2, (short)17, (short)4, (short)1, (short)4, (short)1, (short)1, (short)1, (short)18, (short)5, (short)1, (short)4, (short)1, (short)3, (short)4, (short)9,
            (short)6, (short)1, (short)4, (short)1, (short)1, (short)1, (short)19, (short)7, (short)1, (short)4, (short)1, (short)1, (short)1, (short)20, (short)8, (short)1,
            (short)4, (short)1, (short)1, (short)1, (short)19, (short)9, (short)1, (short)4, (short)1, (short)2, (short)4, (short)9, (short)10, (short)1, (short)4, (short)1,
            (short)1, (short)1, (short)21, (short)12, (short)1, (short)4, (short)1, (short)1, (short)1, (short)15, (short)4, (short)1, (short)5, (short)1, (short)1, (short)1,
            (short)22, (short)6, (short)1, (short)5, (short)1, (short)1, (short)1, (short)20, (short)7, (short)1, (short)5, (short)1, (short)1, (short)1, (short)23, (short)8,
            (short)1, (short)5, (short)1, (short)1, (short)1, (short)20, (short)10, (short)1, (short)5, (short)1, (short)1, (short)1, (short)4, (short)12, (short)1, (short)5,
            (short)1, (short)1, (short)2, (short)24, (short)2, (short)1, (short)6, (short)1, (short)1, (short)1, (short)15, (short)4, (short)1, (short)6, (short)1, (short)1,
            (short)1, (short)18, (short)6, (short)1, (short)6, (short)1, (short)1, (short)1, (short)19, (short)7, (short)1, (short)6, (short)1, (short)1, (short)1, (short)20,
            (short)8, (short)1, (short)6, (short)1, (short)1, (short)1, (short)19, (short)10, (short)1, (short)6, (short)1, (short)1, (short)1, (short)25, (short)2, (short)1,
            (short)7, (short)1, (short)1, (short)1, (short)24, (short)4, (short)1, (short)7, (short)1, (short)1, (short)1, (short)26, (short)6, (short)1, (short)7, (short)3,
            (short)3, (short)1, (short)9, (short)10, (short)1, (short)7, (short)1, (short)1, (short)1, (short)27, (short)12, (short)1, (short)7, (short)1, (short)1, (short)1,
            (short)15, (short)2, (short)1, (short)8, (short)1, (short)1, (short)1, (short)10, (short)3, (short)1, (short)8, (short)1, (short)3, (short)1, (short)13, (short)4,
            (short)1, (short)8, (short)2, (short)1, (short)1, (short)3, (short)6, (short)1, (short)8, (short)1, (short)3, (short)1, (short)13, (short)7, (short)1, (short)8,
            (short)1, (short)2, (short)4, (short)9, (short)8, (short)1, (short)8, (short)1, (short)3, (short)1, (short)13, (short)9, (short)1, (short)8, (short)2, (short)1,
            (short)1, (short)3, (short)11, (short)1, (short)8, (short)1, (short)3, (short)1, (short)13, (short)12, (short)1, (short)8, (short)1, (short)1, (short)1, (short)10,
            (short)2, (short)1, (short)9, (short)1, (short)4, (short)4, (short)9, (short)3, (short)1, (short)9, (short)1, (short)1, (short)1, (short)10, (short)4, (short)1,
            (short)9, (short)1, (short)3, (short)3, (short)28, (short)5, (short)1, (short)9, (short)1, (short)3, (short)3, (short)9, (short)6, (short)1, (short)9, (short)1,
            (short)3, (short)3, (short)29, (short)8, (short)1, (short)9, (short)1, (short)3, (short)3, (short)29, (short)9, (short)1, (short)9, (short)1, (short)3, (short)3,
            (short)9, (short)10, (short)1, (short)9, (short)1, (short)3, (short)3, (short)28, (short)11, (short)1, (short)9, (short)1, (short)1, (short)1, (short)10, (short)12,
            (short)1, (short)9, (short)1, (short)4, (short)4, (short)9, (short)3, (short)1, (short)10, (short)1, (short)7, (short)3, (short)9, (short)11, (short)1, (short)10,
            (short)1, (short)7, (short)3, (short)9, (short)4, (short)1, (short)12, (short)1, (short)4, (short)1, (short)13, (short)5, (short)1, (short)12, (short)5, (short)3,
            (short)1, (short)28, (short)10, (short)1, (short)12, (short)1, (short)4, (short)1, (short)13, (short)3, (short)2, (short)1, (short)1, (short)2, (short)1, (short)30,
            (short)4, (short)2, (short)1, (short)1, (short)1, (short)1, (short)31, (short)10, (short)2, (short)1, (short)1, (short)1, (short)1, (short)31, (short)11, (short)2,
            (short)1, (short)1, (short)2, (short)1, (short)30, (short)2, (short)2, (short)2, (short)1, (short)2, (short)1, (short)30, (short)4, (short)2, (short)2, (short)2,
            (short)2, (short)1, (short)4, (short)7, (short)2, (short)2, (short)1, (short)1, (short)1, (short)32, (short)9, (short)2, (short)2, (short)2, (short)2, (short)1,
            (short)4, (short)12, (short)2, (short)2, (short)1, (short)2, (short)1, (short)30, (short)2, (short)2, (short)3, (short)1, (short)2, (short)5, (short)9, (short)3,
            (short)2, (short)3, (short)1, (short)2, (short)1, (short)4, (short)4, (short)2, (short)3, (short)1, (short)1, (short)1, (short)33, (short)11, (short)2, (short)3,
            (short)1, (short)2, (short)1, (short)4, (short)12, (short)2, (short)3, (short)1, (short)2, (short)5, (short)9, (short)3, (short)2, (short)4, (short)1, (short)2,
            (short)1, (short)28, (short)4, (short)2, (short)4, (short)1, (short)1, (short)1, (short)34, (short)6, (short)2, (short)4, (short)3, (short)2, (short)3, (short)9,
            (short)10, (short)2, (short)4, (short)1, (short)6, (short)1, (short)9, (short)11, (short)2, (short)4, (short)1, (short)2, (short)1, (short)28, (short)3, (short)2,
            (short)5, (short)1, (short)2, (short)1, (short)4, (short)4, (short)2, (short)5, (short)1, (short)1, (short)2, (short)9, (short)10, (short)2, (short)5, (short)1,
            (short)1, (short)1, (short)21, (short)11, (short)2, (short)5, (short)1, (short)2, (short)1, (short)4, (short)3, (short)2, (short)6, (short)1, (short)2, (short)1,
            (short)28, (short)10, (short)2, (short)6, (short)1, (short)1, (short)1, (short)35, (short)11, (short)2, (short)6, (short)1, (short)2, (short)1, (short)28, (short)3,
            (short)2, (short)7, (short)1, (short)2, (short)1, (short)4, (short)4, (short)2, (short)7, (short)1, (short)2, (short)1, (short)36, (short)10, (short)2, (short)7,
            (short)1, (short)1, (short)1, (short)9, (short)11, (short)2, (short)7, (short)1, (short)2, (short)1, (short)4, (short)2, (short)2, (short)8, (short)1, (short)2,
            (short)1, (short)30, (short)4, (short)2, (short)8, (short)2, (short)2, (short)1, (short)4, (short)9, (short)2, (short)8, (short)2, (short)2, (short)1, (short)4,
            (short)12, (short)2, (short)8, (short)1, (short)2, (short)1, (short)30, (short)3, (short)2, (short)9, (short)1, (short)2, (short)1, (short)30, (short)11, (short)2,
            (short)9, (short)1, (short)2, (short)1, (short)30, (short)4, (short)3, (short)1, (short)1, (short)2, (short)1, (short)9, (short)10, (short)3, (short)1, (short)1,
            (short)2, (short)1, (short)9, (short)7, (short)3, (short)2, (short)1, (short)1, (short)1, (short)37, (short)4, (short)3, (short)3, (short)1, (short)1, (short)2,
            (short)9, (short)9, (short)3, (short)4, (short)1, (short)1, (short)3, (short)9, (short)4, (short)3, (short)5, (short)1, (short)1, (short)1, (short)6, (short)10,
            (short)3, (short)5, (short)1, (short)5, (short)1, (short)9, (short)4, (short)3, (short)6, (short)1, (short)1, (short)1, (short)9, (short)10, (short)3, (short)6,
            (short)1, (short)1, (short)1, (short)21, (short)9, (short)3, (short)7, (short)1, (short)1, (short)1, (short)38, (short)10, (short)3, (short)7, (short)1, (short)1,
            (short)1, (short)35, (short)7, (short)3, (short)8, (short)1, (short)1, (short)1, (short)39, (short)7, (short)3, (short)9, (short)1, (short)1, (short)1, (short)40,
            (short)7, (short)3, (short)10, (short)1, (short)1, (short)2, (short)9, (short)3, (short)4, (short)1, (short)1, (short)1, (short)1, (short)10, (short)5, (short)4,
            (short)1, (short)1, (short)1, (short)1, (short)9, (short)6, (short)4, (short)1, (short)1, (short)1, (short)1, (short)37, (short)7, (short)4, (short)1, (short)1,
            (short)1, (short)1, (short)9, (short)8, (short)4, (short)1, (short)1, (short)1, (short)1, (short)37, (short)9, (short)4, (short)1, (short)1, (short)1, (short)1,
            (short)9, (short)11, (short)4, (short)1, (short)1, (short)1, (short)1, (short)10, (short)2, (short)4, (short)2, (short)1, (short)1, (short)1, (short)10, (short)3,
            (short)4, (short)2, (short)1, (short)1, (short)1, (short)4, (short)4, (short)4, (short)2, (short)7, (short)1, (short)1, (short)41, (short)11, (short)4, (short)2,
            (short)1, (short)1, (short)1, (short)4, (short)12, (short)4, (short)2, (short)1, (short)1, (short)1, (short)10, (short)2, (short)4, (short)3, (short)1, (short)1,
            (short)2, (short)9, (short)3, (short)4, (short)3, (short)1, (short)1, (short)5, (short)42, (short)4, (short)4, (short)3, (short)1, (short)1, (short)1, (short)18,
            (short)5, (short)4, (short)3, (short)2, (short)1, (short)5, (short)16, (short)7, (short)4, (short)3, (short)1, (short)1, (short)5, (short)42, (short)8, (short)4,
            (short)3, (short)2, (short)1, (short)4, (short)16, (short)10, (short)4, (short)3, (short)1, (short)1, (short)1, (short)38, (short)11, (short)4, (short)3, (short)1,
            (short)1, (short)5, (short)42, (short)12, (short)4, (short)3, (short)1, (short)1, (short)2, (short)9, (short)4, (short)4, (short)4, (short)1, (short)1, (short)1,
            (short)16, (short)2, (short)4, (short)5, (short)1, (short)1, (short)1, (short)38, (short)4, (short)4, (short)5, (short)1, (short)1, (short)1, (short)18, (short)12,
            (short)4, (short)5, (short)1, (short)1, (short)1, (short)18, (short)2, (short)4, (short)6, (short)1, (short)1, (short)2, (short)9, (short)4, (short)4, (short)6,
            (short)1, (short)1, (short)1, (short)16, (short)10, (short)4, (short)6, (short)1, (short)4, (short)2, (short)9, (short)12, (short)4, (short)6, (short)1, (short)1,
            (short)2, (short)9, (short)4, (short)4, (short)7, (short)1, (short)1, (short)1, (short)18, (short)8, (short)4, (short)7, (short)1, (short)1, (short)1, (short)38,
            (short)9, (short)4, (short)7, (short)1, (short)1, (short)1, (short)43, (short)2, (short)4, (short)8, (short)1, (short)1, (short)1, (short)10, (short)3, (short)4,
            (short)8, (short)1, (short)2, (short)1, (short)4, (short)4, (short)4, (short)8, (short)7, (short)1, (short)1, (short)41, (short)11, (short)4, (short)8, (short)1,
            (short)1, (short)1, (short)4, (short)12, (short)4, (short)8, (short)1, (short)1, (short)1, (short)10, (short)3, (short)4, (short)9, (short)1, (short)1, (short)1,
            (short)10, (short)4, (short)4, (short)9, (short)1, (short)1, (short)1, (short)4, (short)5, (short)4, (short)9, (short)5, (short)1, (short)3, (short)44, (short)10,
            (short)4, (short)9, (short)1, (short)1, (short)1, (short)4, (short)11, (short)4, (short)9, (short)1, (short)1, (short)1, (short)10, (short)4, (short)4, (short)10,
            (short)1, (short)1, (short)2, (short)45, (short)10, (short)4, (short)10, (short)1, (short)1, (short)2, (short)43, (short)5, (short)4, (short)12, (short)5, (short)1,
            (short)1, (short)46, (short)0, (short)5, (short)1, (short)2, (short)3, (short)1, (short)9, (short)2, (short)5, (short)1, (short)1, (short)1, (short)1, (short)16,
            (short)3, (short)5, (short)1, (short)1, (short)1, (short)1, (short)13, (short)4, (short)5, (short)1, (short)2, (short)1, (short)1, (short)37, (short)6, (short)5,
            (short)1, (short)1, (short)3, (short)1, (short)13, (short)7, (short)5, (short)1, (short)1, (short)1, (short)1, (short)37, (short)8, (short)5, (short)1, (short)1,
            (short)3, (short)1, (short)13, (short)9, (short)5, (short)1, (short)2, (short)1, (short)1, (short)37, (short)11, (short)5, (short)1, (short)1, (short)1, (short)1,
            (short)13, (short)12, (short)5, (short)1, (short)1, (short)1, (short)1, (short)16, (short)13, (short)5, (short)1, (short)2, (short)3, (short)1, (short)9, (short)2,
            (short)5, (short)2, (short)1, (short)1, (short)1, (short)13, (short)3, (short)5, (short)2, (short)2, (short)2, (short)2, (short)9, (short)5, (short)5, (short)2,
            (short)1, (short)1, (short)1, (short)16, (short)6, (short)5, (short)2, (short)1, (short)1, (short)1, (short)4, (short)7, (short)5, (short)2, (short)1, (short)1,
            (short)1, (short)0, (short)8, (short)5, (short)2, (short)1, (short)1, (short)1, (short)4, (short)9, (short)5, (short)2, (short)1, (short)1, (short)1, (short)16,
            (short)10, (short)5, (short)2, (short)1, (short)1, (short)1, (short)47, (short)11, (short)5, (short)2, (short)1, (short)1, (short)1, (short)38, (short)12, (short)5,
            (short)2, (short)1, (short)1, (short)1, (short)13, (short)2, (short)5, (short)3, (short)1, (short)1, (short)2, (short)38, (short)5, (short)5, (short)3, (short)1,
            (short)3, (short)5, (short)9, (short)6, (short)5, (short)3, (short)3, (short)2, (short)1, (short)28, (short)9, (short)5, (short)3, (short)2, (short)3, (short)1,
            (short)9, (short)11, (short)5, (short)3, (short)1, (short)1, (short)1, (short)16, (short)12, (short)5, (short)3, (short)1, (short)1, (short)2, (short)18, (short)3,
            (short)5, (short)4, (short)1, (short)4, (short)1, (short)48, (short)4, (short)5, (short)4, (short)1, (short)3, (short)4, (short)9, (short)6, (short)5, (short)4,
            (short)4, (short)3, (short)3, (short)9, (short)11, (short)5, (short)4, (short)1, (short)1, (short)1, (short)38, (short)2, (short)5, (short)5, (short)1, (short)3,
            (short)1, (short)13, (short)3, (short)5, (short)5, (short)1, (short)3, (short)1, (short)9, (short)11, (short)5, (short)5, (short)1, (short)1, (short)1, (short)4,
            (short)12, (short)5, (short)5, (short)1, (short)3, (short)1, (short)13, (short)2, (short)5, (short)6, (short)1, (short)1, (short)2, (short)38, (short)3, (short)5,
            (short)6, (short)1, (short)1, (short)1, (short)18, (short)11, (short)5, (short)6, (short)1, (short)1, (short)1, (short)38, (short)12, (short)5, (short)6, (short)1,
            (short)1, (short)2, (short)18, (short)3, (short)5, (short)7, (short)1, (short)1, (short)1, (short)16, (short)6, (short)5, (short)7, (short)1, (short)3, (short)1,
            (short)9, (short)7, (short)5, (short)7, (short)1, (short)1, (short)1, (short)49, (short)8, (short)5, (short)7, (short)2, (short)3, (short)1, (short)9, (short)11,
            (short)5, (short)7, (short)1, (short)1, (short)1, (short)16, (short)2, (short)5, (short)8, (short)1, (short)1, (short)1, (short)13, (short)4, (short)5, (short)8,
            (short)1, (short)1, (short)1, (short)16, (short)5, (short)5, (short)8, (short)1, (short)1, (short)1, (short)37, (short)6, (short)5, (short)8, (short)1, (short)1,
            (short)1, (short)50, (short)7, (short)5, (short)8, (short)1, (short)1, (short)1, (short)51, (short)8, (short)5, (short)8, (short)1, (short)1, (short)1, (short)52,
            (short)9, (short)5, (short)8, (short)2, (short)3, (short)1, (short)9, (short)11, (short)5, (short)8, (short)1, (short)1, (short)1, (short)38, (short)12, (short)5,
            (short)8, (short)1, (short)1, (short)1, (short)13, (short)2, (short)5, (short)9, (short)1, (short)1, (short)1, (short)16, (short)3, (short)5, (short)9, (short)2,
            (short)1, (short)1, (short)13, (short)5, (short)5, (short)9, (short)5, (short)1, (short)1, (short)35, (short)10, (short)5, (short)9, (short)2, (short)1, (short)1,
            (short)13, (short)12, (short)5, (short)9, (short)1, (short)1, (short)1, (short)16, (short)2, (short)5, (short)10, (short)1, (short)2, (short)3, (short)9, (short)4,
            (short)5, (short)10, (short)7, (short)1, (short)3, (short)9, (short)12, (short)5, (short)10, (short)1, (short)2, (short)3, (short)9, (short)2, (short)6, (short)1,
            (short)1, (short)3, (short)1, (short)13, (short)3, (short)6, (short)1, (short)1, (short)2, (short)1, (short)5, (short)4, (short)6, (short)1, (short)1, (short)2,
            (short)1, (short)28, (short)5, (short)6, (short)1, (short)1, (short)2, (short)1, (short)5, (short)7, (short)6, (short)1, (short)1, (short)2, (short)1, (short)5,
            (short)9, (short)6, (short)1, (short)1, (short)2, (short)1, (short)5, (short)10, (short)6, (short)1, (short)1, (short)2, (short)1, (short)28, (short)11, (short)6,
            (short)1, (short)1, (short)2, (short)1, (short)5, (short)12, (short)6, (short)1, (short)1, (short)3, (short)1, (short)13, (short)2, (short)6, (short)2, (short)1,
            (short)2, (short)1, (short)5, (short)5, (short)6, (short)2, (short)1, (short)2, (short)1, (short)9, (short)6, (short)6, (short)2, (short)1, (short)1, (short)1,
            (short)28, (short)7, (short)6, (short)2, (short)1, (short)1, (short)1, (short)53, (short)8, (short)6, (short)2, (short)1, (short)1, (short)1, (short)28, (short)9,
            (short)6, (short)2, (short)2, (short)1, (short)1, (short)9, (short)11, (short)6, (short)2, (short)1, (short)2, (short)1, (short)54, (short)12, (short)6, (short)2,
            (short)1, (short)2, (short)1, (short)5, (short)2, (short)6, (short)3, (short)1, (short)2, (short)1, (short)28, (short)11, (short)6, (short)3, (short)1, (short)2,
            (short)1, (short)9, (short)12, (short)6, (short)3, (short)1, (short)2, (short)1, (short)28, (short)2, (short)6, (short)4, (short)1, (short)2, (short)1, (short)5,
            (short)11, (short)6, (short)4, (short)1, (short)2, (short)3, (short)54, (short)12, (short)6, (short)4, (short)1, (short)2, (short)1, (short)5, (short)2, (short)6,
            (short)6, (short)1, (short)2, (short)1, (short)5, (short)3, (short)6, (short)6, (short)1, (short)1, (short)1, (short)55, (short)12, (short)6, (short)6, (short)1,
            (short)2, (short)1, (short)5, (short)2, (short)6, (short)7, (short)1, (short)2, (short)1, (short)28, (short)3, (short)6, (short)7, (short)1, (short)1, (short)1,
            (short)56, (short)7, (short)6, (short)7, (short)1, (short)2, (short)1, (short)9, (short)11, (short)6, (short)7, (short)1, (short)2, (short)1, (short)9, (short)12,
            (short)6, (short)7, (short)1, (short)2, (short)1, (short)28, (short)2, (short)6, (short)8, (short)1, (short)2, (short)1, (short)5, (short)3, (short)6, (short)8,
            (short)1, (short)1, (short)1, (short)57, (short)4, (short)6, (short)8, (short)1, (short)1, (short)1, (short)56, (short)5, (short)6, (short)8, (short)1, (short)1,
            (short)1, (short)58, (short)6, (short)6, (short)8, (short)1, (short)2, (short)1, (short)9, (short)7, (short)6, (short)8, (short)1, (short)1, (short)1, (short)59,
            (short)8, (short)6, (short)8, (short)1, (short)2, (short)1, (short)9, (short)11, (short)6, (short)8, (short)1, (short)2, (short)1, (short)54, (short)12, (short)6,
            (short)8, (short)1, (short)2, (short)1, (short)5, (short)2, (short)6, (short)9, (short)1, (short)3, (short)1, (short)13, (short)3, (short)6, (short)9, (short)1,
            (short)2, (short)1, (short)5, (short)4, (short)6, (short)9, (short)1, (short)2, (short)1, (short)13, (short)5, (short)6, (short)9, (short)1, (short)2, (short)1,
            (short)5, (short)6, (short)6, (short)9, (short)1, (short)2, (short)1, (short)28, (short)7, (short)6, (short)9, (short)1, (short)2, (short)1, (short)5, (short)8,
            (short)6, (short)9, (short)1, (short)2, (short)1, (short)28, (short)9, (short)6, (short)9, (short)1, (short)2, (short)1, (short)5, (short)10, (short)6, (short)9,
            (short)1, (short)2, (short)1, (short)13, (short)11, (short)6, (short)9, (short)1, (short)2, (short)1, (short)5, (short)12, (short)6, (short)9, (short)1, (short)3,
            (short)1, (short)13, (short)4, (short)6, (short)10, (short)7, (short)1, (short)2, (short)9, (short)4, (short)6, (short)12, (short)1, (short)1, (short)1, (short)40,
            (short)5, (short)6, (short)12, (short)5, (short)9, (short)1, (short)9, (short)10, (short)6, (short)12, (short)1, (short)1, (short)1, (short)40, (short)0, (short)7,
            (short)0, (short)2, (short)1, (short)1, (short)9, (short)2, (short)7, (short)0, (short)1, (short)1, (short)1, (short)37, (short)3, (short)7, (short)0, (short)3,
            (short)1, (short)1, (short)9, (short)6, (short)7, (short)0, (short)1, (short)1, (short)1, (short)37, (short)7, (short)7, (short)0, (short)1, (short)1, (short)1,
            (short)9, (short)8, (short)7, (short)0, (short)1, (short)1, (short)1, (short)37, (short)9, (short)7, (short)0, (short)3, (short)1, (short)1, (short)9, (short)12,
            (short)7, (short)0, (short)1, (short)1, (short)1, (short)37, (short)13, (short)7, (short)0, (short)2, (short)1, (short)1, (short)9, (short)3, (short)7, (short)2,
            (short)1, (short)1, (short)1, (short)6, (short)4, (short)7, (short)2, (short)1, (short)1, (short)2, (short)9, (short)6, (short)7, (short)2, (short)1, (short)1,
            (short)1, (short)46, (short)7, (short)7, (short)2, (short)1, (short)1, (short)1, (short)4, (short)8, (short)7, (short)2, (short)1, (short)1, (short)1, (short)46,
            (short)9, (short)7, (short)2, (short)1, (short)1, (short)1, (short)9, (short)10, (short)7, (short)2, (short)1, (short)1, (short)1, (short)6, (short)3, (short)7,
            (short)3, (short)1, (short)1, (short)1, (short)9, (short)6, (short)7, (short)3, (short)1, (short)1, (short)1, (short)39, (short)7, (short)7, (short)3, (short)1,
            (short)1, (short)1, (short)46, (short)8, (short)7, (short)3, (short)1, (short)1, (short)1, (short)39, (short)3, (short)7, (short)6, (short)1, (short)1, (short)2,
            (short)9, (short)3, (short)7, (short)8, (short)1, (short)1, (short)1, (short)60, (short)4, (short)7, (short)8, (short)2, (short)1, (short)1, (short)9, (short)7,
            (short)7, (short)8, (short)1, (short)1, (short)1, (short)6, (short)2, (short)7, (short)10, (short)1, (short)1, (short)1, (short)35, (short)4, (short)7, (short)10,
            (short)1, (short)1, (short)1, (short)35, (short)5, (short)7, (short)10, (short)5, (short)1, (short)2, (short)9, (short)10, (short)7, (short)10, (short)1, (short)1,
            (short)1, (short)35, (short)12, (short)7, (short)10, (short)1, (short)1, (short)1, (short)35, (short)2, (short)7, (short)11, (short)1, (short)8, (short)2, (short)9,
            (short)4, (short)7, (short)11, (short)1, (short)8, (short)2, (short)9, (short)10, (short)7, (short)11, (short)1, (short)8, (short)2, (short)9, (short)12, (short)7,
            (short)11, (short)1, (short)8, (short)2, (short)9, (short)0, (short)8, (short)0, (short)1, (short)5, (short)13, (short)9, (short)1, (short)8, (short)0, (short)13,
            (short)1, (short)1, (short)61, (short)14, (short)8, (short)0, (short)1, (short)5, (short)13, (short)9, (short)1, (short)8, (short)1, (short)1, (short)1, (short)1,
            (short)62, (short)3, (short)8, (short)1, (short)9, (short)1, (short)1, (short)41, (short)13, (short)8, (short)1, (short)1, (short)1, (short)1, (short)62, (short)1,
            (short)8, (short)2, (short)1, (short)1, (short)7, (short)9, (short)2, (short)8, (short)2, (short)1, (short)1, (short)7, (short)42, (short)3, (short)8, (short)2,
            (short)1, (short)1, (short)2, (short)16, (short)4, (short)8, (short)2, (short)1, (short)1, (short)3, (short)42, (short)5, (short)8, (short)2, (short)5, (short)1,
            (short)3, (short)16, (short)10, (short)8, (short)2, (short)1, (short)1, (short)3, (short)42, (short)11, (short)8, (short)2, (short)1, (short)1, (short)1, (short)4,
            (short)12, (short)8, (short)2, (short)1, (short)1, (short)7, (short)42, (short)13, (short)8, (short)2, (short)1, (short)1, (short)7, (short)9, (short)11, (short)8,
            (short)3, (short)1, (short)1, (short)1, (short)16, (short)11, (short)8, (short)4, (short)1, (short)1, (short)1, (short)4, (short)3, (short)8, (short)5, (short)9,
            (short)1, (short)1, (short)41, (short)3, (short)8, (short)6, (short)1, (short)1, (short)3, (short)16, (short)4, (short)8, (short)6, (short)1, (short)1, (short)3,
            (short)42, (short)5, (short)8, (short)6, (short)5, (short)1, (short)3, (short)16, (short)10, (short)8, (short)6, (short)1, (short)1, (short)3, (short)42, (short)11,
            (short)8, (short)6, (short)1, (short)1, (short)1, (short)4, (short)11, (short)8, (short)7, (short)1, (short)1, (short)1, (short)16, (short)11, (short)8, (short)8,
            (short)1, (short)1, (short)1, (short)4, (short)1, (short)8, (short)9, (short)1, (short)1, (short)1, (short)63, (short)3, (short)8, (short)9, (short)9, (short)1,
            (short)1, (short)41, (short)13, (short)8, (short)9, (short)1, (short)1, (short)1, (short)63, (short)1, (short)8, (short)10, (short)13, (short)1, (short)1, (short)64,
            (short)1, (short)8, (short)11, (short)1, (short)7, (short)2, (short)9, (short)3, (short)8, (short)11, (short)1, (short)7, (short)2, (short)9, (short)5, (short)8,
            (short)11, (short)5, (short)7, (short)1, (short)9, (short)11, (short)8, (short)11, (short)1, (short)7, (short)2, (short)9, (short)13, (short)8, (short)11, (short)1,
            (short)7, (short)2, (short)9, (short)1, (short)9, (short)0, (short)13, (short)3, (short)1, (short)9, (short)1, (short)9, (short)1, (short)1, (short)1, (short)1,
            (short)61, (short)2, (short)9, (short)1, (short)2, (short)1, (short)1, (short)21, (short)4, (short)9, (short)1, (short)1, (short)1, (short)1, (short)65, (short)5,
            (short)9, (short)1, (short)1, (short)1, (short)1, (short)66, (short)6, (short)9, (short)1, (short)3, (short)1, (short)1, (short)9, (short)9, (short)9, (short)1,
            (short)1, (short)1, (short)1, (short)67, (short)10, (short)9, (short)1, (short)1, (short)1, (short)1, (short)68, (short)11, (short)9, (short)1, (short)2, (short)1,
            (short)1, (short)21, (short)13, (short)9, (short)1, (short)1, (short)1, (short)1, (short)61, (short)1, (short)9, (short)2, (short)1, (short)1, (short)1, (short)62,
            (short)2, (short)9, (short)2, (short)4, (short)1, (short)1, (short)5, (short)6, (short)9, (short)2, (short)1, (short)2, (short)1, (short)13, (short)7, (short)9,
            (short)2, (short)1, (short)2, (short)1, (short)28, (short)8, (short)9, (short)2, (short)1, (short)2, (short)1, (short)13, (short)9, (short)9, (short)2, (short)4,
            (short)1, (short)1, (short)5, (short)13, (short)9, (short)2, (short)1, (short)1, (short)1, (short)62, (short)1, (short)9, (short)3, (short)1, (short)1, (short)5,
            (short)9, (short)2, (short)9, (short)3, (short)1, (short)2, (short)1, (short)5, (short)3, (short)9, (short)3, (short)2, (short)2, (short)1, (short)54, (short)5,
            (short)9, (short)3, (short)1, (short)1, (short)1, (short)69, (short)6, (short)9, (short)3, (short)4, (short)2, (short)4, (short)9, (short)10, (short)9, (short)3,
            (short)1, (short)1, (short)1, (short)70, (short)11, (short)9, (short)3, (short)1, (short)1, (short)1, (short)47, (short)12, (short)9, (short)3, (short)1, (short)2,
            (short)1, (short)5, (short)13, (short)9, (short)3, (short)1, (short)1, (short)5, (short)9, (short)2, (short)9, (short)4, (short)1, (short)3, (short)1, (short)13,
            (short)3, (short)9, (short)4, (short)1, (short)1, (short)1, (short)71, (short)4, (short)9, (short)4, (short)2, (short)3, (short)2, (short)9, (short)10, (short)9,
            (short)4, (short)2, (short)3, (short)1, (short)9, (short)12, (short)9, (short)4, (short)1, (short)3, (short)1, (short)13, (short)2, (short)9, (short)5, (short)1,
            (short)3, (short)1, (short)28, (short)3, (short)9, (short)5, (short)1, (short)2, (short)2, (short)9, (short)10, (short)9, (short)5, (short)1, (short)1, (short)1,
            (short)72, (short)11, (short)9, (short)5, (short)1, (short)1, (short)1, (short)73, (short)12, (short)9, (short)5, (short)1, (short)3, (short)1, (short)28, (short)2,
            (short)9, (short)6, (short)1, (short)3, (short)1, (short)13, (short)4, (short)9, (short)6, (short)1, (short)1, (short)1, (short)74, (short)5, (short)9, (short)6,
            (short)1, (short)3, (short)1, (short)9, (short)10, (short)9, (short)6, (short)2, (short)3, (short)1, (short)9, (short)12, (short)9, (short)6, (short)1, (short)3,
            (short)1, (short)13, (short)2, (short)9, (short)7, (short)1, (short)1, (short)2, (short)5, (short)3, (short)9, (short)7, (short)1, (short)1, (short)1, (short)75,
            (short)4, (short)9, (short)7, (short)1, (short)1, (short)1, (short)76, (short)5, (short)9, (short)7, (short)1, (short)1, (short)1, (short)77, (short)6, (short)9,
            (short)7, (short)3, (short)2, (short)1, (short)9, (short)9, (short)9, (short)7, (short)1, (short)1, (short)1, (short)78, (short)10, (short)9, (short)7, (short)2,
            (short)2, (short)1, (short)54, (short)12, (short)9, (short)7, (short)1, (short)1, (short)2, (short)5, (short)1, (short)9, (short)8, (short)1, (short)1, (short)1,
            (short)63, (short)3, (short)9, (short)8, (short)3, (short)1, (short)1, (short)5, (short)6, (short)9, (short)8, (short)1, (short)2, (short)1, (short)13, (short)7,
            (short)9, (short)8, (short)1, (short)2, (short)1, (short)28, (short)8, (short)9, (short)8, (short)1, (short)2, (short)1, (short)13, (short)9, (short)9, (short)8,
            (short)3, (short)1, (short)1, (short)5, (short)13, (short)9, (short)8, (short)1, (short)1, (short)1, (short)63, (short)1, (short)9, (short)9, (short)1, (short)1,
            (short)1, (short)64, (short)2, (short)9, (short)9, (short)2, (short)1, (short)1, (short)46, (short)4, (short)9, (short)9, (short)1, (short)1, (short)1, (short)65,
            (short)5, (short)9, (short)9, (short)1, (short)1, (short)1, (short)66, (short)6, (short)9, (short)9, (short)3, (short)1, (short)2, (short)9, (short)9, (short)9,
            (short)9, (short)1, (short)1, (short)1, (short)67, (short)10, (short)9, (short)9, (short)1, (short)1, (short)1, (short)68, (short)11, (short)9, (short)9, (short)2,
            (short)1, (short)1, (short)46, (short)13, (short)9, (short)9, (short)1, (short)1, (short)1, (short)64, (short)1, (short)9, (short)10, (short)5, (short)6, (short)1,
            (short)9, (short)9, (short)9, (short)10, (short)5, (short)6, (short)1, (short)9, (short)1, (short)10, (short)1, (short)4, (short)5, (short)1, (short)9, (short)5,
            (short)10, (short)1, (short)1, (short)1, (short)1, (short)65, (short)6, (short)10, (short)1, (short)1, (short)1, (short)1, (short)66, (short)7, (short)10, (short)1,
            (short)1, (short)1, (short)1, (short)9, (short)8, (short)10, (short)1, (short)1, (short)1, (short)1, (short)67, (short)9, (short)10, (short)1, (short)1, (short)1,
            (short)1, (short)68, (short)10, (short)10, (short)1, (short)4, (short)5, (short)1, (short)9, (short)1, (short)10, (short)2, (short)1, (short)1, (short)1, (short)61,
            (short)2, (short)10, (short)2, (short)4, (short)1, (short)1, (short)21, (short)9, (short)10, (short)2, (short)4, (short)1, (short)1, (short)21, (short)13, (short)10,
            (short)2, (short)1, (short)1, (short)1, (short)61, (short)1, (short)10, (short)3, (short)1, (short)1, (short)1, (short)62, (short)5, (short)10, (short)3, (short)1,
            (short)1, (short)1, (short)75, (short)10, (short)10, (short)3, (short)2, (short)1, (short)1, (short)9, (short)13, (short)10, (short)3, (short)1, (short)1, (short)1,
            (short)62, (short)1, (short)10, (short)4, (short)1, (short)1, (short)3, (short)9, (short)3, (short)10, (short)4, (short)1, (short)2, (short)1, (short)9, (short)13,
            (short)10, (short)4, (short)1, (short)1, (short)3, (short)9, (short)10, (short)10, (short)5, (short)2, (short)1, (short)1, (short)9, (short)4, (short)10, (short)6,
            (short)1, (short)1, (short)2, (short)9, (short)1, (short)10, (short)7, (short)1, (short)1, (short)1, (short)63, (short)2, (short)10, (short)7, (short)1, (short)1,
            (short)1, (short)5, (short)3, (short)10, (short)7, (short)1, (short)1, (short)1, (short)54, (short)5, (short)10, (short)7, (short)1, (short)1, (short)1, (short)79,
            (short)9, (short)10, (short)7, (short)1, (short)1, (short)1, (short)77, (short)12, (short)10, (short)7, (short)1, (short)1, (short)1, (short)5, (short)13, (short)10,
            (short)7, (short)1, (short)1, (short)1, (short)63, (short)1, (short)10, (short)8, (short)1, (short)1, (short)1, (short)64, (short)2, (short)10, (short)8, (short)4,
            (short)1, (short)1, (short)46, (short)9, (short)10, (short)8, (short)4, (short)1, (short)1, (short)46, (short)13, (short)10, (short)8, (short)1, (short)1, (short)1,
            (short)64, (short)1, (short)10, (short)9, (short)4, (short)5, (short)1, (short)9, (short)5, (short)10, (short)9, (short)1, (short)1, (short)1, (short)65, (short)6,
            (short)10, (short)9, (short)1, (short)1, (short)1, (short)66, (short)7, (short)10, (short)9, (short)1, (short)1, (short)2, (short)9, (short)8, (short)10, (short)9,
            (short)1, (short)1, (short)1, (short)67, (short)9, (short)10, (short)9, (short)1, (short)1, (short)1, (short)68, (short)10, (short)10, (short)9, (short)4, (short)5,
            (short)1, (short)9, (short)6, (short)10, (short)10, (short)1, (short)5, (short)1, (short)9, (short)8, (short)10, (short)10, (short)1, (short)5, (short)1, (short)9,
            (short)5, (short)11, (short)1, (short)1, (short)4, (short)2, (short)9, (short)6, (short)11, (short)1, (short)1, (short)1, (short)1, (short)65, (short)7, (short)11,
            (short)1, (short)1, (short)1, (short)1, (short)63, (short)8, (short)11, (short)1, (short)1, (short)1, (short)1, (short)68, (short)9, (short)11, (short)1, (short)1,
            (short)4, (short)2, (short)9, (short)1, (short)11, (short)2, (short)4, (short)4, (short)1, (short)9, (short)6, (short)11, (short)2, (short)1, (short)1, (short)1,
            (short)45, (short)7, (short)11, (short)2, (short)1, (short)1, (short)1, (short)42, (short)8, (short)11, (short)2, (short)1, (short)1, (short)1, (short)43, (short)10,
            (short)11, (short)2, (short)4, (short)4, (short)1, (short)9, (short)1, (short)11, (short)3, (short)1, (short)1, (short)1, (short)61, (short)2, (short)11, (short)3,
            (short)5, (short)1, (short)1, (short)21, (short)7, (short)11, (short)3, (short)1, (short)1, (short)1, (short)41, (short)8, (short)11, (short)3, (short)5, (short)1,
            (short)1, (short)21, (short)13, (short)11, (short)3, (short)1, (short)1, (short)1, (short)61, (short)1, (short)11, (short)4, (short)1, (short)1, (short)1, (short)62,
            (short)6, (short)11, (short)4, (short)4, (short)1, (short)3, (short)9, (short)13, (short)11, (short)4, (short)1, (short)1, (short)1, (short)62, (short)1, (short)11,
            (short)5, (short)1, (short)1, (short)1, (short)9, (short)3, (short)11, (short)5, (short)1, (short)1, (short)1, (short)6, (short)10, (short)11, (short)5, (short)1,
            (short)1, (short)1, (short)9, (short)11, (short)11, (short)5, (short)1, (short)1, (short)1, (short)6, (short)13, (short)11, (short)5, (short)1, (short)1, (short)1,
            (short)9, (short)1, (short)11, (short)6, (short)1, (short)1, (short)1, (short)63, (short)3, (short)11, (short)6, (short)2, (short)1, (short)1, (short)9, (short)13,
            (short)11, (short)6, (short)1, (short)1, (short)1, (short)63, (short)1, (short)11, (short)7, (short)1, (short)1, (short)1, (short)64, (short)2, (short)11, (short)7,
            (short)5, (short)1, (short)1, (short)46, (short)7, (short)11, (short)7, (short)1, (short)1, (short)1, (short)41, (short)8, (short)11, (short)7, (short)5, (short)1,
            (short)1, (short)46, (short)13, (short)11, (short)7, (short)1, (short)1, (short)1, (short)64, (short)1, (short)11, (short)8, (short)5, (short)4, (short)1, (short)9,
            (short)6, (short)11, (short)8, (short)1, (short)1, (short)1, (short)45, (short)7, (short)11, (short)8, (short)1, (short)1, (short)1, (short)42, (short)8, (short)11,
            (short)8, (short)1, (short)1, (short)1, (short)43, (short)9, (short)11, (short)8, (short)5, (short)4, (short)1, (short)9, (short)5, (short)11, (short)9, (short)1,
            (short)4, (short)1, (short)9, (short)6, (short)11, (short)9, (short)1, (short)1, (short)1, (short)65, (short)7, (short)11, (short)9, (short)1, (short)1, (short)1,
            (short)62, (short)8, (short)11, (short)9, (short)1, (short)1, (short)1, (short)68, (short)9, (short)11, (short)9, (short)1, (short)4, (short)1, (short)9, (short)7,
            (short)11, (short)10, (short)1, (short)1, (short)1, (short)9, (short)1, (short)12, (short)0, (short)6, (short)3, (short)1, (short)9, (short)7, (short)12, (short)0,
            (short)1, (short)1, (short)1, (short)63, (short)8, (short)12, (short)0, (short)6, (short)3, (short)1, (short)9, (short)6, (short)12, (short)1, (short)1, (short)3,
            (short)3, (short)9, (short)7, (short)12, (short)1, (short)1, (short)1, (short)1, (short)64, (short)8, (short)12, (short)1, (short)1, (short)3, (short)3, (short)9,
            (short)7, (short)12, (short)2, (short)1, (short)1, (short)2, (short)39, (short)1, (short)12, (short)3, (short)5, (short)3, (short)1, (short)9, (short)9, (short)12,
            (short)3, (short)5, (short)3, (short)1, (short)9, (short)1, (short)12, (short)4, (short)1, (short)1, (short)1, (short)61, (short)2, (short)12, (short)4, (short)11,
            (short)1, (short)1, (short)21, (short)13, (short)12, (short)4, (short)1, (short)1, (short)1, (short)61, (short)1, (short)12, (short)5, (short)1, (short)1, (short)1,
            (short)67, (short)2, (short)12, (short)5, (short)11, (short)1, (short)1, (short)41, (short)13, (short)12, (short)5, (short)1, (short)1, (short)1, (short)66, (short)1,
            (short)12, (short)6, (short)1, (short)1, (short)1, (short)64, (short)2, (short)12, (short)6, (short)11, (short)1, (short)1, (short)46, (short)13, (short)12, (short)6,
            (short)1, (short)1, (short)1, (short)64, (short)1, (short)12, (short)7, (short)6, (short)3, (short)1, (short)9, (short)7, (short)12, (short)7, (short)1, (short)1,
            (short)2, (short)39, (short)8, (short)12, (short)7, (short)6, (short)3, (short)1, (short)9, (short)6, (short)12, (short)8, (short)1, (short)3, (short)2, (short)9,
            (short)8, (short)12, (short)8, (short)1, (short)3, (short)2, (short)9, (short)7, (short)12, (short)9, (short)1, (short)1, (short)1, (short)61, (short)7, (short)12,
            (short)10, (short)1, (short)1, (short)1, (short)62, (short)0, (short)13, (short)0, (short)1, (short)2, (short)5, (short)9, (short)7, (short)13, (short)0, (short)1,
            (short)1, (short)1, (short)6, (short)14, (short)13, (short)0, (short)1, (short)2, (short)5, (short)9, (short)7, (short)13, (short)1, (short)1, (short)2, (short)4,
            (short)9, (short)1, (short)13, (short)4, (short)6, (short)2, (short)1, (short)9, (short)8, (short)13, (short)4, (short)6, (short)2, (short)1, (short)9, (short)0,
            (short)13, (short)5, (short)1, (short)1, (short)1, (short)67, (short)1, (short)13, (short)5, (short)1, (short)1, (short)1, (short)68, (short)2, (short)13, (short)5,
            (short)11, (short)1, (short)1, (short)80, (short)13, (short)13, (short)5, (short)1, (short)1, (short)1, (short)65, (short)14, (short)13, (short)5, (short)1, (short)1,
            (short)1, (short)66, (short)0, (short)13, (short)6, (short)15, (short)2, (short)1, (short)9, (short)0, (short)13, (short)7, (short)1, (short)2, (short)6, (short)9,
            (short)7, (short)13, (short)7, (short)1, (short)2, (short)3, (short)9, (short)14, (short)13, (short)7, (short)1, (short)2, (short)6, (short)9, (short)7, (short)13,
            (short)10, (short)1, (short)1, (short)1, (short)6, (short)7, (short)14, (short)0, (short)1, (short)1, (short)1, (short)9, (short)0, (short)14, (short)5, (short)1,
            (short)1, (short)1, (short)6, (short)1, (short)14, (short)5, (short)13, (short)1, (short)1, (short)9, (short)14, (short)14, (short)5, (short)1, (short)1, (short)1,
            (short)6, (short)7, (short)14, (short)10, (short)1, (short)1, (short)1, (short)9
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
    public static int getHeight() { return 15; }
    public static int getLength() { return 13; }

    /*
     * TILE ENTITY TEMPLATE STUBS - SEARCHABLE / MANUAL
     *
     * These are intentionally comments. Copy the useful data into real 1.7.10
     * TileEntity setup code after block placement. Names and coordinates are
     * preserved so chests, spawners, signs, mod machines, etc. can be restored
     * manually without guessing which block each tile belongs to.
     */

    // TILE_ENTITY_000
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (7, 13, 0), world: (x + 7, y + 13, z + 0)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 13, z + 0);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 13
    //     z: 0
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_001
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (0, 14, 5), world: (x + 0, y + 14, z + 5)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 0, y + 14, z + 5);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 0
    //     y: 14
    //     z: 5
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_002
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (3, 7, 2), world: (x + 3, y + 7, z + 2)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 3, y + 7, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 3
    //     y: 7
    //     z: 2
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_003
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (3, 11, 5), world: (x + 3, y + 11, z + 5)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 3, y + 11, z + 5);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 3
    //     y: 11
    //     z: 5
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_004
    // id: FlowerPot
    // block: minecraft:flower_pot meta 8 numericId 140
    // pos: (4, 2, 4), world: (x + 4, y + 2, z + 4)
    // TODO TILE STUB NAME: FlowerPot
    // TODO BLOCK STUB NAME: minecraft:flower_pot
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 2, z + 4);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Item: 38
    //     x: 4
    //     y: 2
    //     z: 4
    //     Data: 8
    //     id: 'FlowerPot'
    //   }

    // TILE_ENTITY_005
    // id: Furnace
    // block: minecraft:furnace meta 5 numericId 61
    // pos: (4, 1, 5), world: (x + 4, y + 1, z + 5)
    // TODO TILE STUB NAME: Furnace
    // TODO BLOCK STUB NAME: minecraft:furnace
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 1, z + 5);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     CookTime: 0
    //     x: 4
    //     BurnTime: 0
    //     y: 1
    //     z: 5
    //   Items: list[0]
    //     id: 'Furnace'
    //   }

    // TILE_ENTITY_006
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (4, 3, 5), world: (x + 4, y + 3, z + 5)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 3, z + 5);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 4
    //     y: 3
    //     z: 5
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_007
    // id: TileArcaneWorkbench
    // block: Thaumcraft:blockTable meta 15 numericId 189
    // pos: (4, 1, 7), world: (x + 4, y + 1, z + 7)
    // TODO TILE STUB NAME: TileArcaneWorkbench
    // TODO BLOCK STUB NAME: Thaumcraft:blockTable
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 1, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 4
    //     y: 1
    //     z: 7
    //     id: 'TileArcaneWorkbench'
    //   Inventory: list[0]
    //   }

    // TILE_ENTITY_008
    // id: Sign
    // block: minecraft:wall_sign meta 5 numericId 68
    // pos: (4, 2, 7), world: (x + 4, y + 2, z + 7)
    // TODO TILE STUB NAME: Sign
    // TODO BLOCK STUB NAME: minecraft:wall_sign
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 2, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 4
    //     Text4: ''
    //     y: 2
    //     Text3: ''
    //     z: 7
    //     Text2: ''
    //     id: 'Sign'
    //     Text1: ''
    //   }

    // TILE_ENTITY_009
    // id: Sign
    // block: minecraft:wall_sign meta 5 numericId 68
    // pos: (4, 3, 7), world: (x + 4, y + 3, z + 7)
    // TODO TILE STUB NAME: Sign
    // TODO BLOCK STUB NAME: minecraft:wall_sign
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 3, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 4
    //     Text4: ''
    //     y: 3
    //     Text3: ''
    //     z: 7
    //     Text2: ''
    //     id: 'Sign'
    //     Text1: ''
    //   }

    // TILE_ENTITY_010
    // id: TileCrystal
    // block: Thaumcraft:blockCrystal meta 6 numericId 183
    // pos: (7, 6, 8), world: (x + 7, y + 6, z + 8)
    // TODO TILE STUB NAME: TileCrystal
    // TODO BLOCK STUB NAME: Thaumcraft:blockCrystal
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 6, z + 8);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     orientation: 1
    //     x: 7
    //     y: 6
    //     z: 8
    //     id: 'TileCrystal'
    //   }

    // TILE_ENTITY_011
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (7, 7, 8), world: (x + 7, y + 7, z + 8)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 7, z + 8);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 7
    //     z: 8
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_012
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (7, 13, 10), world: (x + 7, y + 13, z + 10)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 13, z + 10);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 13
    //     z: 10
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_013
    // id: Chest
    // block: minecraft:chest meta 3 numericId 54
    // pos: (10, 5, 2), world: (x + 10, y + 5, z + 2)
    // TODO TILE STUB NAME: Chest
    // TODO BLOCK STUB NAME: minecraft:chest
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 5, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 10
    //     y: 5
    //     z: 2
    //   Items: list[0]
    //     id: 'Chest'
    //   }

    // TILE_ENTITY_014
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (10, 7, 2), world: (x + 10, y + 7, z + 2)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 7, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 10
    //     y: 7
    //     z: 2
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_015
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (10, 1, 6), world: (x + 10, y + 1, z + 6)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 1, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 10
    //     y: 1
    //     facing: 4
    //     z: 6
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_016
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (10, 0, 7), world: (x + 10, y + 0, z + 7)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 0, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 10
    //     y: 0
    //     z: 7
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_017
    // id: TileCrucible
    // block: Thaumcraft:blockMetalDevice meta 0 numericId 185
    // pos: (10, 1, 7), world: (x + 10, y + 1, z + 7)
    // TODO TILE STUB NAME: TileCrucible
    // TODO BLOCK STUB NAME: Thaumcraft:blockMetalDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 1, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Heat: 0
    //     FluidName: 'water'
    //     Amount: 0
    //   Aspects: list[0]
    //     x: 10
    //     y: 1
    //     z: 7
    //     id: 'TileCrucible'
    //   }

    // TILE_ENTITY_018
    // id: Chest
    // block: minecraft:chest meta 3 numericId 54
    // pos: (11, 9, 3), world: (x + 11, y + 9, z + 3)
    // TODO TILE STUB NAME: Chest
    // TODO BLOCK STUB NAME: minecraft:chest
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 9, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 11
    //     y: 9
    //     z: 3
    //   Items: list[0]
    //     id: 'Chest'
    //   }

    // TILE_ENTITY_019
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (11, 11, 5), world: (x + 11, y + 11, z + 5)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 11, z + 5);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 11
    //     y: 11
    //     z: 5
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_020
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (14, 14, 5), world: (x + 14, y + 14, z + 5)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 14, y + 14, z + 5);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 14
    //     y: 14
    //     z: 5
    //     id: 'TileNitor'
    //   }
}
