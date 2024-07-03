package com.ilya3point999k.thaumicconcilium.common.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;

public class TightBelt extends Item implements IBauble {
    public TightBelt() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID+":tight_belt");
        this.setMaxDamage(-1);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("TightBelt");
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
        return BaubleType.BELT;
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
                if (!player.isPotionActive(Config.potionWarpWardID)) {
                    final int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(player.getCommandSenderName());
                    final int totalWarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(player.getCommandSenderName());
                    if (warp == 0) return;
                    PotionEffect pe = new PotionEffect(Config.potionThaumarhiaID, 60, Math.min(3, totalWarp / 15), true);
                    player.addPotionEffect(pe);
                    int x = (int)player.posX;
                    int y = (int)player.posY;
                    int z = (int)player.posZ;

                    if (warp > 0 && player.worldObj.rand.nextInt() % 25 == 0) {
                        Thaumcraft.addWarpToPlayer(player, -1, true);
                        if(player.worldObj.isAirBlock(x, y + 1, z)){
                            player.worldObj.setBlock(x, y + 1, z, ConfigBlocks.blockFluxGas, 0 ,3);
                        }
                    }
                }
            }
        }
    }

}