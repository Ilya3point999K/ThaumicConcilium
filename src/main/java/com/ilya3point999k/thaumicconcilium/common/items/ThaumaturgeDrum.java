package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.entities.monster.EntityMindSpider;
import thaumcraft.common.entities.monster.EntityWisp;

import java.util.List;

public class ThaumaturgeDrum extends Item {
    public IIcon icon;

    public ThaumaturgeDrum() {
        this.setUnlocalizedName("ThaumaturgeDrum");
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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, Integer.MAX_VALUE);
        return stack;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List lst) {
        lst.add(new ItemStack(item, 1, 0));
        lst.add(new ItemStack(item, 1, 1));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 0:
                return StatCollector.translateToLocal("item.ThaumaturgeDrum.name");
            case 1:
                return StatCollector.translateToLocal("item.WarpedDrum.name");
            default:
                return StatCollector.translateToLocal("item.WarpedDrum.name");
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (!player.worldObj.isRemote) {
            if (count % 8 == 0) {
                player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "note.bd", 1.0F, player.worldObj.rand.nextFloat());
            }
            if (stack.getItemDamage() == 0) {
                if (count % 30 == 0) {
                    EntityWisp wisp = new EntityWisp(player.worldObj);
                    wisp.setPositionAndRotation(player.posX, player.posY, player.posZ, player.worldObj.rand.nextFloat(), player.worldObj.rand.nextFloat());
                    wisp.setType((String) Aspect.getPrimalAspects().get(player.worldObj.rand.nextInt(Aspect.getPrimalAspects().size())).getTag());
                    player.worldObj.spawnEntityInWorld(wisp);
                }
            } else {
                if (count % 10 == 0) {
                    List<EntityMindSpider> spiders = player.worldObj.getEntitiesWithinAABB(EntityMindSpider.class, player.boundingBox.expand(16, 8, 16));
                    if (spiders.size() < 16) {
                        EntityMindSpider spider = new EntityMindSpider(player.worldObj);
                        spider.setHarmless(true);
                        spider.setPositionAndRotation(player.posX, player.posY, player.posZ, player.worldObj.rand.nextFloat(), player.worldObj.rand.nextFloat());
                        player.worldObj.spawnEntityInWorld(spider);
                    }
                }
            }

        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }
}
