package com.ilya3point999k.thaumicconcilium.common.registry;

import com.ilya3point999k.thaumicconcilium.api.ThaumicConciliumApi;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.items.wands.Bracelets;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TCFociUpgrades;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.forbidden.DarkAspects;
import fox.spiteful.forbidden.compat.Compat;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.RecipeSorter;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemWispEssence;
import thaumcraft.common.items.wands.foci.ItemFocusFire;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.ItemBrightNitor;
import thaumic.tinkerer.common.item.ItemGas;
import thaumic.tinkerer.common.item.foci.ItemFocusDeflect;
import thaumic.tinkerer.common.item.foci.ItemFocusHeal;
import thaumic.tinkerer.common.item.foci.ItemFocusTelekinesis;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.item.kami.foci.ItemFocusShadowbeam;
import thaumic.tinkerer.common.item.kami.foci.ItemFocusXPDrain;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartz;

import java.util.Arrays;

public class TCRecipeRegistry {
    public static void init() {
        Configuration configuration;

        GameRegistry.addRecipe(new WandXylography());

        Thaumonomicon.recipes.put("ClearWater", ThaumcraftApi.addCrucibleRecipe("BOTTLEWATER", new ItemStack(TCItemRegistry.bottleOfClearWater), new ItemStack(ConfigItems.itemEssence), new AspectList().add(Aspect.WATER, 1)));
        Thaumonomicon.recipes.put("BottleOfThickTaint", ThaumcraftApi.addCrucibleRecipe("BOTTLEOFTHICKTAINT", new ItemStack(TCItemRegistry.bottleOfThickTaint), new ItemStack(ConfigItems.itemBottleTaint), new AspectList().add(Aspect.TAINT, 16).add(Aspect.SLIME, 16).add(Aspect.CRYSTAL, 16)));
        //Thaumonomicon.recipes.put("VisCapsule", ThaumcraftApi.addCrucibleRecipe("VISCAPSULE", new ItemStack(TCItemRegistry.visCapsule), new ItemStack(ConfigItems.itemShard, 1, 6), new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.ENTROPY, 8)));
        ItemStack bt = new ItemStack(ConfigItems.itemEssence, 1, 1);
        ((IEssentiaContainerItem)((IEssentiaContainerItem)bt.getItem())).setAspects(bt, (new AspectList()).add(Aspect.SENSES, 8));

        Thaumonomicon.recipes.put("EyeDrops", ThaumcraftApi.addCrucibleRecipe("LITHOGRAPHER", new ItemStack(TCItemRegistry.eyedrops), bt, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.SENSES, 2).add(Aspect.HEAL, 2)));

        Thaumonomicon.recipes.put("DestCrystal", ThaumcraftApi.addShapelessArcaneCraftingRecipe("DESTCRYSTAL", new ItemStack(TCBlockRegistry.DESTABILIZED_CRYSTAL_BLOCK), new AspectList().add(Aspect.ORDER, 50).add(Aspect.ENTROPY, 50).add(Aspect.FIRE, 50).add(Aspect.WATER, 50)
                        .add(Aspect.AIR, 50).add(Aspect.EARTH, 50), new ItemStack(ConfigItems.itemCrystalEssence), new ItemStack(ConfigItems.itemCrystalEssence), new ItemStack(ConfigItems.itemCrystalEssence), new ItemStack(ConfigItems.itemCrystalEssence), new ItemStack(ConfigItems.itemCrystalEssence), new ItemStack(ConfigItems.itemCrystalEssence),
                new ItemStack(ConfigItems.itemCrystalEssence), new ItemStack(ConfigItems.itemCrystalEssence), new ItemStack(ConfigItems.itemCrystalEssence)));


        Thaumonomicon.recipes.put("ShardMill", ThaumcraftApi.addArcaneCraftingRecipe("SHARDMILL", new ItemStack(TCItemRegistry.shardMill, 1, 0), new AspectList().add(Aspect.ENTROPY, 50).add(Aspect.ORDER, 25).add(Aspect.AIR, 10).add(Aspect.EARTH, 10),
                new Object[]{
                        "GLG", "WCW", "GWG",
                        'G', new ItemStack(Items.gold_ingot),
                        'L', new ItemStack(Blocks.lever),
                        'W', new ItemStack(ConfigBlocks.blockMagicalLog, 1, 0),
                        'C', new ItemStack(ConfigItems.itemResource, 1, 15)}));

        ItemStack dematfocus = new ItemStack(TCItemRegistry.visConductorFoci);
        ((ItemFocusBasic) dematfocus.getItem()).applyUpgrade(dematfocus, TCFociUpgrades.dematerialization, 1);

        Thaumonomicon.recipes.put("VisCondenser", ThaumcraftApi.addArcaneCraftingRecipe("VISCONDENSER", new ItemStack(TCBlockRegistry.VIS_CONDENSER_BLOCK), new AspectList().add(Aspect.ENTROPY, 50).add(Aspect.ORDER, 50).add(Aspect.AIR, 10).add(Aspect.FIRE, 10),
                new Object[]{
                        "GSG", "BFB", "BAB",
                        'G', new ItemStack(Items.gold_ingot),
                        'S', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
                        'B', new ItemStack(Blocks.gold_block),
                        'F', dematfocus,
                        'A', new ItemStack(ConfigBlocks.blockStoneDevice, 1, 10)}));

		/*
		Thaumonomicon.recipes.put("EldritchTrap", ThaumcraftApi.addArcaneCraftingRecipe("ELDRITCHTRAP", new ItemStack(TCBlockRegistry.ELDRITCH_TRAP_BLOCK),
				new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.AIR, 5), " A ", "BCD", " E ", 'A', new ItemStack(ForbiddenItems.crystalwell), 'B', new ItemStack(ConfigItems.itemShard, 1, 5), 'C', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11), 'D', new ItemStack(ConfigItems.itemShard, 4), 'E', new ItemStack(ConfigItems.itemShard, 1, 6)));
		*/

