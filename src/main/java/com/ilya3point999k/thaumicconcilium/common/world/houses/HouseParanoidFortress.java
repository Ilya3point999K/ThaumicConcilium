package com.ilya3point999k.thaumicconcilium.common.world.houses;

/*
 * GENERATED STRUCTURE TEMPLATE
 * Generator script version: 2026-07-02-v7-optional-world-schematica-first
 * Size: 15 x 9 x 13
 * Unique numeric block IDs used: 40
 * Modded numeric block IDs recognized: 11
 * Tile entity stubs: 20
 * Entity stubs: 0
 *
 * This file is deliberately a template. Compact BOXES place normal blocks;
 * the searchable comments below list every used block ID/name and every
 * tile/entity NBT stub for manual restoration.
 *
 * Schematic mapping entries detected: 40
 * Recognized modded blocks:
 *   182 -> Thaumcraft:blockCosmeticSolid  metas=3  count=1
 *   183 -> Thaumcraft:blockCrystal  metas=4  count=1
 *   185 -> Thaumcraft:blockMetalDevice  metas=0  count=1
 *   186 -> Thaumcraft:blockWoodenDevice  metas=7  count=1
 *   189 -> Thaumcraft:blockTable  metas=15  count=1
 *   195 -> Thaumcraft:blockJar  metas=0  count=3
 *   196 -> Thaumcraft:blockCandle  metas=0  count=1
 *   198 -> Thaumcraft:blockAiry  metas=1,4  count=11
 *   206 -> Thaumcraft:blockStairsArcaneStone  metas=0,2,3  count=3
 *   207 -> Thaumcraft:blockStairsGreatwood  metas=4,5  count=12
 *   210 -> Thaumcraft:blockCosmeticSlabWood  metas=8  count=29
 */

import java.util.Random;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Samurai;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thaumcraft.common.config.ConfigBlocks;

public class HouseParanoidFortress extends WorldGenerator {

    /*
     * USED BLOCK IDS / NAMES
     * Format: numericId:meta -> registryName  count
     */
    // 0:0 -> minecraft:air  count=1096
    // 0:1 -> minecraft:air  count=1
    // 0:10 -> minecraft:air  count=1
    // 1:0 -> minecraft:stone  count=128
    // 2:0 -> minecraft:grass  count=52
    // 3:0 -> minecraft:dirt  count=10
    // 4:0 -> minecraft:cobblestone  count=14
    // 9:0 -> minecraft:water  count=30
    // 12:0 -> minecraft:sand  count=1
    // 13:0 -> minecraft:gravel  count=1
    // 17:4 -> minecraft:log  count=12
    // 26:0 -> minecraft:bed  count=1
    // 26:8 -> minecraft:bed  count=1
    // 44:3 -> minecraft:stone_slab  count=14
    // 44:5 -> minecraft:stone_slab  count=9
    // 47:0 -> minecraft:bookshelf  count=5
    // 54:5 -> minecraft:chest  count=1
    // 55:0 -> minecraft:redstone_wire  count=1
    // 61:4 -> minecraft:furnace  count=1
    // 65:2 -> minecraft:ladder  count=5
    // 66:0 -> minecraft:rail  count=1
    // 66:4 -> minecraft:rail  count=2
    // 66:5 -> minecraft:rail  count=2
    // 71:1 -> minecraft:iron_door  count=1
    // 71:8 -> minecraft:iron_door  count=1
    // 76:5 -> minecraft:redstone_torch  count=1
    // 77:3 -> minecraft:stone_button  count=1
    // 77:4 -> minecraft:stone_button  count=1
    // 83:0 -> minecraft:reeds  count=3
    // 96:4 -> minecraft:trapdoor  count=1
    // 98:0 -> minecraft:stonebrick  count=178
    // 101:0 -> minecraft:iron_bars  count=8
    // 103:0 -> minecraft:melon_block  count=4
    // 109:0 -> minecraft:stone_brick_stairs  count=6
    // 109:1 -> minecraft:stone_brick_stairs  count=8
    // 109:2 -> minecraft:stone_brick_stairs  count=8
    // 109:3 -> minecraft:stone_brick_stairs  count=10
    // 109:4 -> minecraft:stone_brick_stairs  count=10
    // 109:5 -> minecraft:stone_brick_stairs  count=10
    // 109:6 -> minecraft:stone_brick_stairs  count=13
    // 109:7 -> minecraft:stone_brick_stairs  count=13
    // 126:1 -> minecraft:wooden_slab  count=2
    // 126:9 -> minecraft:wooden_slab  count=2
    // 134:0 -> minecraft:spruce_stairs  count=1
    // 134:4 -> minecraft:spruce_stairs  count=4
    // 134:5 -> minecraft:spruce_stairs  count=4
    // 171:7 -> minecraft:carpet  count=6
    // 171:8 -> minecraft:carpet  count=6
    // 182:3 -> Thaumcraft:blockCosmeticSolid  count=1
    // 183:4 -> Thaumcraft:blockCrystal  count=1
    // 185:0 -> Thaumcraft:blockMetalDevice  count=1
    // 186:7 -> Thaumcraft:blockWoodenDevice  count=1
    // 189:15 -> Thaumcraft:blockTable  count=1
    // 195:0 -> Thaumcraft:blockJar  count=3
    // 196:0 -> Thaumcraft:blockCandle  count=1
    // 198:1 -> Thaumcraft:blockAiry  count=9
    // 198:4 -> Thaumcraft:blockAiry  count=2
    // 206:0 -> Thaumcraft:blockStairsArcaneStone  count=1
    // 206:2 -> Thaumcraft:blockStairsArcaneStone  count=1
    // 206:3 -> Thaumcraft:blockStairsArcaneStone  count=1
    // 207:4 -> Thaumcraft:blockStairsGreatwood  count=6
    // 207:5 -> Thaumcraft:blockStairsGreatwood  count=6
    // 210:8 -> Thaumcraft:blockCosmeticSlabWood  count=29

