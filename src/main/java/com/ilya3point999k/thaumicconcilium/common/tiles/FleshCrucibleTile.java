package com.ilya3point999k.thaumicconcilium.common.tiles;

import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.MadThaumaturge;
import com.ilya3point999k.thaumicconcilium.common.registry.TCBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;

public class FleshCrucibleTile extends TileEntity implements IAspectContainer {
    public AspectList aspects = new AspectList();
    int bellows = -1;
    int cooldown;

    public FleshCrucibleTile() {
        cooldown = 0;
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

        if (this.worldObj.isRemote) {
            if (this.aspects.getAmount(Aspect.FLESH) > 0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                for (int i = 0; i < 5; i++) {
                    int x = 5 + this.worldObj.rand.nextInt(22);
                    int y = 5 + this.worldObj.rand.nextInt(22);
                    Thaumcraft.proxy.crucibleBubble(this.worldObj, (float) this.xCoord + (float) x / 32.0F, (float) this.yCoord + 0.3F + (aspects.getAmount(Aspect.FLESH) / 100.0F), (float) this.zCoord + (float) y / 32.0F, 0.93F, 0.28F, 0.55F);
                }
            }
        }
        /*
        if (this.worldObj.isRemote) {
            if (this.aspects.getAmount(Aspect.EXCHANGE) > 0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                for (int i = 0; i < 5; i++) {
                    int x = 5 + this.worldObj.rand.nextInt(22);
                    int y = 5 + this.worldObj.rand.nextInt(22);
                    Thaumcraft.proxy.crucibleBubble(this.worldObj, (float) this.xCoord + (float) x / 32.0F, (float) this.yCoord + 0.3F + (aspects.getAmount(Aspect.EXCHANGE) / 100.0F), (float) this.zCoord + (float) y / 32.0F, 0.6F, 0.6F, 0.6F);
                }
            }
        }

         */
    }

    public void attemptSip(EntityPlayer player){
        TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
        if (capabilities != null && this.aspects.getAmount(Aspect.FLESH) > 0){
            this.aspects.remove(Aspect.FLESH, 1);
            this.markDirty();
            player.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            capabilities.fleshAmount = MathHelper.clamp_int(capabilities.fleshAmount - 1, 0, Integer.MAX_VALUE);
            capabilities.sync();
            this.worldObj.playSoundAtEntity(player, "thaumcraft:gore", 0.3F, 1.0F + this.worldObj.rand.nextFloat() * 0.4F);
        }
    }

    public void attemptSmelt(MadThaumaturge entity) {
        this.worldObj.playSoundAtEntity(entity, "thaumcraft:bubble", 0.2F, 1.0F + this.worldObj.rand.nextFloat() * 0.4F);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, TCBlockRegistry.QUICKSILVER_CRUCIBLE, 2, 1);
        entity.setDead();
        if (this.aspects.getAmount(Aspect.FLESH) < 64) {
            this.aspects.add(Aspect.FLESH, 2);
        } else {
            this.aspects.remove(Aspect.FLESH);
            this.aspects.add(Aspect.FLESH, 64);
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

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (worldObj.blockExists(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e())) {
            TileEntity tile = worldObj.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
            if (tile instanceof FleshCrucibleTile) {
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
