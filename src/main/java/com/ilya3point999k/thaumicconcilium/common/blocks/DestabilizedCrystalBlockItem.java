package com.ilya3point999k.thaumicconcilium.common.blocks;

import com.ilya3point999k.thaumicconcilium.common.tiles.DestabilizedCrystalTile;
import com.ilya3point999k.thaumicconcilium.common.tiles.VisCondenserTile;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DestabilizedCrystalBlockItem extends ItemBlock {
    public DestabilizedCrystalBlockItem(Block par1) {
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
                DestabilizedCrystalTile ts = (DestabilizedCrystalTile) world.getTileEntity(x, y, z);
                ts.orientation = (short)side;
                if (world.getTileEntity(x, y - 1, z) instanceof VisCondenserTile){
                    ts.draining = true;
                }
            } catch (Exception var14) {
            }
        }
        return placed;
    }
}