        Thaumonomicon.recipes.put("HexOfPredictability", Arrays.asList(new AspectList().add(Aspect.ORDER, 100).add(Aspect.EARTH, 100), 3, 1, 3,
                Arrays.asList(
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11),
                        new ItemStack(Blocks.bedrock),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 11)
                )));

        Thaumonomicon.recipes.put("Lithographer", Arrays.asList(new AspectList().add(Aspect.FIRE, 100).add(Aspect.ORDER, 100).add(Aspect.WATER, 50).add(Aspect.AIR, 100), 1, 3, 3,
                Arrays.asList(
                        null,
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 3),
                        null,
                        new ItemStack(Blocks.gold_block),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
                        new ItemStack(Blocks.gold_block),
                        new ItemStack(Blocks.gold_block),
                        new ItemStack(Items.bed),
                        new ItemStack(Blocks.gold_block)
                )));

        Thaumonomicon.recipes.put("QuicksilverCrucible", Arrays.asList(new AspectList(), 1, 2, 1,
                Arrays.asList(
                        new ItemStack(TCItemRegistry.itemEntityIcon, 1, 4),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 0)
                )));

        Thaumonomicon.recipes.put("SignalFoci", ThaumcraftApi.addInfusionCraftingRecipe("SIGNALFOCI", new ItemStack(TCItemRegistry.signalFoci), 5, new AspectList().add(Aspect.ENERGY, 32).add(Aspect.TOOL, 16).add(Aspect.SENSES, 64).add(Aspect.LIGHT, 16),
                new ItemStack(ConfigItems.itemFocusFire), new ItemStack[]{new ItemStack(Items.dye, 1, 0),
                        new ItemStack(Items.dye, 1, 1),
                        new ItemStack(Items.dye, 1, 2),
                        new ItemStack(Items.dye, 1, 3),
                        new ItemStack(Items.dye, 1, 4),
                        new ItemStack(Items.dye, 1, 5),
                        new ItemStack(Items.dye, 1, 6),
                        new ItemStack(Items.dye, 1, 7),
                        new ItemStack(Items.dye, 1, 8),
                        new ItemStack(Items.dye, 1, 9),
                        new ItemStack(Items.dye, 1, 10),
                        new ItemStack(Items.dye, 1, 11),
                        new ItemStack(Items.dye, 1, 12),
                        new ItemStack(Items.dye, 1, 13),
                        new ItemStack(Items.dye, 1, 14),
                        new ItemStack(Items.dye, 1, 15)}));

        Thaumonomicon.recipes.put("VisConductor", ThaumcraftApi.addInfusionCraftingRecipe("VISCONDUCTOR", new ItemStack(TCItemRegistry.visConductorFoci), 20, new AspectList().add(Aspect.EXCHANGE, 128)
                        .add(Aspect.ENERGY, 64).add(Aspect.MAGIC, 128).add(Aspect.MAN, 256).add(Aspect.TOOL, 32), new ItemStack(ConfigBlocks.blockStoneDevice, 1, 11),
                new ItemStack[]{
                        new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2),
                        new ItemStack(Items.quartz),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigBlocks.blockStoneDevice, 1, 8),
                        new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2),
                        new ItemStack(Items.quartz),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigBlocks.blockStoneDevice, 1, 8)}));

        Thaumonomicon.recipes.put("CoolingFoci", ThaumcraftApi.addInfusionCraftingRecipe("COOLINGFOCI", new ItemStack(TCItemRegistry.coolingFoci), 5, new AspectList().add(Aspect.FIRE, 16).add(Aspect.AIR, 16).add(Aspect.TOOL, 16),
                new ItemStack(ConfigItems.itemFocusFrost), new ItemStack[]{
                        new ItemStack(Blocks.torch),
                        new ItemStack(Blocks.torch)}));

        Thaumonomicon.recipes.put("DumpJackboots", ThaumcraftApi.addInfusionCraftingRecipe("DUMPJACKBOOTS", new ItemStack(TCItemRegistry.dumpJackboots), 5, new AspectList().add(Aspect.EARTH, 128).add(Aspect.ARMOR, 16).add(DarkAspects.SLOTH, 64).add(Aspect.MECHANISM, 32),
                new ItemStack(ConfigItems.itemBootsTraveller), new ItemStack[]{new ItemStack(Blocks.piston),
                        new ItemStack(ConfigItems.itemShovelThaumium),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 5),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 0),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 5),
                        new ItemStack(ConfigItems.itemShovelThaumium),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 5),
                        new ItemStack(ConfigItems.itemShovelThaumium),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 5),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 3),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 5),
                        new ItemStack(ConfigItems.itemShovelThaumium)}));

        Thaumonomicon.recipes.put("RunicBodyWindings", ThaumcraftApi.addInfusionCraftingRecipe("RUNICWINDINGS", new ItemStack(TCItemRegistry.runicBodyWindings), 10, new AspectList().add(Aspect.ELDRITCH, 64).add(Aspect.ARMOR, 128).add(Aspect.MAGIC, 32).add(Aspect.ENERGY, 64).add(Aspect.MIND, 128),
                new ItemStack(Items.paper), new ItemStack[]{
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 1),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ConfigItems.itemResource, 1, 14),
                        new ItemStack(Items.diamond),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 6)}));

        Thaumonomicon.recipes.put("RunicLegsWindings", ThaumcraftApi.addInfusionCraftingRecipe("RUNICWINDINGS", new ItemStack(TCItemRegistry.runicLegsWindings), 10, new AspectList().add(Aspect.ELDRITCH, 64).add(Aspect.ARMOR, 128).add(Aspect.MAGIC, 32).add(Aspect.ENERGY, 64).add(Aspect.MIND, 128),
                new ItemStack(Items.paper), new ItemStack[]{
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 1),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ConfigItems.itemResource, 1, 14),
                        new ItemStack(Items.diamond),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigItems.itemResearchNotes, 1, 42),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 6)}));

        Thaumonomicon.recipes.put("ScribingFoci", ThaumcraftApi.addInfusionCraftingRecipe("SCRIBINGFOCI", new ItemStack(TCItemRegistry.scribingFoci), 15, new AspectList().add(Aspect.ARMOR, 256).add(Aspect.ENERGY, 128).add(Aspect.MAGIC, 192).add(Aspect.MAN, 32).add(Aspect.TOOL, 64).add(Aspect.EXCHANGE, 128),
                new ItemStack(ConfigItems.itemFocusWarding), new ItemStack[]{
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                        new ItemStack(Blocks.glowstone), new ItemStack(Items.quartz),
                        new ItemStack(ConfigItems.itemResource, 1, 9),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class))}));

        Thaumonomicon.recipes.put("ImpulseFoci", ThaumcraftApi.addInfusionCraftingRecipe("IMPULSEFOCI", new ItemStack(TCItemRegistry.impulseFoci), 15, new AspectList().add(Aspect.ENTROPY, 128).add(Aspect.FIRE, 128).add(DarkAspects.WRATH, 96).add(Aspect.TRAP, 32),
                new ItemStack(TCItemRegistry.signalFoci), new ItemStack[]{
                        new ItemStack(ConfigItems.itemFocusFire),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                        new ItemStack(ConfigItems.itemFocusFire),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1)}));

        Thaumonomicon.recipes.put("PositiveBurstFoci", ThaumcraftApi.addInfusionCraftingRecipe("POSITIVEBURSTFOCI", new ItemStack(TCItemRegistry.positiveBurstFoci), 15, new AspectList().add(Aspect.HEAL, 256).add(Aspect.MAN, 128).add(Aspect.TOOL, 64).add(Aspect.AURA, 64).add(DarkAspects.SLOTH, 32),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusHeal.class)), new ItemStack[]{
                        new ItemStack(ConfigBlocks.blockTube, 1, 7),
                        new ItemStack(Items.golden_apple, 1, 1),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(Items.golden_apple, 1, 1),
                        new ItemStack(Items.experience_bottle),
                        new ItemStack(Items.golden_apple, 1, 1),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(Items.golden_apple, 1, 1)}));

        ItemWispEssence itemEssence = (ItemWispEssence) ConfigItems.itemWispEssence;
        ItemStack wispyEssence = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(wispyEssence, new AspectList().add(DarkAspects.WRATH, 2));
        Thaumonomicon.recipes.put("WrathAttraction", ThaumcraftApi.addInfusionCraftingRecipe("WRATHATTRACTION", new ItemStack(TCItemRegistry.wrathAttractionFoci), 15, new AspectList().add(DarkAspects.WRATH, 128).add(Aspect.BEAST, 64).add(Aspect.SENSES, 64).add(Aspect.HUNGER, 96).add(DarkAspects.NETHER, 128),
                wispyEssence, new ItemStack[]{
                        new ItemStack(Items.diamond),
                        new ItemStack(Items.diamond),
                        new ItemStack(Items.diamond),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 0),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 0),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 0),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 0),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 0),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 0)}));

        ItemStack taintEssence = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(taintEssence, new AspectList().add(Aspect.TAINT, 2));
        Thaumonomicon.recipes.put("TaintAnimation", ThaumcraftApi.addInfusionCraftingRecipe("TAINTANIMATION", new ItemStack(TCItemRegistry.taintAnimationFoci), 20, new AspectList().add(Aspect.TAINT, 256).add(Aspect.WEAPON, 64).add(Aspect.LIFE, 96).add(Aspect.FLESH, 64).add(Aspect.TOOL, 32),
                new ItemStack(ConfigItems.itemFocusHellbat), new ItemStack[]{
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        taintEssence, new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 12),
                        new ItemStack(ConfigItems.itemResource, 1, 12),
                        new ItemStack(ConfigItems.itemResource, 1, 12),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        taintEssence,
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        taintEssence,
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemResource, 1, 11),
                        new ItemStack(ConfigItems.itemResource, 1, 11),
                        new ItemStack(ConfigItems.itemResource, 1, 11),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        taintEssence,
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2)}));

        Thaumonomicon.recipes.put("ReflectionFoci", ThaumcraftApi.addInfusionCraftingRecipe("REFLECTIONFOCI", new ItemStack(TCItemRegistry.reflectionFoci), 20, new AspectList().add(Aspect.TAINT, 256).add(Aspect.MIND, 256).add(Aspect.TOOL, 64).add(Aspect.EXCHANGE, 128).add(Aspect.ELDRITCH, 128).add(DarkAspects.PRIDE, 64),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusXPDrain.class)), new ItemStack[]{
                        new ItemStack(ConfigItems.itemThaumonomicon),
                        new ItemStack(ConfigItems.itemThaumonomicon),
                        new ItemStack(ConfigItems.itemInkwell),
                        new ItemStack(ConfigItems.itemInkwell),
                        new ItemStack(ConfigItems.itemInkwell),
                        new ItemStack(ConfigItems.itemSanitySoap),
                        new ItemStack(ConfigItems.itemSanitySoap),
                        new ItemStack(ConfigItems.itemSanitySoap),
                        new ItemStack(ConfigItems.itemFocusTrade),
                        new ItemStack(ConfigItems.itemFocusTrade),
                        new ItemStack(ConfigItems.itemFocusTrade),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ConfigItems.itemBucketPure),
                        new ItemStack(ConfigItems.itemBucketPure),
                        new ItemStack(ConfigItems.itemBucketPure),
                        new ItemStack(ConfigItems.itemThaumonomicon)}));

        Thaumonomicon.recipes.put("TightBelt", ThaumcraftApi.addInfusionCraftingRecipe("TIGHTBELT", new ItemStack(TCItemRegistry.tightBelt), 8, new AspectList().add(Aspect.TAINT, 96).add(Aspect.ARMOR, 96).add(Aspect.POISON, 128).add(Aspect.MAN, 16),
                new ItemStack(ConfigItems.itemGirdleHover), new ItemStack[]{
                        new ItemStack(TCItemRegistry.bottleOfThickTaint),
                        new ItemStack(TCItemRegistry.bottleOfThickTaint),
                        new ItemStack(ConfigItems.itemResource, 1, 11),
                        new ItemStack(ConfigItems.itemResource, 1, 11),
                        new ItemStack(ConfigItems.itemResource, 1, 11),
                        new ItemStack(ConfigItems.itemResource, 1, 11),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(TCItemRegistry.bottleOfThickTaint),
                        new ItemStack(TCItemRegistry.bottleOfThickTaint),
                        new ItemStack(TCItemRegistry.bottleOfThickTaint),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                        new ItemStack(ConfigItems.itemResource, 1, 12),
                        new ItemStack(ConfigItems.itemResource, 1, 12),
                        new ItemStack(ConfigItems.itemResource, 1, 12),
                        new ItemStack(ConfigItems.itemResource, 1, 12),
                        new ItemStack(TCItemRegistry.bottleOfThickTaint)}));

        Thaumonomicon.recipes.put("BurdeningAmulet", ThaumcraftApi.addInfusionCraftingRecipe("BURDENINGAMULET", new ItemStack(TCItemRegistry.burdeningAmulet), 8, new AspectList().add(Aspect.ARMOR, 96).add(Aspect.SENSES, 64).add(Aspect.DEATH, 96).add(Aspect.ELDRITCH, 128).add(DarkAspects.ENVY, 128),
                new ItemStack(ConfigItems.itemAmuletVis, 1, 0), new ItemStack[]{
                        new ItemStack(ConfigItems.itemCompassStone),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1),
                        new ItemStack(Blocks.gold_block),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1),
                        new ItemStack(Items.ender_eye),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 1)}));

        Thaumonomicon.recipes.put("WarpExtractionFoci", ThaumcraftApi.addInfusionCraftingRecipe("WARPEXTRACTIONFOCI", new ItemStack(TCItemRegistry.warpExtractionFoci), 20, new AspectList().add(Aspect.MIND, 256).add(Aspect.ELDRITCH, 256).add(Aspect.TOOL, 64).add(Aspect.EXCHANGE, 128).add(Aspect.ENERGY, 256).add(Aspect.ENTROPY, 64).add(Aspect.SOUL, 32),
                new ItemStack(ConfigItems.itemEldritchObject, 1, 3), new ItemStack[]{
                        new ItemStack(ConfigItems.itemEldritchObject, 3),
                        new ItemStack(ConfigItems.itemResource, 1, 9),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusTelekinesis.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusTelekinesis.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusTelekinesis.class)),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ForbiddenItems.crystalwell),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        new ItemStack(TCItemRegistry.resource, 1, 1),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        new ItemStack(Items.experience_bottle),
                        new ItemStack(Items.experience_bottle),
                        new ItemStack(Items.experience_bottle),
                        new ItemStack(ConfigItems.itemFocusPrimal),
                        new ItemStack(ConfigItems.itemFocusPrimal),
                        new ItemStack(ConfigItems.itemFocusPrimal),
                        new ItemStack(ConfigItems.itemResource, 1, 9)}));

        ItemStack prideEssence = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(prideEssence, new AspectList().add(DarkAspects.PRIDE, 2));
        Thaumonomicon.recipes.put("RingOfBlusteringLight", ThaumcraftApi.addInfusionCraftingRecipe("RINGOFBLUSTERINGLIGHT", new ItemStack(TCItemRegistry.ringOfBlusteringLight), 25, new AspectList().add(Aspect.ENERGY, 512).add(Aspect.LIGHT, 512).add(Aspect.ARMOR, 64).add(Aspect.MAGIC, 256).add(Aspect.EXCHANGE, 32).add(Aspect.ORDER, 96).add(DarkAspects.PRIDE, 96),
                new ItemStack(ConfigItems.itemRingRunic, 1, 2), new ItemStack[]{
                        new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                        prideEssence,
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(Blocks.diamond_block),
                        new ItemStack(Blocks.diamond_block),
                        new ItemStack(Blocks.diamond_block),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        prideEssence,
                        new ItemStack(TCItemRegistry.resource, 1, 1),
                        prideEssence,
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        new ItemStack(ThaumicTinkerer.registry.getItemFromClass(ItemGas.class).get(1)),
                        new ItemStack(ThaumicTinkerer.registry.getItemFromClass(ItemGas.class).get(1)),
                        new ItemStack(ThaumicTinkerer.registry.getItemFromClass(ItemGas.class).get(1)),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 0),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 3),
                        new ItemStack(ForbiddenItems.deadlyShards, 1, 6),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        prideEssence}));

        ItemFocusFire itemFocusFire = new ItemFocusFire();
        ItemStack firefocus = new ItemStack(ConfigItems.itemFocusFire);

        itemFocusFire.applyUpgrade(firefocus, FocusUpgradeType.potency, 1);
        itemFocusFire.applyUpgrade(firefocus, FocusUpgradeType.potency, 2);
        itemFocusFire.applyUpgrade(firefocus, itemFocusFire.getPossibleUpgradesByRank(firefocus, 3)[3], 3);
        itemFocusFire.applyUpgrade(firefocus, FocusUpgradeType.potency, 4);
        itemFocusFire.applyUpgrade(firefocus, FocusUpgradeType.potency, 5);

        Thaumonomicon.recipes.put("SpotlightFoci", ThaumcraftApi.addInfusionCraftingRecipe("SPOTLIGHTFOCI", new ItemStack(TCItemRegistry.spotlightFoci), 20, new AspectList().add(Aspect.LIGHT, 512).add(Aspect.ENERGY, 512).add(Aspect.MAGIC, 64).add(Aspect.ORDER, 128).add(Aspect.WEAPON, 96),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusShadowbeam.class)), new ItemStack[]{
                        new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        new ItemStack(TCItemRegistry.resource, 1, 1),
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0),
                        firefocus,
                        firefocus,
                        firefocus,
                        firefocus,
                        firefocus,
                        new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 0)}));

        Thaumonomicon.recipes.put("AlchemistSpree", ThaumcraftApi.addInfusionCraftingRecipe("ALCHEMISTSPREE", new ItemStack(TCItemRegistry.alchemistSpreeFoci), 20, new AspectList().add(Aspect.EXCHANGE, 512).add(Aspect.CRAFT, 128).add(Aspect.MAGIC, 512).add(Aspect.WATER, 64).add(Aspect.FIRE, 64).add(Aspect.ELDRITCH, 128).add(Aspect.WEAPON, 128),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusDeflect.class)), new ItemStack[]{
                        new ItemStack(TCItemRegistry.resource, 1, 1),
                        new ItemStack(ConfigItems.itemResource, 1, 3),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 0),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 3),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 3),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 0),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 12),
                        new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
                        new ItemStack(ConfigItems.itemResource, 1, 3),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 0),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 3),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 3),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 0),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 12)
                }));

        ItemStack lifeessence = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(lifeessence, new AspectList().add(Aspect.LIFE, 2));

        ItemStack auraessence = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(auraessence, new AspectList().add(Aspect.AURA, 2));

        ItemStack magicessence = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(magicessence, new AspectList().add(Aspect.MAGIC, 2));

        ItemStack eldritchessence = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(eldritchessence, new AspectList().add(Aspect.ELDRITCH, 2));

        Thaumonomicon.recipes.put("ThaumDrum", ThaumcraftApi.addInfusionCraftingRecipe("THAUMDRUM", new ItemStack(TCItemRegistry.thaumaturgeDrum), 10, new AspectList().add(Aspect.SENSES, 128).add(Aspect.LIFE, 64).add(Aspect.AURA, 96).add(Aspect.TOOL, 32),
                new ItemStack(Blocks.noteblock), new ItemStack[]{
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(Items.leather),
                        new ItemStack(ConfigItems.itemResource, 1, 7),
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                        lifeessence,
                        new ItemStack(ConfigItems.itemResource, 1, 2),
                        auraessence,
                        new ItemStack(ConfigItems.itemResource, 1, 7),
                        new ItemStack(Items.string),
                        new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
                        new ItemStack(Items.string),
                        new ItemStack(ConfigItems.itemResource, 1, 7),
                        magicessence,
                        new ItemStack(ConfigItems.itemResource, 1, 2),
                        eldritchessence,
                        new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
                        new ItemStack(ConfigItems.itemResource, 1, 7),
                        new ItemStack(Items.leather)}));

        ItemStack airess = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(airess, new AspectList().add(Aspect.AIR, 2));

        ItemStack motioness = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(motioness, new AspectList().add(Aspect.MOTION, 2));

        ItemStack flightess = new ItemStack(itemEssence, 1, 0);
        itemEssence.setAspects(flightess, new AspectList().add(Aspect.FLIGHT, 2));


        Thaumonomicon.recipes.put("EtherealManipulator", ThaumcraftApi.addInfusionCraftingRecipe("ETHEREALMANIPULATOR", new ItemStack(TCItemRegistry.etherealManipulatorFoci), 10, new AspectList().add(Aspect.TOOL, 64).add(Aspect.MOTION, 64).add(Aspect.AURA, 64).add(Aspect.FLIGHT, 64),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusTelekinesis.class)), new ItemStack[]{
                        auraessence,
                        new ItemStack(Items.sugar),
                        auraessence,
                        airess,
                        new ItemStack(ConfigItems.itemResource, 1, 0),
                        airess,
                        motioness,
                        new ItemStack(Items.sugar),
                        motioness,
                        flightess,
                        new ItemStack(ConfigItems.itemShard, 1, 0),
                        flightess
                }));

        Thaumonomicon.recipes.put("SilverwoodShardMill", ThaumcraftApi.addInfusionCraftingRecipe("SILVERWOODSHARDMILL", new ItemStack(TCItemRegistry.shardMill, 1, 1), 5, new AspectList().add(Aspect.TOOL, 64).add(Aspect.HEAL, 64).add(Aspect.TREE, 128).add(Aspect.ORDER, 64).add(Aspect.ENTROPY, 64).add(Aspect.MECHANISM, 32).add(Aspect.MAGIC, 32),
                new ItemStack(ConfigBlocks.blockMagicalLog, 1, 1), new ItemStack[]{
                        new ItemStack(ConfigItems.itemWandRod, 1, 2),
                        new ItemStack(ConfigItems.itemResource, 1, 8),
                        new ItemStack(ConfigItems.itemResource, 1, 2),
                        new ItemStack(ConfigItems.itemResource, 1, 15),
                        new ItemStack(ConfigItems.itemWandCap, 1, 2),
                        new ItemStack(ConfigItems.itemResource, 1, 8),
                        new ItemStack(ConfigItems.itemResource, 1, 2),
                        new ItemStack(ConfigItems.itemResource, 1, 15)
                }));

        if (Integration.taintedMagic) {
            Item material;
            Item focus;
            try {
                material = Compat.getItem("TaintedMagic", "ItemMaterial");
                focus = Compat.getItem("TaintedMagic", "ItemFocusMageMace");
            } catch (Compat.ItemNotFoundException e) {
                throw new RuntimeException(e);
            }

            Thaumonomicon.recipes.put("PontifexHammer", ThaumcraftApi.addInfusionCraftingRecipe("PONTIFEXHAMMER", new ItemStack(TCItemRegistry.pontifexHammer), 7, new AspectList().add(Aspect.WEAPON, 64).add(Aspect.LIFE, 64).add(Aspect.METAL, 128).add(DarkAspects.PRIDE, 32).add(Aspect.EXCHANGE, 16).add(Aspect.HUNGER, 16),
                    new ItemStack(ConfigItems.itemSwordCrimson), new ItemStack[]{
                            new ItemStack(focus),
                            new ItemStack(Blocks.gold_block),
                            new ItemStack(material),
                            new ItemStack(material, 1, 7),
                            new ItemStack(ConfigItems.itemResource, 1, 15),
                            new ItemStack(ConfigItems.itemWandRod, 1, 50),
                            new ItemStack(ConfigItems.itemResource, 1, 15),
                            new ItemStack(material, 1, 7),
                            new ItemStack(material),
                            new ItemStack(Blocks.gold_block)
                    }));

			Thaumonomicon.recipes.put("FleshCrucible", ThaumcraftApi.addInfusionCraftingRecipe("FLESHCRUCIBLE", new ItemStack(TCBlockRegistry.FLESH_CRUCIBLE), 10, new AspectList().add(Aspect.FLESH, 128).add(Aspect.FIRE, 128).add(DarkAspects.GLUTTONY, 64).add(DarkAspects.NETHER, 128).add(Aspect.FLESH, 128).add(Aspect.HUNGER, 64),
					new ItemStack(TCBlockRegistry.QUICKSILVER_CRUCIBLE), new ItemStack[]{
							new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 6),
							new ItemStack(material, 1, 7),
							new ItemStack(material, 1, 7),
							new ItemStack(material, 1, 7),
							new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)),
							new ItemStack(material, 1, 10),
							new ItemStack(material, 1, 10),
							new ItemStack(material, 1, 10),
					}));
        }

        if (Integration.thaumicBases) {
            Thaumonomicon.recipes.put("InfernalBracelet", ThaumcraftApi.addInfusionCraftingRecipe("INFERNALBRACELET", new ItemStack(TCItemRegistry.castingBracelet, 1, 4), 5, new AspectList().add(Aspect.TOOL, 32).add(DarkAspects.NETHER, 128).add(DarkAspects.PRIDE, 64).add(DarkAspects.WRATH, 64),
                    new ItemStack(ForbiddenItems.wandCore, 1, 1), new ItemStack[]{
                            new ItemStack(ConfigItems.itemWandCap, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 14),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 3),
                            new ItemStack(ConfigItems.itemWandCap, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 14),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 3)}));

            Thaumonomicon.recipes.put("TaintedBracelet", ThaumcraftApi.addInfusionCraftingRecipe("TAINTEDBRACELET", new ItemStack(TCItemRegistry.castingBracelet, 1, 5), 5, new AspectList().add(Aspect.TOOL, 32).add(Aspect.TAINT, 128).add(Aspect.ENTROPY, 64).add(Aspect.MAGIC, 64),
                    new ItemStack(ForbiddenItems.wandCore, 1, 0), new ItemStack[]{
                            new ItemStack(ConfigItems.itemWandCap, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 14),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                            new ItemStack(ConfigItems.itemWandCap, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 14),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 2)}));

            if (Compat.botan) {
                Item resource;
                Item storage;
                try {
                    resource = Compat.getItem("Botania", "manaResource");
                    storage = Compat.getItem("Botania", "storage");
                } catch (Compat.ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Thaumonomicon.recipes.put("LivingwoodBracelet", ThaumcraftApi.addInfusionCraftingRecipe("LIVINGWOODBRACELET", new ItemStack(TCItemRegistry.castingBracelet, 1, 0), 5, new AspectList().add(Aspect.TOOL, 32).add(Aspect.TREE, 128).add(Aspect.LIFE, 64).add(Aspect.MAGIC, 64),
                        new ItemStack(ForbiddenItems.wandCore, 1, 7), new ItemStack[]{
                                new ItemStack(ForbiddenItems.wandCap, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 2),
                                new ItemStack(ForbiddenItems.wandCap, 1, 3),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 2)}));
                Thaumonomicon.recipes.put("DreamwoodBracelet", ThaumcraftApi.addInfusionCraftingRecipe("DREAMWOODBRACELET", new ItemStack(TCItemRegistry.castingBracelet, 1, 1), 5, new AspectList().add(Aspect.TOOL, 32).add(Aspect.TREE, 128).add(Aspect.SOUL, 64).add(Aspect.MAGIC, 64),
                        new ItemStack(ForbiddenItems.wandCore, 1, 13), new ItemStack[]{
                                new ItemStack(ForbiddenItems.wandCap, 1, 5),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 9),
                                new ItemStack(ForbiddenItems.wandCap, 1, 5),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 9)}));
                Thaumonomicon.recipes.put("TerrasteelBracelet", ThaumcraftApi.addInfusionCraftingRecipe("TERRASTEELBRACELET", new ItemStack(TCItemRegistry.castingBracelet, 1, 7), 7, new AspectList().add(Aspect.TOOL, 32).add(Aspect.METAL, 128).add(Aspect.EARTH, 64).add(Aspect.ENERGY, 64).add(Aspect.MAGIC, 64),
                        new ItemStack(storage, 1, 1), new ItemStack[]{
                                new ItemStack(ForbiddenItems.wandCap, 1, 2),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 14),
                                new ItemStack(ForbiddenItems.wandCap, 1, 2),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 14)}));
            }

            if (Compat.bm) {
                Item resource;
                try {
                    resource = Compat.getItem("AWWayofTime", "largeBloodStoneBrick");
                } catch (Compat.ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }

                ItemStack bb = new ItemStack(TCItemRegistry.castingBracelet, 1, 2);
                ((Bracelets)bb.getItem()).setRod(bb, ForbiddenItems.WAND_ROD_BLOOD);

                Thaumonomicon.recipes.put("BloodBracelet", ThaumcraftApi.addInfusionCraftingRecipe("BLOODBRACELET", bb, 5, new AspectList().add(Aspect.TOOL, 32).add(Aspect.HUNGER, 128).add(Aspect.LIFE, 64).add(Aspect.MAGIC, 64),
                        new ItemStack(ForbiddenItems.wandCore, 1, 9), new ItemStack[]{
                                new ItemStack(ForbiddenItems.wandCap, 1, 0),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource), new ItemStack(ForbiddenItems.wandCap, 1, 0),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource)}));
            }
            if (Compat.am2) {
                Item resource;
                try {
                    resource = Compat.getItem("arsmagica2", "vinteumOre");
                } catch (Compat.ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Thaumonomicon.recipes.put("WitchwoodBracelet", ThaumcraftApi.addInfusionCraftingRecipe("WITCHWOODBRACELET", new ItemStack(TCItemRegistry.castingBracelet, 1, 3), 5, new AspectList().add(Aspect.TOOL, 32).add(Aspect.ENERGY, 128).add(Aspect.AURA, 64).add(Aspect.MAGIC, 64),
                        new ItemStack(ForbiddenItems.wandCore, 1, 10), new ItemStack[]{
                                new ItemStack(ForbiddenItems.wandCap, 1, 1),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 7),
                                new ItemStack(ForbiddenItems.wandCap, 1, 1),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 7)}));
            }
            if (Integration.taintedMagic) {
                Item resource;
                Item core;
                Item cap;
                try {
                    resource = Compat.getItem("TaintedMagic", "ItemMaterial");
                    core = Compat.getItem("TaintedMagic", "ItemWandRod");
                    cap = Compat.getItem("TaintedMagic", "ItemWandCap");
                } catch (Compat.ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Thaumonomicon.recipes.put("WarpwoodBracelet", ThaumcraftApi.addInfusionCraftingRecipe("WARPWOODBRACELET", new ItemStack(TCItemRegistry.castingBracelet, 1, 6), 5, new AspectList().add(Aspect.TOOL, 32).add(Aspect.ELDRITCH, 128).add(Aspect.MIND, 64).add(Aspect.MAGIC, 64),
                        new ItemStack(core, 1, 1), new ItemStack[]{
                                new ItemStack(cap, 1, 0),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 3),
                                new ItemStack(cap, 1, 0),
                                new ItemStack(ConfigItems.itemResource, 1, 14),
                                new ItemStack(resource, 1, 4)}));

            }
        }

        if (Integration.gadomancy) {
            GameRegistry.addRecipe(new TerraGemRecipe());
            RecipeSorter.register(ThaumicConcilium.MODID + ":TerraCastGem", TerraGemRecipe.class, RecipeSorter.Category.SHAPELESS, "");

            Item auraCore;
            Item resource;
            try {
                auraCore = Compat.getItem("gadomancy", "ItemAuraCore");
                resource = Compat.getItem("gadomancy", "ItemElement");
            } catch (Compat.ItemNotFoundException e) {
                throw new RuntimeException(e);
            }

            Thaumonomicon.recipes.put("RiftGem", ThaumcraftApi.addInfusionCraftingRecipe("RIFTGEM", new ItemStack(TCItemRegistry.riftGem), 10, new AspectList().add(Aspect.CRYSTAL, 64).add(Aspect.VOID, 64).add(Aspect.TRAVEL, 96).add(Aspect.AURA, 32),
                    new ItemStack(auraCore, 1, 6), new ItemStack[]{
                            new ItemStack(ConfigItems.itemResource, 1, 15),
                            new ItemStack(resource),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 6),
                            new ItemStack(ConfigItems.itemResource, 1, 1),
                            new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 1),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 6),
                            new ItemStack(ConfigItems.itemResource, 1, 17),
                            new ItemStack(ConfigItems.itemResource, 1, 15),
                            new ItemStack(resource),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 1),
                            new ItemStack(ConfigItems.itemResource, 1, 0),
                            new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 0),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 1),
                            new ItemStack(ConfigItems.itemResource, 1, 17)

                    }));

            Thaumonomicon.recipes.put("TerraCastGem", ThaumcraftApi.addInfusionCraftingRecipe("TERRACAST", new ItemStack(TCItemRegistry.terraCastGem), 10, new AspectList().add(Aspect.CRYSTAL, 64).add(Aspect.EARTH, 64).add(Aspect.TRAVEL, 96).add(Aspect.AURA, 32),
                    new ItemStack(auraCore, 1, 4), new ItemStack[]{
                            new ItemStack(ConfigItems.itemResource, 1, 15),
                            new ItemStack(ConfigItems.itemNugget, 1, 16),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 4),
                            new ItemStack(ConfigItems.itemResource, 1, 1),
                            new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 1),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 4),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 0),
                            new ItemStack(ConfigItems.itemResource, 1, 15),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 7),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 1),
                            new ItemStack(ConfigItems.itemResource, 1, 0),
                            new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
                            new ItemStack(ConfigItems.itemResource, 1, 0),
                            new ItemStack(ConfigBlocks.blockCustomOre, 1, 1),
                            new ItemStack(ConfigItems.itemNugget, 1, 31)
                    }));
			/*
			Thaumonomicon.recipes.put("BurlakCore", ThaumcraftApi.addInfusionCraftingRecipe("BURLAKCORE", new ItemStack(TCItemRegistry.golemCores, 1, 0), 2, new AspectList().add(Aspect.TRAVEL, 64).add(Aspect.MECHANISM, 16).add(Aspect.TOOL, 16),
					new ItemStack(ConfigItems.itemGolemCore, 1, 100), new ItemStack[]{
							new ItemStack(Items.furnace_minecart),
							new ItemStack(Items.compass),
							new ItemStack(Blocks.golden_rail),
							new ItemStack(Items.stick)
					}));
			*/
            Thaumonomicon.recipes.put("ValetCore", ThaumcraftApi.addInfusionCraftingRecipe("VALETCORE", new ItemStack(TCItemRegistry.golemCores, 1, 1), 8, new AspectList().add(Aspect.TOOL, 32).add(Aspect.MECHANISM, 16).add(Aspect.SENSES, 32),
                    new ItemStack(ConfigItems.itemGolemCore, 1, 8), new ItemStack[]{
                            new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 1),
                            new ItemStack(ConfigItems.itemResource, 1, 9),
                            new ItemStack(ConfigItems.itemBaubleBlanks, 1, 0),
                            new ItemStack(ConfigItems.itemResource, 1, 9),
                    }));
            if (Integration.horizons) {
                Item focus;
                Item syringeBlood;
                try {
                    focus = Compat.getItem("ThaumicHorizons", "focusContainment");
                    syringeBlood = Compat.getItem("ThaumicHorizons", "syringeBlood");
                } catch (Compat.ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }

                Thaumonomicon.recipes.put("AssistanceCore", ThaumcraftApi.addInfusionCraftingRecipe("ASSISTANCECORE", new ItemStack(TCItemRegistry.golemCores, 1, 0), 8, new AspectList().add(Aspect.TOOL, 32).add(Aspect.BEAST, 16).add(Aspect.TRAP, 32),
                        new ItemStack(ConfigItems.itemGolemCore, 1, 9), new ItemStack[]{
                                new ItemStack(focus),
                                new ItemStack(syringeBlood),
                                new ItemStack(ConfigItems.itemGolemCore, 1, 6),
                                new ItemStack(syringeBlood),
                        }));
            }

        }

        if (Integration.horizons) {
            Item injector;
            Item syringeBlood;
            Item golemPowder;
            try {
                injector = Compat.getItem("ThaumicHorizons", "injector");
                syringeBlood = Compat.getItem("ThaumicHorizons", "syringeBlood");
                golemPowder = Compat.getItem("ThaumicHorizons", "golemPowder");
            } catch (Compat.ItemNotFoundException e) {
                throw new RuntimeException(e);
            }


            Thaumonomicon.recipes.put("BrainLittering", ThaumcraftApi.addInfusionCraftingRecipe("BRAINLITTERING", new ItemStack(TCItemRegistry.itemEntityIcon, 1, 1), 10, new AspectList().add(Aspect.ENTROPY, 64).add(Aspect.ELDRITCH, 64).add(Aspect.MIND, 64),
                    new ItemStack(TCItemRegistry.itemEntityIcon, 1, 0), new ItemStack[]{
                            new ItemStack(ConfigItems.itemResource, 1, 9),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 2),
                            new ItemStack(ConfigItems.itemZombieBrain),
                            new ItemStack(ForbiddenItems.deadlyShards, 1, 2)
                    }));
            Thaumonomicon.recipes.put("BuffGolem", ThaumcraftApi.addInfusionCraftingRecipe("BUFFGOLEM", new ItemStack(TCItemRegistry.itemEntityIcon, 1, 2), 10, new AspectList().add(Aspect.ENERGY, 64).add(Aspect.METAL, 128).add(DarkAspects.PRIDE, 32).add(Aspect.ENTROPY, 128),
                    new ItemStack(TCItemRegistry.itemEntityIcon, 1, 1), new ItemStack[]{
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
                    }));
            if (Integration.automagy) {
                Thaumonomicon.recipes.put("RedPoweredMind", ThaumcraftApi.addInfusionCraftingRecipe("REDPOWEREDMIND", new ItemStack(TCItemRegistry.itemEntityIcon, 1, 3), 10, new AspectList().add(Aspect.CRYSTAL, 64).add(Aspect.MIND, 64).add(Aspect.SENSES, 32).add(Aspect.MECHANISM, 32),
                        new ItemStack(TCItemRegistry.itemEntityIcon, 1, 1), new ItemStack[]{
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
                        }));
            }

            if (Integration.thaumicBases){
                Thaumonomicon.recipes.put("DopeSquid", ThaumcraftApi.addInfusionCraftingRecipe("DOPESQUID", new ItemStack(TCItemRegistry.itemEntityIcon, 1, 6), 4, new AspectList().add(Aspect.PLANT, 64).add(Aspect.MAGIC, 64).add(Aspect.MAN, 32).add(Aspect.FLIGHT, 64),
                        new ItemStack(TCItemRegistry.itemEntityIcon, 1, 5), new ItemStack[]{
                                new ItemStack(ConfigItems.itemShard, 1, 0),
                                new ItemStack(Integration.tobaccoitem, 1, 1),
                                new ItemStack(Integration.tobaccoitem, 1, 2),
                                new ItemStack(Integration.tobaccoitem, 1, 3),
                                new ItemStack(Integration.tobaccoitem, 1, 4),
                                new ItemStack(ConfigItems.itemShard, 1, 1),
                                new ItemStack(Integration.tobaccoitem, 1, 5),
                                new ItemStack(Integration.tobaccoitem, 1, 6),
                                new ItemStack(Integration.tobaccoitem, 1, 7),
                                new ItemStack(Integration.tobaccoitem, 1, 8),
                        }));
            }

            Thaumonomicon.recipes.put("DrainageSyringe", ThaumcraftApi.addInfusionCraftingRecipe("DRAINAGESYRINGE", new ItemStack(TCItemRegistry.drainageSyringe), 10, new AspectList().add(Aspect.TOOL, 64).add(Aspect.LIFE, 128).add(Aspect.HUNGER, 64).add(Aspect.VOID, 128).add(Aspect.SLIME, 32),
                    new ItemStack(injector), new ItemStack[]{
                            new ItemStack(ConfigBlocks.blockEssentiaReservoir),
                            new ItemStack(ConfigBlocks.blockTube, 1, 6),
                            new ItemStack(syringeBlood),
                            new ItemStack(ConfigItems.itemResource, 1, 15),
                            new ItemStack(Blocks.lever),
                            new ItemStack(ConfigItems.itemResource, 1, 16)
                    }));
        }


        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.coolingFoci), new AspectList().add(Aspect.WEAPON, 64));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.dumpJackboots), new AspectList().add(Aspect.TRAVEL, 128));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.wrathAttractionFoci), new AspectList().add(Aspect.MIND, 128));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.taintAnimationFoci), new AspectList().add(DarkAspects.NETHER, 256));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.reflectionFoci), new AspectList().add(Aspect.TRAP, 256));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.tightBelt), new AspectList().add(Aspect.FLIGHT, 128));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.burdeningAmulet), new AspectList().add(Aspect.SENSES, 64));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.warpExtractionFoci), new AspectList().add(Aspect.HEAL, 512));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.ringOfBlusteringLight), new AspectList().add(Aspect.DARKNESS, 512));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.spotlightFoci), new AspectList().add(Aspect.ENTROPY, 1024));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.scribingFoci), new AspectList().add(Aspect.CRAFT, 128));
        ThaumicConciliumApi.addPolishmentRecipe(new ItemStack(TCItemRegistry.etherealManipulatorFoci), new AspectList().add(DarkAspects.SLOTH, 128));

        Thaumonomicon.recipes.put("AstralMonitor", ThaumicConciliumApi.addChainedRiftRecipe("ASTRALMONITOR", new ItemStack(TCItemRegistry.astralMonitor), new ItemStack(ConfigItems.itemThaumometer), new AspectList().add(Aspect.SENSES, 128).add(Aspect.VOID, 128).add(Aspect.MIND, 64)));
        Thaumonomicon.recipes.put("WarpDrum", ThaumicConciliumApi.addChainedRiftRecipe("WARPDRUM", new ItemStack(TCItemRegistry.thaumaturgeDrum, 1, 1), new ItemStack(TCItemRegistry.thaumaturgeDrum, 1, 0), new AspectList().add(Aspect.SENSES, 128).add(Aspect.ENTROPY, 128).add(Aspect.MIND, 64).add(Aspect.BEAST, 64).add(Aspect.TAINT, 64)));
        if (Integration.gadomancy){
            Thaumonomicon.recipes.put("RRiftGem", ThaumicConciliumApi.addChainedRiftRecipe("RIFTGEM", new ItemStack(TCItemRegistry.riftGem, 1, 1), new ItemStack(TCItemRegistry.riftGem, 1, 0), new AspectList().add(Aspect.TRAVEL, 128).add(Aspect.ENTROPY, 128).add(Aspect.CRYSTAL, 128).add(Aspect.VOID, 128).add(Aspect.AURA, 64)));
            Thaumonomicon.recipes.put("RTerraCastGem", ThaumicConciliumApi.addChainedRiftRecipe("TERRACAST", new ItemStack(TCItemRegistry.terraCastGem, 1, 1), new ItemStack(TCItemRegistry.terraCastGem, 1, 0), new AspectList().add(Aspect.TRAVEL, 128).add(Aspect.ENTROPY, 128).add(Aspect.CRYSTAL, 128).add(Aspect.MIND, 128).add(Aspect.AURA, 64)));

        }
        if (Integration.taintedMagic){
            Thaumonomicon.recipes.put("PontifexRobeHead", ThaumicConciliumApi.addChainedRiftRecipe("PONTIFEXROBE", new ItemStack(TCItemRegistry.pontifexHead), new ItemStack(ConfigItems.itemHelmetCultistRobe), new AspectList().add(Aspect.LIFE, 128).add(Aspect.VOID, 128).add(Aspect.ARMOR, 256).add(Aspect.EXCHANGE, 64).add(Aspect.FLESH, 256)));
            Thaumonomicon.recipes.put("PontifexRobeChest", ThaumicConciliumApi.addChainedRiftRecipe("PONTIFEXROBE", new ItemStack(TCItemRegistry.pontifexChest), new ItemStack(ConfigItems.itemChestCultistRobe), new AspectList().add(Aspect.LIFE, 128).add(Aspect.VOID, 128).add(Aspect.ARMOR, 256).add(Aspect.EXCHANGE, 64).add(Aspect.FLESH, 256)));
            Thaumonomicon.recipes.put("PontifexRobeLegs", ThaumicConciliumApi.addChainedRiftRecipe("PONTIFEXROBE", new ItemStack(TCItemRegistry.pontifexLegs), new ItemStack(ConfigItems.itemLegsCultistRobe), new AspectList().add(Aspect.LIFE, 128).add(Aspect.VOID, 128).add(Aspect.ARMOR, 256).add(Aspect.EXCHANGE, 64).add(Aspect.FLESH, 256)));
            Thaumonomicon.recipes.put("PontifexRobeFeet", ThaumicConciliumApi.addChainedRiftRecipe("PONTIFEXROBE", new ItemStack(TCItemRegistry.pontifexFeet), new ItemStack(ConfigItems.itemBootsCultist), new AspectList().add(Aspect.LIFE, 128).add(Aspect.VOID, 128).add(Aspect.ARMOR, 256).add(Aspect.EXCHANGE, 64).add(Aspect.FLESH, 256)));
        }
    }
}
