package com.ilya3point999k.thaumicconcilium.common.entities.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemFireResistant extends EntityItem {
    public EntityItemFireResistant(World w){
        super(w);
    }

    public EntityItemFireResistant(World w, double x, double y, double z, ItemStack stack) {
        super(w, x, y, z, stack);
        isImmuneToFire = true;
    }


    @Override
    protected void setOnFireFromLava() {

    }

    @Override
    public void setFire(int p_70015_1_) {

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float f) {
        return !source.isFireDamage() && super.attackEntityFrom(source, f);
    }
}
