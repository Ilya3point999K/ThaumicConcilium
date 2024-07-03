package com.ilya3point999k.thaumicconcilium.common.items.equipment;

import com.ilya3point999k.thaumicconcilium.common.ThaumicConcilium;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.lwjgl.Sys;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.entities.projectile.EntityEmber;
import thaumcraft.common.entities.projectile.EntityFrostShard;
import thaumcraft.common.entities.projectile.EntityGolemOrb;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.ProjectileHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElementalSurferShorts extends ItemArmor {
    private static final AspectList FIRECOST = new AspectList().add(Aspect.FIRE, 200);
    private static final AspectList AIRCOST = new AspectList().add(Aspect.AIR, 200);
    private static final AspectList WATERCOST = new AspectList().add(Aspect.WATER, 200);
    private static final AspectList EARTHCOST = new AspectList().add(Aspect.EARTH, 200);
    private static final AspectList DEFENCECOST = new AspectList().add(Aspect.ORDER, 200).add(Aspect.ENTROPY, 200);

    public ElementalSurferShorts() {
        super(ThaumcraftApi.armorMatSpecial, 0, 2);
        this.setCreativeTab(ThaumicConcilium.tabTC);
        this.setMaxStackSize(1);
        this.setTextureName(ThaumicConcilium.MODID + ":shorts");
    }



    @Override
    public void onArmorTick(World world, EntityPlayer p, ItemStack itemStack) {
        if (!p.worldObj.isRemote) {
            if (p.isSneaking()) {
                if(ThaumcraftApiHelper.consumeVisFromInventory(p, DEFENCECOST)) {
                    List<Entity> projectiles = p.worldObj.getEntitiesWithinAABB(EntityThrowable.class, AxisAlignedBB.getBoundingBox(p.posX - 4, p.posY - 4, p.posZ - 4, p.posX + 3, p.posY + 3, p.posZ + 3));

                    for (Entity e : projectiles) {
                        if (ProjectileHelper.getOwner(e) != p)
                            continue;
                        Vector3 motionVec = new Vector3(e.motionX, e.motionY, e.motionZ).normalize().multiply(Math.sqrt((e.posX - p.posX) * (e.posX - p.posX) + (e.posY - p.posY) * (e.posY - p.posY) + (e.posZ - p.posZ) * (e.posZ - p.posZ)));
                        e.posX += motionVec.x;
                        e.posY += motionVec.y;
                        e.posZ += motionVec.z;
                    }
                }
                if(p.ticksExisted % 7 == 0) {
                    if (ThaumcraftApiHelper.consumeVisFromInventory(p, FIRECOST)) {
                        int range = 17;
                        p.getLook((float) range);
                            /*if (!p.worldObj.isRemote && this.soundDelay < System.currentTimeMillis()) {
                                p.worldObj.playSoundAtEntity(p, "thaumcraft:fireloop", 0.33F, 2.0F);
                                this.soundDelay = System.currentTimeMillis() + 500L;
                            }
                            */
                        float scatter = 15.0F;
                        for (int i = 0; i < 8; i++) {
                            EntityEmber orb = new EntityEmber(p.worldObj, p, scatter);
                            orb.damage = (float) 5;
                            orb.motionX *= -0.5f + Math.random();
                            orb.motionY *= Math.random();
                            orb.motionZ *= -0.5f + Math.random();
                            orb.firey = 0;
                            orb.posX += orb.motionX * (5.0f + Math.random());
                            orb.posY += orb.motionY * (5.0f + Math.random());
                            orb.posZ += orb.motionZ * (5.0f + Math.random());
                            p.worldObj.spawnEntityInWorld(orb);
                        }
                    }

                    if (ThaumcraftApiHelper.consumeVisFromInventory(p, WATERCOST)) {
                        EntityFrostShard shard = null;
                        for(int a = 0; a < 8; ++a) {
                            shard = new EntityFrostShard(world, p, 8.0F);
                            shard.setDamage(1.0F);
                            shard.fragile = true;
                            shard.motionX *= -0.5f + Math.random();
                            shard.motionY *= (2.0f * Math.random());
                            shard.motionZ *= -0.5f + Math.random();
                            shard.posX += shard.motionX * (2.0f + Math.random());
                            shard.posY += 1.0f;
                            shard.posZ += shard.motionZ * (2.0f + Math.random());
                            shard.setFrosty(4);
                            world.spawnEntityInWorld(shard);
                        }
                    }

                    return;
                }

                if(p.ticksExisted % 13 == 0) {
                    if (ThaumcraftApiHelper.consumeVisFromInventory(p, AIRCOST)) {
                        Vec3 v = p.getLook(1.0F);
                        List<EntityLivingBase> list = p.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, p.boundingBox.expand(7, 7, 7));
                        list.remove(p);
                        if (!list.isEmpty()) {
                            Collections.shuffle(list);
                            for (EntityLivingBase e : list) {
                                EntityGolemOrb blast = new EntityGolemOrb(p.worldObj, p, e, false);
                                blast.posX += v.xCoord;
                                blast.posY += 0.5f;
                                blast.posZ += v.zCoord;
                                blast.setPosition(blast.posX, blast.posY, blast.posZ);
                                double d0 = e.posX + e.motionX - p.posX;
                                double d1 = e.posY - p.posY - (double) (e.height / 2.0F);
                                double d2 = e.posZ + e.motionZ - p.posZ;
                                blast.setThrowableHeading(d0, d1, d2, 0.66F, 5.0F);
                                p.playSound("thaumcraft:egattack", 1.0F, 1.0F + p.worldObj.rand.nextFloat() * 0.1F);
                                p.worldObj.spawnEntityInWorld(blast);
                            }
                        }
                    }
                }
            }
        }
    }

}
