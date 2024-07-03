package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.tiles.LithographerTile;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LithographerBlockItem extends ItemBlock {

    public LithographerBlockItem(Block par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    public int getMetadata(int par1) {
        return par1;
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName();
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean placed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (placed) {
            try {
                LithographerTile ts = (LithographerTile) world.getTileEntity(x, y, z);
                int p = MathHelper.floor_double((player.rotationYaw * 4F) / 360F + 0.5D) & 3;
                if (p == 0 || p == 2){
                    ts.orientation = true;
                } else {
                    ts.orientation = false;
                }
            } catch (Exception var14) {
            }
        }
        return placed;
    }

}