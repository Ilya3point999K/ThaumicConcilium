package com.ilya3point999k.thaumicconcilium.common.spells;

import am2.api.power.PowerTypes;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.items.ItemsCommonProxy;
import am2.playerextensions.ExtendedProperties;
import am2.spell.SpellHelper;
import am2.utility.EntityUtilities;
import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.LesserPortal;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.PontifexRobe;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

import java.util.EnumSet;
import java.util.Random;

public class CrimsonRaid implements ISpellComponent {

    public LesserPortal summonCreature(ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z){
        LesserPortal entity = new LesserPortal(world);
        entity.setPosition(x, y, z);
        world.spawnEntityInWorld(entity);
        entity.stagecounter = 0;
        if (!(caster instanceof EntityPlayer)){
            return null;
        }
        entity.getEntityData().setBoolean("AM2_Entity_Is_Made_Summon", true);
        ExtendedProperties.For(caster).addSummon(entity);
        EntityUtilities.setOwner(entity, caster);



        EntityUtilities.setSummonDuration(entity, 400);

        SpellHelper.instance.applyStageToEntity(stack, caster, world, entity, 0, false);

        return entity;
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, int blockx, int blocky, int blockz, int blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster) {
        if (!world.isRemote){
            if (ExtendedProperties.For(caster).getCanHaveMoreSummons()){
                if(caster instanceof EntityPlayer && PontifexRobe.isFullSet((EntityPlayer) caster)) {
                    if (summonCreature(stack, caster, caster, world, impactX, impactY, impactZ) == null) {
                        return false;
                    }
                }
            }else{
                if (caster instanceof EntityPlayer){
                    ((EntityPlayer)caster).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("am2.tooltip.noMoreSummons")));
                }
            }
        }

        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target) {
        if (target instanceof EntityLivingBase && EntityUtilities.isSummon((EntityLivingBase)target))
            return false;

        if (!world.isRemote){
            if (ExtendedProperties.For(caster).getCanHaveMoreSummons()){
                if(caster instanceof EntityPlayer && PontifexRobe.isFullSet((EntityPlayer) caster)) {
                    if (summonCreature(stack, caster, caster, world, target.posX, target.posY, target.posZ) == null) {
                        return false;
                    }
                }
            }else{
                if (caster instanceof EntityPlayer){
                    ((EntityPlayer)caster).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("am2.tooltip.noMoreSummons")));
                }
            }
        }

        return true;
    }

    @Override
    public float manaCost(EntityLivingBase entityLivingBase) {
        return 3000;
    }

    @Override
    public float burnout(EntityLivingBase entityLivingBase) {
        return 300;
    }

    @Override
    public ItemStack[] reagents(EntityLivingBase entityLivingBase) {
        return new ItemStack[0];
    }

    @Override
    public void spawnParticles(World world, double v, double v1, double v2, EntityLivingBase entityLivingBase, Entity entity, Random random, int i) {

    }

    @Override
    public EnumSet<Affinity> getAffinity() {
        return EnumSet.of(Affinity.ENDER, Affinity.ARCANE);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public Object[] getRecipeItems() {
        return new Object[]{
                new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                new ItemStack(ConfigItems.itemEldritchObject, 1, 1),
                new ItemStack(ConfigItems.itemFocusPortableHole),
                new ItemStack(TCItemRegistry.resource, 1, 3),
                new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 8),
                new ItemStack(ItemsCommonProxy.essence, 1, ItemsCommonProxy.essence.META_ENDER),
                new ItemStack(ItemsCommonProxy.essence, 1, ItemsCommonProxy.essence.META_ARCANE),
                new ItemStack(ItemsCommonProxy.essence, 1, ItemsCommonProxy.essence.META_LIFE),
                String.format("E:%d", PowerTypes.DARK.ID()), 10000
        };
    }

    @Override
    public int getID() {
        return TCConfig.crimsonRaidID;
    }
}
