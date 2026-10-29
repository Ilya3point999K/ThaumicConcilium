package com.ilya3point999k.thaumicconcilium.common.world.houses;

/*
 * GENERATED STRUCTURE TEMPLATE
 * Generator script version: 2026-07-02-v7-optional-world-schematica-first
 * Size: 12 x 9 x 15
 * Unique numeric block IDs used: 28
 * Modded numeric block IDs recognized: 3
 * Tile entity stubs: 6
 * Entity stubs: 0
 *
 * This file is deliberately a template. Compact BOXES place normal blocks;
 * the searchable comments below list every used block ID/name and every
 * tile/entity NBT stub for manual restoration.
 *
 * Schematic mapping entries detected: 28
 * Recognized modded blocks:
 *   183 -> Thaumcraft:blockCrystal  metas=2  count=1
 *   196 -> Thaumcraft:blockCandle  metas=3  count=1
 *   198 -> Thaumcraft:blockAiry  metas=1  count=4
 */

import java.util.Random;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Samurai;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.VengefulGolem;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thaumcraft.common.config.ConfigBlocks;

public class HouseGolemancer extends WorldGenerator {

    /*
     * USED BLOCK IDS / NAMES
     * Format: numericId:meta -> registryName  count
     */
    // 0:0 -> minecraft:air  count=1036
    // 0:1 -> minecraft:air  count=3
    // 1:0 -> minecraft:stone  count=66
    // 2:0 -> minecraft:grass  count=60
    // 3:0 -> minecraft:dirt  count=36
    // 5:1 -> minecraft:planks  count=54
    // 13:0 -> minecraft:gravel  count=6
    // 17:0 -> minecraft:log  count=64
    // 17:4 -> minecraft:log  count=28
    // 17:8 -> minecraft:log  count=36
    // 20:0 -> minecraft:glass  count=20
    // 26:2 -> minecraft:bed  count=1
    // 26:10 -> minecraft:bed  count=1
    // 38:1 -> minecraft:red_flower  count=1
    // 38:3 -> minecraft:red_flower  count=1
    // 38:7 -> minecraft:red_flower  count=1
    // 38:8 -> minecraft:red_flower  count=1
    // 42:0 -> minecraft:iron_block  count=1
    // 64:0 -> minecraft:wooden_door  count=1
    // 64:1 -> minecraft:wooden_door  count=2
    // 64:2 -> minecraft:wooden_door  count=1
    // 64:8 -> minecraft:wooden_door  count=3
    // 64:9 -> minecraft:wooden_door  count=1
    // 80:0 -> minecraft:snow  count=6
    // 82:0 -> minecraft:clay  count=4
    // 85:0 -> minecraft:fence  count=8
    // 86:0 -> minecraft:pumpkin  count=2
    // 86:2 -> minecraft:pumpkin  count=2
    // 86:3 -> minecraft:pumpkin  count=3
    // 91:3 -> minecraft:lit_pumpkin  count=1
    // 98:0 -> minecraft:stonebrick  count=40
    // 102:0 -> minecraft:glass_pane  count=10
    // 126:1 -> minecraft:wooden_slab  count=30
    // 126:9 -> minecraft:wooden_slab  count=13
    // 134:0 -> minecraft:spruce_stairs  count=4
    // 134:1 -> minecraft:spruce_stairs  count=5
    // 134:2 -> minecraft:spruce_stairs  count=12
    // 134:3 -> minecraft:spruce_stairs  count=6
    // 134:4 -> minecraft:spruce_stairs  count=4
    // 134:5 -> minecraft:spruce_stairs  count=4
    // 134:6 -> minecraft:spruce_stairs  count=5
    // 134:7 -> minecraft:spruce_stairs  count=12
    // 139:0 -> minecraft:cobblestone_wall  count=4
    // 140:0 -> minecraft:flower_pot  count=1
    // 170:0 -> minecraft:hay_block  count=2
    // 171:1 -> minecraft:carpet  count=4
    // 171:12 -> minecraft:carpet  count=8
    // 183:2 -> Thaumcraft:blockCrystal  count=1
    // 196:3 -> Thaumcraft:blockCandle  count=1
    // 198:1 -> Thaumcraft:blockAiry  count=4