    /*
     * COMPACT PALETTE
     * BOXES stores only palette indexes; this list makes those indexes editable.
     * PALETTE below contains direct Block references, not runtime GameRegistry lookups.
     */
    // palette[0] = minecraft:grass meta 0
    // palette[1] = minecraft:water meta 0
    // palette[2] = minecraft:stonebrick meta 0
    // palette[3] = minecraft:gravel meta 0
    // palette[4] = minecraft:dirt meta 0
    // palette[5] = minecraft:stone meta 0
    // palette[6] = Thaumcraft:blockCosmeticSolid meta 3
    // palette[7] = minecraft:sand meta 0
    // palette[8] = Thaumcraft:blockAiry meta 1
    // palette[9] = minecraft:air meta 0
    // palette[10] = minecraft:stone_brick_stairs meta 2
    // palette[11] = minecraft:stone_brick_stairs meta 0
    // palette[12] = minecraft:iron_door meta 1
    // palette[13] = minecraft:stone_brick_stairs meta 1
    // palette[14] = minecraft:spruce_stairs meta 5
    // palette[15] = minecraft:stone_brick_stairs meta 3
    // palette[16] = Thaumcraft:blockAiry meta 4
    // palette[17] = Thaumcraft:blockTable meta 15
    // palette[18] = minecraft:reeds meta 0
    // palette[19] = minecraft:wooden_slab meta 9
    // palette[20] = minecraft:spruce_stairs meta 0
    // palette[21] = minecraft:carpet meta 8
    // palette[22] = minecraft:carpet meta 7
    // palette[23] = minecraft:redstone_torch meta 5
    // palette[24] = Thaumcraft:blockStairsArcaneStone meta 2
    // palette[25] = minecraft:chest meta 5
    // palette[26] = Thaumcraft:blockStairsArcaneStone meta 0
    // palette[27] = Thaumcraft:blockWoodenDevice meta 7
    // palette[28] = minecraft:redstone_wire meta 0
    // palette[29] = Thaumcraft:blockStairsArcaneStone meta 3
    // palette[30] = minecraft:air meta 10
    // palette[31] = minecraft:ladder meta 2
    // palette[32] = Thaumcraft:blockJar meta 0
    // palette[33] = Thaumcraft:blockMetalDevice meta 0
    // palette[34] = minecraft:iron_door meta 8
    // palette[35] = Thaumcraft:blockCandle meta 0
    // palette[36] = minecraft:bookshelf meta 0
    // palette[37] = Thaumcraft:blockCrystal meta 4
    // palette[38] = minecraft:bed meta 0
    // palette[39] = minecraft:bed meta 8
    // palette[40] = minecraft:stone_button meta 4
    // palette[41] = minecraft:stone_button meta 3
    // palette[42] = minecraft:spruce_stairs meta 4
    // palette[43] = minecraft:iron_bars meta 0
    // palette[44] = minecraft:stone_brick_stairs meta 7
    // palette[45] = minecraft:air meta 1
    // palette[46] = minecraft:wooden_slab meta 1
    // palette[47] = minecraft:stone_brick_stairs meta 5
    // palette[48] = minecraft:stone_brick_stairs meta 4
    // palette[49] = minecraft:stone_brick_stairs meta 6
    // palette[50] = minecraft:furnace meta 4
    // palette[51] = Thaumcraft:blockStairsGreatwood meta 5
    // palette[52] = Thaumcraft:blockCosmeticSlabWood meta 8
    // palette[53] = Thaumcraft:blockStairsGreatwood meta 4
    // palette[54] = minecraft:stone_slab meta 3
    // palette[55] = minecraft:cobblestone meta 0
    // palette[56] = minecraft:rail meta 5
    // palette[57] = minecraft:log meta 4
    // palette[58] = minecraft:rail meta 4
    // palette[59] = minecraft:trapdoor meta 4
    // palette[60] = minecraft:melon_block meta 0
    // palette[61] = minecraft:stone_slab meta 5
    // palette[62] = minecraft:rail meta 0

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
    private static Block MOD_THAUMCRAFT_BLOCKCOSMETICSLABWOOD = ConfigBlocks.blockSlabWood; // TODO provide Thaumcraft:blockCosmeticSlabWood
    private static Block MOD_THAUMCRAFT_BLOCKCOSMETICSOLID = ConfigBlocks.blockCosmeticSolid; // TODO provide Thaumcraft:blockCosmeticSolid
    private static Block MOD_THAUMCRAFT_BLOCKCRYSTAL = ConfigBlocks.blockCrystal; // TODO provide Thaumcraft:blockCrystal
    private static Block MOD_THAUMCRAFT_BLOCKJAR = ConfigBlocks.blockJar; // TODO provide Thaumcraft:blockJar
    private static Block MOD_THAUMCRAFT_BLOCKMETALDEVICE = ConfigBlocks.blockMetalDevice; // TODO provide Thaumcraft:blockMetalDevice
    private static Block MOD_THAUMCRAFT_BLOCKSTAIRSARCANESTONE = ConfigBlocks.blockStairsArcaneStone; // TODO provide Thaumcraft:blockStairsArcaneStone
    private static Block MOD_THAUMCRAFT_BLOCKSTAIRSGREATWOOD = ConfigBlocks.blockStairsGreatwood; // TODO provide Thaumcraft:blockStairsGreatwood
    private static Block MOD_THAUMCRAFT_BLOCKTABLE = ConfigBlocks.blockTable; // TODO provide Thaumcraft:blockTable
    private static Block MOD_THAUMCRAFT_BLOCKWOODENDEVICE = ConfigBlocks.blockWoodenDevice; // TODO provide Thaumcraft:blockWoodenDevice



    private static final Block[] PALETTE = new Block[] {
            Blocks.grass, // palette[0] = minecraft:grass meta 0
            Blocks.water, // palette[1] = minecraft:water meta 0
            Blocks.stonebrick, // palette[2] = minecraft:stonebrick meta 0
            Blocks.gravel, // palette[3] = minecraft:gravel meta 0
            Blocks.dirt, // palette[4] = minecraft:dirt meta 0
            Blocks.stone, // palette[5] = minecraft:stone meta 0
            MOD_THAUMCRAFT_BLOCKCOSMETICSOLID, // palette[6] = Thaumcraft:blockCosmeticSolid meta 3
            Blocks.sand, // palette[7] = minecraft:sand meta 0
            MOD_THAUMCRAFT_BLOCKAIRY, // palette[8] = Thaumcraft:blockAiry meta 1
            Blocks.air, // palette[9] = minecraft:air meta 0
            Blocks.stone_brick_stairs, // palette[10] = minecraft:stone_brick_stairs meta 2
            Blocks.stone_brick_stairs, // palette[11] = minecraft:stone_brick_stairs meta 0
            Blocks.iron_door, // palette[12] = minecraft:iron_door meta 1
            Blocks.stone_brick_stairs, // palette[13] = minecraft:stone_brick_stairs meta 1
            Blocks.spruce_stairs, // palette[14] = minecraft:spruce_stairs meta 5
            Blocks.stone_brick_stairs, // palette[15] = minecraft:stone_brick_stairs meta 3
            MOD_THAUMCRAFT_BLOCKAIRY, // palette[16] = Thaumcraft:blockAiry meta 4
            MOD_THAUMCRAFT_BLOCKTABLE, // palette[17] = Thaumcraft:blockTable meta 15
            Blocks.reeds, // palette[18] = minecraft:reeds meta 0
            Blocks.wooden_slab, // palette[19] = minecraft:wooden_slab meta 9
            Blocks.spruce_stairs, // palette[20] = minecraft:spruce_stairs meta 0
            Blocks.carpet, // palette[21] = minecraft:carpet meta 8
            Blocks.carpet, // palette[22] = minecraft:carpet meta 7
            Blocks.redstone_torch, // palette[23] = minecraft:redstone_torch meta 5
            MOD_THAUMCRAFT_BLOCKSTAIRSARCANESTONE, // palette[24] = Thaumcraft:blockStairsArcaneStone meta 2
            Blocks.chest, // palette[25] = minecraft:chest meta 5
            MOD_THAUMCRAFT_BLOCKSTAIRSARCANESTONE, // palette[26] = Thaumcraft:blockStairsArcaneStone meta 0
            MOD_THAUMCRAFT_BLOCKWOODENDEVICE, // palette[27] = Thaumcraft:blockWoodenDevice meta 7
            Blocks.redstone_wire, // palette[28] = minecraft:redstone_wire meta 0
            MOD_THAUMCRAFT_BLOCKSTAIRSARCANESTONE, // palette[29] = Thaumcraft:blockStairsArcaneStone meta 3
            Blocks.air, // palette[30] = minecraft:air meta 10
            Blocks.ladder, // palette[31] = minecraft:ladder meta 2
            MOD_THAUMCRAFT_BLOCKJAR, // palette[32] = Thaumcraft:blockJar meta 0
            MOD_THAUMCRAFT_BLOCKMETALDEVICE, // palette[33] = Thaumcraft:blockMetalDevice meta 0
            Blocks.iron_door, // palette[34] = minecraft:iron_door meta 8
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[35] = Thaumcraft:blockCandle meta 0
            Blocks.bookshelf, // palette[36] = minecraft:bookshelf meta 0
            MOD_THAUMCRAFT_BLOCKCRYSTAL, // palette[37] = Thaumcraft:blockCrystal meta 4
            Blocks.bed, // palette[38] = minecraft:bed meta 0
            Blocks.bed, // palette[39] = minecraft:bed meta 8
            Blocks.stone_button, // palette[40] = minecraft:stone_button meta 4
            Blocks.stone_button, // palette[41] = minecraft:stone_button meta 3
            Blocks.spruce_stairs, // palette[42] = minecraft:spruce_stairs meta 4
            Blocks.iron_bars, // palette[43] = minecraft:iron_bars meta 0
            Blocks.stone_brick_stairs, // palette[44] = minecraft:stone_brick_stairs meta 7
            Blocks.air, // palette[45] = minecraft:air meta 1
            Blocks.wooden_slab, // palette[46] = minecraft:wooden_slab meta 1
            Blocks.stone_brick_stairs, // palette[47] = minecraft:stone_brick_stairs meta 5
            Blocks.stone_brick_stairs, // palette[48] = minecraft:stone_brick_stairs meta 4
            Blocks.stone_brick_stairs, // palette[49] = minecraft:stone_brick_stairs meta 6
            Blocks.furnace, // palette[50] = minecraft:furnace meta 4
            MOD_THAUMCRAFT_BLOCKSTAIRSGREATWOOD, // palette[51] = Thaumcraft:blockStairsGreatwood meta 5
            MOD_THAUMCRAFT_BLOCKCOSMETICSLABWOOD, // palette[52] = Thaumcraft:blockCosmeticSlabWood meta 8
            MOD_THAUMCRAFT_BLOCKSTAIRSGREATWOOD, // palette[53] = Thaumcraft:blockStairsGreatwood meta 4
            Blocks.stone_slab, // palette[54] = minecraft:stone_slab meta 3
            Blocks.cobblestone, // palette[55] = minecraft:cobblestone meta 0
            Blocks.rail, // palette[56] = minecraft:rail meta 5
            Blocks.log, // palette[57] = minecraft:log meta 4
            Blocks.rail, // palette[58] = minecraft:rail meta 4
            Blocks.trapdoor, // palette[59] = minecraft:trapdoor meta 4
            Blocks.melon_block, // palette[60] = minecraft:melon_block meta 0
            Blocks.stone_slab, // palette[61] = minecraft:stone_slab meta 5
            Blocks.rail // palette[62] = minecraft:rail meta 0
    };

