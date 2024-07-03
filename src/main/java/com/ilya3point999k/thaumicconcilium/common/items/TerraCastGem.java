package com.ilya3point999k.thaumicconcilium.common.items;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.RiftEntity;
import com.ilya3point999k.thaumicconcilium.common.items.wands.foci.VisConductorFoci;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.utils.BlockUtils;

import java.text.DecimalFormat;
import java.util.List;

public class TerraCastGem extends Item {
    public IIcon icon;
    private static AspectList COST = new AspectList().add(Aspect.EARTH, 1000).add(Aspect.AIR, 1000);

    public TerraCastGem() {
        this.setUnlocalizedName("TerraCastGem");
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setNoRepair();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
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
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.rare;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == 1) {
            player.setItemInUse(stack, Integer.MAX_VALUE);
        }
        return stack;
    }
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        final int md = stack.getItemDamage();
        switch (md) {
            case 0: return StatCollector.translateToLocal("item.TerraGemInert.name");
            case 1: return StatCollector.translateToLocal("item.TerraGem.name");
            default: return StatCollector.translateToLocal("item.TerraGemInert.name");
        }

    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (count % 80 == 0) {

            List<RiftEntity> list = player.worldObj.getEntitiesWithinAABB(RiftEntity.class, player.boundingBox.expand(16.0, 16.0, 16.0));
            if (list.isEmpty()) {
                MovingObjectPosition mop = BlockUtils.getTargetBlock(player.worldObj, player, false);
                if (mop != null) {
                    double tx = mop.hitVec.xCoord;
                    double ty = mop.hitVec.yCoord;
                    double tz = mop.hitVec.zCoord;
                    Block block = player.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
                    if (block.getMaterial().blocksMovement() && block.getBlockHardness(player.worldObj, mop.blockX, mop.blockY, mop.blockZ) >= 0.0) {
                        ItemStack item = new ItemStack(block);
                        AspectList al = ThaumcraftCraftingManager.getObjectTags(item);
                        al = ThaumcraftCraftingManager.getBonusTags(item, al);
                            if (al.getAmount(Aspect.EARTH) > 0 && ThaumcraftApiHelper.consumeVisFromInventory(player, COST)) {
                                if (!player.worldObj.isRemote) {
                                    player.worldObj.playSoundAtEntity(player, "thaumcraft:zap", 1.0F, 1.0F + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.2F);
                                    NBTTagCompound tag = new NBTTagCompound();
                                    tag.setString("name", GameData.getBlockRegistry().getNameForObject(block));
                                    tag.setInteger("meta", player.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
                                    tag.setInteger("dim", player.dimension);
                                    stack.setTagCompound(tag);
                                    player.worldObj.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
                                } else {
                                    VisConductorFoci.shootLightning(player.worldObj, player, tx, ty, tz, 0x00FF00);
                                   // Thaumcraft.proxy.arcLightning(player.worldObj, player.posX, player.posY, player.posZ, tx, ty, tz,  0.0F, 1.0F, 0.0F, 0.1f);
                                    for (int i = 0; i < 40; i++) {
                                        Thaumcraft.proxy.hungryNodeFX(player.worldObj, mop.blockX, mop.blockY, mop.blockZ, (int) player.posX, (int) player.posY, (int) player.posZ, block, player.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
                                    }
                                }
                            }

                    }
                }
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
                if(tag.hasKey("name")) {
                    ItemStack block = new ItemStack(GameData.getItemRegistry().getObject(tag.getString("name")), 1, tag.getInteger("meta"));
                    list.add(StatCollector.translateToLocal("tc.tooltip.terragem") + " " + block.getDisplayName());
                }
            }

        }
    }

        @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        //System.out.println(GameData.getBlockRegistry().getNameForObject(world.getBlock(x, y, z)));
        return false;
    }
}