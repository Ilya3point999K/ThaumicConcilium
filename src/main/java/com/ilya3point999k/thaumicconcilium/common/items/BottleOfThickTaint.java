package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.BottleOfThickTaintEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BottleOfThickTaint extends Item {
    public BottleOfThickTaint(ToolMaterial material) {
        this.setTextureName(ThaumicConcilium.MODID+":thicc_bottle");
        this.setUnlocalizedName("BottleOfThickTaint");
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
            world.spawnEntityInWorld(new BottleOfThickTaintEntity(world, player));
        }
        return stack;
    }
}
