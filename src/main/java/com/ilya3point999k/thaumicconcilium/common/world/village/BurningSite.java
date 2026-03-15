package com.ilya3point999k.thaumicconcilium.common.world.village;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.entity.EntityWitchHunter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class BurningSite extends StructureVillagePieces.Village {
    private int averageGroundLevel = -1;

    public BurningSite() {}

    public BurningSite(StructureVillagePieces.Start start, int type,
                       Random rand, StructureBoundingBox box, int coordBaseMode) {
        super(start, type);
        this.coordBaseMode = coordBaseMode;
        this.boundingBox = box;
    }

    public static BurningSite buildComponent(
            StructureVillagePieces.Start start,
            List pieces,
            Random rand,
            int x, int y, int z,
            int coordBaseMode,
            int type) {

        StructureBoundingBox box = StructureBoundingBox.getComponentToAddBoundingBox(
                x, y, z,
                0, 0, 0,
                9, 4, 9,
                coordBaseMode
        );

        if (!canVillageGoDeeper(box) || StructureComponent.findIntersecting(pieces, box) != null)
            return null;

        return new BurningSite(start, type, rand, box, coordBaseMode);
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(world, box);

            if (this.averageGroundLevel < 0)
                return true;

            this.boundingBox.offset(
                    0,
                    this.averageGroundLevel - this.boundingBox.maxY + 4 - 2,
                    0
            );
        }

        for (int x = 0; x <= 8; x++) {
            for (int y = 0; y <= 3; y++) {
                for (int z = 0; z <= 8; z++) {
                    this.placeBlockAtCurrentPosition(world, Blocks.air, 0, x, y, z, box);
                }
            }
        }


        this.fillWithBlocks(world, box,
                0, 0, 0,
                8, 0, 8,
                Blocks.gravel,
                Blocks.gravel,
                false);
        this.fillWithBlocks(world, box,
                0, -1, 0,
                8, -1, 8,
                Blocks.dirt,
                Blocks.dirt,
                false);

        for (int x = 3; x <= 5; x++) {
            for (int z = 3; z <= 5; z++) {

                Block block;
                int r = rand.nextInt(3);

                if (r == 0)
                    block = Blocks.coal_block;
                else if (r == 1)
                    block = Blocks.hay_block;
                else
                    block = Blocks.log;

                this.placeBlockAtCurrentPosition(world, block, 0, x, 0, z, box);
            }
        }

        for (int y = 1; y <= 4; y++) {
            this.placeBlockAtCurrentPosition(world,
                    Witchery.Blocks.STOCKADE,
                    0,
                    4, y, 4,
                    box);
        }

        for (int i = 0; i <= 8; i++) {
            this.placeBlockAtCurrentPosition(world, Blocks.cobblestone_wall, 0, i, 1, 0, box);
            this.placeBlockAtCurrentPosition(world, Blocks.cobblestone_wall, 0, i, 1, 8, box);
            this.placeBlockAtCurrentPosition(world, Blocks.cobblestone_wall, 0, 0, 1, i, box);
            this.placeBlockAtCurrentPosition(world, Blocks.cobblestone_wall, 0, 8, 1, i, box);
        }

        int wx = this.getXWithOffset(2, 2);
        int wy = this.getYWithOffset(1);
        int wz = this.getZWithOffset(2, 2);

        EntityWitchHunter witchHunter = new EntityWitchHunter(world);
        witchHunter.func_110163_bv();
        witchHunter.onSpawnWithEgg(null);
        witchHunter.setLocationAndAngles(wx + 0.5, wy, wz + 0.5, 0, 0);

        world.spawnEntityInWorld(witchHunter);

        return true;
    }
}
