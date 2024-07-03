package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class AstralMonitor extends Item {
    public IIcon icon;

    public AstralMonitor() {
        this.setUnlocalizedName("AstralMonitor");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setNoRepair();
        this.setMaxStackSize(1);
    }
    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.rare;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:blank");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return this.icon;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.none;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote){
            if (player.isSneaking()) {
                player.openGui(ThaumicConcilium.instance, 4, world, MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        super.addInformation(stack, player, list, bool);
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) return;
        NBTTagCompound stacktag = tag.getCompoundTag("wand");
        if (stacktag == null) return;
        NBTTagCompound wandtag = stacktag.getCompoundTag("tag");
        if (wandtag == null) return;
        String xyl = wandtag.getString("Xylography");
        if (xyl != null && !xyl.isEmpty()) {
            list.add(StatCollector.translateToLocal("tc.tooltip.monitor") + " " + xyl);
        }
        if (wandtag.hasKey("lookX")){
            list.add(StatCollector.translateToLocal("tc.tooltip.monitor") + " X: " + wandtag.getInteger("lookX") + " Y: " + wandtag.getInteger("lookY") + " Z: " + wandtag.getInteger("lookZ"));
        }
    }
}