package com.ilya3point999k.thaumicconcilium.common.golems;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.golems.ai.GolemValetAI;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import makeo.gadomancy.api.golems.cores.AdditionalGolemCore;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import thaumcraft.common.entities.ai.misc.AIOpenDoor;
import thaumcraft.common.entities.ai.misc.AIReturnHome;
import thaumcraft.common.entities.golems.EntityGolemBase;

public class ValetGolemCore extends AdditionalGolemCore {
    @Override
    public void setupGolem(EntityGolemBase golem) {
        golem.tasks.addTask(6, new GolemValetAI(golem));
        golem.tasks.addTask(5, new AIOpenDoor(golem, true));
        golem.tasks.addTask(6, new AIReturnHome(golem));
        golem.tasks.addTask(7, new EntityAIWatchClosest(golem, EntityPlayer.class, 6.0F));
        golem.tasks.addTask(8, new EntityAILookIdle(golem));
    }

    @Override
    public ItemStack getToolItem(EntityGolemBase golem) {
        return golem.getCarriedForDisplay();
    }

    @Override
    public boolean hasGui() {
        return true;
    }
    @Override
    public boolean openGui(EntityPlayer player, EntityGolemBase golem) {
        if (!player.isSneaking() && (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemNameTag))) {
            if (!player.worldObj.isRemote) {
                player.openGui(ThaumicConcilium.instance, 2, player.worldObj, golem.getEntityId(), 0, 0);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return ThaumicConcilium.MODID+".core.valet";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(TCItemRegistry.golemCores, 1, 1);
    }
}