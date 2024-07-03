package com.ilya3point999k.thaumicconcilium.common.tiles;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.tiles.TileCrucible;

public class QuicksilverCrucibleTile extends TileEntity implements IAspectContainer {
    public AspectList aspects = new AspectList();
    int bellows = -1;
    int cooldown;

    public QuicksilverCrucibleTile() {
        cooldown = 0;
    }

    public QuicksilverCrucibleTile(boolean created){
        super();
        if (created){
            aspects.add(Aspect.EXCHANGE, 20);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        this.aspects.readFromNBT(nbttagcompound);
        cooldown = nbttagcompound.getInteger("cooldown");
        super.readFromNBT(nbttagcompound);

    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        this.aspects.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("cooldown", cooldown);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public void updateEntity() {

        if (cooldown > 0) {
            cooldown--;
        }

        if (cooldown == 0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
            sendQuicksilver();
        }
        if (this.worldObj.isRemote) {
            if (this.aspects.getAmount(Aspect.EXCHANGE) > 0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                for (int i = 0; i < 5; i++) {
                    int x = 5 + this.worldObj.rand.nextInt(22);
                    int y = 5 + this.worldObj.rand.nextInt(22);
                    Thaumcraft.proxy.crucibleBubble(this.worldObj, (float) this.xCoord + (float) x / 32.0F, (float) this.yCoord + 0.3F + (aspects.getAmount(Aspect.EXCHANGE) / 100.0F), (float) this.zCoord + (float) y / 32.0F, 0.6F, 0.6F, 0.6F);
                }
            }
        }
    }

    public void sendQuicksilver() {
        if (this.aspects.getAmount(Aspect.EXCHANGE) > 0) {
            for (int x = -8; x <= 8; ++x) {
                for (int y = -8; y <= 8; ++y) {
                    for (int z = -8; z <= 8; ++z) {
                        TileEntity tile = worldObj.getTileEntity(this.xCoord + x, this.yCoord + y, this.zCoord + z);
                        if (tile instanceof TileCrucible) {
                            if (((TileCrucible) tile).aspects.size() >= 2) {
                                Aspect[] aspects = ((TileCrucible) tile).aspects.getAspects();
                                Aspect combo = ResearchManager.getCombinationResult(aspects[aspects.length - 1], aspects[aspects.length - 2]);
                                if (combo != null) {
                                    if (!worldObj.isRemote) {
                                        AspectList list = new AspectList();
                                        for (int i = 0; i < aspects.length - 2; i++) {
                                            list.add(aspects[i], ((TileCrucible) tile).aspects.getAmount(aspects[i]));
                                        }
                                        list.add(combo, 1);
                                        if (((TileCrucible) tile).aspects.getAmount(aspects[aspects.length - 2]) > 1) {
                                            list.add(aspects[aspects.length - 2], ((TileCrucible) tile).aspects.getAmount(aspects[aspects.length - 2]) - 1);
                                        }
                                        if (((TileCrucible) tile).aspects.getAmount(aspects[aspects.length - 1]) > 1) {
                                            list.add(aspects[aspects.length - 1], ((TileCrucible) tile).aspects.getAmount(aspects[aspects.length - 1]) - 1);
                                        }
                                        ((TileCrucible) tile).aspects.aspects.clear();
                                        ((TileCrucible) tile).aspects.merge(list);
                                        this.aspects.remove(Aspect.EXCHANGE, 1);
                                        this.cooldown = 20;
                                        this.markDirty();
                                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                                        tile.markDirty();
                                        worldObj.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
                                    } else {
                                        ThaumicConcilium.proxy.quicksilverFlow(worldObj, tile.xCoord, tile.yCoord + 1.0, tile.zCoord, xCoord, yCoord + 1.0, zCoord);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        this.cooldown = 20;
        this.markDirty();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }


    public void attemptSmelt(EntityItem entity) {
        boolean bubble = false;
        ItemStack item = entity.getEntityItem();
        int stacksize = item.stackSize;

        for (int a = 0; a < stacksize; ++a) {
            if (item.getItem() == ConfigItems.itemResource && item.getItemDamage() == 3) {
                this.aspects.add(Aspect.EXCHANGE, 2);
                stacksize--;
                bubble = true;
                if (this.aspects.getAmount(Aspect.EXCHANGE) > 64) {
                    this.aspects.remove(Aspect.EXCHANGE);
                    this.aspects.add(Aspect.EXCHANGE, 64);
                }
            }
        }

        if (bubble) {
            this.worldObj.playSoundAtEntity(entity, "thaumcraft:bubble", 0.2F, 1.0F + this.worldObj.rand.nextFloat() * 0.4F);
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, TCBlockRegistry.QUICKSILVER_CRUCIBLE, 2, 1);
        }

        if (stacksize <= 0) {
            entity.setDead();
        } else {
            item.stackSize = stacksize;
            entity.setEntityItemStack(item);
        }

        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }


    public void getBellows() {
        this.bellows = 0;

        for (int a = 2; a < 6; ++a) {
            ForgeDirection dir = ForgeDirection.getOrientation(a);
            int xx = this.xCoord + dir.offsetX;
            int zz = this.zCoord + dir.offsetZ;
            Block bi = this.worldObj.getBlock(xx, this.yCoord, zz);
            int md = this.worldObj.getBlockMetadata(xx, this.yCoord, zz);
            if (bi == ConfigBlocks.blockWoodenDevice && md == 0) {
                ++this.bellows;
            }
        }

    }

    private void spill() {

    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (worldObj.blockExists(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e())) {
            TileEntity tile = worldObj.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
            if (tile instanceof QuicksilverCrucibleTile) {
                tile.readFromNBT(pkt.func_148857_g());
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
    }


    @Override
    public AspectList getAspects() {
        if (this.aspects == null) {
            return null;
        }
        return aspects;
    }

    @Override
    public void setAspects(AspectList aspects) {

    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return false;
    }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        return false;
    }

    @Override
    public int containerContains(Aspect tag) {
        return 0;
    }
}
