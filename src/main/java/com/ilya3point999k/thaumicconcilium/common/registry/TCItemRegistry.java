package com.ilya3point999k.thaumicconcilium.common.registry;

import com.ilya3point999k.thaumicconcilium.common.entities.ItemSpawnerEgg;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.items.*;
import com.ilya3point999k.thaumicconcilium.common.items.baubles.BurdeningAmulet;
import com.ilya3point999k.thaumicconcilium.common.items.baubles.RingOfBlusteringLight;
import com.ilya3point999k.thaumicconcilium.common.items.baubles.TightBelt;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.DumpJackboots;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.ElementalSurferShorts;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.PontifexRobe;
import com.ilya3point999k.thaumicconcilium.common.items.equipment.RunicWindings;
import com.ilya3point999k.thaumicconcilium.common.items.wands.Bracelets;
import com.ilya3point999k.thaumicconcilium.common.items.wands.TerrasteelCoreUpdate;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.*;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.forbidden.DarkAspects;
import fox.spiteful.forbidden.compat.Compat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.StaffRod;

public class TCItemRegistry {
	public static StaffRod STAFF_ROD_TERRASTEEL;
	public static Item elementalSurferShorts;
	public static Item dumpJackboots;
	public static Item runicBodyWindings;
	public static Item runicLegsWindings;
	//public static Item visCapsule;
	public static Item bottleOfThickTaint;
	public static Item bottleOfClearWater;
	public static Item spotlightFoci;
	public static Item warpExtractionFoci;
	public static Item wrathAttractionFoci;
	public static Item visConductorFoci;
	public static Item polishmentDevice;
	public static Item coolingFoci;
	public static Item reflectionFoci;
	public static Item taintAnimationFoci;
	public static Item positiveBurstFoci;
	public static Item signalFoci;
	public static Item impulseFoci;
	public static Item scribingFoci;
	public static Item alchemistSpreeFoci;
	public static Item etherealManipulatorFoci;
	public static Item thaumaturgeDrum;
	public static Item ringOfBlusteringLight;
	public static Item burdeningAmulet;
	public static Item tightBelt;
	public static Item itemSpawnerEgg;

	public static Item castingBracelet;
	public static Item riftGem;
	public static Item terraCastGem;
	public static Item golemCores;
	public static Item itemEntityIcon;
	public static Item drainageSyringe;
	public static Item astralMonitor;
	public static Item pontifexHammer;
	public static Item shardMill;
	public static Item resource;
	public static Item eyedrops;
	public static Item pontifexHead;
	public static Item pontifexChest;
	public static Item pontifexLegs;
	public static Item pontifexFeet;


