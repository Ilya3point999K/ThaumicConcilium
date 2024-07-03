package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.BottleOfClearWaterEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BottleOfClearWater extends Item {

    public BottleOfClearWater(ToolMaterial material) {
        this.setTextureName(ThaumicConcilium.MODID+":water_bottle");
        this.setUnlocalizedName("BottleOfClearWater");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        //MinecraftForge.EVENT_BUS.register(this);
        //FMLCommonHandler.instance().bus().register(this);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!player.capabilities.isCreativeMode){
            --stack.stackSize;
        }
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if(!world.isRemote){
            world.spawnEntityInWorld(new BottleOfClearWaterEntity(world, player));
        }
        return stack;
    }
}