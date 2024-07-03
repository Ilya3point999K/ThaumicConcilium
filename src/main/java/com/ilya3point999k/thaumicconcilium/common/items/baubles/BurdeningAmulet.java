package com.ilya3point999k.thaumicconcilium.common.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.utils.EntityUtils;

public class BurdeningAmulet extends Item implements IBauble {
    public BurdeningAmulet() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID + ":burdening_amulet");
        this.setMaxDamage(-1);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("BurdeningAmulet");
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.uncommon;
    }

    @Override
    public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        // TODO Auto-generated method stub
        return BaubleType.AMULET;
    }

    @Override
    public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase ent) {
        if (!ent.worldObj.isRemote) {
            if (ent instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) ent;
                if (player.ticksExisted % 20 == 0) {
                    Entity look = EntityUtils.getPointedEntity(player.worldObj, player, 1.0, 32.0, 0.1F);
                    if (look instanceof EntityLivingBase) {
                        MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, look));
                    }
                }
                final int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(player.getCommandSenderName());
                if (warp == 0) return;
                if (!player.isPotionActive(Config.potionWarpWardID)) {
                    PotionEffect pe = new PotionEffect(Config.potionDeathGazeID, 60, Math.min(3, warp / 15), true);
                    player.addPotionEffect(pe);
                    if (warp > 0 && player.worldObj.rand.nextInt() % 10 == 0) {
                        Thaumcraft.addWarpToPlayer(player, -1, true);
                    }
                }
            }
        }
    }

}
