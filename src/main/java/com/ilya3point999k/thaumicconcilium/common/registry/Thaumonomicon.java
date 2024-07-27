package com.ilya3point999k.thaumicconcilium.common.registry;

import com.ilya3point999k.thaumicconcilium.api.ChainedRiftRecipe;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.research.ThaumcraftResearchItem;
import fox.spiteful.forbidden.DarkAspects;
import fox.spiteful.forbidden.compat.Compat;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.lib.LibResearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Thaumonomicon {
	public static final String catName = "CONCILIUM";
	public static HashMap<String, Object> recipes = new HashMap<String, Object>();

	public static void setup() {
		ResearchCategories.registerCategory(catName, new ResourceLocation(ThaumicConcilium.MODID+":textures/category.png"),
				new ResourceLocation(ThaumicConcilium.MODID+":textures/researchback.png"));
		ResearchItem r;

		r = new ThaumcraftResearchItem("TCRESEARCHDUPE", "RESEARCHDUPE", "BASICS",
				-5, -5,
				new ResourceLocation("thaumcraft:textures/misc/r_resdupe.png"));
		r.setSpecial().setRound().registerResearchItem();

		r = new ThaumcraftResearchItem("TCBOTTLETAINT", "BOTTLETAINT", "ALCHEMY",
				0, -5,
				new ItemStack(ConfigItems.itemBottleTaint));
		r.setStub().registerResearchItem();

		r = new ResearchItem("BOTTLEWATER", catName, new AspectList().add(Aspect.WATER, 1),
				0, -6, 1,
				new ItemStack(TCItemRegistry.bottleOfClearWater));
		r.setPages(new ResearchPage("entry.clearwater.first"), cruciblePage("ClearWater"));
		r.setAutoUnlock().setStub().registerResearchItem();

		r = new ResearchItem("THAUMATURGES", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.EXCHANGE, 8),
				-3, -1, 1,
				new ItemStack(TCItemRegistry.itemEntityIcon, 1, 0));
		r.setPages(new ResearchPage("entry.thaumaturges.first"), new ResearchPage("entry.thaumaturges.second"));
		r = r.setConcealed().setStub().setSecondary().setSpecial().setParents("TCRESEARCHDUPE").registerResearchItem();
		/*ArrayList<String> siblings = new ArrayList<String>();
		if (!Integration.gadomancy){
			siblings.add("NOGADOMANCY");
		} else siblings.add("TCGADOMANCY");
		if(!Integration.thaumicBases){
			siblings.add("NOBASES");
		} else siblings.add("TCBASES");
		if(!Integration.horizons){
			siblings.add("NOHORIZONS");
		} else siblings.add("TCHORIZONS");
		if(!Integration.taintedMagic){
			siblings.add("NOTAINTEDMAGIC");
		} else siblings.add("TCTAINTEDMAGIC");

		 */

		//r.setSiblings(siblings.toArray(new String[siblings.size()])).registerResearchItem();

		r = new ResearchItem("RIFTS", catName, new AspectList().add(Aspect.VOID, 4).add(Aspect.MAGIC, 4).add(Aspect.ELDRITCH, 4).add(Aspect.ENTROPY, 8),
				-9, -3, 1, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/rift_res.png"));
		r.setPages(new ResearchPage("entry.rifts.first"), new ResearchPage("entry.rifts.second"));
		r.setSecondary().setSpecial().setLost().setEntityTriggers(new String[]{ThaumicConcilium.MODID+".RiftEntity"}).registerResearchItem();
		ThaumcraftApi.addWarpToResearch("RIFTS", 3);

		r = new ResearchItem("DEMATUPGRADE", catName, new AspectList().add(Aspect.MAGIC, 4).add(Aspect.ENERGY, 4).add(Aspect.AURA, 8).add(Aspect.TOOL, 4),
				-11, -1, 1, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/dematerialization.png"));
		r.setPages(new ResearchPage("entry.dematupgrade.first"));
		r.setParents("RIFTS").setSecondary().setConcealed().setParentsHidden("VISCONDUCTOR", "FOCALMANIPULATION").registerResearchItem();

		r = new ResearchItem("VISCONDENSER", catName, new AspectList().add(Aspect.MAGIC, 4).add(Aspect.ENERGY, 4).add(Aspect.AURA, 8).add(Aspect.MECHANISM, 4),
				-13, -1, 1, new ItemStack(TCBlockRegistry.VIS_CONDENSER_BLOCK));
		r.setPages(new ResearchPage("entry.viscondenser.first"), arcanePage("VisCondenser"), new ResearchPage("entry.viscondenser.second"));
		r.setParents("DEMATUPGRADE").setParentsHidden("NODESTABILIZERADV").registerResearchItem();

		r = new ResearchItem("HEXOFPREDICTABILITY", catName, new AspectList().add(Aspect.ORDER, 15).add(Aspect.EARTH, 30).add(Aspect.TRAP, 15).add(Aspect.MAGIC, 30),
				-12, 1, 3, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/hex.png"));
		r.setPages(new ResearchPage("entry.hex.first"), structPage("HexOfPredictability"), new ResearchPage("entry.hex.second"));
		r.setConcealed().setParents("VISCONDENSER").setParentsHidden("OUTERREV").setSiblings("ASTRALMONITOR").setSpecial().registerResearchItem();
		ThaumcraftApi.addWarpToResearch("HEXOFPREDICTABILITY", 2);

		r = new ResearchItem("ETHEREALMANIPULATOR", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.AURA, 10).add(Aspect.MIND, 10).add(Aspect.MOTION, 10),
				-14, 1, 2, new ItemStack(TCItemRegistry.etherealManipulatorFoci));
		r.setPages(new ResearchPage("entry.ethman.first"), infusionPage("EtherealManipulator"));
		r.setConcealed().setParents("HEXOFPREDICTABILITY").setParentsHidden("FOCUS_TELEKINESIS").registerResearchItem();

		r = new ResearchItem("ASTRALMONITOR", catName, new AspectList().add(Aspect.SENSES, 15).add(Aspect.CRYSTAL, 30).add(Aspect.TOOL, 15).add(Aspect.SOUL, 30).add(Aspect.VOID, 10),
				-12, 3, 3, new ItemStack(TCItemRegistry.astralMonitor));
		r.setPages(new ResearchPage("entry.astralmonitor.first"), riftPage("AstralMonitor"), new ResearchPage("entry.astralmonitor.second"), new ResearchPage("entry.astralmonitor.third"));
		r.setConcealed().setParents("HEXOFPREDICTABILITY").registerResearchItem();

		r = new ResearchItem("LITHOGRAPHER", catName, new AspectList().add(Aspect.SENSES, 15).add(Aspect.MECHANISM, 10).add(Aspect.VOID, 10).add(Aspect.ELDRITCH, 10),
				-13, 5, 3, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/lithographer.png"));
		r.setPages(new ResearchPage("entry.lithographer.first"), structPage("Lithographer"), new ResearchPage("entry.lithographer.second"), cruciblePage("EyeDrops"));
		r.setConcealed().setParents("ASTRALMONITOR").setParentsHidden("ADVALCHEMYFURNACE").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("LITHOGRAPHER", 2);

		r = new ResearchItem("QUICKSILVERCRUCIBLE", catName, new AspectList().add(Aspect.MAN, 10).add(Aspect.EXCHANGE, 10).add(Aspect.METAL, 10).add(Aspect.MAGIC, 10).add(Aspect.FLESH, 10),
				-14, 3, 2, new ItemStack(TCBlockRegistry.QUICKSILVER_CRUCIBLE));
		r.setPages(new ResearchPage("entry.quicksilvercrucible.first"), structPage("QuicksilverCrucible"), new ResearchPage("entry.quicksilvercrucible.second"));
		r.setConcealed().setHidden().setEntityTriggers(new String[]{ThaumicConcilium.MODID+".QuicksilverElemental"}).registerResearchItem();
		ThaumcraftApi.addWarpToResearch("QUICKSILVERCRUCIBLE", 3);

		r = new ResearchItem("MADTHAUMATURGES", catName, new AspectList().add(Aspect.MIND, 5).add(Aspect.ELDRITCH, 10).add(Aspect.MAN, 15),
				-4, 2, 1,
				new ItemStack(TCItemRegistry.itemEntityIcon, 1, 1));
		r.setPages(new ResearchPage("entry.madthaumaturges.first"), new ResearchPage("entry.madthaumaturges.second"));
		r.setLost().setRound().setParents("THAUMATURGES").setConcealed().setEntityTriggers(new String[]{ThaumicConcilium.MODID+".MadThaumaturge"}).registerResearchItem();

		r = new ResearchItem("THAUMICCONCILIUM", catName, new AspectList().add(Aspect.MIND, 5).add(Aspect.ELDRITCH, 10).add(Aspect.MAN, 15),
				-2, 2, 1,
				new ResourceLocation(ThaumicConcilium.MODID+":textures/category.png"));
		r.setPages(new ResearchPage("entry.thaumicconcilium.first"), new ResearchPage("entry.thaumicconcilium.second"),
				new ResearchPage("entry.thaumicconcilium.quicksilver1"), new ResearchPage("entry.thaumicconcilium.quicksilver2"),
				new ResearchPage("entry.thaumicconcilium.dissolved1"), new ResearchPage("entry.thaumicconcilium.dissolved2"),
				new ResearchPage("TCTAINTEDMAGIC", "entry.thaumicconcilium.samurai1"), new ResearchPage("TCTAINTEDMAGIC", "entry.thaumicconcilium.samurai2"),
				new ResearchPage("TCTAINTEDMAGIC", "entry.thaumicconcilium.paladin1"), new ResearchPage("TCTAINTEDMAGIC", "entry.thaumicconcilium.paladin2"),
				new ResearchPage("TCHORIZONS","entry.thaumicconcilium.overanimated1"), new ResearchPage("TCHORIZONS","entry.thaumicconcilium.overanimated2"),
				new ResearchPage("TCAUTOMAGY","entry.thaumicconcilium.vengefulgolem1"), new ResearchPage("TCAUTOMAGY","entry.thaumicconcilium.vengefulgolem2"));
		r.setLost().setRound().setParents("THAUMATURGES").setConcealed().setEntityTriggers(new String[]{
						ThaumicConcilium.MODID + ".Overanimated",
						ThaumicConcilium.MODID + ".VengefulGolem",
						ThaumicConcilium.MODID + ".Dissolved",
						ThaumicConcilium.MODID + ".Samurai",
						ThaumicConcilium.MODID + ".QuicksilverElemental",
						ThaumicConcilium.MODID + ".CrimsonPaladin"
				}
				).registerResearchItem();

		r = new ResearchItem("SHARDMILL", catName, new AspectList().add(Aspect.TOOL, 16).add(Aspect.CRYSTAL, 16).add(Aspect.ENTROPY, 4).add(Aspect.MAGIC, 4),
				0, -3, 2, new ItemStack(TCItemRegistry.shardMill, 1, 0));
		r.setPages(new ResearchPage("entry.shardmill.first"), arcanePage("ShardMill"), new ResearchPage("entry.shardmill.second"), new ResearchPage("entry.shardmill.third"));
		r.setConcealed().setParents("THAUMATURGES").setParentsHidden(new String[]{"ROD_greatwood", "FOCUSFIRE"}).registerResearchItem();

		r = new ResearchItem("SILVERWOODSHARDMILL", catName, new AspectList().add(Aspect.TOOL, 16).add(Aspect.CRYSTAL, 16).add(Aspect.ORDER, 4).add(Aspect.MAGIC, 4),
				1, -4, 2, new ItemStack(TCItemRegistry.shardMill, 1, 1));
		r.setPages(new ResearchPage("entry.silverwoodshardmill.first"), infusionPage("SilverwoodShardMill"), new ResearchPage("entry.silverwoodshardmill.second"), new ResearchPage("entry.silverwoodshardmill.third"), new ResearchPage("entry.silverwoodshardmill.fourth"));
		r.setConcealed().setParents("SHARDMILL").setParentsHidden(new String[]{"ROD_silverwood"}).registerResearchItem();
		ThaumcraftApi.addWarpToResearch("SILVERWOODSHARDMILL", 1);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.shardMill, 1, 1), 1);

		r = new ResearchItem("BOTTLEOFTHICKTAINT", catName, new AspectList().add(Aspect.TAINT, 15).add(Aspect.SLIME, 20).add(Aspect.CRYSTAL, 10),
				3, -5, 2, new ItemStack(TCItemRegistry.bottleOfThickTaint));
		r.setPages(new ResearchPage("entry.bottleofthicktaint.first"), cruciblePage("BottleOfThickTaint"));
		r.setLost().setParents("TCBOTTLETAINT").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("HEXOFPREDICTABILITY", 2);

		r = new ResearchItem("RUNICWINDINGS", catName, new AspectList().add(Aspect.ARMOR, 10).add(Aspect.ELDRITCH, 15).add(Aspect.MIND, 20).add(Aspect.MAGIC, 20),
				3, -3, 1, new ItemStack(TCItemRegistry.runicBodyWindings));
		r.setPages(new ResearchPage("entry.runicwindings.first"), infusionPage("RunicBodyWindings"), infusionPage("RunicLegsWindings"));
		r.setLost().setHidden().setParentsHidden("RUNICAUGMENTATION", "CRYSTALWELL").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("RUNICWINDINGS", 3);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.runicBodyWindings), 3);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.runicLegsWindings), 3);

		r = new ResearchItem("SCRIBINGFOCI", catName, new AspectList().add(Aspect.ARMOR, 25).add(Aspect.ENERGY, 25).add(Aspect.MAGIC, 30).add(Aspect.TOOL, 15).add(Aspect.MAN, 20),
				5, -2, 3, new ItemStack(TCItemRegistry.scribingFoci));
		r.setPages(new ResearchPage("entry.scribingfoci.first"), infusionPage("ScribingFoci"));
		r.setConcealed().setParents("RUNICWINDINGS").setParentsHidden(new String[]{"DESTCRYSTAL", "FOCUSWARDING", "BRIGHT_NITOR"}).registerResearchItem();

		r = new ResearchItem("SIGNALFOCI", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.ENERGY, 30).add(Aspect.SENSES, 20),
				0, 2, 1,
				new ItemStack(TCItemRegistry.signalFoci));
		r.setPages(new ResearchPage("entry.signalfoci.first"), infusionPage("SignalFoci"));
		r.setConcealed().setSecondary().setParents("THAUMATURGES").setParentsHidden(new String[]{"FOCUSFIRE", "INFUSION"}).registerResearchItem();

		r = new ResearchItem("IMPULSEFOCI", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.ENERGY, 30).add(DarkAspects.WRATH, 20).add(Aspect.FIRE, 30),
				2, 6, 2,
				new ItemStack(TCItemRegistry.impulseFoci));
		r.setPages(new ResearchPage("entry.impulsefoci.first"), infusionPage("ImpulseFoci"));
		r.setLost().setParents("SIGNALFOCI").registerResearchItem();

		r = new ResearchItem("POSITIVEBURSTFOCI", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.HEAL, 20).add(Aspect.AURA, 15).add(Aspect.MAN, 20),
				0, 4, 3, new ItemStack(TCItemRegistry.positiveBurstFoci));
		r.setPages(new ResearchPage("entry.positiveburstfoci.first"), infusionPage("PositiveBurstFoci"));
		r.setLost().setConcealed().setParents("SIGNALFOCI").setParentsHidden("FOCUS_HEAL").setLost().registerResearchItem();

		r = new ResearchItem("VISCONDUCTOR", catName, new AspectList().add(Aspect.EXCHANGE, 10).add(Aspect.TOOL, 10).add(Aspect.MAN, 10).add(Aspect.ENERGY, 10).add(Aspect.MAGIC, 10),
				-4, 5, 3, new ItemStack(TCItemRegistry.visConductorFoci));
		r.setPages(new ResearchPage("entry.visconductor.first"), infusionPage("VisConductor"), new ResearchPage("entry.visconductor.second"));
		r.setLost().setSpecial().setParentsHidden(new String[]{"THAUMATURGES", "INFUSION", "VISPOWER", "WANDPEDFOC"}).registerResearchItem();


		r = new ResearchItem("DESTCRYSTAL", catName, new AspectList().add(Aspect.CRYSTAL, 30).add(Aspect.ENERGY, 20).add(Aspect.MAGIC, 20).add(Aspect.AURA, 30),
				-2, 5, 2, new ItemStack(TCBlockRegistry.DESTABILIZED_CRYSTAL_BLOCK));
		r.setPages(new ResearchPage("entry.destcrystal.first"), arcanePage("DestCrystal"));
		r.setLost().setParents("VISCONDUCTOR").setParentsHidden("ESSENTIACRYSTAL").setSiblings("COOLINGFOCI").registerResearchItem();

		r = new ResearchItem("COOLINGFOCI", catName, new AspectList().add(Aspect.TOOL, 15).add(Aspect.COLD, 30).add(Aspect.EXCHANGE, 10).add(Aspect.AIR, 20),
				-3, 7, 2, new ItemStack(TCItemRegistry.coolingFoci));
		r.setPages(new ResearchPage("entry.coolingfoci.first"), infusionPage("CoolingFoci"));
		r.setParents("DESTCRYSTAL").registerResearchItem();

		r = new ResearchItem("DUMPJACKBOOTS", catName, new AspectList().add(Aspect.ARMOR, 20).add(Aspect.CRAFT, 10).add(Aspect.EARTH, 10).add(Aspect.MECHANISM, 5),
				-2, 9, 2, new ItemStack(TCItemRegistry.dumpJackboots));
		r.setPages(new ResearchPage("entry.dumpjackboots.first"), infusionPage("DumpJackboots"));
		r.setConcealed().setParents("DESTCRYSTAL").setParentsHidden("BOOTSTRAVELLER").registerResearchItem();

		r = new ResearchItem("WRATHATTRACTION", catName, new AspectList().add(DarkAspects.WRATH, 20).add(Aspect.BEAST, 15).add(Aspect.SENSES, 15).add(Aspect.HUNGER, 30),
				0, 8, 3, new ItemStack(TCItemRegistry.wrathAttractionFoci));
		r.setPages(new ResearchPage("entry.wrathattraction.first"), infusionPage("WrathAttraction"));
		r.setParents("DESTCRYSTAL").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("WRATHATTRACTION", 3);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.wrathAttractionFoci), 3);

		r = new ResearchItem("TAINTANIMATION", catName, new AspectList().add(Aspect.TAINT, 20).add(Aspect.LIFE, 20).add(Aspect.FLESH, 20).add(Aspect.WEAPON, 10),
				3, 8, 3, new ItemStack(TCItemRegistry.taintAnimationFoci));
		r.setPages(new ResearchPage("entry.taintanimation.first"), infusionPage("TaintAnimation"), new ResearchPage("entry.taintanimation.second"));
		r.setParents("DESTCRYSTAL").setParentsHidden("BOTTLEOFTHICKTAINT", "FOCUSHELLBAT", "TAINTSHOVEL").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("TAINTANIMATION", 4);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.taintAnimationFoci), 4);

		r = new ResearchItem("REFLECTIONFOCI", catName, new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.MIND, 30).add(Aspect.TOOL, 10).add(Aspect.TAINT, 20).add(Aspect.EXCHANGE, 30),
				2, 10, 3, new ItemStack(TCItemRegistry.reflectionFoci));
		r.setPages(new ResearchPage("entry.reflectionfoci.first"), infusionPage("ReflectionFoci"), new ResearchPage("entry.reflectionfoci.second"));
		r.setLost().setParents("DESTCRYSTAL").setParentsHidden("FOCUS_XP_DRAIN", "ADVANCEDGOLEM").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("REFLECTIONFOCI", 5);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.reflectionFoci), 4);

		r = new ResearchItem("TIGHTBELT", catName, new AspectList().add(Aspect.ARMOR, 20).add(Aspect.TAINT, 30).add(Aspect.MAN, 5).add(Aspect.POISON, 30),
				5, -4, 2, new ItemStack(TCItemRegistry.tightBelt));
		r.setPages(new ResearchPage("entry.tightbelt.first"), infusionPage("TightBelt"));
		r.setConcealed().setParents("BOTTLEOFTHICKTAINT").setParentsHidden("HOVERGIRDLE", "DESTCRYSTAL").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("TIGHTBELT", 4);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.tightBelt), 4);

		r = new ResearchItem("BURDENINGAMULET", catName, new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.ARMOR, 30).add(Aspect.SENSES, 30).add(Aspect.DEATH, 30).add(DarkAspects.ENVY, 10),
				7, -5, 2, new ItemStack(TCItemRegistry.burdeningAmulet));
		r.setPages(new ResearchPage("entry.burdeningamulet.first"), infusionPage("BurdeningAmulet"), new ResearchPage("entry.burdeningamulet.second"));
		r.setConcealed().setParents("TIGHTBELT").setParentsHidden("SINSTONE").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("BURDENINGAMULET", 3);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.burdeningAmulet), 3);

		r = new ResearchItem("THAUMDRUM", catName, new AspectList().add(Aspect.SENSES, 10).add(Aspect.AURA, 20).add(Aspect.LIFE, 10),
				-6, 7, 2, new ItemStack(TCItemRegistry.thaumaturgeDrum));
		r.setPages(new ResearchPage("entry.thaumdrum.first"), infusionPage("ThaumDrum"));
		r.setConcealed().setHidden().setParents("VISCONDUCTOR").registerResearchItem();

		r = new ResearchItem("WARPDRUM", catName, new AspectList().add(Aspect.SENSES, 10).add(Aspect.MIND, 20).add(Aspect.LIFE, 10).add(Aspect.ENTROPY, 10).add(Aspect.BEAST, 10),
				-5, 7, 2, new ItemStack(TCItemRegistry.thaumaturgeDrum, 1, 1));
		r.setPages(new ResearchPage("entry.warpdrum.first"), riftPage("WarpDrum"));
		r.setConcealed().setParents("THAUMDRUM").setParentsHidden("HEXOFPREDICTABILITY").registerResearchItem();

		r = new ResearchItem("BRIGHTESTONE", catName, new AspectList().add(Aspect.ENERGY, 10).add(Aspect.AURA, 20).add(Aspect.LIFE, 10).add(DarkAspects.PRIDE, 10),
				-10, 4, 2, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/brightest.png"));
		r.setPages(new ResearchPage("entry.brightestone.first"), new ResearchPage("entry.brightestone.second"));
		r.setConcealed().setRound().setParents("HEXOFPREDICTABILITY").setParentsHidden("THAUMDRUM").registerResearchItem();

		r = new ResearchItem("PRIMORDIALLIFE", catName, new AspectList().add(Aspect.ENERGY, 10).add(Aspect.AURA, 20).add(Aspect.LIFE, 10).add(DarkAspects.PRIDE, 10),
				-12, 7, 2, new ItemStack(TCItemRegistry.resource, 1, 1));
		r.setPages(new ResearchPage("entry.primordiallife.first"));
		r.setSecondary().setSpecial().setLost().setParents("BRIGHTESTONE").setItemTriggers(new ItemStack[]{new ItemStack(TCItemRegistry.resource, 1, 1)}).registerResearchItem();
		ThaumcraftApi.addWarpToResearch("PRIMORDIALLIFE", 3);


		r = new ResearchItem("RINGOFBLUSTERINGLIGHT", catName, new AspectList().add(Aspect.ENERGY, 30).add(Aspect.ARMOR, 30).add(Aspect.LIGHT, 30).add(Aspect.MAGIC, 20).add(DarkAspects.PRIDE, 20),
				-12, 9, 3, new ItemStack(TCItemRegistry.ringOfBlusteringLight));
		r.setPages(new ResearchPage("entry.ringofblusteringlight.first"), infusionPage("RingOfBlusteringLight"));
		r.setConcealed().setParents("PRIMORDIALLIFE").setParentsHidden("PRIMPEARL", "RUNICCHARGED", LibResearch.KEY_ICHOR).registerResearchItem();
		ThaumcraftApi.addWarpToResearch("RINGOFBLUSTERINGLIGHT", 3);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.ringOfBlusteringLight), 3);

		r = new ResearchItem("WARPEXTRACTIONFOCI", catName, new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.EXCHANGE, 30).add(Aspect.ENERGY, 10).add(Aspect.TOOL, 15).add(Aspect.MIND, 20),
				-14, 10, 3, new ItemStack(TCItemRegistry.warpExtractionFoci));
		r.setPages(new ResearchPage("entry.warpextractionfoci.first"), infusionPage("WarpExtractionFoci"), new ResearchPage("entry.warpextractionfoci.second"));
		r.setConcealed().setParents("RINGOFBLUSTERINGLIGHT").setParentsHidden("FOCUSPRIMAL", "PRIMPEARL", "REFLECTIONFOCI").registerResearchItem();
		ThaumcraftApi.addWarpToResearch("WARPEXTRACTIONFOCI", 5);
		ThaumcraftApi.addWarpToItem(new ItemStack(TCItemRegistry.warpExtractionFoci), 5);

		r = new ResearchItem("ALCHEMISTSPREE", catName, new AspectList().add(Aspect.EXCHANGE, 30).add(Aspect.CRAFT, 10).add(Aspect.MAGIC, 20).add(Aspect.WEAPON, 10),
				-12, 11, 3, new ItemStack(TCItemRegistry.alchemistSpreeFoci));
		r.setPages(new ResearchPage("entry.alchemistspreefoci.first"), infusionPage("AlchemistSpree"));
		r.setConcealed().setParents("RINGOFBLUSTERINGLIGHT").setParentsHidden("ADVALCHEMYFURNACE", "THAUMATORIUM").registerResearchItem();

		r = new ResearchItem("SPOTLIGHTFOCI", catName, new AspectList().add(Aspect.LIGHT, 20).add(Aspect.WEAPON, 30).add(Aspect.ORDER, 10).add(Aspect.ENERGY, 20),
				-10, 10, 2, new ItemStack(TCItemRegistry.spotlightFoci));
		r.setPages(new ResearchPage("entry.spotlightfoci.first"), infusionPage("SpotlightFoci"));
		r.setConcealed().setParents("RINGOFBLUSTERINGLIGHT").registerResearchItem();

		ItemStack xyl = new ItemStack(ConfigItems.itemWandCasting);
		((ItemWandCasting)xyl.getItem()).setRod(xyl, ForbiddenItems.WAND_ROD_NEUTRONIUM);
		((ItemWandCasting)xyl.getItem()).setCap(xyl, ConfigItems.WAND_CAP_IRON);
		r = new ResearchItem("XYLOGRAPHY", catName, new AspectList().add(Aspect.TOOL, 1).add(Aspect.MIND, 1).add(Aspect.TREE, 1),
				-2, -3, 2, xyl);
		r.setPages(new ResearchPage("entry.xylography.first"));
		r.setAutoUnlock().setRound().registerResearchItem();



		if(Integration.thaumicBases){
			if (Compat.botan) {
				r = new ResearchItem("LIVINGWOODBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.TREE, 15).add(Aspect.LIFE, 10),
						2, 1, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 0));
				r.setPages(new ResearchPage("entry.livingwoodbracelet.first"), infusionPage("LivingwoodBracelet"));
				r.setConcealed().setParents("TCBASES").setParentsHidden(new String[]{"TB.Bracelet.Iron", "ROD_livingwood", "CAP_manasteel"}).registerResearchItem();

				r = new ResearchItem("DREAMWOODBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.TREE, 15).add(Aspect.AURA, 10),
						2, 2, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 1));
				r.setPages(new ResearchPage("entry.dreamwoodbracelet.first"), infusionPage("DreamwoodBracelet"));
				r.setConcealed().setParents(new String[]{"LIVINGWOODBRACELET", "TCBASES"}).setParentsHidden(new String[]{"TB.Bracelet.Iron", "ROD_dreamwood", "CAP_elementium"}).registerResearchItem();

				r = new ResearchItem("TERRASTEELBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.METAL, 15).add(Aspect.AURA, 10),
						2, 3, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 7));
				r.setPages(new ResearchPage("entry.terrasteelbracelet.first"), infusionPage("TerrasteelBracelet"));
				r.setConcealed().setParents(new String[]{"TCBASES"}).setParentsHidden(new String[]{"TB.Bracelet.Iron", "CAP_terrasteel"}).registerResearchItem();

			}
			if(Compat.bm){
				r = new ResearchItem("BLOODBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.HUNGER, 15).add(Aspect.LIFE, 10),
						3, 1, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 2));
				r.setPages(new ResearchPage("entry.bloodbracelet.first"), infusionPage("BloodBracelet"));
				r.setConcealed().setParents("TCBASES").setParentsHidden(new String[]{"TB.Bracelet.Iron", "ROD_blood", "CAP_alchemical"}).registerResearchItem();
				ThaumcraftApi.addWarpToResearch("BLOODBRACELET", 1);
			}
			if(Compat.am2){
				r = new ResearchItem("WITCHWOODBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.TREE, 15).add(Aspect.MAGIC, 10),
						3, 2, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 3));
				r.setPages(new ResearchPage("entry.witchwoodbracelet.first"), infusionPage("WitchwoodBracelet"));
				r.setConcealed().setParents("TCBASES").setParentsHidden(new String[]{"TB.Bracelet.Iron", "ROD_witchwood", "CAP_vinteum"}).registerResearchItem();
			}
			if(Integration.taintedMagic){
				r = new ResearchItem("WARPWOODBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.TREE, 15).add(Aspect.ELDRITCH, 10).add(Aspect.DARKNESS, 10).add(Aspect.MIND, 10),
						4, 3, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 6));
				r.setPages(new ResearchPage("entry.warpwoodbracelet.first"), infusionPage("WarpwoodBracelet"));
				r.setConcealed().setParents("TCBASES").setParentsHidden(new String[]{"TB.Bracelet.Iron", "ROD_warpwood", "CAP_shadowmetal"}).registerResearchItem();
				ThaumcraftApi.addWarpToResearch("WARPWOODBRACELET", 3);
			}
			r = new ResearchItem("INFERNALBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(DarkAspects.NETHER, 15).add(DarkAspects.PRIDE, 10),
					4, 2, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 4));
			r.setPages(new ResearchPage("entry.infernalbracelet.first"), infusionPage("InfernalBracelet"));
			r.setConcealed().setParents("TCBASES").setParentsHidden(new String[]{"TB.Bracelet.Iron", "ROD_infernal", "CAP_thaumium"}).registerResearchItem();
			ThaumcraftApi.addWarpToResearch("INFERNALBRACELET", 1);

			r = new ResearchItem("TAINTEDBRACELET", catName, new AspectList().add(Aspect.TOOL, 10).add(Aspect.TAINT, 15).add(Aspect.MAGIC, 10),
					3, 3, 2, new ItemStack(TCItemRegistry.castingBracelet, 1, 5));
			r.setPages(new ResearchPage("entry.taintedbracelet.first"), infusionPage("TaintedBracelet"));
			r.setConcealed().setParents("TCBASES").setParentsHidden(new String[]{"TB.Bracelet.Iron", "ROD_tainted", "CAP_thaumium"}).registerResearchItem();
			ThaumcraftApi.addWarpToResearch("TAINTEDBRACELET", 2);
		}

		if (Integration.automagy){
			r = new ResearchItem("TCAUTOMAGY", catName, new AspectList().add(Aspect.MECHANISM, 8).add(Aspect.MIND, 8),
					20, 20, 1, new ItemStack(Blocks.dirt));
			r.setVirtual().setAutoUnlock().registerResearchItem();
		}

		if(Integration.gadomancy){
			r = new ResearchItem("TCGADOMANCY", catName, new AspectList().add(Aspect.TOOL, 8).add(Aspect.AURA, 8),
					-5, 1, 1, new ResourceLocation("gadomancy:textures/misc/category_icon.png"));
			r.setPages(new ResearchPage("entry.tcgadomancy.first"));
			r.setRound().setParents("THAUMATURGES").setParentsHidden("GADOMANCY.AURA_CORE").registerResearchItem();

			r = new ResearchItem("RIFTGEM", catName, new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.AURA, 8).add(Aspect.VOID, 8).add(Aspect.TRAVEL, 8),
					-9, 2, 2, new ItemStack(TCItemRegistry.riftGem, 1, 1));
			r.setPages(new ResearchPage("entry.riftgem.first"), infusionPage("RiftGem"), riftPage("RRiftGem"), new ResearchPage("entry.riftgem.second"), new ResearchPage("entry.riftgem.third"), new ResearchPage("entry.riftgem.fourth"));
			r.setConcealed().setParents("TCGADOMANCY", "HEXOFPREDICTABILITY").setParentsHidden("GADOMANCY.AURA_CORE", "WARDEDARCANA").registerResearchItem();

			r = new ResearchItem("TERRACAST", catName, new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.AURA, 8).add(Aspect.EARTH, 8).add(Aspect.TRAVEL, 8),
					-9, 0, 2, new ItemStack(TCItemRegistry.terraCastGem, 1, 1));
			r.setPages(new ResearchPage("entry.terracast.first"), new ResearchPage("entry.terracast.second"), infusionPage("TerraCastGem"), riftPage("RTerraCastGem"));
			r.setConcealed().setParents("TCGADOMANCY", "HEXOFPREDICTABILITY", "RIFTGEM").setParentsHidden("GADOMANCY.AURA_CORE", "WARDEDARCANA").registerResearchItem();

			r = new ResearchItem("VALETCORE", catName, new AspectList().add(Aspect.MAN, 8).add(DarkAspects.PRIDE, 8).add(Aspect.MECHANISM, 8),
					-7, -1, 2, new ItemStack(TCItemRegistry.golemCores, 1, 1));
			r.setPages(new ResearchPage("entry.valetcore.first"), infusionPage("ValetCore"));
			r.setConcealed().setParents("TCGADOMANCY").setParentsHidden("GADOMANCY.COREUSE").registerResearchItem();

			if (Integration.horizons) {
				r = new ResearchItem("ASSISTANCECORE", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.TRAP, 8).add(Aspect.BEAST, 8),
						-7, 3, 2, new ItemStack(TCItemRegistry.golemCores, 1, 2));
				r.setPages(new ResearchPage("entry.assistancecore.first"), infusionPage("AssistanceCore"), new ResearchPage("entry.assistancecore.second"), new ResearchPage("UPGRADEENTROPY", "entry.assistancecore.third"), new ResearchPage("UPGRADEORDER", "entry.assistancecore.fourth"));
				r.setConcealed().setParents("TCGADOMANCY").setParentsHidden("COREALCHEMY", "COREBUTCHER").registerResearchItem();
				ThaumcraftApi.addWarpToResearch("ASSISTANCECORE", 1);
			}
			/*
			r = new ResearchItem("BURLAKCORE", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.MECHANISM, 8).add(Aspect.TRAVEL, 8).add(Aspect.TOOL, 8),
					-7, 1, 2, new ItemStack(TCItemRegistry.golemCores, 1, 0));
			r.setPages(new ResearchPage("entry.burlakcore.first"), infusionPage("BurlakCore"));
			r.setConcealed().setParents("TCGADOMANCY").registerResearchItem();

			 */
		}

		if (Integration.thaumicBases){
			r = new ResearchItem("TCBASES", catName, new AspectList().add(Aspect.MAGIC, 8).add(Aspect.CRAFT, 8),
					0, 0, 1, new ResourceLocation("thaumicbases:textures/thaumonomicon/bases.png"));
			r.setPages(new ResearchPage("entry.tcbases.first"));
			r.setRound().setParents("THAUMATURGES").setParentsHidden("TB.Bracelet.Iron").registerResearchItem();
		}

		if(Integration.horizons){
			r = new ResearchItem("TCHORIZONS", catName, new AspectList().add(Aspect.MAGIC, 8).add(Aspect.MAN, 8),
					7, -1, 1, new ResourceLocation("thaumichorizons:textures/misc/vat.png"));
			r.setPages(new ResearchPage("entry.tchorizons.first"));
			r.setRound().setParents("THAUMATURGES").setParentsHidden("healingVat").registerResearchItem();

			r = new ResearchItem("BRAINLITTERING", catName, new AspectList().add(Aspect.MIND, 8).add(Aspect.ENTROPY, 8).add(Aspect.ELDRITCH, 8).add(Aspect.MAN, 8),
					8, 2, 2, new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/brain_littering.png"));
			r.setPages(new ResearchPage("entry.brainlittering.first"), new ResearchPage("entry.brainlittering.second"), infusionPage("BrainLittering"));
			r.setConcealed().setParents("TCHORIZONS").setParentsHidden("infusionVat").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("BRAINLITTERING", 5);

			r = new ResearchItem("BUFFGOLEM", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.ENERGY, 8).add(Aspect.METAL, 8).add(Aspect.ELDRITCH, 8),
					7, 5, 1, new ItemStack(TCItemRegistry.itemEntityIcon, 1, 2));
			r.setPages(new ResearchPage("entry.buffgolem.first"), new ResearchPage("entry.buffgolem.second"), new ResearchPage("entry.buffgolem.third"), new ResearchPage("entry.buffgolem.fourth"), infusionPage("BuffGolem"), new ResearchPage("FOCUSEXCAVATION", "entry.buffgolem.fifth"), new ResearchPage("WRATHATTRACTION", "entry.buffgolem.sixth"));
			r.setConcealed().setParents("BRAINLITTERING").setParentsHidden("golemPowder", "GOLEMTHAUMIUM", "GOLEMFETTER").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("BUFFGOLEM", 5);

			r = new ResearchItem("DRAINAGESYRINGE", catName, new AspectList().add(Aspect.TOOL, 8).add(Aspect.VOID, 8).add(Aspect.SLIME, 8).add(Aspect.HUNGER, 8),
					9, 0, 2, new ItemStack(TCItemRegistry.drainageSyringe));
			r.setPages(new ResearchPage("entry.drainagesyringe.first"), new ResearchPage("entry.drainagesyringe.second"), infusionPage("DrainageSyringe"));
			r.setConcealed().setParents("TCHORIZONS").setParentsHidden(new String[]{"injector", "ESSENTIARESERVOIR"}).registerResearchItem();

			if (Integration.automagy){
				r = new ResearchItem("REDPOWEREDMIND", catName, new AspectList().add(Aspect.MIND, 10).add(Aspect.MECHANISM, 10).add(Aspect.CRYSTAL, 10).add(Aspect.SENSES, 10),
						9, 4, 3, new ItemStack(TCItemRegistry.itemEntityIcon, 1, 3));
				r.setPages(new ResearchPage("entry.redpoweredmind.first"), infusionPage("RedPoweredMind"), new ResearchPage("entry.redpoweredmind.second"));
				r.setConcealed().setParents("BRAINLITTERING", "LIQUIDDEATH", "ELDRITCHMINOR").setParentsHidden("CRYSTALBRAIN").registerResearchItem();
				ThaumcraftApi.addWarpToResearch("REDPOWEREDMIND", 5);
			}
			if (Integration.thaumicBases && Integration.allowTobacco){
				r = new ResearchItem("DOPESQUID", catName, new AspectList().add(Aspect.PLANT, 10).add(Aspect.BEAST, 10).add(Aspect.MAGIC, 10).add(Aspect.MAN, 10).add(Aspect.FLIGHT, 10),
						11, -2, 2, new ItemStack(TCItemRegistry.itemEntityIcon, 1, 6));
				r.setPages(new ResearchPage("entry.dopesquid.first"), infusionPage("DopeSquid"));
				r.setConcealed().setParents("TCHORIZONS").setParentsHidden("infusionVat", "TB.Tobacco.Eldritch", "TB.Tobacco.Wispy", "TB.Tobacco.Angry", "TB.Tobacco.Mining",
						"TB.Tobacco.Wisdom", "TB.Tobacco.Tainted", "TB.Tobacco.Saturating", "TB.Tobacco.Sanity").registerResearchItem();
				ThaumcraftApi.addWarpToResearch("DOPESQUID", 1);
			}
		}

		if (Integration.taintedMagic){
			r = new ResearchItem("TCTAINTEDMAGIC", catName, new AspectList().add(Aspect.MAGIC, 8).add(Aspect.TAINT, 8),
					-6, 5, 1, new ResourceLocation("taintedmagic:textures/misc/tab_tm.png"));
			r.setPages(new ResearchPage("entry.tctaintedmagic.first"));
			r.setRound().setParents("THAUMATURGES").setParentsHidden("HOLLOWDAGGER").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("TCTAINTEDMAGIC", 3);

			r = new ResearchItem("CRIMSONANNALES", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.LIFE, 8),
					-8, 6, 1, new ItemStack(TCItemRegistry.resource, 1, 3));
			r.setPages(new ResearchPage("entry.crimsonannales.first"), new ResearchPage("entry.crimsonannales.second"));
			r.setLost().setRound().setParents("TCTAINTEDMAGIC").setParentsHidden("CRIMSON").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("CRIMSONANNALES", 3);

			r = new ResearchItem("CRIMSONPONTIFEX", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.LIFE, 8).add(DarkAspects.PRIDE, 8).add(Aspect.VOID, 8).add(Aspect.ELDRITCH, 8),
					-8, 8, 1, new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/pontifex.png"));
			r.setPages(new ResearchPage("entry.crimsonpontifex.first"));
			r.setRound().setConcealed().setParents("CRIMSONANNALES").setParentsHidden("BRIGHTESTONE").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("CRIMSONPONTIFEX", 2);

			r = new ResearchItem("MATERIALPEELER", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.VOID, 8).add(Aspect.FLESH, 8).add(Aspect.EXCHANGE, 8).add(Aspect.ORDER, 8),
					-8, 10, 1, new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/peeler.png"));
			r.setPages(new ResearchPage("entry.materialpeeler.first"), new ResearchPage("entry.materialpeeler.second"));
			r.setLost().setSiblings("FLESHCRUCIBLE").setRound().setParents("CRIMSONPONTIFEX").setParentsHidden("CREATION").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("MATERIALPEELER", 4);

			r = new ResearchItem("PONTIFEXROBE", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.LIFE, 8).add(DarkAspects.PRIDE, 8).add(Aspect.ARMOR, 8).add(Aspect.CRAFT, 8),
					-7, 11, 1, new ItemStack(TCItemRegistry.pontifexHead));
			r.setPages(new ResearchPage("entry.pontifexrobe.first"), riftMultiPage(new String[]{"PontifexRobeHead", "PontifexRobeChest", "PontifexRobeLegs", "PontifexRobeFeet"}), new ResearchPage("entry.pontifexrobe.second"));
			r.setLost().setParents("CRIMSONPONTIFEX", "MATERIALPEELER").setParentsHidden("PRAETORARMOR").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("PONTIFEXROBE", 2);

			r = new ResearchItem("CRIMSONINITIATION", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.LIFE, 8).add(Aspect.EXCHANGE, 8).add(Aspect.MIND, 8),
					-6, 12, 1, new ResourceLocation(ThaumicConcilium.MODID + ":textures/misc/crimson_initiation.png"));
			r.setPages(new ResearchPage("entry.crimsonimitiation.first"));
			r.setRound().setConcealed().setParents("PONTIFEXROBE").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("CRIMSONINITIATION", 3);


			r = new ResearchItem("PONTIFEXHAMMER", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.LIFE, 8).add(DarkAspects.PRIDE, 8).add(Aspect.WEAPON, 8).add(Aspect.HUNGER, 8),
					-6, 10, 1, new ItemStack(TCItemRegistry.pontifexHammer));
			r.setPages(new ResearchPage("entry.pontifexhammer.first"), infusionPage("PontifexHammer"));
			r.setLost().setParents("CRIMSONPONTIFEX").setParentsHidden("CRIMSONBLADE").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("PONTIFEXHAMMER", 2);

			r = new ResearchItem("FLESHCRUCIBLE", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.VOID, 8).add(Aspect.FLESH, 8).add(Aspect.EXCHANGE, 8).add(Aspect.FIRE, 8),
					-8, 12, 3, new ItemStack(TCBlockRegistry.FLESH_CRUCIBLE));
			r.setPages(new ResearchPage("entry.fleshcrucible.first"), infusionPage("FleshCrucible"));
			r.setConcealed().setParents("MATERIALPEELER").setParentsHidden("QUICKSILVERCRUCIBLE").registerResearchItem();
			ThaumcraftApi.addWarpToResearch("FLESHCRUCIBLE", 4);

			if (Integration.witchery) {
				r = new ResearchItem("CRIMSONSPELLS", catName, new AspectList().add(Aspect.MAN, 8).add(Aspect.LIFE, 8).add(Aspect.MIND, 8).add(Aspect.MAGIC, 8).add(Aspect.WEAPON, 8),
						-5, 9, 1, new ItemStack(ConfigItems.itemResource, 1, 13));
				r.setPages(new ResearchPage("entry.crimsonspells.first"), new ResearchPage("entry.crimsonspells.second"));
				r.setLost().setParents("CRIMSONPONTIFEX").registerResearchItem();
				ThaumcraftApi.addWarpToResearch("CRIMSONSPELLS", 1);
			}
		}

		/*
		r = new ResearchItem("ELDRITCHTRAP", catName, new AspectList().add(Aspect.ELDRITCH, 10).add(Aspect.EARTH, 5).add(Aspect.ENERGY, 20),
				1, 9, 1, new ItemStack(TCBlockRegistry.ELDRITCH_TRAP_BLOCK));
		r.setPages(new ResearchPage("entry.eldritchtrap.first"), arcanePage("EldritchTrap"));
		r.setConcealed().setSecondary().setParents("REFLECTIONFOCI").setParentsHidden("OUTERREV").registerResearchItem();
		*/
	}

	private static ResearchPage structPage(String key) {return new ResearchPage((List) recipes.get(key));}

	private static ResearchPage recipePage(String key) {
		return new ResearchPage((IRecipe) recipes.get(key));
	}

	private static ResearchPage arcanePage(String key) {
		return new ResearchPage((IArcaneRecipe) recipes.get(key));
	}

	private static ResearchPage infusionPage(String key) {
		return new ResearchPage((InfusionRecipe) recipes.get(key));
	}

	private static ResearchPage cruciblePage(String key) {
		return new ResearchPage((CrucibleRecipe) recipes.get(key));
	}
	private static ResearchPage riftPage(String key) {
		ResearchPage page = new ResearchPage("");
		ChainedRiftRecipe recipe = (ChainedRiftRecipe)recipes.get(key);
		page.recipe = recipe;
		page.recipeOutput = recipe.getRecipeOutput();
		page.type = ResearchPage.PageType.CRUCIBLE_CRAFTING;
		return page;
	}

	private static ResearchPage riftMultiPage(String[] keys) {
		ResearchPage page = new ResearchPage("");
		ArrayList<ChainedRiftRecipe> recipes = new ArrayList<>();
		for (String s : keys){
			ChainedRiftRecipe recipe = (ChainedRiftRecipe) Thaumonomicon.recipes.get(s);
			if (recipe != null){
				recipes.add(recipe);
			}
		}
		page.recipe = recipes.toArray();
		//page.recipeOutput = recipe.getRecipeOutput();
		page.type = ResearchPage.PageType.CRUCIBLE_CRAFTING;
		return page;
	}

}