	public static void init() {

		elementalSurferShorts = new ElementalSurferShorts();
		GameRegistry.registerItem(elementalSurferShorts, "ElementalSurferShorts");

		dumpJackboots = new DumpJackboots();
		GameRegistry.registerItem(dumpJackboots, "DumpJackboots");

		runicBodyWindings = new RunicWindings(0, 1);
		runicBodyWindings.setUnlocalizedName("RunicBodyWindings");
		GameRegistry.registerItem(runicBodyWindings, "RunicBodyWindings");

		runicLegsWindings = new RunicWindings(0, 2);
		runicLegsWindings.setUnlocalizedName("RunicLegsWindings");
		GameRegistry.registerItem(runicLegsWindings, "RunicLegsWindings");

		//visCapsule = new VisCapsule(null);
		//GameRegistry.registerItem(visCapsule, "VisCapsule");

		bottleOfThickTaint = new BottleOfThickTaint(null);
		GameRegistry.registerItem(bottleOfThickTaint, "BottleOfThickTaint");

		bottleOfClearWater = new BottleOfClearWater(null);
		GameRegistry.registerItem(bottleOfClearWater, "BottleOfClearWater");

		spotlightFoci = new SpotlightFoci();
		GameRegistry.registerItem(spotlightFoci, "SpotlightFoci");
		
		warpExtractionFoci = new WarpExtractionFoci();
		GameRegistry.registerItem(warpExtractionFoci, "WarpExtractionFoci");
		
		wrathAttractionFoci = new WrathAttractionFoci();
		GameRegistry.registerItem(wrathAttractionFoci, "WrathAttractionFoci");

		visConductorFoci = new VisConductorFoci();
		GameRegistry.registerItem(visConductorFoci, "VisConductorFoci");

		polishmentDevice = new PolishmentDevice();
		GameRegistry.registerItem(polishmentDevice, "PolishmentDevice");

		coolingFoci = new CoolingFoci();
		GameRegistry.registerItem(coolingFoci, "CoolingFoci");

		reflectionFoci = new ReflectionFoci();
		GameRegistry.registerItem(reflectionFoci, "ReflectionFoci");

		taintAnimationFoci = new TaintAnimationFoci();
		GameRegistry.registerItem(taintAnimationFoci, "TaintAnimationFoci");

		positiveBurstFoci = new PositiveBurstFoci();
		GameRegistry.registerItem(positiveBurstFoci, "PositiveBurstFoci");

		signalFoci = new SignalFoci();
		GameRegistry.registerItem(signalFoci, "SignalFoci");

		impulseFoci = new ImpulseFoci();
		GameRegistry.registerItem(impulseFoci, "ImpulseFoci");

		scribingFoci = new ScribingFoci();
		GameRegistry.registerItem(scribingFoci, "ScribingFoci");

		alchemistSpreeFoci = new AlchemistSpreeFoci();
		GameRegistry.registerItem(alchemistSpreeFoci, "AlchemistSpreeFoci");

		etherealManipulatorFoci = new EtherealManipulatorFoci();
		GameRegistry.registerItem(etherealManipulatorFoci, "EtherealManipulatorFoci");

		thaumaturgeDrum = new ThaumaturgeDrum();
		GameRegistry.registerItem(thaumaturgeDrum, "ThaumaturgeDrum");
		ThaumcraftApi.registerObjectTag(new ItemStack(thaumaturgeDrum, 1, 1), new AspectList().add(Aspect.MIND, 16).add(Aspect.CLOTH, 16).add(Aspect.TOOL, 16).add(Aspect.TAINT, 16).add(Aspect.BEAST, 16));

		ringOfBlusteringLight = new RingOfBlusteringLight();
		GameRegistry.registerItem(ringOfBlusteringLight, "RingOfBlusteringLight");

		burdeningAmulet = new BurdeningAmulet();
		GameRegistry.registerItem(burdeningAmulet, "BurdeningAmulet");

		tightBelt = new TightBelt();
		GameRegistry.registerItem(tightBelt, "TightBelt");

		itemSpawnerEgg = new ItemSpawnerEgg();
		GameRegistry.registerItem(itemSpawnerEgg, "ItemSpawnerEgg");

		shardMill = new ShardMill();
		GameRegistry.registerItem(shardMill, "ShardMill");

		resource = new ItemResource();
		GameRegistry.registerItem(resource, "ItemResource");
		ThaumcraftApi.registerObjectTag(new ItemStack(resource, 1, 1), new AspectList().add(Aspect.LIFE, 64));
		if (Integration.taintedMagic) {
			ThaumcraftApi.registerObjectTag(new ItemStack(resource, 1, 3), new AspectList().add(Aspect.MIND, 16).add(Aspect.HUNGER, 16).add(Aspect.ELDRITCH, 16).add(Aspect.MAN, 16));
		}

		eyedrops = new CoagulatingEyeDrops();
		GameRegistry.registerItem(eyedrops, "CoagulatingEyeDrops");

		itemEntityIcon = new ItemEntityIcon();
		GameRegistry.registerItem(itemEntityIcon, "ItemEntityIcon");

		astralMonitor = new AstralMonitor();
		GameRegistry.registerItem(astralMonitor, "AstralMonitor");
		ThaumcraftApi.registerObjectTag(new ItemStack(astralMonitor), new AspectList().add(Aspect.MIND, 16).add(Aspect.SENSES, 16).add(Aspect.TOOL, 8).add(Aspect.VOID, 8));

		if(Integration.thaumicBases){
			if(Compat.botan){
				STAFF_ROD_TERRASTEEL = new StaffRod("terrasteel", 50, (ItemStack)null, 99, new TerrasteelCoreUpdate(), new ResourceLocation("thaumcraft", "textures/models/wand_rod_obsidian.png"));
			}
			castingBracelet = new Bracelets();
			GameRegistry.registerItem(castingBracelet, "CastingBracelet");
		}

		if(Integration.gadomancy){
			riftGem = new RiftGem();
			GameRegistry.registerItem(riftGem, "RiftGem");
			ThaumcraftApi.registerObjectTag(new ItemStack(riftGem,1,1), new AspectList().add(Aspect.VOID, 32).add(Aspect.CRYSTAL, 32).add(Aspect.TRAVEL, 32));
			terraCastGem = new TerraCastGem();
			GameRegistry.registerItem(terraCastGem, "TerraCastGem");
			ThaumcraftApi.registerObjectTag(new ItemStack(terraCastGem,1,1), new AspectList().add(Aspect.VOID, 32).add(Aspect.CRYSTAL, 32).add(Aspect.MIND, 32).add(Aspect.EARTH, 32));

			golemCores = new ItemGolemCore();
			GameRegistry.registerItem(golemCores, "ItemGolemCore");
		}

		if(Integration.horizons){
			drainageSyringe = new DrainageSyringe();
			GameRegistry.registerItem(drainageSyringe, "DrainageSyringe");
		}

		if (Integration.automagy){
		}
		if (Integration.taintedMagic){
			pontifexHammer = new PontifexHammer();
			GameRegistry.registerItem(pontifexHammer, "PontifexHammer");

			pontifexHead = new PontifexRobe(0, 0).setUnlocalizedName("ItemPontifexRobeHead");
			GameRegistry.registerItem(pontifexHead, "PontifexRobeHead");

			pontifexChest = new PontifexRobe(0, 1).setUnlocalizedName("ItemPontifexRobeChest");
			GameRegistry.registerItem(pontifexChest, "PontifexRobeChest");

			pontifexLegs = new PontifexRobe(0, 2).setUnlocalizedName("ItemPontifexRobeLegs");
			GameRegistry.registerItem(pontifexLegs, "PontifexRobeLegs");

			pontifexFeet = new PontifexRobe(0, 3).setUnlocalizedName("ItemPontifexRobeFeet");
			GameRegistry.registerItem(pontifexFeet, "PontifexRobeFeet");

			ThaumcraftApi.registerObjectTag(new ItemStack(pontifexHead), new AspectList().add(Aspect.MIND, 16).add(Aspect.CLOTH, 16).add(Aspect.ARMOR, 16).add(Aspect.HUNGER, 16).add(DarkAspects.PRIDE, 16));
			ThaumcraftApi.registerObjectTag(new ItemStack(pontifexChest), new AspectList().add(Aspect.MIND, 16).add(Aspect.CLOTH, 16).add(Aspect.ARMOR, 16).add(Aspect.HUNGER, 16).add(DarkAspects.PRIDE, 16));
			ThaumcraftApi.registerObjectTag(new ItemStack(pontifexLegs), new AspectList().add(Aspect.MIND, 16).add(Aspect.CLOTH, 16).add(Aspect.ARMOR, 16).add(Aspect.HUNGER, 16).add(DarkAspects.PRIDE, 16));
			ThaumcraftApi.registerObjectTag(new ItemStack(pontifexFeet), new AspectList().add(Aspect.MIND, 16).add(Aspect.CLOTH, 16).add(Aspect.ARMOR, 16).add(Aspect.HUNGER, 16).add(DarkAspects.PRIDE, 16));

		}
	}
}
