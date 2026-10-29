package com.ilya3point999k.thaumicconcilium.common.world.houses;

/*
 * GENERATED STRUCTURE TEMPLATE
 * Generator script version: 2026-07-02-v7-optional-world-schematica-first
 * Size: 12 x 9 x 11
 * Unique numeric block IDs used: 21
 * Modded numeric block IDs recognized: 3
 * Tile entity stubs: 6
 * Entity stubs: 0
 *
 * This file is deliberately a template. Compact BOXES place normal blocks;
 * the searchable comments below list every used block ID/name and every
 * tile/entity NBT stub for manual restoration.
 *
 * Schematic mapping entries detected: 21
 * Recognized modded blocks:
 *   186 -> Thaumcraft:blockWoodenDevice  metas=8  count=3
 *   195 -> Thaumcraft:blockJar  metas=0  count=1
 *   198 -> Thaumcraft:blockAiry  metas=1  count=1
 */

import java.util.Random;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.CrimsonRanger;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileBanner;

public class HouseRuins extends WorldGenerator {

    /*
     * USED BLOCK IDS / NAMES
     * Format: numericId:meta -> registryName  count
     */
    // 0:0 -> minecraft:air  count=953
    // 5:1 -> minecraft:planks  count=13
    // 5:5 -> minecraft:planks  count=11
    // 17:0 -> minecraft:log  count=25
    // 17:4 -> minecraft:log  count=3
    // 17:8 -> minecraft:log  count=6
    // 30:0 -> minecraft:web  count=5
    // 31:1 -> minecraft:tallgrass  count=22
    // 43:3 -> minecraft:double_stone_slab  count=10
    // 44:3 -> minecraft:stone_slab  count=22
    // 44:11 -> minecraft:stone_slab  count=5
    // 67:1 -> minecraft:stone_stairs  count=1
    // 67:2 -> minecraft:stone_stairs  count=6
    // 67:3 -> minecraft:stone_stairs  count=1
    // 67:4 -> minecraft:stone_stairs  count=1
    // 67:7 -> minecraft:stone_stairs  count=2
    // 98:0 -> minecraft:stonebrick  count=10
    // 98:2 -> minecraft:stonebrick  count=8
    // 102:0 -> minecraft:glass_pane  count=3
    // 125:1 -> minecraft:double_wooden_slab  count=4
    // 126:1 -> minecraft:wooden_slab  count=16
    // 134:0 -> minecraft:spruce_stairs  count=1
    // 134:5 -> minecraft:spruce_stairs  count=1
    // 134:6 -> minecraft:spruce_stairs  count=2
    // 144:1 -> minecraft:skull  count=1
    // 162:1 -> minecraft:log2  count=14
    // 162:5 -> minecraft:log2  count=9
    // 162:9 -> minecraft:log2  count=4
    // 164:0 -> minecraft:dark_oak_stairs  count=3
    // 164:1 -> minecraft:dark_oak_stairs  count=1
    // 164:2 -> minecraft:dark_oak_stairs  count=4
    // 164:3 -> minecraft:dark_oak_stairs  count=9
    // 173:0 -> minecraft:coal_block  count=5
    // 175:2 -> minecraft:double_plant  count=1
    // 175:11 -> minecraft:double_plant  count=1
    // 186:8 -> Thaumcraft:blockWoodenDevice  count=3
    // 195:0 -> Thaumcraft:blockJar  count=1
    // 198:1 -> Thaumcraft:blockAiry  count=1

    /*
     * COMPACT PALETTE
     * BOXES stores only palette indexes; this list makes those indexes editable.
     * PALETTE below contains direct Block references, not runtime GameRegistry lookups.
     */
    // palette[0] = minecraft:air meta 0
    // palette[1] = minecraft:tallgrass meta 1
    // palette[2] = minecraft:stone_slab meta 3
    // palette[3] = minecraft:log meta 0
    // palette[4] = minecraft:stonebrick meta 0
    // palette[5] = minecraft:stonebrick meta 2
    // palette[6] = Thaumcraft:blockJar meta 0
    // palette[7] = minecraft:log meta 8
    // palette[8] = minecraft:wooden_slab meta 1
    // palette[9] = minecraft:web meta 0
    // palette[10] = minecraft:log meta 4
    // palette[11] = minecraft:double_wooden_slab meta 1
    // palette[12] = minecraft:double_stone_slab meta 3
    // palette[13] = minecraft:double_plant meta 2
    // palette[14] = minecraft:planks meta 1
    // palette[15] = minecraft:log2 meta 1
    // palette[16] = minecraft:glass_pane meta 0
    // palette[17] = minecraft:planks meta 5
    // palette[18] = minecraft:skull meta 1
    // palette[19] = minecraft:double_plant meta 11
    // palette[20] = minecraft:dark_oak_stairs meta 3
    // palette[21] = minecraft:spruce_stairs meta 0
    // palette[22] = minecraft:dark_oak_stairs meta 1
    // palette[23] = minecraft:dark_oak_stairs meta 0
    // palette[24] = minecraft:dark_oak_stairs meta 2
    // palette[25] = minecraft:coal_block meta 0
    // palette[26] = minecraft:stone_slab meta 11
    // palette[27] = minecraft:log2 meta 9
    // palette[28] = minecraft:log2 meta 5
    // palette[29] = minecraft:stone_stairs meta 2
    // palette[30] = minecraft:stone_stairs meta 7
    // palette[31] = minecraft:stone_stairs meta 3
    // palette[32] = Thaumcraft:blockWoodenDevice meta 8
    // palette[33] = minecraft:spruce_stairs meta 6
    // palette[34] = minecraft:stone_stairs meta 4
    // palette[35] = minecraft:spruce_stairs meta 5
    // palette[36] = minecraft:stone_stairs meta 1
    // palette[37] = Thaumcraft:blockAiry meta 1