    /*
     * COMPACT PALETTE
     * BOXES stores only palette indexes; this list makes those indexes editable.
     * PALETTE below contains direct Block references, not runtime GameRegistry lookups.
     */
    // palette[0] = minecraft:grass meta 0
    // palette[1] = minecraft:dirt meta 0
    // palette[2] = minecraft:planks meta 1
    // palette[3] = minecraft:stonebrick meta 0
    // palette[4] = minecraft:stone meta 0
    // palette[5] = minecraft:gravel meta 0
    // palette[6] = minecraft:air meta 0
    // palette[7] = minecraft:log meta 0
    // palette[8] = minecraft:red_flower meta 7
    // palette[9] = minecraft:snow meta 0
    // palette[10] = minecraft:pumpkin meta 0
    // palette[11] = minecraft:bed meta 10
    // palette[12] = minecraft:red_flower meta 1
    // palette[13] = minecraft:red_flower meta 3
    // palette[14] = minecraft:bed meta 2
    // palette[15] = minecraft:red_flower meta 8
    // palette[16] = minecraft:spruce_stairs meta 7
    // palette[17] = minecraft:wooden_door meta 2
    // palette[18] = minecraft:wooden_door meta 0
    // palette[19] = minecraft:carpet meta 12
    // palette[20] = minecraft:cobblestone_wall meta 0
    // palette[21] = minecraft:carpet meta 1
    // palette[22] = minecraft:pumpkin meta 3
    // palette[23] = minecraft:spruce_stairs meta 5
    // palette[24] = minecraft:spruce_stairs meta 1
    // palette[25] = minecraft:spruce_stairs meta 4
    // palette[26] = minecraft:clay meta 0
    // palette[27] = minecraft:spruce_stairs meta 6
    // palette[28] = minecraft:wooden_door meta 1
    // palette[29] = minecraft:glass_pane meta 0
    // palette[30] = Thaumcraft:blockCrystal meta 2
    // palette[31] = minecraft:flower_pot meta 0
    // palette[32] = minecraft:wooden_door meta 8
    // palette[33] = minecraft:fence meta 0
    // palette[34] = minecraft:pumpkin meta 2
    // palette[35] = minecraft:air meta 1
    // palette[36] = Thaumcraft:blockCandle meta 3
    // palette[37] = minecraft:wooden_slab meta 9
    // palette[38] = minecraft:wooden_door meta 9
    // palette[39] = minecraft:log meta 8
    // palette[40] = minecraft:lit_pumpkin meta 3
    // palette[41] = minecraft:hay_block meta 0
    // palette[42] = minecraft:log meta 4
    // palette[43] = minecraft:glass meta 0
    // palette[44] = minecraft:spruce_stairs meta 2
    // palette[45] = minecraft:spruce_stairs meta 3
    // palette[46] = minecraft:wooden_slab meta 1
    // palette[47] = minecraft:spruce_stairs meta 0
    // palette[48] = minecraft:iron_block meta 0
    // palette[49] = Thaumcraft:blockAiry meta 1

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


    private static final Block[] PALETTE = new Block[] {
            Blocks.grass, // palette[0] = minecraft:grass meta 0
            Blocks.dirt, // palette[1] = minecraft:dirt meta 0
            Blocks.planks, // palette[2] = minecraft:planks meta 1
            Blocks.stonebrick, // palette[3] = minecraft:stonebrick meta 0
            Blocks.stone, // palette[4] = minecraft:stone meta 0
            Blocks.gravel, // palette[5] = minecraft:gravel meta 0
            Blocks.air, // palette[6] = minecraft:air meta 0
            Blocks.log, // palette[7] = minecraft:log meta 0
            Blocks.red_flower, // palette[8] = minecraft:red_flower meta 7
            Blocks.snow, // palette[9] = minecraft:snow meta 0
            Blocks.pumpkin, // palette[10] = minecraft:pumpkin meta 0
            Blocks.bed, // palette[11] = minecraft:bed meta 10
            Blocks.red_flower, // palette[12] = minecraft:red_flower meta 1
            Blocks.red_flower, // palette[13] = minecraft:red_flower meta 3
            Blocks.bed, // palette[14] = minecraft:bed meta 2
            Blocks.red_flower, // palette[15] = minecraft:red_flower meta 8
            Blocks.spruce_stairs, // palette[16] = minecraft:spruce_stairs meta 7
            Blocks.wooden_door, // palette[17] = minecraft:wooden_door meta 2
            Blocks.wooden_door, // palette[18] = minecraft:wooden_door meta 0
            Blocks.carpet, // palette[19] = minecraft:carpet meta 12
            Blocks.cobblestone_wall, // palette[20] = minecraft:cobblestone_wall meta 0
            Blocks.carpet, // palette[21] = minecraft:carpet meta 1
            Blocks.pumpkin, // palette[22] = minecraft:pumpkin meta 3
            Blocks.spruce_stairs, // palette[23] = minecraft:spruce_stairs meta 5
            Blocks.spruce_stairs, // palette[24] = minecraft:spruce_stairs meta 1
            Blocks.spruce_stairs, // palette[25] = minecraft:spruce_stairs meta 4
            Blocks.clay, // palette[26] = minecraft:clay meta 0
            Blocks.spruce_stairs, // palette[27] = minecraft:spruce_stairs meta 6
            Blocks.wooden_door, // palette[28] = minecraft:wooden_door meta 1
            Blocks.glass_pane, // palette[29] = minecraft:glass_pane meta 0
            MOD_THAUMCRAFT_BLOCKCRYSTAL, // palette[30] = Thaumcraft:blockCrystal meta 2
            Blocks.flower_pot, // palette[31] = minecraft:flower_pot meta 0
            Blocks.wooden_door, // palette[32] = minecraft:wooden_door meta 8
            Blocks.fence, // palette[33] = minecraft:fence meta 0
            Blocks.pumpkin, // palette[34] = minecraft:pumpkin meta 2
            Blocks.air, // palette[35] = minecraft:air meta 1
            MOD_THAUMCRAFT_BLOCKCANDLE, // palette[36] = Thaumcraft:blockCandle meta 3
            Blocks.wooden_slab, // palette[37] = minecraft:wooden_slab meta 9
            Blocks.wooden_door, // palette[38] = minecraft:wooden_door meta 9
            Blocks.log, // palette[39] = minecraft:log meta 8
            Blocks.lit_pumpkin, // palette[40] = minecraft:lit_pumpkin meta 3
            Blocks.hay_block, // palette[41] = minecraft:hay_block meta 0
            Blocks.log, // palette[42] = minecraft:log meta 4
            Blocks.glass, // palette[43] = minecraft:glass meta 0
            Blocks.spruce_stairs, // palette[44] = minecraft:spruce_stairs meta 2
            Blocks.spruce_stairs, // palette[45] = minecraft:spruce_stairs meta 3
            Blocks.wooden_slab, // palette[46] = minecraft:wooden_slab meta 1
            Blocks.spruce_stairs, // palette[47] = minecraft:spruce_stairs meta 0
            Blocks.iron_block, // palette[48] = minecraft:iron_block meta 0
            MOD_THAUMCRAFT_BLOCKAIRY // palette[49] = Thaumcraft:blockAiry meta 1
    };

