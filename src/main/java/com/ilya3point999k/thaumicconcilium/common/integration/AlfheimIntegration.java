package com.ilya3point999k.thaumicconcilium.common.integration;

import alfheim.common.core.handler.AlfheimConfigHandler;
import thaumcraft.api.aspects.Aspect;

public class AlfheimIntegration {

    public static void register() {
        Integration.alfheimDimension = AlfheimConfigHandler.INSTANCE.getDimensionIDAlfheim();
        Integration.tincturaAllowed = AlfheimConfigHandler.INSTANCE.getAddTincturaAspect();
        if (Integration.tincturaAllowed){
            Integration.tinctura = Aspect.getAspect("tinctura");
        }
    }
}
