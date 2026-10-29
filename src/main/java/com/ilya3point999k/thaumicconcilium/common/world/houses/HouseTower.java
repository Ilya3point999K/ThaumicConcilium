package com.ilya3point999k.thaumicconcilium.common.world.houses;

/*
 * GENERATED STRUCTURE TEMPLATE
 * Generator script version: 2026-07-02-v7-optional-world-schematica-first
 * Size: 9 x 18 x 9
 * Unique numeric block IDs used: 28
 * Modded numeric block IDs recognized: 7
 * Tile entity stubs: 15
 * Entity stubs: 0
 *
 * This file is deliberately a template. Compact BOXES place normal blocks;
 * the searchable comments below list every used block ID/name and every
 * tile/entity NBT stub for manual restoration.
 *
 * Schematic mapping entries detected: 28
 * Recognized modded blocks:
 *   178 -> Thaumcraft:blockCustomPlant  metas=0  count=1
 *   183 -> Thaumcraft:blockCrystal  metas=1  count=1
 *   189 -> Thaumcraft:blockTable  metas=15  count=1
 *   192 -> Thaumcraft:blockLifter  metas=0  count=1
 *   195 -> Thaumcraft:blockJar  metas=0  count=5
 *   196 -> Thaumcraft:blockCandle  metas=3,10,11  count=6
 *   198 -> Thaumcraft:blockAiry  metas=1  count=4
 */

import java.util.Random;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thaumcraft.common.config.ConfigBlocks;

public class HouseTower extends WorldGenerator {

    /*
     * USED BLOCK IDS / NAMES
     * Format: numericId:meta -> registryName  count
     */
    // 0:0 -> minecraft:air  count=718
    // 0:1 -> minecraft:air  count=1
    // 0:7 -> minecraft:air  count=1
    // 0:9 -> minecraft:air  count=3
    // 0:11 -> minecraft:air  count=1
    // 1:0 -> minecraft:stone  count=20
    // 2:0 -> minecraft:grass  count=13
    // 3:0 -> minecraft:dirt  count=20
    // 5:1 -> minecraft:planks  count=108
    // 17:0 -> minecraft:log  count=124
    // 17:4 -> minecraft:log  count=36
    // 17:8 -> minecraft:log  count=36
    // 20:0 -> minecraft:glass  count=9
    // 26:1 -> minecraft:bed  count=1
    // 26:9 -> minecraft:bed  count=1
    // 47:0 -> minecraft:bookshelf  count=26
    // 53:0 -> minecraft:oak_stairs  count=1
    // 53:1 -> minecraft:oak_stairs  count=2
    // 53:2 -> minecraft:oak_stairs  count=1
    // 53:4 -> minecraft:oak_stairs  count=2
    // 53:5 -> minecraft:oak_stairs  count=4
    // 53:6 -> minecraft:oak_stairs  count=3
    // 53:7 -> minecraft:oak_stairs  count=3
    // 54:3 -> minecraft:chest  count=2
    // 54:4 -> minecraft:chest  count=1
    // 64:0 -> minecraft:wooden_door  count=1
    // 64:1 -> minecraft:wooden_door  count=1
    // 64:2 -> minecraft:wooden_door  count=1
    // 64:3 -> minecraft:wooden_door  count=1
    // 64:8 -> minecraft:wooden_door  count=4
    // 67:0 -> minecraft:stone_stairs  count=2
    // 67:1 -> minecraft:stone_stairs  count=2
    // 67:2 -> minecraft:stone_stairs  count=2
    // 67:3 -> minecraft:stone_stairs  count=2
    // 70:0 -> minecraft:stone_pressure_plate  count=2
    // 85:0 -> minecraft:fence  count=28
    // 96:4 -> minecraft:trapdoor  count=1
    // 96:7 -> minecraft:trapdoor  count=1
    // 98:0 -> minecraft:stonebrick  count=28
    // 102:0 -> minecraft:glass_pane  count=48
    // 126:1 -> minecraft:wooden_slab  count=36
    // 126:8 -> minecraft:wooden_slab  count=1
    // 126:9 -> minecraft:wooden_slab  count=52
    // 134:0 -> minecraft:spruce_stairs  count=12
    // 134:1 -> minecraft:spruce_stairs  count=12
    // 134:2 -> minecraft:spruce_stairs  count=12
    // 134:3 -> minecraft:spruce_stairs  count=12
    // 134:4 -> minecraft:spruce_stairs  count=10
    // 134:5 -> minecraft:spruce_stairs  count=9
    // 134:6 -> minecraft:spruce_stairs  count=10
    // 134:7 -> minecraft:spruce_stairs  count=10
    // 170:0 -> minecraft:hay_block  count=2
    // 178:0 -> Thaumcraft:blockCustomPlant  count=1
    // 183:1 -> Thaumcraft:blockCrystal  count=1
    // 189:15 -> Thaumcraft:blockTable  count=1
    // 192:0 -> Thaumcraft:blockLifter  count=1
    // 195:0 -> Thaumcraft:blockJar  count=5
    // 196:3 -> Thaumcraft:blockCandle  count=3
    // 196:10 -> Thaumcraft:blockCandle  count=2
    // 196:11 -> Thaumcraft:blockCandle  count=1
    // 198:1 -> Thaumcraft:blockAiry  count=4

    /*
     * COMPACT PALETTE
     * BOXES stores only palette indexes; this list makes those indexes editable.
     * PALETTE below contains direct Block references, not runtime GameRegistry lookups.
     */
    // palette[0] = minecraft:grass meta 0
    // palette[1] = minecraft:dirt meta 0
    // palette[2] = minecraft:stonebrick meta 0
    // palette[3] = minecraft:planks meta 1
    // palette[4] = minecraft:spruce_stairs meta 1
    // palette[5] = minecraft:spruce_stairs meta 3
    // palette[6] = minecraft:spruce_stairs meta 0
    // palette[7] = Thaumcraft:blockLifter meta 0
    // palette[8] = minecraft:spruce_stairs meta 2
    // palette[9] = minecraft:air meta 0
    // palette[10] = minecraft:stone_stairs meta 2
    // palette[11] = minecraft:log meta 0
    // palette[12] = minecraft:wooden_door meta 3
    // palette[13] = minecraft:stone_stairs meta 0
    // palette[14] = minecraft:oak_stairs meta 5
    // palette[15] = minecraft:stone_stairs meta 1
    // palette[16] = minecraft:oak_stairs meta 7
    // palette[17] = minecraft:chest meta 4
    // palette[18] = minecraft:wooden_door meta 2
    // palette[19] = minecraft:wooden_door meta 0
    // palette[20] = minecraft:trapdoor meta 4
    // palette[21] = minecraft:trapdoor meta 7
    // palette[22] = minecraft:oak_stairs meta 6
    // palette[23] = minecraft:oak_stairs meta 4
    // palette[24] = minecraft:stone_stairs meta 3
    // palette[25] = minecraft:wooden_door meta 1
    // palette[26] = minecraft:wooden_door meta 8
    // palette[27] = Thaumcraft:blockCrystal meta 1
    // palette[28] = minecraft:stone_pressure_plate meta 0
    // palette[29] = Thaumcraft:blockCustomPlant meta 0
    // palette[30] = minecraft:air meta 1
    // palette[31] = minecraft:log meta 4
    // palette[32] = minecraft:log meta 8
    // palette[33] = minecraft:fence meta 0
    // palette[34] = minecraft:spruce_stairs meta 7
    // palette[35] = minecraft:spruce_stairs meta 5
    // palette[36] = minecraft:wooden_slab meta 9
    // palette[37] = minecraft:spruce_stairs meta 4
    // palette[38] = minecraft:spruce_stairs meta 6
    // palette[39] = minecraft:wooden_slab meta 1
    // palette[40] = minecraft:stone meta 0
    // palette[41] = Thaumcraft:blockTable meta 15
    // palette[42] = minecraft:glass_pane meta 0
    // palette[43] = minecraft:bookshelf meta 0
    // palette[44] = minecraft:chest meta 3
    // palette[45] = Thaumcraft:blockCandle meta 3
    // palette[46] = minecraft:hay_block meta 0
    // palette[47] = minecraft:air meta 11
    // palette[48] = minecraft:air meta 9
    // palette[49] = Thaumcraft:blockJar meta 0
    // palette[50] = Thaumcraft:blockCandle meta 10
    // palette[51] = minecraft:air meta 7
    // palette[52] = Thaumcraft:blockCandle meta 11
    // palette[53] = minecraft:bed meta 9
    // palette[54] = minecraft:bed meta 1
    // palette[55] = minecraft:oak_stairs meta 1
    // palette[56] = minecraft:wooden_slab meta 8
    // palette[57] = minecraft:oak_stairs meta 2
    // palette[58] = minecraft:oak_stairs meta 0
    // palette[59] = minecraft:glass meta 0
    // palette[60] = Thaumcraft:blockAiry meta 1

