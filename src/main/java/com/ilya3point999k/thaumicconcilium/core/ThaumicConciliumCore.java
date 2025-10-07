package com.ilya3point999k.thaumicconcilium.core;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@IFMLLoadingPlugin.Name(value = "ThaumicConciliumCore")
@IFMLLoadingPlugin.TransformerExclusions({"com.ilya3point999k.thaumicconcilium.core"})
@IFMLLoadingPlugin.SortingIndex(value = 1001)
public class ThaumicConciliumCore implements IFMLLoadingPlugin {

    //-Dfml.coreMods.load=com.ilya3point999k.thaumicconcilium.core.ThaumicConciliumCore

    public static final Logger LOG = LogManager.getLogger("ThaumicConciliumCore");

    public static boolean isDevEnvironment;

    public static Side SIDE = FMLLaunchHandler.side();

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{ThaumicConciliumMainTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isDevEnvironment = !(boolean)data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