    /*
     * MOD BLOCK STUBS - MANUAL EDIT SECTION
     *
     * Vanilla palette entries use net.minecraft.init.Blocks directly.
     * Modded/missing entries are deliberately direct Block variables instead of
     * GameRegistry lookups. Replace null with the real block instance from the
     * owning mod, for example ConfigBlocks.blockJar or ModBlocks.someBlock.
     */
    private static Block MOD_THAUMCRAFT_BLOCKAIRY = ConfigBlocks.blockAiry; // TODO provide Thaumcraft:blockAiry
    private static Block MOD_THAUMCRAFT_BLOCKJAR = ConfigBlocks.blockJar; // TODO provide Thaumcraft:blockJar
    private static Block MOD_THAUMCRAFT_BLOCKWOODENDEVICE = ConfigBlocks.blockWoodenDevice; // TODO provide Thaumcraft:blockWoodenDevice

    private static final Block[] PALETTE = new Block[] {
            Blocks.air, // palette[0] = minecraft:air meta 0
            Blocks.tallgrass, // palette[1] = minecraft:tallgrass meta 1
            Blocks.stone_slab, // palette[2] = minecraft:stone_slab meta 3
            Blocks.log, // palette[3] = minecraft:log meta 0
            Blocks.stonebrick, // palette[4] = minecraft:stonebrick meta 0
            Blocks.stonebrick, // palette[5] = minecraft:stonebrick meta 2
            MOD_THAUMCRAFT_BLOCKJAR, // palette[6] = Thaumcraft:blockJar meta 0
            Blocks.log, // palette[7] = minecraft:log meta 8
            Blocks.wooden_slab, // palette[8] = minecraft:wooden_slab meta 1
            Blocks.web, // palette[9] = minecraft:web meta 0
            Blocks.log, // palette[10] = minecraft:log meta 4
            Blocks.double_wooden_slab, // palette[11] = minecraft:double_wooden_slab meta 1
            Blocks.double_stone_slab, // palette[12] = minecraft:double_stone_slab meta 3
            Blocks.double_plant, // palette[13] = minecraft:double_plant meta 2
            Blocks.planks, // palette[14] = minecraft:planks meta 1
            Blocks.log2, // palette[15] = minecraft:log2 meta 1
            Blocks.glass_pane, // palette[16] = minecraft:glass_pane meta 0
            Blocks.planks, // palette[17] = minecraft:planks meta 5
            Blocks.skull, // palette[18] = minecraft:skull meta 1
            Blocks.double_plant, // palette[19] = minecraft:double_plant meta 11
            Blocks.dark_oak_stairs, // palette[20] = minecraft:dark_oak_stairs meta 3
            Blocks.spruce_stairs, // palette[21] = minecraft:spruce_stairs meta 0
            Blocks.dark_oak_stairs, // palette[22] = minecraft:dark_oak_stairs meta 1
            Blocks.dark_oak_stairs, // palette[23] = minecraft:dark_oak_stairs meta 0
            Blocks.dark_oak_stairs, // palette[24] = minecraft:dark_oak_stairs meta 2
            Blocks.coal_block, // palette[25] = minecraft:coal_block meta 0
            Blocks.stone_slab, // palette[26] = minecraft:stone_slab meta 11
            Blocks.log2, // palette[27] = minecraft:log2 meta 9
            Blocks.log2, // palette[28] = minecraft:log2 meta 5
            Blocks.stone_stairs, // palette[29] = minecraft:stone_stairs meta 2
            Blocks.stone_stairs, // palette[30] = minecraft:stone_stairs meta 7
            Blocks.stone_stairs, // palette[31] = minecraft:stone_stairs meta 3
            MOD_THAUMCRAFT_BLOCKWOODENDEVICE, // palette[32] = Thaumcraft:blockWoodenDevice meta 8
            Blocks.spruce_stairs, // palette[33] = minecraft:spruce_stairs meta 6
            Blocks.stone_stairs, // palette[34] = minecraft:stone_stairs meta 4
            Blocks.spruce_stairs, // palette[35] = minecraft:spruce_stairs meta 5
            Blocks.stone_stairs, // palette[36] = minecraft:stone_stairs meta 1
            MOD_THAUMCRAFT_BLOCKAIRY // palette[37] = Thaumcraft:blockAiry meta 1
    };