    private static final byte[] META = new byte[] {
            (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0, (byte)1, (byte)0, (byte)2, (byte)0, (byte)1, (byte)1, (byte)5, (byte)3,
            (byte)4, (byte)15, (byte)0, (byte)9, (byte)0, (byte)8, (byte)7, (byte)5, (byte)2, (byte)5, (byte)0, (byte)7, (byte)0, (byte)3, (byte)10, (byte)2,
            (byte)0, (byte)0, (byte)8, (byte)0, (byte)0, (byte)4, (byte)0, (byte)8, (byte)4, (byte)3, (byte)4, (byte)0, (byte)7, (byte)1, (byte)1, (byte)5,
            (byte)4, (byte)6, (byte)4, (byte)5, (byte)8, (byte)4, (byte)3, (byte)0, (byte)5, (byte)4, (byte)4, (byte)4, (byte)0, (byte)5, (byte)0
    };

    // x, y, z, sizeX, sizeY, sizeZ, palette index
    private static final short[] BOXES = new short[] {
            (short)0, (short)0, (short)0, (short)15, (short)1, (short)1, (short)0, (short)0, (short)0, (short)1, (short)1, (short)1, (short)12, (short)0, (short)1, (short)0,
            (short)1, (short)1, (short)1, (short)1, (short)1, (short)2, (short)0, (short)1, (short)1, (short)1, (short)1, (short)2, (short)3, (short)0, (short)1, (short)3,
            (short)1, (short)1, (short)1, (short)6, (short)0, (short)1, (short)1, (short)1, (short)1, (short)2, (short)7, (short)0, (short)1, (short)1, (short)1, (short)1,
            (short)3, (short)8, (short)0, (short)1, (short)1, (short)1, (short)1, (short)2, (short)9, (short)0, (short)1, (short)3, (short)1, (short)1, (short)1, (short)12,
            (short)0, (short)1, (short)1, (short)1, (short)1, (short)2, (short)13, (short)0, (short)1, (short)1, (short)1, (short)1, (short)1, (short)14, (short)0, (short)1,
            (short)1, (short)1, (short)12, (short)0, (short)1, (short)0, (short)2, (short)1, (short)1, (short)1, (short)2, (short)2, (short)0, (short)2, (short)1, (short)1,
            (short)1, (short)4, (short)3, (short)0, (short)2, (short)3, (short)1, (short)1, (short)2, (short)6, (short)0, (short)2, (short)1, (short)1, (short)1, (short)4,
            (short)7, (short)0, (short)2, (short)1, (short)1, (short)1, (short)2, (short)8, (short)0, (short)2, (short)1, (short)1, (short)1, (short)4, (short)9, (short)0,
            (short)2, (short)3, (short)1, (short)1, (short)2, (short)12, (short)0, (short)2, (short)1, (short)1, (short)1, (short)4, (short)13, (short)0, (short)2, (short)1,
            (short)1, (short)1, (short)2, (short)1, (short)0, (short)3, (short)1, (short)1, (short)3, (short)1, (short)2, (short)0, (short)3, (short)1, (short)1, (short)3,
            (short)2, (short)3, (short)0, (short)3, (short)4, (short)1, (short)7, (short)5, (short)7, (short)0, (short)3, (short)1, (short)1, (short)1, (short)6, (short)8,
            (short)0, (short)3, (short)2, (short)1, (short)7, (short)5, (short)10, (short)0, (short)3, (short)1, (short)1, (short)1, (short)7, (short)11, (short)0, (short)3,
            (short)1, (short)1, (short)1, (short)1, (short)12, (short)0, (short)3, (short)1, (short)1, (short)3, (short)2, (short)13, (short)0, (short)3, (short)1, (short)1,
            (short)3, (short)1, (short)7, (short)0, (short)4, (short)1, (short)1, (short)6, (short)5, (short)10, (short)0, (short)4, (short)2, (short)1, (short)5, (short)5,
            (short)1, (short)0, (short)6, (short)1, (short)1, (short)1, (short)2, (short)2, (short)0, (short)6, (short)1, (short)1, (short)1, (short)4, (short)12, (short)0,
            (short)6, (short)1, (short)1, (short)1, (short)4, (short)13, (short)0, (short)6, (short)1, (short)1, (short)1, (short)2, (short)1, (short)0, (short)7, (short)1,
            (short)1, (short)3, (short)1, (short)2, (short)0, (short)7, (short)1, (short)1, (short)3, (short)2, (short)12, (short)0, (short)7, (short)1, (short)1, (short)3,
            (short)2, (short)13, (short)0, (short)7, (short)1, (short)1, (short)3, (short)1, (short)10, (short)0, (short)9, (short)1, (short)1, (short)1, (short)5, (short)11,
            (short)0, (short)9, (short)1, (short)1, (short)1, (short)8, (short)1, (short)0, (short)10, (short)1, (short)1, (short)1, (short)2, (short)2, (short)0, (short)10,
            (short)1, (short)1, (short)1, (short)4, (short)3, (short)0, (short)10, (short)3, (short)1, (short)1, (short)2, (short)6, (short)0, (short)10, (short)1, (short)1,
            (short)1, (short)4, (short)7, (short)0, (short)10, (short)1, (short)1, (short)1, (short)2, (short)8, (short)0, (short)10, (short)1, (short)1, (short)1, (short)4,
            (short)9, (short)0, (short)10, (short)3, (short)1, (short)1, (short)2, (short)12, (short)0, (short)10, (short)1, (short)1, (short)1, (short)4, (short)13, (short)0,
            (short)10, (short)1, (short)1, (short)1, (short)2, (short)1, (short)0, (short)11, (short)1, (short)1, (short)1, (short)1, (short)2, (short)0, (short)11, (short)1,
            (short)1, (short)1, (short)2, (short)3, (short)0, (short)11, (short)3, (short)1, (short)1, (short)1, (short)6, (short)0, (short)11, (short)1, (short)1, (short)1,
            (short)2, (short)7, (short)0, (short)11, (short)1, (short)1, (short)1, (short)1, (short)8, (short)0, (short)11, (short)1, (short)1, (short)1, (short)2, (short)9,
            (short)0, (short)11, (short)3, (short)1, (short)1, (short)1, (short)12, (short)0, (short)11, (short)1, (short)1, (short)1, (short)2, (short)13, (short)0, (short)11,
            (short)1, (short)1, (short)1, (short)1, (short)1, (short)0, (short)12, (short)13, (short)1, (short)1, (short)0, (short)0, (short)1, (short)0, (short)15, (short)8,
            (short)1, (short)9, (short)0, (short)1, (short)1, (short)2, (short)8, (short)1, (short)9, (short)2, (short)1, (short)1, (short)1, (short)1, (short)1, (short)10,
            (short)3, (short)1, (short)1, (short)3, (short)5, (short)1, (short)9, (short)6, (short)1, (short)1, (short)1, (short)1, (short)1, (short)10, (short)7, (short)1,
            (short)1, (short)1, (short)2, (short)1, (short)9, (short)8, (short)1, (short)1, (short)1, (short)1, (short)1, (short)10, (short)9, (short)1, (short)1, (short)3,
            (short)5, (short)1, (short)9, (short)12, (short)1, (short)1, (short)1, (short)1, (short)1, (short)10, (short)13, (short)1, (short)1, (short)2, (short)8, (short)1,
            (short)9, (short)0, (short)1, (short)2, (short)1, (short)8, (short)11, (short)9, (short)1, (short)1, (short)2, (short)1, (short)1, (short)1, (short)11, (short)2,
            (short)1, (short)2, (short)1, (short)5, (short)1, (short)2, (short)3, (short)1, (short)2, (short)3, (short)1, (short)1, (short)5, (short)6, (short)1, (short)2,
            (short)1, (short)5, (short)1, (short)2, (short)7, (short)1, (short)2, (short)1, (short)1, (short)1, (short)12, (short)8, (short)1, (short)2, (short)1, (short)5,
            (short)1, (short)2, (short)9, (short)1, (short)2, (short)3, (short)1, (short)1, (short)5, (short)12, (short)1, (short)2, (short)1, (short)5, (short)1, (short)2,
            (short)13, (short)1, (short)2, (short)1, (short)1, (short)1, (short)13, (short)14, (short)1, (short)2, (short)1, (short)8, (short)11, (short)9, (short)1, (short)1,
            (short)3, (short)1, (short)5, (short)3, (short)9, (short)2, (short)1, (short)3, (short)1, (short)1, (short)3, (short)5, (short)3, (short)1, (short)3, (short)1,
            (short)1, (short)1, (short)14, (short)4, (short)1, (short)3, (short)2, (short)4, (short)1, (short)9, (short)6, (short)1, (short)3, (short)1, (short)1, (short)1,
            (short)15, (short)7, (short)1, (short)3, (short)1, (short)2, (short)1, (short)16, (short)8, (short)1, (short)3, (short)1, (short)1, (short)1, (short)15, (short)9,
            (short)1, (short)3, (short)1, (short)1, (short)1, (short)17, (short)10, (short)1, (short)3, (short)1, (short)3, (short)1, (short)18, (short)11, (short)1, (short)3,
            (short)1, (short)2, (short)2, (short)9, (short)12, (short)1, (short)3, (short)1, (short)1, (short)3, (short)5, (short)13, (short)1, (short)3, (short)1, (short)5,
            (short)3, (short)9, (short)3, (short)1, (short)4, (short)1, (short)1, (short)1, (short)19, (short)4, (short)1, (short)4, (short)1, (short)1, (short)1, (short)20,
            (short)5, (short)1, (short)4, (short)6, (short)4, (short)1, (short)9, (short)3, (short)1, (short)5, (short)1, (short)1, (short)1, (short)14, (short)4, (short)1,
            (short)5, (short)2, (short)4, (short)3, (short)9, (short)6, (short)1, (short)5, (short)1, (short)1, (short)1, (short)21, (short)7, (short)1, (short)5, (short)1,
            (short)1, (short)1, (short)22, (short)8, (short)1, (short)5, (short)1, (short)1, (short)1, (short)21, (short)9, (short)1, (short)5, (short)1, (short)3, (short)5,
            (short)9, (short)10, (short)1, (short)5, (short)1, (short)1, (short)1, (short)23, (short)11, (short)1, (short)5, (short)1, (short)1, (short)1, (short)24, (short)1,
            (short)1, (short)6, (short)1, (short)1, (short)1, (short)11, (short)2, (short)1, (short)6, (short)1, (short)5, (short)1, (short)2, (short)3, (short)1, (short)6,
            (short)1, (short)1, (short)1, (short)25, (short)6, (short)1, (short)6, (short)1, (short)1, (short)2, (short)22, (short)7, (short)1, (short)6, (short)1, (short)1,
            (short)2, (short)21, (short)8, (short)1, (short)6, (short)1, (short)1, (short)2, (short)22, (short)10, (short)1, (short)6, (short)1, (short)1, (short)1, (short)26,
            (short)11, (short)1, (short)6, (short)1, (short)1, (short)1, (short)27, (short)12, (short)1, (short)6, (short)1, (short)5, (short)1, (short)2, (short)13, (short)1,
            (short)6, (short)1, (short)1, (short)1, (short)13, (short)1, (short)1, (short)7, (short)1, (short)5, (short)3, (short)9, (short)2, (short)1, (short)7, (short)1,
            (short)1, (short)3, (short)5, (short)3, (short)1, (short)7, (short)1, (short)1, (short)1, (short)14, (short)10, (short)1, (short)7, (short)1, (short)1, (short)1,
            (short)28, (short)11, (short)1, (short)7, (short)1, (short)1, (short)1, (short)29, (short)12, (short)1, (short)7, (short)1, (short)1, (short)3, (short)5, (short)13,
            (short)1, (short)7, (short)1, (short)5, (short)3, (short)9, (short)3, (short)1, (short)8, (short)1, (short)1, (short)2, (short)2, (short)4, (short)1, (short)8,
            (short)1, (short)1, (short)2, (short)13, (short)5, (short)1, (short)8, (short)1, (short)4, (short)2, (short)9, (short)6, (short)1, (short)8, (short)1, (short)1,
            (short)1, (short)21, (short)7, (short)1, (short)8, (short)1, (short)1, (short)1, (short)22, (short)8, (short)1, (short)8, (short)1, (short)1, (short)1, (short)21,
            (short)10, (short)1, (short)8, (short)1, (short)4, (short)1, (short)9, (short)11, (short)1, (short)8, (short)1, (short)1, (short)1, (short)30, (short)6, (short)1,
            (short)9, (short)1, (short)3, (short)1, (short)9, (short)7, (short)1, (short)9, (short)1, (short)5, (short)1, (short)31, (short)8, (short)1, (short)9, (short)1,
            (short)3, (short)1, (short)9, (short)10, (short)1, (short)9, (short)1, (short)1, (short)1, (short)32, (short)11, (short)1, (short)9, (short)1, (short)1, (short)1,
            (short)33, (short)1, (short)1, (short)10, (short)1, (short)1, (short)1, (short)11, (short)2, (short)1, (short)10, (short)1, (short)5, (short)1, (short)2, (short)3,
            (short)1, (short)10, (short)3, (short)1, (short)1, (short)5, (short)6, (short)1, (short)10, (short)1, (short)5, (short)1, (short)2, (short)7, (short)1, (short)10,
            (short)1, (short)4, (short)1, (short)5, (short)8, (short)1, (short)10, (short)1, (short)5, (short)1, (short)2, (short)9, (short)1, (short)10, (short)3, (short)1,
            (short)1, (short)5, (short)12, (short)1, (short)10, (short)1, (short)5, (short)1, (short)2, (short)13, (short)1, (short)10, (short)1, (short)1, (short)1, (short)13,
            (short)1, (short)1, (short)11, (short)1, (short)8, (short)2, (short)9, (short)2, (short)1, (short)11, (short)1, (short)1, (short)1, (short)15, (short)3, (short)1,
            (short)11, (short)3, (short)5, (short)2, (short)9, (short)6, (short)1, (short)11, (short)1, (short)1, (short)1, (short)15, (short)7, (short)1, (short)11, (short)1,
            (short)5, (short)2, (short)9, (short)8, (short)1, (short)11, (short)1, (short)1, (short)1, (short)15, (short)9, (short)1, (short)11, (short)3, (short)5, (short)2,
            (short)9, (short)12, (short)1, (short)11, (short)1, (short)1, (short)1, (short)15, (short)13, (short)1, (short)11, (short)1, (short)8, (short)2, (short)9, (short)2,
            (short)1, (short)12, (short)1, (short)8, (short)1, (short)9, (short)6, (short)1, (short)12, (short)1, (short)8, (short)1, (short)9, (short)8, (short)1, (short)12,
            (short)1, (short)8, (short)1, (short)9, (short)12, (short)1, (short)12, (short)1, (short)8, (short)1, (short)9, (short)2, (short)2, (short)1, (short)1, (short)3,
            (short)1, (short)9, (short)6, (short)2, (short)1, (short)1, (short)3, (short)1, (short)9, (short)8, (short)2, (short)1, (short)1, (short)3, (short)1, (short)9,
            (short)12, (short)2, (short)1, (short)1, (short)3, (short)1, (short)9, (short)1, (short)2, (short)2, (short)1, (short)3, (short)1, (short)9, (short)3, (short)2,
            (short)2, (short)3, (short)1, (short)1, (short)2, (short)7, (short)2, (short)2, (short)1, (short)1, (short)1, (short)34, (short)9, (short)2, (short)2, (short)3,
            (short)1, (short)1, (short)2, (short)13, (short)2, (short)2, (short)1, (short)3, (short)1, (short)9, (short)2, (short)2, (short)3, (short)1, (short)1, (short)3,
            (short)2, (short)3, (short)2, (short)3, (short)1, (short)1, (short)1, (short)35, (short)6, (short)2, (short)3, (short)1, (short)2, (short)1, (short)9, (short)8,
            (short)2, (short)3, (short)2, (short)2, (short)1, (short)9, (short)12, (short)2, (short)3, (short)1, (short)1, (short)3, (short)2, (short)3, (short)2, (short)4,
            (short)2, (short)1, (short)1, (short)9, (short)3, (short)2, (short)5, (short)1, (short)2, (short)1, (short)36, (short)6, (short)2, (short)5, (short)3, (short)3,
            (short)4, (short)9, (short)10, (short)2, (short)5, (short)2, (short)3, (short)1, (short)9, (short)1, (short)2, (short)6, (short)1, (short)3, (short)1, (short)9,
            (short)3, (short)2, (short)6, (short)1, (short)2, (short)1, (short)9, (short)10, (short)2, (short)6, (short)1, (short)3, (short)2, (short)9, (short)11, (short)2,
            (short)6, (short)1, (short)1, (short)1, (short)37, (short)13, (short)2, (short)6, (short)1, (short)3, (short)1, (short)9, (short)2, (short)2, (short)7, (short)1,
            (short)1, (short)3, (short)2, (short)3, (short)2, (short)7, (short)1, (short)2, (short)1, (short)36, (short)11, (short)2, (short)7, (short)1, (short)1, (short)3,
            (short)9, (short)12, (short)2, (short)7, (short)1, (short)1, (short)3, (short)2, (short)3, (short)2, (short)8, (short)1, (short)1, (short)1, (short)38, (short)4,
            (short)2, (short)8, (short)1, (short)3, (short)2, (short)9, (short)3, (short)2, (short)9, (short)1, (short)1, (short)1, (short)39, (short)10, (short)2, (short)9,
            (short)1, (short)3, (short)1, (short)9, (short)1, (short)2, (short)10, (short)1, (short)3, (short)1, (short)9, (short)3, (short)2, (short)10, (short)3, (short)1,
            (short)1, (short)2, (short)9, (short)2, (short)10, (short)3, (short)1, (short)1, (short)2, (short)13, (short)2, (short)10, (short)1, (short)3, (short)1, (short)9,
            (short)2, (short)2, (short)11, (short)1, (short)3, (short)1, (short)9, (short)6, (short)2, (short)11, (short)1, (short)3, (short)1, (short)9, (short)8, (short)2,
            (short)11, (short)1, (short)3, (short)1, (short)9, (short)12, (short)2, (short)11, (short)1, (short)3, (short)1, (short)9, (short)7, (short)3, (short)1, (short)1,
            (short)1, (short)1, (short)40, (short)3, (short)3, (short)2, (short)3, (short)1, (short)1, (short)5, (short)7, (short)3, (short)2, (short)1, (short)1, (short)1,
            (short)2, (short)9, (short)3, (short)2, (short)3, (short)1, (short)1, (short)5, (short)2, (short)3, (short)3, (short)1, (short)1, (short)3, (short)5, (short)3,
            (short)3, (short)3, (short)1, (short)1, (short)1, (short)14, (short)7, (short)3, (short)3, (short)1, (short)1, (short)1, (short)41, (short)11, (short)3, (short)3,
            (short)1, (short)1, (short)2, (short)42, (short)12, (short)3, (short)3, (short)1, (short)1, (short)3, (short)5, (short)3, (short)3, (short)4, (short)1, (short)1,
            (short)1, (short)19, (short)4, (short)3, (short)4, (short)1, (short)2, (short)1, (short)9, (short)11, (short)3, (short)6, (short)1, (short)1, (short)2, (short)9,
            (short)2, (short)3, (short)7, (short)1, (short)1, (short)3, (short)5, (short)12, (short)3, (short)7, (short)1, (short)1, (short)3, (short)5, (short)3, (short)3,
            (short)8, (short)1, (short)2, (short)2, (short)9, (short)11, (short)3, (short)8, (short)1, (short)1, (short)2, (short)42, (short)3, (short)3, (short)10, (short)3,
            (short)1, (short)1, (short)5, (short)9, (short)3, (short)10, (short)3, (short)1, (short)1, (short)5, (short)7, (short)4, (short)1, (short)1, (short)2, (short)1,
            (short)9, (short)3, (short)4, (short)2, (short)1, (short)1, (short)1, (short)5, (short)4, (short)4, (short)2, (short)1, (short)1, (short)1, (short)43, (short)5,
            (short)4, (short)2, (short)1, (short)1, (short)1, (short)5, (short)7, (short)4, (short)2, (short)1, (short)1, (short)1, (short)5, (short)9, (short)4, (short)2,
            (short)1, (short)1, (short)1, (short)5, (short)10, (short)4, (short)2, (short)1, (short)1, (short)1, (short)43, (short)11, (short)4, (short)2, (short)1, (short)1,
            (short)1, (short)5, (short)2, (short)4, (short)3, (short)1, (short)1, (short)1, (short)5, (short)3, (short)4, (short)3, (short)1, (short)1, (short)1, (short)36,
            (short)6, (short)4, (short)3, (short)1, (short)1, (short)1, (short)44, (short)7, (short)4, (short)3, (short)1, (short)1, (short)1, (short)45, (short)8, (short)4,
            (short)3, (short)1, (short)1, (short)1, (short)44, (short)9, (short)4, (short)3, (short)2, (short)1, (short)1, (short)9, (short)11, (short)4, (short)3, (short)1,
            (short)1, (short)1, (short)8, (short)12, (short)4, (short)3, (short)1, (short)1, (short)1, (short)5, (short)2, (short)4, (short)4, (short)1, (short)1, (short)1,
            (short)43, (short)3, (short)4, (short)4, (short)1, (short)1, (short)1, (short)9, (short)11, (short)4, (short)4, (short)1, (short)1, (short)1, (short)32, (short)12,
            (short)4, (short)4, (short)1, (short)1, (short)1, (short)43, (short)2, (short)4, (short)5, (short)1, (short)1, (short)1, (short)5, (short)3, (short)4, (short)5,
            (short)1, (short)1, (short)1, (short)46, (short)9, (short)4, (short)5, (short)1, (short)1, (short)4, (short)9, (short)12, (short)4, (short)5, (short)1, (short)1,
            (short)1, (short)5, (short)3, (short)4, (short)6, (short)1, (short)1, (short)1, (short)47, (short)11, (short)4, (short)6, (short)1, (short)1, (short)1, (short)48,
            (short)2, (short)4, (short)7, (short)1, (short)1, (short)1, (short)5, (short)3, (short)4, (short)7, (short)1, (short)1, (short)1, (short)46, (short)11, (short)4,
            (short)7, (short)1, (short)1, (short)1, (short)9, (short)12, (short)4, (short)7, (short)1, (short)1, (short)1, (short)5, (short)2, (short)4, (short)8, (short)1,
            (short)1, (short)1, (short)43, (short)11, (short)4, (short)8, (short)1, (short)1, (short)1, (short)32, (short)12, (short)4, (short)8, (short)1, (short)1, (short)1,
            (short)43, (short)2, (short)4, (short)9, (short)1, (short)1, (short)1, (short)5, (short)6, (short)4, (short)9, (short)1, (short)1, (short)1, (short)49, (short)8,
            (short)4, (short)9, (short)1, (short)1, (short)1, (short)49, (short)9, (short)4, (short)9, (short)1, (short)1, (short)1, (short)8, (short)11, (short)4, (short)9,
            (short)1, (short)1, (short)1, (short)50, (short)12, (short)4, (short)9, (short)1, (short)1, (short)1, (short)5, (short)3, (short)4, (short)10, (short)1, (short)1,
            (short)1, (short)5, (short)4, (short)4, (short)10, (short)1, (short)1, (short)1, (short)43, (short)5, (short)4, (short)10, (short)1, (short)1, (short)1, (short)5,
            (short)9, (short)4, (short)10, (short)1, (short)1, (short)1, (short)5, (short)10, (short)4, (short)10, (short)1, (short)1, (short)1, (short)43, (short)11, (short)4,
            (short)10, (short)1, (short)1, (short)1, (short)5, (short)2, (short)5, (short)1, (short)1, (short)1, (short)1, (short)49, (short)6, (short)5, (short)1, (short)1,
            (short)1, (short)1, (short)49, (short)8, (short)5, (short)1, (short)1, (short)1, (short)1, (short)49, (short)12, (short)5, (short)1, (short)1, (short)1, (short)1,
            (short)49, (short)1, (short)5, (short)2, (short)1, (short)1, (short)1, (short)48, (short)3, (short)5, (short)2, (short)3, (short)1, (short)1, (short)2, (short)7,
            (short)5, (short)2, (short)1, (short)1, (short)1, (short)2, (short)9, (short)5, (short)2, (short)3, (short)1, (short)1, (short)2, (short)13, (short)5, (short)2,
            (short)1, (short)1, (short)1, (short)47, (short)2, (short)5, (short)3, (short)1, (short)1, (short)3, (short)2, (short)3, (short)5, (short)3, (short)1, (short)1,
            (short)3, (short)51, (short)4, (short)5, (short)3, (short)2, (short)1, (short)3, (short)52, (short)6, (short)5, (short)3, (short)1, (short)1, (short)7, (short)2,
            (short)7, (short)5, (short)3, (short)1, (short)1, (short)3, (short)52, (short)8, (short)5, (short)3, (short)1, (short)1, (short)7, (short)2, (short)9, (short)5,
            (short)3, (short)2, (short)1, (short)3, (short)52, (short)11, (short)5, (short)3, (short)1, (short)1, (short)3, (short)53, (short)12, (short)5, (short)3, (short)1,
            (short)1, (short)3, (short)2, (short)1, (short)5, (short)6, (short)1, (short)1, (short)1, (short)48, (short)3, (short)5, (short)6, (short)3, (short)1, (short)1,
            (short)2, (short)7, (short)5, (short)6, (short)1, (short)1, (short)1, (short)2, (short)9, (short)5, (short)6, (short)3, (short)1, (short)1, (short)2, (short)13,
            (short)5, (short)6, (short)1, (short)1, (short)1, (short)47, (short)2, (short)5, (short)7, (short)1, (short)1, (short)3, (short)2, (short)3, (short)5, (short)7,
            (short)1, (short)1, (short)3, (short)51, (short)4, (short)5, (short)7, (short)2, (short)1, (short)3, (short)52, (short)7, (short)5, (short)7, (short)1, (short)1,
            (short)2, (short)52, (short)9, (short)5, (short)7, (short)2, (short)1, (short)3, (short)52, (short)11, (short)5, (short)7, (short)1, (short)1, (short)3, (short)53,
            (short)12, (short)5, (short)7, (short)1, (short)1, (short)3, (short)2, (short)1, (short)5, (short)10, (short)1, (short)1, (short)1, (short)48, (short)3, (short)5,
            (short)10, (short)3, (short)1, (short)1, (short)2, (short)7, (short)5, (short)10, (short)1, (short)1, (short)1, (short)2, (short)9, (short)5, (short)10, (short)3,
            (short)1, (short)1, (short)2, (short)13, (short)5, (short)10, (short)1, (short)1, (short)1, (short)47, (short)2, (short)5, (short)11, (short)1, (short)1, (short)1,
            (short)44, (short)6, (short)5, (short)11, (short)1, (short)1, (short)1, (short)44, (short)8, (short)5, (short)11, (short)1, (short)1, (short)1, (short)44, (short)12,
            (short)5, (short)11, (short)1, (short)1, (short)1, (short)44, (short)2, (short)6, (short)1, (short)1, (short)1, (short)1, (short)2, (short)3, (short)6, (short)1,
            (short)3, (short)1, (short)1, (short)44, (short)6, (short)6, (short)1, (short)1, (short)1, (short)1, (short)2, (short)7, (short)6, (short)1, (short)1, (short)1,
            (short)1, (short)44, (short)8, (short)6, (short)1, (short)1, (short)1, (short)1, (short)2, (short)9, (short)6, (short)1, (short)3, (short)1, (short)1, (short)44,
            (short)12, (short)6, (short)1, (short)1, (short)1, (short)1, (short)2, (short)1, (short)6, (short)2, (short)1, (short)1, (short)1, (short)2, (short)2, (short)6,
            (short)2, (short)1, (short)3, (short)1, (short)9, (short)3, (short)6, (short)2, (short)1, (short)1, (short)1, (short)54, (short)4, (short)6, (short)2, (short)1,
            (short)1, (short)5, (short)55, (short)5, (short)6, (short)2, (short)1, (short)1, (short)1, (short)54, (short)6, (short)6, (short)2, (short)4, (short)3, (short)1,
            (short)9, (short)10, (short)6, (short)2, (short)1, (short)1, (short)1, (short)56, (short)11, (short)6, (short)2, (short)2, (short)3, (short)1, (short)9, (short)13,
            (short)6, (short)2, (short)1, (short)1, (short)1, (short)2, (short)1, (short)6, (short)3, (short)1, (short)1, (short)3, (short)47, (short)2, (short)6, (short)3,
            (short)1, (short)1, (short)1, (short)54, (short)3, (short)6, (short)3, (short)1, (short)1, (short)3, (short)55, (short)5, (short)6, (short)3, (short)1, (short)1,
            (short)3, (short)55, (short)6, (short)6, (short)3, (short)1, (short)1, (short)1, (short)54, (short)7, (short)6, (short)3, (short)2, (short)3, (short)1, (short)9,
            (short)9, (short)6, (short)3, (short)3, (short)1, (short)3, (short)57, (short)12, (short)6, (short)3, (short)1, (short)3, (short)6, (short)9, (short)13, (short)6,
            (short)3, (short)1, (short)1, (short)3, (short)48, (short)2, (short)6, (short)4, (short)1, (short)1, (short)1, (short)55, (short)6, (short)6, (short)4, (short)1,
            (short)1, (short)1, (short)55, (short)7, (short)6, (short)4, (short)1, (short)1, (short)1, (short)54, (short)8, (short)6, (short)4, (short)1, (short)3, (short)7,
            (short)9, (short)2, (short)6, (short)5, (short)1, (short)1, (short)1, (short)54, (short)6, (short)6, (short)5, (short)1, (short)1, (short)1, (short)54, (short)7,
            (short)6, (short)5, (short)1, (short)3, (short)4, (short)9, (short)1, (short)6, (short)6, (short)1, (short)1, (short)1, (short)2, (short)2, (short)6, (short)6,
            (short)1, (short)3, (short)5, (short)9, (short)3, (short)6, (short)6, (short)1, (short)1, (short)1, (short)54, (short)5, (short)6, (short)6, (short)1, (short)1,
            (short)1, (short)54, (short)6, (short)6, (short)6, (short)1, (short)3, (short)5, (short)9, (short)9, (short)6, (short)6, (short)1, (short)3, (short)5, (short)9,
            (short)10, (short)6, (short)6, (short)1, (short)1, (short)1, (short)58, (short)11, (short)6, (short)6, (short)1, (short)3, (short)4, (short)9, (short)13, (short)6,
            (short)6, (short)1, (short)1, (short)1, (short)2, (short)1, (short)6, (short)7, (short)1, (short)1, (short)3, (short)47, (short)3, (short)6, (short)7, (short)1,
            (short)3, (short)4, (short)9, (short)4, (short)6, (short)7, (short)1, (short)1, (short)1, (short)54, (short)5, (short)6, (short)7, (short)1, (short)3, (short)4,
            (short)9, (short)10, (short)6, (short)7, (short)1, (short)3, (short)4, (short)9, (short)13, (short)6, (short)7, (short)1, (short)1, (short)3, (short)48, (short)4,
            (short)6, (short)8, (short)1, (short)3, (short)3, (short)9, (short)7, (short)6, (short)9, (short)1, (short)1, (short)1, (short)59, (short)12, (short)6, (short)9,
            (short)1, (short)1, (short)2, (short)60, (short)1, (short)6, (short)10, (short)1, (short)1, (short)1, (short)2, (short)7, (short)6, (short)10, (short)1, (short)1,
            (short)1, (short)61, (short)11, (short)6, (short)10, (short)1, (short)1, (short)1, (short)60, (short)13, (short)6, (short)10, (short)1, (short)1, (short)1, (short)2,
            (short)2, (short)6, (short)11, (short)1, (short)1, (short)1, (short)2, (short)3, (short)6, (short)11, (short)3, (short)1, (short)1, (short)49, (short)6, (short)6,
            (short)11, (short)1, (short)1, (short)1, (short)2, (short)7, (short)6, (short)11, (short)1, (short)1, (short)1, (short)49, (short)8, (short)6, (short)11, (short)1,
            (short)1, (short)1, (short)2, (short)9, (short)6, (short)11, (short)3, (short)1, (short)1, (short)49, (short)12, (short)6, (short)11, (short)1, (short)1, (short)1,
            (short)2, (short)3, (short)6, (short)12, (short)3, (short)3, (short)1, (short)9, (short)7, (short)6, (short)12, (short)1, (short)3, (short)1, (short)9, (short)9,
            (short)6, (short)12, (short)3, (short)3, (short)1, (short)9, (short)2, (short)7, (short)1, (short)1, (short)1, (short)1, (short)10, (short)3, (short)7, (short)1,
            (short)1, (short)2, (short)3, (short)9, (short)4, (short)7, (short)1, (short)1, (short)1, (short)1, (short)61, (short)5, (short)7, (short)1, (short)1, (short)2,
            (short)3, (short)9, (short)6, (short)7, (short)1, (short)1, (short)1, (short)1, (short)10, (short)7, (short)7, (short)1, (short)1, (short)2, (short)1, (short)9,
            (short)8, (short)7, (short)1, (short)1, (short)1, (short)1, (short)10, (short)9, (short)7, (short)1, (short)1, (short)2, (short)1, (short)9, (short)10, (short)7,
            (short)1, (short)1, (short)1, (short)1, (short)61, (short)11, (short)7, (short)1, (short)1, (short)2, (short)1, (short)9, (short)12, (short)7, (short)1, (short)1,
            (short)1, (short)1, (short)10, (short)1, (short)7, (short)2, (short)1, (short)1, (short)1, (short)11, (short)4, (short)7, (short)2, (short)1, (short)2, (short)1,
            (short)9, (short)10, (short)7, (short)2, (short)1, (short)2, (short)1, (short)9, (short)13, (short)7, (short)2, (short)1, (short)1, (short)1, (short)13, (short)1,
            (short)7, (short)3, (short)2, (short)2, (short)1, (short)9, (short)4, (short)7, (short)3, (short)1, (short)1, (short)1, (short)54, (short)6, (short)7, (short)3,
            (short)1, (short)2, (short)3, (short)9, (short)9, (short)7, (short)3, (short)1, (short)2, (short)1, (short)9, (short)10, (short)7, (short)3, (short)1, (short)1,
            (short)1, (short)56, (short)11, (short)7, (short)3, (short)1, (short)2, (short)1, (short)9, (short)13, (short)7, (short)3, (short)1, (short)2, (short)1, (short)9,
            (short)1, (short)7, (short)4, (short)1, (short)1, (short)1, (short)61, (short)2, (short)7, (short)4, (short)1, (short)2, (short)2, (short)9, (short)3, (short)7,
            (short)4, (short)1, (short)1, (short)1, (short)54, (short)4, (short)7, (short)4, (short)1, (short)1, (short)1, (short)55, (short)5, (short)7, (short)4, (short)1,
            (short)1, (short)1, (short)54, (short)7, (short)7, (short)4, (short)1, (short)2, (short)1, (short)9, (short)9, (short)7, (short)4, (short)3, (short)1, (short)1,
            (short)57, (short)13, (short)7, (short)4, (short)1, (short)1, (short)1, (short)61, (short)1, (short)7, (short)5, (short)1, (short)2, (short)1, (short)9, (short)3,
            (short)7, (short)5, (short)1, (short)2, (short)2, (short)9, (short)4, (short)7, (short)5, (short)1, (short)1, (short)1, (short)54, (short)5, (short)7, (short)5,
            (short)1, (short)2, (short)2, (short)9, (short)9, (short)7, (short)5, (short)1, (short)2, (short)1, (short)9, (short)10, (short)7, (short)5, (short)1, (short)1,
            (short)1, (short)58, (short)11, (short)7, (short)5, (short)1, (short)2, (short)1, (short)9, (short)13, (short)7, (short)5, (short)1, (short)2, (short)1, (short)9,
            (short)1, (short)7, (short)6, (short)1, (short)1, (short)1, (short)11, (short)4, (short)7, (short)6, (short)1, (short)2, (short)2, (short)9, (short)10, (short)7,
            (short)6, (short)1, (short)2, (short)1, (short)9, (short)13, (short)7, (short)6, (short)1, (short)1, (short)1, (short)13, (short)1, (short)7, (short)7, (short)1,
            (short)2, (short)1, (short)9, (short)13, (short)7, (short)7, (short)1, (short)2, (short)1, (short)9, (short)1, (short)7, (short)8, (short)1, (short)1, (short)1,
            (short)61, (short)13, (short)7, (short)8, (short)1, (short)1, (short)1, (short)61, (short)1, (short)7, (short)9, (short)1, (short)2, (short)1, (short)9, (short)7,
            (short)7, (short)9, (short)1, (short)2, (short)3, (short)9, (short)12, (short)7, (short)9, (short)2, (short)2, (short)1, (short)9, (short)1, (short)7, (short)10,
            (short)1, (short)1, (short)1, (short)11, (short)11, (short)7, (short)10, (short)1, (short)2, (short)2, (short)9, (short)12, (short)7, (short)10, (short)1, (short)1,
            (short)1, (short)60, (short)13, (short)7, (short)10, (short)1, (short)1, (short)1, (short)13, (short)2, (short)7, (short)11, (short)1, (short)1, (short)1, (short)15,
            (short)3, (short)7, (short)11, (short)1, (short)2, (short)1, (short)9, (short)4, (short)7, (short)11, (short)1, (short)1, (short)1, (short)61, (short)5, (short)7,
            (short)11, (short)1, (short)2, (short)1, (short)9, (short)6, (short)7, (short)11, (short)1, (short)1, (short)1, (short)15, (short)8, (short)7, (short)11, (short)1,
            (short)1, (short)1, (short)15, (short)9, (short)7, (short)11, (short)1, (short)2, (short)1, (short)9, (short)10, (short)7, (short)11, (short)1, (short)1, (short)1,
            (short)61, (short)12, (short)7, (short)11, (short)1, (short)1, (short)1, (short)15, (short)2, (short)8, (short)1, (short)1, (short)1, (short)1, (short)9, (short)4,
            (short)8, (short)1, (short)1, (short)1, (short)1, (short)9, (short)6, (short)8, (short)1, (short)1, (short)1, (short)1, (short)8, (short)8, (short)8, (short)1,
            (short)1, (short)1, (short)1, (short)8, (short)10, (short)8, (short)1, (short)1, (short)1, (short)1, (short)9, (short)12, (short)8, (short)1, (short)1, (short)1,
            (short)1, (short)9, (short)1, (short)8, (short)2, (short)1, (short)1, (short)1, (short)9, (short)13, (short)8, (short)2, (short)1, (short)1, (short)1, (short)9,
            (short)4, (short)8, (short)3, (short)1, (short)1, (short)3, (short)9, (short)10, (short)8, (short)3, (short)1, (short)1, (short)1, (short)9, (short)1, (short)8,
            (short)4, (short)1, (short)1, (short)1, (short)9, (short)3, (short)8, (short)4, (short)1, (short)1, (short)1, (short)9, (short)5, (short)8, (short)4, (short)1,
            (short)1, (short)1, (short)9, (short)9, (short)8, (short)4, (short)1, (short)1, (short)1, (short)9, (short)10, (short)8, (short)4, (short)1, (short)1, (short)1,
            (short)62, (short)11, (short)8, (short)4, (short)1, (short)1, (short)1, (short)9, (short)13, (short)8, (short)4, (short)1, (short)1, (short)1, (short)9, (short)10,
            (short)8, (short)5, (short)1, (short)1, (short)1, (short)9, (short)1, (short)8, (short)6, (short)1, (short)1, (short)1, (short)8, (short)13, (short)8, (short)6,
            (short)1, (short)1, (short)1, (short)8, (short)1, (short)8, (short)8, (short)1, (short)1, (short)1, (short)9, (short)13, (short)8, (short)8, (short)1, (short)1,
            (short)1, (short)9, (short)1, (short)8, (short)10, (short)1, (short)1, (short)1, (short)9, (short)12, (short)8, (short)10, (short)2, (short)1, (short)1, (short)9,
            (short)2, (short)8, (short)11, (short)1, (short)1, (short)1, (short)9, (short)4, (short)8, (short)11, (short)1, (short)1, (short)1, (short)9, (short)6, (short)8,
            (short)11, (short)1, (short)1, (short)1, (short)8, (short)8, (short)8, (short)11, (short)1, (short)1, (short)1, (short)8, (short)10, (short)8, (short)11, (short)1,
            (short)1, (short)1, (short)9, (short)12, (short)8, (short)11, (short)1, (short)1, (short)1, (short)9
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
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        } else if (rand.nextBoolean()) {
            MadThaumaturge thaumaturge = new MadThaumaturge(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        } else if (Integration.taintedMagic) {
            Samurai thaumaturge = new Samurai(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        }

        return true;
    }

    public static int getWidth() { return 15; }
    public static int getHeight() { return 9; }
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
    // pos: (6, 8, 1), world: (x + 6, y + 8, z + 1)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 8, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 6
    //     y: 8
    //     z: 1
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_001
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (8, 8, 1), world: (x + 8, y + 8, z + 1)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 8, y + 8, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 8
    //     y: 8
    //     z: 1
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_002
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (1, 8, 6), world: (x + 1, y + 8, z + 6)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 1, y + 8, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 1
    //     y: 8
    //     z: 6
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_003
    // id: Chest
    // block: minecraft:chest meta 5 numericId 54
    // pos: (3, 1, 6), world: (x + 3, y + 1, z + 6)
    // TODO TILE STUB NAME: Chest
    // TODO BLOCK STUB NAME: minecraft:chest
    // TODO example: TileEntity te = world.getTileEntity(x + 3, y + 1, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 3
    //     y: 1
    //     z: 6
    //   Items: list[0]
    //     id: 'Chest'
    //   }

    // TILE_ENTITY_004
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (6, 8, 11), world: (x + 6, y + 8, z + 11)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 6, y + 8, z + 11);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 6
    //     y: 8
    //     z: 11
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_005
    // id: TileWardingStone
    // block: Thaumcraft:blockCosmeticSolid meta 3 numericId 182
    // pos: (7, 0, 3), world: (x + 7, y + 0, z + 3)
    // TODO TILE STUB NAME: TileWardingStone
    // TODO BLOCK STUB NAME: Thaumcraft:blockCosmeticSolid
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 0, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 0
    //     z: 3
    //     id: 'TileWardingStone'
    //   }

    // TILE_ENTITY_006
    // id: TileWardingStoneFence
    // block: Thaumcraft:blockAiry meta 4 numericId 198
    // pos: (7, 1, 3), world: (x + 7, y + 1, z + 3)
    // TODO TILE STUB NAME: TileWardingStoneFence
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 1, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 1
    //     z: 3
    //     id: 'TileWardingStoneFence'
    //   }

    // TILE_ENTITY_007
    // id: TileWardingStoneFence
    // block: Thaumcraft:blockAiry meta 4 numericId 198
    // pos: (7, 2, 3), world: (x + 7, y + 2, z + 3)
    // TODO TILE STUB NAME: TileWardingStoneFence
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 2, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 2
    //     z: 3
    //     id: 'TileWardingStoneFence'
    //   }

    // TILE_ENTITY_008
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (8, 8, 11), world: (x + 8, y + 8, z + 11)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 8, y + 8, z + 11);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 8
    //     y: 8
    //     z: 11
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_009
    // id: TileArcaneWorkbench
    // block: Thaumcraft:blockTable meta 15 numericId 189
    // pos: (9, 1, 3), world: (x + 9, y + 1, z + 3)
    // TODO TILE STUB NAME: TileArcaneWorkbench
    // TODO BLOCK STUB NAME: Thaumcraft:blockTable
    // TODO example: TileEntity te = world.getTileEntity(x + 9, y + 1, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 9
    //     y: 1
    //     z: 3
    //     id: 'TileArcaneWorkbench'
    //   Inventory: list[0]
    //   }