    /*
     * MOD BLOCK STUBS - MANUAL EDIT SECTION
     *
     * Vanilla palette entries use net.minecraft.init.Blocks directly.
     * Modded/missing entries are deliberately direct Block variables instead of
     * GameRegistry lookups. Replace null with the real block instance from the
     * owning mod, for example ConfigBlocks.blockJar or ModBlocks.someBlock.
     */
    private static Block MOD_THAUMCRAFT_BLOCKAIRY = ConfigBlocks.blockAiry; // TODO provide Thaumcraft:blockAiry
    private static Block MOD_THAUMCRAFT_BLOCKCANDLE = ConfigBlocks.blockCandle; // TODO provide Thaumcraft:blockCandle
    private static Block MOD_THAUMCRAFT_BLOCKCRYSTAL = ConfigBlocks.blockCrystal; // TODO provide Thaumcraft:blockCrystal
    private static Block MOD_THAUMCRAFT_BLOCKCUSTOMPLANT = ConfigBlocks.blockCustomPlant; // TODO provide Thaumcraft:blockCustomPlant
    private static Block MOD_THAUMCRAFT_BLOCKJAR = ConfigBlocks.blockJar; // TODO provide Thaumcraft:blockJar
    private static Block MOD_THAUMCRAFT_BLOCKLIFTER = ConfigBlocks.blockLifter; // TODO provide Thaumcraft:blockLifter
    private static Block MOD_THAUMCRAFT_BLOCKTABLE = ConfigBlocks.blockTable; // TODO provide Thaumcraft:blockTable

    private static final Block[] PALETTE = new Block[] {
            Blocks.grass, // palette[0] = minecraft:grass meta 0
            Blocks.dirt, // palette[1] = minecraft:dirt meta 0
            Blocks.stonebrick, // palette[2] = minecraft:stonebrick meta 0
            Blocks.planks, // palette[3] = minecraft:planks meta 1
            Blocks.spruce_stairs, // palette[4] = minecraft:spruce_stairs meta 1
            Blocks.spruce_stairs, // palette[5] = minecraft:spruce_stairs meta 3
            Blocks.spruce_stairs, // palette[6] = minecraft:spruce_stairs meta 0
            MOD_THAUMCRAFT_BLOCKLIFTER, // palette[7] = Thaumcraft:blockLifter meta 0
            Blocks.spruce_stairs, // palette[8] = minecraft:spruce_stairs meta 2
            Blocks.air, // palette[9] = minecraft:air meta 0
            Blocks.stone_stairs, // palette[10] = minecraft:stone_stairs meta 2
            Blocks.log, // palette[11] = minecraft:log meta 0
            Blocks.wooden_door, // palette[12] = minecraft:wooden_door meta 3
            Blocks.stone_stairs, // palette[13] = minecraft:stone_stairs meta 0
            Blocks.oak_stairs, // palette[14] = minecraft:oak_stairs meta 5
            Blocks.stone_stairs, // palette[15] = minecraft:stone_stairs meta 1
            Blocks.oak_stairs, // palette[16] = minecraft:oak_stairs meta 7
            Blocks.chest, // palette[17] = minecraft:chest meta 4
            Blocks.wooden_door, // palette[18] = minecraft:wooden_door meta 2
            Blocks.wooden_door, // palette[19] = minecraft:wooden_door meta 0
            Blocks.trapdoor, // palette[20] = minecraft:trapdoor meta 4
            Blocks.trapdoor, // palette[21] = minecraft:trapdoor meta 7
            Blocks.oak_stairs, // palette[22] = minecraft:oak_stairs meta 6
            Blocks.oak_stairs, // palette[23] = minecraft:oak_stairs meta 4
            Blocks.stone_stairs, // palette[24] = minecraft:stone_stairs meta 3
            Blocks.wooden_door, // palette[25] = minecraft:wooden_door meta 1
            Blocks.wooden_door, // palette[26] = minecraft:wooden_door meta 8
            MOD_THAUMCRAFT_BLOCKCRYSTAL, // palette[27] = Thaumcraft:blockCrystal meta 1
            Blocks.stone_pressure_plate, // palette[28] = minecraft:stone_pressure_plate meta 0
            MOD_THAUMCRAFT_BLOCKCUSTOMPLANT, // palette[29] = Thaumcraft:blockCustomPlant meta 0
            Blocks.air, // palette[30] = minecraft:air meta 1
            Blocks.log, // palette[31] = minecraft:log meta 4
            Blocks.log, // palette[32] = minecraft:log meta 8
            Blocks.fence, // palette[33] = minecraft:fence meta 0
            Blocks.spruce_stairs, // palette[34] = minecraft:spruce_stairs meta 7
            Blocks.spruce_stairs, // palette[35] = minecraft:spruce_stairs meta 5
            Blocks.wooden_slab, // palette[36] = minecraft:wooden_slab meta 9
            Blocks.spruce_stairs, // palette[37] = minecraft:spruce_stairs meta 4
            Blocks.spruce_stairs, // palette[38] = minecraft:spruce_stairs meta 6
            Blocks.wooden_slab, // palette[39] = minecraft:wooden_slab meta 1
            Blocks.stone, // palette[40] = minecraft:stone meta 0
            MOD_THAUMCRAFT_BLOCKTABLE, // palette[41] = Thaumcraft:blockTable meta 15
            Blocks.glass_pane, // palette[42] = minecraft:glass_pane meta 0
            Blocks.bookshelf, // palette[43] = minecraft:bookshelf meta 0
            Blocks.chest, // palette[44] = minecraft:chest meta 3
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[45] = Thaumcraft:blockCandle meta 3
            Blocks.hay_block, // palette[46] = minecraft:hay_block meta 0
            Blocks.air, // palette[47] = minecraft:air meta 11
            Blocks.air, // palette[48] = minecraft:air meta 9
            MOD_THAUMCRAFT_BLOCKJAR, // palette[49] = Thaumcraft:blockJar meta 0
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[50] = Thaumcraft:blockCandle meta 10
            Blocks.air, // palette[51] = minecraft:air meta 7
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[52] = Thaumcraft:blockCandle meta 11
            Blocks.bed, // palette[53] = minecraft:bed meta 9
            Blocks.bed, // palette[54] = minecraft:bed meta 1
            Blocks.oak_stairs, // palette[55] = minecraft:oak_stairs meta 1
            Blocks.wooden_slab, // palette[56] = minecraft:wooden_slab meta 8
            Blocks.oak_stairs, // palette[57] = minecraft:oak_stairs meta 2
            Blocks.oak_stairs, // palette[58] = minecraft:oak_stairs meta 0
            Blocks.glass, // palette[59] = minecraft:glass meta 0
            MOD_THAUMCRAFT_BLOCKAIRY // palette[60] = Thaumcraft:blockAiry meta 1
    };

    private static final byte[] META = new byte[] {
            (byte)0, (byte)0, (byte)0, (byte)1, (byte)1, (byte)3, (byte)0, (byte)0, (byte)2, (byte)0, (byte)2, (byte)0, (byte)3, (byte)0, (byte)5, (byte)1,
            (byte)7, (byte)4, (byte)2, (byte)0, (byte)4, (byte)7, (byte)6, (byte)4, (byte)3, (byte)1, (byte)8, (byte)1, (byte)0, (byte)0, (byte)1, (byte)4,
            (byte)8, (byte)0, (byte)7, (byte)5, (byte)9, (byte)4, (byte)6, (byte)1, (byte)0, (byte)15, (byte)0, (byte)0, (byte)3, (byte)3, (byte)0, (byte)11,
            (byte)9, (byte)0, (byte)10, (byte)7, (byte)11, (byte)9, (byte)1, (byte)1, (byte)8, (byte)2, (byte)0, (byte)0, (byte)1
    };

