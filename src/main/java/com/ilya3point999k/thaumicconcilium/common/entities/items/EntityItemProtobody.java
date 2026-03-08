package com.ilya3point999k.thaumicconcilium.common.entities.items;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse.Protolimb;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumic.tinkerer.common.core.helper.MiscHelper;

public class EntityItemProtobody extends EntityItem {
    public EntityItemProtobody(World w){
        super(w);
    }

    public EntityItemProtobody(World w, double x, double y, double z, ItemStack stack) {
        super(w, x, y, z, stack);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!worldObj.isRemote) {
            if (Integration.taintedMagic && posY < -16.0) {
                for (int i = 0; i < 6; i++) {
                    Protolimb protolimb = new Protolimb(this.worldObj, i);
                    protolimb.setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
                    MiscHelper.setEntityMotionFromVector(this, new Vector3(this.posX + (-0.5 + worldObj.rand.nextGaussian()) * 2.0, this.posY + 1.0, this.posZ + (-0.5 + worldObj.rand.nextGaussian()) * 2.0), 0.5f);
                    this.worldObj.spawnEntityInWorld(protolimb);
                    protolimb.setType(i);
                }
                this.setDead();
            }
        }
    }
}