package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;

import java.util.List;

public class PontifexHammer extends ItemSword implements IRepairable, IWarpingGear {
    public static ToolMaterial phammer = EnumHelper.addToolMaterial("PHAMMER", 4, 1000, 8F, 14, 20);

    public PontifexHammer() {
        super(phammer);
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setUnlocalizedName("PontifexHammer");
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.epic;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
        if (stack.isItemDamaged() && entity != null && entity.ticksExisted % 20 == 0 && entity instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase)entity);
        }

    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        super.onUsingTick(stack, player, count);
        if (count % 20 == 0) {
                List<EntityLivingBase> list = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, player.boundingBox.expand(6.0, 2.0, 6.0));
                list.remove(player);
                if (!list.isEmpty()) {
                    for (EntityLivingBase e : list) {
                        if (!e.isDead) {
                            double x = e.posX;
                            double y = e.posY;
                            double z = e.posZ;
                            if (!player.worldObj.isRemote) {
                                if(e.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue())) {
                                    player.heal(2.0F);
                                }
                            } else {
                                for (int i = 0; i < 3; i++) {
                                    ThaumicConcilium.proxy.lifedrain(player, x, y, z);
                                }
                            }
                    }
                }
            }
        }
    }

    @Override
    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 3;
    }
}
