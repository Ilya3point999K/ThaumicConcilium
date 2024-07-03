package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.Thaumonomicon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockHardenedClay;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class VoidRealityBlock extends Block {
    public VoidRealityBlock() {
        super(Material.cloth);
        this.setStepSound(soundTypeCloth);
        this.setBlockTextureName(ThaumicConcilium.MODID + ":void");
    }


    @Override
    public int getRenderBlockPass ()
    {
        return 0;
    }
    @Override
    public boolean isOpaqueCube ()
    {
        return true;
    }
    @Override
    public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_) {
        return -1;
    }
}
