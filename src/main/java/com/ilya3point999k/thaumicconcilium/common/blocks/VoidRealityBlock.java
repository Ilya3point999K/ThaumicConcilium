package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoidRealityBlock extends Block {
    public VoidRealityBlock() {
        super(Material.cloth);
        this.setResistance(6000000);
        this.setBlockUnbreakable();
        this.setStepSound(soundTypeCloth);
        this.setBlockTextureName(ThaumicConcilium.MODID + ":void");
    }

    @Override
    public float getExplosionResistance(Entity p_149638_1_) {
        return super.getExplosionResistance(p_149638_1_);
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

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }
}
