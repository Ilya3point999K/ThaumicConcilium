package com.ilya3point999k.thaumicconcilium.common.integration;

import am2.api.ArsMagicaApi;
import am2.api.events.SkillLearnedEvent;
import am2.api.power.PowerTypes;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.api.spell.enums.SkillPointTypes;
import am2.api.spell.enums.SkillTrees;
import am2.items.ItemsCommonProxy;
import am2.lore.ArcaneCompendium;
import am2.playerextensions.ExtendedProperties;
import am2.playerextensions.SkillData;
import am2.spell.SkillManager;
import am2.spell.SkillTreeManager;
import am2.spell.SpellHelper;
import am2.utility.EntityUtilities;
import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import com.ilya3point999k.thaumicconcilium.common.entities.other.LesserPortal;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.PontifexRobe;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Random;

public class AM2Integration {
    public static CrimsonRaid crimson_raid_component;
    public static void register() {
        if (Integration.taintedMagic) {
            crimson_raid_component = new CrimsonRaid();
            ArsMagicaApi.instance.registerSkillTreeEntry(
                    crimson_raid_component,
                    "Crimson_Raid",
                    SkillTrees.None,
                    25, 175,
                    SkillPointTypes.SILVER
            );
        }
    }

    public static void registerClient() {
        if (Integration.taintedMagic) {
            ArcaneCompendium.instance.AddCompenidumEntry(
                    AM2Integration.crimson_raid_component,
                    "Crimson_Raid",
                    StatCollector.translateToLocal("am2.spell.crimson_raid"),
                    StatCollector.translateToLocal("am2.entry.crimson_raid"),
                    null,
                    false
            );
        }
    }

    public static void unlockCrimsonRaid(EntityPlayer player){
        if(Integration.taintedMagic) {
            SkillData d = SkillData.For(player);
            try {
                SkillTreeManager t = SkillTreeManager.instance;
                d.incrementSpellPoints(t.getSkillPointTypeForPart(AM2Integration.crimson_raid_component));
                d.learn(t.getSkillTreeEntry(AM2Integration.crimson_raid_component).registeredItem);
                d.forceSync();
                MinecraftForge.EVENT_BUS.post(new SkillLearnedEvent(player, AM2Integration.crimson_raid_component));
            } catch (Exception e1) {
                int ID = SkillManager.instance.getShiftedPartID(AM2Integration.crimson_raid_component);
                try {
                    Method m = SkillData.class.getDeclaredMethod("setComponentKnown", int.class);
                    m.setAccessible(true); // bypass private access
                    m.invoke(d, ID);
                    d.forceSync();
                    MinecraftForge.EVENT_BUS.post(new SkillLearnedEvent(player, AM2Integration.crimson_raid_component));
                } catch (Exception e2) {
                    e1.printStackTrace();
                    e2.printStackTrace();
                }
            }
        }
    }

}

class CrimsonRaid implements ISpellComponent {

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
