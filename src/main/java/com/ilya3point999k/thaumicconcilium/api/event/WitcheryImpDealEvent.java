package com.ilya3point999k.thaumicconcilium.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class WitcheryImpDealEvent extends Event {

    public final EntityPlayer impOwner;
    public final ItemStack stack;
    public final EntityLivingBase target;

    public WitcheryImpDealEvent(EntityPlayer impOwner, ItemStack stack, EntityLivingBase target) {
        super();
        this.impOwner = impOwner;
        this.stack = stack;
        this.target = target;
    }

    public static void fireImpDealEvent(EntityPlayer impOwner, ItemStack stack, EntityLivingBase target) {
        MinecraftForge.EVENT_BUS.post(new WitcheryImpDealEvent(impOwner, stack, target));
    }
}