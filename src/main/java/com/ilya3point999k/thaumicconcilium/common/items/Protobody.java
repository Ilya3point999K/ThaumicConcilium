package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.items.EntityItemProtobody;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Protobody extends Item {
    public Protobody(){
        this.setUnlocalizedName("Protobody");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.rare;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        EntityItemProtobody item = new EntityItemProtobody(world, location.posX, location.posY, location.posZ, itemstack);
        item.motionX = location.motionX;
        item.motionY = location.motionY;
        item.motionZ = location.motionZ;
        item.delayBeforeCanPickup = 40;
        return item;
    }


}
