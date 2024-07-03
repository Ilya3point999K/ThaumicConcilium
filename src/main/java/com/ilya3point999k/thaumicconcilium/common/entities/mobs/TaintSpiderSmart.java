package com.ilya3point999k.thaumicconcilium.common.entities.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import thaumcraft.common.entities.monster.EntityTaintSpider;

public class TaintSpiderSmart extends EntityTaintSpider {
    public TaintSpiderSmart(World par1World) {
        super(par1World);
    }

    @Override
    protected Entity findPlayerToAttack() {
        return null;
    }
    @Override
    protected Item getDropItem() {
        return Item.getItemById(0);
    }
    @Override
    protected void dropFewItems(boolean flag, int i) {

    }
}
