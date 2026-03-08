package com.ilya3point999k.thaumicconcilium.common.integration;

import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.SloppyAlchemist;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse.NetherExplorer;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse.Protolimb;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.corpse.WitheredBotanist;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.thaumaturge.Thaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.other.DopeSquid;
import com.ilya3point999k.thaumicconcilium.common.entities.other.GolemBydlo;
import com.ilya3point999k.thaumicconcilium.common.entities.other.RedPoweredMind;
import com.ilya3point999k.thaumicconcilium.common.items.Protobody;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.common.registry.EntityRegistry;
import fox.spiteful.forbidden.DarkAspects;
import fox.spiteful.forbidden.compat.Compat;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.gui.GuiResearchBrowser;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class HorizonsIntegration {
    public static void register() {
        ThaumicHorizons.classBanList.add(NetherExplorer.class);
        if (Integration.witchery) {
            ThaumicHorizons.classBanList.add(SloppyAlchemist.class);
        }
        if (Compat.botan){
            ThaumicHorizons.classBanList.add(WitheredBotanist.class);
        }
        if (Integration.taintedMagic){
            ThaumicHorizons.classBanList.add(Protolimb.class);
            ThaumicHorizons.classBanList.add(Protobody.class);
        }
        Class th = null;
        Class creatureInfusion = null;

        Object recipeList = null;
        try {
            th = Class.forName("com.kentington.thaumichorizons.common.ThaumicHorizons");
            creatureInfusion = Class.forName("com.kentington.thaumichorizons.common.lib.CreatureInfusionRecipe");
            recipeList = th.getDeclaredField("critterRecipes").get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            EntityRegistry.registerModEntity(MadThaumaturge.class, "MadThaumaturge", 41, th.getField("instance").get(null), 64, 3, true);
            EntityRegistry.registerModEntity(GolemBydlo.class, "GolemBydlo", 42, th.getField("instance").get(null), 64, 3, true);
            if (Integration.automagy) {
                EntityRegistry.registerModEntity(RedPoweredMind.class, "RedPoweredMind", 43, th.getField("instance").get(null), 64, 3, true);
            }
            if (Integration.thaumicBases) {
                EntityRegistry.registerModEntity(DopeSquid.class, "DopeSquid", 44, th.getField("instance").get(null), 64, 3, true);
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        Item golemPowder;
        try {
            golemPowder = Compat.getItem("ThaumicHorizons", "golemPowder");
        } catch (Compat.ItemNotFoundException e) {
            throw new RuntimeException(e);
        }


        Object buffGolemRecipe = null;
        Object brainLitteringRecipe = null;
        try {
            buffGolemRecipe = creatureInfusion.getConstructors()[0].newInstance("BUFFGOLEM", 42, (int) 4, new AspectList().add(Aspect.ENERGY, 64).add(Aspect.METAL, 128).add(DarkAspects.PRIDE, 32).add(Aspect.ENTROPY, 128),
                    MadThaumaturge.class, new ItemStack[]{
                            new ItemStack(ConfigItems.itemGolemPlacer, 1, 7),
                            new ItemStack(golemPowder),
                            new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                            new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 5),
                            new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                            new ItemStack(ConfigItems.itemGolemPlacer, 1, 7),
                            new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 9),
                            new ItemStack(ConfigItems.itemGolemPlacer, 1, 7),
                            new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                            new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 5),
                            new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                            new ItemStack(golemPowder)
                    }, (int) 0);
            brainLitteringRecipe = creatureInfusion.getConstructors()[0].newInstance("BRAINLITTERING", 41, 4, new AspectList().add(Aspect.ENTROPY, 64).add(Aspect.ELDRITCH, 64).add(Aspect.MIND, 64),
                    Thaumaturge.class, new ItemStack[]{
                            new ItemStack(ConfigItems.itemResource, 1, 9),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                            new ItemStack(ConfigItems.itemZombieBrain),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 2)
                    }, 0);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        ((ArrayList<Object>) recipeList).add(buffGolemRecipe);
        ((ArrayList<Object>) recipeList).add(brainLitteringRecipe);
        if (Integration.automagy) {
            Object redPoweredMindRecipe = null;
            try {
                redPoweredMindRecipe = creatureInfusion.getConstructors()[0].newInstance("REDPOWEREDMIND", 43, 4, new AspectList().add(Aspect.CRYSTAL, 64).add(Aspect.MIND, 64).add(Aspect.SENSES, 32).add(Aspect.MECHANISM, 32),
                        MadThaumaturge.class, new ItemStack[]{
                                new ItemStack(ConfigItems.itemBucketDeath),
                                new ItemStack(Items.repeater),
                                new ItemStack(Blocks.redstone_block),
                                new ItemStack(Blocks.redstone_block),
                                new ItemStack(Blocks.redstone_block),
                                new ItemStack(ConfigItems.itemBucketDeath),
                                new ItemStack(ConfigItems.itemResource, 1, 17),
                                new ItemStack(ConfigItems.itemBucketDeath),
                                new ItemStack(Blocks.redstone_block),
                                new ItemStack(Blocks.redstone_block),
                                new ItemStack(Blocks.redstone_block),
                                new ItemStack(Items.comparator)
                        }, 0);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            ((ArrayList<Object>) recipeList).add(redPoweredMindRecipe);

            try {
                Integration.crystalEye = Class.forName("tuhljin.automagy.items.ItemCrystalEye");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (Integration.thaumicBases && Integration.allowTobacco){
            Object dopeSquidRecipe = null;
            try {
                dopeSquidRecipe = creatureInfusion.getConstructors()[0].newInstance("DOPESQUID", 44, 4, new AspectList().add(Aspect.PLANT, 64).add(Aspect.MAGIC, 64).add(Aspect.MAN, 32).add(Aspect.FLIGHT, 64),
                        EntitySquid.class, new ItemStack[]{
                                new ItemStack(ConfigItems.itemShard, 1, 0),
                                new ItemStack(Integration.tobaccoitem, 1, 1),
                                new ItemStack(Integration.tobaccoitem, 1, 2),
                                new ItemStack(Integration.tobaccoitem, 1, 3),
                                new ItemStack(Integration.tobaccoitem, 1, 4),
                                new ItemStack(ConfigItems.itemShard, 1, 1),
                                new ItemStack(Integration.tobaccoitem, 1, 5),
                                new ItemStack(Integration.tobaccoitem, 1, 6),
                                new ItemStack(Integration.tobaccoitem, 1, 7),
                                new ItemStack(Integration.tobaccoitem, 1, 8)
                        }, 0);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            ((ArrayList<Object>) recipeList).add(dopeSquidRecipe);

        }

    }
}
