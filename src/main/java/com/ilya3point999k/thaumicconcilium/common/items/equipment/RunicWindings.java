package com.ilya3point999k.thaumicconcilium.common.items.equipment;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketRunicCharge;

import java.util.HashMap;
import java.util.List;

public class RunicWindings extends ItemArmor implements IRunicArmor, IVisDiscountGear {
    public IIcon iconChest;
    public IIcon iconLegs;

    public static HashMap<Integer, Long> nextTick;
    public static String chest =ThaumicConcilium.MODID + ":textures/models/armor/chest_windings.png";
    public static String legs = ThaumicConcilium.MODID + ":textures/models/armor/legs_windings.png";

    final static ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("TCWINDINGS", 1, new int[]{1, 1, 1, 1}, 30);

    public RunicWindings(int j, int k) {
        super(MATERIAL, j, k);
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }

    public int getRunicCharge(ItemStack itemstack) {
        return 200;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.iconChest = ir.registerIcon(ThaumicConcilium.MODID+":chest_windings_icon");
        this.iconLegs = ir.registerIcon(ThaumicConcilium.MODID+":legs_windings_icon");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return slot == 1 ? chest : legs;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return this.armorType == 1 ? this.iconChest : this.iconLegs;
    }

    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.uncommon;
    }

    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack armor) {
        if (!world.isRemote) {
            //final long time = System.currentTimeMillis();
            if(player.ticksExisted % 20 == 0) {
                int add = Thaumcraft.instance.runicEventHandler.runicCharge.get(player.getEntityId()) + 5;
                int runicCharge = Math.min(Thaumcraft.instance.runicEventHandler.runicInfo.get(player.getEntityId())[0], add);
                Thaumcraft.instance.runicEventHandler.runicCharge.put(player.getEntityId(), runicCharge);
                PacketHandler.INSTANCE.sendTo(new PacketRunicCharge(player, (short) runicCharge, Thaumcraft.instance.runicEventHandler.runicInfo.get(player.getEntityId())[0]), (EntityPlayerMP) player);
            }
        }
    }
    static {
        RunicWindings.nextTick = new HashMap<Integer, Long>();

    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
        return 3;
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + this.getVisDiscount(stack, player, (Aspect)null) + "%");
    }

}