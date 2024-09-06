package com.ilya3point999k.thaumicconcilium.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ThaumicConcilium.MODID, name = ThaumicConcilium.NAME, version = ThaumicConcilium.VERSION, dependencies = "after:Thaumcraft;after:ForbiddenMagic;after:ThaumicTinkerer;after:gadomancy;after:thaumicbases;after:Automagy;after:TaintedMagic;after:ThaumicHorizons;after:witchery")
public class ThaumicConcilium
{
    public static final String MODID = "ThaumicConcilium";
    public static final String NAME = "Thaumic Concilium";
    public static final String VERSION = "1.0.9";

    
    @Mod.Instance(value = ThaumicConcilium.MODID)
    public static ThaumicConcilium instance;
    
    @SidedProxy(clientSide = "com.ilya3point999k.thaumicconcilium.client.ClientProxy", serverSide = "com.ilya3point999k.thaumicconcilium.common.CommonProxy")
    public static CommonProxy proxy;
    
    public static CreativeTabs tabTC = new TCCreativeTab();

    public static Logger logger = LogManager.getLogger("Thaumic Concilium");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);
        proxy.registerKeyBindings();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }
}
