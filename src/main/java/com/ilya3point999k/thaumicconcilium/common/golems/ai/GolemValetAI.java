package com.ilya3point999k.thaumicconcilium.common.golems.ai;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.golems.GolemHelper;
import thaumcraft.common.entities.golems.Marker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class GolemValetAI extends EntityAIBase {
    private EntityGolemBase theGolem;
    private int xx;
    private int yy;
    private int zz;
    private float movementSpeed;
    private float distance;
    private World theWorld;
    private Block block;
    private int blockMd;
    FakePlayer player;
    private int count;
    private int color;
    ItemInWorldManager im;
    int nextTick;

    public GolemValetAI(EntityGolemBase par1EntityCreature) {
        this.block = Blocks.air;
        this.blockMd = 0;
        this.count = 0;
        this.color = -1;
        this.nextTick = 0;
        this.theGolem = par1EntityCreature;
        this.theWorld = par1EntityCreature.worldObj;
        this.setMutexBits(3);
        this.distance = (float) MathHelper.ceiling_float_int(this.theGolem.getRange() / 3.0F);
        if (this.theWorld instanceof WorldServer) {
            this.player = FakePlayerFactory.get((WorldServer)this.theWorld, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
        }

        try {
            this.nextTick = this.theGolem.ticksExisted + this.theWorld.rand.nextInt(6);
        } catch (Exception var3) {
        }

    }

    public boolean shouldExecute() {
        int d = 5 - this.theGolem.ticksExisted;
        if (d < 1) {
            d = 1;
        }

        if (this.theGolem.ticksExisted >= this.nextTick && this.theGolem.getNavigator().noPath()) {
            this.nextTick = this.theGolem.ticksExisted + d * 3;
            boolean flag = this.findSomething();
            if (!flag) this.theGolem.setToggle(5, false);
            return flag && this.theGolem.getToggles()[5];
        } else {
            return false;
        }
    }

    public boolean continueExecuting() {
        return this.theWorld.getBlock(this.xx, this.yy, this.zz) == this.block && this.theWorld.getBlockMetadata(this.xx, this.yy, this.zz) == this.blockMd && this.count-- > 0 && !this.theGolem.getNavigator().noPath();
    }

    public void updateTask() {
        this.theGolem.getLookHelper().setLookPosition((double)this.xx + 0.5, (double)this.yy + 0.5, (double)this.zz + 0.5, 30.0F, 30.0F);
        double dist = this.theGolem.getDistanceSq((double)this.xx + 0.5, (double)this.yy + 0.5, (double)this.zz + 0.5);
        if (dist <= 4.0) {
            this.click();
        }

    }

    public void resetTask() {
        this.count = 0;
        this.theGolem.getNavigator().clearPathEntity();
    }

    public void startExecuting() {
        this.count = 200;
        this.theGolem.getNavigator().tryMoveToXYZ((double)this.xx + 0.5, (double)this.yy + 0.5, (double)this.zz + 0.5, (double)this.theGolem.getAIMoveSpeed());
    }

    void click() {
        this.theGolem.setToggle(5, false);

        this.player.setPositionAndRotation(this.theGolem.posX, this.theGolem.posY, this.theGolem.posZ, this.theGolem.rotationYaw, this.theGolem.rotationPitch);
        this.player.setCurrentItemOrArmor(0, this.theGolem.itemCarried);
        this.player.setSneaking(this.theGolem.getToggles()[2]);
        Iterator i$ = GolemHelper.getMarkedSides(this.theGolem, this.xx, this.yy, this.zz, this.theGolem.worldObj.provider.dimensionId, (byte)this.color).iterator();
        if (i$.hasNext()) {
            Integer side = (Integer)i$.next();
            int x = 0;
            int y = 0;
            int z = 0;
            if (this.theGolem.worldObj.isAirBlock(this.xx, this.yy, this.zz)) {
                x = ForgeDirection.getOrientation(side).getOpposite().offsetX;
                y = ForgeDirection.getOrientation(side).getOpposite().offsetY;
                z = ForgeDirection.getOrientation(side).getOpposite().offsetZ;
            }

            if (this.im == null) {
                this.im = new ItemInWorldManager(this.theGolem.worldObj);
            }
                try {
                    if (this.theGolem.getToggles()[1]) {
                        this.theGolem.startLeftArmTimer();
                        this.im.onBlockClicked(this.xx + x, this.yy + y, this.zz + z, side);
                    } else if (this.im.activateBlockOrUseItem(this.player, this.theGolem.worldObj, this.theGolem.itemCarried, this.xx + x, this.yy + y, this.zz + z, side, 0.5F, 0.5F, 0.5F)) {
                        this.theGolem.startRightArmTimer();
                    }

                    this.theGolem.itemCarried = this.player.getCurrentEquippedItem();
                    if (this.theGolem.itemCarried.stackSize <= 0) {
                        this.theGolem.itemCarried = null;
                    }

                    for(int a = 1; a < this.player.inventory.mainInventory.length; ++a) {
                        if (this.player.inventory.getStackInSlot(a) != null) {
                            if (this.theGolem.itemCarried == null) {
                                this.theGolem.itemCarried = this.player.inventory.getStackInSlot(a).copy();
                            } else {
                                this.player.dropPlayerItemWithRandomChoice(this.player.inventory.getStackInSlot(a), false);
                            }

                            this.player.inventory.setInventorySlotContents(a, (ItemStack)null);
                        }
                    }

                    this.theGolem.updateCarried();
                    this.resetTask();
                } catch (Exception var14) {
                    this.resetTask();
                }

        }
    }

    public ArrayList<Byte> getColorsMatching(String s) {
        ArrayList<Byte> l = new ArrayList();
        if (theGolem.inventory.inventory != null && theGolem.inventory.inventory.length > 0) {
            int a;
            for(a = 0; a < theGolem.inventory.inventory.length; ++a) {
                if (theGolem.inventory.getStackInSlot(a) != null && theGolem.inventory.getStackInSlot(a).getItem() == Items.paper) {
                    if (theGolem.inventory.getStackInSlot(a).getDisplayName().equals(s)) {
                        l.add(theGolem.colors[a]);
                    }
                }
            }
        }

        return l;
    }

    boolean findSomething() {
        ArrayList<Byte> matchingColors = getColorsMatching(GolemChatHandler.messages.get(theGolem.getOwnerName()));
        Iterator i$ = matchingColors.iterator();

        label48:
        while(i$.hasNext()) {
            byte col = (Byte)i$.next();
            ArrayList<Marker> markers = this.theGolem.getMarkers();
            Iterator j$ = markers.iterator();

            Marker marker;
            ForgeDirection opp;
            do {
                do {
                    do {
                        do {
                            if (!j$.hasNext()) {
                                continue label48;
                            }

                            marker = (Marker)j$.next();
                        } while(marker.color != col && col != -1);
                    } while(this.theGolem.getToggles()[0] && !this.theGolem.worldObj.isAirBlock(marker.x, marker.y, marker.z));
                } while(!this.theGolem.getToggles()[0] && this.theGolem.worldObj.isAirBlock(marker.x, marker.y, marker.z));

                opp = ForgeDirection.getOrientation(marker.side);
            } while(!this.theGolem.worldObj.isAirBlock(marker.x + opp.offsetX, marker.y + opp.offsetY, marker.z + opp.offsetZ));

            this.color = col;
            this.xx = marker.x;
            this.yy = marker.y;
            this.zz = marker.z;
            this.block = this.theWorld.getBlock(this.xx, this.yy, this.zz);
            this.blockMd = this.theWorld.getBlockMetadata(this.xx, this.yy, this.zz);
            return true;
        }

        return false;
    }
}