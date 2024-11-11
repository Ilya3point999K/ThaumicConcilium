package com.ilya3point999k.thaumicconcilium.common.integration.minetweaker;

import minetweaker.MineTweakerAPI;

public class MineTweakerIntegration {

    public static void register() {
        MineTweakerAPI.registerClass(MTChainedRiftRecipe.class);
        MineTweakerAPI.registerClass(MTResearchPageTweaker.class);
        MineTweakerAPI.registerClass(MTPolishmentRecipe.class);
    }
}
