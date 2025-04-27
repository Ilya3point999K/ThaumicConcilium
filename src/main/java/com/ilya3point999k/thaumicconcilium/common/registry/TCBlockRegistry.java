package com.ilya3point999k.thaumicconcilium.common.registry;

import com.ilya3point999k.thaumicconcilium.common.blocks.*;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class TCBlockRegistry {
    public static int destabilizedCrystalBlockID = -1;
    public static int voidRealityBlockID = -1;
    public static int redPoweredMindBlockID = -1;
    public static int visCondenserBlockID = -1;
    public static int hexOfPredictabilityID = -1;
    public static int quicksilverCrucibleID = -1;
    public static int lithographerID = -1;
    public static int fleshCrucibleID = -1;
    public static int solidVoidID = -1;
    public static Block DESTABILIZED_CRYSTAL_BLOCK = (new DestabilizedCrystalBlock()).setBlockName("DestabilizedCrystalBlock");
    public static Block VOID_REALITY_BLOCK = new VoidRealityBlock().setBlockName("VoidRealityBlock");
    public static Block RED_POWERED_MIND_BLOCK = new RedPoweredMindBlock().setBlockName("RedPoweredMindBlock");
    public static Block VIS_CONDENSER_BLOCK = new VisCondenserBlock().setBlockName("VisCondenserBlock");
    public static Block HEX_OF_PREDICTABILITY_BLOCK = new HexOfPredictabilityBlock().setBlockName("HexOfPredictability");
    public static Block QUICKSILVER_CRUCIBLE = new QuicksilverCrucibleBlock().setBlockName("QuicksilverCrucible");
    public static Block LITHOGRAPHER_BLOCK = new LithographerBlock().setBlockName("Lithographer");
    public static Block FLESH_CRUCIBLE = new FleshCrucibleBlock().setBlockName("FleshCrucible");
    public static Block SOLID_VOID = new SolidVoidBlock().setBlockName("SolidVoid");

    public static void init() {
        GameRegistry.registerBlock(DESTABILIZED_CRYSTAL_BLOCK, DestabilizedCrystalBlockItem.class, "destabilizedCrystalBlock");
        ThaumcraftApi.registerObjectTag(new ItemStack(DESTABILIZED_CRYSTAL_BLOCK), new AspectList().add(Aspect.CRYSTAL, 1));
        if (Integration.gadomancy) {
            GameRegistry.registerBlock(VOID_REALITY_BLOCK, "voidRealityBlock");
            ThaumcraftApi.registerObjectTag(new ItemStack(VOID_REALITY_BLOCK), new AspectList().add(Aspect.VOID, 32));
        }
        GameRegistry.registerBlock(VIS_CONDENSER_BLOCK, VisCondenserBlockItem.class, "visCondenserBlock");
        GameRegistry.registerBlock(HEX_OF_PREDICTABILITY_BLOCK, "hexOfPredictability");
        ThaumcraftApi.registerObjectTag(new ItemStack(HEX_OF_PREDICTABILITY_BLOCK), new AspectList().add(Aspect.EARTH, 64).add(Aspect.ORDER, 128).add(Aspect.AURA, 64));
        GameRegistry.registerBlock(QUICKSILVER_CRUCIBLE, "QuicksilverCrucible");
        ThaumcraftApi.registerObjectTag(new ItemStack(QUICKSILVER_CRUCIBLE), new AspectList().add(Aspect.METAL, 64).add(Aspect.FLESH, 4).add(Aspect.MAGIC, 64).add(Aspect.FIRE, 16));
        GameRegistry.registerBlock(LITHOGRAPHER_BLOCK, LithographerBlockItem.class, "Lithographer");
        ThaumcraftApi.registerObjectTag(new ItemStack(LITHOGRAPHER_BLOCK), new AspectList().add(Aspect.METAL, 64).add(Aspect.SENSES, 32).add(Aspect.MAGIC, 64).add(Aspect.ENERGY, 16).add(Aspect.VOID, 32));
        if(Integration.taintedMagic) {
            GameRegistry.registerBlock(FLESH_CRUCIBLE, "FleshCrucible");
            GameRegistry.registerBlock(SOLID_VOID, "SolidVoid");
            ThaumcraftApi.registerObjectTag(new ItemStack(SOLID_VOID), new AspectList().add(Aspect.VOID, 2));
        }

        if (Integration.automagy && Integration.horizons) {
            GameRegistry.registerBlock(RED_POWERED_MIND_BLOCK, RedPoweredMindBlockItem.class, "redPoweredMindBlock");
            ThaumcraftApi.registerObjectTag(new ItemStack(RED_POWERED_MIND_BLOCK), new AspectList().add(Aspect.MIND, 64).add(Aspect.SENSES, 64).add(Aspect.UNDEAD, 32).add(Aspect.VOID, 32).add(Aspect.MECHANISM, 16));
        }
    }
}
