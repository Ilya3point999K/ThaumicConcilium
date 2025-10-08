package com.ilya3point999k.thaumicconcilium.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ThaumcraftResearchCompletedEvent extends PlayerEvent {

    private final String researchKey;

    public ThaumcraftResearchCompletedEvent(EntityPlayer player, String researchKey) {
        super(player);
        this.researchKey = researchKey;
    }

    public String getUnlockedResearch() {
        return researchKey;
    }

    public static void fireResearchCompletedEvent(EntityPlayer player, String researchKey) {
        MinecraftForge.EVENT_BUS.post(new ThaumcraftResearchCompletedEvent(player, researchKey));
    }
}
