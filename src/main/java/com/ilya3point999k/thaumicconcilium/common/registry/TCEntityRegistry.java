package com.ilya3point999k.thaumicconcilium.common.registry;

import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.*;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.*;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.*;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.common.registry.EntityRegistry;
import fox.spiteful.forbidden.DarkAspects;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.Iterator;
import java.util.List;

public class TCEntityRegistry {
	public static void init() {
		int entityID = 0;
		//EntityRegistry.registerModEntity(VisCapsuleEntity.class, "VisCapsuleEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(BottleOfThickTaintEntity.class, "BottleOfThickTaintEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(BottleOfClearWaterEntity.class, "BottleOfClearWaterEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(PositiveBurstOrbEntity.class, "PositiveBurstOrbEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(CompressedBlastEntity.class, "CompressedBlastEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(ChargedWispEntity.class, "ChargedWispEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(ConcentratedWarpChargeEntity.class, "ConcentratedWarpChargeEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(ShardPowderEntity.class, "ShardPowderEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		EntityRegistry.registerModEntity(Shadowbeam.class, "Shadowbeam", entityID++, ThaumicConcilium.instance, 64, 1, true);


		EntityRegistry.registerModEntity(WrathEffectEntity.class, "WrathEffectEntity", entityID++, ThaumicConcilium.instance, 64, 1, false);
		EntityRegistry.registerModEntity(DistortionEffectEntity.class, "DistortionEffectEntity", entityID++, ThaumicConcilium.instance, 64, 1, false);

		EntityRegistry.registerModEntity(RiftEntity.class, "RiftEntity", entityID++, ThaumicConcilium.instance, 64, 3, true);
		ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID+".RiftEntity", new AspectList().add(Aspect.VOID, 16).add(Aspect.MAGIC, 4).add(Aspect.ELDRITCH, 8).add(Aspect.ENTROPY, 32));


