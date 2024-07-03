package com.ilya3point999k.thaumicconcilium.common.tiles;

import com.ilya3point999k.thaumicconcilium.common.network.TCPacketHandler;
import com.ilya3point999k.thaumicconcilium.common.network.packets.PacketFXLightning;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemWispEssence;
import thaumcraft.common.lib.research.ScanManager;

import java.util.List;

public class VisCondenserTile extends TileEntity {
    public int cooldown;

    public VisCondenserTile() {
        cooldown = 0;
    }

    public boolean canUpdate() {
        return true;
    }

    public void updateEntity() {
        if (cooldown > 0) {
            --cooldown;
        }
        if (worldObj.rand.nextInt(20) == 0) {
            TileEntity tileEntity = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
            if (tileEntity instanceof DestabilizedCrystalTile) {
                if (!worldObj.isRemote) {
                    worldObj.playSoundEffect(xCoord, yCoord + 1, zCoord, "thaumcraft:jacobs", 0.01F, 1F);
                }
            }
        }
        super.updateEntity();
    }

    public void sendEssentia(DestabilizedCrystalTile crystal) {
        for (int x = -8; x <= 8; ++x) {
            for (int y = -8; y <= 8; ++y) {
                for (int z = -8; z <= 8; ++z) {
                    TileEntity tile = worldObj.getTileEntity(crystal.xCoord + x, crystal.yCoord + y, crystal.zCoord + z);
                    if (tile instanceof HexOfPredictabilityTile) {
                        if (((HexOfPredictabilityTile) tile).isMaster && ((HexOfPredictabilityTile) tile).hasRift) {

                            int c;
                            if (crystal.aspect != null) {
                                c = Aspect.aspects.get(crystal.aspect).getColor();
                            } else {
                                c = 0xFFFFFF;
                            }
                            if (!worldObj.isRemote) {
                                ((HexOfPredictabilityTile) tile).essentia.add(Aspect.getAspect(crystal.aspect), crystal.capacity);
                                ((HexOfPredictabilityTile) tile).heat = 1200;
                                crystal.capacity = 0;
                                crystal.markDirty();
                                worldObj.markBlockForUpdate(crystal.xCoord, crystal.yCoord, crystal.zCoord);
                                tile.markDirty();
                                worldObj.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
                                worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "thaumcraft:shock", 0.8F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
                                TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning(this.xCoord + 0.5F, this.yCoord + 1.5F, this.zCoord + 0.5F, tile.xCoord + 0.5F, tile.yCoord + 1.5F, tile.zCoord + 0.5F, c, 0.01F), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32.0));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void shoot(DestabilizedCrystalTile crystal, float x, float y, float z, int power) {
        if (cooldown != 0) return;
        if (crystal.capacity == 0) return;
        int blastPower = Math.min(crystal.capacity, 16);

        int c;
        if (crystal.aspect != null) {
            c = Aspect.aspects.get(crystal.aspect).getColor();
        } else {
            c = 0xFFFFFF;
        }
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(Math.min(crystal.xCoord - 0.6, x - 0.6), crystal.yCoord - 0.3, Math.min(crystal.zCoord - 0.6, z - 0.6), Math.max(x + 0.6, crystal.xCoord), y + 0.3, Math.max(z + 0.6, crystal.zCoord + 0.6));
        if (!this.worldObj.isRemote) {
            crystal.capacity -= blastPower;
            crystal.markDirty();
            this.worldObj.markBlockForUpdate(crystal.xCoord, crystal.yCoord, crystal.zCoord);
            List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, box);
            for (EntityLivingBase e : list) {
                e.attackEntityFrom(DamageSource.magic, MathHelper.clamp_int(blastPower + 1 - power, 0, 16));
                if (e.getHealth() <= 0) {
                    ItemWispEssence itemEssence = (ItemWispEssence) ConfigItems.itemWispEssence;
                    ItemStack wispyEssence = new ItemStack(itemEssence, 1, 0);
                    AspectList aspectsCompound = ScanManager.generateEntityAspects(e);
                    itemEssence.setAspects(wispyEssence, new AspectList().add(aspectsCompound.getAspects()[e.worldObj.rand.nextInt(aspectsCompound.size())], 2));
                    e.entityDropItem(wispyEssence, 0.2f);
                }
            }
            worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "thaumcraft:shock", 0.8F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            TCPacketHandler.INSTANCE.sendToAllAround(new PacketFXLightning(this.xCoord + 0.5F, this.yCoord + 1.5F, this.zCoord + 0.5F, x + 0.5F, y + 0.5F, z + 0.5F, c, 0.01F), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, x, y, z, 32.0));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        cooldown = nbttagcompound.getInteger("cooldown");
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("cooldown", cooldown);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord - 1.0, this.zCoord, this.xCoord + 1.0, this.yCoord + 2.0, this.zCoord + 1.0);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (worldObj.blockExists(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e())) {
            TileEntity tile = worldObj.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
            if (tile instanceof VisCondenserTile) {
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
}