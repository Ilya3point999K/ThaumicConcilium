package com.ilya3point999k.thaumicconcilium.common.integration;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;
import tb.api.ITobacco;

public class BasesIntegration {
    public static ITobacco tobacco = null;
    public static void register() {
        try {
            if (Class.forName("tb.utils.TBConfig").getField("allowTobacco").getBoolean(null)) {
                Integration.allowTobacco = true;
                Integration.tobaccoitem = GameRegistry.findItem("thaumicbases", "tobaccoPowder");
                if (Integration.tobaccoitem != null) {
                    tobacco = (ITobacco) Integration.tobaccoitem;
                } else {
                    ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Thaumic Bases's tobacco, what a mess");
                }
                Integration.tobaccoleaves = GameRegistry.findItem("thaumicbases", "resource");
                if (Integration.tobaccoleaves == null) {
                    ThaumicConcilium.logger.log(Level.ERROR, "Couldn't find Thaumic Bases's tobacco leaves, what a mess");
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
