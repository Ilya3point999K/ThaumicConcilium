package com.ilya3point999k.thaumicconcilium.common.items.wands;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.integration.Integration;
import com.ilya3point999k.thaumicconcilium.common.registry.TCItemRegistry;
import fox.spiteful.forbidden.compat.Compat;
import fox.spiteful.forbidden.items.ForbiddenItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandable;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;

import java.lang.reflect.Method;
import java.util.List;

public class Bracelets extends ItemWandCasting {
    public static String[] names = new String[]{
            "livingwood",
            "dreamwood",
            "alchemist",
            "witchwood",
            "infernal",
            "tainted",
            "warpwood",
            "terrasteel"
    };

    public static ResourceLocation[] braceletTextures = new ResourceLocation[]{
            loc("botania", "textures/blocks/livingwood0.png"),
            loc("botania", "textures/blocks/dreamwood0.png"),
            loc("alchemicalwizardry", "textures/blocks/LargeBloodStoneBrick.png"),
            loc("arsmagica2", "textures/blocks/Witchwood.png"),
            loc("minecraft", "textures/blocks/netherrack.png"),
            loc("thaumcraft", "textures/blocks/taint_crust.png"),
            loc("taintedmagic", "textures/blocks/BlockWarpwoodLog_side.png"),
            loc("botania", "textures/blocks/storage1.png")
    };

    public static WandCap[] caps = new WandCap[]{
            ForbiddenItems.WAND_CAP_MANASTEEL,
            ForbiddenItems.WAND_CAP_ELEMENTIUM,
            ForbiddenItems.WAND_CAP_ALCHEMICAL,
            ForbiddenItems.WAND_CAP_VINTEUM,
            ConfigItems.WAND_CAP_THAUMIUM,
            ConfigItems.WAND_CAP_THAUMIUM,
            null,
            ForbiddenItems.WAND_CAP_TERRASTEEL
    };

    public static WandRod[] rods = new WandRod[]{
            ForbiddenItems.WAND_ROD_LIVINGWOOD,
            ForbiddenItems.STAFF_ROD_DREAMWOOD,
            ForbiddenItems.STAFF_ROD_BLOOD,
            ForbiddenItems.STAFF_ROD_WITCHWOOD,
            ForbiddenItems.WAND_ROD_INFERNAL,
            ForbiddenItems.WAND_ROD_TAINTED,
            null,
            TCItemRegistry.STAFF_ROD_TERRASTEEL
    };

    public static int[] capacity = new int[]{
            30,
            50,
            50,
            50,
            35,
            35,
            90,
            50
    };

    public Bracelets() {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName("CastingBracelet");
        this.setCreativeTab(ThaumicConcilium.tabTC);
    }

    public int getMaxVis(ItemStack stack) {
        return capacity[Math.min(names.length - 1, stack.getItemDamage())] * 100;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getSubItems(Item item, CreativeTabs tab, List lst) {
        if (Compat.botan) {
            lst.add(new ItemStack(item, 1, 0));
            lst.add(new ItemStack(item, 1, 1));
            lst.add(new ItemStack(item, 1, 7));
        }
        if (Compat.bm) {
            //Blood Magic initialization level of bullshit
            Bracelets bracelet = (Bracelets) item;
            ItemStack stack = new ItemStack(item, 1, 2);
            bracelet.setRod(stack, ForbiddenItems.WAND_ROD_BLOOD);
            lst.add(stack);

        }
        if (Compat.am2) {
            lst.add(new ItemStack(item, 1, 3));
        }
        lst.add(new ItemStack(item, 1, 4));
        lst.add(new ItemStack(item, 1, 5));
        if (Integration.taintedMagic) {
            lst.add(new ItemStack(item, 1, 6));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
        if (movingobjectposition != null)
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;
                Block b = world.getBlock(x, y, z);

                if (b instanceof IWandable) {
                    ItemStack is = ((IWandable) b).onWandRightClick(world, itemstack, player);
                    if (is != null)
                        return is;
                }

                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile != null && tile instanceof IWandable) {
                    ItemStack is = ((IWandable) tile).onWandRightClick(world, itemstack, player);
                    if (is != null)
                        return is;
                }
            }

        ItemFocusBasic focus = getFocus(itemstack);
        if (focus != null && !isOnCooldown(player)) {
            ItemStack ret = null;
            if(itemstack.getItemDamage() == 7){
                WandManager.setCooldown(player, focus.getActivationCooldown(getFocusItem(itemstack)) * 3);
                for (int i = 0; i < 10; i++) {
                    ret = focus.onFocusRightClick(itemstack, world, player, movingobjectposition);
                }
            } else {
                WandManager.setCooldown(player, focus.getActivationCooldown(getFocusItem(itemstack)) / 2);
                ret = focus.onFocusRightClick(itemstack, world, player, movingobjectposition);
            }
            if (ret != null)
                return ret;
        }

        return itemstack;
    }

