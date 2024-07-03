package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.RedPoweredMindTile;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RedPoweredMind extends EntityLiving {
    public RedPoweredMind(World world) {
        super(world);
        setSize(1.0F, 1.0F);
    }
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int x = MathHelper.floor_double(posX);
        int y = MathHelper.floor_double(posY);
        int z = MathHelper.floor_double(posZ);

        if (worldObj.isAirBlock(x, y, z)){
            worldObj.setBlockToAir(x, y, z);
            worldObj.removeTileEntity(x, y, z);
            RedPoweredMindTile mind = new RedPoweredMindTile();
            worldObj.setBlock(x, y, z, TCBlockRegistry.RED_POWERED_MIND_BLOCK);
            worldObj.setTileEntity(x, y, z, mind);
            this.setDead();
        } else {
            this.setHealth(getHealth() - 1.0F);
        }
    }
}
