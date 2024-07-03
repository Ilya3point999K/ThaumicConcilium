package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.TCConfig;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;

public class TCFociUpgrades {
    public static FocusUpgradeType refreeze;
    public static FocusUpgradeType vacuum;
    public static FocusUpgradeType pneumoStrike;
    public static FocusUpgradeType largeBall;
    public static FocusUpgradeType star;
    public static FocusUpgradeType creeper;
    public static FocusUpgradeType amorphic;
    public static FocusUpgradeType randomBang;
    public static FocusUpgradeType flashbang;
    public static FocusUpgradeType fulfillment;
    public static FocusUpgradeType vitaminize;
    public static FocusUpgradeType oblivion;
    public static FocusUpgradeType warmingUp;
    public static FocusUpgradeType taintPurification;
    public static FocusUpgradeType thoughtManifestation;
    public static FocusUpgradeType fluxVaporization;
    public static FocusUpgradeType wispLauncher;
    public static FocusUpgradeType dematerialization;
    public static FocusUpgradeType massHysteria;
    public static FocusUpgradeType selfFlagellation;
    public static FocusUpgradeType burnout;
    public static FocusUpgradeType primalEssence;

    public static void init(){
        refreeze = new FocusUpgradeType(TCConfig.refreezeUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/refreeze.png"), "focus.upgrade.refreeze.name", "focus.upgrade.refreeze.text", new AspectList().add(Aspect.COLD, 150).add(Aspect.EXCHANGE, 90));
        vacuum = new FocusUpgradeType(TCConfig.vacuumUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/vacuum.png"), "focus.upgrade.vacuum.name", "focus.upgrade.vacuum.text", new AspectList().add(Aspect.AIR, 30).add(Aspect.TRAP, 50).add(Aspect.VOID, 100));
        pneumoStrike = new FocusUpgradeType(TCConfig.pneumoStrikeUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/pneumo.png"), "focus.upgrade.pneumoStrike.name", "focus.upgrade.pneumoStrike.text", new AspectList().add(Aspect.AIR, 100).add(Aspect.FLIGHT, 90));
        largeBall = new FocusUpgradeType(TCConfig.largeBallUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/large_ball.png"), "focus.upgrade.largeBall.name", "focus.upgrade.largeBall.text", new AspectList().add(Aspect.AIR, 100).add(Aspect.ENERGY, 90));
        star = new FocusUpgradeType(TCConfig.starUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/star.png"), "focus.upgrade.star.name", "focus.upgrade.star.text", new AspectList().add(Aspect.AIR, 100).add(Aspect.ELDRITCH, 90));
        creeper = new FocusUpgradeType(TCConfig.creeperUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/creeper.png"), "focus.upgrade.creeper.name", "focus.upgrade.creeper.text", new AspectList().add(Aspect.AIR, 100).add(Aspect.BEAST, 90));
        amorphic = new FocusUpgradeType(TCConfig.amorphicUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/amorphic.png"), "focus.upgrade.amorphic.name", "focus.upgrade.amorphic.text", new AspectList().add(Aspect.AIR, 100).add(Aspect.ENTROPY, 90));
        randomBang = new FocusUpgradeType(TCConfig.randomBangUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/random_bang.png"), "focus.upgrade.randomBang.name", "focus.upgrade.randomBang.text", new AspectList().add(Aspect.AIR, 100).add(Aspect.ENTROPY, 90));
        flashbang = new FocusUpgradeType(TCConfig.flashbangUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/flashbang.png"), "focus.upgrade.flashbang.name", "focus.upgrade.flashbang.text", new AspectList().add(Aspect.AIR, 100).add(Aspect.LIGHT, 90));
        fulfillment = new FocusUpgradeType(TCConfig.fulfillmentUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/fulfillment.png"), "focus.upgrade.fulfillment.name", "focus.upgrade.fulfillment.text", new AspectList().add(Aspect.ORDER, 100).add(Aspect.ARMOR, 90));
        vitaminize = new FocusUpgradeType(TCConfig.vitaminizeUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/vitaminize.png"), "focus.upgrade.vitaminize.name", "focus.upgrade.vitaminize.text", new AspectList().add(Aspect.ORDER, 100).add(Aspect.HEAL, 90));
        oblivion = new FocusUpgradeType(TCConfig.oblivionUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/oblivion.png"), "focus.upgrade.oblivion.name", "focus.upgrade.oblivion.text", new AspectList().add(Aspect.MIND, 100).add(Aspect.ELDRITCH, 90));
        warmingUp = new FocusUpgradeType(TCConfig.warmingUpUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/warming_up.png"), "focus.upgrade.warmingUp.name", "focus.upgrade.warmingUp.text", new AspectList().add(Aspect.HEAL, 250).add(Aspect.FIRE, 150));
        taintPurification = new FocusUpgradeType(TCConfig.taintPurificationUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/taint_purification.png"), "focus.upgrade.taintPurification.name", "focus.upgrade.taintPurification.text", new AspectList().add(Aspect.HEAL, 250).add(Aspect.TAINT, 150));
        thoughtManifestation = new FocusUpgradeType(TCConfig.thoughtManifestationUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/thought_manifestation.png"), "focus.upgrade.thoughtManifestation.name", "focus.upgrade.thoughtManifestation.text", new AspectList().add(Aspect.MIND, 300).add(Aspect.TAINT, 250));
        fluxVaporization = new FocusUpgradeType(TCConfig.fluxVaporizationUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/flux_vaporization.png"), "focus.upgrade.fluxVaporization.name", "focus.upgrade.fluxVaporization.text", new AspectList().add(Aspect.FIRE, 500).add(Aspect.TAINT, 250));
        wispLauncher = new FocusUpgradeType(TCConfig.wispLauncherUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/wisp_launcher.png"), "focus.upgrade.wispLauncher.name", "focus.upgrade.wispLauncher.text", new AspectList().add(Aspect.AIR, 500).add(Aspect.AURA, 250));
        dematerialization = new FocusUpgradeType(TCConfig.dematerializationUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/dematerialization.png"), "focus.upgrade.dematerialization.name", "focus.upgrade.dematerialization.text", new AspectList().add(Aspect.ENTROPY, 500).add(Aspect.FLESH, 500).add(Aspect.AURA, 1000));
        massHysteria = new FocusUpgradeType(TCConfig.massHysteriaUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/mass_hysteria.png"), "focus.upgrade.massHysteria.name", "focus.upgrade.massHysteria.text", new AspectList().add(Aspect.MIND, 500).add(Aspect.ENTROPY, 500).add(Aspect.ELDRITCH, 1000));
        selfFlagellation = new FocusUpgradeType(TCConfig.selfFlagellationUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/self_flagellation.png"), "focus.upgrade.selfFlagellation.name", "focus.upgrade.selfFlagellation.text", new AspectList().add(Aspect.MAN, 500).add(Aspect.TAINT, 500).add(Aspect.HEAL, 1000));
        burnout = new FocusUpgradeType(TCConfig.burnoutUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/burnout.png"), "focus.upgrade.burnout.name", "focus.upgrade.burnout.text", new AspectList().add(Aspect.MIND, 500).add(Aspect.FIRE, 500).add(Aspect.ENTROPY, 1000));
        primalEssence = new FocusUpgradeType(TCConfig.primalEssenceUpgradeID, new ResourceLocation(ThaumicConcilium.MODID+":textures/misc/primal_essence.png"), "focus.upgrade.primalEssence.name", "focus.upgrade.primalEssence.text", new AspectList().add(Aspect.CRAFT, 500).add(Aspect.FIRE, 500).add(Aspect.AURA, 1000));

    }
}
