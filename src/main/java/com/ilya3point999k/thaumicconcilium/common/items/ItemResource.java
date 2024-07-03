package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.mobs.*;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityMob;
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
    public IIcon iconSummonDevice;
    public IIcon iconPrimordialLife;
    public IIcon iconVoidSlag;
    public IIcon iconCrimsonAnnales;


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
                    spawnThaumaturges(player);
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
        this.iconSummonDevice = ir.registerIcon(ThaumicConcilium.MODID+":iconThaumaturge");
        this.iconPrimordialLife = ir.registerIcon(ThaumicConcilium.MODID+":primordial_life");
        this.iconVoidSlag = ir.registerIcon(ThaumicConcilium.MODID+":void_slag");
        if (Integration.taintedMagic) {
            this.iconCrimsonAnnales = ir.registerIcon(ThaumicConcilium.MODID + ":crimson_annales");
        }
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
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 1:{
                return EnumRarity.epic;
            }
            case 2:{
                return EnumRarity.common;
            }
            case 3:{
                if (Integration.taintedMagic) {
                    return EnumRarity.uncommon;
                }
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
                return this.iconSummonDevice;
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
            }
            default: {
                return this.iconSummonDevice;
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

    private static void spawnThaumaturges(EntityPlayer player) {
        int amount = player.worldObj.rand.nextInt(6) + 6;
        for (int n = 0; n < amount; n++) {
            EntityMob mob;
            int roll = player.worldObj.rand.nextInt(5);
            switch (roll){
                case 0:{
                    mob = new Dissolved(player.worldObj);
                    break;
                }
                case 1:{
                    mob = new Overanimated(player.worldObj);
                    break;
                }
                case 2:{
                    mob = new QuicksilverElemental(player.worldObj);
                    break;
                }
                case 3:{
                    mob = new Samurai(player.worldObj);
                    break;
                }
                case 4:{
                    mob = new VengefulGolem(player.worldObj);
                    break;
                }
                default:{
                    mob = new Samurai(player.worldObj);
                }
            }
            int i = MathHelper.floor_double(player.posX);
            int j = MathHelper.floor_double(player.posY);
            int k = MathHelper.floor_double(player.posZ);

            for (int l = 0; l < 50; ++l) {
                int i1 = i + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
                int j1 = j + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
                int k1 = k + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
                if (World.doesBlockHaveSolidTopSurface(player.worldObj, i1, j1 - 1, k1)) {
                    mob.setPosition((double) i1, (double) j1, (double) k1);
                    if (player.worldObj.checkNoEntityCollision(mob.boundingBox) && player.worldObj.getCollidingBoundingBoxes(mob, mob.boundingBox).isEmpty() && !player.worldObj.isAnyLiquid(mob.boundingBox)) {
                        mob.setTarget(player);
                        mob.setAttackTarget(player);
                        player.worldObj.spawnEntityInWorld(mob);
                        break;
                    }
                }
            }
        }
    }

}