    // TILE_ENTITY_010
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (9, 4, 9), world: (x + 9, y + 4, z + 9)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 9, y + 4, z + 9);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 9
    //     y: 4
    //     z: 9
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_011
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (10, 1, 9), world: (x + 10, y + 1, z + 9)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 10, y + 1, z + 9);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 10
    //     y: 1
    //     facing: 4
    //     z: 9
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_012
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (11, 4, 3), world: (x + 11, y + 4, z + 3)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 4, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 11
    //     y: 4
    //     z: 3
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_013
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (11, 4, 4), world: (x + 11, y + 4, z + 4)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 4, z + 4);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 11
    //     y: 4
    //     facing: 4
    //     z: 4
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_014
    // id: TileCrystal
    // block: Thaumcraft:blockCrystal meta 4 numericId 183
    // pos: (11, 2, 6), world: (x + 11, y + 2, z + 6)
    // TODO TILE STUB NAME: TileCrystal
    // TODO BLOCK STUB NAME: Thaumcraft:blockCrystal
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 2, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     orientation: 1
    //     x: 11
    //     y: 2
    //     z: 6
    //     id: 'TileCrystal'
    //   }

    // TILE_ENTITY_015
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (11, 4, 8), world: (x + 11, y + 4, z + 8)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 4, z + 8);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 11
    //     y: 4
    //     facing: 4
    //     z: 8
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_016
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (11, 0, 9), world: (x + 11, y + 0, z + 9)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 0, z + 9);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 11
    //     y: 0
    //     z: 9
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_017
    // id: TileCrucible
    // block: Thaumcraft:blockMetalDevice meta 0 numericId 185
    // pos: (11, 1, 9), world: (x + 11, y + 1, z + 9)
    // TODO TILE STUB NAME: TileCrucible
    // TODO BLOCK STUB NAME: Thaumcraft:blockMetalDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 1, z + 9);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Heat: 0
    //     FluidName: 'water'
    //     Amount: 0
    //   Aspects: list[0]
    //     x: 11
    //     y: 1
    //     z: 9
    //     id: 'TileCrucible'
    //   }

    // TILE_ENTITY_018
    // id: Furnace
    // block: minecraft:furnace meta 4 numericId 61
    // pos: (11, 4, 9), world: (x + 11, y + 4, z + 9)
    // TODO TILE STUB NAME: Furnace
    // TODO BLOCK STUB NAME: minecraft:furnace
    // TODO example: TileEntity te = world.getTileEntity(x + 11, y + 4, z + 9);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     CookTime: 0
    //     x: 11
    //     BurnTime: 0
    //     y: 4
    //     z: 9
    //   Items: list[0]
    //     id: 'Furnace'
    //   }

    // TILE_ENTITY_019
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (13, 8, 6), world: (x + 13, y + 8, z + 6)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 13, y + 8, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 13
    //     y: 8
    //     z: 6
    //     id: 'TileNitor'
    //   }
}