    public boolean isOnCooldown(EntityLivingBase base) {
        try {
            Class<WandManager> manager = WandManager.class;
            Method method = manager.getDeclaredMethod("isOnCooldown", EntityLivingBase.class);
            boolean access = method.isAccessible();
            if (!access)
                method.setAccessible(true);

            boolean ret = Boolean.class.cast(method.invoke(null, base));

            if (!access)
                method.setAccessible(false);

            return ret;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stk) {
        return StatCollector.translateToLocal(getUnlocalizedName(stk));
    }

    public String getUnlocalizedName(ItemStack stk) {
        return super.getUnlocalizedName(stk) + "." + names[Math.min(names.length - 1, stk.getItemDamage())];
    }

    @Override
    public float getConsumptionModifier(ItemStack is, EntityPlayer player, Aspect aspect, boolean crafting) {
        float consumptionModifier = 0.3F;
        if(is.getItemDamage() == 7){
            return 3.0F - WandManager.getTotalVisDiscount(player, aspect) - (getFocusFrugal(is) / 11.0F);
        }
        if ((getCap(is).getSpecialCostModifierAspects() != null) && (getCap(is).getSpecialCostModifierAspects().contains(aspect)))
            consumptionModifier = getCap(is).getSpecialCostModifier() / 1.5F;
        else
            consumptionModifier = getCap(is).getBaseCostModifier() / 1.5F;

        if (player != null)
            consumptionModifier -= WandManager.getTotalVisDiscount(player, aspect);

        if (getFocus(is) != null && !crafting)
            consumptionModifier -= getFocusFrugal(is) / 11.0F;

        return Math.max(consumptionModifier, 0.1F);
    }

    @Override
    public WandRod getRod(ItemStack stack) {
        return rods[Math.min(rods.length - 1, stack.getItemDamage())];
    }

    @Override
    public WandCap getCap(ItemStack stack) {
        return caps[Math.min(caps.length - 1, stack.getItemDamage())];
    }

    public boolean isStaff(ItemStack stack) {
        return true;
    }

    public boolean isSceptre(ItemStack stack) {
        return false;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        IWandable wandable = getObjectInUse(stack, player.worldObj);
        if (wandable != null) {
            this.animation = ItemFocusBasic.WandFocusAnimation.WAVE;
            wandable.onUsingWandTick(stack, player, count);
            ++count;
            wandable.onUsingWandTick(stack, player, count);
        } else {
            ItemFocusBasic focus = getFocus(stack);
            if (focus != null && !isOnCooldown(player)) {
                if(stack.getItemDamage() == 7){
                    WandManager.setCooldown(player, focus.getActivationCooldown(getFocusItem(stack)) / 2);
                    for(int i = 0; i < 10; i++){
                        focus.onUsingFocusTick(stack, player, i);
                    }
                } else {
                    WandManager.setCooldown(player, focus.getActivationCooldown(getFocusItem(stack)) / 2);
                    focus.onUsingFocusTick(stack, player, count);
                    ++count;
                    focus.onUsingFocusTick(stack, player, count);
                }
            }
        }
    }

    public static final ResourceLocation loc(String mod, String path) {
        return new ResourceLocation(mod, path);
    }

}