package com.ilya3point999k.thaumicconcilium.common.events;

import com.ilya3point999k.thaumicconcilium.common.blocks.HexOfPredictabilityBlock;
import com.ilya3point999k.thaumicconcilium.common.entities.MaterialPeeler;
import com.ilya3point999k.thaumicconcilium.common.entities.RiftEntity;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import com.ilya3point999k.thaumicconcilium.common.tiles.HexOfPredictabilityTile;
import com.ilya3point999k.thaumicconcilium.common.tiles.LithographerTile;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;
import thaumcraft.common.tiles.TileJarNode;

import java.util.List;

public class WandHandler {
    public static void handleWandInteract(World world, int x, int y, int z, EntityPlayer player, ItemStack i) {
        ItemWandCasting wand = (ItemWandCasting) i.getItem();
        if (!world.isRemote){
            if (world.getBlock(x, y, z) == Blocks.bedrock){
                if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "HEXOFPREDICTABILITY") || !HexOfPredictabilityBlock.checkStructure(world, x, y, z) || !wand.consumeAllVisCrafting(i, player, (new AspectList()).add(Aspect.ORDER, 100).add(Aspect.EARTH, 100), true)) return;
                for (int xx = -1; xx <= 1; ++xx) {
                    for (int zz = -1; zz <= 1; ++zz) {
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x + xx, y, z + zz, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double)x + xx, (double)y, (double)z + zz, 32.0));
                        if (xx == 0 && zz == 0){
                            HexOfPredictabilityTile tile = new HexOfPredictabilityTile();
                            tile.isMaster = true;
                            world.setTileEntity(x + xx, y, z + zz, tile);
                            world.markBlockForUpdate(x + xx, y, z + zz);
                            tile.markDirty();
                        }
                        world.setBlock(x + xx, y, z + zz, TCBlockRegistry.HEX_OF_PREDICTABILITY_BLOCK);
                    }
                }
                world.playSoundEffect(x + 0.5, y+0.5, z+0.5, "thaumcraft:wand", 1.0F, 1.0F);
            }
            if (world.getTileEntity(x, y, z) instanceof TileJarNode){
                TileEntity tile = world.getTileEntity(x, y - 1, z);
                if (tile instanceof HexOfPredictabilityTile && ((HexOfPredictabilityTile) tile).isMaster && !((HexOfPredictabilityTile) tile).hasRift) {
                    List<RiftEntity> peelers = world.getEntitiesWithinAABB(MaterialPeeler.class, AxisAlignedBB.getBoundingBox(tile.xCoord - 1.0, tile.yCoord, tile.zCoord - 1.0, tile.xCoord + 1.0, tile.yCoord + 4.0, tile.zCoord + 1.0));
                    if (peelers.isEmpty()) {
                        world.setBlockToAir(x, y, z);
                        world.removeTileEntity(x, y, z);
                        world.markBlockForUpdate(x, y, z);
                        RiftEntity.constructRift(world, x, y + 1, z, 25);
                    }
                }
            }
            if (world.getBlock(x, y, z) == ConfigBlocks.blockMetalDevice && world.getBlockMetadata(x, y, z) == 3) {
                if (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "LITHOGRAPHER")) {
                    if (
                            world.getBlock(x, y - 1, z) == ConfigBlocks.blockCosmeticSolid && world.getBlockMetadata(x, y - 1, z) == 4
                                    && world.getBlock(x, y - 2, z) == Blocks.bed && BlockBed.isBlockHeadOfBed(world.getBlockMetadata(x, y - 2, z))
                                    && world.getBlock(x + 1, y - 1, z) == Blocks.gold_block
                                    && world.getBlock(x + 1, y - 2, z) == Blocks.gold_block
                                    && world.getBlock(x - 1, y - 1, z) == Blocks.gold_block
                                    && world.getBlock(x - 1, y - 2, z) == Blocks.gold_block
                                    && wand.consumeAllVisCrafting(i, player, (new AspectList()).add(Aspect.FIRE, 100).add(Aspect.ORDER, 100).add(Aspect.WATER, 50).add(Aspect.AIR, 100), true)
                    ) {
                        world.setBlockToAir(x, y, z);
                        world.setBlockToAir(x, y - 1, z);
                        world.setBlockToAir(x + 1, y - 1, z);
                        world.setBlockToAir(x + 1, y - 2, z);
                        world.setBlockToAir(x - 1, y - 1, z);
                        world.setBlockToAir(x - 1, y - 2, z);
                        world.setBlock(x, y - 1, z, TCBlockRegistry.LITHOGRAPHER_BLOCK);
                        LithographerTile tile = new LithographerTile();
                        world.setTileEntity(x, y - 1, z, tile);
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y - 1, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x + 1, y - 1, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x + 1, y - 2, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x - 1, y - 1, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x - 1, y - 2, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));

                        world.playSoundEffect((double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "thaumcraft:wand", 1.0F, 1.0F);

                    } else if (
                            world.getBlock(x, y - 1, z) == ConfigBlocks.blockCosmeticSolid && world.getBlockMetadata(x, y - 1, z) == 4
                                    && world.getBlock(x, y - 2, z) == Blocks.bed && BlockBed.isBlockHeadOfBed(world.getBlockMetadata(x, y - 2, z))
                                    && world.getBlock(x, y - 1, z + 1) == Blocks.gold_block
                                    && world.getBlock(x, y - 2, z + 1) == Blocks.gold_block
                                    && world.getBlock(x, y - 1, z - 1) == Blocks.gold_block
                                    && world.getBlock(x, y - 2, z - 1) == Blocks.gold_block
                                    && wand.consumeAllVisCrafting(i, player, (new AspectList()).add(Aspect.FIRE, 100).add(Aspect.ORDER, 100).add(Aspect.WATER, 50).add(Aspect.AIR, 100), true)
                    ) {
                        world.setBlockToAir(x, y, z);
                        world.setBlockToAir(x, y - 1, z);
                        world.setBlockToAir(x, y - 1, z + 1);
                        world.setBlockToAir(x, y - 2, z + 1);
                        world.setBlockToAir(x, y - 1, z - 1);
                        world.setBlockToAir(x, y - 2, z - 1);
                        world.setBlock(x, y - 1, z, TCBlockRegistry.LITHOGRAPHER_BLOCK);
                        LithographerTile tile = new LithographerTile();
                        world.setTileEntity(x, y - 1, z, tile);
                        LithographerTile t = (LithographerTile) world.getTileEntity(x, y - 1, z);
                        t.orientation = false;
                        t.markDirty();
                        world.markBlockForUpdate(x, y - 1, z);
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y - 1, z, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y - 1, z + 1, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y - 2, z + 1, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y - 1, z - 1, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(x, y - 2, z - 1, -9999), new NetworkRegistry.TargetPoint(world.provider.dimensionId, (double) x, (double) y, (double) z, 32.0));

                        world.playSoundEffect((double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "thaumcraft:wand", 1.0F, 1.0F);

                    }
                }
            }
        }
    }
}
