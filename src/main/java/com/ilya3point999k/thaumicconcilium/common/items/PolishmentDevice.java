package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.api.ThaumicConciliumApi;
import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.events.TCEntityEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileInfusionMatrix;
import thaumcraft.common.tiles.TileNodeEnergized;
import thaumcraft.common.tiles.TilePedestal;

import java.util.ArrayList;

public class PolishmentDevice extends Item {

    public PolishmentDevice() {
        this.setTextureName(ThaumicConcilium.MODID+":icon");
        this.setUnlocalizedName("PolishmentDevice");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
            capabilities.ethereal = !capabilities.ethereal;
            capabilities.sync();
        }
        return stack;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int targetX, int targetY, int targetZ, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
            capabilities.ethereal = !capabilities.ethereal;
            capabilities.sync();
            TCEntityEventHandler.ethereals.add(player.getCommandSenderName());
            if (world.getTileEntity(targetX, targetY, targetZ) instanceof TileInfusionMatrix) {
                ArrayList<ChunkCoordinates> pedestals = new ArrayList();
                ArrayList<ItemStack> components = new ArrayList();

                for (int xx = -12; xx <= 12; xx++)
                    for (int zz = -12; zz <= 12; zz++) {
                        boolean skip = false;
                        for (int yy = -10; yy <= 5; yy++)
                            if (xx != 0 || zz != 0) {
                                int x = targetX + xx;
                                int y = targetY + yy;
                                int z = targetZ + zz;
                                TileEntity te = world.getTileEntity(x, y, z);
                                if (!skip && te != null && te instanceof TilePedestal) {
                                    pedestals.add(new ChunkCoordinates(x, y, z));
                                    skip = true;

                                }
                            }
                    }
                for (ChunkCoordinates cc : pedestals) {
                    TileEntity te = world.getTileEntity(cc.posX, cc.posY, cc.posZ);
                    if (te != null && te instanceof TilePedestal) {
                        if (((TilePedestal) te).getStackInSlot(0) != null) {
                            components.add(((TilePedestal) te).getStackInSlot(0));
                        }
                    }
                }

                ItemStack central = null;
                TileEntity te = world.getTileEntity(targetX, targetY - 2, targetZ);
                if (te instanceof TilePedestal)
                    if (((TilePedestal) te).getStackInSlot(0) != null)
                        central = ((TilePedestal) te).getStackInSlot(0).copy();
                if (central != null) {
                    InfusionRecipe infRecipe = ThaumcraftCraftingManager.findMatchingInfusionRecipe(components, central, player);
                    if (infRecipe != null) {
                        if (infRecipe.getRecipeOutput() instanceof ItemStack) {
                            AspectList require = ThaumicConciliumApi.getPolishmentRecipe((ItemStack) infRecipe.getRecipeOutput());
                            if (require != null) {
                                player.addChatMessage(new ChatComponentTranslation("TC.infusionInfo.require", require.getAspects()[0], require.getAmount(require.getAspects()[0])).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                            }
                        }
                    }
                }
                return true;
            }
            else if(world.getTileEntity(targetX, targetY, targetZ) instanceof TileNodeEnergized){
                TileNodeEnergized node = (TileNodeEnergized) world.getTileEntity(targetX, targetY, targetZ);
                node.setAspects(new AspectList().add(Aspect.AIR, 10000).add(Aspect.EARTH, 10000).add(Aspect.FIRE, 10000).add(Aspect.WATER, 10000)
                        .add(Aspect.ENTROPY, 10000).add(Aspect.ORDER, 10000));
                node.setupNode();
                world.markBlockForUpdate(targetX, targetY, targetZ);
                node.markDirty();

            } else {
                if (!world.isRemote) {
                    /*
                    EntityPlayerMP p = (EntityPlayerMP) player;
                    if (p.ridingEntity == null && p.riddenByEntity == null) {
                        MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
                        if (p.timeUntilPortal > 0) {
                            p.timeUntilPortal = 100;
                        } else if (p.dimension != TCConfig.causalBouillonID) {
                            p.timeUntilPortal = 100;
                            p.mcServer.getConfigurationManager().transferPlayerToDimension(p, TCConfig.causalBouillonID, new CausalBouillonTeleporter(mServer.worldServerForDimension(TCConfig.causalBouillonID)));
                        } else {
                            p.timeUntilPortal = 100;
                            p.mcServer.getConfigurationManager().transferPlayerToDimension(p, 0, new CausalBouillonTeleporter(mServer.worldServerForDimension(0)));
                        }
                    }

                     */
                    TileEntity tileEntity = player.worldObj.getTileEntity(targetX, targetY, targetZ);
                    if(tileEntity != null){
                        System.out.println(tileEntity.toString());
                    }
                    //RiftEntity.summonRift(player, new Vector3(targetX, targetY + 2.0, targetZ), false);
                }
            }
        }
        //ItemFocusPortableHole.createHole(world, targetX, targetY, targetZ, side, (byte) 34, 120);

        return false;
    }


}
