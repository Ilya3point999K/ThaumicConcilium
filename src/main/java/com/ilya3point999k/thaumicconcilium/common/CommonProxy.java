package com.ilya3point999k.thaumicconcilium.common;

import com.ilya3point999k.thaumicconcilium.common.entities.ContainerThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.Thaumaturge;
import com.ilya3point999k.thaumicconcilium.common.events.TCEntityEventHandler;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.containers.AstralMonitorContainer;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.TCFociUpgrades;
import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.registry.*;
import com.ilya3point999k.thaumicconcilium.common.containers.LithographerContainer;
import com.ilya3point999k.thaumicconcilium.common.tiles.LithographerTile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.common.entities.golems.ContainerGolem;
import thaumcraft.common.entities.golems.EntityGolemBase;

public class CommonProxy implements IGuiHandler {

	public void preInit(FMLPreInitializationEvent event) {
		Integration.init();
		TCEntityRegistry.init();
		TCConfig.configurate(event.getSuggestedConfigurationFile());
		TCPacketHandler.init();
	}

	public void init(FMLInitializationEvent event) {
		TCEntityEventHandler entityEventHandler = new TCEntityEventHandler();
		MinecraftForge.EVENT_BUS.register(entityEventHandler);
		FMLCommonHandler.instance().bus().register(entityEventHandler);
	}

	public void postInit(FMLPostInitializationEvent event) {
		TCEntityRegistry.postinit();
		TCBlockRegistry.init();
		Util.createDimension();
		TCTileRegistry.init();
		TCItemRegistry.init();
		Integration.postInit();
		TCFociUpgrades.init();
		TCRecipeRegistry.init();
		Thaumonomicon.setup();
	}

	public void registerKeyBindings() {
	}

	public void sendLocalMovementData(EntityLivingBase ent){

	}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID){
			case 0:{
				return new ContainerThaumaturge(player.inventory, world, (Thaumaturge) (world).getEntityByID(x));
			}
			case 1:
			case 2:
			case 3:{
				return new ContainerGolem(player.inventory, ((EntityGolemBase)world.getEntityByID(x)).inventory);
			}
			case 4:{
				return new AstralMonitorContainer(player.inventory, world);
			}
			case 5:{
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile instanceof LithographerTile) {
					return new LithographerContainer(player.inventory, (LithographerTile) tile);
				}
				break;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerRenderers() {

	}

	public void registerHandlers(){

	}

	public void crucibleBoil(World world, int x, int y, int z){

	}
	public void lightbeam(EntityPlayer player, double tx, double ty, double tz, float red, float green, float blue, int age) {}
	public void runeFlow(EntityPlayer player, Entity e){

	}
	public void chain(World world, double sx, double sy, double sz, double tx, double ty, double tz){

	}
	public void warpchain(EntityPlayer player, double tx, double ty, double tz) {
	}

	public void quicksilverFlow(World w, double x, double y, double z, double tx, double ty, double tz) {
	}

	public void lifedrain(Entity player, double tx, double ty, double tz) {
	}

	public void bloodinitiation(Entity player, Entity madman) {
	}

	public void taintsplosion(World world, double x, double y, double z){
	}
	public void bloodsplosion(World world, double x, double y, double z){
	}
	public void smeltFX(final double blockX, final double blockY, final double blockZ, final World w, final int howMany) {
	}
	public void visCapsuleFX(World world, double x, double y, double z){

	}
	public void sparkles(World world, double x, double y, double z){

	}
}
