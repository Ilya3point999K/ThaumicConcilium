package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.RiftEntity;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.VisConductorFoci;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.lib.utils.BlockUtils;

import java.text.DecimalFormat;
import java.util.List;

public class RiftGem extends Item {
    public IIcon icon;
    public static final int MAX_GEMS = 16;
    private static AspectList COST = new AspectList().add(Aspect.ENTROPY, 1000).add(Aspect.AIR, 1000);
    public RiftGem() {
        this.setUnlocalizedName("RiftGem");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setNoRepair();
        this.setHasSubtypes(true);
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

    public int getMaxItemUseDuration(ItemStack itemstack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 0: return StatCollector.translateToLocal("item.RiftGemInert.name");
            case 1: return StatCollector.translateToLocal("item.RiftGem.name");
            default: return StatCollector.translateToLocal("item.RiftGemInert.name");
        }

    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == 1) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setInteger("MAX_GEMS", 0);
                tag.setInteger("CURR", 0);
                tag.setBoolean("REVERSE", false);
                stack.setTagCompound(tag);
            }
            if (player.isSneaking()) {
                int max = tag.getInteger("MAX_GEMS");
                if (max == 0) return stack;
                int curr = tag.getInteger("CURR") + 1;
                if (curr >= max) curr = 0;
                tag.setInteger("CURR", curr);
                stack.setTagCompound(tag);
            } else player.setItemInUse(stack, Integer.MAX_VALUE);
        }
        return stack;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (!(entityLiving instanceof EntityPlayer)) {
            return super.onEntitySwing(entityLiving, stack);
        }
        EntityPlayer player = (EntityPlayer) entityLiving;
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            tag.setInteger("MAX_GEMS", 0);
            tag.setInteger("CURR", 0);
            tag.setBoolean("REVERSE", true);
            stack.setTagCompound(tag);
        } else {
            if (!player.isSneaking()) {
                return super.onEntitySwing(entityLiving, stack);
            } else {
                tag.setBoolean("REVERSE", !tag.getBoolean("REVERSE"));
            }
        }
        return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (count % 80 == 0) {
            List<RiftEntity> list = player.worldObj.getEntitiesWithinAABB(RiftEntity.class, player.boundingBox.expand(16.0, 16.0, 16.0));
            if (list.isEmpty()) {
                if (ThaumcraftApiHelper.consumeVisFromInventory(player, COST)) {
                    MovingObjectPosition mop = BlockUtils.getTargetBlock(player.worldObj, player, false);
                    Vec3 look = player.getLookVec();
                    double tx = player.posX + look.xCoord * 5.0D;
                    double ty = player.posY + look.yCoord * 5.0D;
                    double tz = player.posZ + look.zCoord * 5.0D;
                    if (mop != null) {
                        tx = mop.hitVec.xCoord;
                        ty = mop.hitVec.yCoord;
                        tz = mop.hitVec.zCoord;
                    }
                    if (!player.worldObj.isRemote) {
                        player.worldObj.playSoundAtEntity(player, "thaumcraft:zap", 1.0F, 1.0F + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.2F);
                        RiftEntity.summonRift(player, new Vector3(tx, ty + 1.5, tz), true);
                    } else {
                        VisConductorFoci.shootLightning(player.worldObj, player, tx, ty, tz, 0x333333);
                    }
                }
                player.stopUsingItem();
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        super.addInformation(stack, player, list, bool);
        if (stack.getItemDamage() == 1) {
            AspectList al = COST;
            if (al != null && al.size() > 0) {
                list.add(StatCollector.translateToLocal("item.Focus.cost1"));
                for (Aspect aspect : al.getAspectsSorted()) {
                    DecimalFormat myFormatter = new DecimalFormat("#####.##");
                    String amount = myFormatter.format(al.getAmount(aspect) / 100f);
                    list.add(" \u00A7" + aspect.getChatcolor() + aspect.getName() + "\u00A7r x " + amount);
                }
            }

            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null) {
                boolean mode = tag.getBoolean("REVERSE");
                list.add(StatCollector.translateToLocal(mode ? "tc.tooltip.riftgem.deflate" : "tc.tooltip.riftgem.inflate"));
                int am = tag.getInteger("MAX_GEMS");
                if (am > 0) {
                    int curr = tag.getInteger("CURR");
                    ItemStack block = new ItemStack(GameData.getItemRegistry().getObject(tag.getString("NAME" + curr)), 1, tag.getInteger("META" + curr));
                    list.add(StatCollector.translateToLocal("tc.tooltip.riftgem") + " " + am + "/16");
                    list.add(StatCollector.translateToLocal("tc.tooltip.terragem") + " " + block.getDisplayName());
                }
            } else {
                list.add(StatCollector.translateToLocal("tc.tooltip.riftgem.inflate"));
            }
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        //System.out.println(GameData.getBlockRegistry().getNameForObject(world.getBlock(x, y, z)));
        return false;
    }
}