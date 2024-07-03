package thaumcraft.api.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public interface IInternalMethodHandler {

	void generateVisEffect(int dim, int x, int y, int z, int x2, int y2, int z2, int color);
	boolean isResearchComplete(String username, String researchkey);
	ItemStack getStackInRowAndColumn(Object instance, int row, int column);
	AspectList getObjectAspects(ItemStack is);
	AspectList getBonusObjectTags(ItemStack is,AspectList ot);
	AspectList generateTags(Item item, int meta);
	boolean consumeVisFromWand(ItemStack wand, EntityPlayer player, AspectList cost, boolean doit, boolean crafting);
	boolean consumeVisFromWandCrafting(ItemStack wand,EntityPlayer player, AspectList cost, boolean doit);
	boolean consumeVisFromInventory(EntityPlayer player, AspectList cost);
	void addWarpToPlayer(EntityPlayer player, int amount,boolean temporary);
	void addStickyWarpToPlayer(EntityPlayer player, int amount);
	boolean hasDiscoveredAspect(String username, Aspect aspect);
	AspectList getDiscoveredAspects(String username);
	
}
