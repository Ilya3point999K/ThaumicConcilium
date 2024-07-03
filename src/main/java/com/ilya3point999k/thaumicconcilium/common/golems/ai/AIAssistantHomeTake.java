package com.ilya3point999k.thaumicconcilium.common.golems.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.golems.GolemHelper;
import thaumcraft.common.lib.utils.InventoryUtils;

import java.util.ArrayList;
import java.util.Iterator;

import static thaumcraft.common.entities.golems.GolemHelper.findSomethingUseCore;

public class AIAssistantHomeTake extends EntityAIBase {
    private EntityGolemBase theGolem;
    private final static ItemStack JAR = new ItemStack(ConfigBlocks.blockJar);
    private int countChest = 0;
    private IInventory inv;

    public AIAssistantHomeTake(EntityGolemBase par1EntityCreature) {
        this.theGolem = par1EntityCreature;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        ChunkCoordinates home = this.theGolem.getHomePosition();
        if (this.theGolem.getCarried() == null && this.theGolem.ticksExisted % Config.golemDelay <= 0 && this.theGolem.getNavigator().noPath() && !(this.theGolem.getDistanceSq((double) ((float) home.posX + 0.5F), (double) ((float) home.posY + 0.5F), (double) ((float) home.posZ + 0.5F)) > 5.0)) {
            ForgeDirection facing = ForgeDirection.getOrientation(this.theGolem.homeFacing);
            int cX = home.posX - facing.offsetX;
            int cY = home.posY - facing.offsetY;
            int cZ = home.posZ - facing.offsetZ;
            TileEntity tile = this.theGolem.worldObj.getTileEntity(cX, cY, cZ);
            boolean repeat = true;
            boolean didRepeat = false;

            while (true) {
                while (repeat) {
                    if (didRepeat) {
                        repeat = false;
                    }

                    if (tile != null && tile instanceof IInventory) {
                        ArrayList<ItemStack> neededList = new ArrayList<>();
                        neededList.add(JAR);
                        ItemStack stack;
                        if (neededList == null) {
                            ItemStack is;
                            do {
                                is = GolemHelper.getFirstItemUsingTimeout(this.theGolem, (IInventory) tile, facing.ordinal(), false);
                                if (is != null) {
                                    stack = GolemHelper.getFirstItemUsingTimeout(this.theGolem, (IInventory) tile, facing.ordinal(), true);
                                    this.theGolem.setCarried(stack);

                                    try {
                                        if (Config.golemChestInteract) {
                                            ((IInventory) tile).openInventory();
                                        }
                                    } catch (Exception var16) {
                                    }

                                    this.countChest = 5;
                                    this.inv = (IInventory) tile;
                                    return true;
                                }
                            } while (is != null);

                            return false;
                        }

                        if (neededList.size() > 0) {
                            Iterator i$ = neededList.iterator();

                            while (i$.hasNext()) {
                                stack = (ItemStack) i$.next();
                                ItemStack needed = stack.copy();
                                needed.stackSize = this.theGolem.getCarrySpace();
                                ItemStack result = InventoryUtils.extractStack((IInventory) tile, needed, facing.ordinal(), this.theGolem.checkOreDict(), this.theGolem.ignoreDamage(), this.theGolem.ignoreNBT(), true);
                                if (result != null) {
                                    this.theGolem.setCarried(result);

                                    try {
                                        if (Config.golemChestInteract) {
                                            ((IInventory) tile).openInventory();
                                        }
                                    } catch (Exception var15) {
                                    }

                                    this.countChest = 5;
                                    this.inv = (IInventory) tile;
                                    return true;
                                }

                            }
                        }
                    }

                    if (!didRepeat && InventoryUtils.getDoubleChest((TileEntity) tile) != null) {
                        tile = InventoryUtils.getDoubleChest((TileEntity) tile);
                        didRepeat = true;
                    } else {
                        repeat = false;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public boolean continueExecuting() {
        return this.countChest > 0;
    }

    public void resetTask() {
        try {
            if (this.inv != null && Config.golemChestInteract) {
                this.inv.closeInventory();
            }
        } catch (Exception var2) {
        }

    }

    public void updateTask() {
        --this.countChest;
        super.updateTask();
    }

    public void startExecuting() {
    }

    public static boolean validTargetForItem(EntityGolemBase golem, ItemStack stack) {
        if (GolemHelper.isOnTimeOut(golem, stack)) {
            return false;
        } else {
            return findSomethingUseCore(golem, stack);
        }
    }

}