    // x, y, z, sizeX, sizeY, sizeZ, palette index
    private static final short[] BOXES = new short[] {
            (short)0, (short)0, (short)0, (short)2, (short)1, (short)1, (short)0, (short)2, (short)0, (short)0, (short)2, (short)1, (short)1, (short)1, (short)4, (short)0,
            (short)0, (short)1, (short)1, (short)1, (short)2, (short)5, (short)0, (short)0, (short)2, (short)1, (short)1, (short)1, (short)7, (short)0, (short)0, (short)2,
            (short)1, (short)1, (short)0, (short)0, (short)0, (short)1, (short)1, (short)1, (short)1, (short)0, (short)1, (short)0, (short)1, (short)1, (short)1, (short)1,
            (short)1, (short)2, (short)0, (short)1, (short)5, (short)1, (short)1, (short)3, (short)7, (short)0, (short)1, (short)1, (short)1, (short)1, (short)1, (short)8,
            (short)0, (short)1, (short)1, (short)1, (short)1, (short)0, (short)0, (short)0, (short)2, (short)1, (short)1, (short)2, (short)1, (short)1, (short)0, (short)2,
            (short)1, (short)1, (short)5, (short)3, (short)2, (short)0, (short)2, (short)5, (short)1, (short)1, (short)2, (short)7, (short)0, (short)2, (short)1, (short)1,
            (short)5, (short)3, (short)8, (short)0, (short)2, (short)1, (short)1, (short)2, (short)1, (short)2, (short)0, (short)3, (short)1, (short)1, (short)4, (short)2,
            (short)3, (short)0, (short)3, (short)1, (short)1, (short)2, (short)4, (short)4, (short)0, (short)3, (short)1, (short)1, (short)1, (short)5, (short)5, (short)0,
            (short)3, (short)1, (short)1, (short)3, (short)6, (short)6, (short)0, (short)3, (short)1, (short)1, (short)4, (short)2, (short)0, (short)0, (short)4, (short)1,
            (short)1, (short)1, (short)2, (short)4, (short)0, (short)4, (short)1, (short)1, (short)1, (short)7, (short)8, (short)0, (short)4, (short)1, (short)1, (short)1,
            (short)2, (short)0, (short)0, (short)5, (short)1, (short)1, (short)2, (short)1, (short)3, (short)0, (short)5, (short)2, (short)1, (short)1, (short)8, (short)8,
            (short)0, (short)5, (short)1, (short)1, (short)2, (short)1, (short)3, (short)0, (short)6, (short)3, (short)1, (short)1, (short)2, (short)0, (short)0, (short)7,
            (short)1, (short)1, (short)2, (short)0, (short)1, (short)0, (short)7, (short)1, (short)1, (short)1, (short)1, (short)2, (short)0, (short)7, (short)5, (short)1,
            (short)1, (short)3, (short)7, (short)0, (short)7, (short)1, (short)1, (short)1, (short)1, (short)8, (short)0, (short)7, (short)1, (short)1, (short)2, (short)0,
            (short)1, (short)0, (short)8, (short)1, (short)1, (short)1, (short)0, (short)2, (short)0, (short)8, (short)2, (short)1, (short)1, (short)1, (short)4, (short)0,
            (short)8, (short)1, (short)1, (short)1, (short)2, (short)5, (short)0, (short)8, (short)2, (short)1, (short)1, (short)1, (short)7, (short)0, (short)8, (short)1,
            (short)1, (short)1, (short)0, (short)0, (short)1, (short)0, (short)1, (short)17, (short)1, (short)9, (short)1, (short)1, (short)0, (short)1, (short)1, (short)1,
            (short)10, (short)2, (short)1, (short)0, (short)1, (short)1, (short)1, (short)2, (short)3, (short)1, (short)0, (short)1, (short)3, (short)1, (short)11, (short)4,
            (short)1, (short)0, (short)1, (short)1, (short)1, (short)12, (short)5, (short)1, (short)0, (short)1, (short)3, (short)1, (short)11, (short)6, (short)1, (short)0,
            (short)1, (short)1, (short)1, (short)2, (short)7, (short)1, (short)0, (short)1, (short)1, (short)1, (short)10, (short)8, (short)1, (short)0, (short)1, (short)17,
            (short)1, (short)9, (short)0, (short)1, (short)1, (short)1, (short)1, (short)1, (short)13, (short)1, (short)1, (short)1, (short)1, (short)4, (short)1, (short)11,
            (short)2, (short)1, (short)1, (short)1, (short)1, (short)1, (short)14, (short)3, (short)1, (short)1, (short)4, (short)3, (short)6, (short)9, (short)7, (short)1,
            (short)1, (short)1, (short)4, (short)1, (short)11, (short)8, (short)1, (short)1, (short)1, (short)1, (short)1, (short)15, (short)0, (short)1, (short)2, (short)1,
            (short)1, (short)1, (short)2, (short)1, (short)1, (short)2, (short)1, (short)1, (short)1, (short)16, (short)2, (short)1, (short)2, (short)1, (short)3, (short)4,
            (short)9, (short)7, (short)1, (short)2, (short)1, (short)1, (short)1, (short)17, (short)8, (short)1, (short)2, (short)1, (short)1, (short)1, (short)2, (short)0,
            (short)1, (short)3, (short)1, (short)3, (short)1, (short)11, (short)1, (short)1, (short)3, (short)1, (short)3, (short)2, (short)9, (short)7, (short)1, (short)3,
            (short)1, (short)3, (short)3, (short)9, (short)8, (short)1, (short)3, (short)1, (short)3, (short)1, (short)11, (short)0, (short)1, (short)4, (short)1, (short)1,
            (short)1, (short)18, (short)8, (short)1, (short)4, (short)1, (short)1, (short)1, (short)19, (short)0, (short)1, (short)5, (short)1, (short)3, (short)1, (short)11,
            (short)1, (short)1, (short)5, (short)1, (short)1, (short)1, (short)20, (short)8, (short)1, (short)5, (short)1, (short)3, (short)1, (short)11, (short)0, (short)1,
            (short)6, (short)1, (short)1, (short)1, (short)2, (short)1, (short)1, (short)6, (short)1, (short)1, (short)1, (short)0, (short)2, (short)1, (short)6, (short)1,
            (short)1, (short)1, (short)21, (short)7, (short)1, (short)6, (short)1, (short)1, (short)1, (short)22, (short)8, (short)1, (short)6, (short)1, (short)1, (short)1,
            (short)2, (short)0, (short)1, (short)7, (short)1, (short)1, (short)1, (short)13, (short)1, (short)1, (short)7, (short)1, (short)4, (short)1, (short)11, (short)2,
            (short)1, (short)7, (short)1, (short)1, (short)1, (short)14, (short)3, (short)1, (short)7, (short)3, (short)3, (short)1, (short)9, (short)6, (short)1, (short)7,
            (short)1, (short)1, (short)1, (short)23, (short)7, (short)1, (short)7, (short)1, (short)4, (short)1, (short)11, (short)8, (short)1, (short)7, (short)1, (short)1,
            (short)1, (short)15, (short)0, (short)1, (short)8, (short)1, (short)17, (short)1, (short)9, (short)1, (short)1, (short)8, (short)1, (short)1, (short)1, (short)24,
            (short)2, (short)1, (short)8, (short)1, (short)1, (short)1, (short)2, (short)3, (short)1, (short)8, (short)1, (short)3, (short)1, (short)11, (short)4, (short)1,
            (short)8, (short)1, (short)1, (short)1, (short)25, (short)5, (short)1, (short)8, (short)1, (short)3, (short)1, (short)11, (short)6, (short)1, (short)8, (short)1,
            (short)1, (short)1, (short)2, (short)7, (short)1, (short)8, (short)1, (short)1, (short)1, (short)24, (short)8, (short)1, (short)8, (short)1, (short)17, (short)1,
            (short)9, (short)1, (short)2, (short)0, (short)1, (short)16, (short)1, (short)9, (short)2, (short)2, (short)0, (short)1, (short)2, (short)1, (short)3, (short)4,
            (short)2, (short)0, (short)1, (short)1, (short)1, (short)26, (short)6, (short)2, (short)0, (short)1, (short)2, (short)1, (short)3, (short)7, (short)2, (short)0,
            (short)1, (short)16, (short)1, (short)9, (short)0, (short)2, (short)1, (short)1, (short)16, (short)1, (short)9, (short)2, (short)2, (short)1, (short)1, (short)1,
            (short)1, (short)27, (short)8, (short)2, (short)1, (short)1, (short)16, (short)1, (short)9, (short)0, (short)2, (short)2, (short)1, (short)2, (short)1, (short)3,
            (short)1, (short)2, (short)2, (short)1, (short)1, (short)1, (short)28, (short)7, (short)2, (short)2, (short)1, (short)2, (short)1, (short)9, (short)8, (short)2,
            (short)2, (short)1, (short)2, (short)1, (short)3, (short)0, (short)2, (short)4, (short)1, (short)1, (short)1, (short)26, (short)8, (short)2, (short)4, (short)1,
            (short)1, (short)1, (short)26, (short)1, (short)2, (short)5, (short)1, (short)2, (short)1, (short)9, (short)0, (short)2, (short)6, (short)1, (short)2, (short)1,
            (short)3, (short)1, (short)2, (short)6, (short)1, (short)1, (short)1, (short)29, (short)2, (short)2, (short)6, (short)1, (short)1, (short)2, (short)9, (short)7,
            (short)2, (short)6, (short)1, (short)1, (short)1, (short)28, (short)8, (short)2, (short)6, (short)1, (short)2, (short)1, (short)3, (short)0, (short)2, (short)7,
            (short)1, (short)16, (short)1, (short)9, (short)6, (short)2, (short)7, (short)1, (short)1, (short)1, (short)30, (short)8, (short)2, (short)7, (short)1, (short)16,
            (short)1, (short)9, (short)1, (short)2, (short)8, (short)1, (short)16, (short)1, (short)9, (short)2, (short)2, (short)8, (short)1, (short)2, (short)1, (short)3,
            (short)4, (short)2, (short)8, (short)1, (short)1, (short)1, (short)26, (short)6, (short)2, (short)8, (short)1, (short)2, (short)1, (short)3, (short)7, (short)2,
            (short)8, (short)1, (short)16, (short)1, (short)9, (short)4, (short)3, (short)0, (short)1, (short)1, (short)1, (short)31, (short)2, (short)3, (short)1, (short)1,
            (short)1, (short)1, (short)14, (short)1, (short)3, (short)2, (short)1, (short)1, (short)1, (short)16, (short)0, (short)3, (short)4, (short)1, (short)1, (short)1,
            (short)32, (short)8, (short)3, (short)4, (short)1, (short)1, (short)1, (short)32, (short)1, (short)3, (short)6, (short)2, (short)1, (short)1, (short)9, (short)7,
            (short)3, (short)6, (short)1, (short)1, (short)1, (short)22, (short)2, (short)3, (short)7, (short)1, (short)1, (short)1, (short)14, (short)6, (short)3, (short)7,
            (short)1, (short)1, (short)1, (short)23, (short)4, (short)3, (short)8, (short)1, (short)1, (short)1, (short)31, (short)2, (short)4, (short)0, (short)1, (short)2,
            (short)1, (short)11, (short)3, (short)4, (short)0, (short)1, (short)1, (short)1, (short)8, (short)4, (short)4, (short)0, (short)1, (short)7, (short)1, (short)33,
            (short)5, (short)4, (short)0, (short)1, (short)1, (short)1, (short)8, (short)6, (short)4, (short)0, (short)1, (short)2, (short)1, (short)11, (short)2, (short)4,
            (short)1, (short)5, (short)1, (short)1, (short)31, (short)0, (short)4, (short)2, (short)1, (short)2, (short)1, (short)11, (short)1, (short)4, (short)2, (short)1,
            (short)1, (short)5, (short)32, (short)2, (short)4, (short)2, (short)1, (short)2, (short)1, (short)3, (short)3, (short)4, (short)2, (short)3, (short)1, (short)1,
            (short)34, (short)6, (short)4, (short)2, (short)1, (short)2, (short)1, (short)3, (short)7, (short)4, (short)2, (short)1, (short)1, (short)5, (short)32, (short)8,
            (short)4, (short)2, (short)1, (short)2, (short)1, (short)11, (short)0, (short)4, (short)3, (short)1, (short)1, (short)1, (short)6, (short)2, (short)4, (short)3,
            (short)1, (short)1, (short)3, (short)35, (short)3, (short)4, (short)3, (short)3, (short)1, (short)1, (short)36, (short)6, (short)4, (short)3, (short)1, (short)1,
            (short)3, (short)37, (short)8, (short)4, (short)3, (short)1, (short)1, (short)1, (short)4, (short)0, (short)4, (short)4, (short)1, (short)7, (short)1, (short)33,
            (short)3, (short)4, (short)4, (short)1, (short)1, (short)2, (short)36, (short)4, (short)4, (short)4, (short)1, (short)10, (short)1, (short)9, (short)5, (short)4,
            (short)4, (short)1, (short)1, (short)2, (short)36, (short)8, (short)4, (short)4, (short)1, (short)7, (short)1, (short)33, (short)0, (short)4, (short)5, (short)1,
            (short)1, (short)1, (short)6, (short)4, (short)4, (short)5, (short)1, (short)1, (short)1, (short)36, (short)8, (short)4, (short)5, (short)1, (short)1, (short)1,
            (short)4, (short)0, (short)4, (short)6, (short)1, (short)2, (short)1, (short)11, (short)2, (short)4, (short)6, (short)1, (short)2, (short)1, (short)3, (short)3,
            (short)4, (short)6, (short)3, (short)1, (short)1, (short)38, (short)6, (short)4, (short)6, (short)1, (short)2, (short)1, (short)3, (short)8, (short)4, (short)6,
            (short)1, (short)2, (short)1, (short)11, (short)2, (short)4, (short)7, (short)5, (short)1, (short)1, (short)31, (short)2, (short)4, (short)8, (short)1, (short)2,
            (short)1, (short)11, (short)3, (short)4, (short)8, (short)1, (short)1, (short)1, (short)5, (short)4, (short)4, (short)8, (short)1, (short)7, (short)1, (short)33,
            (short)5, (short)4, (short)8, (short)1, (short)1, (short)1, (short)5, (short)6, (short)4, (short)8, (short)1, (short)2, (short)1, (short)11, (short)3, (short)5,
            (short)0, (short)1, (short)5, (short)1, (short)9, (short)5, (short)5, (short)0, (short)1, (short)5, (short)1, (short)9, (short)1, (short)5, (short)1, (short)1,
            (short)1, (short)1, (short)39, (short)2, (short)5, (short)1, (short)1, (short)5, (short)1, (short)11, (short)3, (short)5, (short)1, (short)1, (short)1, (short)1,
            (short)3, (short)4, (short)5, (short)1, (short)1, (short)5, (short)1, (short)40, (short)5, (short)5, (short)1, (short)1, (short)1, (short)1, (short)3, (short)6,
            (short)5, (short)1, (short)1, (short)5, (short)1, (short)11, (short)7, (short)5, (short)1, (short)1, (short)1, (short)1, (short)39, (short)1, (short)5, (short)2,
            (short)1, (short)5, (short)1, (short)11, (short)3, (short)5, (short)2, (short)1, (short)1, (short)1, (short)36, (short)4, (short)5, (short)2, (short)1, (short)1,
            (short)1, (short)34, (short)5, (short)5, (short)2, (short)1, (short)1, (short)1, (short)36, (short)7, (short)5, (short)2, (short)1, (short)5, (short)1, (short)11,
            (short)0, (short)5, (short)3, (short)1, (short)5, (short)1, (short)9, (short)1, (short)5, (short)3, (short)1, (short)1, (short)1, (short)3, (short)2, (short)5,
            (short)3, (short)1, (short)1, (short)1, (short)36, (short)3, (short)5, (short)3, (short)3, (short)5, (short)1, (short)9, (short)6, (short)5, (short)3, (short)1,
            (short)1, (short)1, (short)36, (short)7, (short)5, (short)3, (short)1, (short)1, (short)1, (short)3, (short)8, (short)5, (short)3, (short)1, (short)5, (short)1,
            (short)9, (short)1, (short)5, (short)4, (short)1, (short)5, (short)1, (short)40, (short)2, (short)5, (short)4, (short)1, (short)1, (short)1, (short)41, (short)3,
            (short)5, (short)4, (short)1, (short)5, (short)2, (short)9, (short)5, (short)5, (short)4, (short)1, (short)5, (short)2, (short)9, (short)6, (short)5, (short)4,
            (short)1, (short)1, (short)1, (short)37, (short)7, (short)5, (short)4, (short)1, (short)5, (short)1, (short)40, (short)0, (short)5, (short)5, (short)1, (short)5,
            (short)1, (short)9, (short)1, (short)5, (short)5, (short)1, (short)1, (short)1, (short)3, (short)2, (short)5, (short)5, (short)1, (short)1, (short)1, (short)36,
            (short)4, (short)5, (short)5, (short)1, (short)5, (short)1, (short)9, (short)6, (short)5, (short)5, (short)1, (short)1, (short)1, (short)36, (short)7, (short)5,
            (short)5, (short)1, (short)1, (short)1, (short)3, (short)8, (short)5, (short)5, (short)1, (short)5, (short)1, (short)9, (short)1, (short)5, (short)6, (short)1,
            (short)5, (short)1, (short)11, (short)3, (short)5, (short)6, (short)1, (short)1, (short)1, (short)36, (short)4, (short)5, (short)6, (short)1, (short)1, (short)1,
            (short)38, (short)5, (short)5, (short)6, (short)1, (short)1, (short)1, (short)36, (short)7, (short)5, (short)6, (short)1, (short)5, (short)1, (short)11, (short)1,
            (short)5, (short)7, (short)1, (short)1, (short)1, (short)39, (short)2, (short)5, (short)7, (short)1, (short)5, (short)1, (short)11, (short)3, (short)5, (short)7,
            (short)1, (short)1, (short)1, (short)3, (short)4, (short)5, (short)7, (short)1, (short)5, (short)1, (short)40, (short)5, (short)5, (short)7, (short)1, (short)1,
            (short)1, (short)3, (short)6, (short)5, (short)7, (short)1, (short)5, (short)1, (short)11, (short)7, (short)5, (short)7, (short)1, (short)1, (short)1, (short)39,
            (short)3, (short)5, (short)8, (short)1, (short)5, (short)1, (short)9, (short)5, (short)5, (short)8, (short)1, (short)5, (short)1, (short)9, (short)2, (short)6,
            (short)0, (short)1, (short)1, (short)1, (short)8, (short)6, (short)6, (short)0, (short)1, (short)1, (short)1, (short)8, (short)1, (short)6, (short)1, (short)1,
            (short)3, (short)1, (short)9, (short)3, (short)6, (short)1, (short)1, (short)3, (short)1, (short)42, (short)5, (short)6, (short)1, (short)1, (short)3, (short)1,
            (short)42, (short)7, (short)6, (short)1, (short)1, (short)3, (short)1, (short)9, (short)0, (short)6, (short)2, (short)1, (short)1, (short)1, (short)6, (short)2,
            (short)6, (short)2, (short)1, (short)1, (short)1, (short)43, (short)3, (short)6, (short)2, (short)2, (short)1, (short)1, (short)9, (short)5, (short)6, (short)2,
            (short)1, (short)1, (short)1, (short)44, (short)6, (short)6, (short)2, (short)1, (short)1, (short)1, (short)43, (short)8, (short)6, (short)2, (short)1, (short)1,
            (short)1, (short)4, (short)1, (short)6, (short)3, (short)1, (short)3, (short)1, (short)42, (short)2, (short)6, (short)3, (short)1, (short)1, (short)1, (short)45,
            (short)6, (short)6, (short)3, (short)1, (short)1, (short)1, (short)46, (short)7, (short)6, (short)3, (short)1, (short)3, (short)1, (short)42, (short)2, (short)6,
            (short)4, (short)1, (short)1, (short)1, (short)9, (short)6, (short)6, (short)4, (short)1, (short)1, (short)2, (short)9, (short)1, (short)6, (short)5, (short)1,
            (short)3, (short)1, (short)42, (short)2, (short)6, (short)5, (short)1, (short)1, (short)1, (short)47, (short)7, (short)6, (short)5, (short)1, (short)3, (short)1,
            (short)42, (short)0, (short)6, (short)6, (short)1, (short)1, (short)1, (short)6, (short)2, (short)6, (short)6, (short)2, (short)1, (short)1, (short)48, (short)4,
            (short)6, (short)6, (short)1, (short)1, (short)1, (short)46, (short)5, (short)6, (short)6, (short)1, (short)1, (short)1, (short)9, (short)6, (short)6, (short)6,
            (short)1, (short)1, (short)1, (short)49, (short)8, (short)6, (short)6, (short)1, (short)1, (short)1, (short)4, (short)1, (short)6, (short)7, (short)1, (short)3,
            (short)1, (short)9, (short)3, (short)6, (short)7, (short)1, (short)3, (short)1, (short)42, (short)5, (short)6, (short)7, (short)1, (short)3, (short)1, (short)42,
            (short)7, (short)6, (short)7, (short)1, (short)3, (short)1, (short)9, (short)2, (short)6, (short)8, (short)1, (short)1, (short)1, (short)5, (short)6, (short)6,
            (short)8, (short)1, (short)1, (short)1, (short)5, (short)2, (short)7, (short)0, (short)1, (short)3, (short)1, (short)9, (short)6, (short)7, (short)0, (short)1,
            (short)3, (short)1, (short)9, (short)0, (short)7, (short)2, (short)1, (short)3, (short)1, (short)9, (short)2, (short)7, (short)2, (short)2, (short)1, (short)1,
            (short)36, (short)4, (short)7, (short)2, (short)1, (short)1, (short)1, (short)34, (short)5, (short)7, (short)2, (short)2, (short)1, (short)1, (short)36, (short)8,
            (short)7, (short)2, (short)1, (short)3, (short)1, (short)9, (short)2, (short)7, (short)3, (short)1, (short)1, (short)1, (short)36, (short)6, (short)7, (short)3,
            (short)1, (short)1, (short)1, (short)36, (short)2, (short)7, (short)4, (short)1, (short)1, (short)1, (short)35, (short)6, (short)7, (short)4, (short)1, (short)1,
            (short)1, (short)37, (short)2, (short)7, (short)5, (short)1, (short)1, (short)2, (short)36, (short)6, (short)7, (short)5, (short)1, (short)1, (short)2, (short)36,
            (short)0, (short)7, (short)6, (short)1, (short)3, (short)1, (short)9, (short)3, (short)7, (short)6, (short)1, (short)1, (short)1, (short)36, (short)4, (short)7,
            (short)6, (short)1, (short)1, (short)1, (short)38, (short)5, (short)7, (short)6, (short)1, (short)1, (short)1, (short)36, (short)8, (short)7, (short)6, (short)1,
            (short)3, (short)1, (short)9, (short)2, (short)7, (short)8, (short)1, (short)3, (short)1, (short)9, (short)6, (short)7, (short)8, (short)1, (short)3, (short)1,
            (short)9, (short)2, (short)8, (short)2, (short)1, (short)1, (short)1, (short)49, (short)3, (short)8, (short)2, (short)1, (short)1, (short)1, (short)48, (short)4,
            (short)8, (short)2, (short)2, (short)1, (short)1, (short)9, (short)6, (short)8, (short)2, (short)1, (short)1, (short)1, (short)50, (short)2, (short)8, (short)3,
            (short)1, (short)1, (short)1, (short)51, (short)6, (short)8, (short)3, (short)1, (short)1, (short)2, (short)9, (short)2, (short)8, (short)4, (short)1, (short)1,
            (short)1, (short)45, (short)2, (short)8, (short)5, (short)1, (short)1, (short)1, (short)52, (short)6, (short)8, (short)5, (short)1, (short)1, (short)2, (short)49,
            (short)2, (short)8, (short)6, (short)1, (short)1, (short)1, (short)50, (short)3, (short)8, (short)6, (short)2, (short)1, (short)1, (short)9, (short)5, (short)8,
            (short)6, (short)1, (short)1, (short)1, (short)49, (short)1, (short)9, (short)1, (short)1, (short)1, (short)1, (short)36, (short)3, (short)9, (short)1, (short)1,
            (short)1, (short)1, (short)38, (short)5, (short)9, (short)1, (short)1, (short)1, (short)1, (short)38, (short)7, (short)9, (short)1, (short)1, (short)1, (short)1,
            (short)36, (short)2, (short)9, (short)2, (short)2, (short)1, (short)1, (short)36, (short)4, (short)9, (short)2, (short)1, (short)1, (short)1, (short)34, (short)5,
            (short)9, (short)2, (short)2, (short)1, (short)1, (short)36, (short)1, (short)9, (short)3, (short)1, (short)1, (short)1, (short)37, (short)2, (short)9, (short)3,
            (short)1, (short)1, (short)1, (short)36, (short)6, (short)9, (short)3, (short)1, (short)1, (short)1, (short)36, (short)7, (short)9, (short)3, (short)1, (short)1,
            (short)1, (short)35, (short)2, (short)9, (short)4, (short)1, (short)1, (short)1, (short)35, (short)6, (short)9, (short)4, (short)1, (short)1, (short)1, (short)37,
            (short)1, (short)9, (short)5, (short)1, (short)1, (short)1, (short)37, (short)2, (short)9, (short)5, (short)1, (short)1, (short)2, (short)36, (short)6, (short)9,
            (short)5, (short)1, (short)1, (short)2, (short)36, (short)7, (short)9, (short)5, (short)1, (short)1, (short)1, (short)35, (short)3, (short)9, (short)6, (short)1,
            (short)1, (short)1, (short)36, (short)4, (short)9, (short)6, (short)1, (short)1, (short)1, (short)38, (short)5, (short)9, (short)6, (short)1, (short)1, (short)1,
            (short)36, (short)1, (short)9, (short)7, (short)1, (short)1, (short)1, (short)36, (short)3, (short)9, (short)7, (short)1, (short)1, (short)1, (short)34, (short)5,
            (short)9, (short)7, (short)1, (short)1, (short)1, (short)34, (short)7, (short)9, (short)7, (short)1, (short)1, (short)1, (short)36, (short)2, (short)10, (short)0,
            (short)1, (short)1, (short)1, (short)38, (short)3, (short)10, (short)0, (short)1, (short)1, (short)1, (short)36, (short)5, (short)10, (short)0, (short)1, (short)1,
            (short)1, (short)36, (short)6, (short)10, (short)0, (short)1, (short)1, (short)1, (short)38, (short)1, (short)10, (short)1, (short)1, (short)1, (short)1, (short)11,
            (short)2, (short)10, (short)1, (short)5, (short)1, (short)1, (short)31, (short)7, (short)10, (short)1, (short)1, (short)1, (short)1, (short)11, (short)0, (short)10,
            (short)2, (short)1, (short)1, (short)1, (short)37, (short)1, (short)10, (short)2, (short)1, (short)1, (short)5, (short)32, (short)2, (short)10, (short)2, (short)2,
            (short)1, (short)1, (short)3, (short)4, (short)10, (short)2, (short)1, (short)1, (short)1, (short)32, (short)5, (short)10, (short)2, (short)2, (short)1, (short)1,
            (short)3, (short)7, (short)10, (short)2, (short)1, (short)1, (short)5, (short)32, (short)8, (short)10, (short)2, (short)1, (short)1, (short)1, (short)35, (short)0,
            (short)10, (short)3, (short)1, (short)1, (short)1, (short)36, (short)2, (short)10, (short)3, (short)1, (short)1, (short)1, (short)3, (short)3, (short)10, (short)3,
            (short)3, (short)1, (short)1, (short)5, (short)6, (short)10, (short)3, (short)1, (short)1, (short)1, (short)3, (short)8, (short)10, (short)3, (short)1, (short)1,
            (short)1, (short)36, (short)2, (short)10, (short)4, (short)1, (short)1, (short)1, (short)31, (short)3, (short)10, (short)4, (short)1, (short)1, (short)2, (short)4,
            (short)5, (short)10, (short)4, (short)1, (short)1, (short)1, (short)6, (short)6, (short)10, (short)4, (short)1, (short)1, (short)1, (short)31, (short)0, (short)10,
            (short)5, (short)1, (short)1, (short)1, (short)36, (short)2, (short)10, (short)5, (short)1, (short)1, (short)2, (short)3, (short)4, (short)10, (short)5, (short)2,
            (short)1, (short)1, (short)8, (short)6, (short)10, (short)5, (short)1, (short)1, (short)2, (short)3, (short)8, (short)10, (short)5, (short)1, (short)1, (short)1,
            (short)36, (short)0, (short)10, (short)6, (short)1, (short)1, (short)1, (short)37, (short)3, (short)10, (short)6, (short)1, (short)1, (short)1, (short)3, (short)4,
            (short)10, (short)6, (short)1, (short)1, (short)1, (short)32, (short)5, (short)10, (short)6, (short)1, (short)1, (short)1, (short)3, (short)8, (short)10, (short)6,
            (short)1, (short)1, (short)1, (short)35, (short)1, (short)10, (short)7, (short)1, (short)1, (short)1, (short)11, (short)2, (short)10, (short)7, (short)5, (short)1,
            (short)1, (short)31, (short)7, (short)10, (short)7, (short)1, (short)1, (short)1, (short)11, (short)2, (short)10, (short)8, (short)1, (short)1, (short)1, (short)34,
            (short)3, (short)10, (short)8, (short)1, (short)1, (short)1, (short)36, (short)5, (short)10, (short)8, (short)1, (short)1, (short)1, (short)36, (short)6, (short)10,
            (short)8, (short)1, (short)1, (short)1, (short)34, (short)2, (short)11, (short)0, (short)1, (short)3, (short)1, (short)11, (short)3, (short)11, (short)0, (short)1,
            (short)3, (short)1, (short)42, (short)4, (short)11, (short)0, (short)1, (short)3, (short)1, (short)3, (short)5, (short)11, (short)0, (short)1, (short)3, (short)1,
            (short)42, (short)6, (short)11, (short)0, (short)1, (short)3, (short)1, (short)11, (short)1, (short)11, (short)1, (short)1, (short)3, (short)1, (short)3, (short)2,
            (short)11, (short)1, (short)1, (short)3, (short)1, (short)43, (short)3, (short)11, (short)1, (short)3, (short)3, (short)3, (short)9, (short)6, (short)11, (short)1,
            (short)1, (short)3, (short)1, (short)43, (short)7, (short)11, (short)1, (short)1, (short)3, (short)1, (short)3, (short)0, (short)11, (short)2, (short)1, (short)3,
            (short)1, (short)11, (short)1, (short)11, (short)2, (short)1, (short)3, (short)1, (short)43, (short)2, (short)11, (short)2, (short)1, (short)3, (short)2, (short)9,
            (short)6, (short)11, (short)2, (short)1, (short)3, (short)2, (short)9, (short)7, (short)11, (short)2, (short)1, (short)3, (short)1, (short)43, (short)8, (short)11,
            (short)2, (short)1, (short)3, (short)1, (short)11, (short)0, (short)11, (short)3, (short)1, (short)3, (short)1, (short)42, (short)1, (short)11, (short)3, (short)1,
            (short)1, (short)1, (short)44, (short)7, (short)11, (short)3, (short)1, (short)1, (short)1, (short)16, (short)8, (short)11, (short)3, (short)1, (short)3, (short)1,
            (short)42, (short)0, (short)11, (short)4, (short)1, (short)3, (short)1, (short)3, (short)1, (short)11, (short)4, (short)1, (short)1, (short)1, (short)53, (short)2,
            (short)11, (short)4, (short)1, (short)1, (short)1, (short)54, (short)3, (short)11, (short)4, (short)1, (short)3, (short)3, (short)9, (short)5, (short)11, (short)4,
            (short)1, (short)3, (short)3, (short)9, (short)6, (short)11, (short)4, (short)1, (short)1, (short)1, (short)55, (short)7, (short)11, (short)4, (short)1, (short)1,
            (short)1, (short)56, (short)8, (short)11, (short)4, (short)1, (short)3, (short)1, (short)3, (short)0, (short)11, (short)5, (short)1, (short)3, (short)1, (short)42,
            (short)1, (short)11, (short)5, (short)2, (short)3, (short)1, (short)9, (short)4, (short)11, (short)5, (short)1, (short)3, (short)2, (short)9, (short)6, (short)11,
            (short)5, (short)1, (short)3, (short)2, (short)9, (short)7, (short)11, (short)5, (short)1, (short)1, (short)1, (short)22, (short)8, (short)11, (short)5, (short)1,
            (short)3, (short)1, (short)42, (short)0, (short)11, (short)6, (short)1, (short)3, (short)1, (short)11, (short)1, (short)11, (short)6, (short)1, (short)3, (short)1,
            (short)43, (short)2, (short)11, (short)6, (short)1, (short)3, (short)1, (short)9, (short)7, (short)11, (short)6, (short)1, (short)3, (short)1, (short)43, (short)8,
            (short)11, (short)6, (short)1, (short)3, (short)1, (short)11, (short)1, (short)11, (short)7, (short)1, (short)3, (short)1, (short)3, (short)2, (short)11, (short)7,
            (short)1, (short)3, (short)1, (short)43, (short)3, (short)11, (short)7, (short)1, (short)1, (short)1, (short)55, (short)4, (short)11, (short)7, (short)1, (short)1,
            (short)1, (short)57, (short)5, (short)11, (short)7, (short)1, (short)1, (short)1, (short)58, (short)6, (short)11, (short)7, (short)1, (short)3, (short)1, (short)43,
            (short)7, (short)11, (short)7, (short)1, (short)3, (short)1, (short)3, (short)2, (short)11, (short)8, (short)1, (short)3, (short)1, (short)11, (short)3, (short)11,
            (short)8, (short)1, (short)3, (short)1, (short)42, (short)4, (short)11, (short)8, (short)1, (short)3, (short)1, (short)3, (short)5, (short)11, (short)8, (short)1,
            (short)3, (short)1, (short)42, (short)6, (short)11, (short)8, (short)1, (short)3, (short)1, (short)11, (short)1, (short)12, (short)3, (short)1, (short)2, (short)2,
            (short)9, (short)7, (short)12, (short)3, (short)1, (short)2, (short)2, (short)9, (short)2, (short)12, (short)4, (short)1, (short)2, (short)1, (short)9, (short)6,
            (short)12, (short)4, (short)1, (short)2, (short)1, (short)9, (short)7, (short)12, (short)5, (short)1, (short)1, (short)1, (short)45, (short)3, (short)12, (short)7,
            (short)3, (short)2, (short)1, (short)9, (short)7, (short)13, (short)5, (short)1, (short)1, (short)1, (short)9, (short)2, (short)14, (short)0, (short)1, (short)1,
            (short)1, (short)8, (short)3, (short)14, (short)0, (short)3, (short)1, (short)1, (short)39, (short)6, (short)14, (short)0, (short)1, (short)1, (short)1, (short)8,
            (short)1, (short)14, (short)1, (short)1, (short)1, (short)1, (short)39, (short)2, (short)14, (short)1, (short)1, (short)1, (short)5, (short)32, (short)3, (short)14,
            (short)1, (short)3, (short)1, (short)1, (short)3, (short)6, (short)14, (short)1, (short)1, (short)1, (short)1, (short)32, (short)7, (short)14, (short)1, (short)1,
            (short)1, (short)1, (short)39, (short)0, (short)14, (short)2, (short)1, (short)1, (short)1, (short)6, (short)1, (short)14, (short)2, (short)1, (short)1, (short)1,
            (short)31, (short)3, (short)14, (short)2, (short)5, (short)1, (short)1, (short)31, (short)8, (short)14, (short)2, (short)1, (short)1, (short)1, (short)4, (short)0,
            (short)14, (short)3, (short)1, (short)1, (short)3, (short)39, (short)1, (short)14, (short)3, (short)1, (short)1, (short)3, (short)3, (short)3, (short)14, (short)3,
            (short)3, (short)1, (short)3, (short)59, (short)6, (short)14, (short)3, (short)1, (short)1, (short)5, (short)32, (short)7, (short)14, (short)3, (short)1, (short)1,
            (short)3, (short)3, (short)8, (short)14, (short)3, (short)1, (short)1, (short)3, (short)39, (short)0, (short)14, (short)6, (short)1, (short)1, (short)1, (short)6,
            (short)1, (short)14, (short)6, (short)5, (short)1, (short)1, (short)31, (short)7, (short)14, (short)6, (short)1, (short)1, (short)1, (short)31, (short)8, (short)14,
            (short)6, (short)1, (short)1, (short)1, (short)4, (short)1, (short)14, (short)7, (short)1, (short)1, (short)1, (short)39, (short)2, (short)14, (short)7, (short)1,
            (short)1, (short)1, (short)32, (short)3, (short)14, (short)7, (short)3, (short)1, (short)1, (short)3, (short)7, (short)14, (short)7, (short)1, (short)1, (short)1,
            (short)39, (short)2, (short)14, (short)8, (short)1, (short)1, (short)1, (short)5, (short)3, (short)14, (short)8, (short)3, (short)1, (short)1, (short)39, (short)6,
            (short)14, (short)8, (short)1, (short)1, (short)1, (short)5, (short)2, (short)15, (short)0, (short)5, (short)3, (short)1, (short)9, (short)1, (short)15, (short)1,
            (short)1, (short)3, (short)1, (short)9, (short)2, (short)15, (short)1, (short)1, (short)1, (short)1, (short)3, (short)3, (short)15, (short)1, (short)3, (short)3,
            (short)1, (short)9, (short)6, (short)15, (short)1, (short)1, (short)1, (short)1, (short)3, (short)7, (short)15, (short)1, (short)1, (short)3, (short)1, (short)9,
            (short)0, (short)15, (short)2, (short)1, (short)3, (short)5, (short)9, (short)1, (short)15, (short)2, (short)1, (short)1, (short)1, (short)3, (short)2, (short)15,
            (short)2, (short)5, (short)1, (short)1, (short)39, (short)7, (short)15, (short)2, (short)1, (short)1, (short)1, (short)3, (short)8, (short)15, (short)2, (short)1,
            (short)3, (short)5, (short)9, (short)1, (short)15, (short)3, (short)1, (short)3, (short)3, (short)9, (short)2, (short)15, (short)3, (short)1, (short)1, (short)4,
            (short)39, (short)3, (short)15, (short)3, (short)3, (short)3, (short)3, (short)9, (short)6, (short)15, (short)3, (short)1, (short)1, (short)4, (short)39, (short)7,
            (short)15, (short)3, (short)1, (short)3, (short)3, (short)9, (short)1, (short)15, (short)6, (short)1, (short)1, (short)1, (short)3, (short)3, (short)15, (short)6,
            (short)3, (short)1, (short)1, (short)39, (short)7, (short)15, (short)6, (short)1, (short)1, (short)1, (short)3, (short)1, (short)15, (short)7, (short)1, (short)3,
            (short)1, (short)9, (short)2, (short)15, (short)7, (short)1, (short)1, (short)1, (short)3, (short)3, (short)15, (short)7, (short)3, (short)3, (short)2, (short)9,
            (short)6, (short)15, (short)7, (short)1, (short)1, (short)1, (short)3, (short)7, (short)15, (short)7, (short)1, (short)3, (short)1, (short)9, (short)2, (short)15,
            (short)8, (short)1, (short)3, (short)1, (short)9, (short)6, (short)15, (short)8, (short)1, (short)3, (short)1, (short)9, (short)2, (short)16, (short)1, (short)1,
            (short)1, (short)1, (short)8, (short)6, (short)16, (short)1, (short)1, (short)1, (short)1, (short)8, (short)1, (short)16, (short)2, (short)1, (short)1, (short)1,
            (short)6, (short)2, (short)16, (short)2, (short)5, (short)1, (short)1, (short)9, (short)7, (short)16, (short)2, (short)1, (short)1, (short)1, (short)4, (short)2,
            (short)16, (short)3, (short)1, (short)1, (short)4, (short)9, (short)6, (short)16, (short)3, (short)1, (short)1, (short)4, (short)9, (short)1, (short)16, (short)6,
            (short)1, (short)1, (short)1, (short)6, (short)3, (short)16, (short)6, (short)3, (short)2, (short)1, (short)9, (short)7, (short)16, (short)6, (short)1, (short)1,
            (short)1, (short)4, (short)2, (short)16, (short)7, (short)1, (short)1, (short)1, (short)5, (short)6, (short)16, (short)7, (short)1, (short)1, (short)1, (short)5,
            (short)2, (short)17, (short)1, (short)1, (short)1, (short)1, (short)9, (short)6, (short)17, (short)1, (short)1, (short)1, (short)1, (short)9, (short)1, (short)17,
            (short)2, (short)1, (short)1, (short)1, (short)9, (short)2, (short)17, (short)2, (short)1, (short)1, (short)1, (short)60, (short)3, (short)17, (short)2, (short)3,
            (short)1, (short)1, (short)9, (short)6, (short)17, (short)2, (short)1, (short)1, (short)1, (short)60, (short)7, (short)17, (short)2, (short)1, (short)1, (short)1,
            (short)9, (short)2, (short)17, (short)3, (short)1, (short)1, (short)3, (short)9, (short)6, (short)17, (short)3, (short)1, (short)1, (short)3, (short)9, (short)1,
            (short)17, (short)6, (short)1, (short)1, (short)1, (short)9, (short)2, (short)17, (short)6, (short)1, (short)1, (short)1, (short)60, (short)6, (short)17, (short)6,
            (short)1, (short)1, (short)1, (short)60, (short)7, (short)17, (short)6, (short)1, (short)1, (short)1, (short)9, (short)2, (short)17, (short)7, (short)1, (short)1,
            (short)1, (short)9, (short)6, (short)17, (short)7, (short)1, (short)1, (short)1, (short)9
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
            if (block == null) {
                continue;
            }
            int meta = META[pi] & 15;
            for (int yy = 0; yy < sy; yy++)
                for (int zz = 0; zz < sz; zz++)
                    for (int xx = 0; xx < sx; xx++)
                        world.setBlock(x + dx + xx, y + dy + yy, z + dz + zz, block, meta, 2);
        }
        if (rand.nextBoolean()) {
            Thaumaturge thaumaturge = new Thaumaturge(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2 + 1, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        } else {
            MadThaumaturge thaumaturge = new MadThaumaturge(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2 + 1, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        }
        return true;
    }

    public static int getWidth() { return 9; }
    public static int getHeight() { return 18; }
    public static int getLength() { return 9; }

    /*
     * TILE ENTITY TEMPLATE STUBS - SEARCHABLE / MANUAL
     *
     * These are intentionally comments. Copy the useful data into real 1.7.10
     * TileEntity setup code after block placement. Names and coordinates are
     * preserved so chests, spawners, signs, mod machines, etc. can be restored
     * manually without guessing which block each tile belongs to.
     */

    // TILE_ENTITY_000
    // id: Chest
    // block: minecraft:chest meta 3 numericId 54
    // pos: (1, 11, 3), world: (x + 1, y + 11, z + 3)
    // TODO TILE STUB NAME: Chest
    // TODO BLOCK STUB NAME: minecraft:chest
    // TODO example: TileEntity te = world.getTileEntity(x + 1, y + 11, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 1
    //     y: 11
    //     z: 3
    //   Items: list[0]
    //     id: 'Chest'
    //   }

    // TILE_ENTITY_001
    // id: TileCrystal
    // block: Thaumcraft:blockCrystal meta 1 numericId 183
    // pos: (2, 2, 1), world: (x + 2, y + 2, z + 1)
    // TODO TILE STUB NAME: TileCrystal
    // TODO BLOCK STUB NAME: Thaumcraft:blockCrystal
    // TODO example: TileEntity te = world.getTileEntity(x + 2, y + 2, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     orientation: 1
    //     x: 2
    //     y: 2
    //     z: 1
    //     id: 'TileCrystal'
    //   }

    // TILE_ENTITY_002
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (2, 8, 2), world: (x + 2, y + 8, z + 2)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 2, y + 8, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 2
    //     y: 8
    //     facing: 5
    //     z: 2
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_003
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (2, 17, 2), world: (x + 2, y + 17, z + 2)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 2, y + 17, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 2
    //     y: 17
    //     z: 2
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_004
    // id: TileArcaneWorkbench
    // block: Thaumcraft:blockTable meta 15 numericId 189
    // pos: (2, 5, 4), world: (x + 2, y + 5, z + 4)
    // TODO TILE STUB NAME: TileArcaneWorkbench
    // TODO BLOCK STUB NAME: Thaumcraft:blockTable
    // TODO example: TileEntity te = world.getTileEntity(x + 2, y + 5, z + 4);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 2
    //     y: 5
    //     z: 4
    //     id: 'TileArcaneWorkbench'
    //   Inventory: list[0]
    //   }

    // TILE_ENTITY_005
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (2, 17, 6), world: (x + 2, y + 17, z + 6)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 2, y + 17, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 2
    //     y: 17
    //     z: 6
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_006
    // id: TileLifter
    // block: Thaumcraft:blockLifter meta 0 numericId 192
    // pos: (4, 0, 4), world: (x + 4, y + 0, z + 4)
    // TODO TILE STUB NAME: TileLifter
    // TODO BLOCK STUB NAME: Thaumcraft:blockLifter
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 0, z + 4);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 4
    //     y: 0
    //     z: 4
    //     id: 'TileLifter'
    //   }

