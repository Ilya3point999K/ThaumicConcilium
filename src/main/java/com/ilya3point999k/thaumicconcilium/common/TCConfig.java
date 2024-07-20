package com.ilya3point999k.thaumicconcilium.common;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import thaumcraft.api.wands.FocusUpgradeType;

import java.io.File;

public class TCConfig {
    private static ConfigCategory category;
    public static int refreezeUpgradeID;
    public static int vacuumUpgradeID;
    public static int pneumoStrikeUpgradeID;

    public static int largeBallUpgradeID;
    public static int starUpgradeID;
    public static int creeperUpgradeID;
    public static int amorphicUpgradeID;
    public static int randomBangUpgradeID;
    public static int flashbangUpgradeID;
    public static int fulfillmentUpgradeID;
    public static int vitaminizeUpgradeID;
    public static int oblivionUpgradeID;
    public static int warmingUpUpgradeID;
    public static int taintPurificationUpgradeID;
    public static int thoughtManifestationUpgradeID;
    public static int fluxVaporizationUpgradeID;
    public static int wispLauncherUpgradeID;
    public static int dematerializationUpgradeID;
    public static int massHysteriaUpgradeID;
    public static int selfFlagellationUpgradeID;
    public static int burnoutUpgradeID;
    public static int primalEssenceUpgradeID;

    public static int thaumaturgeSpawnChance;
    public static int madThaumaturgeSpawnChance;
    public static int quicksilverElementalSpawnChance;
    public static int dissolvedSpawnChance;
    public static int overanimatedSpawnChance;
    public static int paranoidWarriorSpawnChance;
    public static int vengefulGolemSpawnChance;
    public static int madThaumaturgeReplacesBrainyZombieChance;
    public static int crimsonPaladinReplacesCultistWarriorChance;

    public static int causalBouillonID;

    public static void configurate(File f){
        Configuration conf = new Configuration(f);
        try {
            conf.load();
            refreezeUpgradeID = conf.getInt("refreezeID", "focus", 100, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            vacuumUpgradeID = conf.getInt("vacuumID", "focus", 101, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            pneumoStrikeUpgradeID = conf.getInt("pneumoStrikeID", "focus", 102, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            largeBallUpgradeID = conf.getInt("largeBallUpgradeID", "focus", 103, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            starUpgradeID = conf.getInt("starUpgradeID", "focus", 104, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            creeperUpgradeID = conf.getInt("creeperUpgradeID", "focus", 105, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            amorphicUpgradeID = conf.getInt("amorphicUpgradeID", "focus", 106, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            randomBangUpgradeID = conf.getInt("randomBangUpgradeID", "focus", 107, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            flashbangUpgradeID = conf.getInt("flashbangUpgradeID", "focus", 108, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            fulfillmentUpgradeID = conf.getInt("fulfillmentUpgradeID", "focus", 109, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            vitaminizeUpgradeID = conf.getInt("vitaminizeUpgradeID", "focus", 110, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            oblivionUpgradeID = conf.getInt("oblivionUpgradeID", "focus", 111, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            warmingUpUpgradeID = conf.getInt("warmingUpUpgradeID", "focus", 112, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            taintPurificationUpgradeID = conf.getInt("taintPurificationUpgradeID", "focus", 113, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            thoughtManifestationUpgradeID = conf.getInt("thoughtManifestationUpgradeID", "focus", 114, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            fluxVaporizationUpgradeID = conf.getInt("fluxVaporizationUpgradeID", "focus", 115, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            wispLauncherUpgradeID = conf.getInt("wispLauncherUpgradeID", "focus", 116, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            dematerializationUpgradeID = conf.getInt("dematerializationUpgradeID", "focus", 117, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            massHysteriaUpgradeID = conf.getInt("massHysteriaUpgradeID", "focus", 118, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            selfFlagellationUpgradeID = conf.getInt("selfFlagellationUpgradeID", "focus", 119, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            burnoutUpgradeID = conf.getInt("burnoutUpgradeID", "focus", 120, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");
            primalEssenceUpgradeID = conf.getInt("primalEssenceUpgradeID", "focus", 121, FocusUpgradeType.types.length+1, Short.MAX_VALUE, "");

            thaumaturgeSpawnChance = conf.getInt("thaumaturgeSpawnChance", "mobs", 10, 0, Integer.MAX_VALUE, "");
            madThaumaturgeSpawnChance = conf.getInt("madThaumaturgeSpawnChance", "mobs", 15, 0, Integer.MAX_VALUE, "");
            quicksilverElementalSpawnChance = conf.getInt("quicksilverElementalSpawnChance", "mobs", 10, 0, Integer.MAX_VALUE, "");
            dissolvedSpawnChance = conf.getInt("dissolvedSpawnChance", "mobs", 10, 0, Integer.MAX_VALUE, "");
            overanimatedSpawnChance = conf.getInt("overanimatedSpawnChance", "mobs", 10, 0, Integer.MAX_VALUE, "");
            paranoidWarriorSpawnChance = conf.getInt("paranoidWarriorSpawnChance", "mobs", 10, 0, Integer.MAX_VALUE, "");
            vengefulGolemSpawnChance = conf.getInt("vengefulGolemSpawnChance", "mobs", 10, 0, Integer.MAX_VALUE, "");

            madThaumaturgeReplacesBrainyZombieChance = conf.getInt("madThaumaturgeReplacesBrainyZombieChance", "mobs", 80, 0, Integer.MAX_VALUE, "99 - never, 0 - always");
            crimsonPaladinReplacesCultistWarriorChance = conf.getInt("crimsonPaladinReplacesCultistWarriorChance", "mobs", 80, 0, Integer.MAX_VALUE, "99 - never, 0 - always");

            causalBouillonID = conf.get("causalBouillonID", "dim", 33).getInt();
        } catch (Exception e){
            ThaumicConcilium.logger.log(Level.ERROR, "Thaumic Concilium can't load config");
        } finally {
            conf.save();
        }
    }
}
