package com.ilya3point999k.thaumicconcilium.common.golems;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.golems.ai.AIAssistantHomeTake;
import com.ilya3point999k.thaumicconcilium.common.golems.ai.AINearestAnyTarget;
import com.ilya3point999k.thaumicconcilium.common.golems.ai.GolemAssistantAI;
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

public class AssistantGolemCore extends AdditionalGolemCore {
    @Override
    public void setupGolem(EntityGolemBase golem) {
        golem.tasks.addTask(0, new AIAssistantHomeTake(golem));
        golem.tasks.addTask(3, new GolemAssistantAI(golem));
        golem.tasks.addTask(5, new AIOpenDoor(golem, true));
        golem.tasks.addTask(6, new AIReturnHome(golem));
        golem.tasks.addTask(7, new EntityAIWatchClosest(golem, EntityPlayer.class, 6.0F));
        golem.tasks.addTask(8, new EntityAILookIdle(golem));
        golem.targetTasks.addTask(1, new AINearestAnyTarget(golem, 0, true));
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
                player.openGui(ThaumicConcilium.instance, 1, player.worldObj, golem.getEntityId(), 0, 0);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasMarkers() {
        return true;
    }


    @Override
    public String getUnlocalizedGuiText() {
        return "tc.assistant.gui.text";
    }

    @Override
    public String getUnlocalizedName() {
        return ThaumicConcilium.MODID+".core.assistant";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(TCItemRegistry.golemCores, 1, 0);
    }
}