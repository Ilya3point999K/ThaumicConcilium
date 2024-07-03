package thaumcraft.api;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IArchitect {

	/**
	 * Returns a list of blocks that should be highlighted in world.
	 */
    ArrayList<BlockCoordinates> getArchitectBlocks(ItemStack stack, World world,
			int x, int y, int z, int side, EntityPlayer player);
	
	/**
	 * which axis should be displayed. 
	 */
    boolean showAxis(ItemStack stack, World world, EntityPlayer player, int side, EnumAxis axis);
	
	enum EnumAxis {
		X, // east / west
		Y, // up / down
		Z // north / south
	}
}
