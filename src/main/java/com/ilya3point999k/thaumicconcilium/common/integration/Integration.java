package com.ilya3point999k.thaumicconcilium.common.integration;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.minetweaker.MineTweakerIntegration;
import cpw.mods.fml.common.Loader;
import fox.spiteful.forbidden.compat.Compat;
import net.minecraft.item.Item;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

import java.lang.reflect.Method;


public class Integration {
    public static boolean thaumicBases = false;
    public static boolean taintedMagic = false;
    public static boolean gadomancy = false;
    public static boolean horizons = false;
    public static boolean automagy = false;
    public static boolean dyes = false;
    public static boolean witchery = false;
    public static boolean bloodArsenal = false;
    public static boolean alfheim = false;
    public static boolean avaritia = false;

    public static boolean minetweaker = false;

    public static Class witcheryClass;
    public static Class witcheryInfusionClass;

    public static Class crystalEye = null;

    public static Item crimsonDagger = null;
    public static boolean allowTobacco = false;
    public static Item tobaccoitem = null;
    public static Item tobaccoleaves = null;
    public static Item tmMaterial = null;
    public static Item earmuffs = null;
    public static Item witchery_ingredient = null;
    public static Item paladinHelm = null;
    public static Item archerHelm = null;
    public static Item archerChest = null;
    public static Item archerLegs = null;
    public static Item rangerHelm = null;
    public static Item rangerChest = null;
    public static Item rangerLegs = null;
    public static Item bloodMoney = null;
    public static String[] spellNames = new String[]{"Crimson_Raid"};

    public static int alfheimDimension = 0;
    public static Aspect tinctura = null;
    public static boolean tincturaAllowed = false;
    public static int tintPotionId = -1;

    public static void preInit() throws Exception {
        thaumicBases = Loader.isModLoaded("thaumicbases");
        taintedMagic = Loader.isModLoaded("TaintedMagic");
        gadomancy = Loader.isModLoaded("gadomancy");
        horizons = Loader.isModLoaded("ThaumicHorizons");
        automagy = Loader.isModLoaded("Automagy");
        dyes = Loader.isModLoaded("thaumicdyes");
        witchery = Loader.isModLoaded("witchery");
        bloodArsenal = Loader.isModLoaded("BloodArsenal");
        alfheim = Loader.isModLoaded("alfheim");
        avaritia = Loader.isModLoaded("Avaritia");
        minetweaker = Loader.isModLoaded("MineTweaker3");

        if (!ConfigHandler.enableKami){
            throw new Exception("Thaumic Concilium - turning off KAMI module of Thaumic Tinkerer is not supported.");
        }

        if (gadomancy) {
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.GadomancyIntegration");
                Method m = clazz.getMethod("register");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Gadomancy integration: " + t);
            }
        }

    }

    public static void init(){
        if (Compat.am2){
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.AM2Integration");
                Method m = clazz.getMethod("register");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Ars Magica integration: " + t);
            }
        }
    }

    public static void postInit() {

        if (taintedMagic) {
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.TaintedMagicIntegration");
                Method m = clazz.getMethod("register");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Tainted Magic integration: " + t);
            }
        }
        if (thaumicBases){
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.BasesIntegration");
                Method m = clazz.getMethod("register");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Thaumic Bases integration: " + t);
            }
        }
        if(dyes){
            try {
                paladinHelm = Compat.getItem("thaumicdyes", "CultistPaladinHelm");
                archerHelm = Compat.getItem("thaumicdyes", "CultistArcherHelm");
                archerChest = Compat.getItem("thaumicdyes", "CultistArcherChest");
                archerLegs = Compat.getItem("thaumicdyes", "CultistArcherLegs");
                rangerHelm = Compat.getItem("thaumicdyes", "CultistRangerHelm");
                rangerChest = Compat.getItem("thaumicdyes", "CultistRangerChest");
                rangerLegs = Compat.getItem("thaumicdyes", "CultistRangerLegs");
            } catch (Compat.ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (horizons) {
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.HorizonsIntegration");
                Method m = clazz.getMethod("register");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Thaumic Horizons integration: " + t);
            }
        }

        if (witchery) {
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.WitcheryIntegration");
                Method m = clazz.getMethod("register");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Witchery integration: " + t);
            }
        }

        if (bloodArsenal){
            try {
                bloodMoney = Compat.getItem("BloodArsenal", "blood_money");
            } catch (Compat.ItemNotFoundException e) {
                System.err.println("[ThaumicConcilium] Failed to find Blood Arsenal' Blood Money: " + e);
                throw new RuntimeException(e);
            }
        }

        if (alfheim){
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.AlfheimIntegration");
                Method m = clazz.getMethod("register");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Alfheim integration: " + t);
            }
        }

        if (gadomancy) {
            try {
                Class<?> clazz = Class.forName("com.ilya3point999k.thaumicconcilium.common.integration.GadomancyIntegration");
                Method m = clazz.getMethod("postRegister");
                m.invoke(null);
            } catch (Throwable t) {
                System.err.println("[ThaumicConcilium] Failed to register Gadomancy integration: " + t);
            }
        }

        if(minetweaker) {
            MineTweakerIntegration.register();
            ThaumicConcilium.logger.info("MineTweaker integration loaded");
        }
    }
}
