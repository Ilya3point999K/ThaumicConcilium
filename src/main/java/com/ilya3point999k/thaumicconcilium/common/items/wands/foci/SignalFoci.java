package com.ilya3point999k.thaumicconcilium.common.items.wands.foci;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import com.ilya3point999k.thaumicconcilium.common.entities.projectiles.CompressedBlastEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;

import java.awt.*;
import java.util.Random;

public class SignalFoci extends ItemFocusBasic {
    private static final AspectList COST = new AspectList().add(Aspect.FIRE, 100).add(Aspect.AIR, 100);
    private static final AspectList FLASHBANG_COST = new AspectList().add(Aspect.FIRE, 500).add(Aspect.AIR, 1000);
    private static final int[] COLORS =  {11743532, 15435844, 14602026, 4312372, 3887386, 6719955, 2651799, 2437522, 8073150, 12801229, 14188952, 15790320, 11250603, 4408131, 1973019, 5320730};
    private IIcon depthIcon = null;

    public SignalFoci() {
        super();
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setTextureName(ThaumicConcilium.MODID+":signal_foci");
        this.setUnlocalizedName("SignalFoci");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(ThaumicConcilium.MODID+":signal_foci");
        this.depthIcon = ir.registerIcon(ThaumicConcilium.MODID+":signal_depth");
    }

    public IIcon getFocusDepthLayerIcon(ItemStack stack) {
        return this.depthIcon;
    }

    public String getSortingHelper(ItemStack stack) {
        return "SIGNAL" + super.getSortingHelper(stack);
    }

    public int getFocusColor(ItemStack stack) {
        NBTTagCompound fociTag = stack.getTagCompound();
        if (fociTag != null) {
            if (fociTag.hasKey("Color")) {
                int color = fociTag.getInteger("Color");
                if (color == COLORS.length) {
                    EntityPlayer player = ThaumicTinkerer.proxy.getClientPlayer();
                    return player == null ? 0xFFFFFF : Color.HSBtoRGB(player.ticksExisted * 2 % 360 / 360F, 1F, 1F);
                } else return COLORS[color];
            }
        }
        return 11743532;
    }

    public int getActivationCooldown(ItemStack focusstack) {
        return 1000;
    }

    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mob) {
        ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
        if (!world.isRemote) {
            if (wand.consumeAllVis(itemstack, p, this.getVisCost(itemstack), false, false)) {
                NBTTagCompound fociTag = itemstack.getTagCompound().getCompoundTag("focus").getCompoundTag("tag");
                if (!fociTag.hasKey("Color")){
                    fociTag.setInteger("Color", 0);
                }
                if(!p.isSneaking()) {
                    wand.consumeAllVis(itemstack, p, this.getVisCost(itemstack), true, false);
                    int color = fociTag.getInteger("Color");
                    if (color == COLORS.length) {
                        p.worldObj.spawnEntityInWorld(new CompressedBlastEntity(p.worldObj, p.posX, p.posY + p.getEyeHeight(), p.posZ, p, generateFirework(itemstack, COLORS[p.worldObj.rand.nextInt(COLORS.length)]), itemstack));
                    }
                    else p.worldObj.spawnEntityInWorld(new CompressedBlastEntity(p.worldObj, p.posX, p.posY + p.getEyeHeight(), p.posZ, p, generateFirework(itemstack, COLORS[color]), itemstack));
                }
                else {
                    int color = fociTag.getInteger("Color") + 1;
                    if (color > COLORS.length) color = 0;
                    fociTag.setInteger("Color", color);
                    itemstack.getTagCompound().getCompoundTag("focus").setTag("tag", fociTag);
                }
            }
        }
        p.swingItem();
        return itemstack;
    }


    public AspectList getVisCost(ItemStack itemstack) {
        return this.isUpgradedWith(itemstack, TCFociUpgrades.flashbang) ? FLASHBANG_COST : COST;
    }

    public ItemStack generateFirework(ItemStack wandstack, int color) {
        ItemStack stack = new ItemStack(Items.fireworks);
        NBTTagCompound explosion = new NBTTagCompound();
        explosion.setIntArray("Colors", new int[] { color });

        ItemWandCasting wand = (ItemWandCasting) wandstack.getItem();
        int type = 0;
        if(this.isUpgradedWith(wand.getFocusItem(wandstack), TCFociUpgrades.largeBall)) type = 1;
        if(this.isUpgradedWith(wand.getFocusItem(wandstack), TCFociUpgrades.star)) type = 2;
        if(this.isUpgradedWith(wand.getFocusItem(wandstack), TCFociUpgrades.creeper)) type = 3;
        if(this.isUpgradedWith(wand.getFocusItem(wandstack), TCFociUpgrades.amorphic)) type = 4;
        if(this.isUpgradedWith(wand.getFocusItem(wandstack), TCFociUpgrades.randomBang)){
            Random random = new Random(System.currentTimeMillis());
            type = random.nextInt(5);
        }
        /*
        double rand = Math.random();
        if(rand > 0.25) {
            if(rand > 0.9)
                type = 2;
            else type = 0;
        }
         */

        explosion.setInteger("Type", type);

        if(Math.random() < 0.05)
            if(Math.random() < 0.5)
                explosion.setBoolean("Flicker", true);
            else explosion.setBoolean("Trail", true);

        ItemNBTHelper.setCompound(stack, "Explosion", explosion);

        NBTTagCompound fireworks = new NBTTagCompound();
        fireworks.setInteger("Flight", (int) Math.random() * 3 + 2);

        NBTTagList explosions = new NBTTagList();
        explosions.appendTag(explosion);
        fireworks.setTag("Explosions", explosions);
        ItemNBTHelper.setCompound(stack, "Fireworks", fireworks);
        return stack;
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank) {
        switch (rank) {
            case 1:
                return new FocusUpgradeType[]{TCFociUpgrades.largeBall, TCFociUpgrades.star, TCFociUpgrades.creeper, TCFociUpgrades.amorphic, TCFociUpgrades.randomBang};
            case 2:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
            case 3:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
            case 4:
                return new FocusUpgradeType[]{FocusUpgradeType.frugal};
            case 5:
                return new FocusUpgradeType[]{TCFociUpgrades.flashbang};
            default:
                return null;
        }
    }
}