    private static final byte[] META = new byte[] {
            (byte)0, (byte)1, (byte)3, (byte)0, (byte)0, (byte)2, (byte)0, (byte)8, (byte)1, (byte)0, (byte)4, (byte)1, (byte)3, (byte)2, (byte)1, (byte)1,
            (byte)0, (byte)5, (byte)1, (byte)11, (byte)3, (byte)0, (byte)1, (byte)0, (byte)2, (byte)0, (byte)11, (byte)9, (byte)5, (byte)2, (byte)7, (byte)3,
            (byte)8, (byte)6, (byte)4, (byte)5, (byte)1, (byte)1
    };

    // x, y, z, sizeX, sizeY, sizeZ, palette index
    private static final short[] BOXES = new short[] {
            (short)0, (short)0, (short)0, (short)2, (short)3, (short)1, (short)0, (short)2, (short)0, (short)0, (short)3, (short)1, (short)1, (short)1, (short)5, (short)0,
            (short)0, (short)1, (short)1, (short)1, (short)2, (short)6, (short)0, (short)0, (short)1, (short)1, (short)1, (short)1, (short)7, (short)0, (short)0, (short)2,
            (short)1, (short)1, (short)2, (short)9, (short)0, (short)0, (short)1, (short)9, (short)1, (short)0, (short)10, (short)0, (short)0, (short)1, (short)1, (short)1,
            (short)2, (short)11, (short)0, (short)0, (short)1, (short)9, (short)1, (short)0, (short)0, (short)0, (short)1, (short)1, (short)3, (short)1, (short)0, (short)1,
            (short)0, (short)1, (short)1, (short)4, (short)1, (short)3, (short)2, (short)0, (short)1, (short)2, (short)1, (short)1, (short)4, (short)4, (short)0, (short)1,
            (short)1, (short)1, (short)1, (short)3, (short)5, (short)0, (short)1, (short)2, (short)1, (short)1, (short)5, (short)7, (short)0, (short)1, (short)1, (short)2,
            (short)1, (short)3, (short)8, (short)0, (short)1, (short)1, (short)1, (short)1, (short)5, (short)9, (short)0, (short)1, (short)1, (short)1, (short)1, (short)4,
            (short)10, (short)0, (short)1, (short)1, (short)1, (short)1, (short)3, (short)11, (short)0, (short)1, (short)1, (short)1, (short)1, (short)1, (short)0, (short)0,
            (short)2, (short)1, (short)1, (short)1, (short)1, (short)1, (short)0, (short)2, (short)1, (short)1, (short)2, (short)5, (short)2, (short)0, (short)2, (short)1,
            (short)1, (short)1, (short)6, (short)3, (short)0, (short)2, (short)1, (short)1, (short)1, (short)1, (short)4, (short)0, (short)2, (short)1, (short)1, (short)1,
            (short)7, (short)5, (short)0, (short)2, (short)2, (short)1, (short)1, (short)1, (short)7, (short)0, (short)2, (short)1, (short)9, (short)1, (short)0, (short)8,
            (short)0, (short)2, (short)1, (short)1, (short)1, (short)8, (short)9, (short)0, (short)2, (short)1, (short)1, (short)1, (short)9, (short)10, (short)0, (short)2,
            (short)1, (short)1, (short)1, (short)4, (short)11, (short)0, (short)2, (short)1, (short)1, (short)2, (short)2, (short)0, (short)0, (short)3, (short)1, (short)9,
            (short)1, (short)0, (short)2, (short)0, (short)3, (short)1, (short)1, (short)1, (short)1, (short)3, (short)0, (short)3, (short)2, (short)1, (short)1, (short)8,
            (short)5, (short)0, (short)3, (short)1, (short)1, (short)1, (short)10, (short)6, (short)0, (short)3, (short)1, (short)1, (short)1, (short)1, (short)7, (short)0,
            (short)3, (short)1, (short)1, (short)1, (short)8, (short)8, (short)0, (short)3, (short)1, (short)1, (short)1, (short)11, (short)9, (short)0, (short)3, (short)1,
            (short)1, (short)1, (short)8, (short)10, (short)0, (short)3, (short)1, (short)1, (short)1, (short)1, (short)0, (short)0, (short)4, (short)1, (short)1, (short)1,
            (short)2, (short)1, (short)0, (short)4, (short)1, (short)3, (short)1, (short)3, (short)2, (short)0, (short)4, (short)1, (short)1, (short)2, (short)8, (short)3,
            (short)0, (short)4, (short)2, (short)1, (short)1, (short)11, (short)5, (short)0, (short)4, (short)1, (short)1, (short)1, (short)8, (short)6, (short)0, (short)4,
            (short)1, (short)1, (short)1, (short)10, (short)7, (short)0, (short)4, (short)1, (short)4, (short)3, (short)0, (short)8, (short)0, (short)4, (short)1, (short)1,
            (short)1, (short)8, (short)9, (short)0, (short)4, (short)1, (short)3, (short)2, (short)0, (short)10, (short)0, (short)4, (short)1, (short)1, (short)1, (short)3,
            (short)11, (short)0, (short)4, (short)1, (short)1, (short)1, (short)1, (short)0, (short)0, (short)5, (short)1, (short)1, (short)1, (short)12, (short)1, (short)0,
            (short)5, (short)1, (short)1, (short)2, (short)4, (short)3, (short)0, (short)5, (short)1, (short)1, (short)1, (short)11, (short)4, (short)0, (short)5, (short)1,
            (short)1, (short)1, (short)8, (short)5, (short)0, (short)5, (short)2, (short)9, (short)1, (short)0, (short)8, (short)0, (short)5, (short)1, (short)9, (short)1,
            (short)0, (short)10, (short)0, (short)5, (short)1, (short)1, (short)1, (short)4, (short)11, (short)0, (short)5, (short)1, (short)9, (short)1, (short)0, (short)0,
            (short)0, (short)6, (short)1, (short)1, (short)1, (short)2, (short)2, (short)0, (short)6, (short)1, (short)1, (short)1, (short)13, (short)3, (short)0, (short)6,
            (short)1, (short)1, (short)1, (short)8, (short)4, (short)0, (short)6, (short)1, (short)1, (short)1, (short)1, (short)5, (short)0, (short)6, (short)1, (short)9,
            (short)1, (short)0, (short)6, (short)0, (short)6, (short)1, (short)1, (short)1, (short)8, (short)8, (short)0, (short)6, (short)1, (short)1, (short)2, (short)1,
            (short)9, (short)0, (short)6, (short)1, (short)1, (short)1, (short)8, (short)10, (short)0, (short)6, (short)1, (short)1, (short)1, (short)5, (short)11, (short)0,
            (short)6, (short)1, (short)1, (short)1, (short)2, (short)0, (short)0, (short)7, (short)1, (short)9, (short)1, (short)0, (short)1, (short)0, (short)7, (short)1,
            (short)2, (short)1, (short)3, (short)2, (short)0, (short)7, (short)1, (short)1, (short)1, (short)4, (short)3, (short)0, (short)7, (short)1, (short)1, (short)1,
            (short)5, (short)4, (short)0, (short)7, (short)1, (short)2, (short)1, (short)3, (short)5, (short)0, (short)7, (short)1, (short)1, (short)2, (short)8, (short)6,
            (short)0, (short)7, (short)1, (short)1, (short)2, (short)14, (short)7, (short)0, (short)7, (short)1, (short)2, (short)1, (short)3, (short)9, (short)0, (short)7,
            (short)1, (short)1, (short)1, (short)5, (short)10, (short)0, (short)7, (short)1, (short)2, (short)1, (short)3, (short)11, (short)0, (short)7, (short)1, (short)9,
            (short)4, (short)0, (short)0, (short)0, (short)8, (short)1, (short)1, (short)1, (short)1, (short)1, (short)0, (short)8, (short)1, (short)1, (short)1, (short)2,
            (short)2, (short)0, (short)8, (short)2, (short)1, (short)1, (short)12, (short)4, (short)0, (short)8, (short)1, (short)1, (short)1, (short)4, (short)7, (short)0,
            (short)8, (short)1, (short)1, (short)1, (short)4, (short)8, (short)0, (short)8, (short)1, (short)1, (short)1, (short)12, (short)9, (short)0, (short)8, (short)1,
            (short)1, (short)1, (short)2, (short)10, (short)0, (short)8, (short)1, (short)1, (short)1, (short)1, (short)0, (short)0, (short)9, (short)2, (short)9, (short)2,
            (short)0, (short)2, (short)0, (short)9, (short)1, (short)1, (short)1, (short)2, (short)3, (short)0, (short)9, (short)1, (short)1, (short)1, (short)12, (short)4,
            (short)0, (short)9, (short)1, (short)1, (short)1, (short)3, (short)5, (short)0, (short)9, (short)1, (short)1, (short)1, (short)1, (short)6, (short)0, (short)9,
            (short)1, (short)1, (short)1, (short)8, (short)7, (short)0, (short)9, (short)1, (short)1, (short)1, (short)3, (short)8, (short)0, (short)9, (short)1, (short)1,
            (short)1, (short)2, (short)9, (short)0, (short)9, (short)2, (short)9, (short)2, (short)0, (short)2, (short)0, (short)10, (short)1, (short)1, (short)1, (short)1,
            (short)3, (short)0, (short)10, (short)1, (short)1, (short)1, (short)2, (short)4, (short)0, (short)10, (short)2, (short)9, (short)1, (short)0, (short)6, (short)0,
            (short)10, (short)1, (short)1, (short)1, (short)1, (short)7, (short)0, (short)10, (short)1, (short)9, (short)1, (short)0, (short)8, (short)0, (short)10, (short)1,
            (short)1, (short)1, (short)1, (short)2, (short)1, (short)0, (short)7, (short)2, (short)1, (short)0, (short)10, (short)1, (short)0, (short)1, (short)8, (short)1,
            (short)0, (short)2, (short)1, (short)1, (short)1, (short)2, (short)1, (short)14, (short)3, (short)1, (short)1, (short)1, (short)2, (short)3, (short)0, (short)4,
            (short)1, (short)1, (short)1, (short)1, (short)1, (short)15, (short)5, (short)1, (short)1, (short)1, (short)1, (short)1, (short)16, (short)6, (short)1, (short)1,
            (short)1, (short)1, (short)1, (short)17, (short)8, (short)1, (short)1, (short)1, (short)3, (short)4, (short)0, (short)9, (short)1, (short)1, (short)1, (short)1,
            (short)1, (short)17, (short)10, (short)1, (short)1, (short)1, (short)1, (short)1, (short)15, (short)11, (short)1, (short)1, (short)1, (short)1, (short)4, (short)0,
            (short)0, (short)1, (short)2, (short)1, (short)3, (short)1, (short)0, (short)1, (short)1, (short)2, (short)1, (short)2, (short)1, (short)14, (short)2, (short)1,
            (short)2, (short)1, (short)4, (short)4, (short)0, (short)4, (short)1, (short)2, (short)3, (short)4, (short)3, (short)0, (short)9, (short)1, (short)2, (short)1,
            (short)8, (short)2, (short)0, (short)10, (short)1, (short)2, (short)1, (short)1, (short)1, (short)17, (short)1, (short)1, (short)3, (short)1, (short)1, (short)1,
            (short)0, (short)7, (short)1, (short)3, (short)1, (short)8, (short)1, (short)0, (short)10, (short)1, (short)3, (short)1, (short)8, (short)1, (short)0, (short)0,
            (short)1, (short)4, (short)1, (short)5, (short)3, (short)0, (short)3, (short)1, (short)4, (short)1, (short)1, (short)1, (short)18, (short)10, (short)1, (short)4,
            (short)1, (short)2, (short)1, (short)15, (short)1, (short)1, (short)5, (short)1, (short)2, (short)1, (short)0, (short)3, (short)1, (short)5, (short)2, (short)8,
            (short)2, (short)0, (short)10, (short)1, (short)5, (short)1, (short)1, (short)1, (short)16, (short)1, (short)1, (short)6, (short)1, (short)2, (short)1, (short)14,
            (short)2, (short)1, (short)6, (short)1, (short)1, (short)1, (short)19, (short)6, (short)1, (short)6, (short)1, (short)2, (short)2, (short)0, (short)8, (short)1,
            (short)6, (short)4, (short)2, (short)1, (short)0, (short)2, (short)1, (short)7, (short)1, (short)2, (short)1, (short)14, (short)3, (short)1, (short)7, (short)1,
            (short)1, (short)1, (short)9, (short)5, (short)1, (short)7, (short)1, (short)1, (short)3, (short)0, (short)8, (short)1, (short)7, (short)1, (short)2, (short)4,
            (short)0, (short)9, (short)1, (short)7, (short)1, (short)1, (short)1, (short)20, (short)0, (short)1, (short)8, (short)3, (short)2, (short)1, (short)0, (short)3,
            (short)1, (short)8, (short)1, (short)1, (short)1, (short)12, (short)4, (short)1, (short)8, (short)1, (short)1, (short)1, (short)14, (short)6, (short)1, (short)8,
            (short)1, (short)1, (short)1, (short)21, (short)7, (short)1, (short)8, (short)1, (short)1, (short)1, (short)14, (short)9, (short)1, (short)8, (short)2, (short)8,
            (short)1, (short)0, (short)2, (short)1, (short)9, (short)1, (short)8, (short)2, (short)0, (short)3, (short)1, (short)9, (short)1, (short)1, (short)1, (short)2,
            (short)4, (short)1, (short)9, (short)1, (short)2, (short)1, (short)15, (short)6, (short)1, (short)9, (short)1, (short)1, (short)2, (short)0, (short)7, (short)1,
            (short)9, (short)1, (short)1, (short)1, (short)15, (short)3, (short)1, (short)10, (short)1, (short)8, (short)1, (short)0, (short)4, (short)2, (short)1, (short)1,
            (short)1, (short)1, (short)22, (short)5, (short)2, (short)1, (short)1, (short)7, (short)1, (short)0, (short)6, (short)2, (short)1, (short)1, (short)1, (short)1,
            (short)23, (short)7, (short)2, (short)1, (short)1, (short)1, (short)1, (short)15, (short)9, (short)2, (short)1, (short)1, (short)7, (short)1, (short)0, (short)10,
            (short)2, (short)1, (short)1, (short)1, (short)1, (short)24, (short)11, (short)2, (short)1, (short)1, (short)7, (short)3, (short)0, (short)10, (short)2, (short)2,
            (short)1, (short)1, (short)1, (short)25, (short)1, (short)2, (short)3, (short)1, (short)1, (short)1, (short)16, (short)3, (short)2, (short)4, (short)1, (short)4,
            (short)1, (short)0, (short)11, (short)2, (short)4, (short)1, (short)1, (short)1, (short)26, (short)10, (short)2, (short)5, (short)1, (short)1, (short)1, (short)0,
            (short)2, (short)2, (short)6, (short)1, (short)1, (short)1, (short)0, (short)1, (short)2, (short)7, (short)1, (short)2, (short)1, (short)15, (short)3, (short)2,
            (short)7, (short)1, (short)1, (short)3, (short)0, (short)4, (short)2, (short)7, (short)1, (short)1, (short)1, (short)15, (short)5, (short)2, (short)7, (short)1,
            (short)7, (short)2, (short)0, (short)7, (short)2, (short)7, (short)1, (short)1, (short)1, (short)15, (short)9, (short)2, (short)7, (short)1, (short)1, (short)1,
            (short)0, (short)10, (short)2, (short)7, (short)1, (short)2, (short)1, (short)15, (short)4, (short)2, (short)8, (short)1, (short)1, (short)1, (short)7, (short)6,
            (short)2, (short)8, (short)1, (short)1, (short)1, (short)9, (short)7, (short)2, (short)8, (short)1, (short)1, (short)1, (short)27, (short)5, (short)2, (short)9,
            (short)2, (short)1, (short)1, (short)28, (short)7, (short)2, (short)9, (short)1, (short)1, (short)1, (short)20, (short)6, (short)2, (short)10, (short)1, (short)7,
            (short)1, (short)0, (short)0, (short)3, (short)0, (short)4, (short)1, (short)1, (short)29, (short)4, (short)3, (short)0, (short)5, (short)6, (short)1, (short)0,
            (short)0, (short)3, (short)1, (short)1, (short)1, (short)1, (short)30, (short)2, (short)3, (short)1, (short)1, (short)1, (short)1, (short)10, (short)3, (short)3,
            (short)1, (short)1, (short)1, (short)1, (short)25, (short)4, (short)3, (short)1, (short)1, (short)6, (short)1, (short)0, (short)6, (short)3, (short)1, (short)1,
            (short)6, (short)1, (short)0, (short)7, (short)3, (short)1, (short)1, (short)1, (short)1, (short)28, (short)10, (short)3, (short)1, (short)1, (short)6, (short)2,
            (short)0, (short)1, (short)3, (short)2, (short)1, (short)1, (short)4, (short)7, (short)3, (short)3, (short)2, (short)1, (short)6, (short)2, (short)0, (short)9,
            (short)3, (short)4, (short)1, (short)1, (short)1, (short)26, (short)10, (short)3, (short)4, (short)1, (short)1, (short)1, (short)12, (short)11, (short)3, (short)4,
            (short)1, (short)1, (short)1, (short)2, (short)9, (short)3, (short)5, (short)1, (short)6, (short)2, (short)0, (short)10, (short)3, (short)5, (short)1, (short)1,
            (short)2, (short)27, (short)1, (short)3, (short)6, (short)1, (short)1, (short)1, (short)27, (short)2, (short)3, (short)6, (short)1, (short)1, (short)1, (short)9,
            (short)6, (short)3, (short)6, (short)1, (short)6, (short)1, (short)0, (short)8, (short)3, (short)6, (short)1, (short)6, (short)1, (short)0, (short)11, (short)3,
            (short)6, (short)1, (short)6, (short)1, (short)0, (short)2, (short)3, (short)7, (short)3, (short)1, (short)1, (short)28, (short)6, (short)3, (short)7, (short)1,
            (short)1, (short)1, (short)28, (short)7, (short)3, (short)7, (short)1, (short)1, (short)1, (short)25, (short)8, (short)3, (short)7, (short)2, (short)1, (short)1,
            (short)28, (short)0, (short)3, (short)8, (short)1, (short)6, (short)1, (short)0, (short)1, (short)3, (short)8, (short)1, (short)1, (short)1, (short)31, (short)2,
            (short)3, (short)8, (short)2, (short)6, (short)1, (short)0, (short)4, (short)3, (short)8, (short)1, (short)1, (short)2, (short)17, (short)6, (short)3, (short)8,
            (short)1, (short)6, (short)1, (short)0, (short)7, (short)3, (short)8, (short)1, (short)1, (short)1, (short)32, (short)8, (short)3, (short)8, (short)1, (short)6,
            (short)3, (short)0, (short)3, (short)3, (short)9, (short)1, (short)6, (short)1, (short)0, (short)5, (short)3, (short)9, (short)2, (short)1, (short)1, (short)17,
            (short)7, (short)3, (short)9, (short)1, (short)6, (short)1, (short)0, (short)0, (short)4, (short)0, (short)4, (short)5, (short)1, (short)0, (short)0, (short)4,
            (short)1, (short)1, (short)1, (short)1, (short)29, (short)1, (short)4, (short)1, (short)1, (short)1, (short)1, (short)25, (short)2, (short)4, (short)1, (short)1,
            (short)1, (short)1, (short)24, (short)3, (short)4, (short)1, (short)1, (short)5, (short)1, (short)0, (short)7, (short)4, (short)1, (short)1, (short)1, (short)1,
            (short)32, (short)8, (short)4, (short)1, (short)1, (short)5, (short)3, (short)0, (short)0, (short)4, (short)2, (short)1, (short)1, (short)1, (short)30, (short)1,
            (short)4, (short)2, (short)1, (short)1, (short)1, (short)17, (short)1, (short)4, (short)3, (short)1, (short)1, (short)1, (short)14, (short)1, (short)4, (short)4,
            (short)1, (short)3, (short)1, (short)3, (short)7, (short)4, (short)4, (short)1, (short)1, (short)1, (short)26, (short)8, (short)4, (short)4, (short)1, (short)1,
            (short)1, (short)12, (short)9, (short)4, (short)4, (short)1, (short)1, (short)1, (short)2, (short)10, (short)4, (short)4, (short)2, (short)5, (short)1, (short)0,
            (short)1, (short)4, (short)5, (short)1, (short)2, (short)1, (short)17, (short)7, (short)4, (short)5, (short)1, (short)5, (short)4, (short)0, (short)10, (short)4,
            (short)5, (short)1, (short)5, (short)1, (short)0, (short)1, (short)4, (short)6, (short)1, (short)1, (short)1, (short)25, (short)2, (short)4, (short)6, (short)1,
            (short)1, (short)1, (short)33, (short)10, (short)4, (short)6, (short)1, (short)1, (short)1, (short)24, (short)1, (short)4, (short)7, (short)1, (short)5, (short)2,
            (short)0, (short)2, (short)4, (short)7, (short)3, (short)1, (short)1, (short)20, (short)6, (short)4, (short)7, (short)1, (short)5, (short)1, (short)0, (short)8,
            (short)4, (short)7, (short)1, (short)5, (short)1, (short)0, (short)9, (short)4, (short)7, (short)1, (short)1, (short)1, (short)23, (short)10, (short)4, (short)7,
            (short)1, (short)1, (short)1, (short)20, (short)4, (short)4, (short)8, (short)1, (short)1, (short)1, (short)23, (short)4, (short)4, (short)9, (short)3, (short)5,
            (short)1, (short)0, (short)0, (short)5, (short)1, (short)3, (short)4, (short)1, (short)0, (short)7, (short)5, (short)1, (short)1, (short)4, (short)1, (short)0,
            (short)0, (short)5, (short)2, (short)1, (short)1, (short)1, (short)29, (short)1, (short)5, (short)2, (short)1, (short)1, (short)1, (short)24, (short)2, (short)5,
            (short)2, (short)1, (short)4, (short)2, (short)0, (short)4, (short)5, (short)2, (short)3, (short)4, (short)2, (short)0, (short)1, (short)5, (short)3, (short)1,
            (short)1, (short)1, (short)17, (short)2, (short)5, (short)4, (short)1, (short)1, (short)1, (short)9, (short)4, (short)5, (short)4, (short)1, (short)1, (short)1,
            (short)0, (short)5, (short)5, (short)4, (short)1, (short)1, (short)1, (short)26, (short)6, (short)5, (short)4, (short)1, (short)1, (short)1, (short)12, (short)7,
            (short)5, (short)4, (short)1, (short)1, (short)1, (short)2, (short)8, (short)5, (short)4, (short)2, (short)4, (short)1, (short)0, (short)2, (short)5, (short)5,
            (short)1, (short)1, (short)1, (short)33, (short)1, (short)5, (short)6, (short)1, (short)1, (short)1, (short)32, (short)2, (short)5, (short)6, (short)1, (short)1,
            (short)1, (short)20, (short)10, (short)5, (short)6, (short)1, (short)4, (short)2, (short)0, (short)2, (short)5, (short)7, (short)3, (short)4, (short)1, (short)0,
            (short)9, (short)5, (short)7, (short)1, (short)4, (short)1, (short)0, (short)4, (short)5, (short)8, (short)1, (short)4, (short)1, (short)0, (short)0, (short)6,
            (short)2, (short)2, (short)3, (short)1, (short)0, (short)1, (short)6, (short)3, (short)1, (short)3, (short)1, (short)0, (short)0, (short)6, (short)4, (short)1,
            (short)1, (short)1, (short)34, (short)2, (short)6, (short)4, (short)1, (short)1, (short)1, (short)35, (short)3, (short)6, (short)4, (short)1, (short)1, (short)1,
            (short)26, (short)4, (short)6, (short)4, (short)1, (short)1, (short)1, (short)12, (short)5, (short)6, (short)4, (short)1, (short)1, (short)1, (short)2, (short)6,
            (short)6, (short)4, (short)2, (short)3, (short)1, (short)0, (short)0, (short)6, (short)5, (short)1, (short)3, (short)2, (short)0, (short)1, (short)6, (short)5,
            (short)2, (short)1, (short)1, (short)20, (short)1, (short)6, (short)6, (short)2, (short)3, (short)1, (short)0, (short)0, (short)7, (short)4, (short)1, (short)1,
            (short)1, (short)36, (short)1, (short)7, (short)4, (short)3, (short)1, (short)1, (short)2, (short)4, (short)7, (short)4, (short)2, (short)2, (short)1, (short)0,
            (short)1, (short)7, (short)5, (short)2, (short)2, (short)1, (short)0, (short)0, (short)8, (short)4, (short)1, (short)1, (short)1, (short)37, (short)1, (short)8,
            (short)4, (short)3, (short)1, (short)1, (short)0
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

        TileEntity te = world.getTileEntity(x + 1, y + 5, z + 6);
        if (te instanceof TileBanner) {
            ((TileBanner) te).setFacing((byte) rand.nextInt(16));
        }

        te = world.getTileEntity(x + 13, y + 2, z + 6);
        if (te instanceof TileBanner) {
            ((TileBanner) te).setFacing((byte) rand.nextInt(16));
        }

        te = world.getTileEntity(x + 7, y + 4, z + 1);
        if (te instanceof TileBanner) {
            ((TileBanner) te).setFacing((byte) rand.nextInt(16));
        }

        te = world.getTileEntity(x + 7, y + 3, z + 8);
        if (te instanceof TileBanner) {
            ((TileBanner) te).setFacing((byte) rand.nextInt(16));
        }

        te = world.getTileEntity(x + 3, y + 1, z + 4);
        if (te instanceof TileEntitySkull) {
            TileEntitySkull skull = (TileEntitySkull) te;
            skull.func_145903_a(rand.nextInt(16));
            skull.markDirty();
            world.markBlockForUpdate(x + 3, y + 1, z + 4);
        }


        if (Integration.dyes && rand.nextBoolean()) {
            CrimsonRanger thaumaturge = new CrimsonRanger(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2 + 1, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        }
        return true;
    }

    public static int getWidth() { return 12; }
    public static int getHeight() { return 9; }
    public static int getLength() { return 11; }

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
    // pos: (0, 8, 4), world: (x + 0, y + 8, z + 4)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 0, y + 8, z + 4);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 0
    //     y: 8
    //     z: 4
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_001
    // id: TileBanner
    // block: Thaumcraft:blockWoodenDevice meta 8 numericId 186
    // pos: (1, 5, 6), world: (x + 1, y + 5, z + 6)
    // TODO TILE STUB NAME: TileBanner
    // TODO BLOCK STUB NAME: Thaumcraft:blockWoodenDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 1, y + 5, z + 6);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     color: -1
    //     aspect: ''
    //     x: 1
    //     y: 5
    //     facing: 2
    //     z: 6
    //     id: 'TileBanner'
    //     wall: 0
    //   }

    // TILE_ENTITY_002
    // id: TileJar
    // block: Thaumcraft:blockJar meta 0 numericId 195
    // pos: (2, 0, 2), world: (x + 2, y + 0, z + 2)
    // TODO TILE STUB NAME: TileJar
    // TODO BLOCK STUB NAME: Thaumcraft:blockJar
    // TODO example: TileEntity te = world.getTileEntity(x + 2, y + 0, z + 2);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Amount: 0
    //     x: 2
    //     y: 0
    //     facing: 5
    //     z: 2
    //     id: 'TileJar'
    //   }

    // TILE_ENTITY_003
    // id: Skull
    // block: minecraft:skull meta 1 numericId 144
    // pos: (3, 1, 4), world: (x + 3, y + 1, z + 4)
    // TODO TILE STUB NAME: Skull
    // TODO BLOCK STUB NAME: minecraft:skull
    // TODO example: TileEntity te = world.getTileEntity(x + 3, y + 1, z + 4);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Rot: 5
    //     x: 3
    //     y: 1
    //     z: 4
    //     id: 'Skull'
    //     SkullType: 0
    //   }

    // TILE_ENTITY_004
    // id: TileBanner
    // block: Thaumcraft:blockWoodenDevice meta 8 numericId 186
    // pos: (7, 4, 1), world: (x + 7, y + 4, z + 1)
    // TODO TILE STUB NAME: TileBanner
    // TODO BLOCK STUB NAME: Thaumcraft:blockWoodenDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 4, z + 1);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     color: -1
    //     aspect: ''
    //     x: 7
    //     y: 4
    //     facing: 10
    //     z: 1
    //     id: 'TileBanner'
    //     wall: 0
    //   }

    // TILE_ENTITY_005
    // id: TileBanner
    // block: Thaumcraft:blockWoodenDevice meta 8 numericId 186
    // pos: (7, 3, 8), world: (x + 7, y + 3, z + 8)
    // TODO TILE STUB NAME: TileBanner
    // TODO BLOCK STUB NAME: Thaumcraft:blockWoodenDevice
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 3, z + 8);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     color: -1
    //     aspect: ''
    //     x: 7
    //     y: 3
    //     facing: 15
    //     z: 8
    //     id: 'TileBanner'
    //     wall: 0
    //   }
}
