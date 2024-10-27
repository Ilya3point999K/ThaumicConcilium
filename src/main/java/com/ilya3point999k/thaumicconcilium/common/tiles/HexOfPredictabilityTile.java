package com.ilya3point999k.thaumicconcilium.common.tiles;

import com.ilya3point999k.thaumicconcilium.api.ChainedRiftRecipe;
import com.ilya3point999k.thaumicconcilium.api.ThaumicConciliumApi;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.blocks.HexOfPredictabilityBlock;
import com.ilya3point999k.thaumicconcilium.common.entities.RiftEntity;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.container.InventoryFake;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.util.List;

public class HexOfPredictabilityTile extends TileEntity implements IAspectContainer {

    public boolean isMaster;
    public boolean isSlave;

    public boolean hasRift;
    public AspectList essentia;

    public int heat;

    public HexOfPredictabilityTile() {
        isMaster = false;
        isSlave = false;
        hasRift = false;
        heat = 0;
        essentia = new AspectList();
    }

    public boolean canUpdate() {
        return !isSlave;
    }

    public void updateEntity() {

        if (!this.worldObj.isRemote) {
            if (HexOfPredictabilityBlock.checkTiles(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
                if (!this.isMaster) {
                    this.isMaster = true;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            } else {
                if (this.isMaster) {
                    this.isMaster = false;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }

            if (isMaster) {
                if (worldObj.getTotalWorldTime() % 60 == 0) {
                    List<RiftEntity> rifts = worldObj.getEntitiesWithinAABB(RiftEntity.class, AxisAlignedBB.getBoundingBox(xCoord - 1.0, yCoord, zCoord - 1.0, xCoord + 1.0, yCoord + 4.0, zCoord + 1.0));
                    if (!rifts.isEmpty() && !hasRift) {
                        hasRift = true;
                        this.markDirty();
                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    } else if (rifts.isEmpty() && hasRift) {
                        hasRift = false;
                        essentia.aspects.clear();
                        this.markDirty();
                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    }
                }
                if (hasRift) {
                    if (heat >= 2400) {
                        List<RiftEntity> rifts = worldObj.getEntitiesWithinAABB(RiftEntity.class, AxisAlignedBB.getBoundingBox(xCoord - 1.0, yCoord, zCoord - 1.0, xCoord + 1.0, yCoord + 4.0, zCoord + 1.0));
                        if (!rifts.isEmpty()) {
                            rifts.get(0).setCollapsing(0);
                            hasRift = false;
                            heat = 0;
                            essentia.aspects.clear();
                            this.markDirty();
                            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                        }
                    }
                    List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - 2.0, yCoord, zCoord - 2.0, xCoord + 2.0, yCoord + 4.0, zCoord + 2.0));
                    List<EntityItem> near = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - 8.0, yCoord - 3.0, zCoord - 8.0, xCoord + 8.0, yCoord + 3.0, zCoord + 8.0));
                    if (essentia.size() != 0 && !near.isEmpty()) {
                        for (EntityItem e : near) {
                            ItemStack es = e.getEntityItem();
                            if (es != null && es.getItem() == TCItemRegistry.resource && es.getItemDamage() == 2) {
                                heat += 2;
                                this.markDirty();
                                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                            }
                        }
                    }
                    if (list.size() == 1) {
                        EntityItem item = list.get(0);
                        if (!item.isDead) {
                            if (heat > 0) {
                                if (worldObj.getTotalWorldTime() % 60 == 0 && worldObj.rand.nextInt(10) >= 8) {
                                    int rand = 3 + worldObj.rand.nextInt(3);
                                    for (int i = 0; i < rand; i++) {
                                        EntityItem slag = new EntityItem(this.worldObj, xCoord + (-0.5 + worldObj.rand.nextDouble()) * 2.0, yCoord + 2, zCoord + (-0.5 + worldObj.rand.nextDouble()) * 2.0, new ItemStack(TCItemRegistry.resource, 1, 2));
                                        this.worldObj.spawnEntityInWorld(slag);
                                        MiscHelper.setEntityMotionFromVector(slag, new Vector3(slag.posX + (-0.5 + worldObj.rand.nextDouble()) * 2.0, slag.posY + 1.0, slag.posZ + (-0.5 + worldObj.rand.nextDouble()) * 2.0), 0.2F);
                                    }
                                }
                                heat -= 3;
                                this.markDirty();
                                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                            } else {
                                NBTTagCompound itemData = item.getEntityData();
                                String thrower = itemData.getString("thrower");
                                if (thrower != null) {
                                    ChainedRiftRecipe recipe = ThaumicConciliumApi.findMatchingRiftRecipe(thrower, essentia, item.getEntityItem());
                                    if (recipe != null) {
                                        item.setDead();
                                        ItemStack resultStack = recipe.getRecipeOutput().copy();
                                        EntityItem result = new EntityItem(this.worldObj, item.posX, item.posY, item.posZ, resultStack);
                                        this.worldObj.spawnEntityInWorld(result);

                                        EntityPlayer p = this.worldObj.getPlayerEntityByName(thrower);
                                        if (p != null) {
                                            FMLCommonHandler.instance().firePlayerCraftingEvent(p, resultStack, new InventoryFake(new ItemStack[]{item.getEntityItem()}));
                                        }

                                        for (int a = 0; a < Thaumcraft.proxy.particleCount(10); ++a) {
                                            ThaumicConcilium.proxy.sparkles(this.worldObj, (int) result.posX, (int) (result.posY), (int) result.posZ);
                                        }
                                        MiscHelper.setEntityMotionFromVector(result, new Vector3(result.posX + (-0.5 + worldObj.rand.nextDouble()) * 5.0, result.posY + 1.0, result.posZ + (-0.5 + worldObj.rand.nextDouble()) * 5.0), 0.4F);
                                        essentia.aspects.clear();
                                        this.markDirty();
                                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        super.updateEntity();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        isMaster = nbttagcompound.getBoolean("isMaster");
        isSlave = nbttagcompound.getBoolean("IsSlave");
        hasRift = nbttagcompound.getBoolean("hasRift");
        essentia.readFromNBT((NBTTagCompound) nbttagcompound.getTag("essentia"));
        heat = nbttagcompound.getInteger("heat");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("isMaster", isMaster);
        nbttagcompound.setBoolean("isSlave", isSlave);
        nbttagcompound.setBoolean("hasRift", hasRift);
        NBTTagCompound compound1 = new NBTTagCompound();
        essentia.writeToNBT(compound1);
        nbttagcompound.setTag("essentia", compound1);
        nbttagcompound.setInteger("heat", heat);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord - 2.0, this.yCoord - -2.0, this.zCoord - 2.0, this.xCoord + 2.0, this.yCoord + 1.0, this.zCoord + 2.0);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (worldObj.blockExists(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e())) {
            TileEntity tile = worldObj.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
            if (tile instanceof HexOfPredictabilityTile) {
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
        return essentia;
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