    private static final byte[] META = new byte[] {
            (byte)0, (byte)0, (byte)1, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)7, (byte)0, (byte)0, (byte)10, (byte)1, (byte)3, (byte)2, (byte)8,
            (byte)7, (byte)2, (byte)0, (byte)12, (byte)0, (byte)1, (byte)3, (byte)5, (byte)1, (byte)4, (byte)0, (byte)6, (byte)1, (byte)0, (byte)2, (byte)0,
            (byte)8, (byte)0, (byte)2, (byte)1, (byte)3, (byte)9, (byte)9, (byte)8, (byte)3, (byte)0, (byte)4, (byte)0, (byte)2, (byte)3, (byte)1, (byte)0,
            (byte)0, (byte)1
    };

    // x, y, z, sizeX, sizeY, sizeZ, palette index
    private static final short[] BOXES = new short[] {
            (short)0, (short)0, (short)0, (short)3, (short)1, (short)3, (short)0, (short)3, (short)0, (short)0, (short)6, (short)1, (short)1, (short)1, (short)9, (short)0,
            (short)0, (short)3, (short)1, (short)3, (short)0, (short)3, (short)0, (short)1, (short)1, (short)1, (short)2, (short)1, (short)4, (short)0, (short)1, (short)4,
            (short)1, (short)3, (short)2, (short)8, (short)0, (short)1, (short)1, (short)1, (short)2, (short)1, (short)0, (short)0, (short)3, (short)2, (short)1, (short)5,
            (short)0, (short)2, (short)0, (short)3, (short)1, (short)1, (short)2, (short)1, (short)3, (short)0, (short)3, (short)1, (short)1, (short)1, (short)2, (short)8,
            (short)0, (short)3, (short)1, (short)1, (short)1, (short)2, (short)9, (short)0, (short)3, (short)1, (short)1, (short)2, (short)1, (short)10, (short)0, (short)3,
            (short)2, (short)1, (short)5, (short)0, (short)3, (short)0, (short)4, (short)6, (short)1, (short)1, (short)3, (short)2, (short)0, (short)5, (short)1, (short)1,
            (short)1, (short)3, (short)3, (short)0, (short)5, (short)6, (short)1, (short)7, (short)4, (short)9, (short)0, (short)5, (short)1, (short)1, (short)1, (short)3,
            (short)2, (short)0, (short)6, (short)1, (short)1, (short)2, (short)1, (short)9, (short)0, (short)6, (short)1, (short)1, (short)2, (short)1, (short)0, (short)0,
            (short)8, (short)2, (short)1, (short)1, (short)1, (short)2, (short)0, (short)8, (short)1, (short)1, (short)4, (short)3, (short)9, (short)0, (short)8, (short)1,
            (short)1, (short)4, (short)3, (short)10, (short)0, (short)8, (short)2, (short)1, (short)1, (short)1, (short)0, (short)0, (short)9, (short)1, (short)1, (short)3,
            (short)1, (short)1, (short)0, (short)9, (short)1, (short)1, (short)2, (short)2, (short)10, (short)0, (short)9, (short)1, (short)1, (short)2, (short)2, (short)11,
            (short)0, (short)9, (short)1, (short)1, (short)3, (short)1, (short)1, (short)0, (short)11, (short)1, (short)1, (short)1, (short)1, (short)10, (short)0, (short)11,
            (short)1, (short)1, (short)1, (short)1, (short)0, (short)0, (short)12, (short)2, (short)1, (short)3, (short)0, (short)2, (short)0, (short)12, (short)3, (short)1,
            (short)1, (short)1, (short)5, (short)0, (short)12, (short)2, (short)1, (short)1, (short)3, (short)7, (short)0, (short)12, (short)3, (short)1, (short)1, (short)1,
            (short)10, (short)0, (short)12, (short)2, (short)1, (short)3, (short)0, (short)2, (short)0, (short)13, (short)2, (short)1, (short)2, (short)0, (short)4, (short)0,
            (short)13, (short)4, (short)1, (short)1, (short)5, (short)8, (short)0, (short)13, (short)2, (short)1, (short)2, (short)0, (short)4, (short)0, (short)14, (short)1,
            (short)1, (short)1, (short)0, (short)5, (short)0, (short)14, (short)2, (short)1, (short)1, (short)5, (short)7, (short)0, (short)14, (short)1, (short)1, (short)1,
            (short)0, (short)0, (short)1, (short)0, (short)3, (short)8, (short)1, (short)6, (short)3, (short)1, (short)0, (short)1, (short)4, (short)1, (short)7, (short)4,
            (short)1, (short)0, (short)4, (short)1, (short)1, (short)3, (short)8, (short)1, (short)0, (short)1, (short)4, (short)1, (short)7, (short)9, (short)1, (short)0,
            (short)3, (short)8, (short)1, (short)6, (short)0, (short)1, (short)1, (short)2, (short)8, (short)6, (short)6, (short)2, (short)1, (short)1, (short)1, (short)1,
            (short)1, (short)8, (short)3, (short)1, (short)1, (short)1, (short)1, (short)2, (short)3, (short)4, (short)1, (short)1, (short)1, (short)2, (short)1, (short)9,
            (short)5, (short)1, (short)1, (short)1, (short)1, (short)1, (short)10, (short)6, (short)1, (short)1, (short)1, (short)3, (short)5, (short)6, (short)7, (short)1,
            (short)1, (short)1, (short)1, (short)1, (short)11, (short)8, (short)1, (short)1, (short)1, (short)1, (short)2, (short)3, (short)9, (short)1, (short)1, (short)1,
            (short)1, (short)1, (short)12, (short)10, (short)1, (short)1, (short)2, (short)8, (short)6, (short)6, (short)2, (short)1, (short)2, (short)1, (short)1, (short)1,
            (short)13, (short)4, (short)1, (short)2, (short)2, (short)3, (short)4, (short)6, (short)7, (short)1, (short)2, (short)1, (short)1, (short)1, (short)14, (short)9,
            (short)1, (short)2, (short)1, (short)1, (short)1, (short)15, (short)2, (short)1, (short)3, (short)1, (short)5, (short)1, (short)7, (short)3, (short)1, (short)3,
            (short)1, (short)1, (short)1, (short)16, (short)7, (short)1, (short)3, (short)1, (short)4, (short)4, (short)6, (short)8, (short)1, (short)3, (short)1, (short)1,
            (short)1, (short)16, (short)9, (short)1, (short)3, (short)1, (short)5, (short)1, (short)7, (short)2, (short)1, (short)4, (short)1, (short)1, (short)1, (short)3,
            (short)3, (short)1, (short)4, (short)1, (short)3, (short)7, (short)6, (short)8, (short)1, (short)4, (short)1, (short)1, (short)8, (short)6, (short)9, (short)1,
            (short)4, (short)1, (short)1, (short)1, (short)3, (short)2, (short)1, (short)5, (short)1, (short)1, (short)1, (short)17, (short)9, (short)1, (short)5, (short)1,
            (short)1, (short)1, (short)18, (short)2, (short)1, (short)6, (short)1, (short)1, (short)1, (short)3, (short)4, (short)1, (short)6, (short)1, (short)4, (short)1,
            (short)6, (short)5, (short)1, (short)6, (short)2, (short)1, (short)1, (short)19, (short)9, (short)1, (short)6, (short)1, (short)1, (short)1, (short)3, (short)0,
            (short)1, (short)7, (short)1, (short)8, (short)1, (short)6, (short)1, (short)1, (short)7, (short)1, (short)1, (short)1, (short)20, (short)2, (short)1, (short)7,
            (short)1, (short)6, (short)1, (short)7, (short)4, (short)1, (short)7, (short)1, (short)1, (short)2, (short)19, (short)5, (short)1, (short)7, (short)2, (short)1,
            (short)2, (short)21, (short)7, (short)1, (short)7, (short)1, (short)1, (short)2, (short)19, (short)9, (short)1, (short)7, (short)1, (short)6, (short)1, (short)7,
            (short)10, (short)1, (short)7, (short)1, (short)1, (short)1, (short)20, (short)11, (short)1, (short)7, (short)1, (short)8, (short)1, (short)6, (short)0, (short)1,
            (short)8, (short)1, (short)4, (short)1, (short)7, (short)1, (short)1, (short)8, (short)1, (short)1, (short)1, (short)3, (short)2, (short)1, (short)8, (short)1,
            (short)1, (short)1, (short)22, (short)9, (short)1, (short)8, (short)1, (short)3, (short)1, (short)6, (short)10, (short)1, (short)8, (short)1, (short)1, (short)1,
            (short)3, (short)11, (short)1, (short)8, (short)1, (short)4, (short)1, (short)7, (short)0, (short)1, (short)9, (short)1, (short)1, (short)2, (short)3, (short)1,
            (short)1, (short)9, (short)1, (short)1, (short)2, (short)23, (short)2, (short)1, (short)9, (short)1, (short)5, (short)1, (short)6, (short)4, (short)1, (short)9,
            (short)1, (short)5, (short)3, (short)6, (short)5, (short)1, (short)9, (short)2, (short)1, (short)1, (short)19, (short)7, (short)1, (short)9, (short)1, (short)5,
            (short)3, (short)6, (short)9, (short)1, (short)9, (short)1, (short)1, (short)1, (short)24, (short)10, (short)1, (short)9, (short)1, (short)1, (short)2, (short)25,
            (short)11, (short)1, (short)9, (short)1, (short)1, (short)2, (short)3, (short)2, (short)1, (short)10, (short)1, (short)1, (short)1, (short)26, (short)5, (short)1,
            (short)10, (short)2, (short)3, (short)2, (short)6, (short)9, (short)1, (short)10, (short)1, (short)5, (short)1, (short)6, (short)0, (short)1, (short)11, (short)1,
            (short)4, (short)1, (short)7, (short)1, (short)1, (short)11, (short)1, (short)1, (short)1, (short)3, (short)2, (short)1, (short)11, (short)1, (short)1, (short)1,
            (short)27, (short)3, (short)1, (short)11, (short)1, (short)1, (short)1, (short)26, (short)9, (short)1, (short)11, (short)1, (short)1, (short)1, (short)27, (short)10,
            (short)1, (short)11, (short)1, (short)1, (short)1, (short)3, (short)11, (short)1, (short)11, (short)1, (short)4, (short)1, (short)7, (short)0, (short)1, (short)12,
            (short)1, (short)8, (short)3, (short)6, (short)1, (short)1, (short)12, (short)1, (short)1, (short)1, (short)20, (short)2, (short)1, (short)12, (short)1, (short)6,
            (short)1, (short)7, (short)3, (short)1, (short)12, (short)1, (short)1, (short)1, (short)3, (short)4, (short)1, (short)12, (short)1, (short)3, (short)1, (short)7,
            (short)5, (short)1, (short)12, (short)2, (short)1, (short)1, (short)28, (short)7, (short)1, (short)12, (short)1, (short)3, (short)1, (short)7, (short)8, (short)1,
            (short)12, (short)1, (short)1, (short)1, (short)3, (short)9, (short)1, (short)12, (short)1, (short)6, (short)1, (short)7, (short)10, (short)1, (short)12, (short)1,
            (short)1, (short)1, (short)20, (short)11, (short)1, (short)12, (short)1, (short)8, (short)3, (short)6, (short)1, (short)1, (short)13, (short)10, (short)3, (short)2,
            (short)6, (short)4, (short)2, (short)0, (short)1, (short)2, (short)1, (short)2, (short)5, (short)2, (short)0, (short)2, (short)2, (short)1, (short)29, (short)7,
            (short)2, (short)0, (short)1, (short)2, (short)1, (short)2, (short)2, (short)2, (short)1, (short)1, (short)7, (short)2, (short)6, (short)3, (short)2, (short)1,
            (short)1, (short)2, (short)2, (short)2, (short)5, (short)2, (short)1, (short)1, (short)2, (short)1, (short)6, (short)7, (short)2, (short)1, (short)1, (short)2,
            (short)2, (short)6, (short)8, (short)2, (short)1, (short)1, (short)2, (short)2, (short)2, (short)9, (short)2, (short)1, (short)1, (short)7, (short)2, (short)6,
            (short)3, (short)2, (short)3, (short)1, (short)1, (short)1, (short)30, (short)8, (short)2, (short)3, (short)1, (short)1, (short)1, (short)31, (short)2, (short)2,
            (short)4, (short)1, (short)1, (short)1, (short)4, (short)8, (short)2, (short)4, (short)1, (short)2, (short)7, (short)6, (short)9, (short)2, (short)4, (short)1,
            (short)1, (short)1, (short)4, (short)2, (short)2, (short)5, (short)1, (short)1, (short)1, (short)32, (short)9, (short)2, (short)5, (short)1, (short)1, (short)1,
            (short)32, (short)2, (short)2, (short)6, (short)1, (short)1, (short)1, (short)4, (short)5, (short)2, (short)6, (short)2, (short)3, (short)4, (short)6, (short)9,
            (short)2, (short)6, (short)1, (short)1, (short)1, (short)4, (short)1, (short)2, (short)7, (short)1, (short)2, (short)1, (short)33, (short)4, (short)2, (short)7,
            (short)1, (short)4, (short)2, (short)6, (short)7, (short)2, (short)7, (short)1, (short)4, (short)2, (short)6, (short)10, (short)2, (short)7, (short)1, (short)2,
            (short)1, (short)33, (short)1, (short)2, (short)8, (short)1, (short)2, (short)1, (short)4, (short)2, (short)2, (short)8, (short)1, (short)1, (short)1, (short)10,
            (short)10, (short)2, (short)8, (short)1, (short)2, (short)1, (short)4, (short)0, (short)2, (short)9, (short)1, (short)2, (short)2, (short)4, (short)1, (short)2,
            (short)9, (short)1, (short)1, (short)1, (short)34, (short)9, (short)2, (short)9, (short)2, (short)3, (short)1, (short)6, (short)11, (short)2, (short)9, (short)1,
            (short)2, (short)2, (short)4, (short)1, (short)2, (short)10, (short)1, (short)1, (short)1, (short)22, (short)2, (short)2, (short)10, (short)1, (short)1, (short)1,
            (short)35, (short)10, (short)2, (short)10, (short)1, (short)1, (short)1, (short)36, (short)1, (short)2, (short)11, (short)1, (short)2, (short)1, (short)4, (short)2,
            (short)2, (short)11, (short)1, (short)2, (short)1, (short)26, (short)3, (short)2, (short)11, (short)1, (short)1, (short)1, (short)27, (short)8, (short)2, (short)11,
            (short)1, (short)1, (short)1, (short)37, (short)9, (short)2, (short)11, (short)1, (short)1, (short)1, (short)9, (short)10, (short)2, (short)11, (short)1, (short)2,
            (short)1, (short)4, (short)1, (short)2, (short)12, (short)1, (short)2, (short)1, (short)33, (short)3, (short)2, (short)12, (short)1, (short)2, (short)1, (short)4,
            (short)5, (short)2, (short)12, (short)1, (short)1, (short)1, (short)38, (short)6, (short)2, (short)12, (short)1, (short)1, (short)1, (short)32, (short)8, (short)2,
            (short)12, (short)1, (short)2, (short)1, (short)4, (short)10, (short)2, (short)12, (short)1, (short)2, (short)1, (short)33, (short)4, (short)3, (short)1, (short)1,
            (short)1, (short)1, (short)6, (short)3, (short)3, (short)3, (short)1, (short)1, (short)1, (short)6, (short)8, (short)3, (short)3, (short)1, (short)1, (short)1,
            (short)6, (short)2, (short)3, (short)4, (short)1, (short)1, (short)3, (short)39, (short)9, (short)3, (short)4, (short)1, (short)1, (short)3, (short)39, (short)2,
            (short)3, (short)8, (short)1, (short)1, (short)1, (short)6, (short)1, (short)3, (short)9, (short)1, (short)1, (short)1, (short)40, (short)1, (short)3, (short)10,
            (short)1, (short)1, (short)1, (short)34, (short)2, (short)3, (short)10, (short)1, (short)3, (short)1, (short)6, (short)10, (short)3, (short)10, (short)1, (short)2,
            (short)1, (short)6, (short)3, (short)3, (short)11, (short)1, (short)1, (short)1, (short)41, (short)8, (short)3, (short)11, (short)1, (short)1, (short)1, (short)9,
            (short)9, (short)3, (short)11, (short)1, (short)1, (short)1, (short)6, (short)5, (short)3, (short)12, (short)2, (short)1, (short)1, (short)42, (short)4, (short)4,
            (short)0, (short)4, (short)1, (short)1, (short)42, (short)3, (short)4, (short)1, (short)1, (short)1, (short)2, (short)39, (short)4, (short)4, (short)1, (short)4,
            (short)1, (short)1, (short)43, (short)8, (short)4, (short)1, (short)1, (short)1, (short)2, (short)39, (short)4, (short)4, (short)2, (short)4, (short)1, (short)1,
            (short)2, (short)3, (short)4, (short)3, (short)1, (short)1, (short)1, (short)16, (short)4, (short)4, (short)3, (short)3, (short)1, (short)3, (short)6, (short)8,
            (short)4, (short)3, (short)1, (short)1, (short)1, (short)16, (short)2, (short)4, (short)4, (short)1, (short)1, (short)1, (short)2, (short)3, (short)4, (short)4,
            (short)1, (short)1, (short)1, (short)6, (short)8, (short)4, (short)4, (short)1, (short)1, (short)1, (short)6, (short)9, (short)4, (short)4, (short)1, (short)1,
            (short)1, (short)2, (short)2, (short)4, (short)5, (short)1, (short)1, (short)1, (short)29, (short)3, (short)4, (short)5, (short)1, (short)1, (short)1, (short)35,
            (short)8, (short)4, (short)5, (short)1, (short)1, (short)1, (short)35, (short)9, (short)4, (short)5, (short)1, (short)1, (short)1, (short)29, (short)2, (short)4,
            (short)6, (short)1, (short)1, (short)1, (short)2, (short)3, (short)4, (short)6, (short)1, (short)1, (short)5, (short)6, (short)8, (short)4, (short)6, (short)1,
            (short)1, (short)5, (short)6, (short)9, (short)4, (short)6, (short)1, (short)1, (short)1, (short)2, (short)1, (short)4, (short)7, (short)1, (short)1, (short)1,
            (short)44, (short)10, (short)4, (short)7, (short)1, (short)1, (short)1, (short)44, (short)1, (short)4, (short)8, (short)1, (short)1, (short)1, (short)42, (short)2,
            (short)4, (short)8, (short)1, (short)1, (short)1, (short)23, (short)9, (short)4, (short)8, (short)1, (short)1, (short)1, (short)25, (short)10, (short)4, (short)8,
            (short)1, (short)1, (short)1, (short)42, (short)0, (short)4, (short)9, (short)1, (short)1, (short)2, (short)39, (short)1, (short)4, (short)9, (short)1, (short)1,
            (short)1, (short)22, (short)11, (short)4, (short)9, (short)1, (short)1, (short)2, (short)39, (short)1, (short)4, (short)10, (short)1, (short)1, (short)1, (short)6,
            (short)5, (short)4, (short)10, (short)2, (short)2, (short)1, (short)6, (short)1, (short)4, (short)11, (short)1, (short)1, (short)1, (short)42, (short)2, (short)4,
            (short)11, (short)1, (short)1, (short)1, (short)23, (short)3, (short)4, (short)11, (short)1, (short)1, (short)1, (short)37, (short)5, (short)4, (short)11, (short)2,
            (short)1, (short)1, (short)37, (short)8, (short)4, (short)11, (short)1, (short)1, (short)1, (short)37, (short)9, (short)4, (short)11, (short)1, (short)1, (short)1,
            (short)25, (short)10, (short)4, (short)11, (short)1, (short)1, (short)1, (short)42, (short)1, (short)4, (short)12, (short)1, (short)1, (short)1, (short)45, (short)3,
            (short)4, (short)12, (short)6, (short)1, (short)1, (short)2, (short)10, (short)4, (short)12, (short)1, (short)1, (short)1, (short)45, (short)1, (short)4, (short)13,
            (short)3, (short)5, (short)2, (short)6, (short)4, (short)4, (short)13, (short)4, (short)1, (short)1, (short)46, (short)8, (short)4, (short)13, (short)3, (short)5,
            (short)2, (short)6, (short)4, (short)4, (short)14, (short)4, (short)5, (short)1, (short)6, (short)3, (short)5, (short)0, (short)1, (short)1, (short)2, (short)46,
            (short)4, (short)5, (short)0, (short)4, (short)4, (short)2, (short)6, (short)8, (short)5, (short)0, (short)1, (short)1, (short)2, (short)46, (short)3, (short)5,
            (short)2, (short)1, (short)1, (short)1, (short)44, (short)4, (short)5, (short)2, (short)4, (short)1, (short)1, (short)46, (short)8, (short)5, (short)2, (short)1,
            (short)1, (short)1, (short)44, (short)3, (short)5, (short)3, (short)6, (short)1, (short)1, (short)42, (short)2, (short)5, (short)4, (short)1, (short)1, (short)3,
            (short)39, (short)3, (short)5, (short)4, (short)2, (short)1, (short)2, (short)43, (short)5, (short)5, (short)4, (short)2, (short)1, (short)2, (short)2, (short)7,
            (short)5, (short)4, (short)2, (short)1, (short)2, (short)43, (short)9, (short)5, (short)4, (short)1, (short)1, (short)3, (short)39, (short)3, (short)5, (short)6,
            (short)6, (short)1, (short)1, (short)16, (short)1, (short)5, (short)7, (short)1, (short)4, (short)1, (short)6, (short)3, (short)5, (short)7, (short)1, (short)1,
            (short)4, (short)6, (short)5, (short)5, (short)7, (short)2, (short)1, (short)3, (short)6, (short)8, (short)5, (short)7, (short)1, (short)1, (short)4, (short)6,
            (short)10, (short)5, (short)7, (short)1, (short)4, (short)1, (short)6, (short)0, (short)5, (short)8, (short)1, (short)1, (short)1, (short)47, (short)1, (short)5,
            (short)8, (short)1, (short)1, (short)1, (short)27, (short)2, (short)5, (short)8, (short)1, (short)1, (short)1, (short)2, (short)9, (short)5, (short)8, (short)1,
            (short)1, (short)1, (short)2, (short)10, (short)5, (short)8, (short)1, (short)1, (short)1, (short)27, (short)11, (short)5, (short)8, (short)1, (short)1, (short)1,
            (short)24, (short)0, (short)5, (short)9, (short)1, (short)4, (short)2, (short)6, (short)1, (short)5, (short)9, (short)1, (short)1, (short)2, (short)43, (short)9,
            (short)5, (short)9, (short)1, (short)1, (short)1, (short)6, (short)10, (short)5, (short)9, (short)1, (short)1, (short)2, (short)43, (short)11, (short)5, (short)9,
            (short)1, (short)4, (short)2, (short)6, (short)0, (short)5, (short)11, (short)1, (short)1, (short)1, (short)47, (short)1, (short)5, (short)11, (short)1, (short)1,
            (short)1, (short)16, (short)2, (short)5, (short)11, (short)1, (short)1, (short)1, (short)2, (short)3, (short)5, (short)11, (short)1, (short)1, (short)1, (short)9,
            (short)5, (short)5, (short)11, (short)1, (short)1, (short)1, (short)41, (short)6, (short)5, (short)11, (short)1, (short)1, (short)1, (short)9, (short)8, (short)5,
            (short)11, (short)1, (short)1, (short)1, (short)48, (short)9, (short)5, (short)11, (short)1, (short)1, (short)1, (short)2, (short)10, (short)5, (short)11, (short)1,
            (short)1, (short)1, (short)16, (short)11, (short)5, (short)11, (short)1, (short)1, (short)1, (short)24, (short)1, (short)5, (short)12, (short)1, (short)4, (short)1,
            (short)6, (short)3, (short)5, (short)12, (short)1, (short)1, (short)1, (short)2, (short)4, (short)5, (short)12, (short)4, (short)1, (short)1, (short)29, (short)8,
            (short)5, (short)12, (short)1, (short)1, (short)1, (short)2, (short)10, (short)5, (short)12, (short)1, (short)4, (short)1, (short)6, (short)4, (short)5, (short)13,
            (short)4, (short)4, (short)1, (short)6, (short)3, (short)6, (short)0, (short)1, (short)3, (short)7, (short)6, (short)8, (short)6, (short)0, (short)1, (short)3,
            (short)7, (short)6, (short)4, (short)6, (short)2, (short)4, (short)3, (short)2, (short)6, (short)2, (short)6, (short)3, (short)1, (short)1, (short)3, (short)46,
            (short)9, (short)6, (short)3, (short)1, (short)1, (short)3, (short)46, (short)4, (short)6, (short)4, (short)1, (short)3, (short)3, (short)6, (short)5, (short)6,
            (short)4, (short)2, (short)1, (short)2, (short)46, (short)7, (short)6, (short)4, (short)1, (short)3, (short)3, (short)6, (short)2, (short)6, (short)6, (short)1,
            (short)1, (short)1, (short)44, (short)5, (short)6, (short)6, (short)2, (short)1, (short)1, (short)44, (short)9, (short)6, (short)6, (short)1, (short)1, (short)1,
            (short)44, (short)3, (short)6, (short)7, (short)6, (short)1, (short)1, (short)42, (short)0, (short)6, (short)8, (short)1, (short)3, (short)1, (short)6, (short)1,
            (short)6, (short)8, (short)1, (short)1, (short)1, (short)47, (short)2, (short)6, (short)8, (short)1, (short)1, (short)4, (short)39, (short)3, (short)6, (short)8,
            (short)1, (short)1, (short)4, (short)37, (short)4, (short)6, (short)8, (short)1, (short)1, (short)4, (short)39, (short)5, (short)6, (short)8, (short)2, (short)1,
            (short)1, (short)44, (short)7, (short)6, (short)8, (short)1, (short)1, (short)4, (short)39, (short)8, (short)6, (short)8, (short)1, (short)1, (short)4, (short)37,
            (short)9, (short)6, (short)8, (short)1, (short)1, (short)4, (short)39, (short)10, (short)6, (short)8, (short)1, (short)1, (short)1, (short)24, (short)11, (short)6,
            (short)8, (short)1, (short)3, (short)1, (short)6, (short)1, (short)6, (short)9, (short)1, (short)3, (short)2, (short)6, (short)5, (short)6, (short)9, (short)2,
            (short)1, (short)2, (short)43, (short)10, (short)6, (short)9, (short)1, (short)3, (short)2, (short)6, (short)0, (short)6, (short)11, (short)1, (short)3, (short)1,
            (short)6, (short)1, (short)6, (short)11, (short)1, (short)1, (short)1, (short)47, (short)5, (short)6, (short)11, (short)2, (short)1, (short)1, (short)45, (short)10,
            (short)6, (short)11, (short)1, (short)1, (short)1, (short)24, (short)11, (short)6, (short)11, (short)1, (short)3, (short)1, (short)6, (short)3, (short)6, (short)12,
            (short)6, (short)1, (short)1, (short)42, (short)2, (short)7, (short)3, (short)1, (short)2, (short)4, (short)6, (short)9, (short)7, (short)3, (short)1, (short)2,
            (short)4, (short)6, (short)5, (short)7, (short)4, (short)2, (short)2, (short)9, (short)6, (short)2, (short)7, (short)7, (short)1, (short)1, (short)1, (short)46,
            (short)3, (short)7, (short)7, (short)1, (short)2, (short)6, (short)6, (short)4, (short)7, (short)7, (short)1, (short)1, (short)1, (short)44, (short)7, (short)7,
            (short)7, (short)1, (short)1, (short)1, (short)44, (short)8, (short)7, (short)7, (short)1, (short)2, (short)6, (short)6, (short)9, (short)7, (short)7, (short)1,
            (short)1, (short)1, (short)46, (short)1, (short)7, (short)8, (short)2, (short)2, (short)1, (short)6, (short)4, (short)7, (short)8, (short)1, (short)2, (short)4,
            (short)6, (short)7, (short)7, (short)8, (short)1, (short)2, (short)4, (short)6, (short)9, (short)7, (short)8, (short)2, (short)2, (short)1, (short)6, (short)2,
            (short)7, (short)9, (short)1, (short)1, (short)2, (short)46, (short)9, (short)7, (short)9, (short)1, (short)1, (short)2, (short)46, (short)1, (short)7, (short)11,
            (short)2, (short)2, (short)1, (short)6, (short)9, (short)7, (short)11, (short)2, (short)2, (short)1, (short)6, (short)2, (short)7, (short)12, (short)1, (short)1,
            (short)1, (short)46, (short)4, (short)7, (short)12, (short)1, (short)1, (short)1, (short)45, (short)7, (short)7, (short)12, (short)1, (short)1, (short)1, (short)45,
            (short)9, (short)7, (short)12, (short)1, (short)1, (short)1, (short)46, (short)2, (short)8, (short)7, (short)1, (short)1, (short)1, (short)6, (short)4, (short)8,
            (short)7, (short)1, (short)1, (short)1, (short)49, (short)7, (short)8, (short)7, (short)1, (short)1, (short)1, (short)49, (short)9, (short)8, (short)7, (short)1,
            (short)1, (short)1, (short)6, (short)2, (short)8, (short)9, (short)1, (short)1, (short)2, (short)6, (short)9, (short)8, (short)9, (short)1, (short)1, (short)2,
            (short)6, (short)2, (short)8, (short)12, (short)1, (short)1, (short)1, (short)6, (short)4, (short)8, (short)12, (short)1, (short)1, (short)1, (short)49, (short)7,
            (short)8, (short)12, (short)1, (short)1, (short)1, (short)49, (short)9, (short)8, (short)12, (short)1, (short)1, (short)1, (short)6
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
        } else if (Integration.automagy) {
            VengefulGolem thaumaturge = new VengefulGolem(world);
            thaumaturge.func_110163_bv();
            thaumaturge.setLocationAndAngles(x + (double) getWidth() / 2, y + 2, z + (double) getLength() / 2, 0, 0);
            thaumaturge.onSpawnWithEgg(null);
            world.spawnEntityInWorld(thaumaturge);
        }

        return true;
    }


    public static int getWidth() { return 12; }
    public static int getHeight() { return 9; }
    public static int getLength() { return 15; }

    /*
     * TILE ENTITY TEMPLATE STUBS - SEARCHABLE / MANUAL
     *
     * These are intentionally comments. Copy the useful data into real 1.7.10
     * TileEntity setup code after block placement. Names and coordinates are
     * preserved so chests, spawners, signs, mod machines, etc. can be restored
     * manually without guessing which block each tile belongs to.
     */

    // TILE_ENTITY_000
    // id: TileCrystal
    // block: Thaumcraft:blockCrystal meta 2 numericId 183
    // pos: (3, 2, 3), world: (x + 3, y + 2, z + 3)
    // TODO TILE STUB NAME: TileCrystal
    // TODO BLOCK STUB NAME: Thaumcraft:blockCrystal
    // TODO example: TileEntity te = world.getTileEntity(x + 3, y + 2, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     orientation: 1
    //     x: 3
    //     y: 2
    //     z: 3
    //     id: 'TileCrystal'
    //   }

    // TILE_ENTITY_001
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (4, 8, 7), world: (x + 4, y + 8, z + 7)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 8, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 4
    //     y: 8
    //     z: 7
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_002
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (4, 8, 12), world: (x + 4, y + 8, z + 12)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 4, y + 8, z + 12);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 4
    //     y: 8
    //     z: 12
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_003
    // id: TileNitor
    // block: Thaumcraft:blockAiry meta 1 numericId 198
    // pos: (7, 8, 7), world: (x + 7, y + 8, z + 7)
    // TODO TILE STUB NAME: TileNitor
    // TODO BLOCK STUB NAME: Thaumcraft:blockAiry
    // TODO example: TileEntity te = world.getTileEntity(x + 7, y + 8, z + 7);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     x: 7
    //     y: 8
    //     z: 7
    //     id: 'TileNitor'
    //   }

    // TILE_ENTITY_004
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

    // TILE_ENTITY_005
    // id: FlowerPot
    // block: minecraft:flower_pot meta 0 numericId 140
    // pos: (8, 2, 3), world: (x + 8, y + 2, z + 3)
    // TODO TILE STUB NAME: FlowerPot
    // TODO BLOCK STUB NAME: minecraft:flower_pot
    // TODO example: TileEntity te = world.getTileEntity(x + 8, y + 2, z + 3);
    // TODO fill fields from the NBT preview below, or replace with custom mod API calls.
    //   NBT: compound {
    //     Item: 6
    //     x: 8
    //     y: 2
    //     z: 3
    //     Data: 0
    //     id: 'FlowerPot'
    //   }
}