		EntityRegistry.registerModEntity(Thaumaturge.class, "Thaumaturge", entityID++, ThaumicConcilium.instance, 64, 3, true);
		ItemSpawnerEgg.addMapping("Thaumaturge", 0x00FFFF, 0x00008B);
		ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID+".Thaumaturge", new AspectList().add(Aspect.MAN, 4).add(Aspect.MAGIC, 4).add(Aspect.AURA, 4).add(Aspect.ORDER, 4));

		if(Integration.horizons) {
			EntityRegistry.registerModEntity(Overanimated.class, "Overanimated", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ItemSpawnerEgg.addMapping("Overanimated", 0x00FFFF, 0x00008B);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".Overanimated", new AspectList().add(Aspect.MAN, 4).add(Aspect.MAGIC, 4).add(Aspect.LIFE, 4).add(Aspect.ELDRITCH, 4));

			EntityRegistry.registerModEntity(ThaumGib.class, "ThaumGib", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".ThaumGib", new AspectList().add(Aspect.MAN, 4).add(Aspect.MAGIC, 4).add(Aspect.LIFE, 4).add(Aspect.ENTROPY, 4));

			if (Integration.thaumicBases) {
				EntityRegistry.registerModEntity(DopeSquid.class, "DopeSquid", entityID++, ThaumicConcilium.instance, 64, 3, true);
				ItemSpawnerEgg.addMapping("DopeSquid", 0x00FFFF, 0x00008B);
				ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".DopeSquid", new AspectList().add(Aspect.MAN, 4).add(Aspect.MAGIC, 4).add(Aspect.LIFE, 4).add(Aspect.ENTROPY, 4));
			}

			EntityRegistry.registerModEntity(GolemBydlo.class, "GolemBydlo", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ItemSpawnerEgg.addMapping("GolemBydlo", 0x00FFFF, 0x555555);

			if (Integration.automagy){
				EntityRegistry.registerModEntity(RedPoweredMind.class, "RedPoweredMind", entityID++, ThaumicConcilium.instance, 64, 3, true);
				ItemSpawnerEgg.addMapping("RedPoweredMind", 0x00FFFF, 0x00008B);
			}

		}

		if (Integration.automagy) {
			EntityRegistry.registerModEntity(VengefulGolem.class, "VengefulGolem", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ItemSpawnerEgg.addMapping("VengefulGolem", 0x00FFFF, 0x00008B);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".VengefulGolem", new AspectList().add(Aspect.MAN, 4).add(Aspect.CRAFT, 4).add(DarkAspects.PRIDE, 4).add(Aspect.MOTION, 4));
		}

		EntityRegistry.registerModEntity(Dissolved.class, "Dissolved", entityID++, ThaumicConcilium.instance, 64, 3, true);
		ItemSpawnerEgg.addMapping("Dissolved", 0x00FFFF, 0x00008B);
		ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID+".Dissolved", new AspectList().add(Aspect.MAN, 4).add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4).add(Aspect.SLIME, 4));

		if (Integration.taintedMagic) {
			EntityRegistry.registerModEntity(Samurai.class, "Samurai", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ItemSpawnerEgg.addMapping("Samurai", 0x00FFFF, 0x00008B);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".Samurai", new AspectList().add(Aspect.MAN, 4).add(Aspect.MAGIC, 4).add(Aspect.WEAPON, 4).add(Aspect.ARMOR, 4));

			EntityRegistry.registerModEntity(CrimsonPaladin.class, "CrimsonPaladin", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ItemSpawnerEgg.addMapping("CrimsonPaladin", 0x00FFFF, 0x00008B);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".CrimsonPaladin", new AspectList().add(Aspect.MAN, 4).add(Aspect.LIFE, 4).add(Aspect.ELDRITCH, 4).add(Aspect.MAGIC, 4));

			EntityRegistry.registerModEntity(CrimsonPontifex.class, "CrimsonPontifex", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".CrimsonPontifex", new AspectList().add(Aspect.SOUL, 16).add(Aspect.LIFE, 16).add(Aspect.MAGIC, 16));
			ItemSpawnerEgg.addMapping("CrimsonPontifex", 0x00FFFF, 0x111111);

			EntityRegistry.registerModEntity(LesserPortal.class, "LesserPortal", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".LesserPortal", new AspectList().add(Aspect.ELDRITCH, 16).add(Aspect.VOID, 16).add(Aspect.MAGIC, 16));
			ItemSpawnerEgg.addMapping("LesserPortal", 0x00FFFF, 0x111111);

			EntityRegistry.registerModEntity(MaterialPeeler.class, "MaterialPeeler", entityID++, ThaumicConcilium.instance, 64, 3, true);
			ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID + ".MaterialPeeler", new AspectList().add(Aspect.ELDRITCH, 16).add(Aspect.VOID, 16).add(Aspect.MAGIC, 16).add(Aspect.FLESH, 32));

			EntityRegistry.registerModEntity(EtherealShacklesEntity.class, "EtherealShacklesEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
			EntityRegistry.registerModEntity(CrimsonOrbEntity.class, "CrimsonOrbEntity", entityID++, ThaumicConcilium.instance, 64, 1, true);
		}
		EntityRegistry.registerModEntity(QuicksilverElemental.class, "QuicksilverElemental", entityID++, ThaumicConcilium.instance, 64, 3, true);
		ItemSpawnerEgg.addMapping("QuicksilverElemental", 0x00FFFF, 0x00008B);
		ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID+".QuicksilverElemental", new AspectList().add(Aspect.MAN, 4).add(Aspect.LIFE, 4).add(Aspect.METAL, 4).add(Aspect.EXCHANGE, 4));

		EntityRegistry.registerModEntity(TaintSpiderSmart.class, "EnslavedTaintSpider", entityID++, ThaumicConcilium.instance, 64, 3, true);
		ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID+".EnslavedTaintSpider", new AspectList().add(Aspect.MAN, 4).add(Aspect.LIFE, 4).add(Aspect.METAL, 4).add(Aspect.EXCHANGE, 4));


		EntityRegistry.registerModEntity(MadThaumaturge.class, "MadThaumaturge", entityID++, ThaumicConcilium.instance, 64, 1, true);
		ItemSpawnerEgg.addMapping("MadThaumaturge", 0x00FFFF, 0x111111);
		ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID+".MadThaumaturge", new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 4).add(Aspect.ELDRITCH, 8));

		EntityRegistry.registerModEntity(BrightestOne.class, "BrightestOne", entityID++, ThaumicConcilium.instance, 64, 1, true);
		ThaumcraftApi.registerEntityTag(ThaumicConcilium.MODID+".BrightestOne", new AspectList().add(Aspect.AURA, 16).add(Aspect.LIFE, 16).add(Aspect.MAGIC, 16));
		ItemSpawnerEgg.addMapping("BrightestOne", 0x00FFFF, 0x111111);

	}

	public static void postinit(){
		List<BiomeGenBase> biomes = WorldChunkManager.allowedBiomes;
		BiomeGenBase[] allBiomes = (BiomeGenBase[])biomes.toArray(new BiomeGenBase[]{null});
		Iterator i$ = biomes.iterator();

		while(i$.hasNext()) {
			BiomeGenBase bgb = (BiomeGenBase)i$.next();
			if (bgb.getSpawnableList(EnumCreatureType.monster) != null & bgb.getSpawnableList(EnumCreatureType.monster).size() > 0) {
				EntityRegistry.addSpawn(Dissolved.class, TCConfig.dissolvedSpawnChance, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{bgb});
				EntityRegistry.addSpawn(QuicksilverElemental.class, TCConfig.quicksilverElementalSpawnChance, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{bgb});
				EntityRegistry.addSpawn(Samurai.class, TCConfig.paranoidWarriorSpawnChance, 3, 5, EnumCreatureType.monster, new BiomeGenBase[]{bgb});
				EntityRegistry.addSpawn(VengefulGolem.class, TCConfig.vengefulGolemSpawnChance, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{bgb});
				EntityRegistry.addSpawn(Overanimated.class, TCConfig.overanimatedSpawnChance, 2, 3, EnumCreatureType.monster, new BiomeGenBase[]{bgb});
				EntityRegistry.addSpawn(MadThaumaturge.class, TCConfig.madThaumaturgeSpawnChance, 2, 3, EnumCreatureType.monster, new BiomeGenBase[]{bgb});
			}
			if (bgb.getSpawnableList(EnumCreatureType.creature) != null & bgb.getSpawnableList(EnumCreatureType.creature).size() > 0) {
				EntityRegistry.addSpawn(Thaumaturge.class, TCConfig.thaumaturgeSpawnChance, 1, 3, EnumCreatureType.creature, new BiomeGenBase[]{bgb});
			}

		}

	}
}
