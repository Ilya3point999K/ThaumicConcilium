package com.ilya3point999k.thaumicconcilium.common.items.equipment;

import com.ilya3point999k.thaumicconcilium.client.render.model.PontifexRobeModel;
import com.ilya3point999k.thaumicconcilium.common.TCPlayerCapabilities;
import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.*;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

import java.util.List;

public class PontifexRobe extends ItemArmor implements IRunicArmor, IRepairable, IGoggles, IRevealer, IVisDiscountGear, IWarpingGear {
    public IIcon[] icons = new IIcon[4];
    final static ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("TCPONTIFEXROBE", 30, new int[]{4, 8, 7, 4}, 25);
    static PontifexRobeModel model = null;
    
    public static String chest = ThaumicConcilium.MODID + ":textures/models/armor/pontifex_robe.png";

    public PontifexRobe(int j, int k) {
        super(MATERIAL, j, k);
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        for (int i = 0; i < 4; i++){
            this.icons[i] = ir.registerIcon(ThaumicConcilium.MODID+":pontifex"+i);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        if (itemStack != null && itemStack.getItem() instanceof PontifexRobe) {
            if (model == null){
                model = new PontifexRobeModel();
            }
            model.bipedHead.showModel = armorSlot == 0;
            model.armor.showModel = armorSlot == 1;
            model.cloth.showModel = armorSlot == 2;
            model.bipedRightArm.showModel = armorSlot == 1;
            model.bipedLeftArm.showModel = armorSlot == 1;
            model.Shape45RL.showModel = armorSlot == 2;
            model.Shape50LL.showModel = armorSlot == 2;
            model.leftBoot.showModel = armorSlot == 3;
            model.rightBoot.showModel = armorSlot == 3;
            model.isSneak = entityLiving.isSneaking();
            model.isRiding = entityLiving.isRiding();
            model.isChild = entityLiving.isChild();
            model.aimedBow = false;
            model.heldItemRight = entityLiving.getHeldItem() != null ? 1 : 0;
            if (entityLiving instanceof EntityPlayer && ((EntityPlayer)entityLiving).getItemInUseDuration() > 0) {
                EnumAction enumaction = ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction();
                if (enumaction == EnumAction.block) {
                    model.heldItemRight = 3;
                } else if (enumaction == EnumAction.bow) {
                    model.aimedBow = true;
                }
            }
            return model;
        }
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return chest;
    }

    public static boolean isFullSet(EntityPlayer player){
        return player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() instanceof PontifexRobe &&
                player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() instanceof PontifexRobe &&
                player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() instanceof PontifexRobe &&
                player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() instanceof PontifexRobe;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return icons[this.armorType];
    }

    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.epic;
    }


    @Override
    public int getRunicCharge(ItemStack itemstack) {
        return 5;
    }

    @Override
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!world.isRemote && player != null && isFullSet(player)){
            TCPlayerCapabilities capabilities = TCPlayerCapabilities.get(player);
            if (capabilities == null) return;
            if (!capabilities.pontifexRobeToggle) return;
            capabilities.chainedTime = 100;
            capabilities.sync();
        }
    }

    @Override
    public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
        return 5;
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + this.getVisDiscount(stack, player, null) + "%");
    }

    @Override
    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 3;
    }
}