    // TILE_ENTITY_007
    // id: Chest
    // block: minecraft:chest meta 3 numericId 54
    // pos: (5, 6, 2), world: (x + 5, y + 6, z + 2)
    // TODO TILE STUB NAME: Chest
    // TODO BLOCK STUB NAME: minecraft:chest
    // TODO example: TileEntity te = world.getTileEntity(x + 5, y + 6, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 5
    //     y: 6
    //     z: 2
    //   Items: list[0]
    //     id: 'Chest'
    //   }

    // TILE_ENTITY_008
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (5, 8, 6), world: (x + 5, y + 8, z + 6)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 5, y + 8, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 5
    //     y: 8
    //     facing: 2
    //     z: 6
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_009
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (6, 17, 2), world: (x + 6, y + 17, z + 2)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 17, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 6
    //     y: 17
    //     z: 2
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_010
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (6, 8, 5), world: (x + 6, y + 8, z + 5)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 8, z + 5);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 6
    //     y: 8
    //     facing: 4
    //     z: 5
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_011
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (6, 6, 6), world: (x + 6, y + 6, z + 6)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 6, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 6
    //     y: 6
    //     facing: 4
    //     z: 6
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_012
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (6, 8, 6), world: (x + 6, y + 8, z + 6)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 8, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 6
    //     y: 8
    //     facing: 4
    //     z: 6
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_013
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (6, 17, 6), world: (x + 6, y + 17, z + 6)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 17, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 6
    //     y: 17
    //     z: 6
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_014
    // id: Chest
    // block: minecraft:chest meta 4 numericId 54
    // pos: (7, 1, 2), world: (x + 7, y + 1, z + 2)
    // TODO TILE STUB NAME: Chest
    // TODO BLOCK STUB NAME: minecraft:chest
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 1, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 1
    //     z: 2
    //   Items: list[0]
    //     id: 'Chest'
    //   }
}

