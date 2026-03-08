package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.items.EntityItemFireResistant;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketResearchComplete;
import thaumcraft.common.lib.research.ResearchManager;

import java.util.List;

public class ItemResource extends Item {
    @SideOnly(Side.CLIENT)
    public IIcon iconBlank;
    public IIcon iconPrimordialLife;
    public IIcon iconVoidSlag;
    public IIcon iconCrimsonAnnales;
    public IIcon iconNetherMembrane;

    public static String TAG_MEMBRANE = "Nether_Membrane_Tag";

    public ItemResource() {
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            switch (stack.getItemDamage()) {
                case 0: {
                    stack.stackSize--;
                    break;
                }
                case 3:{
                    if (Integration.taintedMagic) {
                        if (!ResearchManager.isResearchComplete(player.getCommandSenderName(), "CRIMSONANNALES")) {
                            if (ResearchManager.isResearchComplete(player.getCommandSenderName(), "CRIMSON") && ResearchManager.isResearchComplete(player.getCommandSenderName(), "TCTAINTEDMAGIC")) {
                                PacketHandler.INSTANCE.sendTo(new PacketResearchComplete("CRIMSONANNALES"), (EntityPlayerMP) player);
                                Thaumcraft.proxy.getResearchManager().completeResearch(player, "CRIMSONANNALES");
                                world.playSoundAtEntity(player, "thaumcraft:learn", 0.75F, 1.0F);
                            } else {
                                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.tooltip.book.2")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
                            }
                        }
                    }
                }
            }
        }
        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister ir) {
        this.iconBlank = ir.registerIcon(ThaumicConcilium.MODID+":iconThaumaturge");
        this.iconPrimordialLife = ir.registerIcon(ThaumicConcilium.MODID+":primordial_life");
        this.iconVoidSlag = ir.registerIcon(ThaumicConcilium.MODID+":void_slag");
        if (Integration.taintedMagic) {
            this.iconCrimsonAnnales = ir.registerIcon(ThaumicConcilium.MODID + ":crimson_annales");
        }
        this.iconNetherMembrane = ir.registerIcon(ThaumicConcilium.MODID+":nether_membrane");
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        if (stack.getItemDamage() == 3 || stack.getItemDamage() == 1){
            return 1;
        } else {
            return 64;
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List lst) {
        //lst.add(new ItemStack(item, 1, 0));
        lst.add(new ItemStack(item, 1, 1));
        lst.add(new ItemStack(item, 1, 2));
        if (Integration.taintedMagic){
            lst.add(new ItemStack(item, 1, 3));
        }
        lst.add(new ItemStack(item, 1, 4));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 1:{
                return EnumRarity.epic;
            }
            case 2:
            {
                return EnumRarity.common;
            }
            case 3:{
                if (Integration.taintedMagic) {
                    return EnumRarity.uncommon;
                } else return EnumRarity.common;
            }
            case 4:{
                return EnumRarity.uncommon;
            }
            default: {
                return EnumRarity.common;
            }
        }
    }

    public String getItemStackDisplayName(final ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 1:{
                return StatCollector.translateToLocal("item.PrimordialLife.name");
            }
            case 2:{
                return StatCollector.translateToLocal("item.VoidSlag.name");
            }
            case 3:{
                if (Integration.taintedMagic) {
                    return StatCollector.translateToLocal("item.CrimsonAnnales.name");
                }
                return "";
            }
            case 4:{
                return StatCollector.translateToLocal("item.NetherMembrane.name");
            }
            default: {
                return "";
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int md) {
        switch (md) {
            case 0: {
                return this.iconBlank;
            }
            case 1:{
                return this.iconPrimordialLife;
            }
            case 2:{
                return this.iconVoidSlag;
            }
            case 3:{
                if (Integration.taintedMagic) {
                    return this.iconCrimsonAnnales;
                }
                return this.iconBlank;
            }
            case 4:{
                return this.iconNetherMembrane;
            }
            default: {
                return this.iconBlank;
            }
        }
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        super.addInformation(stack, player, list, par4);
        if (stack != null) {
            switch (stack.getItemDamage()) {
                case 1:
                    list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.tooltip.life.0"));
                    list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.tooltip.life.1"));
                    break;
                case 3:
                    if (Integration.taintedMagic) {
                        list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.tooltip.book.0"));
                        list.add(EnumChatFormatting.DARK_BLUE + StatCollector.translateToLocal("tc.tooltip.book.1"));
                    }
                    break;
            }
        }

    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        if (itemstack != null){
            switch (itemstack.getItemDamage()){
                case 0:
                case 1:
                case 2:
                case 3:{
                    return super.createEntity(world, location, itemstack);
                }
                case 4:{
                    EntityItemFireResistant item = new EntityItemFireResistant(world, location.posX, location.posY, location.posZ, itemstack);
                    item.motionX = location.motionX;
                    item.motionY = location.motionY;
                    item.motionZ = location.motionZ;
                    item.delayBeforeCanPickup = 40;
                    return item;
                }
            }
        }
        return super.createEntity(world, location, itemstack);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        if (stack == null) return false;
        switch (stack.getItemDamage()){
            case 0:
            case 1:
            case 2:
            case 3:{
                return false;
            }
            case 4:{
                return true;
            }
        }
        return false;
    }
}