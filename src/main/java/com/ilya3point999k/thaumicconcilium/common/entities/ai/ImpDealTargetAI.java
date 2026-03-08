package com.ilya3point999k.thaumicconcilium.common.entities.ai;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Chort;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ImpDealTargetAI extends EntityAINearestAttackableTarget {

    private final EntityCreature attacker;

    public ImpDealTargetAI(EntityCreature entity) {
        super(entity, EntityPlayer.class, 0, true);
        this.attacker = entity;
    }

    @Override
    protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {

        if (!(target instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) target;
        boolean hasPact = false;
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
        if (capabilities != null && capabilities.impDealTime > 0){
            hasPact = true;
        }

        boolean attackedMob = attacker.getAITarget() == player;
        if (hasPact || attackedMob) {
            if ((attacker instanceof Chort) && hasPact) {
                Chort chort = (Chort) attacker;
                if (chort.chatCooldown == 0) {
                    player.addChatMessage(new ChatComponentText(
                            StatCollector.translateToLocal("tc.chort.has_pact")
                    ).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)));
                    chort.chatCooldown = 1200;
                }
            }
            return false;
        }

        return super.isSuitableTarget(target, includeInvincibles);
    }
}

